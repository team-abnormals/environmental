package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LargeCoarseDirtOnStoneFeature extends CoarseDirtOnStoneFeature {

	public LargeCoarseDirtOnStoneFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected double getRadius(RandomSource random) {
		return random.nextDouble() * 2.0D + 1;
	}
}