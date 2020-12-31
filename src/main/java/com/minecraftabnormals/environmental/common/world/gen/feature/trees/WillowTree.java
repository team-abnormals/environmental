package com.minecraftabnormals.environmental.common.world.gen.feature.trees;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class WillowTree extends Tree {

	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return Feature.TREE.withConfiguration(EnvironmentalFeatures.Configs.WILLOW_TREE_CONFIG);
	}
}