package com.team_abnormals.environmental.common.world.gen.feature.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.core.other.BiomeFeatures;
import com.team_abnormals.environmental.core.other.WisteriaColor;
import com.team_abnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class WisteriaTree extends Tree {
    private WisteriaColor color;

    public WisteriaTree(WisteriaColor colorIn) {
        color = colorIn;
    }
    
    private TreeFeatureConfig setConfigByColor(WisteriaColor color, boolean beehive) {
        switch (color) {
            case PURPLE:
            	default:
            	return beehive ? BiomeFeatures.PURPLE_WISTERIA_MORE_BEEHIVES_CONFIG : BiomeFeatures.PURPLE_WISTERIA_CONFIG;
            case WHITE:
            	return beehive ? BiomeFeatures.WHITE_WISTERIA_MORE_BEEHIVES_CONFIG : BiomeFeatures.WHITE_WISTERIA_CONFIG;
            case PINK:
            	return beehive ? BiomeFeatures.PINK_WISTERIA_MORE_BEEHIVES_CONFIG : BiomeFeatures.PINK_WISTERIA_CONFIG;
            case BLUE:
            	return beehive ? BiomeFeatures.BLUE_WISTERIA_MORE_BEEHIVES_CONFIG : BiomeFeatures.BLUE_WISTERIA_CONFIG;
        }
    }

    /**
     * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
     */
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
       return randomIn.nextInt(10) == 0 ? EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(setConfigByColor(color, beehive)) : EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(setConfigByColor(color, beehive));
    }
}