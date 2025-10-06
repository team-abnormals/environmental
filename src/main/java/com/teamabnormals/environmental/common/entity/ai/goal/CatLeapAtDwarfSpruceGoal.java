package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.block.DwarfSpruceBlock;
import com.teamabnormals.environmental.common.block.DwarfSpruceHeadBlock;
import com.teamabnormals.environmental.common.block.DwarfSprucePlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CatLeapAtDwarfSpruceGoal extends Goal {
	private final Cat cat;
	private int nextStartTick;
	private int tryTicks;
	private int maxStayTicks;
	private BlockPos targetPos;
	private boolean leaping;

	public CatLeapAtDwarfSpruceGoal(Cat cat) {
		this.cat = cat;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		if (this.nextStartTick > 0) {
			--this.nextStartTick;
			return false;
		} else {
			this.nextStartTick = 200 + this.cat.getRandom().nextInt(200);
			if (!this.cat.isTame() || this.cat.isOrderedToSit() || this.cat.isLying()) {
				return false;
			} else {
				this.targetPos = this.findNearestTorchPos();
				return this.targetPos != null;
			}
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (this.tryTicks < -this.maxStayTicks || this.tryTicks > 600)
			return false;
		else
			return ((!this.cat.onGround() && !this.cat.isInFluidType()) || !this.leaping) && this.isTorchSpruce(this.cat.level(), this.targetPos);
	}

	@Override
	public void start() {
		this.moveMobToBlock();
		this.cat.setInSittingPose(false);
		this.tryTicks = 0;
		this.maxStayTicks = this.cat.getRandom().nextInt(this.cat.getRandom().nextInt(1200) + 1200) + 1200;
		this.leaping = false;
	}

	@Override
	public void tick() {
		double d0 = this.targetPos.getX() + 0.5D - this.cat.getX();
		double d1 = this.targetPos.getY() - this.cat.getY();
		double d2 = this.targetPos.getZ() + 0.5D - this.cat.getZ();
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);

		if (this.leaping) {
			if (this.cat.blockPosition().equals(this.targetPos)) {
				BlockState blockstate = this.cat.level().getBlockState(this.targetPos);
				DwarfSpruceBlock block = (DwarfSpruceBlock) blockstate.getBlock();
				this.cat.level().setBlockAndUpdate(this.targetPos, block.getWithoutTorchesState(blockstate));
				if (block.getTorch() != null) {
					Block.popResource(this.cat.level(), this.targetPos, new ItemStack(block.getTorch()));
				}
			}
		} else {
			int patience = 1;

			if (d3 < 2.0D && this.cat.onGround()) {
				if (Math.abs(d1) <= 2.0D) {
					Vec3 vec3 = this.cat.getDeltaMovement();
					Vec3 vec31 = new Vec3(this.targetPos.getX() + 0.5D - this.cat.getX(), 0.0D, this.targetPos.getZ() + 0.5D - this.cat.getZ());
					if (vec31.lengthSqr() > 1.0E-7D)
						vec31 = vec31.normalize().scale(0.3D).add(vec3.scale(0.2D));
					double yd = Math.max(this.targetPos.getY() + 0.5D - this.cat.getY(), 0.8D);
					this.cat.setDeltaMovement(vec31.x, Math.sqrt(2.0D * this.cat.getAttributeValue(Attributes.GRAVITY) * yd), vec31.z);
					this.leaping = true;
					return;
				}
				patience = 3;
			}

			this.cat.getLookControl().setLookAt(this.targetPos.getX() + 0.5D, this.targetPos.getY() + 0.5D, this.targetPos.getZ() + 0.5D);

			for (int i = 0; i < patience; i++) {
				++this.tryTicks;
				if (this.tryTicks % 40 == 0)
					this.moveMobToBlock();
			}
		}
	}

	private void moveMobToBlock() {
		this.cat.getNavigation().moveTo(this.targetPos.getX() + 0.5D, this.targetPos.getY(), this.targetPos.getZ() + 0.5D, 0.6D);
	}

	private boolean isTorchSpruce(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof DwarfSpruceBlock dwarfspruce && dwarfspruce.getTorch() != null;
	}

	private boolean isSpruceBottom(LevelReader level, BlockPos pos) {
		BlockState blockstate = level.getBlockState(pos);
		Block block = blockstate.getBlock();
		return block instanceof DwarfSprucePlantBlock && blockstate.getValue(DwarfSprucePlantBlock.BOTTOM) || block instanceof DwarfSpruceHeadBlock && !blockstate.getValue(DwarfSpruceHeadBlock.TOP);
	}

	private BlockPos findNearestTorchPos() {
		BlockPos blockpos = this.cat.blockPosition();
		MutableBlockPos mutable = new MutableBlockPos();

		for (int y = 0; y <= 1; y = y > 0 ? -y : 1 - y) {
			for (int r = 0; r < 6; ++r) {
				for (int x = 0; x <= r; x = x > 0 ? -x : 1 - x) {
					for (int z = x < r && x > -r ? r : 0; z <= r; z = z > 0 ? -z : 1 - z) {
						mutable.setWithOffset(blockpos, x, y, z);
						if (this.isSpruceBottom(this.cat.level(), mutable)) {
							List<BlockPos> treepositions = new ArrayList<>();
							for (int i = 0; i <= 2; i++) {
								mutable.setWithOffset(blockpos, x, y + i, z);
								if (this.isTorchSpruce(this.cat.level(), mutable))
									treepositions.add(mutable.immutable());
							}
							if (!treepositions.isEmpty())
								return treepositions.get(this.cat.getRandom().nextInt(treepositions.size()));
						}
					}
				}
			}
		}

		return null;
	}
}