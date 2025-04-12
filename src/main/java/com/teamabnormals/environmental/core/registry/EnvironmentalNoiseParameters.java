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
	public static final ResourceKey<NoiseParameters> CUP_LICHEN_NOISE = createKey("cup_lichen_noise");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_DENSITY = createKey("dwarf_spruce_density");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_HEIGHT = createKey("dwarf_spruce_height");
	public static final ResourceKey<NoiseParameters> WISTERIA_DENSITY = createKey("wisteria_density");
	public static final ResourceKey<NoiseParameters> WISTERIA_COLOR = createKey("wisteria_color");

	public static final LevelNoiseReceiver DWARF_SPRUCE_DENSITY_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_DENSITY);
	public static final LevelNoiseReceiver DWARF_SPRUCE_HEIGHT_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_HEIGHT);

	public static void bootstrap(BootstapContext<NoiseParameters> context) {
		context.register(PINE_BARRENS_STONE, new NormalNoise.NoiseParameters(-4, 1.0D));
		context.register(CUP_LICHEN_NOISE, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(DWARF_SPRUCE_DENSITY, new NormalNoise.NoiseParameters(-7, 1.0D));
		context.register(DWARF_SPRUCE_HEIGHT, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(WISTERIA_DENSITY, new NormalNoise.NoiseParameters(-8, 1.0D));
		context.register(WISTERIA_COLOR, new NormalNoise.NoiseParameters(-9, 1.0D));
	}

	public static ResourceKey<NoiseParameters> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, new ResourceLocation(Environmental.MOD_ID, name));
	}
}