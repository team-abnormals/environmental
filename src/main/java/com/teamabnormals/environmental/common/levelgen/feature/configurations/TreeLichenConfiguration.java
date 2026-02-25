package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record TreeLichenConfiguration(float probability, boolean useCedarShadeNoise) implements FeatureConfiguration {
	public static final Codec<TreeLichenConfiguration> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
				Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(config -> config.probability),
				Codec.BOOL.fieldOf("use_cedar_shade_noise").forGetter(config -> config.useCedarShadeNoise)
		).apply(builder, TreeLichenConfiguration::new);
	});
}
