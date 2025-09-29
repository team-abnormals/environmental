package com.teamabnormals.environmental.core.registry.slabfish;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Event;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Time;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnvironmentalSlabfishTypes {
	public static final ResourceKey<SlabfishType> SWAMP = createKey("swamp");
	public static final ResourceKey<SlabfishType> BADLANDS = createKey("badlands");
	public static final ResourceKey<SlabfishType> BAMBOO = createKey("bamboo");
	public static final ResourceKey<SlabfishType> BEACH = createKey("beach");
	public static final ResourceKey<SlabfishType> DARK_FOREST = createKey("dark_forest");
	public static final ResourceKey<SlabfishType> DESERT = createKey("desert");
	public static final ResourceKey<SlabfishType> FLOWER_FOREST = createKey("flower_forest");
	public static final ResourceKey<SlabfishType> FOREST = createKey("forest");
	public static final ResourceKey<SlabfishType> HILL = createKey("hill");
	public static final ResourceKey<SlabfishType> ICE_SPIKES = createKey("ice_spikes");
	public static final ResourceKey<SlabfishType> JUNGLE = createKey("jungle");
	public static final ResourceKey<SlabfishType> MANGROVE = createKey("mangrove");
	public static final ResourceKey<SlabfishType> MOUNTAIN = createKey("mountain");
	public static final ResourceKey<SlabfishType> PLAINS = createKey("plains");
	public static final ResourceKey<SlabfishType> RIVER = createKey("river");
	public static final ResourceKey<SlabfishType> SAVANNA = createKey("savanna");
	public static final ResourceKey<SlabfishType> SNOWY = createKey("snowy");
	public static final ResourceKey<SlabfishType> TAIGA = createKey("taiga");
	public static final ResourceKey<SlabfishType> CHERRY_GROVE = createKey("cherry_grove");

	public static final ResourceKey<SlabfishType> MARSH = createKey("marsh");
	public static final ResourceKey<SlabfishType> PINE = createKey("pine");
	public static final ResourceKey<SlabfishType> BLOSSOM = createKey("blossom");

	public static final ResourceKey<SlabfishType> RAINFOREST = createKey("rainforest");
	public static final ResourceKey<SlabfishType> DUNES = createKey("dunes");
	public static final ResourceKey<SlabfishType> SCRUBLAND = createKey("scrubland");
	public static final ResourceKey<SlabfishType> SPINY_THICKET = createKey("spiny_thicket");
	public static final ResourceKey<SlabfishType> ASPEN = createKey("aspen");
	public static final ResourceKey<SlabfishType> LAUREL = createKey("laurel");
	public static final ResourceKey<SlabfishType> KOUSA = createKey("kousa");

	public static final ResourceKey<SlabfishType> MAPLE = createKey("maple");

	public static final ResourceKey<SlabfishType> OCEAN = createKey("ocean");
	public static final ResourceKey<SlabfishType> WARM_OCEAN = createKey("warm_ocean");
	public static final ResourceKey<SlabfishType> FROZEN_OCEAN = createKey("frozen_ocean");

	public static final ResourceKey<SlabfishType> CAVE = createKey("cave");
	public static final ResourceKey<SlabfishType> DEEPSLATE = createKey("deepslate");
	public static final ResourceKey<SlabfishType> LUSH_CAVES = createKey("lush_caves");
	public static final ResourceKey<SlabfishType> DRIPSTONE_CAVES = createKey("dripstone_caves");
	public static final ResourceKey<SlabfishType> DEEP_DARK = createKey("deep_dark");

	public static final ResourceKey<SlabfishType> NETHER = createKey("nether");
	public static final ResourceKey<SlabfishType> CRIMSON = createKey("crimson");
	public static final ResourceKey<SlabfishType> WARPED = createKey("warped");
	public static final ResourceKey<SlabfishType> BASALT_DELTAS = createKey("basalt_deltas");
	public static final ResourceKey<SlabfishType> SOUL_SAND_VALLEY = createKey("soul_sand_valley");
	public static final ResourceKey<SlabfishType> GHOST = createKey("ghost");

	public static final ResourceKey<SlabfishType> END = createKey("end");
	public static final ResourceKey<SlabfishType> CHORUS = createKey("chorus");
	public static final ResourceKey<SlabfishType> POISE = createKey("poise");

	public static final ResourceKey<SlabfishType> MUSHROOM = createKey("mushroom");
	public static final ResourceKey<SlabfishType> BROWN_MUSHROOM = createKey("brown_mushroom");
	public static final ResourceKey<SlabfishType> DROWNED = createKey("drowned");
	public static final ResourceKey<SlabfishType> NIGHTMARE = createKey("nightmare");
	public static final ResourceKey<SlabfishType> SKY = createKey("sky");
	public static final ResourceKey<SlabfishType> SKELETON = createKey("skeleton");
	public static final ResourceKey<SlabfishType> STRAY = createKey("stray");
	public static final ResourceKey<SlabfishType> WITHER = createKey("wither");
	public static final ResourceKey<SlabfishType> TOTEM = createKey("totem");

	public static final List<ResourceKey<SlabfishType>> ATMOSPHERIC_SLABFISH = List.of(RAINFOREST, DUNES, SCRUBLAND, SPINY_THICKET, ASPEN, KOUSA, LAUREL);
	public static final List<ResourceKey<SlabfishType>> AUTUMNITY_SLABFISH = List.of(MAPLE);
	public static final List<ResourceKey<SlabfishType>> ENDERGETIC_SLABFISH = List.of(POISE);

	public static final List<ResourceKey<SlabfishType>> COMPAT_SLABFISH = Stream.concat(Stream.concat(ATMOSPHERIC_SLABFISH.stream(), AUTUMNITY_SLABFISH.stream()), ENDERGETIC_SLABFISH.stream()).collect(Collectors.toList());

	public static void bootstrap(BootstapContext<SlabfishType> context) {
		register(context, SWAMP, -1, Tags.Biomes.IS_SWAMP);

		register(context, BADLANDS, 0, BiomeTags.IS_BADLANDS);
		register(context, BEACH, 0, spawn(), or(biome(context, BiomeTags.IS_BEACH), biome(context, Biomes.STONY_SHORE)));
		register(context, DESERT, 0, Biomes.DESERT);
		register(context, FOREST, 0, BiomeTags.IS_FOREST);
		register(context, HILL, 0, BiomeTags.IS_HILL);
		register(context, JUNGLE, 0, BiomeTags.IS_JUNGLE);
		register(context, OCEAN, 0, BiomeTags.IS_OCEAN);
		register(context, PLAINS, 0, Tags.Biomes.IS_PLAINS);
		register(context, RIVER, 0, Biomes.RIVER);
		register(context, SAVANNA, 0, BiomeTags.IS_SAVANNA);
		register(context, TAIGA, 0, BiomeTags.IS_TAIGA);

		register(context, WARM_OCEAN, 1, Biomes.WARM_OCEAN);
		register(context, BAMBOO, 1, Biomes.BAMBOO_JUNGLE);
		register(context, CHERRY_GROVE, 1, Biomes.CHERRY_GROVE);
		register(context, DARK_FOREST, 1, Biomes.DARK_FOREST);
		register(context, FLOWER_FOREST, 1, Biomes.FLOWER_FOREST);
		register(context, ICE_SPIKES, 1, Biomes.ICE_SPIKES);
		register(context, MANGROVE, 1, Biomes.MANGROVE_SWAMP);
		register(context, MOUNTAIN, 1, spawn(), or(biome(context, Biomes.MEADOW), biome(context, Biomes.STONY_PEAKS)));
		register(context, SNOWY, 1, Tags.Biomes.IS_SNOWY);

		register(context, FROZEN_OCEAN, 2, spawn(), or(biome(context, Biomes.FROZEN_OCEAN), biome(context, Biomes.DEEP_FROZEN_OCEAN)));
		register(context, MUSHROOM, 2, or(and(spawn(), biome(context, Biomes.MUSHROOM_FIELDS)), and(new SlabfishEventCondition(Event.LIGHTNING), new SlabfishTypeCondition(BROWN_MUSHROOM.location()))));
		register(context, BROWN_MUSHROOM, 2, new SlabfishEventCondition(Event.LIGHTNING), new SlabfishTypeCondition(MUSHROOM.location()));

		register(context, TOTEM, 3, spawn(), new SlabfishRaidCondition());
		register(context, NIGHTMARE, 3, spawn(), new SlabfishInsomniaCondition(), new SlabfishTimeCondition(Time.NIGHT));

		register(context, CAVE, 4, spawn(), new SlabfishHeightCondition(0, 48), new SlabfishLightCondition(0, 0, LightLayer.SKY), overworld());
		register(context, DEEPSLATE, 4, spawn(), new SlabfishHeightCondition(Integer.MIN_VALUE, 0), new SlabfishLightCondition(0, 0, LightLayer.SKY), overworld());
		register(context, SKY, 4, spawn(), new SlabfishHeightCondition(256, Integer.MAX_VALUE), overworld());

		register(context, LUSH_CAVES, 5, Biomes.LUSH_CAVES);
		register(context, DRIPSTONE_CAVES, 5, Biomes.DRIPSTONE_CAVES);
		register(context, DEEP_DARK, 5, Biomes.DEEP_DARK);

		register(context, DROWNED, 10, spawn(), biome(context, BiomeTags.IS_OCEAN), new SlabfishHeightCondition(Integer.MIN_VALUE, 48), new SlabfishInFluidCondition(context.lookup(Registries.FLUID).getOrThrow(FluidTags.WATER)));
		register(context, SKELETON, 10, new SlabfishEventCondition(Event.LIGHTNING));
		register(context, STRAY, 10, new SlabfishEventCondition(Event.BREED), new SlabfishBreedCondition(SKELETON.location(), SKELETON.location()), biome(context, Tags.Biomes.IS_SNOWY));
		register(context, WITHER, 10, new SlabfishEventCondition(Event.BREED), new SlabfishBreedCondition(SKELETON.location(), SKELETON.location()), dimension(BuiltinDimensionTypes.NETHER));

		register(context, BLOSSOM, 1, spawn(), or(biome(context, EnvironmentalBiomes.BLOSSOM_WOODS), biome(context, EnvironmentalBiomes.BLOSSOM_VALLEYS)));
		register(context, MARSH, 1, EnvironmentalBiomes.MARSH);
		register(context, PINE, 1, spawn(), or(biome(context, EnvironmentalBiomeTags.IS_PINE_BARRENS), biome(context, EnvironmentalBiomes.PINE_SLOPES)));

		register(context, DUNES, 0, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalBiomeTags.IS_DUNES);
		register(context, RAINFOREST, 1, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalBiomeTags.IS_RAINFOREST);
		register(context, ASPEN, 1, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalConstants.ASPEN_PARKLAND);
		register(context, LAUREL, 1, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalConstants.LAUREL_FOREST);
		register(context, SPINY_THICKET, 1, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalConstants.SPINY_THICKET);
		register(context, KOUSA, 2, EnvironmentalConstants.ATMOSPHERIC, EnvironmentalConstants.KOUSA_JUNGLE);
		register(context, SCRUBLAND, 2, modLoaded(EnvironmentalConstants.ATMOSPHERIC), spawn(), or(biome(EnvironmentalConstants.SCRUBLAND), biome(EnvironmentalConstants.SNOWY_SCRUBLAND)));

		register(context, MAPLE, 1, modLoaded(EnvironmentalConstants.AUTUMNITY), spawn(), or(biome(EnvironmentalConstants.MAPLE_FOREST), biome(EnvironmentalConstants.PUMPKIN_FIELDS)));

		register(context, NETHER, 0, spawn(), dimension(BuiltinDimensionTypes.NETHER));
		register(context, CRIMSON, 1, Biomes.CRIMSON_FOREST);
		register(context, WARPED, 1, Biomes.WARPED_FOREST);
		register(context, SOUL_SAND_VALLEY, 1, Biomes.SOUL_SAND_VALLEY);
		register(context, BASALT_DELTAS, 1, Biomes.BASALT_DELTAS);
		register(context, GHOST, 0, new SlabfishImpossibleCondition());

		register(context, END, 0, spawn(), dimension(BuiltinDimensionTypes.END));
		register(context, CHORUS, 1, BlueprintBiomeTags.IS_OUTER_END);
		register(context, POISE, 1, EnvironmentalConstants.ENDERGETIC, EnvironmentalConstants.POISE_FOREST);
	}

	public static void register(BootstapContext<SlabfishType> context, ResourceKey<SlabfishType> key, int priority, SlabfishCondition... conditions) {
		context.register(key, new SlabfishType(
				Component.translatable(Util.makeDescriptionId("slabfish.type", key.location())),
				new ResourceLocation(key.location().getNamespace(), "type/" + key.location().getPath()),
				Optional.empty(), priority, Arrays.stream(conditions).toArray(SlabfishCondition[]::new)));
	}

	public static void register(BootstapContext<SlabfishType> context, ResourceKey<SlabfishType> key, int priority, TagKey<Biome> biomes) {
		register(context, key, priority, spawn(), biome(context, biomes));
	}

	public static void register(BootstapContext<SlabfishType> context, ResourceKey<SlabfishType> key, int priority, ResourceKey<Biome> biome) {
		register(context, key, priority, spawn(), biome(context, biome));
	}

	public static void register(BootstapContext<SlabfishType> context, ResourceKey<SlabfishType> key, int priority, String modid, TagKey<Biome> biomes) {
		register(context, key, priority, modLoaded(modid), spawn(), biome(context, biomes));
	}

	public static void register(BootstapContext<SlabfishType> context, ResourceKey<SlabfishType> key, int priority, String modid, ResourceLocation biome) {
		register(context, key, priority, modLoaded(modid), spawn(), biome(biome));
	}

	public static SlabfishEventCondition spawn() {
		return new SlabfishEventCondition(Event.SPAWN, Event.BREED);
	}

	public static SlabfishOrCondition or(SlabfishCondition... conditions) {
		return new SlabfishOrCondition(conditions);
	}

	public static SlabfishAndCondition and(SlabfishCondition... conditions) {
		return new SlabfishAndCondition(conditions);
	}

	public static SlabfishInBiomeCondition biome(BootstapContext<SlabfishType> context, ResourceKey<Biome> biome) {
		return new SlabfishInBiomeCondition(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)));
	}

	public static SlabfishInBiomeCondition biome(BootstapContext<SlabfishType> context, TagKey<Biome> biomes) {
		return new SlabfishInBiomeCondition(context.lookup(Registries.BIOME).getOrThrow(biomes));
	}

	public static SlabfishInBiomeKeyCondition biome(ResourceLocation biome) {
		return new SlabfishInBiomeKeyCondition(biome);
	}

	public static SlabfishDimensionCondition dimension(ResourceKey<DimensionType> dimension) {
		return new SlabfishDimensionCondition(dimension.location());
	}

	public static SlabfishDimensionCondition overworld() {
		return dimension(BuiltinDimensionTypes.OVERWORLD);
	}

	public static SlabfishModLoadedCondition modLoaded(String modid) {
		return new SlabfishModLoadedCondition(modid);
	}

	public static ResourceKey<SlabfishType> createKey(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_TYPE, Environmental.location(name));
	}
}
