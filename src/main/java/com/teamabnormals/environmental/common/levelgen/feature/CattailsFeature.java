package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.CattailBlock;
import com.teamabnormals.environmental.common.block.CattailStalkBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class CattailsFeature extends Feature<NoneFeatureConfiguration> {

	public CattailsFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		boolean place = false;
		for (int i = 0; i < this.getTries(); ++i) {
			BlockPos pos = origin.offset(random.nextInt(8) - random.nextInt(4), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(4));
			if ((i == 0 && !level.isWaterAt(pos))) {
				return false;
			}

			int stalkCount = this.getStalkCount(random);
			BlockState belowState = level.getBlockState(pos.below());
			if (EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().canSurvive(level, pos) && !belowState.is(EnvironmentalBlocks.CATTAIL_STALK.get()) && (level.isWaterAt(pos) || level.isEmptyBlock(pos)) && (level.isEmptyBlock(pos.above()) || (level.isWaterAt(pos.above()) && level.isEmptyBlock(pos.above(2))))) {
				boolean fluffy = belowState.is(Blocks.MUD);
				level.setBlock(pos, EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER), 2);
				if (level.isWaterAt(pos.above()) || (level.isEmptyBlock(pos.above(2)) && random.nextBoolean())) {
					level.setBlock(pos.above(), EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailStalkBlock.BOTTOM, false).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER), 2);
					level.setBlock(pos.above(2), EnvironmentalBlocks.CATTAIL.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailBlock.AGE, 2).setValue(CattailBlock.FLUFFY, fluffy).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above(2)).getType() == Fluids.WATER), 2);
				} else {
					level.setBlock(pos.above(), EnvironmentalBlocks.CATTAIL.get().defaultBlockState().setValue(CattailBlock.CATTAILS, stalkCount).setValue(CattailBlock.AGE, 2).setValue(CattailBlock.FLUFFY, fluffy).setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER), 2);
				}
				place = true;
			}

		}
		return place;
	}

	public int getTries() {
		return 384;
	}

	public int getStalkCount(RandomSource random) {
		return 1 + random.nextInt(2);
	}
}