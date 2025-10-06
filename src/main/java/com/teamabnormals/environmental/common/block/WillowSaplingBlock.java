package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class WillowSaplingBlock extends SaplingBlock {
	protected final TreeGrower weepingTreeGrower;

	public WillowSaplingBlock(TreeGrower treeGrower, TreeGrower weepingTreeGrower, Properties properties) {
		super(treeGrower, properties);
		this.weepingTreeGrower = weepingTreeGrower;
	}

	@Override
	public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
		if (state.getValue(STAGE) == 0) {
			level.setBlock(pos, state.cycle(STAGE), 4);
		} else {
			TreeGrower treeGrower = this.shouldBeWeepingWillow(level, pos) ? this.weepingTreeGrower : this.treeGrower;
			treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		}
	}

	private boolean shouldBeWeepingWillow(ServerLevel level, BlockPos pos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, -1, 1))) {
			if (level.getFluidState(blockpos).is(FluidTags.WATER))
				return true;
		}
		return false;
	}
}