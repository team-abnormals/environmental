package com.minecraftabnormals.environmental.common.world.gen.treedecorator;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

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
	public void place(ISeedReader world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> changedBlocks, MutableBoundingBox boundsIn) {
		for (BlockPos pos : leavesPositions) {
			if (world.getBlockState(pos.below()).isAir()) {
				if (rand.nextInt(2) == 0) {
					world.setBlock(pos.below(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().defaultBlockState(), 3);
				}
			}
		}
	}
}