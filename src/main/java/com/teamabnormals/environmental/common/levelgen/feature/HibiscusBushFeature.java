package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.WallHibiscusBlock;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public class HibiscusBushFeature extends Feature<NoneFeatureConfiguration> {

	public HibiscusBushFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

		int bushes = 0;

		for (int i = 0; i < 128 && bushes < 10; ++i) {
			blockpos.setWithOffset(pos, random.nextInt(10) - random.nextInt(10), random.nextInt(4) - random.nextInt(4), random.nextInt(10) - random.nextInt(10));
			if (canBushGrowAt(level, blockpos)) {
				placeBush(level, blockpos, random);
				plantGroundHibiscuses(level, blockpos, random);
				bushes++;
			}
		}

		return bushes > 0;
	}

	private static boolean placeBush(WorldGenLevel level, BlockPos pos, RandomSource random) {
		double radius = random.nextDouble() + 1.0D;

		placeLeafBlob(level, pos.above(), random, radius);
		if (radius > 1.5D && random.nextInt(3) > 0)
			placeLeafBlob(level, pos.above(2), random, radius * 0.75D);

		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

		for (int x = -3; x <= 3; ++x) {
			for (int y = -1; y <= 6; ++y) {
				for (int z = -3; z <= 3; ++z) {
					if (random.nextBoolean()) {
						blockpos.setWithOffset(pos, x, y, z);
						plantWallHibiscus(level, blockpos, level.getBlockState(blockpos), random);
					}
				}
			}
		}

		return true;
	}

	private static void plantGroundHibiscuses(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
		for (int i = 0; i < 64; ++i) {
			Optional<Block> block = ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.HIBISCUSES).getRandomElement(random);
			if (block.isPresent()) {
				BlockState blockstate = block.get().defaultBlockState();
				blockpos.setWithOffset(pos, random.nextInt(7) - random.nextInt(7), random.nextInt(3) - random.nextInt(3), random.nextInt(7) - random.nextInt(7));

				if (level.getBlockState(blockpos).isAir() && blockstate.canSurvive(level, blockpos))
					level.setBlock(blockpos, blockstate, 2);
			}
		}
	}

	private static void placeLeafBlob(WorldGenLevel level, BlockPos pos, RandomSource random, double radius) {
		double offsetX = random.nextDouble();
		double offsetY = -random.nextDouble() * 0.5D;
		double offsetZ = random.nextDouble();

		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2))) {
			if (blockpos.distToCenterSqr(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ) < radius * radius && isAirOrReplaceablePlant(level, blockpos)) {
				level.setBlock(blockpos, EnvironmentalBlocks.HIBISCUS_LEAVES.get().defaultBlockState(), 4);
			}
		}
	}

	private static void plantWallHibiscus(WorldGenLevel level, BlockPos pos, BlockState state, RandomSource random) {
		if (state.isAir()) {
			List<Direction> validdirections = Lists.newArrayList();
			for (Direction direction : Direction.values()) {
				if (direction != Direction.UP && level.getBlockState(pos.relative(direction)).is(EnvironmentalBlocks.HIBISCUS_LEAVES.get())) {
					validdirections.add(direction);
				}
			}

			if (!validdirections.isEmpty()) {
				Direction direction = validdirections.get(random.nextInt(validdirections.size()));
				ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
					level.setBlock(pos, block.defaultBlockState().setValue(WallHibiscusBlock.FACING, direction), 2);
				});
			}
		}
	}

	private static boolean canBushGrowAt(LevelAccessor level, BlockPos pos) {
		int i = 0;
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))) {
			if (!isAirOrReplaceablePlant(level, blockpos))
				return false;
			else if (!isGrassOrDirt(level, blockpos.below()) && ++i > 2)
				return false;
		}
		return true;
	}

	private static boolean isAirOrReplaceablePlant(LevelAccessor level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.isAir() || state.getMaterial() == Material.REPLACEABLE_PLANT;
	}
}