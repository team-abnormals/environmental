package com.teamabnormals.environmental.core.registry.datapack;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.levelgen.feature.placement.CedarSwampDarknessPlacement;
import com.teamabnormals.environmental.common.levelgen.feature.placement.CedarSwampTreePlacement;
import com.teamabnormals.environmental.common.levelgen.feature.placement.NoiseDensityPlacement;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class EnvironmentalPlacedFeatures {
	private static final PlacementFilter ON_STONE_PLACEMENT_FILTER = BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.BASE_STONE_OVERWORLD));

	public static final ResourceKey<PlacedFeature> ORE_MUD = createKey("ore_mud");
	public static final ResourceKey<PlacedFeature> SEAGRASS_MARSH = createKey("seagrass_marsh");
	public static final ResourceKey<PlacedFeature> PATCH_WATERLILY_MARSH = createKey("patch_waterlily");
	public static final ResourceKey<PlacedFeature> PATCH_DUCKWEED = createKey("patch_duckweed");
	public static final ResourceKey<PlacedFeature> PATCH_DUCKWEED_SWAMP = createKey("patch_duckweed_swamp");

	public static final ResourceKey<PlacedFeature> FLOWER_ESTUARY_MARIGOLD = createKey("flower_estuary_marigold");
	public static final ResourceKey<PlacedFeature> FLOWER_CORNFLOWER = createKey("flower_cornflower");
	public static final ResourceKey<PlacedFeature> FLOWER_DIANTHUS = createKey("flower_dianthus");
	public static final ResourceKey<PlacedFeature> FLOWER_BLUEBELL = createKey("flower_bluebell");
	public static final ResourceKey<PlacedFeature> FLOWER_BLUEBELL_LARGE = createKey("flower_bluebell_large");
	public static final ResourceKey<PlacedFeature> FLOWER_VIOLET = createKey("flower_violet");
	public static final ResourceKey<PlacedFeature> FLOWER_RED_LOTUS = createKey("flower_red_lotus");
	public static final ResourceKey<PlacedFeature> FLOWER_WHITE_LOTUS = createKey("flower_white_lotus");
	public static final ResourceKey<PlacedFeature> FLOWER_BIRD_OF_PARADISE = createKey("flower_bird_of_paradise");
	public static final ResourceKey<PlacedFeature> PATCH_TULIPS = createKey("patch_tulips");
	public static final ResourceKey<PlacedFeature> FOREST_FLOWERS = createKey("forest_flowers");
	public static final ResourceKey<PlacedFeature> PATCH_TASSELFLOWER = createKey("patch_tasselflower");
	public static final ResourceKey<PlacedFeature> PATCH_DELPHINIUMS = createKey("patch_delphiniums");

	public static final ResourceKey<PlacedFeature> FLOWER_CARTWHEEL = createKey("flower_cartwheel");

	public static final ResourceKey<PlacedFeature> ZEBRA_DAZZLE = createKey("zebra_dazzle");

	public static final ResourceKey<PlacedFeature> HIBISCUS_BUSH = createKey("hibiscus_bush");

	public static final ResourceKey<PlacedFeature> WHITE_WISTERIA_BEES_002 = createKey("white_wisteria_bees_002");
	public static final ResourceKey<PlacedFeature> PINK_WISTERIA_BEES_002 = createKey("pink_wisteria_bees_002");
	public static final ResourceKey<PlacedFeature> PURPLE_WISTERIA_BEES_002 = createKey("purple_wisteria_bees_002");
	public static final ResourceKey<PlacedFeature> BLUE_WISTERIA_BEES_002 = createKey("blue_wisteria_bees_002");
	public static final ResourceKey<PlacedFeature> TREES_WISTERIA = createKey("trees_wisteria");

	public static final ResourceKey<PlacedFeature> TREES_WILLOW = createKey("trees_willow");
	public static final ResourceKey<PlacedFeature> SWAMP_OAK = createKey("swamp_oak");

	public static final ResourceKey<PlacedFeature> TREES_MARSH = createKey("trees_marsh");
	public static final ResourceKey<PlacedFeature> TREES_SWAMP = createKey("trees_swamp");
	public static final ResourceKey<PlacedFeature> HUGE_BROWN_MUSHROOM_MARSH = createKey("huge_brown_mushroom_marsh");

	public static final ResourceKey<PlacedFeature> PLUM_BEES_0002 = createKey("plum_bees_0002");
	public static final ResourceKey<PlacedFeature> CHEERFUL_PLUM_BEES_0002 = createKey("cheerful_plum_bees_0002");
	public static final ResourceKey<PlacedFeature> MOODY_PLUM_BEES_0002 = createKey("moody_plum_bees_0002");

	public static final ResourceKey<PlacedFeature> PLUM_BEES_005 = createKey("plum_bees_005");
	public static final ResourceKey<PlacedFeature> CHEERFUL_PLUM_BEES_005 = createKey("cheerful_plum_bees_005");
	public static final ResourceKey<PlacedFeature> MOODY_PLUM_BEES_005 = createKey("moody_plum_bees_005");

	public static final ResourceKey<PlacedFeature> PLUM_TREES_BLOSSOM_WOODS = createKey("plum_tree_blossom_woods");
	public static final ResourceKey<PlacedFeature> PLUM_TREES_BLOSSOM_VALLEYS = createKey("plum_tree_blossom_valleys");
	public static final ResourceKey<PlacedFeature> PINE_TREES_BLOSSOM_WOODS = createKey("pine_trees_blossom_woods");
	public static final ResourceKey<PlacedFeature> PINE_TREES_BLOSSOM_VALLEYS = createKey("pine_trees_blossom_valleys");

	public static final ResourceKey<PlacedFeature> FALLEN_PLUM_LEAVES = createKey("fallen_plum_leaves");
	public static final ResourceKey<PlacedFeature> FALLEN_CHEERFUL_PLUM_LEAVES = createKey("fallen_cheerful_plum_leaves");
	public static final ResourceKey<PlacedFeature> FALLEN_MOODY_PLUM_LEAVES = createKey("fallen_moody_plum_leaves");

	public static final ResourceKey<PlacedFeature> FALLEN_PLUM_LEAVES_BLOSSOM_WOODS = createKey("fallen_plum_leaves_blossom_woods");
	public static final ResourceKey<PlacedFeature> FALLEN_PLUM_LEAVES_BLOSSOM_VALLEYS = createKey("fallen_plum_leaves_blossom_valleys");

	public static final ResourceKey<PlacedFeature> BLOSSOM_WOODS_ROCK = createKey("blossom_woods_rock");

	public static final ResourceKey<PlacedFeature> PINE = createKey("pine");
	public static final ResourceKey<PlacedFeature> TALL_PINE_WITH_PODZOL = createKey("tall_pine_with_podzol");
	public static final ResourceKey<PlacedFeature> TREES_PINE_BARRENS = createKey("trees_pine_barrens");
	public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_BARRENS = createKey("trees_old_growth_pine_barrens");
	public static final ResourceKey<PlacedFeature> TREES_PINE_BARRENS_ON_STONE = createKey("trees_pine_barrens_on_stone");
	public static final ResourceKey<PlacedFeature> TREES_PINE_SLOPES = createKey("trees_pine_slopes");
	public static final ResourceKey<PlacedFeature> TREES_PINE_RIVER = createKey("trees_pine_river");

	public static final ResourceKey<PlacedFeature> GRAINY_COARSE_DIRT = createKey("grainy_coarse_dirt");
	public static final ResourceKey<PlacedFeature> COARSE_DIRT_ON_STONE = createKey("coarse_dirt_on_stone");
	public static final ResourceKey<PlacedFeature> SMALL_COARSE_DIRT_ON_STONE = createKey("small_coarse_dirt_on_stone");

	public static final ResourceKey<PlacedFeature> FALLEN_PINE_TREE = createKey("fallen_pine_tree");
	public static final ResourceKey<PlacedFeature> FALLEN_TALL_PINE_TREE = createKey("fallen_tall_pine_tree");

	public static final ResourceKey<PlacedFeature> CEDAR = createKey("cedar");
	public static final ResourceKey<PlacedFeature> TREES_CEDAR_SWAMP = createKey("trees_cedar_swamp");
	public static final ResourceKey<PlacedFeature> TREES_CEDAR_SWAMP_EXTRA = createKey("trees_cedar_swamp_extra");
	public static final ResourceKey<PlacedFeature> TREES_CEDAR_RIVER = createKey("trees_cedar_river");

	public static final ResourceKey<PlacedFeature> DWARF_SPRUCE = createKey("dwarf_spruce");
	public static final ResourceKey<PlacedFeature> DWARF_SPRUCE_THICKET = createKey("dwarf_spruce_thicket");
	public static final ResourceKey<PlacedFeature> DWARF_SPRUCE_SPARSE = createKey("dwarf_spruce_sparse");

	public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN = createKey("patch_cup_lichen");
	public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN_TAIGA = createKey("patch_cup_lichen_taiga");
	public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN_SMALL = createKey("patch_cup_lichen_small");

	public static final ResourceKey<PlacedFeature> PINE_SLOPES_ROCK = createKey("pine_slopes_rock");
	public static final ResourceKey<PlacedFeature> PINE_SLOPES_BOULDER = createKey("pine_slopes_boulder");

	public static final ResourceKey<PlacedFeature> MUDDY_SAND = createKey("muddy_sand");
	public static final ResourceKey<PlacedFeature> CEDAR_BOG_ORE = createKey("cedar_bog_ore");
	public static final ResourceKey<PlacedFeature> MUDDY_EDGES = createKey("muddy_edges");
	public static final ResourceKey<PlacedFeature> SHRUB_PATCH = createKey("shrub_patch");
	public static final ResourceKey<PlacedFeature> TREE_LICHEN = createKey("tree_lichen");
	public static final ResourceKey<PlacedFeature> TREE_LICHEN_UNCOMMON = createKey("tree_lichen_uncommon");

	public static final ResourceKey<PlacedFeature> BAMBOO_BLOSSOM_WOODS = createKey("bamboo_blossom_woods");
	public static final ResourceKey<PlacedFeature> BAMBOO_LIGHT_BLOSSOM_WOODS = createKey("bamboo_light_blossom_woods");
	public static final ResourceKey<PlacedFeature> BAMBOO_BLOSSOM_VALLEYS = createKey("bamboo_blossom_valleys");
	public static final ResourceKey<PlacedFeature> BAMBOO_LIGHT_BLOSSOM_VALLEYS = createKey("bamboo_light_blossom_valleys");

	public static final ResourceKey<PlacedFeature> PATCH_GRASS_MARSH = createKey("patch_grass_marsh");
	public static final ResourceKey<PlacedFeature> PATCH_GRASS_BLOSSOM_WOODS = createKey("patch_grass_blossom_woods");
	public static final ResourceKey<PlacedFeature> PATCH_MYCELIUM_SPROUTS = createKey("patch_mycelium_sprouts");
	public static final ResourceKey<PlacedFeature> PATCH_GRASS_PINE_BARRENS = createKey("patch_grass_pine_barrens");
	public static final ResourceKey<PlacedFeature> PATCH_GRASS_SNOWY_PINE_BARRENS = createKey("patch_grass_snowy_pine_barrens");
	public static final ResourceKey<PlacedFeature> PATCH_GRASS_OLD_GROWTH_PINE_BARRENS = createKey("patch_grass_old_growth_pine_barrens");
	public static final ResourceKey<PlacedFeature> PATCH_GRASS_PINE_SLOPES = createKey("patch_grass_pine_slopes");
	public static final ResourceKey<PlacedFeature> PATCH_LARGE_FERN_JUNGLE = createKey("patch_large_fern_jungle");
	public static final ResourceKey<PlacedFeature> PATCH_LARGE_FERN_PINE_BARRENS = createKey("patch_large_fern_pine_barrens");

	public static final ResourceKey<PlacedFeature> PATCH_GIANT_TALL_GRASS_PLAINS = createKey("patch_giant_tall_grass_plains");
	public static final ResourceKey<PlacedFeature> PATCH_GIANT_TALL_GRASS_SAVANNA = createKey("patch_giant_tall_grass_savanna");
	public static final ResourceKey<PlacedFeature> PATCH_GIANT_TALL_GRASS_JUNGLE = createKey("patch_giant_tall_grass_jungle");
	public static final ResourceKey<PlacedFeature> PATCH_GIANT_TALL_GRASS_MARSH = createKey("patch_giant_tall_grass_marsh");

	public static final ResourceKey<PlacedFeature> CATTAILS = createKey("cattails");
	public static final ResourceKey<PlacedFeature> CATTAILS_DENSE = createKey("cattails_dense");
	public static final ResourceKey<PlacedFeature> PATCH_SUGAR_CANE_BLOSSOM = createKey("patch_sugar_cane_blossom");

	public static final ResourceKey<PlacedFeature> PATCH_WATERLILY_CEDAR_RIVER = createKey("patch_waterlily_cedar_river");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<NoiseParameters> noises = context.lookup(Registries.NOISE);

		register(context, ORE_MUD, EnvironmentalConfiguredFeatures.ORE_MUD, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
		register(context, SEAGRASS_MARSH, EnvironmentalConfiguredFeatures.SEAGRASS_MID, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(128), BiomeFilter.biome());
		register(context, PATCH_WATERLILY_MARSH, EnvironmentalConfiguredFeatures.PATCH_WATERLILY, VegetationPlacements.worldSurfaceSquaredWithCount(1));
		register(context, PATCH_DUCKWEED, EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, PATCH_DUCKWEED_SWAMP, EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, FLOWER_ESTUARY_MARIGOLD, EnvironmentalConfiguredFeatures.FLOWER_ESTUARY_MARIGOLD, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_CORNFLOWER, EnvironmentalConfiguredFeatures.FLOWER_CORNFLOWER, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_DIANTHUS, EnvironmentalConfiguredFeatures.FLOWER_DIANTHUS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_BLUEBELL, EnvironmentalConfiguredFeatures.FLOWER_BLUEBELL, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_BLUEBELL_LARGE, EnvironmentalConfiguredFeatures.FLOWER_BLUEBELL_LARGE, RarityFilter.onAverageOnceEvery(256), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_VIOLET, EnvironmentalConfiguredFeatures.FLOWER_VIOLET, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_RED_LOTUS, EnvironmentalConfiguredFeatures.FLOWER_RED_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_WHITE_LOTUS, EnvironmentalConfiguredFeatures.FLOWER_WHITE_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FLOWER_BIRD_OF_PARADISE, EnvironmentalConfiguredFeatures.FLOWER_BIRD_OF_PARADISE, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_TULIPS, EnvironmentalConfiguredFeatures.PATCH_TULIPS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FOREST_FLOWERS, EnvironmentalConfiguredFeatures.FOREST_FLOWERS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)), BiomeFilter.biome());
		register(context, PATCH_TASSELFLOWER, EnvironmentalConfiguredFeatures.PATCH_TASSELFLOWER, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_DELPHINIUMS, EnvironmentalConfiguredFeatures.PATCH_DELPHINIUMS, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 2), 0, 2)), BiomeFilter.biome());

		register(context, FLOWER_CARTWHEEL, EnvironmentalConfiguredFeatures.FLOWER_CARTWHEEL, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, ZEBRA_DAZZLE, EnvironmentalConfiguredFeatures.ZEBRA_DAZZLE, RarityFilter.onAverageOnceEvery(256), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, HIBISCUS_BUSH, EnvironmentalConfiguredFeatures.HIBISCUS_BUSH, RarityFilter.onAverageOnceEvery(256), CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, WHITE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.WHITE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get()));
		register(context, PINK_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.PINK_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get()));
		register(context, PURPLE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.PURPLE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get()));
		register(context, BLUE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.BLUE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get()));
		register(context, TREES_WISTERIA, EnvironmentalConfiguredFeatures.TREES_WISTERIA, VegetationPlacements.treePlacement(new NoiseDensityPlacement(noises.getOrThrow(EnvironmentalNoiseParameters.WISTERIA_DENSITY), 1.25D, 0.5D)));

		register(context, TREES_WILLOW, EnvironmentalConfiguredFeatures.TREES_WILLOW, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.WILLOW_SAPLING.get()));
		register(context, SWAMP_OAK, EnvironmentalConfiguredFeatures.SWAMP_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		register(context, TREES_MARSH, EnvironmentalConfiguredFeatures.MARSH_OAK, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 1)));
		register(context, TREES_SWAMP, EnvironmentalConfiguredFeatures.TREES_SWAMP, PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
		register(context, HUGE_BROWN_MUSHROOM_MARSH, EnvironmentalConfiguredFeatures.HUGE_BROWN_MUSHROOM, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.025F, 1)));

		register(context, PLUM_BEES_0002, EnvironmentalConfiguredFeatures.PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PLUM_SAPLING.get()));
		register(context, CHEERFUL_PLUM_BEES_0002, EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.CHEERFUL_PLUM_SAPLING.get()));
		register(context, MOODY_PLUM_BEES_0002, EnvironmentalConfiguredFeatures.MOODY_PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.MOODY_PLUM_SAPLING.get()));

		register(context, PLUM_BEES_005, EnvironmentalConfiguredFeatures.PLUM_BEES_005_FALLEN_LEAVES, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PLUM_SAPLING.get()));
		register(context, CHEERFUL_PLUM_BEES_005, EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_005_FALLEN_LEAVES, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.CHEERFUL_PLUM_SAPLING.get()));
		register(context, MOODY_PLUM_BEES_005, EnvironmentalConfiguredFeatures.MOODY_PLUM_BEES_005_FALLEN_LEAVES, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.MOODY_PLUM_SAPLING.get()));

		register(context, PLUM_TREES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.TREES_BLOSSOM_WOODS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1), EnvironmentalBlocks.PLUM_SAPLING.get()));
		register(context, PLUM_TREES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.TREES_BLOSSOM_VALLEYS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.2F, 2), EnvironmentalBlocks.PLUM_SAPLING.get()));
		register(context, PINE_TREES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.05F, 7), EnvironmentalBlocks.PLUM_SAPLING.get()));
		register(context, PINE_TREES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 3), EnvironmentalBlocks.PLUM_SAPLING.get()));

		register(context, FALLEN_PLUM_LEAVES, EnvironmentalConfiguredFeatures.FALLEN_PLUM_LEAVES);
		register(context, FALLEN_CHEERFUL_PLUM_LEAVES, EnvironmentalConfiguredFeatures.FALLEN_CHEERFUL_PLUM_LEAVES);
		register(context, FALLEN_MOODY_PLUM_LEAVES, EnvironmentalConfiguredFeatures.FALLEN_MOODY_PLUM_LEAVES);

		register(context, FALLEN_PLUM_LEAVES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.FALLEN_LEAVES_BLOSSOM_WOODS, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, FALLEN_PLUM_LEAVES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.FALLEN_LEAVES_BLOSSOM_WOODS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, BLOSSOM_WOODS_ROCK, EnvironmentalConfiguredFeatures.STONE_ROCK, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, PINE, EnvironmentalConfiguredFeatures.PINE, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PINE_SAPLING.get()));
		register(context, TALL_PINE_WITH_PODZOL, EnvironmentalConfiguredFeatures.TALL_PINE_WITH_PODZOL, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.PINE_SAPLING.get()));
		register(context, TREES_PINE_BARRENS, EnvironmentalConfiguredFeatures.TREES_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(14, 0.1F, 1)));
		register(context, TREES_OLD_GROWTH_PINE_BARRENS, EnvironmentalConfiguredFeatures.TREES_OLD_GROWTH_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(22, 0.1F, 1)));
		register(context, TREES_PINE_BARRENS_ON_STONE, EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1), ON_STONE_PLACEMENT_FILTER));
		register(context, TREES_PINE_SLOPES, EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), ON_STONE_PLACEMENT_FILTER));
		register(context, TREES_PINE_RIVER, EnvironmentalConfiguredFeatures.TREES_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(7, 0.1F, 1)));

		register(context, CEDAR, EnvironmentalConfiguredFeatures.CEDAR, PlacementUtils.filteredByBlockSurvival(EnvironmentalBlocks.CEDAR_SAPLING.get()));
		register(context, TREES_CEDAR_SWAMP, EnvironmentalConfiguredFeatures.SWAMPY_CEDAR, ImmutableList.<PlacementModifier>builder().add(CedarSwampTreePlacement.INSTANCE).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome()).build());
		register(context, TREES_CEDAR_SWAMP_EXTRA, EnvironmentalConfiguredFeatures.SWAMPY_CEDAR, ImmutableList.<PlacementModifier>builder().add(CedarSwampDarknessPlacement.INSTANCE).add(BiomeFilter.biome()).build());
		register(context, TREES_CEDAR_RIVER, EnvironmentalConfiguredFeatures.CEDAR_BEES_005, treePlacement(PlacementUtils.countExtra(7, 0.1F, 1)));

		register(context, GRAINY_COARSE_DIRT, EnvironmentalConfiguredFeatures.GRAINY_COARSE_DIRT, CountPlacement.of(56), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, COARSE_DIRT_ON_STONE, EnvironmentalConfiguredFeatures.COARSE_DIRT_ON_STONE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, SMALL_COARSE_DIRT_ON_STONE, EnvironmentalConfiguredFeatures.SMALL_COARSE_DIRT_ON_STONE, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, FALLEN_PINE_TREE, EnvironmentalConfiguredFeatures.FALLEN_PINE_TREE, RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, FALLEN_TALL_PINE_TREE, EnvironmentalConfiguredFeatures.FALLEN_TALL_PINE_TREE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, DWARF_SPRUCE, EnvironmentalConfiguredFeatures.DWARF_SPRUCE, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, DWARF_SPRUCE_THICKET, EnvironmentalConfiguredFeatures.DWARF_SPRUCE_THICKET, RarityFilter.onAverageOnceEvery(128), CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, DWARF_SPRUCE_SPARSE, EnvironmentalConfiguredFeatures.DWARF_SPRUCE_SPARSE, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, PATCH_CUP_LICHEN, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN, RarityFilter.onAverageOnceEvery(22), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_CUP_LICHEN_TAIGA, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN, RarityFilter.onAverageOnceEvery(22), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_CUP_LICHEN_SMALL, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_SMALL, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, SHRUB_PATCH, EnvironmentalConfiguredFeatures.SHRUB_PATCH, new NoiseDensityPlacement(noises.getOrThrow(EnvironmentalNoiseParameters.SHRUB_DENSITY), 16.0F, 0.5F), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, MUDDY_SAND, EnvironmentalConfiguredFeatures.MUDDY_SAND, BiomeFilter.biome());
		register(context, CEDAR_BOG_ORE, EnvironmentalConfiguredFeatures.CEDAR_BOG_ORE, commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(69))));
		register(context, MUDDY_EDGES, EnvironmentalConfiguredFeatures.MUDDY_EDGES, BiomeFilter.biome());
		register(context, TREE_LICHEN, EnvironmentalConfiguredFeatures.TREE_LICHEN, BiomeFilter.biome());
		register(context, TREE_LICHEN_UNCOMMON, EnvironmentalConfiguredFeatures.TREE_LICHEN_UNCOMMON, BiomeFilter.biome());

		register(context, PINE_SLOPES_ROCK, EnvironmentalConfiguredFeatures.STONE_ROCK, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, PINE_SLOPES_BOULDER, EnvironmentalConfiguredFeatures.PINE_SLOPES_BOULDER, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		register(context, BAMBOO_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.BAMBOO_SHORT_PODZOL, NoiseBasedCountPlacement.of(11, 5.0D, 0.2D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, BAMBOO_LIGHT_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.BAMBOO_SHORT_PODZOL, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, BAMBOO_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.BAMBOO_SHORT, NoiseBasedCountPlacement.of(5, 5.0D, 0.2D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, BAMBOO_LIGHT_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.BAMBOO_SHORT, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, PATCH_GRASS_MARSH, EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(5));
		register(context, PATCH_GRASS_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(12));
		register(context, PATCH_MYCELIUM_SPROUTS, EnvironmentalConfiguredFeatures.PATCH_MYCELIUM_SPROUTS, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, PATCH_GRASS_PINE_BARRENS, EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(2));
		register(context, PATCH_GRASS_SNOWY_PINE_BARRENS, EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(context, PATCH_GRASS_OLD_GROWTH_PINE_BARRENS, EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(8));
		register(context, PATCH_GRASS_PINE_SLOPES, EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(26));
		register(context, PATCH_LARGE_FERN_JUNGLE, EnvironmentalConfiguredFeatures.PATCH_JUNGLE_LARGE_FERN, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_LARGE_FERN_PINE_BARRENS, EnvironmentalConfiguredFeatures.PATCH_JUNGLE_LARGE_FERN, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, PATCH_GIANT_TALL_GRASS_PLAINS, EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_GIANT_TALL_GRASS_SAVANNA, EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_GIANT_TALL_GRASS_JUNGLE, EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_GIANT_TALL_GRASS_MARSH, EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, CATTAILS, EnvironmentalConfiguredFeatures.CATTAILS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, CATTAILS_DENSE, EnvironmentalConfiguredFeatures.CATTAILS_DENSE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(context, PATCH_SUGAR_CANE_BLOSSOM, EnvironmentalConfiguredFeatures.PATCH_SUGAR_CANE, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		register(context, PATCH_WATERLILY_CEDAR_RIVER, VegetationFeatures.PATCH_WATERLILY, new NoiseDensityPlacement(noises.getOrThrow(EnvironmentalNoiseParameters.CEDAR_RIVER_WATERLILY_DENSITY), 1.0F, 1.333F), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	}

	private static ImmutableList<PlacementModifier> treePlacement(PlacementModifier modifier, PlacementModifier... extraModifiers) {
		return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome()).add(extraModifiers).build();
	}

	private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
		return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
		return orePlacement(CountPlacement.of(count), heightRange);
	}

	public static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Environmental.location(name));
	}

	public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(feature), modifiers));
	}

	public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
		register(context, key, feature, List.of(modifiers));
	}
}