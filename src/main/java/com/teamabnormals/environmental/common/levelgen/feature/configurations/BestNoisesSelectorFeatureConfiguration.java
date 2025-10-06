package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

import java.util.List;
import java.util.stream.Stream;

public class BestNoisesSelectorFeatureConfiguration implements FeatureConfiguration {
	public static final Codec<BestNoisesSelectorFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			builder -> builder.apply3(
					BestNoisesSelectorFeatureConfiguration::new,
					NoiseParameters.CODEC.fieldOf("noise").forGetter(config -> config.parameters),
					Codec.floatRange(0.0F, 2.0F).fieldOf("blending").forGetter(config -> config.blending),
					PlacedFeature.CODEC.listOf().fieldOf("features").forGetter(config -> config.features)
			)
	);
	public final Holder<NoiseParameters> parameters;
	public final float blending;
	public final List<Holder<PlacedFeature>> features;
	private NormalNoise[] noises;
	private volatile boolean initialized;

	public BestNoisesSelectorFeatureConfiguration(Holder<NoiseParameters> parameters, float blending, List<Holder<PlacedFeature>> features) {
		this.parameters = parameters;
		this.blending = blending;
		this.features = features;
		this.noises = new NormalNoise[features.size()];
	}

	public NormalNoise[] getNoises(WorldGenLevel level) {
		if (!this.initialized) {
			synchronized (this) {
				if (!this.initialized) {
					RandomSource random = WorldgenRandom.Algorithm.LEGACY.newInstance((long) this.parameters.unwrapKey().orElseThrow().location().hashCode() ^ level.getSeed());
					int size = this.features.size();

					for (int i = 0; i < size; i++) {
						this.noises[i] = NormalNoise.create(new LegacyRandomSource(random.nextLong()), this.parameters.value());
					}

					this.initialized = true;
				}
			}
		}
		return this.noises;
	}

	@Override
	public Stream<ConfiguredFeature<?, ?>> getFeatures() {
		return this.features.stream().flatMap(holder -> holder.value().getFeatures());
	}
}