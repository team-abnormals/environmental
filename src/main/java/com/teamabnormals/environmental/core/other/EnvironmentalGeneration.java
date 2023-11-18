package com.teamabnormals.environmental.core.other;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

import static com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures.*;

public class EnvironmentalGeneration {

	public static void marsh(BiomeGenerationSettings.Builder generation) {
		BiomeDefaultFeatures.addFossilDecoration(generation);
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addSwampClayDisk(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		BiomeDefaultFeatures.addSwampExtraVegetation(generation);
		generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_MARSH.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_BLUE_ORCHID.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_CORNFLOWER.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_DIANTHUS.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_WATERLILY_MARSH.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GRASS_MARSH.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, CATTAILS_DENSE.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_DUCKWEED.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, SEAGRASS_MARSH.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, HUGE_BROWN_MUSHROOM_MARSH.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GIANT_TALL_GRASS_MARSH.getHolder().get());
	}

	public static void blossomWoods(BiomeGenerationSettings.Builder generation, boolean valley) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		generation.addFeature(Decoration.UNDERGROUND_DECORATION, BLOSSOM_WOODS_ROCK.getHolder().get());
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		generation.addFeature(Decoration.VEGETAL_DECORATION, FOREST_FLOWERS.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_SUGAR_CANE_BLOSSOM.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_TULIPS.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GRASS_BLOSSOM_WOODS.getHolder().get());

		if (!valley) {
			generation.addFeature(Decoration.VEGETAL_DECORATION, PINE_TREES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, CHERRY_TREES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_LIGHT_BLOSSOM_WOODS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_RED_LOTUS.getHolder().get());
		} else {
			generation.addFeature(Decoration.VEGETAL_DECORATION, PINE_TREES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, CHERRY_TREES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_LIGHT_BLOSSOM_VALLEYS.getHolder().get());
			generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_WHITE_LOTUS.getHolder().get());
		}
	}

	public static void pineBarrens(BiomeGenerationSettings.Builder generation, boolean snowy) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addFerns(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		generation.addFeature(Decoration.LOCAL_MODIFICATIONS, COARSE_DIRT_ON_STONE.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, COARSE_DIRT.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_PINE_BARRENS.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
		generation.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
		generation.addFeature(Decoration.VEGETAL_DECORATION, snowy ? PATCH_GRASS_SNOWY_PINE_BARRENS.getHolder().get() : PATCH_GRASS_PINE_BARRENS.getHolder().get());
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_CUP_LICHEN_STONE.getHolder().get());
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
	}
}