package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;

public class ColoredWisteriaLeavesBlock extends LeavesBlock {
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public ColoredWisteriaLeavesBlock(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, Half.BOTTOM).setValue(DISTANCE, 7).setValue(PERSISTENT, false).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HALF);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (direction == Direction.DOWN && state.getValue(HALF) == Half.TOP && offsetState.getBlock() != this)
			return EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
		else
			return super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return state.getValue(HALF) == Half.TOP ? new ItemStack(EnvironmentalBlocks.WISTERIA_LEAVES.get()) : super.getCloneItemStack(level, pos, state);
	}
}