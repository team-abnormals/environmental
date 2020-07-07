package com.team_abnormals.environmental.common.world;

import com.google.common.collect.ImmutableList;
import com.team_abnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.team_abnormals.environmental.common.block.WisteriaLeavesBlock;
import com.team_abnormals.environmental.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class EnvironmentalFeatureConfigs {
	public static final BlockState WISTERIA_LOG = EnvironmentalBlocks.WISTERIA_LOG.get().getDefaultState();
	
	public static final BlockState BLUE_WISTERIA_LEAVES = EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
	public static final BlockState PINK_WISTERIA_LEAVES = EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
	public static final BlockState WHITE_WISTERIA_LEAVES = EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
	public static final BlockState PURPLE_WISTERIA_LEAVES = EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get().getDefaultState().with(WisteriaLeavesBlock.DISTANCE, 1);
	
	public static final BlockClusterFeatureConfig OXEYE_DAISY_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OXEYE_DAISY.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig ALLIUM_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ALLIUM.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig PINK_TULIP_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.PINK_TULIP.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig BLUE_ORCHID_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BLUE_ORCHID.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();

	public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_TOP = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
	public static final BlockState PINK_HANGING_WISTERIA_LEAVES_TOP  = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
	public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_TOP  = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
	public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_TOP  = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.UPPER);
	
	public static final BlockState BLUE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	public static final BlockState PINK_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	public static final BlockState WHITE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	public static final BlockState PURPLE_HANGING_WISTERIA_LEAVES_BOTTOM = EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get().getDefaultState().with(HangingWisteriaLeavesBlock.HALF, DoubleBlockHalf.LOWER);
	
	public static final BlockClusterFeatureConfig WHITE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.WHITE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig PINK_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PINK_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig PURPLE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.PURPLE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig BLUE_DELPHINIUM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.BLUE_DELPHINIUM.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();


	public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORNFLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DUCKWEED.get().getDefaultState()), new SimpleBlockPlacer())).tries(1024).build();
	public static final BlockClusterFeatureConfig GIANT_TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(256).func_227317_b_().build();

	public static final BaseTreeFeatureConfig WILLOW_TREE_CONFIG = (
			new BaseTreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(EnvironmentalBlocks.WILLOW_LOG.get().getDefaultState()), 
					new SimpleBlockStateProvider(EnvironmentalBlocks.WILLOW_LEAVES.get().getDefaultState()), 
					new BlobFoliagePlacer(3, 0, 0, 0, 3), 
					new StraightTrunkPlacer(5, 3, 0), 
					new TwoLayerFeature(1, 0, 1)))
			.func_236701_a_(1)
			.func_236703_a_(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_,HangingWillowLeavesTreeDecorator.field_236871_b_))
			.build();
	
	public static final BaseTreeFeatureConfig BLUE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES),new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig BLUE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig BLUE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).func_236701_a_(1).build();
	
	public static final BaseTreeFeatureConfig PINK_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig PINK_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig PINK_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG),new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).func_236701_a_(1).build();
	
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).func_236701_a_(1).build();
	
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).func_236701_a_(1).build();
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236703_a_(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).func_236701_a_(1).build();

	   
}