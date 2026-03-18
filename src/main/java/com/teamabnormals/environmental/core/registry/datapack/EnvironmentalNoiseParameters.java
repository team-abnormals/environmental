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
	public static final ResourceKey<NoiseParameters> CEDAR_SWAMP_SHADE = create("cedar_swamp_shade");
	public static final ResourceKey<NoiseParameters> CEDAR_SWAMP_MUD = create("cedar_swamp_mud");
	public static final ResourceKey<NoiseParameters> SHRUB_DENSITY = create("shrub_density");
	public static final ResourceKey<NoiseParameters> SHRUB_FLOWER_POWER = create("shrub_flower_power");
	public static final ResourceKey<NoiseParameters> SMOOTHCAP_MOSS_OCCURRENCE = create("smoothcap_moss_occurrence");
	public static final ResourceKey<NoiseParameters> SMOOTHCAP_MOSS_FREQUENCY = create("smoothcap_moss_frequency");
	public static final ResourceKey<NoiseParameters> SMOOTHCAP_MOSS_COVER = create("smoothcap_moss_cover");
	public static final ResourceKey<NoiseParameters> SMOOTHCAP_MOSS_RADIUS = create("smoothcap_moss_radius");
	public static final ResourceKey<NoiseParameters> WISTERIA_DENSITY = create("wisteria_density");
	public static final ResourceKey<NoiseParameters> WISTERIA_COLOR = create("wisteria_color");

	public static final LevelNoiseReceiver DWARF_SPRUCE_DENSITY_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_DENSITY);
	public static final LevelNoiseReceiver DWARF_SPRUCE_HEIGHT_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, DWARF_SPRUCE_HEIGHT);
	public static final LevelNoiseReceiver CEDAR_SWAMP_SHADE_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, CEDAR_SWAMP_SHADE);
	public static final LevelNoiseReceiver SHRUB_FLOWER_POWER_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, SHRUB_FLOWER_POWER);
	public static final LevelNoiseReceiver SMOOTHCAP_MOSS_OCCURRENCE_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, SMOOTHCAP_MOSS_OCCURRENCE);
	public static final LevelNoiseReceiver SMOOTHCAP_MOSS_FREQUENCY_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, SMOOTHCAP_MOSS_FREQUENCY);
	public static final LevelNoiseReceiver SMOOTHCAP_MOSS_COVER_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, SMOOTHCAP_MOSS_COVER);
	public static final LevelNoiseReceiver SMOOTHCAP_MOSS_RADIUS_RECEIVER = new LevelNoiseReceiver(Algorithm.LEGACY, SMOOTHCAP_MOSS_RADIUS);

	public static void bootstrap(BootstrapContext<NoiseParameters> context) {
		context.register(PINE_BARRENS_STONE, new NormalNoise.NoiseParameters(-4, 1.0D));
		context.register(CUP_LICHEN_NOISE, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(DWARF_SPRUCE_DENSITY, new NormalNoise.NoiseParameters(-7, 1.0D));
		context.register(DWARF_SPRUCE_HEIGHT, new NormalNoise.NoiseParameters(-8, 1.0D, 1.0D));
		context.register(CEDAR_RIVER_MUD, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D, 1.0D));
		context.register(CEDAR_RIVER_WATERLILY_DENSITY, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D));
		context.register(CEDAR_SWAMP_SHADE, new  NormalNoise.NoiseParameters(-6, 1.0D, 1.5D, 2.0D, 2.0D, 2.0D, 1.0D));
		context.register(CEDAR_SWAMP_MUD, new NormalNoise.NoiseParameters(-4, 1.0D, 1.0D, 0.0D, 1.0D));
		context.register(SHRUB_DENSITY, new NormalNoise.NoiseParameters(-3, 1.0D, 1.0D, 0.0D));
		context.register(SHRUB_FLOWER_POWER, new NormalNoise.NoiseParameters(-7, 2.0D, 1.0D, 0.0D));
		context.register(SMOOTHCAP_MOSS_OCCURRENCE, new NormalNoise.NoiseParameters(-4, 1.0D, 1.0D, 0.0D));
		context.register(SMOOTHCAP_MOSS_FREQUENCY, new NormalNoise.NoiseParameters(-7, 2.0D, 1.0D, 0.0D));
		context.register(SMOOTHCAP_MOSS_COVER, new NormalNoise.NoiseParameters(-4, 1.0D, 1.0D, 0.0D));
		context.register(SMOOTHCAP_MOSS_RADIUS, new NormalNoise.NoiseParameters(-5, 1.0D, 1.0D, 0.0D));
		context.register(WISTERIA_DENSITY, new NormalNoise.NoiseParameters(-8, 1.0D));
		context.register(WISTERIA_COLOR, new NormalNoise.NoiseParameters(-9, 1.0D));
	}

	public static ResourceKey<NoiseParameters> create(String name) {
		return ResourceKey.create(Registries.NOISE, Environmental.location(name));
	}
}