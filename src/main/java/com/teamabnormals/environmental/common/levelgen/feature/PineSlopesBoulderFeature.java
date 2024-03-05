package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class PineSlopesBoulderFeature extends Feature<BlockStateConfiguration> {

	public PineSlopesBoulderFeature(Codec<BlockStateConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
		BlockStateConfiguration config = context.config();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();

		while (true) {
			if (pos.getY() <= 3)
				return false;
			else if (!level.isEmptyBlock(pos.below())) {
				int i1 = random.nextInt(2) + random.nextInt(2);

				for (int l = 0; l < 3; ++l) {
					int i = i1 + random.nextInt(2);
					int j = i1 + random.nextInt(2);
					int k = i1 + random.nextInt(2);
					float f = (float) (i + j + k) * 0.333F + 0.5F;

					for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
						if (blockpos.distSqr(pos) <= (double) (f * f)) {
							level.setBlock(blockpos, config.state, 4);
						}
					}

					pos = pos.offset(-(i1 + 1) + random.nextInt(2 + i1 * 2), -random.nextInt(2), -(i1 + 1) + random.nextInt(2 + i1 * 2));
					if (level.isEmptyBlock(pos.below()))
						return true;
				}

				return true;
			}

			pos = pos.below();
		}
	}
}