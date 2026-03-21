package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class WeirdnessBandPlacement extends PlacementModifier {
	public static final MapCodec<WeirdnessBandPlacement> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.INT.optionalFieldOf("count", 2).forGetter(placement -> placement.count),
					Codec.INT.optionalFieldOf("max_height", 65).forGetter(placement -> placement.maxHeight),
					Codec.DOUBLE.fieldOf("min_weirdness").forGetter(placement -> placement.minWeirdness),
					Codec.DOUBLE.fieldOf("max_weirdness").forGetter(placement -> placement.maxWeirdness),
					Codec.floatRange(0.0F, 1.0F).optionalFieldOf("probability_below_min", 0.0F).forGetter(placement -> placement.probabilityBelowMin),
					Codec.floatRange(0.0F, 1.0F).optionalFieldOf("probability_above_max", 0.0F).forGetter(placement -> placement.probabilityAboveMax),
					Codec.floatRange(0.0F, 1.0F).fieldOf("probability_at_min").forGetter(placement -> placement.probabilityAtMin),
					Codec.floatRange(0.0F, 1.0F).fieldOf("probability_at_max").forGetter(placement -> placement.probabilityAtMax)
			).apply(instance, WeirdnessBandPlacement::new)
	);
	private final int count;
	private final int maxHeight;
	private final double minWeirdness;
	private final double maxWeirdness;
	private final float probabilityBelowMin;
	private final float probabilityAboveMax;
	private final float probabilityAtMin;
	private final float probabilityAtMax;

	public WeirdnessBandPlacement(int count, int maxHeight, double minWeirdness, double maxWeirdness, float probabilityBelowMin, float probabilityAboveMax, float probabilityAtMin, float probabilityAtMax) {
		if (minWeirdness > maxWeirdness)
			throw new IllegalArgumentException("minWeirdness cannot be greater than maxWeirdness");
		this.count = count;
		this.maxHeight = maxHeight;
		this.minWeirdness = minWeirdness;
		this.maxWeirdness = maxWeirdness;
		this.probabilityBelowMin = probabilityBelowMin;
		this.probabilityAboveMax = probabilityAboveMax;
		this.probabilityAtMin = probabilityAtMin;
		this.probabilityAtMax = probabilityAtMax;
	}

	public static WeirdnessBandPlacement riverConstant(int count, double minWeirdness, double maxWeirdness, float probability) {
		return new WeirdnessBandPlacement(count, 65, minWeirdness, maxWeirdness, 0.0F, 0.0F, probability, probability);
	}

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		int originX = pos.getX();
		int originZ = pos.getZ();
		WorldGenLevel level = context.getLevel();
		DensityFunction weirdnessFunction = level.getLevel().getChunkSource().randomState().sampler().weirdness();
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < this.count; i++) {
			int x = originX + random.nextInt(16);
			int z = originZ + random.nextInt(16);
			int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
			if (height > this.maxHeight) continue;
			double weirdness = Math.abs(weirdnessFunction.compute(new DensityFunction.SinglePointContext(x, height, z)));
			float probability = this.getProbability(weirdness);
			if (probability <= 0.0F) continue;
			if (random.nextFloat() >= probability) continue;
			builder.add(new BlockPos(x, height, z));
		}
		return builder.build();
	}

	private float getProbability(double weirdness) {
		if (weirdness < this.minWeirdness) return this.probabilityBelowMin;
		if (weirdness > this.maxWeirdness) return this.probabilityAboveMax;
		if (this.probabilityAtMin == this.probabilityAtMax) return this.probabilityAtMin;
		float progress = (float) ((weirdness - this.minWeirdness) / (this.maxWeirdness - this.minWeirdness));
		return Mth.lerp(progress, this.probabilityAtMin, this.probabilityAtMax);
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.WEIRDNESS_BAND.get();
	}
}