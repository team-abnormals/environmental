package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SmallCoarseDirtOnStoneFeature extends CoarseDirtOnStoneFeature {
	public SmallCoarseDirtOnStoneFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected double getRadius(RandomSource random) {
		return Math.pow(random.nextDouble(), 2) + 1.0D;
	}
}