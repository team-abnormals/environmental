package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class ShortBambooFeature extends Feature<ProbabilityFeatureConfiguration> {
	private static final BlockState BAMBOO_TRUNK = Blocks.BAMBOO.defaultBlockState().setValue(BambooBlock.AGE, 1).setValue(BambooBlock.LEAVES, BambooLeaves.NONE).setValue(BambooBlock.STAGE, 0);
	private static final BlockState BAMBOO_FINAL_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE).setValue(BambooBlock.STAGE, 1);
	private static final BlockState BAMBOO_TOP_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState BAMBOO_TOP_SMALL = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.SMALL);

	public ShortBambooFeature(Codec<ProbabilityFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> config) {
		int count = 0;
		BlockPos origin = config.origin();
		WorldGenLevel level = config.level();
		RandomSource random = config.random();
		ProbabilityFeatureConfiguration probabilityfeatureconfiguration = config.config();
		BlockPos.MutableBlockPos bambooPos = origin.mutable();
		BlockPos.MutableBlockPos podzolPos = origin.mutable();
		if (level.isEmptyBlock(bambooPos)) {
			if (Blocks.BAMBOO.defaultBlockState().canSurvive(level, bambooPos)) {
				int bambooHeight = random.nextInt(7) + 5;
				if (random.nextFloat() < probabilityfeatureconfiguration.probability) {
					int podzolRadius = random.nextInt(4) + 1;

					for (int xPos = origin.getX() - podzolRadius; xPos <= origin.getX() + podzolRadius; ++xPos) {
						for (int zPos = origin.getZ() - podzolRadius; zPos <= origin.getZ() + podzolRadius; ++zPos) {
							int xDist = xPos - origin.getX();
							int zDist = zPos - origin.getZ();
							if (xDist * xDist + zDist * zDist <= podzolRadius * podzolRadius) {
								podzolPos.set(xPos, level.getHeight(Heightmap.Types.WORLD_SURFACE, xPos, zPos) - 1, zPos);
								if (isDirt(level.getBlockState(podzolPos))) {
									level.setBlock(podzolPos, Blocks.PODZOL.defaultBlockState(), 2);
									if (level.getBlockState(podzolPos.above()).isAir() && random.nextBoolean()) {
										level.setBlock(podzolPos.above(), Blocks.GRASS.defaultBlockState(), 2);
									}
								}
							}
						}
					}
				}

				for (int i = 0; i < bambooHeight && level.isEmptyBlock(bambooPos); ++i) {
					level.setBlock(bambooPos, BAMBOO_TRUNK, 2);
					bambooPos.move(Direction.UP, 1);
				}

				if (bambooPos.getY() - origin.getY() >= 3) {
					level.setBlock(bambooPos, BAMBOO_FINAL_LARGE, 2);
					level.setBlock(bambooPos.move(Direction.DOWN, 1), BAMBOO_TOP_LARGE, 2);
					level.setBlock(bambooPos.move(Direction.DOWN, 1), BAMBOO_TOP_SMALL, 2);
				}
			}

			++count;
		}

		return count > 0;
	}
}