package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DenseCattailsFeature extends CattailsFeature {

	public DenseCattailsFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public int getTries() {
		return 1536;
	}

	public int getStalkCount(RandomSource random) {
		return 1 + random.nextInt(3);
	}
}