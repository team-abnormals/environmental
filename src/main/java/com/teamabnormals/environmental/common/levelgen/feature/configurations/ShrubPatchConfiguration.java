package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ShrubPatchConfiguration(int extraRadius, boolean flowersOnSand, boolean flowersInLight, boolean noiseBasedFlowering) implements FeatureConfiguration {
	public static final Codec<ShrubPatchConfiguration> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(
				ExtraCodecs.NON_NEGATIVE_INT.fieldOf("extra_radius").orElse(1).forGetter(ShrubPatchConfiguration::extraRadius),
				Codec.BOOL.optionalFieldOf("flowers_on_sand", true).forGetter(ShrubPatchConfiguration::flowersOnSand),
				Codec.BOOL.optionalFieldOf("flowers_in_light", false).forGetter(ShrubPatchConfiguration::flowersInLight),
				Codec.BOOL.optionalFieldOf("noised_based_flowering", false).forGetter(ShrubPatchConfiguration::noiseBasedFlowering)
		).apply(builder, ShrubPatchConfiguration::new);
	});

	public boolean shouldFlowerInLight(WorldGenLevel level, RandomSource random, int x, int z) {
		if (!this.flowersInLight()) return false;
		int surfaceHeight = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
		int motionBlockingHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		if (surfaceHeight == motionBlockingHeight) return random.nextInt(7) != 0;
		return random.nextInt(10) == 0;
	}
}
