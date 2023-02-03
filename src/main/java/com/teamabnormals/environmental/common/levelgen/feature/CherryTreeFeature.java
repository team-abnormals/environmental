package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
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
		WorldGenLevel level = context.level();
		Random random = context.random();
		BlockPos pos = context.origin();
		TreeConfiguration config = context.config();

		int height = 5 + random.nextInt(3) + random.nextInt(2) + random.nextInt(2);
		boolean flag = true;

		this.logPosSet = Sets.newHashSet();
		if (pos.getY() >= level.getMinBuildHeight() && pos.getY() + height + 1 <= level.getMaxBuildHeight()) {
			for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j) {
				int k = 1;
				if (j == pos.getY())
					k = 0;
				if (j >= pos.getY() + 1 + height - 2)
					k = 2;
				MutableBlockPos mutablePos = new MutableBlockPos();
				for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
					for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < level.getMaxBuildHeight()) {
							if (!TreeUtil.isAirOrLeaves(level, mutablePos.set(l, j, i1))) flag = false;
						} else flag = false;
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isGrassOrDirt(level, pos.below()) && pos.getY() < level.getMaxBuildHeight()) {
				TreeUtil.setDirtAt(level, pos.below());

				int logX = pos.getX();
				int logZ = pos.getZ();

				for (int k = 0; k < height; ++k) {
					int logY = pos.getY() + k;
					BlockPos blockpos = new BlockPos(logX, logY, logZ);
					if (TreeUtil.isAirOrLeaves(level, blockpos)) {
						TreeUtil.placeLogAt(level, blockpos, random, config);
						logPosSet.add(blockpos.above().immutable());
					}
				}

				Plane.HORIZONTAL.stream().forEach(direction -> {
					BlockPos stumpPos = pos.relative(direction);
					if (TreeUtil.isAirOrLeaves(level, stumpPos) && isGrassOrDirt(level, stumpPos.below())) {
						TreeUtil.placeLogAt(level, stumpPos, random, config);
						logPosSet.add(stumpPos.immutable());
						TreeUtil.setDirtAt(level, stumpPos.below());
						BlockPos sideStumpPos = stumpPos.relative(direction.getClockWise());
						if (random.nextBoolean() && isGrassOrDirt(level, sideStumpPos.below()) && TreeUtil.isAirOrLeaves(level, sideStumpPos)) {
							TreeUtil.placeLogAt(level, sideStumpPos, random, config);
							logPosSet.add(sideStumpPos.immutable());
							TreeUtil.setDirtAt(level, sideStumpPos.below());
						}
						if (random.nextBoolean() && TreeUtil.isAirOrLeaves(level, stumpPos.above())) {
							TreeUtil.placeLogAt(level, stumpPos.above(), random, config);
							logPosSet.add(stumpPos.above().immutable());
						}
					}
				});

				Plane.HORIZONTAL.stream().forEach(direction -> {
					int newHeight = random.nextBoolean() ? height + random.nextInt(2) : height - random.nextInt(2);
					BlockPos newPos = this.createCherryBranch(newHeight, level, pos, direction, random, config);
					for (int i = 0; i < 5; ++i) {
						this.createCherryLeaves(level, newPos.above(2).below(i), random, i, config);
					}
				});

				TreeUtil.updateLeaves(level, this.logPosSet);
				return true;
			} else {
				return false;
			}
		} else {
			return false;

		}
	}

	private void createCherryLeaves(LevelSimulatedRW level, BlockPos pos, Random random, int leafLevel, TreeConfiguration config) {
		int leafSize = 2;
		for (int k = -leafSize; k <= leafSize; ++k) {
			for (int j = -leafSize; j <= leafSize; ++j) {
				if (leafLevel == 2) {
					TreeUtil.placeLeafAt(level, pos.offset(k, 0, j), random, config);
				} else {
					if (leafLevel > 1 && leafLevel < 4 && (Math.abs(k) != leafSize || Math.abs(j) != leafSize)) {
						TreeUtil.placeLeafAt(level, pos.offset(k, 0, j), random, config);
					} else if ((leafLevel == 1 || leafLevel == 4) && (Math.abs(k) <= 1 && Math.abs(j) <= 1)) {
						if ((!(Math.abs(k) == 1 && Math.abs(j) == 1 && leafLevel == 4) && !(Math.abs(k) == 2 && Math.abs(j) == 2)) || random.nextBoolean()) {
							TreeUtil.placeLeafAt(level, pos.offset(k, 0, j), random, config);
						}
					} else if (leafLevel == 1) {
						if (random.nextInt(3) == 0 && ((Math.abs(k) == 1 && Math.abs(j) == 2) || (Math.abs(k) == 2 && Math.abs(j) == 1))) {
							TreeUtil.placeLeafAt(level, pos.offset(k, 0, j), random, config);
						} else if (random.nextInt(4) != 0 && ((Math.abs(k) == 0 && Math.abs(j) == 2) || (Math.abs(k) == 2 && Math.abs(j) == 0))) {
							TreeUtil.placeLeafAt(level, pos.offset(k, 0, j), random, config);
						}
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
			if (rand.nextInt(4) != 0)
				logY++;
			if (direction.getAxis() == Axis.X) {
				logX += rand.nextInt(2) * direction.getAxisDirection().getStep();
			} else {
				logZ += rand.nextInt(2) * direction.getAxisDirection().getStep();
			}
		}

		for (int i = 0; i < 3; i++) {
			blockpos = new BlockPos(logX, logY, logZ);
			this.createHorizontalLog(worldIn, blockpos, direction, rand, config);
			logY++;
		}

		return blockpos.relative(direction);
	}

	private void createHorizontalLog(LevelSimulatedRW level, BlockPos pos, Direction direction, Random random, TreeConfiguration config) {
		int logX = pos.getX();
		int logY = pos.getY();
		int logZ = pos.getZ();

		for (int i = 0; i < 1; ++i) {
			logX += direction.getStepX();
			logZ += direction.getStepZ();
			BlockPos logPos = new BlockPos(logX, logY, logZ);
			if (TreeUtil.isAirOrLeaves(level, logPos)) {
				TreeUtil.placeLogAt(level, logPos, random, config);
				logPosSet.add(logPos.immutable());
			}
		}
	}
}
