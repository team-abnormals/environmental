package com.teamabnormals.environmental.core.data.server.modifiers;

import com.mojang.serialization.JsonOps;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.InvertedRemoveSpawnsBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
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
		addSpawn("duck", EnvironmentalBiomeTags.HAS_DUCK, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 10, 3, 4));
		addSpawn("duck_river", Biomes.RIVER, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 1, 1, 2));
		addSpawn("deer", EnvironmentalBiomeTags.HAS_DEER, new SpawnerData(EnvironmentalEntityTypes.DEER.get(), 16, 4, 4));
		addSpawn("reindeer", EnvironmentalBiomeTags.HAS_REINDEER, new SpawnerData(EnvironmentalEntityTypes.REINDEER.get(), 16, 4, 4));
		addSpawn("yak", EnvironmentalBiomeTags.HAS_YAK, new SpawnerData(EnvironmentalEntityTypes.YAK.get(), 8, 2, 4));
		addSpawn("yak_meadow", Biomes.MEADOW, new SpawnerData(EnvironmentalEntityTypes.YAK.get(), 2, 2, 4));

		removeSpawnInverted("pig", EnvironmentalBiomeTags.HAS_PIG, EntityType.PIG);
		removeSpawnInverted("sheep", EnvironmentalBiomeTags.HAS_SHEEP, EntityType.SHEEP);
		removeSpawnInverted("cow", EnvironmentalBiomeTags.HAS_COW, EntityType.COW);
		removeSpawnInverted("chicken", EnvironmentalBiomeTags.HAS_CHICKEN, EntityType.CHICKEN);

		addFeature("bluebell", Biomes.DARK_FOREST, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL);
		addFeature("violet", BiomeTags.IS_TAIGA, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_VIOLET);
		addFeature("cattails", EnvironmentalBiomeTags.HAS_CATTAILS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS);
		addFeature("tall_dead_bush", Biomes.DESERT, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH);
		addFeature("tall_dead_bush_badlands", BiomeTags.IS_BADLANDS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS);
		addFeature("mycelium_sprouts", Biomes.MUSHROOM_FIELDS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_MYCELIUM_SPROUTS);
		addFeature("mud_disk", EnvironmentalBiomeTags.HAS_MUD_DISK, Decoration.UNDERGROUND_ORES, EnvironmentalPlacedFeatures.DISK_MUD);

		addFeature("swamp_vegetation", Biomes.SWAMP, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.HUGE_SWAMP_MUSHROOMS, EnvironmentalPlacedFeatures.TREES_SWAMP, EnvironmentalPlacedFeatures.PATCH_DUCKWEED_SWAMP);
		addFeature("flower_forest_vegetation", Biomes.FLOWER_FOREST, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL, EnvironmentalPlacedFeatures.PATCH_DELPHINIUMS, EnvironmentalPlacedFeatures.TREES_WISTERIA);
		addFeature("savanna_vegetation", BiomeTags.IS_SAVANNA, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_ALLIUM, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA);
		addFeature("plains_vegetation", EnvironmentalBiomeTags.HAS_COW, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_PLAINS);
		addFeature("jungle_vegetation", BiomeTags.IS_JUNGLE, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BIRD_OF_PARADISE, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_JUNGLE);

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

	private static void addSpawn(String name, ResourceKey<Biome> biome, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsBiomeModifier(biomeSet(biome), List.of(spawns)));
	}

	private static void addSpawn(String name, TagKey<Biome> tagKey, MobSpawnSettings.SpawnerData... spawns) {
		addModifier("add_spawn/" + name, new AddSpawnsBiomeModifier(new HolderSet.Named<>(BIOMES, tagKey), List.of(spawns)));
	}

	private static void removeSpawnInverted(String name, TagKey<Biome> biomes, EntityType<?>... spawns) {
		addModifier("remove_spawn/" + name, new InvertedRemoveSpawnsBiomeModifier(new HolderSet.Named<>(BIOMES, biomes), HolderSet.direct(Stream.of(spawns).map(type -> ENTITY_TYPES.getOrCreateHolderOrThrow(ENTITY_TYPES.getResourceKey(type).get())).collect(Collectors.toList()))));
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
}