package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class CheerfulCherryTreeGrower extends AbstractTreeGrower {

	@Nullable
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return beehive ? EnvironmentalConfiguredFeatures.CHEERFUL_CHERRY_BEES_005.getHolder().get() : EnvironmentalConfiguredFeatures.CHEERFUL_CHERRY.getHolder().get();
	}
}