package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WillowTreeGrower extends AbstractTreeGrower {

	@Override
	public boolean growTree(ServerLevel level, ChunkGenerator chunkgenerator, BlockPos pos, BlockState state, RandomSource random) {
		Holder<? extends ConfiguredFeature<?, ?>> holder = this.shouldBeWeepingWillow(level, pos) ? this.getConfiguredLargeFeature() : this.getConfiguredFeature(random, false);
		if (holder == null) {
			return false;
		} else {
			ConfiguredFeature<?, ?> configuredfeature = holder.value();
			BlockState blockstate = level.getFluidState(pos).createLegacyBlock();
			level.setBlock(pos, blockstate, 4);
			if (configuredfeature.place(level, chunkgenerator, random, pos)) {
				if (level.getBlockState(pos) == blockstate) {
					level.sendBlockUpdated(pos, state, blockstate, 2);
				}

				return true;
			} else {
				level.setBlock(pos, state, 4);
				return false;
			}
		}
	}

	private boolean shouldBeWeepingWillow(ServerLevel level, BlockPos pos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, -1, 1))) {
			if (level.getFluidState(blockpos).is(FluidTags.WATER))
				return true;
		}
		return false;
	}

	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return EnvironmentalConfiguredFeatures.WILLOW.getHolder().get();
	}

	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredLargeFeature() {
		return EnvironmentalConfiguredFeatures.WEEPING_WILLOW.getHolder().get();
	}
}