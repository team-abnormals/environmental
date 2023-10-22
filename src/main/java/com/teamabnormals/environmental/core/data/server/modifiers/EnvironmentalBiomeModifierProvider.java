package com.teamabnormals.environmental.core.data.server.modifiers;

import com.mojang.serialization.JsonOps;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.AddFeaturesIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.AddSpawnsIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.InvertedRemoveSpawnsBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.InvertedRemoveSpawnsIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnvironmentalBiomeModifierProvider {
	private static final RegistryAccess ACCESS = RegistryAccess.builtinCopy();
	private static final Registry<Biome> BIOMES = ACCESS.registryOrThrow(Registry.BIOME_REGISTRY);
	private static final Registry<PlacedFeature> PLACED_FEATURES = ACCESS.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
	private static final Registry<EntityType<?>> ENTITY_TYPES = ACCESS.registryOrThrow(ForgeRegistries.ENTITY_TYPES.getRegistryKey());
	private static final HashMap<ResourceLocation, BiomeModifier> MODIFIERS = new HashMap<>();

	public static JsonCodecProvider<BiomeModifier> create(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		addSpawn("slabfish", EnvironmentalBiomeTags.HAS_SLABFISH, new SpawnerData(EnvironmentalEntityTypes.SLABFISH.get(), 16, 2, 4));
		addSpawn("duck", EnvironmentalBiomeTags.HAS_DUCK, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 10, 4, 4));
		addSpawn("duck_river", Biomes.RIVER, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 1, 1, 2));
		addSpawnIgnore("deer", EnvironmentalBiomeTags.HAS_DEER, EnvironmentalBiomeTags.WITHOUT_DEER, new SpawnerData(EnvironmentalEntityTypes.DEER.get(), 16, 4, 4));
		addSpawn("reindeer", EnvironmentalBiomeTags.HAS_REINDEER, new SpawnerData(EnvironmentalEntityTypes.REINDEER.get(), 16, 4, 4));
		addSpawn("yak", EnvironmentalBiomeTags.HAS_YAK, new SpawnerData(EnvironmentalEntityTypes.YAK.get(), 8, 4, 4));
		addSpawn("deer_meadow", Biomes.MEADOW, new SpawnerData(EnvironmentalEntityTypes.DEER.get(), 1, 2, 4));
		addSpawn("zebra", EnvironmentalBiomeTags.HAS_ZEBRA, new SpawnerData(EnvironmentalEntityTypes.ZEBRA.get(), 1, 12, 18));

		removeSpawnInvertedIgnore("pig", EnvironmentalBiomeTags.HAS_PIG, Biomes.GROVE, EntityType.PIG);
		removeSpawnInverted("sheep", EnvironmentalBiomeTags.HAS_SHEEP, EntityType.SHEEP);
		removeSpawnInverted("cow", EnvironmentalBiomeTags.HAS_COW, EntityType.COW);
		removeSpawnInvertedIgnore("chicken", EnvironmentalBiomeTags.HAS_CHICKEN, Biomes.GROVE, EntityType.CHICKEN);

		addFeature("cattails", EnvironmentalBiomeTags.HAS_CATTAILS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS);
		addFeature("cup_lichen", EnvironmentalBiomeTags.HAS_CUP_LICHEN, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_CUP_LICHEN);
		addFeature("bluebell", EnvironmentalBiomeTags.HAS_BLUEBELL, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL_LARGE);
		addFeature("violet", EnvironmentalBiomeTags.HAS_VIOLET, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_VIOLET);
		addFeature("tasselflower", EnvironmentalBiomeTags.HAS_TASSELFLOWER, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TASSELFLOWER);
		addFeature("bird_of_paradise", EnvironmentalBiomeTags.HAS_BIRD_OF_PARADISE, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BIRD_OF_PARADISE);
		addFeature("hibiscus_bush", EnvironmentalBiomeTags.HAS_HIBISCUS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.HIBISCUS_BUSH);

		addFeature("tall_dead_bush", Biomes.DESERT, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH);
		addFeature("tall_dead_bush_badlands", BiomeTags.IS_BADLANDS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS);
		addFeature("mycelium_sprouts", Biomes.MUSHROOM_FIELDS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_MYCELIUM_SPROUTS);
		addFeature("mud_disk", EnvironmentalBiomeTags.HAS_MUD_DISK, Decoration.UNDERGROUND_ORES, EnvironmentalPlacedFeatures.DISK_MUD);

		removeFeature("swamp_oak", Biomes.SWAMP, Set.of(Decoration.VEGETAL_DECORATION), VegetationPlacements.TREES_SWAMP);
		addFeature("swamp_vegetation", Biomes.SWAMP, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_SWAMP, EnvironmentalPlacedFeatures.PATCH_DUCKWEED_SWAMP);
		addFeature("flower_forest_vegetation", Biomes.FLOWER_FOREST, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL, EnvironmentalPlacedFeatures.TREES_WISTERIA);
		addFeature("savanna_vegetation", BiomeTags.IS_SAVANNA, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA);
		addFeature("plains_vegetation", BlueprintBiomeTags.IS_GRASSLAND, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_PLAINS);
		addFeature("jungle_vegetation", BiomeTags.IS_JUNGLE, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_JUNGLE);

		return JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, Environmental.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, ACCESS), ForgeRegistries.Keys.BIOME_MODIFIERS, MODIFIERS);
	}

	@SafeVarargs
	private static void addFeature(String name, ResourceKey<Biome> biome, GenerationStep.Decoration step, RegistryObject<PlacedFeature>... features) {
		addModifier("add_feature/" + name, new AddFeaturesBiomeModifier(biomeSet(biome), featureSet(features), step));
	}

	@SafeVarargs
	private static void addFeature(String name, TagKey<Biome> biomes, GenerationStep.Decoration step, RegistryObject<PlacedFeature>... features) {
		addModifier("add_feature/" + name, new AddFeaturesBiomeModifier(new HolderSet.Named<>(BIOMES, biomes), featureSet(features), step));
	}

	@SafeVarargs
	private static void removeFeature(String name, ResourceKey<Biome> biome, Set<GenerationStep.Decoration> steps, Holder<PlacedFeature>... features) {
		addModifier("remove_feature/" + name, new RemoveFeaturesBiomeModifier(biomeSet(biome), featureSet(features), steps));
	}

	private static void addSpawn(String name, ResourceKey<Biome> biome, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsBiomeModifier(biomeSet(biome), List.of(spawns)));
	}

	private static void addSpawn(String name, TagKey<Biome> tagKey, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsBiomeModifier(new HolderSet.Named<>(BIOMES, tagKey), List.of(spawns)));
	}

	private static void removeSpawnInverted(String name, TagKey<Biome> biomes, EntityType<?>... spawns) {
		addModifier("remove_spawn/" + name, new InvertedRemoveSpawnsBiomeModifier(new HolderSet.Named<>(BIOMES, biomes), HolderSet.direct(Stream.of(spawns).map(type -> ENTITY_TYPES.getOrCreateHolderOrThrow(ENTITY_TYPES.getResourceKey(type).get())).collect(Collectors.toList()))));
	}

	private static void removeSpawnInvertedIgnore(String name, TagKey<Biome> biomes, ResourceKey<Biome> ignoredBiome, EntityType<?>... spawns) {
		addModifier("remove_spawn/" + name, new InvertedRemoveSpawnsIgnoreBiomeModifier(new HolderSet.Named<>(BIOMES, biomes), biomeSet(ignoredBiome), HolderSet.direct(Stream.of(spawns).map(type -> ENTITY_TYPES.getOrCreateHolderOrThrow(ENTITY_TYPES.getResourceKey(type).get())).collect(Collectors.toList()))));
	}

	private static void addSpawnIgnore(String name, TagKey<Biome> tagKey, TagKey<Biome> ignoredBiomes, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsIgnoreBiomeModifier(new HolderSet.Named<>(BIOMES, tagKey), new HolderSet.Named<>(BIOMES, ignoredBiomes), List.of(spawns)));
	}

	private static void addSpawnIgnore(String name, TagKey<Biome> tagKey, ResourceKey<Biome> ignoredBiome, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsIgnoreBiomeModifier(new HolderSet.Named<>(BIOMES, tagKey), biomeSet(ignoredBiome), List.of(spawns)));
	}

	@SafeVarargs
	private static void addFeatureIgnore(String name, TagKey<Biome> biomes, TagKey<Biome> ignoredBiomes, GenerationStep.Decoration step, RegistryObject<PlacedFeature>... features) {
		addModifier("add_feature/" + name, new AddFeaturesIgnoreBiomeModifier(new HolderSet.Named<>(BIOMES, biomes), new HolderSet.Named<>(BIOMES, ignoredBiomes), featureSet(features), step));
	}

	private static void addModifier(String name, BiomeModifier modifier) {
		MODIFIERS.put(new ResourceLocation(Environmental.MOD_ID, name), modifier);
	}

	@SafeVarargs
	private static HolderSet<Biome> biomeSet(ResourceKey<Biome>... biomes) {
		return HolderSet.direct(Stream.of(biomes).map(BIOMES::getOrCreateHolderOrThrow).collect(Collectors.toList()));
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(RegistryObject<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(registryObject -> PLACED_FEATURES.getOrCreateHolderOrThrow(registryObject.getKey())).collect(Collectors.toList()));
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(Holder<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(registryObject -> PLACED_FEATURES.getOrCreateHolderOrThrow(registryObject.unwrapKey().get())).collect(Collectors.toList()));
	}
}