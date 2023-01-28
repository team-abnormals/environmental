package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;
import java.util.Set;

public class CherryTreeFeature extends Feature<TreeConfiguration> {
	private Set<BlockPos> logPosSet;

	public CherryTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		WorldGenLevel worldIn = context.level();
		Random rand = context.random();
		BlockPos position = context.origin();
		TreeConfiguration config = context.config();

		int height = 5 + rand.nextInt(3) + rand.nextInt(3) + rand.nextInt(3);
		boolean flag = true;

		this.logPosSet = Sets.newHashSet();
		if (position.getY() >= 1 && position.getY() + height + 1 <= worldIn.getMaxBuildHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == position.getY()) k = 0;
				if (j >= position.getY() + 1 + height - 2) k = 2;
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxBuildHeight()) {
							if (!TreeUtil.isAirOrLeaves(worldIn, blockpos$mutableblockpos.set(l, j, i1))) flag = false;
						} else flag = false;
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
						TreeUtil.placeLogAt(worldIn, blockpos, rand, config);
						logPosSet.add(blockpos.above().immutable());
					}
				}

				Plane.HORIZONTAL.stream().forEach(direction -> {
					BlockPos stumpPos = position.relative(direction);
					if (TreeUtil.isAirOrLeaves(worldIn, stumpPos) && isGrassOrDirt(worldIn, stumpPos.below())) {
						TreeUtil.placeLogAt(worldIn, stumpPos, rand, config);
						logPosSet.add(stumpPos.immutable());
						TreeUtil.setDirtAt(worldIn, stumpPos.below());
						BlockPos sideStumpPos = stumpPos.relative(direction.getClockWise());
						if (rand.nextBoolean() && isGrassOrDirt(worldIn, sideStumpPos.below()) && TreeUtil.isAirOrLeaves(worldIn, sideStumpPos)) {
							TreeUtil.placeLogAt(worldIn, sideStumpPos, rand, config);
							logPosSet.add(sideStumpPos.immutable());
							TreeUtil.setDirtAt(worldIn, sideStumpPos.below());
						}
						if (rand.nextBoolean() && TreeUtil.isAirOrLeaves(worldIn, stumpPos.above())) {
							TreeUtil.placeLogAt(worldIn, stumpPos.above(), rand, config);
							logPosSet.add(stumpPos.above().immutable());
						}
					}
				});

				Plane.HORIZONTAL.stream().forEach(direction -> {
					int newHeight = rand.nextBoolean() ? height + rand.nextInt(2) : height - rand.nextInt(2);
					BlockPos newPos = this.createCherryBranch(newHeight, worldIn, position, direction, rand, config);
					for (int i = 0; i < 5; ++i) {
						this.createCherryLeaves(worldIn, newPos.above(2).below(i), rand, i, config);
					}
				});

				TreeUtil.updateLeaves(worldIn, this.logPosSet);
				return true;
			} else {
				return false;
			}
		} else {
			return false;

		}
	}

	private void createCherryLeaves(LevelSimulatedRW worldIn, BlockPos newPos, Random rand, int level, TreeConfiguration config) {
		int leafSize = 2;
		for (int k3 = -leafSize; k3 <= leafSize; ++k3) {
			for (int j4 = -leafSize; j4 <= leafSize; ++j4) {
				if (level == 2) {
					TreeUtil.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
				} else {
					if (level > 0 && level < 4 && (Math.abs(k3) != leafSize || Math.abs(j4) != leafSize)) {
						TreeUtil.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
					} else if (rand.nextInt(4) == 0) {
						TreeUtil.placeLeafAt(worldIn, newPos.offset(k3, 0, j4), rand, config);
					}
				}
			}
		}
	}

	private BlockPos createCherryBranch(int height, LevelSimulatedRW worldIn, BlockPos pos, Direction direction, Random rand, TreeConfiguration config) {
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

	private void createHorizontalLog(LevelSimulatedRW worldIn, BlockPos pos, Direction direction, Random rand, TreeConfiguration config) {
		int logX = pos.getX();
		int logY = pos.getY();
		int logZ = pos.getZ();

		for (int k3 = 0; k3 < 1; ++k3) {
			logX += direction.getStepX();
			logZ += direction.getStepZ();
			BlockPos blockpos1 = new BlockPos(logX, logY, logZ);
			if (TreeUtil.isAirOrLeaves(worldIn, blockpos1)) {
				TreeUtil.placeLogAt(worldIn, blockpos1, rand, config);
				logPosSet.add(blockpos1.immutable());
			}
		}
	}
}
