package com.teamabnormals.environmental.core.registry.datapack;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.block.CartwheelBlock;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.*;
import com.teamabnormals.environmental.common.levelgen.treedecorators.*;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class EnvironmentalConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW = createKey("willow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WEEPING_WILLOW = createKey("weeping_willow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WILLOW = createKey("trees_willow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SWAMP = createKey("trees_swamp");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_OAK = createKey("swamp_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MARSH_OAK = createKey("marsh_oak");

	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_ROCK = createKey("stone_rock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_SLOPES_BOULDER = createKey("pine_slopes_boulder");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PLUM = createKey("plum");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PLUM_BEES_0002 = createKey("plum_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PLUM_BEES_005 = createKey("plum_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PLUM_BEES_005_FALLEN_LEAVES = createKey("plum_bees_005_fallen_leaves");

	public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM = createKey("cheerful_plum");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM_BEES_0002 = createKey("cheerful_plum_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM_BEES_005 = createKey("cheerful_plum_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM_BEES_005_FALLEN_LEAVES = createKey("cheerful_plum_bees_005_fallen_leaves");

	public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM = createKey("moody_plum");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM_BEES_0002 = createKey("moody_plum_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM_BEES_005 = createKey("moody_plum_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM_BEES_005_FALLEN_LEAVES = createKey("moody_plum_bees_005_fallen_leaves");

	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BLOSSOM_WOODS = createKey("trees_blossom_woods");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BLOSSOM_VALLEYS = createKey("trees_blossom_valleys");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LEAVES_BLOSSOM_WOODS = createKey("fallen_leaves_blossom_woods");

	public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_WISTERIA = createKey("white_wisteria");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_WISTERIA_BEES_002 = createKey("white_wisteria_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_WISTERIA_BEES_005 = createKey("white_wisteria_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_WISTERIA = createKey("blue_wisteria");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_WISTERIA_BEES_002 = createKey("blue_wisteria_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_WISTERIA_BEES_005 = createKey("blue_wisteria_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_WISTERIA = createKey("purple_wisteria");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_WISTERIA_BEES_002 = createKey("purple_wisteria_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_WISTERIA_BEES_005 = createKey("purple_wisteria_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_WISTERIA = createKey("pink_wisteria");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_WISTERIA_BEES_002 = createKey("pink_wisteria_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_WISTERIA_BEES_005 = createKey("pink_wisteria_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WISTERIA = createKey("trees_wisteria");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PINE = createKey("pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_BEES_0002 = createKey("pine_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_ON_STONE = createKey("pine_on_stone");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_PINE = createKey("tall_pine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_PINE_WITH_PODZOL = createKey("tall_pine_with_podzol");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PINE_BARRENS = createKey("trees_pine_barrens");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_BARRENS = createKey("trees_old_growth_pine_barrens");

	public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR = createKey("cedar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMPY_CEDAR = createKey("swampy_cedar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_BEES_005 = createKey("cedar_bees_005");

	public static final ResourceKey<ConfiguredFeature<?, ?>> GRAINY_COARSE_DIRT = createKey("grainy_coarse_dirt");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ON_STONE = createKey("coarse_dirt_on_stone");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_COARSE_DIRT_ON_STONE = createKey("small_coarse_dirt_on_stone");

	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_PINE_TREE = createKey("fallen_pine_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TALL_PINE_TREE = createKey("fallen_tall_pine_tree");

	public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE = createKey("dwarf_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE_THICKET = createKey("dwarf_spruce_thicket");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE_SPARSE = createKey("dwarf_spruce_sparse");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN = createKey("patch_cup_lichen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN_SMALL = createKey("patch_cup_lichen_small");

	public static final ResourceKey<ConfiguredFeature<?, ?>> MUDDY_SAND = createKey("muddy_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_BOG_ORE = createKey("cedar_bog_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MUDDY_EDGES = createKey("muddy_edges");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUB_PATCH = createKey("shrub_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CEDAR_SWAMP_FERN = createKey("patch_cedar_swamp_fern");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_LICHEN = createKey("tree_lichen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_LICHEN_UNCOMMON = createKey("tree_lichen_uncommon");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_LICHEN_CEDAR_SWAMP = createKey("tree_lichen_cedar_swamp");

	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ESTUARY_MARIGOLD = createKey("flower_estuary_marigold");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CORNFLOWER = createKey("flower_cornflower");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DIANTHUS = createKey("flower_dianthus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLUEBELL = createKey("flower_bluebell");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLUEBELL_LARGE = createKey("flower_bluebell_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_VIOLET = createKey("flower_violet");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_RED_LOTUS = createKey("flower_red_lotus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_WHITE_LOTUS = createKey("flower_white_lotus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CARTWHEEL = createKey("flower_cartwheel");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BIRD_OF_PARADISE = createKey("flower_bird_of_paradise");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TULIPS = createKey("patch_tulips");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TASSELFLOWER = createKey("patch_tasselflower");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DELPHINIUMS = createKey("patch_delphiniums");

	public static final ResourceKey<ConfiguredFeature<?, ?>> ZEBRA_DAZZLE = createKey("zebra_dazzle");

	public static final ResourceKey<ConfiguredFeature<?, ?>> HIBISCUS_BUSH = createKey("hibiscus_bush");

	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAILS = createKey("cattails");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAILS_DENSE = createKey("cattails_dense");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = createKey("patch_duckweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GIANT_TALL_GRASS = createKey("patch_giant_tall_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BROWN_MUSHROOM = createKey("huge_brown_mushroom");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MUD = createKey("ore_mud");

	public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_SHORT = createKey("bamboo_short");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_SHORT_PODZOL = createKey("bamboo_short_podzol");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_PLUM_LEAVES = createKey("fallen_plum_leaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CHEERFUL_PLUM_LEAVES = createKey("fallen_cheerful_plum_leaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MOODY_PLUM_LEAVES = createKey("fallen_moody_plum_leaves");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MYCELIUM_SPROUTS = createKey("patch_mycelium_sprouts");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PINE_BARRENS_GRASS = createKey("patch_pine_barrens_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_JUNGLE_LARGE_FERN = createKey("patch_jungle_large_fern");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WATERLILY = createKey("patch_waterlily");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SUGAR_CANE = createKey("patch_sugar_cane");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS = createKey("patch_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_FLOWERS = createKey("forest_flowers");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_MID = createKey("seagrass_mid");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<NoiseParameters> noises = context.lookup(Registries.NOISE);

		register(context, WILLOW, Feature.TREE, Configs.WILLOW);
		register(context, WEEPING_WILLOW, EnvironmentalFeatures.WEEPING_WILLOW_TREE.get(), Configs.WEEPING_WILLOW);
		register(context, TREES_WILLOW, EnvironmentalFeatures.WILLOW_TREE_PLACER.get(), NoneFeatureConfiguration.NONE);
		register(context, TREES_SWAMP, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.SWAMP_OAK), 0.1F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.TREES_WILLOW)));
		register(context, SWAMP_OAK, Feature.TREE, Configs.SWAMP_OAK);
		register(context, MARSH_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.33333334F)), placedFeatures.getOrThrow(TreePlacements.OAK_CHECKED)));

		register(context, STONE_ROCK, Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.STONE.defaultBlockState()));
		register(context, PINE_SLOPES_BOULDER, EnvironmentalFeatures.PINE_SLOPES_BOULDER.get(), new BlockStateConfiguration(Blocks.STONE.defaultBlockState()));

		register(context, PLUM, EnvironmentalFeatures.PLUM_TREE.get(), Configs.PLUM);
		register(context, PLUM_BEES_0002, EnvironmentalFeatures.PLUM_TREE.get(), Configs.PLUM_BEES_0002);
		register(context, PLUM_BEES_005, EnvironmentalFeatures.PLUM_TREE.get(), Configs.PLUM_BEES_005);
		register(context, PLUM_BEES_005_FALLEN_LEAVES, EnvironmentalFeatures.PLUM_TREE.get(), Configs.PLUM_BEES_005_FALLEN_LEAVES);

		register(context, CHEERFUL_PLUM, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM);
		register(context, CHEERFUL_PLUM_BEES_0002, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM_BEES_0002);
		register(context, CHEERFUL_PLUM_BEES_005, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM_BEES_005);
		register(context, CHEERFUL_PLUM_BEES_005_FALLEN_LEAVES, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM_BEES_005_FALLEN_LEAVES);

		register(context, MOODY_PLUM, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM);
		register(context, MOODY_PLUM_BEES_0002, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM_BEES_0002);
		register(context, MOODY_PLUM_BEES_005, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM_BEES_005);
		register(context, MOODY_PLUM_BEES_005_FALLEN_LEAVES, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM_BEES_005_FALLEN_LEAVES);

		register(context, TREES_BLOSSOM_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.CHEERFUL_PLUM_BEES_0002), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.MOODY_PLUM_BEES_0002), 0.3F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PLUM_BEES_0002)));
		register(context, TREES_BLOSSOM_VALLEYS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.OAK_BEES_002), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.CHEERFUL_PLUM_BEES_005), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.MOODY_PLUM_BEES_005), 0.3F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PLUM_BEES_005)));

		register(context, FALLEN_PLUM_LEAVES, EnvironmentalFeatures.FALLEN_LEAVES.get(), fallenLeaves(EnvironmentalBlocks.PLUM_LEAF_PILE.get().defaultBlockState(), 4, 3));
		register(context, FALLEN_MOODY_PLUM_LEAVES, EnvironmentalFeatures.FALLEN_LEAVES.get(), fallenLeaves(EnvironmentalBlocks.MOODY_PLUM_LEAF_PILE.get().defaultBlockState(), 4, 3));
		register(context, FALLEN_CHEERFUL_PLUM_LEAVES, EnvironmentalFeatures.FALLEN_LEAVES.get(), fallenLeaves(EnvironmentalBlocks.CHEERFUL_PLUM_LEAF_PILE.get().defaultBlockState(), 4, 3));
		register(context, FALLEN_LEAVES_BLOSSOM_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.FALLEN_CHEERFUL_PLUM_LEAVES), 0.33F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.FALLEN_MOODY_PLUM_LEAVES), 0.3F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.FALLEN_PLUM_LEAVES)));

		register(context, WHITE_WISTERIA, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA);
		register(context, WHITE_WISTERIA_BEES_002, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_002);
		register(context, WHITE_WISTERIA_BEES_005, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_005);
		register(context, BLUE_WISTERIA, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA);
		register(context, BLUE_WISTERIA_BEES_002, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_002);
		register(context, BLUE_WISTERIA_BEES_005, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_005);
		register(context, PURPLE_WISTERIA, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA);
		register(context, PURPLE_WISTERIA_BEES_002, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_002);
		register(context, PURPLE_WISTERIA_BEES_005, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_005);
		register(context, PINK_WISTERIA, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA);
		register(context, PINK_WISTERIA_BEES_002, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_002);
		register(context, PINK_WISTERIA_BEES_005, EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_005);
		register(context, TREES_WISTERIA, EnvironmentalFeatures.MULTI_NOISE_SELECTOR.get(), new BestNoisesSelectorFeatureConfiguration(noises.getOrThrow(EnvironmentalNoiseParameters.WISTERIA_COLOR), 0.6F, List.of(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PINK_WISTERIA_BEES_002), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.BLUE_WISTERIA_BEES_002), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PURPLE_WISTERIA_BEES_002), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.WHITE_WISTERIA_BEES_002))));

		register(context, PINE, EnvironmentalFeatures.PINE_TREE.get(), Configs.PINE);
		register(context, PINE_BEES_0002, EnvironmentalFeatures.PINE_TREE.get(), Configs.PINE_BEES_0002);
		register(context, PINE_ON_STONE, EnvironmentalFeatures.PINE_TREE_ON_STONE.get(), Configs.PINE);
		register(context, TALL_PINE, EnvironmentalFeatures.PINE_TREE.get(), Configs.TALL_PINE);
		register(context, TALL_PINE_WITH_PODZOL, EnvironmentalFeatures.PINE_TREE.get(), Configs.TALL_PINE_WITH_PODZOL);
		register(context, TREES_PINE_BARRENS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.SPRUCE_CHECKED), 0.2F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PINE)));
		register(context, TREES_OLD_GROWTH_PINE_BARRENS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.SPRUCE_CHECKED), 0.15F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.TALL_PINE_WITH_PODZOL)));

		register(context, CEDAR, EnvironmentalFeatures.CEDAR_TREE.get(), Configs.CEDAR);
		register(context, SWAMPY_CEDAR, EnvironmentalFeatures.CEDAR_TREE.get(), Configs.SWAMPY_CEDAR);
		register(context, CEDAR_BEES_005, EnvironmentalFeatures.CEDAR_TREE.get(), Configs.CEDAR_BEES_005);

		register(context, GRAINY_COARSE_DIRT, EnvironmentalFeatures.GRAINY_COARSE_DIRT.get(), new ProbabilityFeatureConfiguration(0.1F));
		register(context, COARSE_DIRT_ON_STONE, EnvironmentalFeatures.COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE);
		register(context, SMALL_COARSE_DIRT_ON_STONE, EnvironmentalFeatures.SMALL_COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE);

		register(context, FALLEN_PINE_TREE, EnvironmentalFeatures.FALLEN_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE);
		register(context, FALLEN_TALL_PINE_TREE, EnvironmentalFeatures.FALLEN_TALL_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE);

		register(context, DWARF_SPRUCE, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(5, 0.6F, true));
		register(context, DWARF_SPRUCE_THICKET, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(12, 0.0F, false));
		register(context, DWARF_SPRUCE_SPARSE, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(3, 0.0F, true));

		register(context, PATCH_CUP_LICHEN, EnvironmentalFeatures.CUP_LICHEN_PATCH.get(), new CupLichenPatchConfiguration(64, 3, 2));
		register(context, PATCH_CUP_LICHEN_SMALL, EnvironmentalFeatures.CUP_LICHEN_PATCH.get(), new CupLichenPatchConfiguration(32, 2, 2));

		register(context, SHRUB_PATCH, EnvironmentalFeatures.SHRUB_PATCH.get(), new ShrubPatchConfiguration(1, true));
		register(context, MUDDY_SAND, EnvironmentalFeatures.MUDDY_SAND.get(), NoneFeatureConfiguration.NONE);
		register(context, CEDAR_BOG_ORE, EnvironmentalFeatures.SUSPICIOUS_MUDDY_SAND.get(), NoneFeatureConfiguration.NONE);
		register(context, MUDDY_EDGES, EnvironmentalFeatures.MUDDY_EDGES.get(), NoneFeatureConfiguration.NONE);
		register(context, TREE_LICHEN, EnvironmentalFeatures.TREE_LICHEN.get(), new TreeLichenConfiguration(1.0F, false));
		register(context, TREE_LICHEN_UNCOMMON, EnvironmentalFeatures.TREE_LICHEN.get(), new TreeLichenConfiguration(0.5F, false));
		register(context, TREE_LICHEN_CEDAR_SWAMP, EnvironmentalFeatures.TREE_LICHEN.get(), new TreeLichenConfiguration(1.0F, true));
		register(context, PATCH_CEDAR_SWAMP_FERN, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.LARGE_FERN.defaultBlockState()).add(Blocks.FERN.defaultBlockState()).build()))));

		register(context, FLOWER_ESTUARY_MARIGOLD, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.ESTUARY_MARIGOLD.get())))));
		register(context, FLOWER_CORNFLOWER, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CORNFLOWER)))));
		register(context, FLOWER_DIANTHUS, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DIANTHUS.get())))));
		register(context, FLOWER_BLUEBELL, Feature.FLOWER, new RandomPatchConfiguration(128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUEBELL.get())))));
		register(context, FLOWER_BLUEBELL_LARGE, EnvironmentalFeatures.LARGE_BLUEBELL_PATCH.get(), NoneFeatureConfiguration.NONE);
		register(context, FLOWER_VIOLET, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.VIOLET.get())))));
		register(context, FLOWER_RED_LOTUS, Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.RED_LOTUS_FLOWER.get())))));
		register(context, FLOWER_WHITE_LOTUS, Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get())))));
		register(context, FLOWER_CARTWHEEL, EnvironmentalFeatures.CARTWHEEL.get(), new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.NORTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.SOUTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.EAST), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.WEST), 1))));
		register(context, FLOWER_BIRD_OF_PARADISE, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BIRD_OF_PARADISE.get())), List.of(), 32));
		register(context, PATCH_TULIPS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.WHITE_TULIP)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.RED_TULIP)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PINK_TULIP)))), PlacementUtils.inlinePlaced(Feature.FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ORANGE_TULIP)))))));
		register(context, PATCH_TASSELFLOWER, EnvironmentalFeatures.TASSELFLOWER_PATCH.get(), NoneFeatureConfiguration.NONE);
		register(context, PATCH_DELPHINIUMS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.PINK_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.PURPLE_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUE_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_DELPHINIUM.get())))))));

		register(context, ZEBRA_DAZZLE, EnvironmentalFeatures.ZEBRA_DAZZLE.get(), NoneFeatureConfiguration.NONE);

		register(context, HIBISCUS_BUSH, EnvironmentalFeatures.HIBISCUS_BUSH.get(), NoneFeatureConfiguration.NONE);

		register(context, CATTAILS, EnvironmentalFeatures.CATTAILS.get(), NoneFeatureConfiguration.NONE);
		register(context, CATTAILS_DENSE, EnvironmentalFeatures.DENSE_CATTAILS.get(), NoneFeatureConfiguration.NONE);
		register(context, PATCH_DUCKWEED, Feature.RANDOM_PATCH, new RandomPatchConfiguration(1024, 8, 5, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DUCKWEED.get())))));
		register(context, PATCH_GIANT_TALL_GRASS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EnvironmentalBlocks.GIANT_TALL_GRASS.get()), 64));
		register(context, HUGE_BROWN_MUSHROOM, Feature.HUGE_BROWN_MUSHROOM, new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, true).setValue(HugeMushroomBlock.DOWN, false)), BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 3));
		register(context, ORE_MUD, Feature.ORE, new OreConfiguration(new TagMatchTest(EnvironmentalBlockTags.MUD_REPLACEABLES), Blocks.MUD.defaultBlockState(), 64));

		register(context, BAMBOO_SHORT, EnvironmentalFeatures.SHORT_BAMBOO.get(), new ProbabilityFeatureConfiguration(0.0F));
		register(context, BAMBOO_SHORT_PODZOL, EnvironmentalFeatures.SHORT_BAMBOO.get(), new ProbabilityFeatureConfiguration(0.2F));

		register(context, PATCH_MYCELIUM_SPROUTS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EnvironmentalBlocks.MYCELIUM_SPROUTS.get()), 32));
		register(context, PATCH_PINE_BARRENS_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.SHORT_GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 2)), 32));
		register(context, PATCH_JUNGLE_LARGE_FERN, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LARGE_FERN))));

		register(context, PATCH_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_PAD)))));
		register(context, PATCH_SUGAR_CANE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 4), BlockStateProvider.simple(Blocks.SUGAR_CANE)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.SUGAR_CANE.defaultBlockState(), BlockPos.ZERO), BlockPredicate.anyOf(BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)))))));
		register(context, PATCH_GRASS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(Blocks.SHORT_GRASS), 32));
		register(context, FOREST_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY)))))));
		register(context, SEAGRASS_MID, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.6F));
	}

	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
	}

	private static FallenLeavesConfiguration fallenLeaves(BlockState leafPileState, int radius, int ySpread) {
		return new FallenLeavesConfiguration(BlockStateProvider.simple(leafPileState.setValue(PipeBlock.DOWN, true)), radius, ySpread);
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Environmental.location(name));
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
		context.register(key, new ConfiguredFeature<>(feature, config));
	}

	public static final class Configs {
		private static final BeehiveDecorator BEEHIVE_0002 = new BeehiveDecorator(0.002F);
		private static final BeehiveDecorator BEEHIVE_002 = new BeehiveDecorator(0.02F);
		private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);

		private static final HangingWisteriaDecorator HANGING_WHITE_WISTERIA = new HangingWisteriaDecorator(0.05F);
		private static final HangingWisteriaDecorator HANGING_BLUE_WISTERIA = new HangingWisteriaDecorator(0.25F);
		private static final HangingWisteriaDecorator HANGING_PURPLE_WISTERIA = new HangingWisteriaDecorator(0.50F);
		private static final HangingWisteriaDecorator HANGING_PINK_WISTERIA = new HangingWisteriaDecorator(0.75F);

		private static final PineconeDecorator PINECONE = new PineconeDecorator(0.05F);

		public static final TreeConfiguration SWAMP_OAK = createStraightBlobTree(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 5, 3, 0, 2).decorators(ImmutableList.of(new LeaveVineDecorator(0.25F))).build();

		public static final TreeConfiguration WILLOW = createStraightBlobTree(EnvironmentalBlocks.WILLOW_LOG.get(), EnvironmentalBlocks.WILLOW_LEAVES.get(), 5, 3, 0, 3).decorators(ImmutableList.of(new LeaveVineDecorator(0.25F), HangingWillowDecorator.INSTANCE)).build();
		public static final TreeConfiguration WEEPING_WILLOW = createCustomTree(EnvironmentalBlocks.WILLOW_LOG.get(), new StraightTrunkPlacer(11, 1, 0), EnvironmentalBlocks.WILLOW_LEAVES.get()).decorators(ImmutableList.of(new LeaveVineDecorator(0.35F), HangingWillowDecorator.INSTANCE)).build();

		public static final TreeConfiguration PLUM = createPlum().build();
		public static final TreeConfiguration PLUM_BEES_0002 = createPlum().decorators(List.of(BEEHIVE_0002, fallenLeavesDecorator(EnvironmentalBlocks.PLUM_LEAF_PILE.get().defaultBlockState()))).build();
		public static final TreeConfiguration PLUM_BEES_005 = createPlum().decorators(List.of(BEEHIVE_005)).build();
		public static final TreeConfiguration PLUM_BEES_005_FALLEN_LEAVES = createPlum().decorators(List.of(BEEHIVE_005, fallenLeavesDecorator(EnvironmentalBlocks.PLUM_LEAF_PILE.get().defaultBlockState()))).build();

		public static final TreeConfiguration CHEERFUL_PLUM = createCheerfulPlum().build();
		public static final TreeConfiguration CHEERFUL_PLUM_BEES_0002 = createCheerfulPlum().decorators(List.of(BEEHIVE_0002, fallenLeavesDecorator(EnvironmentalBlocks.CHEERFUL_PLUM_LEAF_PILE.get().defaultBlockState()))).build();
		public static final TreeConfiguration CHEERFUL_PLUM_BEES_005 = createCheerfulPlum().decorators(List.of(BEEHIVE_005)).build();
		public static final TreeConfiguration CHEERFUL_PLUM_BEES_005_FALLEN_LEAVES = createCheerfulPlum().decorators(List.of(BEEHIVE_005, fallenLeavesDecorator(EnvironmentalBlocks.CHEERFUL_PLUM_LEAF_PILE.get().defaultBlockState()))).build();

		public static final TreeConfiguration MOODY_PLUM = createMoodyPlum().build();
		public static final TreeConfiguration MOODY_PLUM_BEES_0002 = createMoodyPlum().decorators(List.of(BEEHIVE_0002, fallenLeavesDecorator(EnvironmentalBlocks.MOODY_PLUM_LEAF_PILE.get().defaultBlockState()))).build();
		public static final TreeConfiguration MOODY_PLUM_BEES_005 = createMoodyPlum().decorators(List.of(BEEHIVE_005)).build();
		public static final TreeConfiguration MOODY_PLUM_BEES_005_FALLEN_LEAVES = createMoodyPlum().decorators(List.of(BEEHIVE_005, fallenLeavesDecorator(EnvironmentalBlocks.MOODY_PLUM_LEAF_PILE.get().defaultBlockState()))).build();

		public static final TreeConfiguration WHITE_WISTERIA = createWhiteWisteria().build();
		public static final TreeConfiguration WHITE_WISTERIA_BEES_002 = createWhiteWisteria().decorators(List.of(HANGING_WHITE_WISTERIA, BEEHIVE_002)).build();
		public static final TreeConfiguration WHITE_WISTERIA_BEES_005 = createWhiteWisteria().decorators(List.of(HANGING_WHITE_WISTERIA, BEEHIVE_005)).build();

		public static final TreeConfiguration BLUE_WISTERIA = createBlueWisteria().build();
		public static final TreeConfiguration BLUE_WISTERIA_BEES_002 = createBlueWisteria().decorators(List.of(HANGING_BLUE_WISTERIA, BEEHIVE_002)).build();
		public static final TreeConfiguration BLUE_WISTERIA_BEES_005 = createBlueWisteria().decorators(List.of(HANGING_BLUE_WISTERIA, BEEHIVE_005)).build();

		public static final TreeConfiguration PURPLE_WISTERIA = createPurpleWisteria().build();
		public static final TreeConfiguration PURPLE_WISTERIA_BEES_002 = createPurpleWisteria().decorators(List.of(HANGING_PURPLE_WISTERIA, BEEHIVE_002)).build();
		public static final TreeConfiguration PURPLE_WISTERIA_BEES_005 = createPurpleWisteria().decorators(List.of(HANGING_PURPLE_WISTERIA, BEEHIVE_005)).build();

		public static final TreeConfiguration PINK_WISTERIA = createPinkWisteria().build();
		public static final TreeConfiguration PINK_WISTERIA_BEES_002 = createPinkWisteria().decorators(List.of(HANGING_PINK_WISTERIA, BEEHIVE_002)).build();
		public static final TreeConfiguration PINK_WISTERIA_BEES_005 = createPinkWisteria().decorators(List.of(HANGING_PINK_WISTERIA, BEEHIVE_005)).build();

		public static final TreeConfiguration PINE = createPine().decorators(List.of(PINECONE)).build();
		public static final TreeConfiguration PINE_BEES_0002 = createPine().decorators(List.of(PINECONE, BEEHIVE_0002)).build();
		public static final TreeConfiguration TALL_PINE = createTallPine().decorators(List.of(PINECONE)).build();
		public static final TreeConfiguration TALL_PINE_WITH_PODZOL = createTallPine().decorators(List.of(PINECONE, PinePodzolDecorator.INSTANCE)).build();

		public static final TreeConfiguration CEDAR = createCedar(3, 1).decorators(List.of(SuspiciousMuddySandDecorator.INSTANCE)).build();
		public static final TreeConfiguration SWAMPY_CEDAR = createCedar(7, 1).decorators(List.of(CedarPodzolDecorator.INSTANCE)).build();
		public static final TreeConfiguration CEDAR_BEES_005 = createCedar(3, 1).decorators(List.of(BEEHIVE_005, PinePodzolDecorator.INSTANCE)).build();

		private static TreeConfigurationBuilder createPlum() {
			return createCustomTree(EnvironmentalBlocks.PLUM_LOG.get(), new StraightTrunkPlacer(4, 2, 0), EnvironmentalBlocks.PLUM_LEAVES.get());
		}

		private static TreeConfigurationBuilder createCheerfulPlum() {
			return createCustomTree(EnvironmentalBlocks.PLUM_LOG.get(), new StraightTrunkPlacer(4, 1, 0), EnvironmentalBlocks.CHEERFUL_PLUM_LEAVES.get());
		}

		private static TreeConfigurationBuilder createMoodyPlum() {
			return createCustomTree(EnvironmentalBlocks.PLUM_LOG.get(), new StraightTrunkPlacer(3, 1, 0), EnvironmentalBlocks.MOODY_PLUM_LEAVES.get());
		}

		private static TreeConfigurationBuilder createPine() {
			return createCustomTree(EnvironmentalBlocks.PINE_LOG.get(), new StraightTrunkPlacer(11, 3, 1), EnvironmentalBlocks.PINE_LEAVES.get());
		}

		private static TreeConfigurationBuilder createTallPine() {
			return createCustomTree(EnvironmentalBlocks.PINE_LOG.get(), new StraightTrunkPlacer(16, 3, 1), EnvironmentalBlocks.PINE_LEAVES.get());
		}

		private static TreeConfigurationBuilder createCedar(int heightExtraA, int heightExtraB) {
			return createCustomTree(EnvironmentalBlocks.CEDAR_LOG.get(), new StraightTrunkPlacer(13, heightExtraA, heightExtraB), EnvironmentalBlocks.CEDAR_LEAVES.get());
		}

		private static TreeConfigurationBuilder createWhiteWisteria() {
			return createWisteriaTree(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), HANGING_WHITE_WISTERIA);
		}

		private static TreeConfigurationBuilder createBlueWisteria() {
			return createWisteriaTree(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), HANGING_BLUE_WISTERIA);
		}

		private static TreeConfigurationBuilder createPurpleWisteria() {
			return createWisteriaTree(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), HANGING_PURPLE_WISTERIA);
		}

		private static TreeConfigurationBuilder createPinkWisteria() {
			return createWisteriaTree(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), HANGING_PINK_WISTERIA);
		}

		private static TreeConfigurationBuilder createCustomTree(BlockStateProvider logProvider, TrunkPlacer trunkPlacer, BlockStateProvider leavesProvider) {
			return new TreeConfigurationBuilder(logProvider, trunkPlacer, leavesProvider, new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0)).ignoreVines();
		}

		private static TreeConfigurationBuilder createCustomTree(Block log, TrunkPlacer trunkPlacer, Block leaves) {
			return createCustomTree(BlockStateProvider.simple(log), trunkPlacer, BlockStateProvider.simple(leaves));
		}

		private static TreeConfigurationBuilder createCustomTree(BlockStateProvider logProvider, BlockStateProvider leavesProvider) {
			return createCustomTree(logProvider, new StraightTrunkPlacer(0, 0, 0), leavesProvider);
		}

		private static TreeConfigurationBuilder createWisteriaTree(Block leaves, TreeDecorator... decorators) {
			return createCustomTree(EnvironmentalBlocks.WISTERIA_LOG.get(), new StraightTrunkPlacer(2, 1, 0), leaves).decorators(List.of(decorators));
		}

		private static TreeConfigurationBuilder createCustomTree(Block log, Block leaves) {
			return createCustomTree(BlockStateProvider.simple(log), BlockStateProvider.simple(leaves));
		}

		private static TreeDecorator fallenLeavesDecorator(BlockState leafPileState) {
			return new FallenLeavesDecorator(BlockStateProvider.simple(leafPileState.setValue(PipeBlock.DOWN, true)), 4, 3);
		}
		
		private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
			return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
		}
	}
}