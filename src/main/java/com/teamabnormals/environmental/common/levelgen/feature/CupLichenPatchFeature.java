package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.CupLichenBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CupLichenPatchFeature extends Feature<NoneFeatureConfiguration> {

	public CupLichenPatchFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		int r = this.getRadius();
		int s = this.getYSpread();

		boolean place = false;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int x = -r; x <= r; ++x) {
			for (int z = -r; z <= r; ++z) {
				for (int y = -s; y <= s; ++y) {
					double distance = Math.sqrt(x * x + z * z) / r;
					mutable.setWithOffset(origin, x, 0, z);
					if (distance <= 1.0D && this.canGenerateAt(level, mutable)) {
						int cups = this.getCups(distance, random);
						if (cups > 0) {
							level.setBlock(mutable, EnvironmentalBlocks.CUP_LICHEN.get().defaultBlockState().setValue(CupLichenBlock.CUPS, cups), 2);
							place = true;
						}
					}
				}
			}
		}

		return place;
	}

	protected int getRadius() {
		return 4;
	}

	protected int getYSpread() {
		return 0;
	}

	protected int getCups(double distance, RandomSource random) {
		int cups = distance < 0.2D ? 3 : distance < 0.35D ? 2 : distance < 0.5D ? 1 : 0;

		float f = random.nextFloat();
		if (f < 0.4F)
			cups--;
		else if (f < 0.8F)
			cups++;

		return Mth.clamp(cups, 0, 4);
	}

	protected boolean canGenerateAt(WorldGenLevel level, BlockPos pos) {
		return level.isEmptyBlock(pos) && EnvironmentalBlocks.CUP_LICHEN.get().defaultBlockState().canSurvive(level, pos);
	}
}