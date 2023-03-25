package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WillowTreeGrower extends AbstractTreeGrower {

	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return random.nextInt(10) == 0 ? EnvironmentalConfiguredFeatures.WEEPING_WILLOW.getHolder().get() : EnvironmentalConfiguredFeatures.WILLOW.getHolder().get();
	}
}