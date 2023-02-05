package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalGeneration {

//	@SubscribeEvent
//	public static void onBiomeLoad(BiomeLoadingEvent event) {
//		ResourceLocation biome = event.getName();
//		BiomeGenerationSettingsBuilder generation = event.getGeneration();
//		MobSpawnSettingsBuilder spawns = event.getSpawns();
//
//		if (event.getCategory() == Biome.BiomeCategory.SWAMP)
//			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.SLABFISH.get(), 8, 2, 4));
//
//		if (DataUtil.matchesKeys(biome, Biomes.SWAMP)) {
//			EnvironmentalGeneration.withMushrooms(generation);
//			EnvironmentalGeneration.withMudDisks(generation);
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_SWAMP.getHolder().get());
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_DUCKWEED.getHolder().get());
//		}
//
//		if (event.getCategory() == Biome.BiomeCategory.RIVER || event.getCategory() == Biome.BiomeCategory.SWAMP)
//			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 5, 2, 4));
//
//		if (event.getCategory() == Biome.BiomeCategory.FOREST) {
//			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DEER.get(), 16, 2, 4));
//			if (DataUtil.matchesKeys(biome, Biomes.FLOWER_FOREST)) {
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL.getHolder().get());
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_DELPHINIUMS.getHolder().get());
//				if (EnvironmentalConfig.COMMON.generateWisteriaTrees.get()) {
//					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_WISTERIA.getHolder().get());
//				}
//			}
//			if (DataUtil.matchesKeys(biome, Biomes.DARK_FOREST)) {
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL.getHolder().get());
//			}
//		}
//
//		if (event.getCategory() == Biome.BiomeCategory.EXTREME_HILLS)
//			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.YAK.get(), 20, 2, 4));
//
//		if (event.getCategory() == Biome.BiomeCategory.MUSHROOM)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_MYCELIUM_SPROUTS.getHolder().get());
//
//		if (!DataUtil.matchesKeys(biome, Biomes.FROZEN_RIVER) && (event.getCategory() == Biome.BiomeCategory.SWAMP || event.getCategory() == Biome.BiomeCategory.RIVER)) {
//			EnvironmentalGeneration.withCattails(generation);
//		}
//
//		if (event.getCategory() == Biome.BiomeCategory.DESERT) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH.getHolder().get());
////			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.FENNEC_FOX.get(), 8, 2, 4));
//		}
//
//		if (event.getCategory() == Biome.BiomeCategory.MESA)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS.getHolder().get());
//
//		if (event.getCategory() == Biome.BiomeCategory.SAVANNA) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_ALLIUM.getHolder().get());
//			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA.getHolder().get());
//		}
//
//		if (event.getCategory() == Biome.BiomeCategory.TAIGA)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_VIOLET.getHolder().get());
//
//		if (biome.toString().contains("rainforest") && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA.getHolder().get());
//
//		if (event.getCategory() == Biome.BiomeCategory.PLAINS && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_PLAINS.getHolder().get());
//
//		if (event.getCategory() == Biome.BiomeCategory.JUNGLE) {
//			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_JUNGLE.getHolder().get());
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BIRD_OF_PARADISE.getHolder().get());
//
//			if (EnvironmentalConfig.COMMON.generateHibiscus.get()) {
//				if (DataUtil.matchesKeys(biome, Biomes.JUNGLE))
//					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_COOL.getHolder().get());
//				if (DataUtil.matchesKeys(biome, Biomes.SPARSE_JUNGLE))
//					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_WARM.getHolder().get());
//			}
//		}
//
//		if (EnvironmentalConfig.COMMON.limitFarmAnimalSpawns.get())
//			removeSpawns(event);
//	}

	public static void withMarshFeatures(BiomeGenerationSettings.Builder generation) {
		BiomeDefaultFeatures.addFossilDecoration(generation);
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addSwampClayDisk(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addSwampExtraVegetation(generation);

		EnvironmentalGeneration.withMudDisks(generation);
		EnvironmentalGeneration.withMarshVegetation(generation);
		EnvironmentalGeneration.withMarshMushrooms(generation);

		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_MARSH.getHolder().get());
	}

	public static void withBaseBlossomFeatures(BiomeGenerationSettings.Builder generation) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FOREST_FLOWERS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_SUGAR_CANE_BLOSSOM.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_TULIPS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GRASS_BLOSSOM_WOODS.getHolder().get());

	}

	public static void withBlossomWoodsFeatures(BiomeGenerationSettings.Builder generation) {
		EnvironmentalGeneration.withBaseBlossomFeatures(generation);
		EnvironmentalGeneration.withBlossomWoodsVegetation(generation);
	}

	public static void withBlossomValleysFeatures(BiomeGenerationSettings.Builder generation) {
		EnvironmentalGeneration.withBaseBlossomFeatures(generation);
		EnvironmentalGeneration.withBlossomValleysVegetation(generation);
	}

	public static void withMushrooms(BiomeGenerationSettingsBuilder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
	}

	public static void withMarshMushrooms(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.HUGE_BROWN_MUSHROOM_MARSH.getHolder().get());
	}

	public static void withMudDisks(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EnvironmentalPlacedFeatures.DISK_MUD.getHolder().get());
	}

	public static void withBlossomWoodsVegetation(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CHERRY_TREES_BLOSSOM_WOODS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BIRCH_TREES_BLOSSOM_WOODS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_BLOSSOM_WOODS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_LIGHT_BLOSSOM_WOODS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_RED_LOTUS.getHolder().get());
	}

	public static void withBlossomValleysVegetation(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CHERRY_TREES_BLOSSOM_VALLEYS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BIRCH_TREES_BLOSSOM_VALLEYS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_BLOSSOM_VALLEYS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_LIGHT_BLOSSOM_VALLEYS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_WHITE_LOTUS.getHolder().get());
	}

	public static void withCattails(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS.getHolder().get());
	}

	public static void withMarshVegetation(BiomeGenerationSettings.Builder generation) {
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUE_ORCHID.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CORNFLOWER.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_DIANTHUS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_WATERLILY_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GRASS_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS_DENSE.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_DUCKWEED.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.SEAGRASS_MARSH.getHolder().get());
	}

//	private static void removeSpawns(BiomeLoadingEvent event) {
//		MobSpawnSettingsBuilder spawns = event.getSpawns();
//		List<MobSpawnSettings.SpawnerData> entrysToRemove = new ArrayList<>();
//		for (MobSpawnSettings.SpawnerData entry : spawns.getSpawner(MobCategory.CREATURE)) {
//			if (event.getCategory() != Biome.BiomeCategory.FOREST) {
//				if (entry.type == EntityType.PIG || entry.type == EntityType.CHICKEN) {
//					entrysToRemove.add(entry);
//				}
//			}
//			if (event.getCategory() != Biome.BiomeCategory.PLAINS) {
//				if (entry.type == EntityType.COW || entry.type == EntityType.SHEEP) {
//					entrysToRemove.add(entry);
//				}
//			}
//		}
//		spawns.getSpawner(MobCategory.CREATURE).removeAll(entrysToRemove);
//	}
}
