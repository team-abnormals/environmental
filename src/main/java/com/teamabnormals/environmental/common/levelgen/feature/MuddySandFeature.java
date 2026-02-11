package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MuddySandFeature extends Feature<NoneFeatureConfiguration> {

	public MuddySandFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		int originX = origin.getX();
		int originZ = origin.getZ();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		RandomSource random = context.random();

		for (int i = 0; i < 16; i++) {
			pos.setX(originX + i);
			for (int j = 0; j < 16; j++) {
				pos.setZ(originZ + j);
				int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 1;
				pos.setY(groundY);
				BlockState state = level.getBlockState(pos);
				if (cannotReplace(state) || !level.getBlockState(pos.above()).isAir()) continue;
				for (Direction waterAdjacentDirection : Direction.Plane.HORIZONTAL) {
					if (!level.getBlockState(pos.relative(waterAdjacentDirection)).getFluidState().is(FluidTags.WATER)) continue;
					int minY = groundY - 2;
					int maxY = groundY + 2;
					int radius = random.nextInt(3) + 1;
					int radiusSquared = radius * radius;
					int centerX = pos.getX();
					int centerZ = pos.getZ();
					for (int x = -radius; x <= radius; x++) {
						for (int z = -radius; z <= radius; z++) {
							float sqrDist = x * x + z * z - random.nextFloat() * 0.5F * radiusSquared;
							if (sqrDist > radiusSquared) continue;
							pos.setX(centerX + x);
							pos.setZ(centerZ + z);
							for (int y = minY; y <= maxY; y++) {
								pos.setY(y);
								if (cannotReplace(level.getBlockState(pos))) continue;
								level.setBlock(pos, EnvironmentalBlocks.MUDDY_SAND.get().defaultBlockState(), 2);
								BlockPos.MutableBlockPos sandPos = pos.mutable();
								int sandX = sandPos.getX();
								int sandZ = sandPos.getZ();
								for (Direction firstAdjacent : Direction.Plane.HORIZONTAL) {
									int directedX = sandX + firstAdjacent.getStepX();
									int directedZ = sandZ + firstAdjacent.getStepZ();
									sandPos.setX(directedX);
									sandPos.setZ(directedZ);
									if (cannotReplace(level.getBlockState(sandPos))) continue;
									level.setBlock(sandPos, Blocks.SAND.defaultBlockState(), 2);
									for (Direction secondAdjacent : Direction.Plane.HORIZONTAL) {
										if (random.nextBoolean()) continue;
										sandPos.setX(directedX + secondAdjacent.getStepX());
										sandPos.setZ(directedZ + secondAdjacent.getStepZ());
										if (cannotReplace(level.getBlockState(sandPos))) continue;
										level.setBlock(sandPos, Blocks.SAND.defaultBlockState(), 2);
									}
								}
							}
						}
					}
					break;
				}
			}
		}
		return true;
	}

	private static boolean cannotReplace(BlockState state) {
		return state.is(EnvironmentalBlocks.MUDDY_SAND) || (!state.is(BlockTags.SAND) && !state.is(BlockTags.DIRT));
	}

}
