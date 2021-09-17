package com.minecraftabnormals.environmental.common.entity.goals;

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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;
import java.util.List;

public class HuntTruffleGoal extends Goal {
	private final PigEntity pig;
	private final IDataManager data;
	private int runDelay;
	private int lookTimer;
	private Vector3d lookVector;

	public HuntTruffleGoal(PigEntity pigIn) {
		this.pig = pigIn;
		this.data = (IDataManager) this.pig;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
	}

	public boolean canUse() {
		if (this.runDelay > 0) {
			--this.runDelay;
			return false;
		} else {
			this.runDelay = 20;
			if (this.data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET)) {
				return true;
			} else {
				return this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) > 0 && this.findTruffle();
			}
		}
	}

	public boolean canContinueToUse() {
		return this.data.getValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET) && this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME) != 0;
	}

	public void start() {
		this.lookVector = new Vector3d(1.0D, 0.0D, 1.0D);
		this.moveToTruffle();
	}

	public void tick() {
		int trufflehuntingtime = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME);
		BlockPos blockpos = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);
		Vector3d pigpos = this.pig.position();

		Vector3d vector3d = new Vector3d((blockpos.getX() + 0.5D) - pigpos.x(), 0.0D, (blockpos.getZ() + 0.5D) - pigpos.z()).normalize();

		this.pig.getLookControl().setLookAt(pigpos.x() + vector3d.x() * this.lookVector.x(), this.pig.getY() - 0.6D + this.lookVector.y(), pigpos.z() + vector3d.z() * this.lookVector.z(), (float) (this.pig.getMaxHeadYRot() + 20), (float) this.pig.getMaxHeadXRot());

		if (blockpos.closerThan(this.pig.position(), 4.0D)) {
			if (trufflehuntingtime > 0)
				this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_HUNTING_TIME, -800);
		} else {
			if (this.lookTimer-- <= 0) {
				this.lookTimer = 18 + this.pig.getRandom().nextInt(9);
				this.lookVector = new Vector3d((double) this.pig.getRandom().nextFloat() * 1.2D, (double) this.pig.getRandom().nextFloat() * 0.4D, (double) this.pig.getRandom().nextFloat() * 1.2D);
			}

			this.moveToTruffle();
		}
	}

	private void moveToTruffle() {
		BlockPos blockpos = this.data.getValue(EnvironmentalDataProcessors.TRUFFLE_POS);
		this.pig.getNavigation().moveTo((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY() + 1, (double) ((float) blockpos.getZ()) + 0.5D, 1.1D);
	}

	private boolean findTruffle() {
		int range = 80;
		int height = 16;
		BlockPos blockpos = this.pig.blockPosition();
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		List<BlockPos> truffleblocks = Lists.newArrayList();

		for (int i = 0; i < range; ++i) {
			boolean flag = false;

			for (int y = -height; y < height; ++y) {
				for (int x = 0; x <= i; x = x > 0 ? -x : 1 - x) {
					for (int z = x < i && x > -i ? i : 0; z <= i; z = z > 0 ? -z : 1 - z) {
						blockpos$mutable.setWithOffset(blockpos, x, y - 1, z);

						if (this.pig.isWithinRestriction(blockpos$mutable)) {
							if (this.isTruffle(this.pig.level, blockpos$mutable)) {
								this.data.setValue(EnvironmentalDataProcessors.HAS_TRUFFLE_TARGET, true);
								this.data.setValue(EnvironmentalDataProcessors.TRUFFLE_POS, blockpos$mutable);
								return true;
							} else if (this.isSuitableForTruffle(this.pig.level, blockpos$mutable)) {
								if (i <= 48 && !flag) {
									flag = true;
									truffleblocks.clear();
								}

								truffleblocks.add(blockpos$mutable.immutable());
							}
						}
					}
				}
			}
		}

		if (this.pig.level.dimensionType().natural() && truffleblocks.size() > 0) {
			BlockPos trufflepos = truffleblocks.get(this.pig.getRandom().nextInt(truffleblocks.size()));

			this.pig.level.setBlock(trufflepos, EnvironmentalBlocks.BURIED_TRUFFLE.get().defaultBlockState(), 3);
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

		for (Direction direction : Direction.values()) {
			BlockState blockstate = worldIn.getBlockState(pos.relative(direction));

			if (direction == Direction.UP) {
				if (!blockstate.is(EnvironmentalTags.Blocks.GRASS_LIKE)) {
					if (!blockstate.is(Tags.Blocks.DIRT) || !worldIn.getBlockState(pos.above()).is(EnvironmentalTags.Blocks.GRASS_LIKE)) {
						return false;
					}
				}
			} else if (!blockstate.is(Tags.Blocks.DIRT)) {
				return false;
			}
		}

		return true;
	}
}