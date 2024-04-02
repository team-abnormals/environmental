package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StoneCupLichenPatchFeature extends CupLichenPatchFeature {

	public StoneCupLichenPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	protected int getYSpread() {
		return 2;
	}

	@Override
	protected int getCups(double distance, RandomSource random) {
		int cups = distance < 0.4D ? 2 : distance < 0.6D ? 1 : 0;

		float f = random.nextFloat();
		if (f < 0.5F)
			cups--;
		else if (f < 0.6F)
			cups++;

		return Mth.clamp(cups, 0, 4);
	}

	@Override
	protected boolean canGenerateAt(WorldGenLevel level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(BlockTags.BASE_STONE_OVERWORLD) && super.canGenerateAt(level, pos);
	}
}
