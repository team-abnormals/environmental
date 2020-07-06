package com.team_abnormals.environmental.common.world.gen.feature.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.common.world.EnvironmentalFeatureConfigs;
import com.team_abnormals.environmental.common.world.gen.util.WisteriaColor;
import com.team_abnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WisteriaTree extends Tree {
    private WisteriaColor color;

    public WisteriaTree(WisteriaColor colorIn) {
        color = colorIn;
    }
    
    private BaseTreeFeatureConfig setConfigByColor(WisteriaColor color, boolean beehive) {
        switch (color) {
            case PURPLE:
            	default:
            	return beehive ? EnvironmentalFeatureConfigs.PURPLE_WISTERIA_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.PURPLE_WISTERIA_CONFIG;
            case WHITE:
            	return beehive ? EnvironmentalFeatureConfigs.WHITE_WISTERIA_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.WHITE_WISTERIA_CONFIG;
            case PINK:
            	return beehive ? EnvironmentalFeatureConfigs.PINK_WISTERIA_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.PINK_WISTERIA_CONFIG;
            case BLUE:
            	return beehive ? EnvironmentalFeatureConfigs.BLUE_WISTERIA_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.BLUE_WISTERIA_CONFIG;
        }
    }

    /**
     * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
     */
    @Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
       return randomIn.nextInt(10) == 0 ? EnvironmentalFeatures.BIG_WISTERIA_TREE.withConfiguration(setConfigByColor(color, beehive)) : EnvironmentalFeatures.WISTERIA_TREE.withConfiguration(setConfigByColor(color, beehive));
    }
}