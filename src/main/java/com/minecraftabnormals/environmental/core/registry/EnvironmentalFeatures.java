  
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

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> DIRECTIONAL_FLOWER = FEATURES.register("directional_flower", () -> new DirectionalFlowersFeature<>(BlockClusterFeatureConfig.field_236587_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoFeatureConfig.field_236558_a_));

	public static final RegistryObject<Feature<NoFeatureConfig>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> RICE = FEATURES.register("rice", () -> new RiceFeature(NoFeatureConfig.field_236558_a_));

	public static final RegistryObject<Feature<NoFeatureConfig>> CHICKEN_NEST = FEATURES.register("chicken_nest", () -> new ChickenNestFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> DUCK_NEST = FEATURES.register("duck_nest", () -> new DuckNestFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<NoFeatureConfig>> TURKEY_NEST = FEATURES.register("turkey_nest", () -> new TurkeyNestFeature(NoFeatureConfig.field_236558_a_));

	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CHERRY_TREE = FEATURES.register("cherry_tree", () -> new CherryTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BIG_WISTERIA_TREE = FEATURES.register("big_wisteria_tree", () -> new BigWisteriaTreeFeature(BaseTreeFeatureConfig.CODEC));

	public static final RegistryObject<TreeDecoratorType<?>> HANGING_WILLOW_LEAVES = TREE_DECORATORS.register("hanging_willow_leaves", () -> new TreeDecoratorType<>(HangingWillowLeavesTreeDecorator.CODEC));


	public static final class States {
		public static final BlockState MUD = EnvironmentalBlocks.MUD.get().getDefaultState();

		public static final BlockState WILLOW_LOG = EnvironmentalBlocks.WILLOW_LOG.get().getDefaultState();
		public static final BlockState WILLOW_LEAVES = EnvironmentalBlocks.WILLOW_LEAVES.get().getDefaultState();

		public static final BlockState CHERRY_LOG = EnvironmentalBlocks.CHERRY_LOG.get().getDefaultState();
		public static final BlockState CHERRY_LEAVES = EnvironmentalBlocks.CHERRY_LEAVES.get().getDefaultState();

		public static final BlockState TALL_DEAD_BUSH = EnvironmentalBlocks.TALL_DEAD_BUSH.get().getDefaultState();

		public static final BlockState WISTERIA_LOG = EnvironmentalBlocks.WISTERIA_LOG.get().getDefaultState();
		public static final BlockState BLUE_WISTERIA_LEAVES = EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState PINK_WISTERIA_LEAVES = EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState WHITE_WISTERIA_LEAVES = EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
		public static final BlockState PURPLE_WISTERIA_LEAVES = EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);

		public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState PINK_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
		public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);

		public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState PINK_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
		public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	}

	public static final class Configs {
		public static final BlockClusterFeatureConfig CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(Blocks.POPPY.getDefaultState(), 2).addWeightedBlockstate(Blocks.RED_TULIP.getDefaultState(), 2).addWeightedBlockstate(Blocks.WHITE_TULIP.getDefaultState(), 1).addWeightedBlockstate(Blocks.PINK_TULIP.getDefaultState(), 3).addWeightedBlockstate(Blocks.ORANGE_TULIP.getDefaultState(), 1).addWeightedBlockstate(Blocks.LILY_OF_THE_VALLEY.getDefaultState(), 1), SimpleBlockPlacer.PLACER)).tries(64).build();

		public static final BlockClusterFeatureConfig WHITE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
		public static final BlockClusterFeatureConfig PINK_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
		public static final BlockClusterFeatureConfig PURPLE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
		public static final BlockClusterFeatureConfig BLUE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();

		public static final BlockClusterFeatureConfig CARTWHEEL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.CARTWHEEL.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(32).build();
		public static final BlockClusterFeatureConfig BLUE_ORCHID_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BLUE_ORCHID.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORNFLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig DIANTHUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DIANTHUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig BLUEBELL_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUEBELL.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig ALLIUM_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ALLIUM.getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig VIOLET_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.VIOLET.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig WHITE_LOTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig RED_LOTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.RED_LOTUS_FLOWER.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();

		public static final BlockClusterFeatureConfig YELLOW_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.YELLOW_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig ORANGE_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.ORANGE_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig PINK_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig RED_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.RED_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig MAGENTA_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.MAGENTA_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig PURPLE_HIBISCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_HIBISCUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		public static final BlockClusterFeatureConfig BIRD_OF_PARADISE_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BIRD_OF_PARADISE.get().getDefaultState()), DoublePlantBlockPlacer.PLACER)).tries(64).func_227317_b_().build();

		public static final BlockClusterFeatureConfig LILY_PAD_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_PAD.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(10).build();
		public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DUCKWEED.get().getDefaultState()), new SimpleBlockPlacer())).tries(1024).build();
		public static final BlockClusterFeatureConfig GIANT_TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(256).func_227317_b_().build();
		public static final BlockClusterFeatureConfig MYCELIUM_SPROUTS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.MYCELIUM_SPROUTS.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(32).build();
		public static final BlockClusterFeatureConfig TALL_DEAD_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_DEAD_BUSH), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();

		public static final BaseTreeFeatureConfig WILLOW_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WILLOW_LOG), new SimpleBlockStateProvider(States.WILLOW_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).setMaxWaterDepth(1).setDecorators(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_, HangingWillowLeavesTreeDecorator.field_236871_b_)).build();

		public static final BaseTreeFeatureConfig CHERRY_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.CHERRY_LOG), new SimpleBlockStateProvider(States.CHERRY_LEAVES), new FancyFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new FancyTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(0)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT));
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT));
		public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT));

		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).setMaxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT));
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT));
		public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT));

		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).setMaxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT));
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT));
		public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT));

		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).setMaxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT));
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT));
		public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT));

		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.WISTERIA_LOG), new SimpleBlockStateProvider(States.WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).setMaxWaterDepth(1).build();
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT));
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT));
		public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT));
	}

	public static final class Configured {
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_MARSH = Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(5));
		public static final ConfiguredFeature<?, ?> PATCH_WATERLILLY_MARSH = Feature.RANDOM_PATCH.withConfiguration(Configs.LILY_PAD_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(1));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS_MARSH = Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TALL_GRASS_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(128));
		public static final ConfiguredFeature<?, ?> PATCH_RICE = EnvironmentalFeatures.RICE.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((2)));
		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_SWAMP = Feature.RANDOM_PATCH.withConfiguration(Configs.DUCKWEED_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.15F, 1)));
		public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED_MARSH = Feature.RANDOM_PATCH.withConfiguration(Configs.DUCKWEED_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.15F, 1)));
		public static final ConfiguredFeature<?, ?> PATCH_MYCELIUM_SPROUTS = Feature.RANDOM_PATCH.withConfiguration(Configs.MYCELIUM_SPROUTS_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(5));
		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS_DENSE = EnvironmentalFeatures.DENSE_CATTAILS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((8)));
		public static final ConfiguredFeature<?, ?> PATCH_CATTAILS = EnvironmentalFeatures.CATTAILS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((12)));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH = Feature.RANDOM_PATCH.withConfiguration(Configs.TALL_DEAD_BUSH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((1)));
		public static final ConfiguredFeature<?, ?> PATCH_TALL_DEAD_BUSH_MESA = Feature.RANDOM_PATCH.withConfiguration(Configs.TALL_DEAD_BUSH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((6)));
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_PLAINS = Feature.RANDOM_PATCH.withConfiguration(Configs.GIANT_TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_SAVANNA = Feature.RANDOM_PATCH.withConfiguration(Configs.GIANT_TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_JUNGLE = Feature.RANDOM_PATCH.withConfiguration(Configs.GIANT_TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4);
		public static final ConfiguredFeature<?, ?> PATCH_GIANT_TALL_GRASS_MARSH = Feature.RANDOM_PATCH.withConfiguration(Configs.GIANT_TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(65);
		public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_BLOSSOM = Feature.RANDOM_PATCH.withConfiguration(Features.Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(12));
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_BLOSSOM_WOODS = Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(8));
		public static final ConfiguredFeature<?, ?> PATCH_WHITE_DELPHINIUM = Feature.RANDOM_PATCH.withConfiguration(Configs.WHITE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_PINK_DELPHINIUM = Feature.RANDOM_PATCH.withConfiguration(Configs.PINK_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_PURPLE_DELPHINIUM = Feature.RANDOM_PATCH.withConfiguration(Configs.PURPLE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_BLUE_DELPHINIUM = Feature.RANDOM_PATCH.withConfiguration(Configs.BLUE_DELPHINIUM);
		public static final ConfiguredFeature<?, ?> PATCH_DELPHINIUMS = Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(() -> PATCH_WHITE_DELPHINIUM, () -> PATCH_PINK_DELPHINIUM, () -> PATCH_PURPLE_DELPHINIUM, () -> PATCH_BLUE_DELPHINIUM))).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(12));
		public static final ConfiguredFeature<?, ?> PATCH_BIRD_OF_PARADISE = Feature.RANDOM_PATCH.withConfiguration(Configs.BIRD_OF_PARADISE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(5));

		public static final ConfiguredFeature<?, ?> FLOWER_BLUE_ORCHID = Feature.FLOWER.withConfiguration(Configs.BLUE_ORCHID_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_CORNFLOWER = Feature.FLOWER.withConfiguration(Configs.CORNFLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_DIANTHUS = Feature.FLOWER.withConfiguration(Configs.DIANTHUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> FLOWER_CARTWHEEL = EnvironmentalFeatures.DIRECTIONAL_FLOWER.get().withConfiguration(Configs.CARTWHEEL_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(5));
		public static final ConfiguredFeature<?, ?> FLOWER_RED_LOTUS = Feature.FLOWER.withConfiguration(Configs.RED_LOTUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> FLOWER_WHITE_LOTUS = Feature.FLOWER.withConfiguration(Configs.WHITE_LOTUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> FLOWER_ALLIUM = Feature.FLOWER.withConfiguration(Configs.ALLIUM_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> FLOWER_VIOLET = Feature.FLOWER.withConfiguration(Configs.VIOLET_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4);
		public static final ConfiguredFeature<?, ?> FLOWER_BLUEBELL = Feature.FLOWER.withConfiguration(Configs.BLUEBELL_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3);
		public static final ConfiguredFeature<?, ?> FLOWER_YELLOW_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.YELLOW_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_ORANGE_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.ORANGE_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_RED_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.RED_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_PINK_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.PINK_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_MAGENTA_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.MAGENTA_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_PURPLE_HIBISCUS = Feature.FLOWER.withConfiguration(Configs.PURPLE_HIBISCUS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);
		public static final ConfiguredFeature<?, ?> FLOWER_BLOSSOM_WOODS = Feature.FLOWER.withConfiguration(Configs.CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((4)));
		public static final ConfiguredFeature<?, ?> FLOWER_TALL_BLOSSOM_WOODS = Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(() -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ROSE_BUSH.getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build()), () -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.PEONY.getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build())))).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b((6)));

		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE = EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);

		public static final ConfiguredFeature<?, ?> BLUE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(Configs.BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> WHITE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(Configs.WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PINK_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(Configs.PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<?, ?> PURPLE_WISTERIA_TREE_BIG = EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(Configs.PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG);

		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE = EnvironmentalFeatures.CHERRY_TREE.get().withConfiguration(Configs.CHERRY_TREE_CONFIG);
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_0002 = EnvironmentalFeatures.CHERRY_TREE.get().withConfiguration(Configs.CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG);
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE_BEES_005 = EnvironmentalFeatures.CHERRY_TREE.get().withConfiguration(Configs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG);

		public static final ConfiguredFeature<?, ?> WILLOW_TREE = Feature.TREE.withConfiguration(Configs.WILLOW_TREE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> MARSH_OAK = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.FANCY_OAK.withChance(0.33333334F)), Features.OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.25F, 1)));
		public static final ConfiguredFeature<?, ?> SWAMP_OAK = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.FANCY_OAK.withChance(0.33333334F)), Features.OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1)));
		public static final ConfiguredFeature<?, ?> WISTERIA_TREE = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BLUE_WISTERIA_TREE.withChance(0.15F), WHITE_WISTERIA_TREE.withChance(0.15F), PINK_WISTERIA_TREE.withChance(0.15F)), PURPLE_WISTERIA_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.0075F, 1)));
		public static final ConfiguredFeature<?, ?> WISTERIA_TREE_BIG = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BLUE_WISTERIA_TREE_BIG.withChance(0.5F), WHITE_WISTERIA_TREE_BIG.withChance(0.5F), PINK_WISTERIA_TREE_BIG.withChance(0.5F)), PURPLE_WISTERIA_TREE_BIG)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.0075F, 1)));
		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_WOODS = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.withChance(0.1F)), CHERRY_TREE_BEES_0002)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(8, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> CHERRY_TREE_BLOSSOM_VALLEYS = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(CHERRY_TREE.withChance(0.1F)), CHERRY_TREE_BEES_005)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_WOODS = Features.BIRCH_TALL.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.02F, 2)));
		public static final ConfiguredFeature<?, ?> BIRCH_TREE_BLOSSOM_VALLEYS = Features.BIRCH_TALL.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.02F, 1)));
		public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM_MARSH = Features.HUGE_BROWN_MUSHROOM.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.32F, 1)));

		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS = EnvironmentalFeatures.FALLEN_LEAVES.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(64));
		public static final ConfiguredFeature<?, ?> FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS = EnvironmentalFeatures.FALLEN_LEAVES.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(4));

		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_WOODS = Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Features.Placements.BAMBOO_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(11, 5.0D, 0.2D)));
		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_WOODS = Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(20));
		public static final ConfiguredFeature<?, ?> BAMBOO_BLOSSOM_VALLEYS = Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Features.Placements.BAMBOO_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(2, 5.0D, 0.2D)));
		public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT_BLOSSOM_VALLEYS = Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(2));

		public static final ConfiguredFeature<?, ?> SEAGRASS_MARSH = Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.6F)).func_242731_b(128).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
		public static final ConfiguredFeature<?, ?> DISK_MUD = Feature.DISK.withConfiguration(new SphereReplaceConfig(States.MUD, FeatureSpread.func_242253_a(4, 1), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), States.MUD))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

		public static final ConfiguredFeature<?, ?> NEST_CHICKEN = EnvironmentalFeatures.CHICKEN_NEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT.chance(32));
		public static final ConfiguredFeature<?, ?> NEST_DUCK = EnvironmentalFeatures.DUCK_NEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT.chance(32));
		public static final ConfiguredFeature<?, ?> NEST_TURKEY = EnvironmentalFeatures.TURKEY_NEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT.chance(32));

		public static final ConfiguredFeature<?, ?> SPRING_WATER_MARSH = Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(Placement.RANGE_BIASED.configure(new TopSolidRangeConfig(8, 8, 256))).square().func_242731_b(128);
		public static final ConfiguredFeature<?, ?> LAKE_WATER_MARSH = Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(48)));

		private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Environmental.MOD_ID, name), configuredFeature);
		}

		public static void registerConfiguredFeatures() {
			register("patch_grass_marsh", PATCH_GRASS_MARSH);
			register("patch_waterlilly_marsh", PATCH_WATERLILLY_MARSH);
			register("patch_tall_grass_marsh", PATCH_TALL_GRASS_MARSH);
			register("patch_rice", PATCH_RICE);
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