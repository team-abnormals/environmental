package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class CedarTreeFeature extends BlueprintTreeFeature {
	private static final int[][][] LEAF_CORNERS = new int[][][]{
			{{2, 2}, {1, 3}, {3, 1}},
			{{-2, 2}, {-1, 3}, {-3, 1}},
			{{2, -2}, {1, -3}, {3, -1}},
			{{-2, -2}, {-1, -3}, {-3, -1}}
	};

	public CedarTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public BlockState getSapling() {
		return EnvironmentalBlocks.CEDAR_SAPLING.get().defaultBlockState();
	}

	@Override
	public void doPlace(FeaturePlaceContext<TreeConfiguration> context, TreeInfo info) {
		TreeConfiguration config = context.config();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		int trunkHeight = config.trunkPlacer.getTreeHeight(random);
		int mainFoliageStart = trunkHeight - 7;
		for (int y = 0; y < trunkHeight; y++) {
			info.addLog(origin.above(y));
		}
		int bottomFoliageTop = mainFoliageStart - 2;
		if (trunkHeight > 13) {
			if (random.nextBoolean()) {
				BlockPos bottomFoliagePos = origin.above(bottomFoliageTop);
				for (Direction horizontal : Direction.Plane.HORIZONTAL) {
					info.addFoliage(bottomFoliagePos.relative(horizontal));
					if (random.nextInt(4) != 0)
						info.addFoliage(bottomFoliagePos.relative(horizontal, 2));
				}
				encloseCloseSpottyLeaves(origin.above(bottomFoliageTop - 2), random, info, true);
			} else encloseCloseSpottyLeaves(origin.above(bottomFoliageTop), random, info, true);
		} else encloseCloseSpottyLeaves(origin.above(bottomFoliageTop), random, info, true);
		encloseCloseLeaves(origin.above(mainFoliageStart), info);
		encloseSpottyLeaves(origin.above(mainFoliageStart + 1), random, info);
		encloseCloseLeaves(origin.above(mainFoliageStart + 2), info);
		encloseLargeLeaves(origin.above(mainFoliageStart + 3), random, info);
		encloseCloseLeaves(origin.above(mainFoliageStart + 4), info);
		encloseLargeLeaves(origin.above(mainFoliageStart + 5), random, info);
		// Top leaves
		origin = origin.above(mainFoliageStart + 6);
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared > 4) continue;
				if (distanceSquared > 1 && random.nextBoolean()) continue;
				info.addFoliage(origin.offset(x, 0, z));
			}
		}
		origin = origin.above();
		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared > 9 || distanceSquared == 8) continue;
				if (distanceSquared > 4 && random.nextBoolean()) continue;
				info.addFoliage(origin.offset(x, 0, z));
			}
		}
		origin = origin.above();
		info.addFoliage(origin);
		encloseCloseSpottyLeaves(origin, random, info, false);
		if (random.nextInt(4) != 0) {
			info.addFoliage(origin.above());
		}
	}

	private static void encloseCloseSpottyLeaves(BlockPos pos, RandomSource random, TreeInfo info, boolean optional) {
		if (optional && random.nextBoolean()) return;
		for (Direction horizontal : Direction.Plane.HORIZONTAL) {
			if (random.nextInt(4) == 0) continue;
			info.addFoliage(pos.relative(horizontal));
		}
	}

	private static void encloseCloseLeaves(BlockPos pos, TreeInfo info) {
		for (Direction horizontal : Direction.Plane.HORIZONTAL) {
			info.addFoliage(pos.relative(horizontal));
		}
	}

	private static void encloseSpottyLeaves(BlockPos pos, RandomSource random, TreeInfo info) {
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared == 5 && random.nextBoolean()) continue;
				if (distanceSquared > 4) continue;
				info.addFoliage(pos.offset(x, 0, z));
			}
		}
	}

	private static void encloseLargeLeaves(BlockPos pos, RandomSource random, TreeInfo info) {
		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared > 9 || distanceSquared == 8) continue;
				info.addFoliage(pos.offset(x, 0, z));
			}
		}
		if (random.nextBoolean()) {
			int i = random.nextInt(4);
			placeCornerPair(info, pos, random, LEAF_CORNERS[i]);
			if (random.nextBoolean()) {
				int j = random.nextInt(3);
				if (j >= i) j++; // skip i so j != i
				placeCornerPair(info, pos, random, LEAF_CORNERS[j]);
			}
		}
	}

	private static void placeCornerPair(TreeInfo info, BlockPos origin, RandomSource random, int[][] pair) {
		info.addFoliage(origin.offset(pair[0][0], 0, pair[0][1]));
		if (random.nextBoolean()) {
			info.addFoliage(origin.offset(pair[1][0], 0, pair[1][1]));
		} else {
			info.addFoliage(origin.offset(pair[2][0], 0, pair[2][1]));
		}
	}

}
