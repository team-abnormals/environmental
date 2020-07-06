package com.team_abnormals.environmental.common.world.biome;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.team_abnormals.environmental.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraftforge.fml.ModList;

public class EnvironmentalBiomeFeatures {
	public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORNFLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
	public static final BlockClusterFeatureConfig DUCKWEED_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.DUCKWEED.get().getDefaultState()), new SimpleBlockPlacer())).tries(1024).build();
	public static final BlockClusterFeatureConfig GIANT_TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(EnvironmentalBlocks.GIANT_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(256).func_227317_b_().build();

	
	public static final TreeFeatureConfig WILLOW_TREE_CONFIG = (
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(EnvironmentalBlocks.WILLOW_LOG.get().getDefaultState()), 
					new SimpleBlockStateProvider(EnvironmentalBlocks.WILLOW_LEAVES.get().getDefaultState()), 
					new BlobFoliagePlacer(3, 0)))
			.baseHeight(5)
			.heightRandA(3)
			.foliageHeight(3)
			.maxWaterDepth(1)
			.decorators(ImmutableList.of(new LeaveVineTreeDecorator(), new HangingWillowLeavesTreeDecorator()))
			.setSapling((net.minecraftforge.common.IPlantable)EnvironmentalBlocks.WILLOW_SAPLING.get()).build();
	
	public static void addMushrooms(Biome biome) {
		if (!ModList.get().isLoaded("enhanced_mushrooms"))
			biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM), Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM))).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(1, 0.25F))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(1, 0.125F))));
	}
	
	public static void addMarshMushrooms(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.32F, 1))));
	}
	
	public static void addMarshVegetation(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.25F, 1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(EnvironmentalBiomeFeatures.CORNFLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(5))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LILY_PAD_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(8, 0.25F))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(8, 0.125F)))); 
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.TALL_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(130))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.withConfiguration(new SeaGrassConfig(128, 0.6D)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}
	
	public static void addGiantTallGrass(Biome biome, int chance) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GIANT_TALL_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(chance))));
	}
	
	public static void addMarshPools(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.WATER_SPRING_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(150, 8, 8, 800))));
		biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(48))));
	}
	
	public static void addCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.CATTAILS.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(12))));
	}
	
	public static void addDenseCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.DENSE_CATTAILS.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(8))));
	}
	
	public static void addRice(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.RICE.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
	}
	
	public static void addDuckweed(Biome biome, float chance) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DUCKWEED_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, chance, 1))));
	}
	
	public static void addWillowTrees(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.WILLOW_TREE.withConfiguration(WILLOW_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
	}
	
	public static void addSwampOaks(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
	}
	
	public static void overrideFeatures(Biome biome){
    	for (Decoration stage : GenerationStage.Decoration.values())
    	{
    		List<ConfiguredFeature<?, ?>> list = biome.getFeatures(stage);
    		for(int j = 0; j<list.size(); j++)
    	    {
    			ConfiguredFeature<?, ?> configuredFeature = list.get(j);
    			if (configuredFeature.config instanceof DecoratedFeatureConfig) {
    	    		 DecoratedFeatureConfig decorated = (DecoratedFeatureConfig)configuredFeature.config;
    	    		 
    	    		 if (decorated.feature.config instanceof TreeFeatureConfig) {
    	    			 TreeFeatureConfig tree = (TreeFeatureConfig)decorated.feature.config;
    	    			 
    	    			 if (tree == DefaultBiomeFeatures.SWAMP_TREE_CONFIG) {
    	    				 biome.getFeatures(stage).remove(configuredFeature);
    	    			 }
    	    		 }
    	    	 }
    	    }
    	}    	   
    }
}
