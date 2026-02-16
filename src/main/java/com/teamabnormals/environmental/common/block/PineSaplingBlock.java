package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class PineSaplingBlock extends SaplingBlock {
	protected final TreeGrower tallTreeGrower;

	public PineSaplingBlock(TreeGrower treeGrower, TreeGrower tallTreeGrower, Properties properties) {
		super(treeGrower, properties);
		this.tallTreeGrower = tallTreeGrower;
	}

	@Override
	public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
		if (state.getValue(STAGE) == 0) {
			level.setBlock(pos, state.cycle(STAGE), 4);
		} else {
			BlockState belowState = level.getBlockState(pos.below());
			TreeGrower treeGrower = belowState.is(Blocks.PODZOL) || belowState.is(EnvironmentalBlocks.MUDDY_PODZOL) ? this.tallTreeGrower : this.treeGrower;
			treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		}
	}
}