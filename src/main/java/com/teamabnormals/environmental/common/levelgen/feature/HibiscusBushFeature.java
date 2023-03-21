package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.WallHibiscusBlock;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
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

		if (random.nextBoolean() && pos.getY() > context.chunkGenerator().getSeaLevel() + 20)
			return false;

		int bushes = 0;

		for (int i = 0; i < 128 && bushes < 6; ++i) {
			blockpos.setWithOffset(pos, random.nextInt(16) - random.nextInt(16), random.nextInt(4) - random.nextInt(4), random.nextInt(16) - random.nextInt(16));
			if (canBushGrowAt(level, blockpos)) {
				placeBush(level, blockpos, random);
				placeGroundHibiscuses(level, blockpos, random);
				bushes++;
			}
		}

		return bushes > 0;
	}

	private static void placeBush(WorldGenLevel level, BlockPos pos, RandomSource random) {
		int maxX = random.nextInt(2) + 1;
		int maxY = random.nextInt(3);
		int maxZ = random.nextInt(2) + 1;

		if ((maxX == 2 || maxZ == 2) && maxY == 0)
			maxY = 1;

		int minX = maxX == 2 ? -1 : -random.nextInt(2);
		int minY = -1;
		int minZ = maxZ == 2 ? -1 : -random.nextInt(2);

		maxX += minX;
		maxZ += minZ;

		placeLeafCube(level, pos, minX, minY, minZ, maxX, maxY, maxZ);

		if (maxY > 0 && random.nextBoolean()) {
			int offsetX = random.nextBoolean() ? -1 : 1;
			int offsetY = random.nextBoolean() ? -1 : 1;

			minX += offsetX;
			maxX += offsetX;
			minZ += offsetY;
			maxZ += offsetY;
			maxY -= 1;

			if (random.nextBoolean() && maxX - minX > 1) {
				if (offsetX > 0)
					minX += 1;
				else
					maxX -= 1;
			}
			if (random.nextBoolean() && maxZ - minZ > 1) {
				if (offsetY > 0)
					minZ += 1;
				else
					maxZ -= 1;
			}

			placeLeafCube(level, pos, minX, minY, minZ, maxX, maxY, maxZ);
		}

		placeWallHibiscuses(level, pos, random);
	}

	private static void placeGroundHibiscuses(WorldGenLevel level, BlockPos pos, RandomSource random) {
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

	private static void placeLeafCube(WorldGenLevel level, BlockPos pos, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(minX, minY, minZ), pos.offset(maxX, maxY, maxZ))) {
			if (isAirOrReplaceablePlant(level, blockpos)) {
				level.setBlock(blockpos, EnvironmentalBlocks.HIBISCUS_LEAVES.get().defaultBlockState(), 4);
			}
		}
	}

	private static void placeWallHibiscuses(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();
		for (int x = -3; x <= 3; ++x) {
			for (int y = -1; y <= 3; ++y) {
				for (int z = -3; z <= 3; ++z) {
					blockpos.setWithOffset(pos, x, y, z);
					if (random.nextBoolean() && level.getBlockState(blockpos).isAir()) {
						List<Direction> validdirections = Lists.newArrayList();
						for (Direction direction : Direction.values()) {
							if (direction != Direction.UP && level.getBlockState(blockpos.relative(direction)).is(EnvironmentalBlocks.HIBISCUS_LEAVES.get())) {
								validdirections.add(direction);
							}
						}

						if (!validdirections.isEmpty()) {
							Direction direction = validdirections.get(random.nextInt(validdirections.size()));
							ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
								level.setBlock(blockpos, block.defaultBlockState().setValue(WallHibiscusBlock.FACING, direction), 2);
							});
						}
					}
				}
			}
		}
	}

	private static boolean canBushGrowAt(LevelAccessor level, BlockPos pos) {
		int i = 0;
		int j = 0;
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))) {
			if (!isAirOrReplaceablePlant(level, blockpos) && ++i > 1)
				return false;
			else if (!isGrassOrDirt(level, blockpos.below()) && !hasLeavesAndDirtBelow(level, blockpos, 1) && !hasLeavesAndDirtBelow(level, blockpos, 2) && ++j > 2)
				return false;
		}
		return true;
	}

	private static boolean hasLeavesAndDirtBelow(LevelAccessor level, BlockPos pos, int depth) {
		for (int i = 1; i <= depth; ++i) {
			BlockState state = level.getBlockState(pos.below(i));
			if (!state.is(BlockTags.LEAVES) || state.getBlock() == EnvironmentalBlocks.HIBISCUS_LEAVES.get())
				return false;
		}
		return isGrassOrDirt(level, pos.below(depth + 1));
	}

	private static boolean isAirOrReplaceablePlant(LevelAccessor level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.isAir() || state.getMaterial() == Material.REPLACEABLE_PLANT || state.is(EnvironmentalBlockTags.HIBISCUSES);
	}
}