package com.minecraftabnormals.environmental.common.world.gen.feature.trees;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class CherryTree extends Tree {

	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean beehive) {
		return EnvironmentalFeatures.CHERRY_TREE.get().withConfiguration(beehive ? EnvironmentalFeatures.Configs.CHERRY_TREE_WITH_MORE_BEEHIVES_CONFIG : EnvironmentalFeatures.Configs.CHERRY_TREE_CONFIG);
	}
}