package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom.Algorithm;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NoiseDensityPlacement extends PlacementModifier {
	public static final Codec<NoiseDensityPlacement> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			NoiseParameters.CODEC.fieldOf("noise").forGetter((placement) -> placement.noiseParameters),
			Codec.DOUBLE.fieldOf("density").forGetter((placement) -> placement.density),
			Codec.DOUBLE.fieldOf("noise_offset").orElse(0.0D).forGetter((placement) -> placement.noiseOffset))
			.apply(instance, NoiseDensityPlacement::new));
	private final Holder<NoiseParameters> noiseParameters;
	private final double density;
	private final double noiseOffset;
	private volatile boolean initialized;
	private NormalNoise noise;

	public NoiseDensityPlacement(Holder<NormalNoise.NoiseParameters> noiseParameters, double density, double noiseOffset) {
		this.noiseParameters = noiseParameters;
		this.density = density;
		this.noiseOffset = noiseOffset;
	}

	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		if (!this.initialized) {
			synchronized(this) {
				if (!this.initialized) {
					this.noise = NormalNoise.create(Algorithm.LEGACY.newInstance(context.getLevel().getSeed()).forkPositional().fromHashOf((this.noiseParameters.unwrapKey().orElseThrow()).location()), this.noiseParameters.value());
					this.initialized = true;
				}
			}
		}

		double value = (this.noise.getValue(pos.getX(), 0.0F, pos.getZ()) + this.noiseOffset) * this.density;
		int count = (int) value + (random.nextFloat() < value - (int) value ? 1 : 0);

		return IntStream.range(0, count).mapToObj((i) -> pos);
	}

	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.NOISE_DENSITY.get();
	}
}