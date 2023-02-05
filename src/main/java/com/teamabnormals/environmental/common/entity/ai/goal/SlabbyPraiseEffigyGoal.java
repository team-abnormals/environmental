package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.block.SlabfishEffigyBlock;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.util.RandomSource;

public class SlabbyPraiseEffigyGoal extends Goal {
	private final Slabfish slabfish;
	private BlockPos effigyPos;

	public SlabbyPraiseEffigyGoal(Slabfish slabfish) {
		this.slabfish = slabfish;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		this.effigyPos = this.slabfish.getEffigy();
		if (this.effigyPos == null)
			return false;

		BlockState state = this.slabfish.level.getBlockState(this.effigyPos);
		if (!(state.getBlock() instanceof SlabfishEffigyBlock)) {
			this.slabfish.setEffigy(null);
			return false;
		}

		if (!this.slabfish.hasBackpack())
			return false;
		else if (this.slabfish.isOrderedToSit())
			return false;
		else if (isBackpackEmpty())
			return false;
		else if (state.getValue(SlabfishEffigyBlock.POWERED))
			return false;
		else return this.slabfish.level.isNight();
	}

	@Override
	public boolean canContinueToUse() {
		return this.slabfish.hasBackpack() && !isBackpackEmpty() && !this.slabfish.isOrderedToSit() && this.effigyPos != null && this.slabfish.level.isNight();
	}

	@Override
	public void start() {
		if (this.effigyPos != null) {
			this.slabfish.getNavigation().moveTo(this.effigyPos.getX(), this.effigyPos.getY(), this.effigyPos.getZ(), 1.1F);
		}
	}

	@Override
	public void stop() {
		this.slabfish.getNavigation().stop();
	}

	@Override
	public void tick() {
		if (this.effigyPos != null) {
			BlockState state = this.slabfish.level.getBlockState(this.effigyPos);
			if (!(state.getBlock() instanceof SlabfishEffigyBlock))
				return;

			this.slabfish.getNavigation().moveTo(this.effigyPos.getX() + 0.5, this.effigyPos.getY(), this.effigyPos.getZ() + 0.5, 1.1D);
			this.slabfish.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(this.effigyPos.getX() + 0.5, this.effigyPos.getY() - 1, this.effigyPos.getZ() + 0.5));

			if (this.slabfish.distanceToSqr(this.effigyPos.getX() + 0.5, this.effigyPos.getY(), this.effigyPos.getZ() + 0.5) < 3.5D) {
				this.slabfish.getNavigation().stop();
				this.throwItems();
			}
		}
	}

	private void throwItems() {
		RandomSource rand = this.slabfish.getRandom();

		for (int i = 3; i < this.slabfish.slabfishBackpack.getContainerSize(); i++) {
			ItemStack stack = this.slabfish.slabfishBackpack.getItem(i);
			if (stack.isEmpty())
				continue;

			ItemEntity item = new ItemEntity(this.slabfish.level, this.slabfish.getX(), this.slabfish.getEyeY(), this.slabfish.getZ(), stack);
			item.setExtendedLifetime();
			item.setThrower(this.slabfish.getUUID());
			item.getPersistentData().putBoolean("EffigyItem", true);

			float f8 = Mth.sin(this.slabfish.getXRot() * ((float) Math.PI / 180F));
			float f2 = Mth.cos(this.slabfish.getXRot() * ((float) Math.PI / 180F));
			float f3 = Mth.sin(this.slabfish.getYRot() * ((float) Math.PI / 180F));
			float f4 = Mth.cos(this.slabfish.getYRot() * ((float) Math.PI / 180F));
			float f5 = rand.nextFloat() * ((float) Math.PI * 2F);
			float f6 = 0.02F * rand.nextFloat();
			item.setDeltaMovement((double) (-f3 * f2 * 0.3F) + Math.cos(f5) * (double) f6, -f8 * 0.3F + 0.1F + (rand.nextFloat() - rand.nextFloat()) * 0.1F, (double) (f4 * f2 * 0.3F) + Math.sin(f5) * (double) f6);

			this.slabfish.level.addFreshEntity(item);
			this.slabfish.slabfishBackpack.setItem(i, ItemStack.EMPTY);
		}
	}

	private boolean isBackpackEmpty() {
		for (int i = 3; i < this.slabfish.slabfishBackpack.getContainerSize(); i++) {
			ItemStack stack = this.slabfish.slabfishBackpack.getItem(i);
			if (!stack.isEmpty())
				return false;
		}
		return true;
	}
}