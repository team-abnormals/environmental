package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.AddFeaturesIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.AddSpawnsIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.InvertedRemoveSpawnsBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomeModifierTypes.InvertedRemoveSpawnsIgnoreBiomeModifier;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnvironmentalBiomeModifiers {

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		addSpawn(context, "slabfish", EnvironmentalBiomeTags.HAS_SLABFISH, new SpawnerData(EnvironmentalEntityTypes.SLABFISH.get(), 12, 4, 4));
		addSpawn(context, "duck", EnvironmentalBiomeTags.HAS_DUCK, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 10, 4, 4));
		addSpawn(context, "duck_rare", Biomes.RIVER, new SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 1, 1, 2));
		addSpawnIgnore(context, "deer", EnvironmentalBiomeTags.HAS_DEER, EnvironmentalBiomeTags.WITHOUT_DEER, new SpawnerData(EnvironmentalEntityTypes.DEER.get(), 10, 4, 4));
		addSpawn(context, "reindeer", EnvironmentalBiomeTags.HAS_REINDEER, new SpawnerData(EnvironmentalEntityTypes.REINDEER.get(), 10, 4, 4));
		addSpawn(context, "tapir", EnvironmentalBiomeTags.HAS_TAPIR, new SpawnerData(EnvironmentalEntityTypes.TAPIR.get(), 10, 2, 2));
		addSpawn(context, "tapir_rare", Biomes.SPARSE_JUNGLE, new SpawnerData(EnvironmentalEntityTypes.TAPIR.get(), 4, 2, 2));
		addSpawn(context, "yak", EnvironmentalBiomeTags.HAS_YAK, new SpawnerData(EnvironmentalEntityTypes.YAK.get(), 8, 4, 4));
		addSpawn(context, "deer_meadow", Biomes.MEADOW, new SpawnerData(EnvironmentalEntityTypes.DEER.get(), 1, 2, 4));
		addSpawn(context, "pig", Biomes.MANGROVE_SWAMP, new SpawnerData(EntityType.PIG, 10, 4, 4));
		addFeature(context, "zebra_dazzle", EnvironmentalBiomeTags.HAS_ZEBRA, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.ZEBRA_DAZZLE);

		removeSpawnInvertedIgnore(context, "pig", EnvironmentalBiomeTags.HAS_PIG, Biomes.GROVE, EntityType.PIG);
		removeSpawnInverted(context, "sheep", EnvironmentalBiomeTags.HAS_SHEEP, EntityType.SHEEP);
		removeSpawnInverted(context, "cow", EnvironmentalBiomeTags.HAS_COW, EntityType.COW);
		removeSpawnInvertedIgnore(context, "chicken", EnvironmentalBiomeTags.HAS_CHICKEN, Biomes.GROVE, EntityType.CHICKEN);

		addFeature(context, "cattails", EnvironmentalBiomeTags.HAS_CATTAILS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS);
		addFeature(context, "cup_lichen", EnvironmentalBiomeTags.HAS_CUP_LICHEN, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_CUP_LICHEN_TAIGA);
		addFeatureIgnore(context, "dwarf_spruce_sparse", EnvironmentalBiomeTags.HAS_SPARSE_DWARF_SPRUCE, EnvironmentalBiomeTags.IS_PINE_BARRENS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.DWARF_SPRUCE_SPARSE);
		addFeature(context, "bluebell", EnvironmentalBiomeTags.HAS_BLUEBELL, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL_LARGE);
		addFeature(context, "violet", EnvironmentalBiomeTags.HAS_VIOLET, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_VIOLET);
		addFeature(context, "tasselflower", EnvironmentalBiomeTags.HAS_TASSELFLOWER, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TASSELFLOWER);
		addFeature(context, "bird_of_paradise", EnvironmentalBiomeTags.HAS_BIRD_OF_PARADISE, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BIRD_OF_PARADISE);
		addFeature(context, "hibiscus_bush", EnvironmentalBiomeTags.HAS_HIBISCUS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.HIBISCUS_BUSH);
		addFeature(context, "cartwheel", BiomeTags.IS_OVERWORLD, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL);

		addFeature(context, "mycelium_sprouts", Biomes.MUSHROOM_FIELDS, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_MYCELIUM_SPROUTS);
		addFeature(context, "mud_disk", EnvironmentalBiomeTags.HAS_MUD_DISK, Decoration.UNDERGROUND_ORES, EnvironmentalPlacedFeatures.ORE_MUD);

		removeFeature(context, "swamp_oak", Biomes.SWAMP, Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SWAMP);
		addFeature(context, "swamp_vegetation", Biomes.SWAMP, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_SWAMP, EnvironmentalPlacedFeatures.PATCH_DUCKWEED_SWAMP);
		addFeature(context, "flower_forest_vegetation", Biomes.FLOWER_FOREST, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_DELPHINIUMS, EnvironmentalPlacedFeatures.TREES_WISTERIA);
		addFeature(context, "savanna_vegetation", BiomeTags.IS_SAVANNA, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA);
		addFeature(context, "plains_vegetation", BlueprintBiomeTags.IS_GRASSLAND, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_PLAINS);
		addFeature(context, "jungle_vegetation", BiomeTags.IS_JUNGLE, Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_JUNGLE, EnvironmentalPlacedFeatures.PATCH_LARGE_FERN_JUNGLE);
	}

	@SafeVarargs
	private static void removeFeature(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "remove_feature/" + name, () -> new RemoveFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), featureSet(context, features), Set.of(step)));
	}

	private static void removeSpawnInverted(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, EntityType<?>... types) {
		register(context, "remove_spawn/" + name, () -> new InvertedRemoveSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), HolderSet.direct(Stream.of(types).map(type -> ForgeRegistries.ENTITY_TYPES.getHolder(type).get()).collect(Collectors.toList()))));
	}

	private static void removeSpawnInvertedIgnore(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, ResourceKey<Biome> ignoredBiome, EntityType<?>... types) {
		register(context, "remove_spawn/" + name, () -> new InvertedRemoveSpawnsIgnoreBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(ignoredBiome)), HolderSet.direct(Stream.of(types).map(type -> ForgeRegistries.ENTITY_TYPES.getHolder(type).get()).collect(Collectors.toList()))));
	}

	@SafeVarargs
	private static void addFeatureIgnore(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, TagKey<Biome> ignoredBiomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesIgnoreBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), context.lookup(Registries.BIOME).getOrThrow(ignoredBiomes), featureSet(context, features), step));
	}

	@SafeVarargs
	private static void addFeature(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
	}

	@SafeVarargs
	private static void addFeature(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), featureSet(context, features), step));
	}

	private static void addSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
		register(context, "add_spawn/" + name, () -> new AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
	}

	private static void addSpawn(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, MobSpawnSettings.SpawnerData... spawns) {
		register(context, "add_spawn/" + name, () -> new AddSpawnsBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), List.of(spawns)));
	}


	private static void addSpawnIgnore(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, TagKey<Biome> ignoredBiome, MobSpawnSettings.SpawnerData... spawns) {
		register(context, "add_spawn/" + name, () -> new AddSpawnsIgnoreBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), context.lookup(Registries.BIOME).getOrThrow(ignoredBiome), List.of(spawns)));
	}

	private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
		context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Environmental.MOD_ID, name)), modifier.get());
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(key -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(key)).collect(Collectors.toList()));
	}
}