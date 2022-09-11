package com.teamabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.block.CartwheelBlock;
import com.teamabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.teamabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.teamabnormals.environmental.common.levelgen.feature.*;
import com.teamabnormals.environmental.common.levelgen.treedecorators.HangingWillowLeavesTreeDecorator;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Environmental.MOD_ID);
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoneFeatureConfiguration.CODEC));

	public static final RegistryObject<Feature<TreeConfiguration>> CHERRY_TREE = FEATURES.register("cherry_tree", () -> new CherryTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> BIG_WISTERIA_TREE = FEATURES.register("big_wisteria_tree", () -> new BigWisteriaTreeFeature(TreeConfiguration.CODEC));

	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WILLOW_LEAVES = TREE_DECORATORS.register("hanging_willow_leaves", () -> new TreeDecoratorType<>(HangingWillowLeavesTreeDecorator.CODEC));

	public static final class States {
		public static final BlockState MUD = EnvironmentalBlocks.MUD.get().defaultBlockState();

		public static final BlockState WILLOW_LOG = EnvironmentalBlocks.WILLOW_LOG.get().defaultBlockState();
		public static final BlockState WILLOW_LEAVES = EnvironmentalBlocks.WILLOW_LEAVES.get().defaultBlockState();

		public static final BlockState CHERRY_LOG = EnvironmentalBlocks.CHERRY_LOG.get().defaultBlockState();
		public static final BlockState CHERRY_LEAVES = EnvironmentalBlocks.CHERRY_LEAVES.get().defaultBlockState();

		public static final BlockState TALL_DEAD_BUSH = EnvironmentalBlocks.TALL_DEAD_BUSH.get().defaultBlockState();

		public static final BlockState WISTERIA_LOG = EnvironmentalBlocks.WISTERIA_LOG.get().defaultBlockState();
		public static final BlockState BLUE_WISTERIA_LEAVES = EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get().defaultBlockState().setValue(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState PINK_WISTERIA_LEAVES = EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get().defaultBlockState().setValue(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState WHITE_WISTERIA_LEAVES = EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get().defaultBlockState().setValue(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState PURPLE_WISTERIA_LEAVES = EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get().defaultBlockState().setValue(WisteriaLeavesBlock.DISTANCE, 1);

		public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState PINK_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);

		public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState PINK_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().defaultBlockState().setValue(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	}

	public static final class Configs {
		private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);

		public static final TreeConfiguration CHERRY = createCherry().build();
		public static final TreeConfiguration CHERRY_BEES_005 = createCherry().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration WHITE_WISTERIA = createWhiteWisteria().build();
		public static final TreeConfiguration WHITE_WISTERIA_BEES_005 = createWhiteWisteria().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration BLUE_WISTERIA = createBlueWisteria().build();
		public static final TreeConfiguration BLUE_WISTERIA_BEES_005 = createBlueWisteria().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration PURPLE_WISTERIA = createPurpleWisteria().build();
		public static final TreeConfiguration PURPLE_WISTERIA_BEES_005 = createPurpleWisteria().decorators(List.of(BEEHIVE_005)).build();

		public static final TreeConfiguration PINK_WISTERIA = createPinkWisteria().build();
		public static final TreeConfiguration PINK_WISTERIA_BEES_005 = createPinkWisteria().decorators(List.of(BEEHIVE_005)).build();

//		public static final RandomPatchConfiguration CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder((new WeightedStateProvider()).add(Blocks.POPPY.defaultBlockState(), 2).add(Blocks.RED_TULIP.defaultBlockState(), 2).add(Blocks.WHITE_TULIP.defaultBlockState(), 1).add(Blocks.DANDELION.defaultBlockState(), 1).add(Blocks.PINK_TULIP.defaultBlockState(), 3).add(Blocks.ORANGE_TULIP.defaultBlockState(), 1).add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();
//
//		public static final RandomPatchConfiguration WHITE_DELPHINIUM = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.WHITE_DELPHINIUM.get().defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build();
//		public static final RandomPatchConfiguration PINK_DELPHINIUM = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.PINK_DELPHINIUM.get().defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build();
//		public static final RandomPatchConfiguration PURPLE_DELPHINIUM = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.PURPLE_DELPHINIUM.get().defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build();
//		public static final RandomPatchConfiguration BLUE_DELPHINIUM = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.BLUE_DELPHINIUM.get().defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build();
//
//		public static final RandomPatchConfiguration CARTWHEEL_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build();
//		public static final RandomPatchConfiguration BLUE_ORCHID_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.BLUE_ORCHID.defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
//		public static final RandomPatchConfiguration CORNFLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.CORNFLOWER.defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
//		public static final RandomPatchConfiguration DIANTHUS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.DIANTHUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
//		public static final RandomPatchConfiguration BLUEBELL_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.BLUEBELL.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
//		public static final RandomPatchConfiguration ALLIUM_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.ALLIUM.defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
//		public static final RandomPatchConfiguration VIOLET_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.VIOLET.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
//		public static final RandomPatchConfiguration WHITE_LOTUS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
//		public static final RandomPatchConfiguration RED_LOTUS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.RED_LOTUS_FLOWER.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
//
// 		public static final RandomPatchConfiguration BIRD_OF_PARADISE_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.BIRD_OF_PARADISE.get().defaultBlockState()), DoublePlantPlacer.INSTANCE)).tries(64).noProjection().build();
//
//		public static final RandomPatchConfiguration LILY_PAD_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.LILY_PAD.defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(10).build();
//		public static final RandomPatchConfiguration DUCKWEED_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.DUCKWEED.get().defaultBlockState()), new SimpleBlockPlacer())).tries(1024).build();
//		public static final RandomPatchConfiguration GIANT_TALL_GRASS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().defaultBlockState()), new DoublePlantPlacer())).tries(256).noProjection().build();
//		public static final RandomPatchConfiguration MYCELIUM_SPROUTS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build();
//		public static final RandomPatchConfiguration TALL_DEAD_BUSH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(States.TALL_DEAD_BUSH), new DoublePlantPlacer())).tries(64).noProjection().build();
//
//		public static final TreeConfiguration WILLOW_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.WILLOW_LOG), new SimpleStateProvider(States.WILLOW_LEAVES), new BlobFoliagePlacer(UniformInt.fixed(3), UniformInt.fixed(0), 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayersFeatureSize(1, 0, 1))).maxWaterDepth(1).decorators(ImmutableList.of(LeaveVineDecorator.INSTANCE, HangingWillowLeavesTreeDecorator.INSTANCE)).build();
//
//		public static final TreeConfiguration CHERRY_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.CHERRY_LOG), new SimpleStateProvider(States.CHERRY_LEAVES), new FancyFoliagePlacer(UniformInt.fixed(0), UniformInt.fixed(0), 0), new FancyTrunkPlacer(0, 0, 0), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(0)))).ignoreVines().heightmap(Heightmap.Types.MOTION_BLOCKING).build();
//		public static final TreeConfiguration CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_0002));
//		public static final TreeConfiguration CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_005));
//
//		public static final TreeConfiguration BLUE_WISTERIA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.WISTERIA_LOG), new SimpleStateProvider(States.BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(UniformInt.fixed(0), UniformInt.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).maxWaterDepth(1).build();
//		public static final TreeConfiguration BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_0002));
//		public static final TreeConfiguration BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_002));
//		public static final TreeConfiguration BLUE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_005));
//
//		public static final TreeConfiguration PINK_WISTERIA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.WISTERIA_LOG), new SimpleStateProvider(States.PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(UniformInt.fixed(0), UniformInt.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).maxWaterDepth(1).build();
//		public static final TreeConfiguration PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_0002));
//		public static final TreeConfiguration PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_002));
//		public static final TreeConfiguration PINK_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_005));
//
//		public static final TreeConfiguration PURPLE_WISTERIA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.WISTERIA_LOG), new SimpleStateProvider(States.PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(UniformInt.fixed(0), UniformInt.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).maxWaterDepth(1).build();
//		public static final TreeConfiguration PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_0002));
//		public static final TreeConfiguration PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_002));
//		public static final TreeConfiguration PURPLE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_005));
//
//		public static final TreeConfiguration WHITE_WISTERIA_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.WISTERIA_LOG), new SimpleStateProvider(States.WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(UniformInt.fixed(0), UniformInt.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayersFeatureSize(0, 0, 0))).maxWaterDepth(1).build();
//		public static final TreeConfiguration WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_0002));
//		public static final TreeConfiguration WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_002));
//		public static final TreeConfiguration WHITE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Decorators.BEEHIVE_005));

		private static TreeConfigurationBuilder createCherry() {
			return createCustomTree(EnvironmentalBlocks.CHERRY_LOG.get().defaultBlockState(), EnvironmentalBlocks.CHERRY_LEAVES.get().defaultBlockState());
		}

		private static TreeConfigurationBuilder createWhiteWisteria() {
			return createCustomTree(EnvironmentalBlocks.WISTERIA_LOG.get().defaultBlockState(), EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get().defaultBlockState());
		}

		private static TreeConfigurationBuilder createBlueWisteria() {
			return createCustomTree(EnvironmentalBlocks.WISTERIA_LOG.get().defaultBlockState(), EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get().defaultBlockState());
		}

		private static TreeConfigurationBuilder createPurpleWisteria() {
			return createCustomTree(EnvironmentalBlocks.WISTERIA_LOG.get().defaultBlockState(), EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get().defaultBlockState());
		}

		private static TreeConfigurationBuilder createPinkWisteria() {
			return createCustomTree(EnvironmentalBlocks.WISTERIA_LOG.get().defaultBlockState(), EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get().defaultBlockState());
		}

		private static TreeConfigurationBuilder createCustomTree(BlockState logState, BlockState leavesState) {
			return new TreeConfigurationBuilder(BlockStateProvider.simple(logState), new StraightTrunkPlacer(0, 0, 0), BlockStateProvider.simple(leavesState), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0)).ignoreVines();
		}
	}

	public static final class EnvironmentalConfiguredFeatures {
		public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Environmental.MOD_ID);

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WILLOW = register("willow", () -> new ConfiguredFeature<>(Feature.TREE, createStraightBlobTree(EnvironmentalBlocks.WILLOW_LOG.get(), EnvironmentalBlocks.WILLOW_LEAVES.get(), 5, 3, 0, 3).decorators(ImmutableList.of(LeaveVineDecorator.INSTANCE, HangingWillowLeavesTreeDecorator.INSTANCE)).build()));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHERRY = register("cherry", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHERRY));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BEES_005 = register("cherry_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.CHERRY_TREE.get(), Configs.CHERRY_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WHITE_WISTERIA = register("white_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> WHITE_WISTERIA_BEES_005 = register("white_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_WHITE_WISTERIA = register("big_white_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.BIG_WISTERIA_TREE.get(), Configs.WHITE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_WHITE_WISTERIA_BEES_005 = register("big_white_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.WHITE_WISTERIA_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BLUE_WISTERIA = register("blue_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BLUE_WISTERIA_BEES_005 = register("blue_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_BLUE_WISTERIA = register("big_blue_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.BIG_WISTERIA_TREE.get(), Configs.BLUE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_BLUE_WISTERIA_BEES_005 = register("big_blue_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.BLUE_WISTERIA_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PURPLE_WISTERIA = register("purple_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PURPLE_WISTERIA_BEES_005 = register("purple_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_PURPLE_WISTERIA = register("big_purple_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.BIG_WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_PURPLE_WISTERIA_BEES_005 = register("big_purple_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PURPLE_WISTERIA_BEES_005));

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINK_WISTERIA = register("pink_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> PINK_WISTERIA_BEES_005 = register("pink_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_005));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_PINK_WISTERIA = register("big_pink_wisteria", () -> new ConfiguredFeature<>(EnvironmentalFeatures.BIG_WISTERIA_TREE.get(), Configs.PINK_WISTERIA));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BIG_PINK_WISTERIA_BEES_005 = register("big_pink_wisteria_bees_005", () -> new ConfiguredFeature<>(EnvironmentalFeatures.WISTERIA_TREE.get(), Configs.PINK_WISTERIA_BEES_005));


		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BLUE_ORCHID = register("flower_blue_orchid", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BLUE_ORCHID))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_CORNFLOWER = register("flower_cornflower", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CORNFLOWER))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_DIANTHUS = register("flower_dianthus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.DIANTHUS.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BLUEBELL = register("flower_bluebell", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.BLUEBELL.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_VIOLET = register("flower_violet", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.VIOLET.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_ALLIUM = register("flower_allium", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_RED_LOTUS = register("flower_red_lotus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.RED_LOTUS_FLOWER.get()))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_WHITE_LOTUS = register("flower_white_lotus", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get()))))));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_HIBISCUS_WARM = register("flower_hibiscus_warm", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.RED_HIBISCUS.get().defaultBlockState(), 1).add(EnvironmentalBlocks.ORANGE_HIBISCUS.get().defaultBlockState(), 2).add(EnvironmentalBlocks.YELLOW_HIBISCUS.get().defaultBlockState(), 3)))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_HIBISCUS_COOL = register("flower_hibiscus_cool", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.PURPLE_HIBISCUS.get().defaultBlockState(), 1).add(EnvironmentalBlocks.MAGENTA_HIBISCUS.get().defaultBlockState(), 2).add(EnvironmentalBlocks.PINK_HIBISCUS.get().defaultBlockState(), 3)))))));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_CARTWHEEL = register("flower_cartwheel", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.NORTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.SOUTH), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.EAST), 1).add(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState().setValue(CartwheelBlock.FACING, Direction.WEST), 1)))))));


		//		public static final ConfiguredFeature<?, ?> PATCH_GRASS_MARSH = register("patch_grass_marsh", Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(5)));
