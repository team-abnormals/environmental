package com.teamabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.block.CartwheelBlock;
import com.teamabnormals.environmental.common.levelgen.feature.*;
import com.teamabnormals.environmental.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import com.teamabnormals.environmental.common.levelgen.treedecorators.HangingWillowDecorator;
import com.teamabnormals.environmental.common.levelgen.treedecorators.HangingWisteriaDecorator;
import com.teamabnormals.environmental.common.levelgen.treedecorators.PineconeDecorator;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.*;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Environmental.MOD_ID);
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> HIBISCUS_BUSH = FEATURES.register("hibiscus_bush", () -> new HibiscusBushFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> TASSELFLOWER_PATCH = FEATURES.register("tasselflower_patch", () -> new TasselflowerPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> LARGE_BLUEBELL_PATCH = FEATURES.register("large_bluebell_patch", () -> new LargeBluebellPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> GRAINY_COARSE_DIRT = FEATURES.register("grainy_coarse_dirt", () -> new GrainyCoarseDirtFeature(ProbabilityFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> COARSE_DIRT_ON_STONE = FEATURES.register("coarse_dirt_on_stone", () -> new CoarseDirtOnStoneFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SMALL_COARSE_DIRT_ON_STONE = FEATURES.register("small_coarse_dirt_on_stone", () -> new SmallCoarseDirtOnStoneFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<BlockStateConfiguration>> PINE_SLOPES_BOULDER = FEATURES.register("pine_slopes_boulder", () -> new PineSlopesBoulderFeature(BlockStateConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_PINE_TREE = FEATURES.register("fallen_pine_tree", () -> new FallenPineTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_TALL_PINE_TREE = FEATURES.register("fallen_tall_pine_tree", () -> new FallenTallPineTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<SimpleBlockConfiguration>> CARTWHEEL = FEATURES.register("cartwheel", () -> new CartwheelFeature(SimpleBlockConfiguration.CODEC));
	public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> SHORT_BAMBOO = FEATURES.register("short_bamboo", () -> new ShortBambooFeature(ProbabilityFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CUP_LICHEN_PATCH = FEATURES.register("cup_lichen_patch", () -> new CupLichenPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SMALL_CUP_LICHEN_PATCH = FEATURES.register("small_cup_lichen_patch", () -> new SmallCupLichenPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> STONE_CUP_LICHEN_PATCH = FEATURES.register("stone_cup_lichen_patch", () -> new StoneCupLichenPatchFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<TreeConfiguration>> WEEPING_WILLOW_TREE = FEATURES.register("weeping_willow_tree", () -> new WeepingWillowTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> CHERRY_TREE = FEATURES.register("cherry_tree", () -> new CherryTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> PINE_TREE = FEATURES.register("pine_tree", () -> new PineTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> PINE_TREE_ON_STONE = FEATURES.register("pine_tree_on_stone", () -> new PineTreeOnStoneFeature(TreeConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> WILLOW_TREE_PLACER = FEATURES.register("willow_tree_placer", () -> new WillowTreePlacerFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZEBRA_DAZZLE = FEATURES.register("zebra_dazzle", () -> new ZebraDazzleFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WILLOW_LEAVES = TREE_DECORATORS.register("hanging_willow_leaves", () -> new TreeDecoratorType<>(HangingWillowDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WISTERIA_LEAVES = TREE_DECORATORS.register("hanging_wisteria_leaves", () -> new TreeDecoratorType<>(HangingWisteriaDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>> PINECONE = TREE_DECORATORS.register("pinecone", () -> new TreeDecoratorType<>(PineconeDecorator.CODEC));

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

		public static final TreeConfiguration CHERRY = createCherry().build();
		public static final TreeConfiguration CHERRY_BEES_0002 = createCherry().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration CHERRY_BEES_005 = createCherry().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration CHEERFUL_CHERRY = createCheerfulCherry().build();
		public static final TreeConfiguration CHEERFUL_CHERRY_BEES_0002 = createCheerfulCherry().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration CHEERFUL_CHERRY_BEES_005 = createCheerfulCherry().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration MOODY_CHERRY = createMoodyCherry().build();
		public static final TreeConfiguration MOODY_CHERRY_BEES_0002 = createMoodyCherry().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration MOODY_CHERRY_BEES_005 = createMoodyCherry().decorators(List.of(BEEHIVE_005)).build();

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

		private static TreeConfigurationBuilder createCherry() {
			return createCustomTree(EnvironmentalBlocks.CHERRY_LOG.get(), new StraightTrunkPlacer(4, 2, 0), EnvironmentalBlocks.CHERRY_LEAVES.get());
		}

		private static TreeConfigurationBuilder createCheerfulCherry() {
			return createCustomTree(EnvironmentalBlocks.CHERRY_LOG.get(), new StraightTrunkPlacer(4, 1, 0), EnvironmentalBlocks.CHEERFUL_CHERRY_LEAVES.get());
		}

		private static TreeConfigurationBuilder createMoodyCherry() {
			return createCustomTree(EnvironmentalBlocks.CHERRY_LOG.get(), new StraightTrunkPlacer(3, 1, 0), EnvironmentalBlocks.MOODY_CHERRY_LEAVES.get());
		}

		private static TreeConfigurationBuilder createPine() {
			return createCustomTree(EnvironmentalBlocks.PINE_LOG.get(), new StraightTrunkPlacer(11, 3, 1), EnvironmentalBlocks.PINE_LEAVES.get());
		}

		private static TreeConfigurationBuilder createTallPine() {
			return createCustomTree(EnvironmentalBlocks.PINE_LOG.get(), new StraightTrunkPlacer(16, 3, 1), EnvironmentalBlocks.PINE_LEAVES.get());
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

		private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
			return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
		}
	}

	public static final class EnvironmentalConfiguredFeatures {
		public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Environmental.MOD_ID);

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WILLOW = register("willow", () -> new ConfiguredFeature<>(Feature.TREE, Configs.WILLOW));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WEEPING_WILLOW = register("weeping_willow", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WEEPING_WILLOW_TREE.get(), Configs.WEEPING_WILLOW));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> TREES_WILLOW = register("trees_willow", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WILLOW_TREE_PLACER.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SWAMP = register("trees_swamp", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(EnvironmentalPlacedFeatures.SWAMP_OAK.getHolder().get(), 0.1F)), EnvironmentalPlacedFeatures.TREES_WILLOW.getHolder().get())));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> SWAMP_OAK = register("swamp_oak", () -> new ConfiguredFeature<>(Feature.TREE, Configs.SWAMP_OAK));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> MARSH_OAK = register("marsh_oak", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.33333334F)), TreePlacements.OAK_CHECKED)));

		public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> STONE_ROCK = register("stone_rock", () -> new ConfiguredFeature<>(Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.STONE.defaultBlockState())));
		public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> PINE_SLOPES_BOULDER = register("pine_slopes_boulder", () -> new ConfiguredFeature<>(EnvironmentalFeatures.PINE_SLOPES_BOULDER.get(), new BlockStateConfiguration(Blocks.STONE.defaultBlockState())));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHERRY = register("cherry", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHERRY));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BEES_0002 = register("cherry_bees_0002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHERRY_BEES_0002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BEES_005 = register("cherry_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHERRY_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHEERFUL_CHERRY = register("cheerful_cherry", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHEERFUL_CHERRY));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHEERFUL_CHERRY_BEES_0002 = register("cheerful_cherry_bees_0002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHEERFUL_CHERRY_BEES_0002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHEERFUL_CHERRY_BEES_005 = register("cheerful_cherry_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHEERFUL_CHERRY_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MOODY_CHERRY = register("moody_cherry", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.MOODY_CHERRY));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MOODY_CHERRY_BEES_0002 = register("moody_cherry_bees_0002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.MOODY_CHERRY_BEES_0002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MOODY_CHERRY_BEES_005 = register("moody_cherry_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.MOODY_CHERRY_BEES_005));

		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BLOSSOM_WOODS = register("trees_blossom_woods", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.OAK_BEES_0002, 0.05F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.CHEERFUL_CHERRY_BEES_0002.getHolder().get(), 0.3F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.MOODY_CHERRY_BEES_0002.getHolder().get(), 0.3F)), EnvironmentalPlacedFeatures.CHERRY_BEES_0002.getHolder().get())));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BLOSSOM_VALLEYS = register("trees_blossom_valleys", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.OAK_BEES_002, 0.05F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.CHEERFUL_CHERRY_BEES_005.getHolder().get(), 0.3F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.MOODY_CHERRY_BEES_005.getHolder().get(), 0.3F)), EnvironmentalPlacedFeatures.CHERRY_BEES_005.getHolder().get())));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WHITE_WISTERIA = register("white_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WHITE_WISTERIA_BEES_002 = register("white_wisteria_bees_002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WHITE_WISTERIA_BEES_005 = register("white_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BLUE_WISTERIA = register("blue_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BLUE_WISTERIA_BEES_002 = register("blue_wisteria_bees_002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BLUE_WISTERIA_BEES_005 = register("blue_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PURPLE_WISTERIA = register("purple_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PURPLE_WISTERIA_BEES_002 = register("purple_wisteria_bees_002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PURPLE_WISTERIA_BEES_005 = register("purple_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINK_WISTERIA = register("pink_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINK_WISTERIA_BEES_002 = register("pink_wisteria_bees_002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINK_WISTERIA_BEES_005 = register("pink_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_WISTERIA = register("trees_wisteria", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(EnvironmentalPlacedFeatures.PINK_WISTERIA_BEES_002.getHolder().get(), 0.25F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.BLUE_WISTERIA_BEES_002.getHolder().get(), 0.25F), new WeightedPlacedFeature(EnvironmentalPlacedFeatures.PURPLE_WISTERIA_BEES_002.getHolder().get(), 0.25F)), EnvironmentalPlacedFeatures.WHITE_WISTERIA_BEES_002.getHolder().get())));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINE = register("pine", () -> new ConfiguredFeature<>(EnvironmentalFeatures.PINE_TREE.get(), Configs.PINE));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINE_BEES_0002 = register("pine_bees_0002", () -> new ConfiguredFeature<>(EnvironmentalFeatures.PINE_TREE.get(), Configs.PINE_BEES_0002));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINE_ON_STONE = register("pine_on_stone", () -> new ConfiguredFeature<>(EnvironmentalFeatures.PINE_TREE_ON_STONE.get(), Configs.PINE));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> TALL_PINE = register("tall_pine", () -> new ConfiguredFeature<>(EnvironmentalFeatures.PINE_TREE.get(), Configs.TALL_PINE));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_PINE_BARRENS = register("trees_pine_barrens", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.SPRUCE_CHECKED, 0.18F)), EnvironmentalPlacedFeatures.PINE.getHolder().get())));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_PINE_BARRENS = register("trees_old_growth_pine_barrens", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(TreePlacements.SPRUCE_CHECKED, 0.15F)), EnvironmentalPlacedFeatures.TALL_PINE.getHolder().get())));

		public static final RegistryObject<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> GRAINY_COARSE_DIRT = register("grainy_coarse_dirt", () -> new ConfiguredFeature<>(EnvironmentalFeatures.GRAINY_COARSE_DIRT.get(), new ProbabilityFeatureConfiguration(0.1F)));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> COARSE_DIRT_ON_STONE = register("coarse_dirt_on_stone", () -> new ConfiguredFeature<>(EnvironmentalFeatures.COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> SMALL_COARSE_DIRT_ON_STONE = register("small_coarse_dirt_on_stone", () -> new ConfiguredFeature<>(EnvironmentalFeatures.SMALL_COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_PINE_TREE = register("fallen_pine_tree", () -> new ConfiguredFeature<>(EnvironmentalFeatures.FALLEN_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_TALL_PINE_TREE = register("fallen_tall_pine_tree", () -> new ConfiguredFeature<>(EnvironmentalFeatures.FALLEN_TALL_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_CUP_LICHEN = register("patch_cup_lichen", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_CUP_LICHEN_SMALL = register("patch_cup_lichen_small", () -> new ConfiguredFeature<>(EnvironmentalFeatures.SMALL_CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_CUP_LICHEN_STONE = register("patch_cup_lichen_stone", () -> new ConfiguredFeature<>(EnvironmentalFeatures.STONE_CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> PATCH_CUP_LICHEN_NOISE = register("patch_cup_lichen_noise", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(PATCH_CUP_LICHEN_SMALL.getHolder().get()), 0.8F)), PlacementUtils.inlinePlaced(PATCH_CUP_LICHEN.getHolder().get()))));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BLUE_ORCHID = register("flower_blue_orchid", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BLUE_ORCHID))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_CORNFLOWER = register("flower_cornflower", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CORNFLOWER))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_DIANTHUS = register("flower_dianthus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DIANTHUS.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BLUEBELL = register("flower_bluebell", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUEBELL.get()))))));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FLOWER_BLUEBELL_LARGE = register("flower_bluebell_large", () -> new ConfiguredFeature<>(EnvironmentalFeatures.LARGE_BLUEBELL_PATCH.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_VIOLET = register("flower_violet", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.VIOLET.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_RED_LOTUS = register("flower_red_lotus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.RED_LOTUS_FLOWER.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_WHITE_LOTUS = register("flower_white_lotus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get()))))));
		public static final RegistryObject<ConfiguredFeature<SimpleBlockConfiguration, ?>> FLOWER_CARTWHEEL = register("flower_cartwheel", () -> new ConfiguredFeature<>(CARTWHEEL.get(), new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.NORTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.SOUTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.EAST), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.WEST), 1)))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BIRD_OF_PARADISE = register("flower_bird_of_paradise", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BIRD_OF_PARADISE.get())), List.of(), 32)));
		public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> PATCH_TULIPS = register("patch_tulips", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.WHITE_TULIP)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.RED_TULIP)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PINK_TULIP)))), PlacementUtils.inlinePlaced(Feature.FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ORANGE_TULIP))))))));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_TASSELFLOWER = register("patch_tasselflower", () -> new ConfiguredFeature<>(EnvironmentalFeatures.TASSELFLOWER_PATCH.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> PATCH_DELPHINIUMS = register("patch_delphiniums", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.PINK_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.PURPLE_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUE_DELPHINIUM.get())))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_DELPHINIUM.get()))))))));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> ZEBRA_DAZZLE = register("zebra_dazzle", () -> new ConfiguredFeature<>(EnvironmentalFeatures.ZEBRA_DAZZLE.get(), NoneFeatureConfiguration.NONE));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> HIBISCUS_BUSH = register("hibiscus_bush", () -> new ConfiguredFeature<>(EnvironmentalFeatures.HIBISCUS_BUSH.get(), NoneFeatureConfiguration.NONE));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> CATTAILS = register("cattails", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CATTAILS.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> CATTAILS_DENSE = register("cattails_dense", () -> new ConfiguredFeature<>(EnvironmentalFeatures.DENSE_CATTAILS.get(), NoneFeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_DUCKWEED = register("patch_duckweed", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(1024, 8, 5, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DUCKWEED.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_GIANT_TALL_GRASS = register("patch_giant_tall_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EnvironmentalBlocks.GIANT_TALL_GRASS.get()), 64)));
		public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom", () -> new ConfiguredFeature<>(Feature.HUGE_BROWN_MUSHROOM, new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.UP, true).setValue(HugeMushroomBlock.DOWN, false)), BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 3)));
		public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ORE_MUD = register("ore_mud", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new TagMatchTest(EnvironmentalBlockTags.MUD_REPLACEABLES), Blocks.MUD.defaultBlockState(), 64)));

		public static final RegistryObject<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> BAMBOO_SHORT = register("bamboo_short", () -> new ConfiguredFeature<>(EnvironmentalFeatures.SHORT_BAMBOO.get(), new ProbabilityFeatureConfiguration(0.0F)));
		public static final RegistryObject<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> BAMBOO_SHORT_PODZOL = register("bamboo_short_podzol", () -> new ConfiguredFeature<>(EnvironmentalFeatures.SHORT_BAMBOO.get(), new ProbabilityFeatureConfiguration(0.2F)));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_CHERRY_LEAVES = register("fallen_cherry_leaves", () -> new ConfiguredFeature<>(EnvironmentalFeatures.FALLEN_LEAVES.get(), NoneFeatureConfiguration.NONE));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_MYCELIUM_SPROUTS = register("patch_mycelium_sprouts", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EnvironmentalBlocks.MYCELIUM_SPROUTS.get()), 32)));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_PINE_BARRENS_GRASS = register("patch_pine_barrens_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 2)), 32)));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_PINE_BARRENS_TALL_GRASS = register("patch_pine_barrens_tall_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS)))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_JUNGLE_LARGE_FERN = register("patch_jungle_large_fern", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LARGE_FERN)))));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_WATERLILY = register("patch_waterlily", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_PAD))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_SUGAR_CANE = register("patch_sugar_cane", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 4), BlockStateProvider.simple(Blocks.SUGAR_CANE)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.SUGAR_CANE.defaultBlockState(), BlockPos.ZERO), BlockPredicate.anyOf(BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER))))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_GRASS = register("patch_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(Blocks.GRASS), 32)));
		public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FOREST_FLOWERS = register("forest_flowers", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY))))))));
		public static final RegistryObject<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> SEAGRASS_MID = register("seagrass_mid", () -> new ConfiguredFeature<>(Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.6F)));

		private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
			return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
		}

		private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
			return CONFIGURED_FEATURES.register(name, feature);
		}
	}

	public static final class EnvironmentalPlacedFeatures {
		public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Environmental.MOD_ID);

		private static final PlacementFilter PINE_PLACEMENT_FILTER = BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.ROOTED_DIRT)));
		private static final PlacementFilter PINE_ON_STONE_PLACEMENT_FILTER = BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.BASE_STONE_OVERWORLD));

		public static final RegistryObject<PlacedFeature> ORE_MUD = register("ore_mud", EnvironmentalConfiguredFeatures.ORE_MUD, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> SEAGRASS_MARSH = register("seagrass_marsh", EnvironmentalConfiguredFeatures.SEAGRASS_MID, AquaticPlacements.seagrassPlacement(128));
		public static final RegistryObject<PlacedFeature> PATCH_WATERLILY_MARSH = register("patch_waterlily", EnvironmentalConfiguredFeatures.PATCH_WATERLILY, VegetationPlacements.worldSurfaceSquaredWithCount(1));
		public static final RegistryObject<PlacedFeature> PATCH_DUCKWEED = register("patch_duckweed", EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_DUCKWEED_SWAMP = register("patch_duckweed_swamp", EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> FLOWER_BLUE_ORCHID = register("flower_blue_orchid", EnvironmentalConfiguredFeatures.FLOWER_BLUE_ORCHID, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_CORNFLOWER = register("flower_cornflower", EnvironmentalConfiguredFeatures.FLOWER_CORNFLOWER, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_DIANTHUS = register("flower_dianthus", EnvironmentalConfiguredFeatures.FLOWER_DIANTHUS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_BLUEBELL = register("flower_bluebell", EnvironmentalConfiguredFeatures.FLOWER_BLUEBELL, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_BLUEBELL_LARGE = register("flower_bluebell_large", EnvironmentalConfiguredFeatures.FLOWER_BLUEBELL_LARGE, RarityFilter.onAverageOnceEvery(256), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_VIOLET = register("flower_violet", EnvironmentalConfiguredFeatures.FLOWER_VIOLET, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_RED_LOTUS = register("flower_red_lotus", EnvironmentalConfiguredFeatures.FLOWER_RED_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_WHITE_LOTUS = register("flower_white_lotus", EnvironmentalConfiguredFeatures.FLOWER_WHITE_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_BIRD_OF_PARADISE = register("flower_bird_of_paradise", EnvironmentalConfiguredFeatures.FLOWER_BIRD_OF_PARADISE, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_TULIPS = register("patch_tulips", EnvironmentalConfiguredFeatures.PATCH_TULIPS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FOREST_FLOWERS = register("forest_flowers", EnvironmentalConfiguredFeatures.FOREST_FLOWERS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)), BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_TASSELFLOWER = register("patch_tasselflower", EnvironmentalConfiguredFeatures.PATCH_TASSELFLOWER, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_DELPHINIUMS = register("patch_delphiniums", EnvironmentalConfiguredFeatures.PATCH_DELPHINIUMS, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 2), 0, 2)), BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> FLOWER_CARTWHEEL = register("flower_cartwheel", EnvironmentalConfiguredFeatures.FLOWER_CARTWHEEL, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> ZEBRA_DAZZLE = register("zebra_dazzle", EnvironmentalConfiguredFeatures.ZEBRA_DAZZLE, RarityFilter.onAverageOnceEvery(128), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> HIBISCUS_BUSH = register("hibiscus_bush", EnvironmentalConfiguredFeatures.HIBISCUS_BUSH, RarityFilter.onAverageOnceEvery(256), CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> WHITE_WISTERIA_BEES_002 = register("white_wisteria_bees_002", EnvironmentalConfiguredFeatures.WHITE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> PINK_WISTERIA_BEES_002 = register("pink_wisteria_bees_002", EnvironmentalConfiguredFeatures.PINK_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> PURPLE_WISTERIA_BEES_002 = register("purple_wisteria_bees_002", EnvironmentalConfiguredFeatures.PURPLE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> BLUE_WISTERIA_BEES_002 = register("blue_wisteria_bees_002", EnvironmentalConfiguredFeatures.BLUE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> TREES_WISTERIA = register("trees_wisteria", EnvironmentalConfiguredFeatures.TREES_WISTERIA, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 2)));

		public static final RegistryObject<PlacedFeature> TREES_WILLOW = register("trees_willow", EnvironmentalConfiguredFeatures.TREES_WILLOW, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> SWAMP_OAK = register("swamp_oak", EnvironmentalConfiguredFeatures.SWAMP_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		public static final RegistryObject<PlacedFeature> TREES_MARSH = register("trees_marsh", EnvironmentalConfiguredFeatures.MARSH_OAK, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 1)));
		public static final RegistryObject<PlacedFeature> TREES_SWAMP = register("trees_swamp", EnvironmentalConfiguredFeatures.TREES_SWAMP, PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
		public static final RegistryObject<PlacedFeature> HUGE_BROWN_MUSHROOM_MARSH = register("huge_brown_mushroom_marsh", EnvironmentalConfiguredFeatures.HUGE_BROWN_MUSHROOM, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.025F, 1)));

		public static final RegistryObject<PlacedFeature> CHERRY_BEES_0002 = register("cherry_bees_0002", EnvironmentalConfiguredFeatures.CHERRY_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> CHEERFUL_CHERRY_BEES_0002 = register("cheerful_cherry_bees_0002", EnvironmentalConfiguredFeatures.CHEERFUL_CHERRY_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> MOODY_CHERRY_BEES_0002 = register("moody_cherry_bees_0002", EnvironmentalConfiguredFeatures.MOODY_CHERRY_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		public static final RegistryObject<PlacedFeature> CHERRY_BEES_005 = register("cherry_bees_005", EnvironmentalConfiguredFeatures.CHERRY_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> CHEERFUL_CHERRY_BEES_005 = register("cheerful_cherry_bees_005", EnvironmentalConfiguredFeatures.CHEERFUL_CHERRY_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> MOODY_CHERRY_BEES_005 = register("moody_cherry_bees_005", EnvironmentalConfiguredFeatures.MOODY_CHERRY_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		public static final RegistryObject<PlacedFeature> CHERRY_TREES_BLOSSOM_WOODS = register("cherry_tree_blossom_woods", EnvironmentalConfiguredFeatures.TREES_BLOSSOM_WOODS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1), Blocks.BIRCH_SAPLING));
		public static final RegistryObject<PlacedFeature> CHERRY_TREES_BLOSSOM_VALLEYS = register("cherry_tree_blossom_valleys", EnvironmentalConfiguredFeatures.TREES_BLOSSOM_VALLEYS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 3), Blocks.BIRCH_SAPLING));
		public static final RegistryObject<PlacedFeature> PINE_TREES_BLOSSOM_WOODS = register("pine_trees_blossom_woods", EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.05F, 7), Blocks.BIRCH_SAPLING));
		public static final RegistryObject<PlacedFeature> PINE_TREES_BLOSSOM_VALLEYS = register("pine_trees_blossom_valleys", EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 3), Blocks.BIRCH_SAPLING));

		public static final RegistryObject<PlacedFeature> FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS = register("fallen_cherry_leaves_blossom_woods", EnvironmentalConfiguredFeatures.FALLEN_CHERRY_LEAVES, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS = register("fallen_cherry_leaves_blossom_valleys", EnvironmentalConfiguredFeatures.FALLEN_CHERRY_LEAVES, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> BLOSSOM_WOODS_ROCK = register("blossom_woods_rock", EnvironmentalConfiguredFeatures.STONE_ROCK, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> PINE = register("pine", EnvironmentalConfiguredFeatures.PINE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> TALL_PINE = register("tall_pine", EnvironmentalConfiguredFeatures.TALL_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> TREES_PINE_BARRENS = register("trees_pine_barrens", EnvironmentalConfiguredFeatures.TREES_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(16, 0.1F, 1), PINE_PLACEMENT_FILTER));
		public static final RegistryObject<PlacedFeature> TREES_OLD_GROWTH_PINE_BARRENS = register("trees_old_growth_pine_barrens", EnvironmentalConfiguredFeatures.TREES_OLD_GROWTH_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(22, 0.1F, 1), PINE_PLACEMENT_FILTER));
		public static final RegistryObject<PlacedFeature> TREES_PINE_BARRENS_ON_STONE = register("trees_pine_barrens_on_stone", EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1), PINE_ON_STONE_PLACEMENT_FILTER));
		public static final RegistryObject<PlacedFeature> TREES_PINE_SLOPES = register("trees_pine_slopes", EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), PINE_ON_STONE_PLACEMENT_FILTER));

		public static final RegistryObject<PlacedFeature> GRAINY_COARSE_DIRT = register("grainy_coarse_dirt", EnvironmentalConfiguredFeatures.GRAINY_COARSE_DIRT, CountPlacement.of(56), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> COARSE_DIRT_ON_STONE = register("coarse_dirt_on_stone", EnvironmentalConfiguredFeatures.COARSE_DIRT_ON_STONE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> SMALL_COARSE_DIRT_ON_STONE = register("small_coarse_dirt_on_stone", EnvironmentalConfiguredFeatures.SMALL_COARSE_DIRT_ON_STONE, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> FALLEN_PINE_TREE = register("fallen_pine_tree", EnvironmentalConfiguredFeatures.FALLEN_PINE_TREE, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FALLEN_TALL_PINE_TREE = register("fallen_tall_pine_tree", EnvironmentalConfiguredFeatures.FALLEN_TALL_PINE_TREE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> PATCH_CUP_LICHEN = register("patch_cup_lichen", EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_CUP_LICHEN_SMALL = register("patch_cup_lichen_small", EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_SMALL, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_CUP_LICHEN_STONE = register("patch_cup_lichen_stone", EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_STONE, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_CUP_LICHEN_NOISE = register("patch_cup_lichen_noise", EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_NOISE, new BetterNoiseBasedCountPlacement(EnvironmentalNoiseParameters.NOISE_CUP_LICHEN.getHolder().orElseThrow(), 16, -0.4F), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> PINE_SLOPES_ROCK = register("pine_slopes_rock", EnvironmentalConfiguredFeatures.STONE_ROCK, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PINE_SLOPES_BOULDER = register("pine_slopes_boulder", EnvironmentalConfiguredFeatures.PINE_SLOPES_BOULDER, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> BAMBOO_BLOSSOM_WOODS = register("bamboo_blossom_woods", EnvironmentalConfiguredFeatures.BAMBOO_SHORT_PODZOL, NoiseBasedCountPlacement.of(11, 5.0D, 0.2D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> BAMBOO_LIGHT_BLOSSOM_WOODS = register("bamboo_light_blossom_woods", EnvironmentalConfiguredFeatures.BAMBOO_SHORT_PODZOL, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> BAMBOO_BLOSSOM_VALLEYS = register("bamboo_blossom_valleys", EnvironmentalConfiguredFeatures.BAMBOO_SHORT, NoiseBasedCountPlacement.of(5, 5.0D, 0.2D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> BAMBOO_LIGHT_BLOSSOM_VALLEYS = register("bamboo_light_blossom_valleys", EnvironmentalConfiguredFeatures.BAMBOO_SHORT, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> PATCH_GRASS_MARSH = register("patch_grass_marsh", EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(5));
		public static final RegistryObject<PlacedFeature> PATCH_GRASS_BLOSSOM_WOODS = register("patch_grass_blossom_woods", EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(12));
		public static final RegistryObject<PlacedFeature> PATCH_MYCELIUM_SPROUTS = register("patch_mycelium_sprouts", EnvironmentalConfiguredFeatures.PATCH_MYCELIUM_SPROUTS, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_GRASS_PINE_BARRENS = register("patch_grass_pine_barrens", EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(3));
		public static final RegistryObject<PlacedFeature> PATCH_GRASS_SNOWY_PINE_BARRENS = register("patch_grass_snowy_pine_barrens", EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_GRASS_OLD_GROWTH_PINE_BARRENS = register("patch_grass_old_growth_pine_barrens", EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(8));
		public static final RegistryObject<PlacedFeature> PATCH_GRASS_PINE_SLOPES = register("patch_grass_pine_slopes", EnvironmentalConfiguredFeatures.PATCH_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(26));
		public static final RegistryObject<PlacedFeature> PATCH_TALL_GRASS_PINE_BARRENS = register("patch_pine_barrens_tall_grass", EnvironmentalConfiguredFeatures.PATCH_PINE_BARRENS_TALL_GRASS, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_LARGE_FERN_JUNGLE = register("patch_large_fern_jungle", EnvironmentalConfiguredFeatures.PATCH_JUNGLE_LARGE_FERN, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> PATCH_GIANT_TALL_GRASS_PLAINS = register("patch_giant_tall_grass_plains", EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_GIANT_TALL_GRASS_SAVANNA = register("patch_giant_tall_grass_savanna", EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_GIANT_TALL_GRASS_JUNGLE = register("patch_giant_tall_grass_jungle", EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_GIANT_TALL_GRASS_MARSH = register("patch_giant_tall_grass_marsh", EnvironmentalConfiguredFeatures.PATCH_GIANT_TALL_GRASS, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> CATTAILS = register("cattails", EnvironmentalConfiguredFeatures.CATTAILS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> CATTAILS_DENSE = register("cattails_dense", EnvironmentalConfiguredFeatures.CATTAILS_DENSE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_SUGAR_CANE_BLOSSOM = register("patch_sugar_cane_blossom", EnvironmentalConfiguredFeatures.PATCH_SUGAR_CANE, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		private static ImmutableList<PlacementModifier> treePlacement(PlacementModifier modifier, PlacementModifier... extraModifiers) {
			return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(VegetationPlacements.TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome()).add(extraModifiers).build();
		}

		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
			return register(name, feature, List.of(placementModifiers));
		}

		@SuppressWarnings("unchecked")
		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placementModifiers) {
			return PLACED_FEATURES.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) feature.getHolder().get(), ImmutableList.copyOf(placementModifiers)));
		}
	}
}