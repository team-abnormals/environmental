package com.minecraftabnormals.environmental.common.world.gen.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.world.EnvironmentalFeatureConfigs;

import java.util.Random;

public class WillowTree extends Tree {

    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.field_236291_c_.withConfiguration(EnvironmentalFeatureConfigs.WILLOW_TREE_CONFIG);
    }
}