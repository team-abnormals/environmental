package com.teamabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.blueprint.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import com.teamabnormals.environmental.common.block.CartwheelBlock;
import com.teamabnormals.environmental.common.levelgen.feature.*;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.DwarfSpruceConfiguration;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.BestNoisesSelectorFeatureConfiguration;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.NoiseSelectorFeatureConfiguration;
import com.teamabnormals.environmental.common.levelgen.feature.placement.NoiseDensityPlacement;
import com.teamabnormals.environmental.common.levelgen.treedecorators.HangingWillowDecorator;
import com.teamabnormals.environmental.common.levelgen.treedecorators.HangingWisteriaDecorator;
import com.teamabnormals.environmental.common.levelgen.treedecorators.PinePodzolDecorator;
import com.teamabnormals.environmental.common.levelgen.treedecorators.PineconeDecorator;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

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
	public static final RegistryObject<Feature<DwarfSpruceConfiguration>> DWARF_SPRUCE = FEATURES.register("dwarf_spruce", () -> new DwarfSpruceFeature(DwarfSpruceConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CUP_LICHEN_PATCH = FEATURES.register("cup_lichen_patch", () -> new CupLichenPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SMALL_CUP_LICHEN_PATCH = FEATURES.register("small_cup_lichen_patch", () -> new SmallCupLichenPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> STONE_CUP_LICHEN_PATCH = FEATURES.register("stone_cup_lichen_patch", () -> new StoneCupLichenPatchFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<TreeConfiguration>> WEEPING_WILLOW_TREE = FEATURES.register("weeping_willow_tree", () -> new WeepingWillowTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> PLUM_TREE = FEATURES.register("plum_tree", () -> new PlumTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> PINE_TREE = FEATURES.register("pine_tree", () -> new PineTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> PINE_TREE_ON_STONE = FEATURES.register("pine_tree_on_stone", () -> new PineTreeOnStoneFeature(TreeConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> WILLOW_TREE_PLACER = FEATURES.register("willow_tree_placer", () -> new WillowTreePlacerFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZEBRA_DAZZLE = FEATURES.register("zebra_dazzle", () -> new ZebraDazzleFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<NoiseSelectorFeatureConfiguration>> NOISE_SELECTOR = FEATURES.register("noise_selector", () -> new NoiseSelectorFeature(NoiseSelectorFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<BestNoisesSelectorFeatureConfiguration>> MULTI_NOISE_SELECTOR = FEATURES.register("best_noises_selector", () -> new BestNoisesSelectorFeature(BestNoisesSelectorFeatureConfiguration.CODEC));

	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WILLOW_LEAVES = TREE_DECORATORS.register("hanging_willow_leaves", () -> new TreeDecoratorType<>(HangingWillowDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WISTERIA_LEAVES = TREE_DECORATORS.register("hanging_wisteria_leaves", () -> new TreeDecoratorType<>(HangingWisteriaDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>> PINECONE = TREE_DECORATORS.register("pinecone", () -> new TreeDecoratorType<>(PineconeDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>> PINE_PODZOL = TREE_DECORATORS.register("pine_podzol", () -> new TreeDecoratorType<>(PinePodzolDecorator.CODEC));

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
		public static final TreeConfiguration PLUM_BEES_0002 = createPlum().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration PLUM_BEES_005 = createPlum().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration CHEERFUL_PLUM = createCheerfulPlum().build();
		public static final TreeConfiguration CHEERFUL_PLUM_BEES_0002 = createCheerfulPlum().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration CHEERFUL_PLUM_BEES_005 = createCheerfulPlum().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration MOODY_PLUM = createMoodyPlum().build();
		public static final TreeConfiguration MOODY_PLUM_BEES_0002 = createMoodyPlum().decorators(List.of(BEEHIVE_0002)).build();
		public static final TreeConfiguration MOODY_PLUM_BEES_005 = createMoodyPlum().decorators(List.of(BEEHIVE_005)).build();

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

		public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM = createKey("cheerful_plum");
		public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM_BEES_0002 = createKey("cheerful_plum_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> CHEERFUL_PLUM_BEES_005 = createKey("cheerful_plum_bees_005");

		public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM = createKey("moody_plum");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM_BEES_0002 = createKey("moody_plum_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MOODY_PLUM_BEES_005 = createKey("moody_plum_bees_005");

		public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BLOSSOM_WOODS = createKey("trees_blossom_woods");
		public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BLOSSOM_VALLEYS = createKey("trees_blossom_valleys");

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

		public static final ResourceKey<ConfiguredFeature<?, ?>> GRAINY_COARSE_DIRT = createKey("grainy_coarse_dirt");
		public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ON_STONE = createKey("coarse_dirt_on_stone");
		public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_COARSE_DIRT_ON_STONE = createKey("small_coarse_dirt_on_stone");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_PINE_TREE = createKey("fallen_pine_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TALL_PINE_TREE = createKey("fallen_tall_pine_tree");

		public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE = createKey("dwarf_spruce");
		public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE_DENSE = createKey("dwarf_spruce_dense");
		public static final ResourceKey<ConfiguredFeature<?, ?>> DWARF_SPRUCE_TAIGA = createKey("dwarf_spruce_taiga");

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN = createKey("patch_cup_lichen");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN_SMALL = createKey("patch_cup_lichen_small");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN_STONE = createKey("patch_cup_lichen_stone");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CUP_LICHEN_NOISE = createKey("patch_cup_lichen_noise");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLUE_ORCHID = createKey("flower_blue_orchid");
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

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MYCELIUM_SPROUTS = createKey("patch_mycelium_sprouts");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PINE_BARRENS_GRASS = createKey("patch_pine_barrens_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_JUNGLE_LARGE_FERN = createKey("patch_jungle_large_fern");

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WATERLILY = createKey("patch_waterlily");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SUGAR_CANE = createKey("patch_sugar_cane");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS = createKey("patch_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_FLOWERS = createKey("forest_flowers");
		public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_MID = createKey("seagrass_mid");

		public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
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

			register(context, CHEERFUL_PLUM, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM);
			register(context, CHEERFUL_PLUM_BEES_0002, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM_BEES_0002);
			register(context, CHEERFUL_PLUM_BEES_005, EnvironmentalFeatures.PLUM_TREE.get(), Configs.CHEERFUL_PLUM_BEES_005);

			register(context, MOODY_PLUM, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM);
			register(context, MOODY_PLUM_BEES_0002, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM_BEES_0002);
			register(context, MOODY_PLUM_BEES_005, EnvironmentalFeatures.PLUM_TREE.get(), Configs.MOODY_PLUM_BEES_005);

			register(context, TREES_BLOSSOM_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.CHEERFUL_PLUM_BEES_0002), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.MOODY_PLUM_BEES_0002), 0.3F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PLUM_BEES_0002)));
			register(context, TREES_BLOSSOM_VALLEYS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.OAK_BEES_002), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.CHEERFUL_PLUM_BEES_005), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.MOODY_PLUM_BEES_005), 0.3F)), placedFeatures.getOrThrow(EnvironmentalPlacedFeatures.PLUM_BEES_005)));

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

			register(context, GRAINY_COARSE_DIRT, EnvironmentalFeatures.GRAINY_COARSE_DIRT.get(), new ProbabilityFeatureConfiguration(0.1F));
			register(context, COARSE_DIRT_ON_STONE, EnvironmentalFeatures.COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE);
			register(context, SMALL_COARSE_DIRT_ON_STONE, EnvironmentalFeatures.SMALL_COARSE_DIRT_ON_STONE.get(), NoneFeatureConfiguration.NONE);

			register(context, FALLEN_PINE_TREE, EnvironmentalFeatures.FALLEN_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE);
			register(context, FALLEN_TALL_PINE_TREE, EnvironmentalFeatures.FALLEN_TALL_PINE_TREE.get(), NoneFeatureConfiguration.INSTANCE);

			register(context, DWARF_SPRUCE, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(5, 0.6F));
			register(context, DWARF_SPRUCE_DENSE, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(96, -0.8F));
			register(context, DWARF_SPRUCE_TAIGA, EnvironmentalFeatures.DWARF_SPRUCE.get(), new DwarfSpruceConfiguration(3, 0.0F));

			register(context, PATCH_CUP_LICHEN, EnvironmentalFeatures.CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE);
			register(context, PATCH_CUP_LICHEN_SMALL, EnvironmentalFeatures.SMALL_CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE);
			register(context, PATCH_CUP_LICHEN_STONE, EnvironmentalFeatures.STONE_CUP_LICHEN_PATCH.get(), NoneFeatureConfiguration.INSTANCE);
			register(context, PATCH_CUP_LICHEN_NOISE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PATCH_CUP_LICHEN_SMALL)), 0.8F)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PATCH_CUP_LICHEN))));

			register(context, FLOWER_BLUE_ORCHID, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BLUE_ORCHID)))));
			register(context, FLOWER_CORNFLOWER, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CORNFLOWER)))));
			register(context, FLOWER_DIANTHUS, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DIANTHUS.get())))));
			register(context, FLOWER_BLUEBELL, Feature.FLOWER, new RandomPatchConfiguration(128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUEBELL.get())))));
			register(context, FLOWER_BLUEBELL_LARGE, EnvironmentalFeatures.LARGE_BLUEBELL_PATCH.get(), NoneFeatureConfiguration.NONE);
			register(context, FLOWER_VIOLET, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.VIOLET.get())))));
			register(context, FLOWER_RED_LOTUS, Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.RED_LOTUS_FLOWER.get())))));
			register(context, FLOWER_WHITE_LOTUS, Feature.FLOWER, new RandomPatchConfiguration(24, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get())))));
			register(context, FLOWER_CARTWHEEL, CARTWHEEL.get(), new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.NORTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.SOUTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.EAST), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.WEST), 1))));
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
			register(context, FALLEN_PLUM_LEAVES, EnvironmentalFeatures.FALLEN_LEAVES.get(), NoneFeatureConfiguration.NONE);

			register(context, PATCH_MYCELIUM_SPROUTS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EnvironmentalBlocks.MYCELIUM_SPROUTS.get()), 32));
			register(context, PATCH_PINE_BARRENS_GRASS, Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 2)), 32));
			register(context, PATCH_JUNGLE_LARGE_FERN, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LARGE_FERN))));

			register(context, PATCH_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_PAD)))));
			register(context, PATCH_SUGAR_CANE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0, PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 4), BlockStateProvider.simple(Blocks.SUGAR_CANE)), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.SUGAR_CANE.defaultBlockState(), BlockPos.ZERO), BlockPredicate.anyOf(BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)))))));
			register(context, PATCH_GRASS, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(Blocks.GRASS), 32));
			register(context, FOREST_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY)))))));
			register(context, SEAGRASS_MID, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.6F));
		}

		private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
			return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
		}

		public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Environmental.MOD_ID, name));
		}

		public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
			context.register(key, new ConfiguredFeature<>(feature, config));
		}
	}

	public static final class EnvironmentalPlacedFeatures {
		private static final PlacementFilter PINE_ON_STONE_PLACEMENT_FILTER = BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.BASE_STONE_OVERWORLD));

		public static final ResourceKey<PlacedFeature> ORE_MUD = createKey("ore_mud");
		public static final ResourceKey<PlacedFeature> SEAGRASS_MARSH = createKey("seagrass_marsh");
		public static final ResourceKey<PlacedFeature> PATCH_WATERLILY_MARSH = createKey("patch_waterlily");
		public static final ResourceKey<PlacedFeature> PATCH_DUCKWEED = createKey("patch_duckweed");
		public static final ResourceKey<PlacedFeature> PATCH_DUCKWEED_SWAMP = createKey("patch_duckweed_swamp");

		public static final ResourceKey<PlacedFeature> FLOWER_BLUE_ORCHID = createKey("flower_blue_orchid");
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

		public static final ResourceKey<PlacedFeature> FALLEN_PLUM_LEAVES_BLOSSOM_WOODS = createKey("fallen_plum_leaves_blossom_woods");
		public static final ResourceKey<PlacedFeature> FALLEN_PLUM_LEAVES_BLOSSOM_VALLEYS = createKey("fallen_plum_leaves_blossom_valleys");

		public static final ResourceKey<PlacedFeature> BLOSSOM_WOODS_ROCK = createKey("blossom_woods_rock");

		public static final ResourceKey<PlacedFeature> PINE = createKey("pine");
		public static final ResourceKey<PlacedFeature> TALL_PINE_WITH_PODZOL = createKey("tall_pine_with_podzol");
		public static final ResourceKey<PlacedFeature> TREES_PINE_BARRENS = createKey("trees_pine_barrens");
		public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_BARRENS = createKey("trees_old_growth_pine_barrens");
		public static final ResourceKey<PlacedFeature> TREES_PINE_BARRENS_ON_STONE = createKey("trees_pine_barrens_on_stone");
		public static final ResourceKey<PlacedFeature> TREES_PINE_SLOPES = createKey("trees_pine_slopes");

		public static final ResourceKey<PlacedFeature> GRAINY_COARSE_DIRT = createKey("grainy_coarse_dirt");
		public static final ResourceKey<PlacedFeature> COARSE_DIRT_ON_STONE = createKey("coarse_dirt_on_stone");
		public static final ResourceKey<PlacedFeature> SMALL_COARSE_DIRT_ON_STONE = createKey("small_coarse_dirt_on_stone");

		public static final ResourceKey<PlacedFeature> FALLEN_PINE_TREE = createKey("fallen_pine_tree");
		public static final ResourceKey<PlacedFeature> FALLEN_TALL_PINE_TREE = createKey("fallen_tall_pine_tree");

		public static final ResourceKey<PlacedFeature> DWARF_SPRUCE = createKey("dwarf_spruce");
		public static final ResourceKey<PlacedFeature> DWARF_SPRUCE_DENSE = createKey("dwarf_spruce_dense");
		public static final ResourceKey<PlacedFeature> DWARF_SPRUCE_TAIGA = createKey("dwarf_spruce_taiga");
		public static final ResourceKey<PlacedFeature> DWARF_SPRUCE_TAIGA_DENSE = createKey("dwarf_spruce_taiga_dense");

		public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN = createKey("patch_cup_lichen");
		public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN_SMALL = createKey("patch_cup_lichen_small");
		public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN_STONE = createKey("patch_cup_lichen_stone");
		public static final ResourceKey<PlacedFeature> PATCH_CUP_LICHEN_NOISE = createKey("patch_cup_lichen_noise");

		public static final ResourceKey<PlacedFeature> PINE_SLOPES_ROCK = createKey("pine_slopes_rock");
		public static final ResourceKey<PlacedFeature> PINE_SLOPES_BOULDER = createKey("pine_slopes_boulder");

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

		public static void bootstrap(BootstapContext<PlacedFeature> context) {
			HolderGetter<NoiseParameters> noises = context.lookup(Registries.NOISE);

			register(context, ORE_MUD, EnvironmentalConfiguredFeatures.ORE_MUD, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
			register(context, SEAGRASS_MARSH, EnvironmentalConfiguredFeatures.SEAGRASS_MID, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(128), BiomeFilter.biome());
			register(context, PATCH_WATERLILY_MARSH, EnvironmentalConfiguredFeatures.PATCH_WATERLILY, VegetationPlacements.worldSurfaceSquaredWithCount(1));
			register(context, PATCH_DUCKWEED, EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_DUCKWEED_SWAMP, EnvironmentalConfiguredFeatures.PATCH_DUCKWEED, PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

			register(context, FLOWER_BLUE_ORCHID, EnvironmentalConfiguredFeatures.FLOWER_BLUE_ORCHID, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
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

			register(context, WHITE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.WHITE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, PINK_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.PINK_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, PURPLE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.PURPLE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, BLUE_WISTERIA_BEES_002, EnvironmentalConfiguredFeatures.BLUE_WISTERIA_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, TREES_WISTERIA, EnvironmentalConfiguredFeatures.TREES_WISTERIA, VegetationPlacements.treePlacement(new NoiseDensityPlacement(noises.getOrThrow(EnvironmentalNoiseParameters.WISTERIA_DENSITY), 0.85D, 1.0D)));

			register(context, TREES_WILLOW, EnvironmentalConfiguredFeatures.TREES_WILLOW, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, SWAMP_OAK, EnvironmentalConfiguredFeatures.SWAMP_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

			register(context, TREES_MARSH, EnvironmentalConfiguredFeatures.MARSH_OAK, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 1)));
			register(context, TREES_SWAMP, EnvironmentalConfiguredFeatures.TREES_SWAMP, PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
			register(context, HUGE_BROWN_MUSHROOM_MARSH, EnvironmentalConfiguredFeatures.HUGE_BROWN_MUSHROOM, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.025F, 1)));

			register(context, PLUM_BEES_0002, EnvironmentalConfiguredFeatures.PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, CHEERFUL_PLUM_BEES_0002, EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, MOODY_PLUM_BEES_0002, EnvironmentalConfiguredFeatures.MOODY_PLUM_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

			register(context, PLUM_BEES_005, EnvironmentalConfiguredFeatures.PLUM_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, CHEERFUL_PLUM_BEES_005, EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, MOODY_PLUM_BEES_005, EnvironmentalConfiguredFeatures.MOODY_PLUM_BEES_005, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

			register(context, PLUM_TREES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.TREES_BLOSSOM_WOODS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1), Blocks.BIRCH_SAPLING));
			register(context, PLUM_TREES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.TREES_BLOSSOM_VALLEYS, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 3), Blocks.BIRCH_SAPLING));
			register(context, PINE_TREES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.05F, 7), Blocks.BIRCH_SAPLING));
			register(context, PINE_TREES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.PINE_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 3), Blocks.BIRCH_SAPLING));

			register(context, FALLEN_PLUM_LEAVES_BLOSSOM_WOODS, EnvironmentalConfiguredFeatures.FALLEN_PLUM_LEAVES, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, FALLEN_PLUM_LEAVES_BLOSSOM_VALLEYS, EnvironmentalConfiguredFeatures.FALLEN_PLUM_LEAVES, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

			register(context, BLOSSOM_WOODS_ROCK, EnvironmentalConfiguredFeatures.STONE_ROCK, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, PINE, EnvironmentalConfiguredFeatures.PINE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, TALL_PINE_WITH_PODZOL, EnvironmentalConfiguredFeatures.TALL_PINE_WITH_PODZOL, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, TREES_PINE_BARRENS, EnvironmentalConfiguredFeatures.TREES_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(14, 0.1F, 1)));
			register(context, TREES_OLD_GROWTH_PINE_BARRENS, EnvironmentalConfiguredFeatures.TREES_OLD_GROWTH_PINE_BARRENS, treePlacement(PlacementUtils.countExtra(22, 0.1F, 1)));
			register(context, TREES_PINE_BARRENS_ON_STONE, EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1), PINE_ON_STONE_PLACEMENT_FILTER));
			register(context, TREES_PINE_SLOPES, EnvironmentalConfiguredFeatures.PINE_ON_STONE, treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), PINE_ON_STONE_PLACEMENT_FILTER));

			register(context, GRAINY_COARSE_DIRT, EnvironmentalConfiguredFeatures.GRAINY_COARSE_DIRT, CountPlacement.of(56), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, COARSE_DIRT_ON_STONE, EnvironmentalConfiguredFeatures.COARSE_DIRT_ON_STONE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, SMALL_COARSE_DIRT_ON_STONE, EnvironmentalConfiguredFeatures.SMALL_COARSE_DIRT_ON_STONE, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

			register(context, FALLEN_PINE_TREE, EnvironmentalConfiguredFeatures.FALLEN_PINE_TREE, RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, FALLEN_TALL_PINE_TREE, EnvironmentalConfiguredFeatures.FALLEN_TALL_PINE_TREE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, DWARF_SPRUCE, EnvironmentalConfiguredFeatures.DWARF_SPRUCE, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, DWARF_SPRUCE_DENSE, EnvironmentalConfiguredFeatures.DWARF_SPRUCE_DENSE, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, DWARF_SPRUCE_TAIGA, EnvironmentalConfiguredFeatures.DWARF_SPRUCE_TAIGA, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, DWARF_SPRUCE_TAIGA_DENSE, EnvironmentalConfiguredFeatures.DWARF_SPRUCE_DENSE, PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, PATCH_CUP_LICHEN, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, PATCH_CUP_LICHEN_SMALL, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_SMALL, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, PATCH_CUP_LICHEN_STONE, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_STONE, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, PATCH_CUP_LICHEN_NOISE, EnvironmentalConfiguredFeatures.PATCH_CUP_LICHEN_NOISE, new BetterNoiseBasedCountPlacement(noises.getOrThrow(EnvironmentalNoiseParameters.NOISE_CUP_LICHEN), 18, -0.6F), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

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
		}

		private static ImmutableList<PlacementModifier> treePlacement(PlacementModifier modifier, PlacementModifier... extraModifiers) {
			return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome()).add(extraModifiers).build();
		}

		public static ResourceKey<PlacedFeature> createKey(String name) {
			return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Environmental.MOD_ID, name));
		}

		public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
			context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(feature), modifiers));
		}

		public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
			register(context, key, feature, List.of(modifiers));
		}

	}
}