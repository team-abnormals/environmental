package com.minecraftabnormals.environmental.common.entity.goals;

import java.util.EnumSet;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataProcessors;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class HuntTruffleGoal extends Goal {
	private final PigEntity pig;
	private final IDataManager data;
	private int runDelay;

	public HuntTruffleGoal(PigEntity pigIn) {
		this.pig = pigIn;
		this.data = (IDataManager) this.pig;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
	}

	public boolean shouldExecute() {
		if (this.runDelay > 0) {
			--this.runDelay;
			return false;
		} else {
			this.runDelay = 20;
			int trufflehuntingtime = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME);
			if (trufflehuntingtime == 0) {
				return false;
			}
			else {
				return this.data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) || (this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) > 0 && this.findATruffle());
			}
		}
	}

	public boolean shouldContinueExecuting() {
		return this.data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) != 0;
	}

	public void startExecuting() {
		this.moveToTruffle();
	}

	public void tick() {
		BlockPos blockpos = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);
		this.pig.getLookController().setLookPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ(), (float)(this.pig.getHorizontalFaceSpeed() + 20), (float)this.pig.getVerticalFaceSpeed());
		if (blockpos.withinDistance(this.pig.getPositionVec(), 4.0D)) {
			if (this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) > 0)
				this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, -800);
		}
		else {
			this.moveToTruffle();
		}
	}

	private void moveToTruffle() {
		BlockPos blockpos = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);
		this.pig.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) + 0.5D, (double)(blockpos.getY() + 1), (double)((float)blockpos.getZ()) + 0.5D, 1.2D);
	}

	private boolean findATruffle() {
		int i = 64;
		int j = 1;
		BlockPos blockpos = this.pig.getPosition();
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
			for(int l = 0; l < i; ++l) {
				for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
					for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
						blockpos$mutable.setAndOffset(blockpos, i1, k - 1, j1);
						if (this.pig.isWithinHomeDistanceFromPosition(blockpos$mutable) && this.isTruffle(this.pig.world, blockpos$mutable)) {
							this.data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, true);
							this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_POS, blockpos$mutable);
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	private boolean isTruffle(IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock() == EnvironmentalBlocks.BURIED_TRUFFLE.get();
	}
}