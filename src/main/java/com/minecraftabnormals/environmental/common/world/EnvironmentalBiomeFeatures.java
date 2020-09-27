package com.minecraftabnormals.environmental.common.world;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.minecraftabnormals.environmental.common.world.gen.util.WisteriaColor;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class EnvironmentalBiomeFeatures {
    public static void addMushrooms(Biome biome) {
        if (EnvironmentalConfig.COMMON.generateGiantMushroomsInSwamps.get()) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM), Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM))).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
        }
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(1, 0.25F))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(1, 0.125F))));
    }

    public static void addMarshMushrooms(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.32F, 1))));
    }

    public static void addMarshVegetation(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.33333334F)), Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.25F, 1))));
        
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(EnvironmentalFeatureConfigs.CORNFLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(EnvironmentalFeatureConfigs.DIANTHUS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(3))));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(5))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LILY_PAD_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(8, 0.25F))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(8, 0.125F))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.TALL_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(130))));
        
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.withConfiguration(new SeaGrassConfig(128, 0.6D)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }
    
    public static void addMudDisks(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(EnvironmentalFeatureConfigs.MUD, 5, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(),EnvironmentalFeatureConfigs.MUD))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(1))));
    }
    
    public static void addDuckNests(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.DUCK_NEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(32))));
    }
    
    public static void addChickenNests(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.CHICKEN_NEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(32))));
    }
    
    public static void addBlossomVegetation(Biome biomeIn) {
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.SUGAR_CANE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(12))));
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(EnvironmentalFeatureConfigs.CHERRY_BLOSSOM_FOREST_FLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.ROSE_BUSH_CONFIG), Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.PEONY_CONFIG)), 0)).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(6))));
    }
    
    public static void addBlossomBamboo(Biome biomeIn) {
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(11, 5.0D, 0.2D, Heightmap.Type.WORLD_SURFACE_WG))));
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(20))));
    }
    
    public static void addBlossomValleysBamboo(Biome biomeIn) {
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP_NOISE_BIASED.configure(new TopSolidWithNoiseConfig(2, 5.0D, 0.2D, Heightmap.Type.WORLD_SURFACE_WG))));
    	biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
    }    	
    
    public static void addGiantTallGrass(Biome biome, int chance) {
        if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get()) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.GIANT_TALL_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(chance))));
        }
    }
    
    public static void addMyceliumSprouts(Biome biome) {
    	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.MYCELIUM_SPROUTS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(5))));
    }

    public static void addMarshPools(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.WATER_SPRING_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(150, 8, 8, 800))));
        biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(48))));
    }

    public static void addCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.CATTAILS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(12))));
    }

    public static void addDenseCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.DENSE_CATTAILS.get().withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(8))));
    }

    public static void addRice(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.RICE.get().withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
    }

    public static void addDuckweed(Biome biome, float chance) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.DUCKWEED_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, chance, 1))));
    }

    public static void addWillowTrees(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.WILLOW_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
    }

    public static void addSwampOaks(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.33333334F)), Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
    }
    
    public static void addCherryTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.CHERRY_TREE_CONFIG).withChance(0.1F)), Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.CHERRY_TREE_WITH_FEW_BEEHIVES_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(7, 0.1F, 1))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.FALLEN_LEAVES.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(64))));
    }
    
    public static void addValleyCherryTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.CHERRY_TREE_CONFIG).withChance(0.1F)), Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.FALLEN_LEAVES.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(4))));
    }
    
    public static void addGrass(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(8))));
    }

    public static void addShortFlower(BlockState blockState, Biome biomeIn, int frequency) {
        BlockClusterFeatureConfig config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(blockState), SimpleBlockPlacer.field_236447_c_)).tries(32).build();
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(config).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(frequency))));
    }

    public static void addDirectionalFlower(BlockState blockState, Biome biomeIn, int frequency) {
        BlockClusterFeatureConfig config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(blockState), SimpleBlockPlacer.field_236447_c_)).tries(32).build();
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.DIRECTIONAL_FLOWER.get().withConfiguration(config).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(frequency))));
    }

    public static void addDoubleFlower(BlockState blockState, Biome biomeIn, int frequency) {
        BlockClusterFeatureConfig config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(blockState), DoublePlantBlockPlacer.field_236444_c_)).tries(64).func_227317_b_().build();
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.RANDOM_PATCH.withConfiguration(config)), 0)).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(frequency))));
    }

    public static void addDelphiniums(Biome biome, int count) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(
                new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(
                        Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.WHITE_DELPHINIUM),
                        Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.PINK_DELPHINIUM),
                        Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.PURPLE_DELPHINIUM),
                        Feature.RANDOM_PATCH.withConfiguration(EnvironmentalFeatureConfigs.BLUE_DELPHINIUM)), 0))
                .withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(count))));
    }

    public static void addWisteriaTrees(Biome biome, int count, float extraChance, boolean extraFlowers) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                new MultipleRandomFeatureConfig(
                        ImmutableList.of(
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_CONFIG).withChance(0.15F),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_CONFIG).withChance(0.15F),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_CONFIG).withChance(0.15F)),
                        EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_CONFIG)))
                .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                new MultipleRandomFeatureConfig(
                        ImmutableList.of(
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_CONFIG).withChance(0.5F),
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_CONFIG).withChance(0.5F),
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_CONFIG).withChance(0.5F)),
                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_CONFIG)))
                .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count / 2, extraChance, 1))));
    }

    public static void addWisteriaTreesBeehive(Biome biome, int count, float extraChance, boolean extraFlowers) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                new MultipleRandomFeatureConfig(
                        ImmutableList.of(
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.15F),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.15F),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.15F)),
                        EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));

        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                new MultipleRandomFeatureConfig(
                        ImmutableList.of(
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.5F),
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.5F),
                                EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.5F)),
                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count / 2, extraChance, 1))));
    }

    public static void addWisteriaTreeBeehive(Biome biome, WisteriaColor color, int count, float extraChance, boolean extraFlowers) {
        switch (color) {
            case BLUE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case WHITE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case PINK:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case PURPLE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));

                break;
        }
    }

    public static void addWisteriaTree(Biome biome, WisteriaColor color, int count, float extraChance, boolean extraFlowers) {
        switch (color) {
            case BLUE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case WHITE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case PINK:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
            case PURPLE:
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(
                        new MultipleRandomFeatureConfig(
                                ImmutableList.of(
                                        EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG).withChance(0.2F)),
                                EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_CONFIG)))
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(count, extraChance, 1))));
                break;
        }
    }

    public static void overrideFeatures(Biome biome) {
        for (Decoration stage : GenerationStage.Decoration.values()) {
            List<ConfiguredFeature<?, ?>> list = biome.getFeatures(stage);
            for (int j = 0; j < list.size(); j++) {
                ConfiguredFeature<?, ?> configuredFeature = list.get(j);
                if (configuredFeature.config instanceof DecoratedFeatureConfig) {
                    DecoratedFeatureConfig decorated = (DecoratedFeatureConfig) configuredFeature.config;

                    if (decorated.feature.config instanceof BaseTreeFeatureConfig) {
                        BaseTreeFeatureConfig tree = (BaseTreeFeatureConfig) decorated.feature.config;

                        if (tree == DefaultBiomeFeatures.SWAMP_TREE_CONFIG) {
                            biome.getFeatures(stage).remove(configuredFeature);
                        }
                    }
                }
            }
        }
    }
}
