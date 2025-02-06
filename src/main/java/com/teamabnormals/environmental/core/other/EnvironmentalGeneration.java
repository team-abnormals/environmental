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
		generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_MARSH);
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_BLUE_ORCHID);
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_CORNFLOWER);
		generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_DIANTHUS);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_WATERLILY_MARSH);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GRASS_MARSH);
		generation.addFeature(Decoration.VEGETAL_DECORATION, CATTAILS_DENSE);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_DUCKWEED);
		generation.addFeature(Decoration.VEGETAL_DECORATION, SEAGRASS_MARSH);
		generation.addFeature(Decoration.VEGETAL_DECORATION, HUGE_BROWN_MUSHROOM_MARSH);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GIANT_TALL_GRASS_MARSH);
	}

	public static void blossomWoods(BiomeGenerationSettings.Builder generation, boolean valley) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addSavannaGrass(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		generation.addFeature(Decoration.UNDERGROUND_DECORATION, BLOSSOM_WOODS_ROCK);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		BiomeDefaultFeatures.addDefaultMushrooms(generation);
		generation.addFeature(Decoration.VEGETAL_DECORATION, FOREST_FLOWERS);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_SUGAR_CANE_BLOSSOM);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_TULIPS);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GRASS_BLOSSOM_WOODS);

		if (!valley) {
			generation.addFeature(Decoration.VEGETAL_DECORATION, PINE_TREES_BLOSSOM_WOODS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, PLUM_TREES_BLOSSOM_WOODS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_PLUM_LEAVES_BLOSSOM_WOODS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_BLOSSOM_WOODS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_LIGHT_BLOSSOM_WOODS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_RED_LOTUS);
		} else {
			generation.addFeature(Decoration.VEGETAL_DECORATION, PINE_TREES_BLOSSOM_VALLEYS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, PLUM_TREES_BLOSSOM_VALLEYS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_PLUM_LEAVES_BLOSSOM_VALLEYS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_BLOSSOM_VALLEYS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, BAMBOO_LIGHT_BLOSSOM_VALLEYS);
			generation.addFeature(Decoration.VEGETAL_DECORATION, FLOWER_WHITE_LOTUS);
		}
	}

	public static void pineBarrens(BiomeGenerationSettings.Builder generation, boolean snowy, boolean oldGrowth) {
		OverworldBiomes.globalOverworldGeneration(generation);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_CUP_LICHEN_NOISE);
		if (oldGrowth)
			BiomeDefaultFeatures.addFerns(generation);
		else
			generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_LARGE_FERN_PINE_BARRENS);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		generation.addFeature(Decoration.LOCAL_MODIFICATIONS, SMALL_COARSE_DIRT_ON_STONE);
		if (oldGrowth) {
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_TALL_PINE_TREE);
		} else {
			generation.addFeature(Decoration.VEGETAL_DECORATION, GRAINY_COARSE_DIRT);
			generation.addFeature(Decoration.VEGETAL_DECORATION, FALLEN_PINE_TREE);
		}
		generation.addFeature(Decoration.VEGETAL_DECORATION, oldGrowth ? TREES_OLD_GROWTH_PINE_BARRENS : TREES_PINE_BARRENS);
		generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_PINE_BARRENS_ON_STONE);
		generation.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
		generation.addFeature(Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
		generation.addFeature(Decoration.VEGETAL_DECORATION, DWARF_SPRUCE);
		generation.addFeature(Decoration.VEGETAL_DECORATION, DWARF_SPRUCE_DENSE);
		if (oldGrowth)
			generation.addFeature(Decoration.VEGETAL_DECORATION, snowy ? PATCH_GRASS_PINE_BARRENS : PATCH_GRASS_OLD_GROWTH_PINE_BARRENS);
		else
			generation.addFeature(Decoration.VEGETAL_DECORATION, snowy ? PATCH_GRASS_SNOWY_PINE_BARRENS : PATCH_GRASS_PINE_BARRENS);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_CUP_LICHEN_SMALL);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_CUP_LICHEN_STONE);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
	}

	public static void pineSlopes(BiomeGenerationSettings.Builder generation) {
		OverworldBiomes.globalOverworldGeneration(generation);
		BiomeDefaultFeatures.addDefaultOres(generation);
		BiomeDefaultFeatures.addDefaultSoftDisks(generation);
		generation.addFeature(Decoration.UNDERGROUND_DECORATION, PINE_SLOPES_BOULDER);
		generation.addFeature(Decoration.UNDERGROUND_DECORATION, PINE_SLOPES_ROCK);
		BiomeDefaultFeatures.addDefaultFlowers(generation);
		generation.addFeature(Decoration.LOCAL_MODIFICATIONS, COARSE_DIRT_ON_STONE);
		generation.addFeature(Decoration.VEGETAL_DECORATION, TREES_PINE_SLOPES);
		generation.addFeature(Decoration.VEGETAL_DECORATION, PATCH_GRASS_PINE_SLOPES);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
		BiomeDefaultFeatures.addExtraEmeralds(generation);
		BiomeDefaultFeatures.addInfestedStone(generation);
	}
}