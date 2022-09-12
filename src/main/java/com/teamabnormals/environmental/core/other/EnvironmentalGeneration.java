package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalGeneration {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEarlyBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnSettingsBuilder spawns = event.getSpawns();

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MARSH.getKey(), EnvironmentalBiomes.MUSHROOM_MARSH.getKey())) {
			EnvironmentalGeneration.withMarshFeatures(generation);
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MUSHROOM_MARSH.getKey()))
				EnvironmentalGeneration.withMarshMushrooms(generation);

			BiomeDefaultFeatures.farmAnimals(spawns);
			BiomeDefaultFeatures.commonSpawns(spawns);
		}

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_WOODS.getKey(), EnvironmentalBiomes.BLOSSOM_HILLS.getKey(), EnvironmentalBiomes.BLOSSOM_HIGHLANDS.getKey(), EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey())) {
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey()))
				EnvironmentalGeneration.withBlossomValleysFeatures(generation);
			else
				EnvironmentalGeneration.withBlossomWoodsFeatures(generation);

			BiomeDefaultFeatures.farmAnimals(spawns);
			BiomeDefaultFeatures.commonSpawns(spawns);
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 80, 1, 2));
			spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.KOI.get(), 12, 1, 3));
		}
	}

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnSettingsBuilder spawns = event.getSpawns();

		if (event.getCategory() == Biome.BiomeCategory.SWAMP)
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.SLABFISH.get(), 8, 2, 4));

		if (DataUtil.matchesKeys(biome, Biomes.SWAMP)) {
			EnvironmentalGeneration.withMushrooms(generation);
			EnvironmentalGeneration.withCattails(generation);
			EnvironmentalGeneration.withMudDisks(generation);
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SWAMP_OAK);
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WILLOW_TREE);
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_SWAMP);
		}

		if (event.getCategory() == Biome.BiomeCategory.RIVER || event.getCategory() == Biome.BiomeCategory.SWAMP)
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 5, 2, 4));

		if (event.getCategory() == Biome.BiomeCategory.FOREST) {
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DEER.get(), 16, 1, 4));
			if (DataUtil.matchesKeys(biome, Biomes.FLOWER_FOREST)) {
				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL.getHolder().get());
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DELPHINIUMS);
				if (EnvironmentalConfig.COMMON.generateWisteriaTrees.get()) {
//					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE);
//					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE_BIG);
				}
			}
			if (DataUtil.matchesKeys(biome, Biomes.DARK_FOREST)) {
				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL.getHolder().get());
			}
		}

		if (event.getCategory() == Biome.BiomeCategory.EXTREME_HILLS)
			spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.YAK.get(), 20, 2, 4));

//		if (event.getCategory() == Biome.BiomeCategory.MUSHROOM)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_MYCELIUM_SPROUTS);

		if (!DataUtil.matchesKeys(biome, Biomes.FROZEN_RIVER) && (event.getCategory() == Biome.BiomeCategory.SWAMP || event.getCategory() == Biome.BiomeCategory.RIVER)) {
			EnvironmentalGeneration.withCattails(generation);
		}

//		if (event.getCategory() == Biome.BiomeCategory.DESERT) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH);
//			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.FENNEC_FOX.get(), 8, 2, 4));
//		}

//		if (event.getCategory() == Biome.BiomeCategory.MESA)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH_MESA);

		if (event.getCategory() == Biome.BiomeCategory.SAVANNA) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_ALLIUM.getHolder().get());
//			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);
		}

		if (event.getCategory() == Biome.BiomeCategory.TAIGA)
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_VIOLET.getHolder().get());

//		if (biome.toString().contains("rosewood") && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);

