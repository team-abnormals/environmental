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
	public static final HangingWillowLeavesTreeDecorator field_236871_b_ = new HangingWillowLeavesTreeDecorator();

	@Override
	protected TreeDecoratorType<?> func_230380_a_() {
		return EnvironmentalFeatures.HANGING_WILLOW_LEAVES.get();
	}

	static {
		CODEC = Codec.unit(() -> field_236871_b_);
	}

	@Override
	public void func_225576_a_(ISeedReader world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> changedBlocks, MutableBoundingBox boundsIn) {
		for (BlockPos pos : leavesPositions) {
			if (world.getBlockState(pos.down()).isAir()) {
				if (rand.nextInt(2) == 0) {
					world.setBlockState(pos.down(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState(), 3);
				}
			}
		}
	}
}