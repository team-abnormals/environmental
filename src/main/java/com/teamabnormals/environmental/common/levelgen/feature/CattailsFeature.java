package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CattailsFeature extends Feature<NoneFeatureConfiguration> {

	public CattailsFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel world = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();

		boolean place = false;
		for (int i = 0; i < 512; ++i) {
			BlockPos placePos = pos.offset(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
			if (i == 0 && !world.isWaterAt(placePos)) {
				return false;
			}
			if ((world.isWaterAt(placePos) || world.isEmptyBlock(placePos)) && world.isEmptyBlock(placePos.above()) && EnvironmentalBlocks.CATTAIL.get().defaultBlockState().canSurvive(world, placePos)) {
				((CattailBlock) EnvironmentalBlocks.CATTAIL.get()).placeAt(world, placePos, 2);
				place = true;
			}
		}
		return place;
	}
}