package com.teamabnormals.environmental.common.block;

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
			TreeGrower treeGrower = level.getBlockState(pos.below()).is(Blocks.PODZOL) ? this.tallTreeGrower : this.treeGrower;
			treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
		}
	}
}