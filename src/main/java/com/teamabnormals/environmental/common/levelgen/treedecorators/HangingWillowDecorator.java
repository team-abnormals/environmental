package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HangingWillowDecorator extends TreeDecorator {
	public static final MapCodec<HangingWillowDecorator> CODEC;
	public static final HangingWillowDecorator INSTANCE = new HangingWillowDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalTreeDecorators.HANGING_WILLOW_LEAVES.get();
	}

	@Override
	public void place(Context context) {
		for (BlockPos pos : context.leaves()) {
			if (context.level().isStateAtPosition(pos.below(), BlockState::isAir)) {
				if (context.random().nextInt(2) == 0) {
					context.setBlock(pos.below(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().defaultBlockState());
				}
			}
		}
	}

	static {
		CODEC = MapCodec.unit(() -> INSTANCE);
	}
}