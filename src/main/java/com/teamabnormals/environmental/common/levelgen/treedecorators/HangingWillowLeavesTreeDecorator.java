package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class HangingWillowLeavesTreeDecorator extends TreeDecorator {
	public static final Codec<HangingWillowLeavesTreeDecorator> CODEC;
	public static final HangingWillowLeavesTreeDecorator INSTANCE = new HangingWillowLeavesTreeDecorator();

	@Override
	protected TreeDecoratorType<?> type() {
		return EnvironmentalFeatures.HANGING_WILLOW_LEAVES.get();
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
		CODEC = Codec.unit(() -> INSTANCE);
	}
}