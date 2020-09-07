package com.minecraftabnormals.environmental.common.world;

import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.block.WisteriaLeavesBlock;
import com.minecraftabnormals.environmental.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class EnvironmentalFeatureConfigs {

    private static final BeehiveTreeDecorator FEW_BEEHIVES = new BeehiveTreeDecorator(0.002F);
    private static final BeehiveTreeDecorator BEEHIVES = new BeehiveTreeDecorator(0.02F);
    private static final BeehiveTreeDecorator MANY_BEEHIVES = new BeehiveTreeDecorator(0.05F);
    
    public static final BlockState RED_LOTUS_FLOWER = EnvironmentalBlocks.RED_LOTUS_FLOWER.get().getDefaultState();
    public static final BlockState WHITE_LOTUS_FLOWER = EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().getDefaultState();
    public static final BlockState MUD = EnvironmentalBlocks.MUD.get().getDefaultState();

    public static final BlockState WILLOW_LOG = EnvironmentalBlocks.WILLOW_LOG.get().getDefaultState();
    public static final BlockState WILLOW_LEAVES = EnvironmentalBlocks.WILLOW_LEAVES.get().getDefaultState();

    public static final BlockState CHERRY_LOG = EnvironmentalBlocks.CHERRY_LOG.get().getDefaultState();
    public static final BlockState CHERRY_LEAVES = EnvironmentalBlocks.CHERRY_LEAVES.get().getDefaultState();

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

    public static final BlockClusterFeatureConfig CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(Blocks.POPPY.getDefaultState(), 2).addWeightedBlockstate(Blocks.RED_TULIP.getDefaultState(), 2).addWeightedBlockstate(Blocks.WHITE_TULIP.getDefaultState(), 1).addWeightedBlockstate(Blocks.PINK_TULIP.getDefaultState(), 3).addWeightedBlockstate(Blocks.ORANGE_TULIP.getDefaultState(), 1).addWeightedBlockstate(Blocks.LILY_OF_THE_VALLEY.getDefaultState(), 1), SimpleBlockPlacer.field_236447_c_)).tries(64).build();

    public static final BlockClusterFeatureConfig WHITE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
    public static final BlockClusterFeatureConfig PINK_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
    public static final BlockClusterFeatureConfig PURPLE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
    public static final BlockClusterFeatureConfig BLUE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();

    public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORNFLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockClusterFeatureConfig DIANTHUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DIANTHUS.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DUCKWEED.get().getDefaultState()), new SimpleBlockPlacer())).tries(1024).build();
    public static final BlockClusterFeatureConfig GIANT_TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(256).func_227317_b_().build();
    public static final BlockClusterFeatureConfig MYCELIUM_SPROUTS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.MYCELIUM_SPROUTS.get().getDefaultState()), SimpleBlockPlacer.field_236447_c_)).tries(32).build();

    public static final BaseTreeFeatureConfig WILLOW_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WILLOW_LOG), new SimpleBlockStateProvider(WILLOW_LEAVES), new BlobFoliagePlacer(3, 0, 0, 0, 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).func_236701_a_(1).func_236703_a_(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_, HangingWillowLeavesTreeDecorator.field_236871_b_)).build();

    public static final BaseTreeFeatureConfig CHERRY_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(CHERRY_LOG), new SimpleBlockStateProvider(CHERRY_LEAVES), new FancyFoliagePlacer(2, 0, 4, 0, 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();
    public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(FEW_BEEHIVES));
    public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(BEEHIVES));
    public static final BaseTreeFeatureConfig CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG = CHERRY_TREE_CONFIG.func_236685_a_(ImmutableList.of(MANY_BEEHIVES));

    public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), null, null, null)).func_236701_a_(1).build();
    public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(FEW_BEEHIVES));
    public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(BEEHIVES));
    public static final BaseTreeFeatureConfig BLUE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(MANY_BEEHIVES));

    public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), null, null, null)).func_236701_a_(1).build();
    public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(FEW_BEEHIVES));
    public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(BEEHIVES));
    public static final BaseTreeFeatureConfig PINK_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PINK_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(MANY_BEEHIVES));

    public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), null, null, null)).func_236701_a_(1).build();
    public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(FEW_BEEHIVES));
    public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(BEEHIVES));
    public static final BaseTreeFeatureConfig PURPLE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = PURPLE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(MANY_BEEHIVES));

    public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), null, null, null)).func_236701_a_(1).build();
    public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(FEW_BEEHIVES));
    public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_BEEHIVES_CONFIG = BLUE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(BEEHIVES));
    public static final BaseTreeFeatureConfig WHITE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG = WHITE_WISTERIA_TREE_CONFIG.func_236685_a_(ImmutableList.of(MANY_BEEHIVES));
}