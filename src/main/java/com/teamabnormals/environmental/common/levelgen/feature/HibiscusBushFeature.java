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
import net.minecraft.world.level.levelgen.Heightmap;
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
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

		if (pos.getY() > context.chunkGenerator().getSeaLevel() + 20 && random.nextBoolean())
			return false;

		for (int i = 0; i < 8; ++i) {
			int x = pos.getX() + random.nextInt(8) - random.nextInt(8);
			int z = pos.getZ() + random.nextInt(8) - random.nextInt(8);
			mutablepos.set(x, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);

			for (int j = 0; j < 2; ++j) {
				if (isNonHibiscusLeaves(level.getBlockState(mutablepos)))
					mutablepos.move(Direction.UP);
				else
					break;
			}

			if (canBushGrowAt(level, mutablepos)) {
				placeBush(level, mutablepos, random);
				placeGroundHibiscuses(level, mutablepos, random);

				return true;
			}
		}

		return false;
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

		if (maxY > 0 && random.nextInt(3) > 0) {
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
		for (int i = 0; i < 80; ++i) {
			Optional<Block> block = ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.HIBISCUSES).getRandomElement(random);
			if (block.isPresent()) {
				BlockState blockstate = block.get().defaultBlockState();
				blockpos.setWithOffset(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

				if (level.getBlockState(blockpos).isAir() && blockstate.canSurvive(level, blockpos))
					level.setBlock(blockpos, blockstate, 2);
			}
		}
	}

	private static void placeLeafCube(WorldGenLevel level, BlockPos pos, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(minX, minY, minZ), pos.offset(maxX, maxY, maxZ))) {
			if (isAirOrPlant(level.getBlockState(blockpos))) {
				level.setBlock(blockpos, EnvironmentalBlocks.HIBISCUS_LEAVES.get().defaultBlockState(), 19);
			}
		}
	}

	private static void placeWallHibiscuses(WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
		for (int x = -3; x <= 3; ++x) {
			for (int z = -3; z <= 3; ++z) {
				for (int y = -1; y <= 3; ++y) {
					mutablepos.setWithOffset(pos, x, y, z);
					if (random.nextInt(4) > 0 && level.getBlockState(mutablepos).isAir()) {
						List<Direction> validdirections = Lists.newArrayList();
						for (Direction direction : Direction.values()) {
							if (direction != Direction.UP && level.getBlockState(mutablepos.relative(direction)).is(EnvironmentalBlocks.HIBISCUS_LEAVES.get())) {
								validdirections.add(direction);
							}
						}

						if (!validdirections.isEmpty()) {
							Direction direction = validdirections.get(random.nextInt(validdirections.size())).getOpposite();
							ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
								level.setBlock(mutablepos, WallHibiscusBlock.setPropertiesForDirection(block.defaultBlockState(), direction, random), 2);
							});
						}
					}
				}
			}
		}
	}

	private static boolean canBushGrowAt(LevelAccessor level, BlockPos pos) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				for (int y = 1; y >= -1; --y) {
					mutablepos.setWithOffset(pos, x, y, z);
					if (!isAirOrPlant(level.getBlockState(mutablepos))) {
						return false;
					} else if (isGrassOrDirt(level, mutablepos.move(Direction.DOWN)) || isNonHibiscusLeaves(level.getBlockState(mutablepos))) {
						break;
					} else if (y == -1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean isNonHibiscusLeaves(BlockState state) {
		return state.is(BlockTags.LEAVES) && state.getBlock() != EnvironmentalBlocks.HIBISCUS_LEAVES.get();
	}

	private static boolean isAirOrPlant(BlockState state) {
		return state.isAir() || state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.PLANT;
	}
}