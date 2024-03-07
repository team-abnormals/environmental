package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
				int i1 = random.nextInt(2);
				if (random.nextInt(4) == 0)
					i1 += 1;

				boolean hasemeralds = i1 > 0 && random.nextInt(7) == 0;

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

					if (hasemeralds) {
						for (int m = 0; m < 2; ++m) {
							BlockPos blockpos = pos.offset(random.nextInt(i) - random.nextInt(i), random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k));
							boolean genemerald = true;

							for (Direction direction : Direction.values())
								if (blockpos.relative(direction).distSqr(pos) > (double) (f * f))
									genemerald = false;

							if (genemerald) {
								level.setBlock(blockpos, Blocks.EMERALD_ORE.defaultBlockState(), 4);
								break;
							}
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