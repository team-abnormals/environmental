package com.minecraftabnormals.environmental.common.world.gen.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.world.EnvironmentalFeatureConfigs;

import java.util.Random;

public class CherryTree extends Tree {

    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
        return Feature.field_236291_c_.withConfiguration(beehive ? EnvironmentalFeatureConfigs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatureConfigs.CHERRY_TREE_CONFIG);
    }
}