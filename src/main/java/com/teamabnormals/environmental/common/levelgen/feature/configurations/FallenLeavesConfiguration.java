package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenLeavesConfiguration(BlockStateProvider provider, int radius, int ySpread) implements FeatureConfiguration {
	public static final Codec<FallenLeavesConfiguration> CODEC = RecordCodecBuilder.create(codec -> codec.group(
			BlockStateProvider.CODEC.fieldOf("provider").forGetter(FallenLeavesConfiguration::provider),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius").orElse(3).forGetter(FallenLeavesConfiguration::radius),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(FallenLeavesConfiguration::ySpread)
	).apply(codec, FallenLeavesConfiguration::new));
}