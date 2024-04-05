package com.teamabnormals.environmental.common.block.grower;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class PineTreeGrower extends AbstractTreeGrower {

	@Override
	public boolean growTree(ServerLevel level, ChunkGenerator chunkgenerator, BlockPos pos, BlockState state, RandomSource random) {
		Holder<? extends ConfiguredFeature<?, ?>> holder = this.shouldBeTallPine(level, pos) ? this.getConfiguredLargeFeature() : this.getConfiguredFeature(random, false);
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

	private boolean shouldBeTallPine(ServerLevel level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(Blocks.PODZOL);
	}

	@Nullable
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean beehive) {
		return EnvironmentalConfiguredFeatures.PINE.getHolder().get();
	}

	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredLargeFeature() {
		return EnvironmentalConfiguredFeatures.TALL_PINE.getHolder().get();
	}
}