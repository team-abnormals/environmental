package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.blueprint.common.world.storage.receiver.LevelNoiseReceiver;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.WorldgenRandom.Algorithm;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class EnvironmentalNoiseParameters {
	public static final ResourceKey<NoiseParameters> PINE_BARRENS_STONE = create("pine_barrens_stone");
	public static final ResourceKey<NoiseParameters> CUP_LICHEN_NOISE = create("cup_lichen_noise");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_DENSITY = create("dwarf_spruce_density");
	public static final ResourceKey<NoiseParameters> DWARF_SPRUCE_HEIGHT = create("dwarf_spruce_height");
	public static final ResourceKey<NoiseParameters> CEDAR_RIVER_MUD = create("cedar_river_mud");
	public static final ResourceKey<NoiseParameters> CEDAR_RIVER_WATERLILY_DENSITY = create("cedar_river_waterlily_density");
	public static final ResourceKey<NoiseParameters> SHRUB_DENSITY = create("shrub_density");
	public static final ResourceKey<NoiseParameters> WISTERIA_DENSITY = create("wisteria_density");
	public static final ResourceKey<NoiseParameters> WISTERIA_COLOR = create("wisteria_color");

	public static final LevelNoiseReceiver DWARF_SPRUCE_DENSITY_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_DENSITY);
	public static final LevelNoiseReceiver DWARF_SPRUCE_HEIGHT_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_HEIGHT);

	public static void bootstrap(BootstrapContext<NoiseParameters> context) {
		context.register(PINE_BARRENS_STONE, new NormalNoise.NoiseParameters(-4, 1.0D));
		context.register(CUP_LICHEN_NOISE, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(DWARF_SPRUCE_DENSITY, new NormalNoise.NoiseParameters(-7, 1.0D));
		context.register(DWARF_SPRUCE_HEIGHT, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(CEDAR_RIVER_MUD, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D, 1.0D));
		context.register(CEDAR_RIVER_WATERLILY_DENSITY, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D));
		context.register(SHRUB_DENSITY, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D));
		context.register(WISTERIA_DENSITY, new NormalNoise.NoiseParameters(-8, 1.0D));
		context.register(WISTERIA_COLOR, new NormalNoise.NoiseParameters(-9, 1.0D));
	}

	public static ResourceKey<NoiseParameters> create(String name) {
		return ResourceKey.create(Registries.NOISE, Environmental.location(name));
	}
}