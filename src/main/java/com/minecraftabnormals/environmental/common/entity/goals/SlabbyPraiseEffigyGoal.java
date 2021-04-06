package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.block.SlabfishEffigyBlock;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.Random;

public class SlabbyPraiseEffigyGoal extends Goal {
	private final SlabfishEntity slabfish;
	private BlockPos effigyPos;

	public SlabbyPraiseEffigyGoal(SlabfishEntity slabfish) {
		this.slabfish = slabfish;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}

	@Override
	public boolean shouldExecute() {
		this.effigyPos = this.slabfish.getEffigy();
		if (this.effigyPos == null)
			return false;

		BlockState state = this.slabfish.world.getBlockState(this.effigyPos);
		if (!(state.getBlock() instanceof SlabfishEffigyBlock)) {
			this.slabfish.setEffigy(null);
			return false;
		}

		if (!this.slabfish.hasBackpack())
			return false;
		else if (this.slabfish.isSitting())
			return false;
		else if (isBackpackEmpty())
			return false;
		else return this.slabfish.world.isNightTime();
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.slabfish.hasBackpack() && !isBackpackEmpty() && !this.slabfish.isSitting() && this.effigyPos != null && this.slabfish.world.isNightTime();
	}

	@Override
	public void startExecuting() {
		if (this.effigyPos != null) {
			this.slabfish.getNavigator().tryMoveToXYZ(this.effigyPos.getX(), this.effigyPos.getY(), this.effigyPos.getZ(), 1.1F);
		}
	}

	@Override
	public void resetTask() {
		this.slabfish.getNavigator().clearPath();
	}

	@Override
	public void tick() {
		if (this.effigyPos != null) {
			BlockState state = this.slabfish.world.getBlockState(this.effigyPos);
			if (!(state.getBlock() instanceof SlabfishEffigyBlock))
				return;

			this.slabfish.getNavigator().tryMoveToXYZ(this.effigyPos.getX() + 0.5, this.effigyPos.getY(), this.effigyPos.getZ() + 0.5, 1.1D);
			if (this.slabfish.getDistanceSq(this.effigyPos.getX() + 0.5, this.effigyPos.getY(), this.effigyPos.getZ() + 0.5) < 3.5D) {
				this.slabfish.getLookController().setLookPosition(this.effigyPos.getX() + 0.5, this.effigyPos.getY() - 1, this.effigyPos.getZ() + 0.5);
				this.slabfish.getNavigator().clearPath();
				this.throwItems(this.effigyPos);
			}
		}
	}

	private void throwItems(BlockPos pos) {
		Random rand = this.slabfish.getRNG();
		for (int i = 3; i < this.slabfish.slabfishBackpack.getSizeInventory(); i++) {
			ItemStack stack = this.slabfish.slabfishBackpack.getStackInSlot(i);
			if (stack.isEmpty())
				continue;

			ItemEntity item = new ItemEntity(this.slabfish.world, this.slabfish.getPosX(), this.slabfish.getPosYEye(), this.slabfish.getPosZ(), stack);
			item.setNoDespawn();
			item.setThrowerId(this.slabfish.getUniqueID());

			this.slabfish.getLookController().setLookPosition(pos.getX() + 0.5, pos.getY() - 1F, pos.getZ() + 0.5);
			float f8 = MathHelper.sin(this.slabfish.rotationPitch * ((float) Math.PI / 180F));
			float f2 = MathHelper.cos(this.slabfish.rotationPitch * ((float) Math.PI / 180F));
			float f3 = MathHelper.sin(this.slabfish.rotationYaw * ((float) Math.PI / 180F));
			float f4 = MathHelper.cos(this.slabfish.rotationYaw * ((float) Math.PI / 180F));
			float f5 = rand.nextFloat() * ((float) Math.PI * 2F);
			float f6 = 0.02F * rand.nextFloat();
			item.setMotion((double) (-f3 * f2 * 0.3F) + Math.cos(f5) * (double) f6, -f8 * 0.3F + 0.1F + (rand.nextFloat() - rand.nextFloat()) * 0.1F, (double) (f4 * f2 * 0.3F) + Math.sin(f5) * (double) f6);

			this.slabfish.world.addEntity(item);
			this.slabfish.slabfishBackpack.setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	private boolean isBackpackEmpty() {
		for (int i = 3; i < this.slabfish.slabfishBackpack.getSizeInventory(); i++) {
			ItemStack stack = this.slabfish.slabfishBackpack.getStackInSlot(i);
			if (!stack.isEmpty())
				return false;
		}
		return true;
	}
}