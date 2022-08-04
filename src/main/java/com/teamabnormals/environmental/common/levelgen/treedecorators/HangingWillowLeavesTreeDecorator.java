package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class HangingWillowLeavesTreeDecorator extends TreeDecorator {
	public static final Codec<HangingWillowLeavesTreeDecorator> CODEC;
	public static final HangingWillowLeavesTreeDecorator INSTANCE = new HangingWillowLeavesTreeDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalFeatures.HANGING_WILLOW_LEAVES.get();
	}

	static {
		CODEC = Codec.unit(() -> INSTANCE);
	}

	@Override
	public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, Random random, List<BlockPos> logs, List<BlockPos> leaves) {
		for (BlockPos pos : leaves) {
			if (Feature.isAir(level, pos.below())) {
				if (random.nextInt(2) == 0) {
					consumer.accept(pos.below(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().defaultBlockState());
				}
			}
		}
	}
}