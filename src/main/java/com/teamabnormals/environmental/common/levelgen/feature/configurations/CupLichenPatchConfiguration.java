package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CupLichenPatchConfiguration(int tries, int xzSpread, int ySpread) implements FeatureConfiguration {
	public static final Codec<CupLichenPatchConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(CupLichenPatchConfiguration::tries), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(CupLichenPatchConfiguration::xzSpread), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(CupLichenPatchConfiguration::ySpread)).apply(builder, CupLichenPatchConfiguration::new);
	});
}