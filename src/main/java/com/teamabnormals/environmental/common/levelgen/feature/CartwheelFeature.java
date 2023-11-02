package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class CartwheelFeature extends Feature<SimpleBlockConfiguration> {

	public CartwheelFeature(Codec<SimpleBlockConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
		SimpleBlockConfiguration config = context.config();
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		BlockPos placePos = origin;

		BlockState state = config.toPlace().getState(context.random(), origin);
		if (origin.getY() >= 160) {
			for (int y = -16; y < 96; y++) {
				boolean foundBlock = false;
				for (int x = -16; x < 16; x++) {
					for (int z = -16; z < 16; z++) {
						BlockPos offsetPos = origin.offset(x, y, z);
						if (level.getBlockState(offsetPos).is(EnvironmentalBlocks.CARTWHEEL.get())) {
							return false;
						}

						if (state.canSurvive(level, offsetPos)) {
							foundBlock = true;
							if (y > 0 && level.isEmptyBlock(offsetPos)) {
								placePos = offsetPos;
							}
						}
					}
				}

				if (y > 0 && !foundBlock) {
					break;
				}
			}

			if (!state.canSurvive(level, placePos) || !level.isEmptyBlock(placePos)) {
				return false;
			}

			if (state.getBlock() instanceof DoublePlantBlock) {
				if (!level.isEmptyBlock(placePos.above())) {
					return false;
				}

				DoublePlantBlock.placeAt(level, state, placePos, 2);
			} else {
				level.setBlock(placePos, state, 2);
			}

			return true;
		} else {
			return false;
		}
	}
}