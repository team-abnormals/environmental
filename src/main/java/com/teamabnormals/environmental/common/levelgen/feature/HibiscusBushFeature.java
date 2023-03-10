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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class HibiscusBushFeature extends Feature<NoneFeatureConfiguration> {

	public HibiscusBushFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		if (!isGrassOrDirt(level, pos.below()))
			return false;

		int minX = pos.getX();
		int maxX = pos.getX();
		int minY = pos.getY();
		int maxY = pos.getY();
		int minZ = pos.getZ();
		int maxZ = pos.getZ();

		boolean hasleaves = false;
		int size = 2 + random.nextInt(3);
		BlockPos oldpos = pos;

		for (int i = 0; i < size; ++i) {
			if (canGrowLeavesAt(level, pos)) {
				int lengthX = i == 0 ? 3 : random.nextInt(2) + 2;
				int lengthY = random.nextInt(2) + 2;
				int lengthZ = i == 0 ? 3 : random.nextInt(2) + 2;

				int offsetX = -lengthX / 2;
				int offsetY = -lengthY / 2;
				int offsetZ = -lengthZ / 2;

				offsetX += lengthX % 2 == 0 ? random.nextInt(2) : 0;
				offsetZ += lengthZ % 2 == 0 ? random.nextInt(2) : 0;

				for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(offsetX, offsetY, offsetZ), pos.offset(lengthX - 1 + offsetX, lengthY - 1 + offsetY, lengthZ - 1 + offsetZ))) {
					if (isReplaceable(level, blockpos)) {
						level.setBlock(blockpos, EnvironmentalBlocks.HIBISCUS_LEAVES.get().defaultBlockState(), 4);
						hasleaves = true;
					}
				}

				minX = Math.min(minX, pos.getX());
				maxX = Math.max(maxX, pos.getX());
				minY = Math.min(minY, pos.getY());
				maxY = Math.max(maxY, pos.getY());
				minZ = Math.min(minZ, pos.getZ());
				maxZ = Math.max(maxZ, pos.getZ());

				oldpos = pos;
			}

			pos = oldpos.offset(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
		}

		if (hasleaves) {
			BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

			minX -= 3;
			maxX += 3;
			minY -= 3;
			maxY += 3;
			minZ -= 3;
			;
			maxZ += 3;

			for (int x = minX; x <= maxX; ++x) {
				for (int y = minY; y <= maxY; ++y) {
					for (int z = minZ; z <= maxZ; ++z) {
						if (random.nextInt(5) < 2) {
							blockpos.set(x, y, z);
							plantRandomHibiscus(level, blockpos, level.getBlockState(blockpos), random);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	private static void plantRandomHibiscus(LevelAccessor level, BlockPos pos, BlockState state, RandomSource random) {
		if (state.is(BlockTags.DIRT) && level.getBlockState(pos.above()).isAir()) {
			ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
				level.setBlock(pos.above(), block.defaultBlockState(), 2);
			});
		} else if (state.is(EnvironmentalBlocks.HIBISCUS_LEAVES.get())) {
			List<Direction> validdirections = Lists.newArrayList();
			for (Direction direction : Direction.values()) {
				if (direction != Direction.DOWN && level.getBlockState(pos.relative(direction)).isAir()) {
					validdirections.add(direction);
				}
			}

			if (!validdirections.isEmpty()) {
				Direction direction = validdirections.get(random.nextInt(validdirections.size()));
				BlockPos blockpos1 = pos.relative(direction);

				if (direction == Direction.UP) {
					ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
						level.setBlock(blockpos1, block.defaultBlockState(), 2);
					});
				} else {
					ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
						level.setBlock(blockpos1, block.defaultBlockState().setValue(WallHibiscusBlock.FACING, direction.getOpposite()), 2);
					});
				}
			}
		}
	}

	private static boolean canGrowLeavesAt(LevelAccessor level, BlockPos pos) {
		return isReplaceable(level, pos) || level.getBlockState(pos).is(EnvironmentalBlocks.HIBISCUS_LEAVES.get());
	}

	private static boolean isReplaceable(LevelAccessor level, BlockPos pos) {
		return level.isStateAtPosition(pos, (state) -> {
			Material material = state.getMaterial();
			return material.isReplaceable() || material == Material.PLANT;
		});
	}
}
