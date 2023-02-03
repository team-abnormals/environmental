package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface BuiltInSlabfishTypes {
	ResourceKey<SlabfishType> SWAMP = createKey("swamp");

	ResourceKey<SlabfishType> BADLANDS = createKey("badlands");
	ResourceKey<SlabfishType> BAMBOO = createKey("bamboo");
	ResourceKey<SlabfishType> BEACH = createKey("beach");
	ResourceKey<SlabfishType> BLOSSOM = createKey("blossom");
	ResourceKey<SlabfishType> DARK_FOREST = createKey("dark_forest");
	ResourceKey<SlabfishType> DESERT = createKey("desert");
	ResourceKey<SlabfishType> FLOWER_FOREST = createKey("flower_forest");
	ResourceKey<SlabfishType> FOREST = createKey("forest");
	ResourceKey<SlabfishType> HILL = createKey("hill");
	ResourceKey<SlabfishType> ICE_SPIKES = createKey("ice_spikes");
	ResourceKey<SlabfishType> JUNGLE = createKey("jungle");
	ResourceKey<SlabfishType> MARSH = createKey("marsh");
	ResourceKey<SlabfishType> PLAINS = createKey("plains");
	ResourceKey<SlabfishType> SAVANNA = createKey("savanna");
	ResourceKey<SlabfishType> SNOWY = createKey("snowy");
	ResourceKey<SlabfishType> TAIGA = createKey("taiga");

	ResourceKey<SlabfishType> RIVER = createKey("river");
	ResourceKey<SlabfishType> OCEAN = createKey("ocean");
	ResourceKey<SlabfishType> WARM_OCEAN = createKey("warm_ocean");
	ResourceKey<SlabfishType> FROZEN_OCEAN = createKey("frozen_ocean");
	ResourceKey<SlabfishType> DROWNED = createKey("drowned");

	ResourceKey<SlabfishType> NETHER = createKey("nether");
	ResourceKey<SlabfishType> CRIMSON = createKey("crimson");
	ResourceKey<SlabfishType> WARPED = createKey("warped");
	ResourceKey<SlabfishType> SOUL_SAND_VALLEY = createKey("soul_sand_valley");
	ResourceKey<SlabfishType> BASALT_DELTAS = createKey("basalt_deltas");

	ResourceKey<SlabfishType> END = createKey("end");
	ResourceKey<SlabfishType> CHORUS = createKey("chorus");

	ResourceKey<SlabfishType> MUSHROOM = createKey("mushroom");
	ResourceKey<SlabfishType> BROWN_MUSHROOM = createKey("brown_mushroom");
	ResourceKey<SlabfishType> SKELETON = createKey("skeleton");
	ResourceKey<SlabfishType> STRAY = createKey("stray");
	ResourceKey<SlabfishType> WITHER = createKey("wither");
	ResourceKey<SlabfishType> CAVE = createKey("cave");
	ResourceKey<SlabfishType> SKY = createKey("sky");
	ResourceKey<SlabfishType> GHOST = createKey("ghost");
	ResourceKey<SlabfishType> NIGHTMARE = createKey("nightmare");
	ResourceKey<SlabfishType> TOTEM = createKey("totem");

	ResourceKey<SlabfishType> BAGEL = createKey("bagel");
	ResourceKey<SlabfishType> BMO = createKey("bmo");
	ResourceKey<SlabfishType> CAMERON = createKey("cameron");
	ResourceKey<SlabfishType> EVE = createKey("eve");
	ResourceKey<SlabfishType> GORE = createKey("gore");
	ResourceKey<SlabfishType> JACKSON = createKey("jackson");
	ResourceKey<SlabfishType> MINION = createKey("minion");
	ResourceKey<SlabfishType> OCELOT = createKey("ocelot");
	ResourceKey<SlabfishType> RENREN = createKey("renren");
	ResourceKey<SlabfishType> SMELLY = createKey("smelly");
	ResourceKey<SlabfishType> SNAKE_BLOCK = createKey("snake_block");

	ResourceKey<SlabfishType> RAINFOREST = createKey("atmospheric", "rainforest");
	ResourceKey<SlabfishType> DUNES = createKey("atmospheric", "dunes");
	ResourceKey<SlabfishType> MAPLE = createKey("autumnity", "maple");
	ResourceKey<SlabfishType> POISE = createKey("endergetic", "poise");

	private static ResourceKey<SlabfishType> createKey(String namespace, String path) {
		return ResourceKey.create(EnvironmentalSlabfishTypes.SLABFISH_TYPES_REGISTRY, new ResourceLocation(namespace, path));
	}

	private static ResourceKey<SlabfishType> createKey(String path) {
		return createKey(Environmental.MOD_ID, path);
	}
}