//		public static final ConfiguredFeature<?, ?> PATCH_WATERLILLY_MARSH = register("patch_waterlilly_marsh", Feature.RANDOM_PATCH.configured(Configs.LILY_PAD_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(1)));
//		public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS_MARSH = register("patch_tall_grass_marsh", Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(128)));
//		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_SWAMP = register("patch_duckweed_swamp", Feature.RANDOM_PATCH.configured(Configs.DUCKWEED_CONFIG).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.15F, 1))));
//		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_MARSH = register("patch_duckweed_marsh", Feature.RANDOM_PATCH.configured(Configs.DUCKWEED_CONFIG).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.15F, 1))));
//		public static final ConfiguredFeature<?, ?> PATCH_MYCELIUM_SPROUTS = register("patch_mycelium_sprouts", Feature.RANDOM_PATCH.configured(Configs.MYCELIUM_SPROUTS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(5)));
//		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS_DENSE = register("patch_cattails", EnvironmentalFeatures.DENSE_CATTAILS.get().configured(new NoneFeatureConfiguration()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((8))));
//		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS = register("patch_cattails_dense", EnvironmentalFeatures.CATTAILS.get().configured(new NoneFeatureConfiguration()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((12))));
//		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH = register("patch_tall_dead_bush", Feature.RANDOM_PATCH.configured(Configs.TALL_DEAD_BUSH_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((1))));
//		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH_MESA = register("patch_tall_dead_bush_mesa", Feature.RANDOM_PATCH.configured(Configs.TALL_DEAD_BUSH_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((6))));
//		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_PLAINS = register("patch_giant_tall_grass_plains", Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(1));
//		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_SAVANNA = register("patch_giant_tall_grass_savanna", Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
//		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_JUNGLE = register("patch_giant_tall_grass_jungle", Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4));
//		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_MARSH = register("patch_giant_tall_grass_marsh", Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(65));
//		public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_BLOSSOM = register("patch_sugar_cane_blossom", Feature.RANDOM_PATCH.configured(Features.Configs.SUGAR_CANE_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(12)));
//		public static final ConfiguredFeature<?, ?> PATCH_GRASS_BLOSSOM_WOODS = register("patch_grass_blossom_woods", Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(8)));
//		public static final ConfiguredFeature<?, ?> PATCH_WHITE_DELPHINIUM = register("patch_white_delphinium", Feature.RANDOM_PATCH.configured(Configs.WHITE_DELPHINIUM));
//		public static final ConfiguredFeature<?, ?> PATCH_PINK_DELPHINIUM = register("patch_pink_delphinium", Feature.RANDOM_PATCH.configured(Configs.PINK_DELPHINIUM));
//		public static final ConfiguredFeature<?, ?> PATCH_PURPLE_DELPHINIUM = register("patch_purple_delphinium", Feature.RANDOM_PATCH.configured(Configs.PURPLE_DELPHINIUM));
//		public static final ConfiguredFeature<?, ?> PATCH_BLUE_DELPHINIUM = register("patch_blue_delphinium", Feature.RANDOM_PATCH.configured(Configs.BLUE_DELPHINIUM));
//		public static final ConfiguredFeature<?, ?> PATCH_DELPHINIUMS = register("patch_delphiniums", Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(ImmutableList.of(() -> PATCH_WHITE_DELPHINIUM, () -> PATCH_PINK_DELPHINIUM, () -> PATCH_PURPLE_DELPHINIUM, () -> PATCH_BLUE_DELPHINIUM))).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(12)));
//		public static final ConfiguredFeature<?, ?> PATCH_BIRD_OF_PARADISE = register("patch_bird_of_paradise", Feature.RANDOM_PATCH.configured(Configs.BIRD_OF_PARADISE_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(5)));
//
//		public static final ConfiguredFeature<?, ?> FLOWER_CARTWHEEL = register("flower_cartwheel", EnvironmentalFeatures.DIRECTIONAL_FLOWER.get().configured(Configs.CARTWHEEL_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(5)));
//		public static final ConfiguredFeature<?, ?> FLOWER_RED_LOTUS = register("flower_red_lotus", Feature.FLOWER.configured(Configs.RED_LOTUS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
//		public static final ConfiguredFeature<?, ?> FLOWER_WHITE_LOTUS = register("flower_white_lotus", Feature.FLOWER.configured(Configs.WHITE_LOTUS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
//		public static final ConfiguredFeature<?, ?> FLOWER_ALLIUM = register("flower_allium", Feature.FLOWER.configured(Configs.ALLIUM_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
//		public static final ConfiguredFeature<?, ?> FLOWER_VIOLET = register("flower_violet", Feature.FLOWER.configured(Configs.VIOLET_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4));
//		public static final ConfiguredFeature<?, ?> FLOWER_BLUEBELL = register("flower_bluebell", Feature.FLOWER.configured(Configs.BLUEBELL_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));
//		public static final ConfiguredFeature<?, ?> FLOWER_BLOSSOM_WOODS = register("flower_blossom_woods", Feature.FLOWER.configured(Configs.CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((4))));
//		public static final ConfiguredFeature<?, ?> FLOWER_TALL_BLOSSOM_WOODS = register("flower_tall_blossom_woods", Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(ImmutableList.of(() -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.ROSE_BUSH.defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build()), () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.PEONY.defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build()), () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.LILAC.defaultBlockState()), new DoublePlantPlacer())).tries(64).noProjection().build())))).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count((6))));
//
//		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE = register("blue_wisteria_tree", EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE_BIG = register("blue_wisteria_tree_big", EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE = register("white_wisteria_tree", EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE_BIG = register("white_wisteria_tree_big", EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE = register("pink_wisteria_tree", EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE_BIG = register("pink_wisteria_tree_big", EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE = register("purple_wisteria_tree", EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE_BIG = register("purple_wisteria_tree_big", EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<?, ?> WISTERIA_TREE = register("wisteria_tree", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(BLUE_WISTERIA_TREE.weighted(0.15F), WHITE_WISTERIA_TREE.weighted(0.15F), PINK_WISTERIA_TREE.weighted(0.15F)), PURPLE_WISTERIA_TREE)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.0075F, 1))));
//		public static final ConfiguredFeature<?, ?> WISTERIA_TREE_BIG = register("wisteria_tree_big", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(BLUE_WISTERIA_TREE_BIG.weighted(0.5F), WHITE_WISTERIA_TREE_BIG.weighted(0.5F), PINK_WISTERIA_TREE_BIG.weighted(0.5F)), PURPLE_WISTERIA_TREE_BIG)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.0075F, 1))));
//
//		public static final ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE = register("cherry_tree", EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_CONFIG));
//		public static final ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE_BEES_0002 = register("cherry_tree_bees_0002", EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG));
//		public static final ConfiguredFeature<TreeConfiguration, ?> CHERRY_TREE_BEES_005 = register("cherry_tree_bees_005", EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG));
//
//		public static final ConfiguredFeature<?, ?> MARSH_OAK = register("marsh_oak", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(Features.FANCY_OAK.weighted(0.33333334F)), Features.OAK)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.25F, 1))));
//		public static final ConfiguredFeature<?, ?> SWAMP_OAK = register("swamp_oak", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(Features.FANCY_OAK.weighted(0.33333334F)), Features.OAK)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05F, 1))));
//		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_WOODS = register("cherry_tree_blossom_woods", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(CHERRY_TREE.weighted(0.1F)), CHERRY_TREE_BEES_0002)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(8, 0.1F, 1))));
//		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_VALLEYS = register("cherry_tree_blossom_valleys", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(CHERRY_TREE.weighted(0.1F)), CHERRY_TREE_BEES_005)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))));
//		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_WOODS = register("birch_tree_blossom_woods", Features.BIRCH_TALL.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.02F, 2))));
//		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_VALLEYS = register("birch_tree_blossom_valleys", Features.BIRCH_TALL.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.02F, 1))));
//		public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM_MARSH = register("huge_brown_mushroom_marsh", Features.HUGE_BROWN_MUSHROOM.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.32F, 1))));
//
//		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS = register("fallen_cherry_leaves_blossom_woods", EnvironmentalFeatures.FALLEN_LEAVES.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(64)));
//		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS = register("fallen_cherry_leaves_blossom_valleys", EnvironmentalFeatures.FALLEN_LEAVES.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(4)));
//
//		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_WOODS = register("bamboo_blossom_woods", Feature.BAMBOO.configured(new ProbabilityFeatureConfiguration(0.0F)).decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE).squared().decorated(FeatureDecorator.COUNT_NOISE_BIASED.configured(new NoiseCountFactorDecoratorConfiguration(11, 5.0D, 0.2D))));
//		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_WOODS = register("bamboo_light_blossom_woods", Feature.BAMBOO.configured(new ProbabilityFeatureConfiguration(0.0F)).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(20)));
//		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_VALLEYS = register("bamboo_blossom_valleys", Feature.BAMBOO.configured(new ProbabilityFeatureConfiguration(0.0F)).decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE).squared().decorated(FeatureDecorator.COUNT_NOISE_BIASED.configured(new NoiseCountFactorDecoratorConfiguration(2, 5.0D, 0.2D))));
//		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_VALLEYS = register("bamboo_light_blossom_valleys", Feature.BAMBOO.configured(new ProbabilityFeatureConfiguration(0.0F)).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(2)));
//
//		public static final ConfiguredFeature<?, ?> SEAGRASS_MARSH = register("seagrass_marsh", Feature.SEAGRASS.configured(new ProbabilityFeatureConfiguration(0.6F)).count(128).decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE));
		public static final RegistryObject<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> SEAGRASS_MID = register("seagrass_mid", () -> new ConfiguredFeature<>(Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.6F)));
		public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> DISK_MUD = register("disk_mud", () -> new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(EnvironmentalBlocks.MUD.get().defaultBlockState(), UniformInt.of(1, 4), 1, List.of(Blocks.DIRT.defaultBlockState(), EnvironmentalBlocks.MUD.get().defaultBlockState()))));
