package com.minecraftabnormals.environmental.common.world.gen.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.world.EnvironmentalFeatureConfigs;
import com.minecraftabnormals.environmental.common.world.gen.util.WisteriaColor;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;

import java.util.Random;

public class WisteriaTree extends Tree {
    private WisteriaColor color;

    public WisteriaTree(WisteriaColor colorIn) {
        color = colorIn;
    }

    private BaseTreeFeatureConfig setConfigByColor(WisteriaColor color, boolean beehive) {
        switch (color) {
            case PURPLE:
            default:
                return beehive ? EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.PURPLE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG;
            case WHITE:
                return beehive ? EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.WHITE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG;
            case PINK:
                return beehive ? EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.PINK_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG;
            case BLUE:
                return beehive ? EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.BLUE_WISTERIA_TREE_WITH_FEW_BEEHIVES_CONFIG;
        }
    }

    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
        return randomIn.nextInt(10) == 0 ? EnvironmentalFeatures.BIG_WISTERIA_TREE.get().withConfiguration(setConfigByColor(color, beehive)) : EnvironmentalFeatures.WISTERIA_TREE.get().withConfiguration(setConfigByColor(color, beehive));
    }
}