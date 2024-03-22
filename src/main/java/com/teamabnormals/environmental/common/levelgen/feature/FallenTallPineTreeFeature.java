package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FallenTallPineTreeFeature extends FallenPineTreeFeature {

	public FallenTallPineTreeFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	protected int getLength(RandomSource random) {
		return random.nextInt(10) != 0 ? 12 + random.nextInt(6) : super.getLength(random);
	}
}