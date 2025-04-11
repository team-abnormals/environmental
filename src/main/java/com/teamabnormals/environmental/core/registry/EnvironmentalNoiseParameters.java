package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.common.world.storage.receiver.LevelNoiseReceiver;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom.Algorithm;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class EnvironmentalNoiseParameters {
	public static final ResourceKey<NoiseParameters> PINE_BARRENS_STONE = createKey("pine_barrens_stone");
	public static final ResourceKey<NoiseParameters> NOISE_CUP_LICHEN = createKey("noise_cup_lichen");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_DENSITY = createKey("dwarf_spruce_density");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_HEIGHT_NOISE = createKey("dwarf_spruce_height_noise");

	public static final LevelNoiseReceiver DWARF_SPRUCE_DENSITY_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, EnvironmentalNoiseParameters.DWARF_SPRUCE_DENSITY);
	public static final LevelNoiseReceiver DWARF_SPRUCE_HEIGHT_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, EnvironmentalNoiseParameters.DWARF_SPRUCE_HEIGHT_NOISE);

	public static void bootstrap(BootstapContext<NoiseParameters> context) {
		context.register(PINE_BARRENS_STONE, new NormalNoise.NoiseParameters(-4, 1.0D));
		context.register(NOISE_CUP_LICHEN, new NormalNoise.NoiseParameters(-7, 1.0D));
		context.register(DWARF_SPRUCE_DENSITY, new NormalNoise.NoiseParameters(-7, 1.0D));
		context.register(DWARF_SPRUCE_HEIGHT_NOISE, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
	}

	public static ResourceKey<NoiseParameters> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, new ResourceLocation(Environmental.MOD_ID, name));
	}
}