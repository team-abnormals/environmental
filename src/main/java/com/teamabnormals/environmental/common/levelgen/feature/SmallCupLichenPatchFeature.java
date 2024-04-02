package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SmallCupLichenPatchFeature extends CupLichenPatchFeature {

	public SmallCupLichenPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	protected int getRadius() {
		return 3;
	}

	@Override
	protected int getCups(double distance, RandomSource random) {
		int cups = distance < 0.3D ? 2 : distance < 0.5D ? 1 : 0;

		float f = random.nextFloat();
		if (f < 0.4F)
			cups--;
		else if (f < 0.7F)
			cups++;

		return Mth.clamp(cups, 0, 4);
	}
}