package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FallenLeavesFeature extends Feature<NoneFeatureConfiguration> {
	public FallenLeavesFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos pos = context.origin();

		float chance = random.nextFloat();
		BlockState state = (chance < 0.3F ? EnvironmentalBlocks.CHEERFUL_CHERRY_LEAF_PILE.get() : chance < 0.6F ? EnvironmentalBlocks.MOODY_CHERRY_LEAF_PILE.get() : EnvironmentalBlocks.CHERRY_LEAF_PILE.get()).defaultBlockState();

		int i = 0;
		for (int x = -3; x <= 3; ++x) {
			for (int z = -3; z <= 3; ++z) {
				if (Math.abs(x) < 2 || Math.abs(z) < 2) {
					for (int y = -3; y <= 3; ++y) {
						BlockPos blockpos = pos.offset(x, y, z);
						if (random.nextInt(3) > 0 && level.isEmptyBlock(blockpos) && blockpos.getY() < level.getMaxBuildHeight() && level.getBlockState(blockpos.below()).is(BlockTags.DIRT)) {
							level.setBlock(blockpos, state
									.setValue(PipeBlock.UP, false)
									.setValue(PipeBlock.DOWN, true)
									.setValue(PipeBlock.NORTH, false)
									.setValue(PipeBlock.SOUTH, false)
									.setValue(PipeBlock.EAST, false)
									.setValue(PipeBlock.WEST, false), 2);
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}
}