//
//		public static final ConfiguredFeature<?, ?> SPRING_WATER_MARSH = register("spring_water_marsh", Feature.SPRING.configured(new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).decorated(FeatureDecorator.RANGE_BIASED.configured(new RangeDecoratorConfiguration(8, 8, 256))).squared().count(128));
//		public static final ConfiguredFeature<?, ?> LAKE_WATER_MARSH = register("lake_water_marsh", Feature.LAKE.configured(new BlockStateConfiguration(Blocks.WATER.defaultBlockState())).decorated(FeatureDecorator.WATER_LAKE.configured(new ChanceDecoratorConfiguration(48))));

		private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
			return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
		}

		private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
			return CONFIGURED_FEATURES.register(name, feature);
		}
	}

	public static final class EnvironmentalPlacedFeatures {
		public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Environmental.MOD_ID);

		public static final RegistryObject<PlacedFeature> DISK_MUD = register("disk_mud", EnvironmentalConfiguredFeatures.DISK_MUD, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> SEAGRASS_MARSH = register("seagrass_marsh", EnvironmentalConfiguredFeatures.SEAGRASS_MID, AquaticPlacements.seagrassPlacement(128));

		public static final RegistryObject<PlacedFeature> FLOWER_BLUE_ORCHID = register("flower_blue_orchid", EnvironmentalConfiguredFeatures.FLOWER_BLUE_ORCHID, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_CORNFLOWER = register("flower_cornflower", EnvironmentalConfiguredFeatures.FLOWER_CORNFLOWER, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_DIANTHUS = register("flower_dianthus", EnvironmentalConfiguredFeatures.FLOWER_DIANTHUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_CARTWHEEL = register("flower_cartwheel", EnvironmentalConfiguredFeatures.FLOWER_CARTWHEEL, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_BLUEBELL = register("flower_bluebell", EnvironmentalConfiguredFeatures.FLOWER_BLUEBELL, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_VIOLET = register("flower_violet", EnvironmentalConfiguredFeatures.FLOWER_VIOLET, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_ALLIUM = register("flower_allium", EnvironmentalConfiguredFeatures.FLOWER_ALLIUM, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_RED_LOTUS = register("flower_red_lotus", EnvironmentalConfiguredFeatures.FLOWER_RED_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_WHITE_LOTUS = register("flower_white_lotus", EnvironmentalConfiguredFeatures.FLOWER_WHITE_LOTUS, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> FLOWER_HIBISCUS_WARM = register("flower_hibiscus_warm", EnvironmentalConfiguredFeatures.FLOWER_HIBISCUS_WARM, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_HIBISCUS_COOL = register("flower_hibiscus_cool", EnvironmentalConfiguredFeatures.FLOWER_HIBISCUS_COOL, RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
			return register(name, feature, List.of(placementModifiers));
		}

		@SuppressWarnings("unchecked")
		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placementModifiers) {
			return PLACED_FEATURES.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) feature.getHolder().get(), ImmutableList.copyOf(placementModifiers)));
		}
	}
}