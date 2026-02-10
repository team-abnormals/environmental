package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ShrubPatchConfiguration(int extraRadius, boolean conditionallyFlowers) implements FeatureConfiguration {
	public static final Codec<ShrubPatchConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(
				ExtraCodecs.NON_NEGATIVE_INT.fieldOf("extra_radius").orElse(1).forGetter(ShrubPatchConfiguration::extraRadius),
				Codec.BOOL.optionalFieldOf("conditionally_flowers", true).forGetter(ShrubPatchConfiguration::conditionallyFlowers)
		).apply(builder, ShrubPatchConfiguration::new);
	});
}
