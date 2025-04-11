package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.NoiseSelectorFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class NoiseSelectorFeature extends Feature<NoiseSelectorFeatureConfiguration> {

	public NoiseSelectorFeature(Codec<NoiseSelectorFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoiseSelectorFeatureConfiguration> context) {
		NoiseSelectorFeatureConfiguration config = context.config();
		RandomSource random = context.random();
		WorldGenLevel level = context.level();
		ChunkGenerator chunkgenerator = context.chunkGenerator();
		BlockPos origin = context.origin();

		double value = config.getNoise(level).getValue(origin.getX(), origin.getY(), origin.getZ());
		for (NoiseThresholdPlacedFeature feature : config.features) {
			if (value < feature.threshold - config.blending || (value < feature.threshold + config.blending && random.nextFloat() < 1.0F - (value - feature.threshold + config.blending) / (2 * config.blending))) {
				return feature.place(level, chunkgenerator, random, origin);
			}
		}

		return config.defaultFeature.value().place(level, chunkgenerator, random, origin);
	}
}