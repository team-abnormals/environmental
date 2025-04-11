package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NoiseThresholdPlacedFeature {
	public static final Codec<NoiseThresholdPlacedFeature> CODEC = RecordCodecBuilder.create(
			p_191187_ -> p_191187_.group(
							PlacedFeature.CODEC.fieldOf("feature").forGetter(placedFeature -> placedFeature.feature),
							Codec.floatRange(-1.0F, 1.0F).fieldOf("threshold").forGetter(placedFeature -> placedFeature.threshold))
					.apply(p_191187_, NoiseThresholdPlacedFeature::new)
	);
	public final Holder<PlacedFeature> feature;
	public final float threshold;

	public NoiseThresholdPlacedFeature(Holder<PlacedFeature> feature, float threshold) {
		this.feature = feature;
		this.threshold = threshold;
	}

	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos pos) {
		return this.feature.value().place(level, chunkGenerator, random, pos);
	}
}