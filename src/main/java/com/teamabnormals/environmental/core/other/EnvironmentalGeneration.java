package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class EnvironmentalGeneration {

	public static void marsh(BiomeGenerationSettings.Builder generation) {
		BiomeDefaultFeatures.addFossilDecoration(generation);
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addSwampClayDisk(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addSwampExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_BLUE_ORCHID.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_CORNFLOWER.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_DIANTHUS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_WATERLILY_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GRASS_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CATTAILS_DENSE.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_DUCKWEED.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.SEAGRASS_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.HUGE_BROWN_MUSHROOM_MARSH.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_MARSH.getHolder().get());
	}

	public static void blossomWoods(BiomeGenerationSettings.Builder generation, boolean valley) {
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

		if (!valley) {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CHERRY_TREES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BIRCH_TREES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_LIGHT_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_RED_LOTUS.getHolder().get());
		} else {
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.CHERRY_TREES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BIRCH_TREES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.BAMBOO_LIGHT_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.FLOWER_WHITE_LOTUS.getHolder().get());
		}
	}

	public static void pinelands(BiomeGenerationSettings.Builder generation) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addFerns(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.PATCH_GRASS_PINELANDS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.TREES_PINELANDS.getHolder().get());
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EnvironmentalPlacedFeatures.COARSE_DIRT_PATCH.getHolder().get());
	}
}