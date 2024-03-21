package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;

public class WisteriaLeavesBlock extends LeavesBlock {

	public WisteriaLeavesBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState belowstate = context.getLevel().getBlockState(context.getClickedPos().below());
		if (belowstate.getBlock() instanceof WisteriaLeafColorBlock leaves && leaves.causesBlendTexture(belowstate))
			return ColoredWisteriaLeavesBlock.getLeavesFromColor(leaves.getColor()).getStateForPlacement(context).setValue(ColoredWisteriaLeavesBlock.HALF, Half.TOP);
		else
			return super.getStateForPlacement(context);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (direction == Direction.DOWN && offsetState.getBlock() instanceof WisteriaLeafColorBlock leaves && leaves.causesBlendTexture(offsetState))
			return ColoredWisteriaLeavesBlock.getLeavesFromColor(leaves.getColor()).defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(ColoredWisteriaLeavesBlock.HALF, Half.TOP);
		else
			return super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}
}