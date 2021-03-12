package com.minecraftabnormals.environmental.common.entity.goals;

import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataProcessors;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTags;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

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
			if (this.data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET)) {
				return true;
			}
			else {
				return this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) > 0 && this.findTruffle();
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
		this.pig.getLookController().setLookPosition(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D, (float)(this.pig.getHorizontalFaceSpeed() + 20), (float)this.pig.getVerticalFaceSpeed());
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

	private boolean findTruffle() {
		int range = 80;
		int height = 16;
		BlockPos blockpos = this.pig.getPosition();
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		List<BlockPos> truffleblocks = Lists.newArrayList();

		for(int i = 0; i < range; ++i) {
			boolean flag = false;
			
			for(int y = -height; y < height; ++y) {
				for(int x = 0; x <= i; x = x > 0 ? -x : 1 - x) {
					for(int z = x < i && x > -i ? i : 0; z <= i; z = z > 0 ? -z : 1 - z) {
						blockpos$mutable.setAndOffset(blockpos, x, y - 1, z);
						
						if (this.pig.isWithinHomeDistanceFromPosition(blockpos$mutable)) {
							if (this.isTruffle(this.pig.world, blockpos$mutable)) {
								this.data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, true);
								this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_POS, blockpos$mutable);
								return true;
							}
							else if (this.isSuitableForTruffle(this.pig.world, blockpos$mutable)) {
								if (i <= 48 && !flag) {
									flag = true;
									truffleblocks.clear();
								}

								truffleblocks.add(blockpos$mutable.toImmutable());
							}
						}
					}
				}
			}
		}

		if (this.pig.world.getDimensionType().isNatural() && truffleblocks.size() > 0) {
			BlockPos trufflepos = truffleblocks.get(this.pig.getRNG().nextInt(truffleblocks.size()));

			this.pig.world.setBlockState(trufflepos, EnvironmentalBlocks.BURIED_TRUFFLE.get().getDefaultState(), 3);
			this.data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, true);
			this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_POS, trufflepos);

			return true;
		}

		return false;
	}

	private boolean isTruffle(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock() == EnvironmentalBlocks.BURIED_TRUFFLE.get();
	}

	private boolean isSuitableForTruffle(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() != Blocks.DIRT)
			return false;

		for(Direction direction : Direction.values()) {
			BlockState blockstate = worldIn.getBlockState(pos.offset(direction));

			if (direction == Direction.UP) {
				if (!blockstate.isIn(EnvironmentalTags.Blocks.GRASS_LIKE)) {
					if (!blockstate.isIn(Tags.Blocks.DIRT) || !worldIn.getBlockState(pos.up()).isIn(EnvironmentalTags.Blocks.GRASS_LIKE)) {
						return false;
					}
				}
			}
			else if (!blockstate.isIn(Tags.Blocks.DIRT)) {
				return false;
			}
		}

		return true;
	}
}