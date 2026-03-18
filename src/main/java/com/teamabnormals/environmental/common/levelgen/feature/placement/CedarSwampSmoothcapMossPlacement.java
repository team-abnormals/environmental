package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.stream.Stream;

public final class CedarSwampSmoothcapMossPlacement extends PlacementModifier {
	public static final CedarSwampSmoothcapMossPlacement INSTANCE = new CedarSwampSmoothcapMossPlacement();
	public static final MapCodec<CedarSwampSmoothcapMossPlacement> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		int originX = pos.getX();
		int originZ = pos.getZ();
		ServerLevel level = context.getLevel().getLevel();
		NormalNoise occurrenceNoise = EnvironmentalNoiseParameters.SMOOTHCAP_MOSS_OCCURRENCE_RECEIVER.get(level);
		NormalNoise frequencyNoise = EnvironmentalNoiseParameters.SMOOTHCAP_MOSS_FREQUENCY_RECEIVER.get(level);
		double shade = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(level).getValue(0.25D * (originX + 8.0D), 0.0D, 0.25D * (originZ + 8.0D));
		if (shade < 0.333D) shade = 0.0D;
		double shadedOccurrenceTarget = 0.9D - shade * 0.4D;
		double baseShadedOccurrence = 0.02D + 0.02D * shade;
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int x = originX + i;
				int z = originZ + j;
				double occurrence = Math.min(Math.abs(occurrenceNoise.getValue(x * 0.25D, 0.0D, z * 0.25D)) / 0.75D, 1.0D);
				if (occurrence < 0.1D) occurrence = baseShadedOccurrence;
				else {
					double frequency = frequencyNoise.getValue(x * 0.5D, 0.0D, z * 0.5D);
					if (frequency < 0.0D) frequency = 0.2D;
					else if (frequency > 0.0D) frequency = 0.2D + frequency * 0.8D;
					occurrence = baseShadedOccurrence + 0.23D * Math.min((occurrence - 0.1D) / shadedOccurrenceTarget, 1.0D) * frequency;
				}
				if (random.nextDouble() > occurrence) continue;
				builder.add(new BlockPos(x, 0, z));
			}
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_SMOOTHCAP_MOSS.get();
	}
}
