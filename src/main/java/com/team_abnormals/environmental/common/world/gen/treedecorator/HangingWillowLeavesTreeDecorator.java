package com.team_abnormals.environmental.common.world.gen.treedecorator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class HangingWillowLeavesTreeDecorator extends TreeDecorator {
	public static final Codec<LeaveVineTreeDecorator> field_236870_a_;
	public static final LeaveVineTreeDecorator field_236871_b_ = new LeaveVineTreeDecorator();

	protected TreeDecoratorType<?> func_230380_a_() {
		return TreeDecoratorType.LEAVE_VINE;
	}

	static {
		field_236870_a_ = Codec.unit(() -> {
			return field_236871_b_;
		});
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void func_225576_a_(IWorld world, Random rand, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> changedBlocks, MutableBoundingBox boundsIn) {
		for (BlockPos pos : leavesPositions) {
			if (world.getBlockState(pos.down()).isAir()) {
				if (rand.nextInt(2) == 0) {
					world.setBlockState(pos.down(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState(), 3);
				}
			}
		}
	}
}