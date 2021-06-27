  
package com.minecraftabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.world.gen.feature.*;
import com.minecraftabnormals.environmental.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.OptionalInt;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Environmental.MOD_ID);
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> DIRECTIONAL_FLOWER = FEATURES.register("directional_flower", () -> new DirectionalFlowersFeature<>(BlockClusterFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoFeatureConfig.CODEC));

	public static final RegistryObject<Feature<NoFeatureConfig>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoFeatureConfig.CODEC));

	public static final RegistryObject<Feature<NoFeatureConfig>> CHICKEN_NEST = FEATURES.register("chicken_nest", () -> new ChickenNestFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> DUCK_NEST = FEATURES.register("duck_nest", () -> new DuckNestFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> TURKEY_NEST = FEATURES.register("turkey_nest", () -> new TurkeyNestFeature(NoFeatureConfig.CODEC));

	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CHERRY_TREE = FEATURES.register("cherry_tree", () -> new CherryTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BIG_WISTERIA_TREE = FEATURES.register("big_wisteria_tree", () -> new BigWisteriaTreeFeature(BaseTreeFeatureConfig.CODEC));

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
		public static final BlockClusterFeatureConfig CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(Blocks.POPPY.defaultBlockState(), 2).add(Blocks.RED_TULIP.defaultBlockState(), 2).add(Blocks.WHITE_TULIP.defaultBlockState(), 1).add(Blocks.DANDELION.defaultBlockState(), 1).add(Blocks.PINK_TULIP.defaultBlockState(), 3).add(Blocks.ORANGE_TULIP.defaultBlockState(), 1).add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();

		public static final BlockClusterFeatureConfig WHITE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_DELPHINIUM.get().defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig PINK_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_DELPHINIUM.get().defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig PURPLE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_DELPHINIUM.get().defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig BLUE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUE_DELPHINIUM.get().defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build();

		public static final BlockClusterFeatureConfig CARTWHEEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.CARTWHEEL.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig BLUE_ORCHID_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BLUE_ORCHID.defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORNFLOWER.defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig DIANTHUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DIANTHUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig BLUEBELL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUEBELL.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig ALLIUM_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ALLIUM.defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig VIOLET_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.VIOLET.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig WHITE_LOTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig RED_LOTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.RED_LOTUS_FLOWER.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();

		public static final BlockClusterFeatureConfig YELLOW_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.YELLOW_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig ORANGE_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.ORANGE_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig PINK_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig RED_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.RED_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig MAGENTA_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.MAGENTA_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig PURPLE_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_HIBISCUS.get().defaultBlockState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig BIRD_OF_PARADISE_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BIRD_OF_PARADISE.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE)).tries(64).noProjection().build();

		public static final BlockClusterFeatureConfig LILY_PAD_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_PAD.defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(10).build();
		public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DUCKWEED.get().defaultBlockState()), new SimpleBlockPlacer())).tries(1024).build();
		public static final BlockClusterFeatureConfig GIANT_TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().defaultBlockState()), new DoublePlantBlockPlacer())).tries(256).noProjection().build();
		public static final BlockClusterFeatureConfig MYCELIUM_SPROUTS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(32).build();
		public static final BlockClusterFeatureConfig TALL_DEAD_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_DEAD_BUSH), new DoublePlantBlockPlacer())).tries(64).noProjection().build();

		public static final BaseTreeFeatureConfig WILLOW_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WILLOW_LOG), new SimpleBlockStateProvider(States.WILLOW_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).maxWaterDepth(1).decorators(ImmutableList.of(LeaveVineTreeDecorator.INSTANCE, HangingWillowLeavesTreeDecorator.INSTANCE)).build();

		public static final BaseTreeFeatureConfig CHERRY_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new FancyFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new FancyTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(0)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002));
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002));
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005));

		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).maxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002));
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002));
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005));

		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).maxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002));
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002));
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005));

		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).maxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002));
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002));
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005));

		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).maxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002));
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002));
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005));
	}

	public static final class Configured {
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_MARSH = Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(5));
		public static final ConfiguredFeature<?, ?> PATCH_WATERLILLY_MARSH = Feature.RANDOM_PATCH.configured(Configs.LILY_PAD_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(1));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS_MARSH = Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(128));
		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_SWAMP = Feature.RANDOM_PATCH.configured(Configs.DUCKWEED_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.15F, 1)));
		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_MARSH = Feature.RANDOM_PATCH.configured(Configs.DUCKWEED_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.15F, 1)));
		public static final ConfiguredFeature<?, ?> PATCH_MYCELIUM_SPROUTS = Feature.RANDOM_PATCH.configured(Configs.MYCELIUM_SPROUTS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(5));
		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS_DENSE = EnvironmentalFeatures.DENSE_CATTAILS.get().configured(new NoFeatureConfig()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((8)));
		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS = EnvironmentalFeatures.CATTAILS.get().configured(new NoFeatureConfig()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((12)));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH = Feature.RANDOM_PATCH.configured(Configs.TALL_DEAD_BUSH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((1)));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH_MESA = Feature.RANDOM_PATCH.configured(Configs.TALL_DEAD_BUSH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((6)));
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_PLAINS = Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_SAVANNA = Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_JUNGLE = Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_MARSH = Feature.RANDOM_PATCH.configured(Configs.GIANT_TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(65);
		public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_BLOSSOM = Feature.RANDOM_PATCH.configured(Features.Configs.SUGAR_CANE_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(12));
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_BLOSSOM_WOODS = Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(8));
		public static final ConfiguredFeature<?, ?> PATCH_WHITE_DELPHINIUM = Feature.RANDOM_PATCH.configured(Configs.WHITE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_PINK_DELPHINIUM = Feature.RANDOM_PATCH.configured(Configs.PINK_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_PURPLE_DELPHINIUM = Feature.RANDOM_PATCH.configured(Configs.PURPLE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_BLUE_DELPHINIUM = Feature.RANDOM_PATCH.configured(Configs.BLUE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_DELPHINIUMS = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(ImmutableList.of(() -> PATCH_WHITE_DELPHINIUM, () -> PATCH_PINK_DELPHINIUM, () -> PATCH_PURPLE_DELPHINIUM, () -> PATCH_BLUE_DELPHINIUM))).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(12));
		public static final ConfiguredFeature<?, ?> PATCH_BIRD_OF_PARADISE = Feature.RANDOM_PATCH.configured(Configs.BIRD_OF_PARADISE_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(5));

		public static final ConfiguredFeature<?, ?> FLOWER_BLUE_ORCHID = Feature.FLOWER.configured(Configs.BLUE_ORCHID_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_CORNFLOWER = Feature.FLOWER.configured(Configs.CORNFLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_DIANTHUS = Feature.FLOWER.configured(Configs.DIANTHUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> FLOWER_CARTWHEEL = EnvironmentalFeatures.DIRECTIONAL_FLOWER.get().configured(Configs.CARTWHEEL_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(5));
		public static final ConfiguredFeature<?, ?> FLOWER_RED_LOTUS = Feature.FLOWER.configured(Configs.RED_LOTUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> FLOWER_WHITE_LOTUS = Feature.FLOWER.configured(Configs.WHITE_LOTUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> FLOWER_ALLIUM = Feature.FLOWER.configured(Configs.ALLIUM_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> FLOWER_VIOLET = Feature.FLOWER.configured(Configs.VIOLET_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4);
		public static final ConfiguredFeature<?, ?> FLOWER_BLUEBELL = Feature.FLOWER.configured(Configs.BLUEBELL_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(3);
		public static final ConfiguredFeature<?, ?> FLOWER_YELLOW_HIBISCUS = Feature.FLOWER.configured(Configs.YELLOW_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_ORANGE_HIBISCUS = Feature.FLOWER.configured(Configs.ORANGE_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_RED_HIBISCUS = Feature.FLOWER.configured(Configs.RED_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_PINK_HIBISCUS = Feature.FLOWER.configured(Configs.PINK_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_MAGENTA_HIBISCUS = Feature.FLOWER.configured(Configs.MAGENTA_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_PURPLE_HIBISCUS = Feature.FLOWER.configured(Configs.PURPLE_HIBISCUS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> FLOWER_BLOSSOM_WOODS = Feature.FLOWER.configured(Configs.CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((4)));
		public static final ConfiguredFeature<?, ?> FLOWER_TALL_BLOSSOM_WOODS = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(ImmutableList.of(() -> Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ROSE_BUSH.defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build()), () -> Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.PEONY.defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build()), () -> Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILAC.defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build())))).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count((6)));

		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().configured(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().configured(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> WISTERIA_TREE = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(BLUE_WISTERIA_TREE.weighted(0.15F), WHITE_WISTERIA_TREE.weighted(0.15F), PINK_WISTERIA_TREE.weighted(0.15F)), PURPLE_WISTERIA_TREE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.0075F, 1)));
		public static final ConfiguredFeature<?, ?> WISTERIA_TREE_BIG = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(BLUE_WISTERIA_TREE_BIG.weighted(0.5F), WHITE_WISTERIA_TREE_BIG.weighted(0.5F), PINK_WISTERIA_TREE_BIG.weighted(0.5F)), PURPLE_WISTERIA_TREE_BIG)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.0075F, 1)));

		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE = EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_CONFIG);
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_0002 = EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_005 = EnvironmentalFeatures.CHERRY_TREE.get().configured(Configs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG);

		public static final ConfiguredFeature<?, ?> WILLOW_TREE = Feature.TREE.configured(Configs.WILLOW_TREE_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> MARSH_OAK = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(Features.FANCY_OAK.weighted(0.33333334F)), Features.OAK)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.25F, 1)));
		public static final ConfiguredFeature<?, ?> SWAMP_OAK = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(Features.FANCY_OAK.weighted(0.33333334F)), Features.OAK)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.05F, 1)));
		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_WOODS = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.weighted(0.1F)), CHERRY_TREE_BEES_0002)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(8, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_VALLEYS = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.weighted(0.1F)), CHERRY_TREE_BEES_005)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_WOODS = Features.BIRCH_TALL.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.02F, 2)));
		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_VALLEYS = Features.BIRCH_TALL.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.02F, 1)));
		public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM_MARSH = Features.HUGE_BROWN_MUSHROOM.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.32F, 1)));

		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS = EnvironmentalFeatures.FALLEN_LEAVES.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(64));
		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS = EnvironmentalFeatures.FALLEN_LEAVES.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(4));

		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_WOODS = Feature.BAMBOO.configured(new ProbabilityConfig(0.0F)).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).squared().decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(11, 5.0D, 0.2D)));
		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_WOODS = Feature.BAMBOO.configured(new ProbabilityConfig(0.0F)).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(20));
		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_VALLEYS = Feature.BAMBOO.configured(new ProbabilityConfig(0.0F)).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).squared().decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(2, 5.0D, 0.2D)));
		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_VALLEYS = Feature.BAMBOO.configured(new ProbabilityConfig(0.0F)).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.count(2));

		public static final ConfiguredFeature<?, ?> SEAGRASS_MARSH = Feature.SEAGRASS.configured(new ProbabilityConfig(0.6F)).count(128).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);
		public static final ConfiguredFeature<?, ?> DISK_MUD = Feature.DISK.configured(new SphereReplaceConfig(States.MUD, FeatureSpread.of(4, 1), 1, ImmutableList.of(Blocks.DIRT.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), States.MUD))).decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE);

		public static final ConfiguredFeature<?, ?> NEST_CHICKEN = EnvironmentalFeatures.CHICKEN_NEST.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE.chance(32));
		public static final ConfiguredFeature<?, ?> NEST_DUCK = EnvironmentalFeatures.DUCK_NEST.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE.chance(32));
		public static final ConfiguredFeature<?, ?> NEST_TURKEY = EnvironmentalFeatures.TURKEY_NEST.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE.chance(32));

		public static final ConfiguredFeature<?, ?> SPRING_WATER_MARSH = Feature.SPRING.configured(new LiquidsConfig(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(8, 8, 256))).squared().count(128);
		public static final ConfiguredFeature<?, ?> LAKE_WATER_MARSH = Feature.LAKE.configured(new BlockStateFeatureConfig(Blocks.WATER.defaultBlockState())).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(48)));

		private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Environmental.MOD_ID, name), configuredFeature);
		}

		public static void registerConfiguredFeatures() {
			register("patch_grass_marsh", PATCH_GRASS_MARSH);
			register("patch_waterlilly_marsh", PATCH_WATERLILLY_MARSH);
			register("patch_tall_grass_marsh", PATCH_TALL_GRASS_MARSH);
			register("patch_duckweed_swamp", PATCH_DUCKWEED_SWAMP);
			register("patch_duckweed_marsh", PATCH_DUCKWEED_MARSH);
			register("patch_mycelium_sprouts", PATCH_MYCELIUM_SPROUTS);
			register("patch_cattails", PATCH_CATTAILS);
			register("patch_cattails_dense", PATCH_CATTAILS_DENSE);
			register("patch_tall_dead_bush", PATCH_TALL_DEAD_BUSH);
			register("patch_tall_dead_bush_mesa", PATCH_TALL_DEAD_BUSH_MESA);
			register("patch_giant_tall_grass_plains", PATCH_GIANT_TALL_GRASS_PLAINS);
			register("patch_giant_tall_grass_savanna", PATCH_GIANT_TALL_GRASS_SAVANNA);
			register("patch_giant_tall_grass_jungle", PATCH_GIANT_TALL_GRASS_JUNGLE);
			register("patch_giant_tall_grass_marsh", PATCH_GIANT_TALL_GRASS_MARSH);
			register("patch_sugar_cane_blossom", PATCH_SUGAR_CANE_BLOSSOM);
			register("patch_grass_blossom_woods", PATCH_GRASS_BLOSSOM_WOODS);
			register("patch_white_delphinium", PATCH_WHITE_DELPHINIUM);
			register("patch_pink_delphinium", PATCH_PINK_DELPHINIUM);
			register("patch_purple_delphinium", PATCH_PURPLE_DELPHINIUM);
			register("patch_blue_delphinium", PATCH_BLUE_DELPHINIUM);
			register("patch_delphiniums", PATCH_DELPHINIUMS);
			register("patch_bird_of_paradise", PATCH_BIRD_OF_PARADISE);

			register("flower_blue_orchid", FLOWER_BLUE_ORCHID);
			register("flower_cornflower", FLOWER_CORNFLOWER);
			register("flower_dianthus", FLOWER_DIANTHUS);
			register("flower_cartwheel", FLOWER_CARTWHEEL);
			register("flower_red_lotus", FLOWER_RED_LOTUS);
			register("flower_white_lotus", FLOWER_WHITE_LOTUS);
			register("flower_allium", FLOWER_ALLIUM);
			register("flower_violet", FLOWER_VIOLET);
			register("flower_bluebell", FLOWER_BLUEBELL);
			register("flower_yellow_hibiscus", FLOWER_YELLOW_HIBISCUS);
			register("flower_orange_hibiscus", FLOWER_ORANGE_HIBISCUS);
			register("flower_red_hibiscus", FLOWER_RED_HIBISCUS);
			register("flower_pink_hibiscus", FLOWER_PINK_HIBISCUS);
			register("flower_magenta_hibiscus", FLOWER_MAGENTA_HIBISCUS);
			register("flower_purple_hibiscus", FLOWER_PURPLE_HIBISCUS);
			register("flower_blossom_woods", FLOWER_BLOSSOM_WOODS);
			register("flower_tall_blossom_woods", FLOWER_TALL_BLOSSOM_WOODS);

			register("blue_wisteria_tree", BLUE_WISTERIA_TREE);
			register("blue_wisteria_tree_big", BLUE_WISTERIA_TREE_BIG);
			register("white_wisteria_tree", WHITE_WISTERIA_TREE);
			register("white_wisteria_tree_big", WHITE_WISTERIA_TREE_BIG);
			register("pink_wisteria_tree", PINK_WISTERIA_TREE);
			register("pink_wisteria_tree_big", PINK_WISTERIA_TREE_BIG);
			register("purple_wisteria_tree", PURPLE_WISTERIA_TREE);
			register("purple_wisteria_tree_big", PURPLE_WISTERIA_TREE_BIG);
			register("wisteria_tree", WISTERIA_TREE);
			register("wisteria_tree_big", WISTERIA_TREE_BIG);

			register("cherry_tree", CHERRY_TREE);
			register("cherry_tree_bees_0002", CHERRY_TREE_BEES_0002);
			register("cherry_tree_bees_005", CHERRY_TREE_BEES_005);

			register("willow_tree", WILLOW_TREE);
			register("marsh_oak", MARSH_OAK);
			register("swamp_oak", SWAMP_OAK);
			register("cherry_tree_blossom_woods", CHERRY_TREE_BLOSSOM_WOODS);
			register("cherry_tree_blossom_valleys", CHERRY_TREE_BLOSSOM_VALLEYS);
			register("birch_tree_blossom_woods", BIRCH_TREE_BLOSSOM_WOODS);
			register("birch_tree_blossom_valleys", BIRCH_TREE_BLOSSOM_VALLEYS);
			register("huge_brown_mushroom_marsh", HUGE_BROWN_MUSHROOM_MARSH);

			register("fallen_cherry_leaves_blossom_woods", FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS);
			register("fallen_cherry_leaves_blossom_valleys", FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS);

			register("bamboo_blossom_woods", BAMBOO_BLOSSOM_WOODS);
			register("bamboo_light_blossom_woods", BAMBOO_LIGHT_BLOSSOM_WOODS);
			register("bamboo_blossom_valleys", BAMBOO_BLOSSOM_VALLEYS);
			register("bamboo_light_blossom_valleys", BAMBOO_LIGHT_BLOSSOM_VALLEYS);

			register("seagrass_marsh", SEAGRASS_MARSH);
			register("disk_mud", DISK_MUD);

			register("nest_chicken", NEST_CHICKEN);
			register("nest_duck", NEST_DUCK);
			register("nest_turkey", NEST_TURKEY);

			register("spring_water_marsh", SPRING_WATER_MARSH);
			register("lake_water_marsh", LAKE_WATER_MARSH);
		}
	}
}