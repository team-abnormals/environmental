package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.levelgen.feature.BlueprintTreeFeature;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.NoiseBeehivesTreeConfiguration;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public class CedarTreeFeature extends BlueprintTreeFeature {
	private static final BeehiveDecorator BEEHIVE_DECORATOR = new BeehiveDecorator(1.0F);
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
		MiddleFoliageType middleFoliageType;
		if (trunkHeight > 13) {
			if (trunkHeight > 14) {
				if ((middleFoliageType = MiddleFoliageType.random(random)) != MiddleFoliageType.COMPRESSED)
					mainFoliageStart--;
			} else middleFoliageType = MiddleFoliageType.COMPRESSED;
			int bottomFoliageTop = mainFoliageStart - 2;
			if (random.nextBoolean()) {
				BlockPos bottomFoliagePos = origin.above(bottomFoliageTop);
				for (Direction horizontal : Direction.Plane.HORIZONTAL) {
					info.addFoliage(bottomFoliagePos.relative(horizontal));
					if (random.nextInt(4) != 0)
						info.addFoliage(bottomFoliagePos.relative(horizontal, 2));
				}
				encloseCloseSpottyLeaves(origin.above(bottomFoliageTop - 2), random, info, true);
			} else encloseCloseSpottyLeaves(origin.above(bottomFoliageTop), random, info, true);
		} else {
			middleFoliageType = MiddleFoliageType.COMPRESSED;
			encloseCloseSpottyLeaves(origin.above(mainFoliageStart - 2), random, info, true);
		}
		encloseCloseLeaves(origin.above(mainFoliageStart), random, info);
		encloseSpottyLeaves(origin.above(mainFoliageStart + 1), random, info);
		encloseCloseLeaves(origin.above(mainFoliageStart + 2), random, info);
		encloseLargeLeaves(origin.above(mainFoliageStart + 3), random, info);
		if (middleFoliageType == MiddleFoliageType.INVERTED_CONE) {
			encloseCloseLeaves(origin.above(++mainFoliageStart + 3), random, info);
			encloseMediumLeaves(origin.above(mainFoliageStart + 4), random, info, 2);
		} else {
			if (middleFoliageType == MiddleFoliageType.CONE)
				encloseMediumLeaves(origin.above(++mainFoliageStart + 3), random, info, 2);
			encloseCloseLeaves(origin.above(mainFoliageStart + 4), random, info);
		}
		encloseLargeLeaves(origin.above(mainFoliageStart + 5), random, info);
		// Top leaves
		origin = origin.above(mainFoliageStart + 6);
		encloseMediumLeaves(origin, random, info, random.nextInt(4) + 5);
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

	@Override
	public void doPostPlace(FeaturePlaceContext<TreeConfiguration> context, TreeInfo info) {
		RandomSource random = context.random();
		// Only sample noise if base is even successful
		if (random.nextFloat() > 0.0333 || !NoiseBeehivesTreeConfiguration.useNoiseBeehives(context.config())) return;
		WorldGenLevel level = context.level();
		ServerLevel serverLevel = level.getLevel();
		DensityFunction weirdnessFunction = serverLevel.getChunkSource().randomState().sampler().weirdness();
		BlockPos origin = context.origin();
		int x = origin.getX();
		int z = origin.getZ();
		int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		double weirdness = Math.abs(weirdnessFunction.compute(new DensityFunction.SinglePointContext(x, height, z)));
		if (weirdness > 0.1D) {
			double flowerPower = EnvironmentalNoiseParameters.SHRUB_FLOWER_POWER_RECEIVER.get(serverLevel).getValue(x, 0.0D, z);
			if (random.nextFloat() > 0.1F + Math.max(flowerPower, 0.0F)) return;
		}
		BEEHIVE_DECORATOR.place(new TreeDecorator.Context(level, (decorationPos, state) -> {
			level.setBlock(decorationPos, state, 19);
		}, random, info.logMap().keySet(), info.foliageMap().keySet(), Sets.newHashSet()));
	}

	private static void encloseCloseSpottyLeaves(BlockPos pos, RandomSource random, TreeInfo info, boolean optional) {
		if (optional && random.nextBoolean()) return;
		for (Direction horizontal : Direction.Plane.HORIZONTAL) {
			if (random.nextInt(4) == 0) continue;
			info.addFoliage(pos.relative(horizontal));
		}
	}

	private static void encloseCloseLeaves(BlockPos pos, RandomSource random, TreeInfo info) {
		for (Direction horizontal : Direction.Plane.HORIZONTAL) {
			info.addFoliage(pos.relative(horizontal));
		}
		if (random.nextInt(5) != 0) {
			Direction horizontal = Direction.Plane.HORIZONTAL.getRandomDirection(random);
			info.addFoliage(pos.relative(horizontal).relative(random.nextBoolean() ? horizontal.getClockWise() : horizontal.getCounterClockWise()));
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

	private static void encloseMediumLeaves(BlockPos pos, RandomSource random, TreeInfo info, int maxDecay) {
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				int distanceSquared = x * x + z * z;
				if (distanceSquared > 4) continue;
				if (distanceSquared > 1 && maxDecay > 0 && random.nextBoolean()) {
					maxDecay--;
					continue;
				}
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

	private enum MiddleFoliageType {
		COMPRESSED,
		CONE,
		INVERTED_CONE;

		private static final MiddleFoliageType[] VALUES = values();

		private static MiddleFoliageType random(RandomSource random) {
			return VALUES[random.nextInt(3)];
		}
	}
}
