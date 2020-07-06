package com.team_abnormals.environmental.core.other;

import com.google.common.collect.ImmutableList;
import com.team_abnormals.environmental.common.block.HangingWisteriaLeavesBlock;
import com.team_abnormals.environmental.common.block.WisteriaLeavesBlock;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;

public class BiomeFeatures {
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

	public static final BaseTreeFeatureConfig BLUE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig BLUE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig BLUE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(BLUE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get()).build();
	
	public static final BaseTreeFeatureConfig PINK_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig PINK_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig PINK_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG),new SimpleBlockStateProvider(PINK_WISTERIA_LEAVES),new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get()).build();
	
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig PURPLE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(PURPLE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get()).build();
	
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_NO_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.002F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get()).build();
	public static final BaseTreeFeatureConfig WHITE_WISTERIA_MORE_BEEHIVES_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(WISTERIA_LOG), new SimpleBlockStateProvider(WHITE_WISTERIA_LEAVES), new BlobFoliagePlacer(2, 0))).decorators(ImmutableList.of(new BeehiveTreeDecorator(0.05F))).setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get()).build();

	   
    public static void addDelphiniums(Biome biome, int count) {
    	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(
    			new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(
    					Feature.RANDOM_PATCH.withConfiguration(WHITE_DELPHINIUM), 
    					Feature.RANDOM_PATCH.withConfiguration(PINK_DELPHINIUM), 
    					Feature.RANDOM_PATCH.withConfiguration(PURPLE_DELPHINIUM), 
    					Feature.RANDOM_PATCH.withConfiguration(BLUE_DELPHINIUM)), 0))
    			.withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(count))));
    }

    public static void addWisteriaTrees(Biome biome, int count, float extraChance, boolean extraFlowers) {
    	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
        		new MultipleRandomFeatureConfig(
        				ImmutableList.of(
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.15F), 
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.15F),
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PINK_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.15F)), 
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_NO_BEEHIVES_CONFIG)))
        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
    	
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
        		new MultipleRandomFeatureConfig(
        				ImmutableList.of(
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.5F), 
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.5F),
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PINK_WISTERIA_NO_BEEHIVES_CONFIG).withChance(0.5F)), 
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_NO_BEEHIVES_CONFIG)))
        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count / 2, extraChance, 1))));
    }
    
    public static void addWisteriaTreesBeehive(Biome biome, int count, float extraChance, boolean extraFlowers) {
    	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
        		new MultipleRandomFeatureConfig(
        				ImmutableList.of(
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_CONFIG).withChance(0.15F), 
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_CONFIG).withChance(0.15F),
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PINK_WISTERIA_CONFIG).withChance(0.15F)), 
        						EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_CONFIG)))
        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
    	
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
        		new MultipleRandomFeatureConfig(
        				ImmutableList.of(
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_CONFIG).withChance(0.5F), 
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_CONFIG).withChance(0.5F),
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PINK_WISTERIA_CONFIG).withChance(0.5F)), 
        						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_CONFIG)))
        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count / 2, extraChance, 1))));
    }

    public static void addWisteriaTreeBeehive(Biome biome, WisteriaColor color, int count, float extraChance, boolean extraFlowers) {
        switch (color) {
            case BLUE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                
//                if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BLUE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case WHITE:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
            	
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(WHITE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(OXEYE_DAISY_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case PINK:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PINK_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PINK_WISTERIA_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
            	
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PINK_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PINK_TULIP_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case PURPLE:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
            	
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PURPLE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ALLIUM_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
        }
    }
    
    public static void addWisteriaTree(Biome biome, WisteriaColor color, int count, float extraChance, boolean extraFlowers) {
        switch (color) {
            case BLUE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(BLUE_WISTERIA_NO_BEEHIVES_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
//                if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BLUE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case WHITE:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(WHITE_WISTERIA_NO_BEEHIVES_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(WHITE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(OXEYE_DAISY_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case PINK:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PINK_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PINK_WISTERIA_NO_BEEHIVES_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PINK_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PINK_TULIP_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
            case PURPLE:
            	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                		new MultipleRandomFeatureConfig(
                				ImmutableList.of(
                						EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_CONFIG).withChance(0.2F)), 
                				EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(PURPLE_WISTERIA_NO_BEEHIVES_CONFIG)))
                		.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
//            	if (extraFlowers) {
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(PURPLE_DELPHINIUM).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//                	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ALLIUM_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
//            	}
                break;
        }
    }
}