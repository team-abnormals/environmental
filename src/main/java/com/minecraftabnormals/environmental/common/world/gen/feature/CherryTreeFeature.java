package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.abnormals_core.core.util.TreeUtil;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CherryTreeFeature extends Feature<BaseTreeFeatureConfig> {
	public CherryTreeFeature(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos position, BaseTreeFeatureConfig config) {
		int height = 5 + rand.nextInt(3) + rand.nextInt(3) + rand.nextInt(3);
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + height + 1 <= worldIn.getMaxBuildHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == position.getY()) k = 0;
				if (j >= position.getY() + 1 + height - 2) k = 2;
				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxBuildHeight()) {
							if (!TreeUtil.isAirOrLeaves(worldIn, blockpos$mutableblockpos.set(l, j, i1)))
								flag = false;
						} else
							flag = false;
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isGrassOrDirt(worldIn, position.below()) && position.getY() < worldIn.getMaxBuildHeight()) {
				// base log
				TreeUtil.setDirtAt(worldIn, position.below());

				int logX = position.getX();
				int logZ = position.getZ();

				for (int k1 = 0; k1 < height; ++k1) {
					int logY = position.getY() + k1;
					BlockPos blockpos = new BlockPos(logX, logY, logZ);
					if (TreeUtil.isAirOrLeaves(worldIn, blockpos)) {
						this.placeLogAt(worldIn, blockpos, Direction.UP, rand, config);
					}
				}

				Plane.HORIZONTAL.stream().forEach(direction -> {
					BlockPos stumpPos = position.relative(direction);
					if (TreeUtil.isAirOrLeaves(worldIn, stumpPos) && isGrassOrDirt(worldIn, stumpPos.below())) {
						this.placeLogAt(worldIn, stumpPos, Direction.UP, rand, config);
						TreeUtil.setDirtAt(worldIn, stumpPos.below());
						BlockPos sideStumpPos = stumpPos.relative(direction.getClockWise());
						if (rand.nextBoolean() && isGrassOrDirt(worldIn, sideStumpPos.below()) && TreeUtil.isAirOrLeaves(worldIn, sideStumpPos)) {
							this.placeLogAt(worldIn, sideStumpPos, Direction.UP, rand, config);
							TreeUtil.setDirtAt(worldIn, sideStumpPos.below());
						}
						if (rand.nextBoolean() && TreeUtil.isAirOrLeaves(worldIn, stumpPos.above()))
							this.placeLogAt(worldIn, stumpPos.above(), Direction.UP, rand, config);
					}
				});


				Plane.HORIZONTAL.stream().forEach(direction -> {
					int newHeight = rand.nextBoolean() ? height + rand.nextInt(2) : height - rand.nextInt(2);
					BlockPos newPos = this.createCherryBranch(newHeight, worldIn, position, direction, rand, config);
					for (int i = 0; i < 5; ++i) {
						this.createCherryLeaves(worldIn, newPos.above(2).below(i), rand, i, config);
					}
				});
				return true;
			} else {
				return false;
			}
		} else {
			return false;

		}
	}

	private void createCherryLeaves(IWorldGenerationReader worldIn, BlockPos newPos, Random rand, int level, BaseTreeFeatureConfig config) {
		int leafSize = 2;
		for (int k3 = -leafSize; k3 <= leafSize; ++k3) {
			for (int j4 = -leafSize; j4 <= leafSize; ++j4) {
				if (level == 2) {
					this.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
				} else {
					if (level > 0 && level < 4 && (Math.abs(k3) != leafSize || Math.abs(j4) != leafSize)) {
						this.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
					} else if (rand.nextInt(4) == 0) {
						this.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
					}
				}
			}
		}
	}

	private BlockPos createCherryBranch(int height, IWorldGenerationReader worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		int logX = pos.getX();
		int logZ = pos.getZ();
		int logY = pos.getY() + height - 1;
		int length = 4 + rand.nextInt(3) + rand.nextInt(3);
		BlockPos blockpos = new BlockPos(logX, logY, logZ);

		for (int i = 0; i < length; i++) {
			blockpos = new BlockPos(logX, logY, logZ);

			this.createHorizontalLog(worldIn, blockpos, direction, rand, config);
			if (direction == Direction.EAST || direction == Direction.WEST) {
				if (direction == Direction.EAST) {
					logX += rand.nextInt(2);
				} else {
					logX -= rand.nextInt(2);
				}
			} else {
				if (direction == Direction.SOUTH) {
					logZ += rand.nextInt(2);
				} else {
					logZ -= rand.nextInt(2);
				}
				
			}

			logY += 1;
		}
		return blockpos.relative(direction);
	}

	private void createHorizontalLog(IWorldGenerationReader worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		int logX = pos.getX();
		int logY = pos.getY();
		int logZ = pos.getZ();

		for (int k3 = 0; k3 < 1; ++k3) {
			logX += direction.getStepX();
			logZ += direction.getStepZ();
			BlockPos blockpos1 = new BlockPos(logX, logY, logZ);
			if (TreeUtil.isAirOrLeaves(worldIn, blockpos1)) {
				this.placeLogAt(worldIn, blockpos1, Direction.UP, rand, config);
			}
		}
	}

	private void placeLogAt(IWorldWriter worldIn, BlockPos pos, Direction direction, Random rand, BaseTreeFeatureConfig config) {
		BlockState logState = config.trunkProvider.getState(rand, pos).setValue(RotatedPillarBlock.AXIS, direction.getAxis());
		TreeUtil.setForcedState(worldIn, pos, logState);
	}

	private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
		if (TreeUtil.isAirOrLeaves(world, pos)) {
			if (config.leavesProvider.getState(rand, pos).hasProperty(LeavesBlock.DISTANCE)) {
				TreeUtil.setForcedState(world, pos, config.leavesProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
			} else {
				TreeUtil.setForcedState(world, pos, config.leavesProvider.getState(rand, pos));
			}
		}
	}
}
