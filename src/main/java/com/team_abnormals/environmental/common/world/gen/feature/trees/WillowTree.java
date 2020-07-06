package com.team_abnormals.environmental.common.world.gen.feature.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.common.world.biome.EnvironmentalBiomeFeatures;
import com.team_abnormals.environmental.core.registry.EnvironmentalFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class WillowTree extends Tree {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return EnvironmentalFeatures.WILLOW_TREE.withConfiguration(EnvironmentalBiomeFeatures.WILLOW_TREE_CONFIG);
	}
}