//		if (event.getCategory() == Biome.BiomeCategory.PLAINS && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_PLAINS);

		if (event.getCategory() == Biome.BiomeCategory.JUNGLE) {
//			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//				generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_JUNGLE);
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_BIRD_OF_PARADISE);

			if (EnvironmentalConfig.COMMON.generateHibiscus.get()) {
				if (DataUtil.matchesKeys(biome, Biomes.JUNGLE))
					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_COOL.getHolder().get());
				if (DataUtil.matchesKeys(biome, Biomes.SPARSE_JUNGLE))
					generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_WARM.getHolder().get());
			}
		}

		if (EnvironmentalConfig.COMMON.limitFarmAnimalSpawns.get())
			removeSpawns(event);
	}

	public static void withMarshFeatures(BiomeGenerationSettingsBuilder generation) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addSwampClayDisk(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addSwampExtraVegetation(generation);
		BiomeDefaultFeatures.addFossilDecoration(generation);

//		if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_MARSH);

		EnvironmentalGeneration.withMudDisks(generation);
		EnvironmentalGeneration.withMarshVegetation(generation);
	}

	public static void withBaseBlossomFeatures(BiomeGenerationSettingsBuilder generation) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		EnvironmentalGeneration.withBlossomVegetation(generation);
	}

	public static void withBlossomWoodsFeatures(BiomeGenerationSettingsBuilder generation) {
		EnvironmentalGeneration.withBaseBlossomFeatures(generation);
		EnvironmentalGeneration.withBlossomWoodsVegetation(generation);
	}

	public static void withBlossomValleysFeatures(BiomeGenerationSettingsBuilder generation) {
		EnvironmentalGeneration.withBaseBlossomFeatures(generation);
		EnvironmentalGeneration.withBlossomValleysVegetation(generation);
	}

	public static void withMushrooms(BiomeGenerationSettingsBuilder generation) {
		if (EnvironmentalConfig.COMMON.generateGiantMushrooms.get()) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
		}
	}

	public static void withMarshMushrooms(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.HUGE_BROWN_MUSHROOM_MARSH);
	}

	public static void withMudDisks(BiomeGenerationSettingsBuilder generation) {
		generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EnvironmentalPlacedFeatures.DISK_MUD.getHolder().get());
	}

	public static void withBlossomVegetation(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_SUGAR_CANE_BLOSSOM);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_TALL_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_BLOSSOM_WOODS);
	}

	public static void withBlossomWoodsVegetation(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_WOODS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_WOODS);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_RED_LOTUS.getHolder().get());
	}

	public static void withBlossomValleysVegetation(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE_BLOSSOM_VALLEYS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_VALLEYS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_VALLEYS);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_VALLEYS);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_WHITE_LOTUS.getHolder().get());
	}

	public static void withCattails(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS);
	}

	public static void withMarshVegetation(BiomeGenerationSettingsBuilder generation) {
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.MARSH_OAK);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUE_ORCHID.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CORNFLOWER.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_DIANTHUS.getHolder().get());
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_WATERLILLY_MARSH);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_MARSH);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_GRASS_MARSH);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS_DENSE);
//		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_MARSH);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.SEAGRASS_MARSH.getHolder().get());
	}

	private static void removeSpawns(BiomeLoadingEvent event) {
		MobSpawnSettingsBuilder spawns = event.getSpawns();
		List<MobSpawnSettings.SpawnerData> entrysToRemove = new ArrayList<>();
		for (MobSpawnSettings.SpawnerData entry : spawns.getSpawner(MobCategory.CREATURE)) {
			if (event.getCategory() != Biome.BiomeCategory.FOREST) {
				if (entry.type == EntityType.PIG || entry.type == EntityType.CHICKEN) {
					entrysToRemove.add(entry);
				}
			}
			if (event.getCategory() != Biome.BiomeCategory.PLAINS) {
				if (entry.type == EntityType.COW || entry.type == EntityType.SHEEP) {
					entrysToRemove.add(entry);
				}
			}
		}
		spawns.getSpawner(MobCategory.CREATURE).removeAll(entrysToRemove);
	}
}
