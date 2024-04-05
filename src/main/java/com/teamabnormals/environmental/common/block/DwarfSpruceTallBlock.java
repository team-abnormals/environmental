package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DwarfSpruceTallBlock extends BushBlock {
	private static final VoxelShape TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	private static final VoxelShape BOTTOM_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	public DwarfSpruceTallBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = state.getValue(HALF) == DoubleBlockHalf.UPPER ? TOP_SHAPE : BOTTOM_SHAPE;
		Vec3 vec3d = state.getOffset(level, pos);
		return shape.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		DoubleBlockHalf half = state.getValue(HALF);
		if (direction == Direction.UP && half == DoubleBlockHalf.LOWER && (!offsetState.is(this) || offsetState.getValue(HALF) == DoubleBlockHalf.LOWER))
			return EnvironmentalBlocks.DWARF_SPRUCE.get().defaultBlockState();
		else if (direction == Direction.DOWN && half == DoubleBlockHalf.UPPER && (!offsetState.is(this) || offsetState.getValue(HALF) == DoubleBlockHalf.UPPER))
			return Blocks.AIR.defaultBlockState();
		else
			return super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide) {
			BlockPos abovepos = pos.above();
			BlockState abovestate = level.getBlockState(abovepos);
			if (state.getValue(HALF) == DoubleBlockHalf.LOWER && abovestate.is(this)) {
				level.setBlock(abovepos, Blocks.AIR.defaultBlockState(), 35);
				level.levelEvent(player, 2001, abovepos, Block.getId(abovestate));
				if (!player.isCreative())
					dropResources(abovestate, level, abovepos, null, player, player.getMainHandItem());
			}
		}
		super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			return super.canSurvive(state, level, pos);
		} else {
			BlockState belowstate = level.getBlockState(pos.below());
			if (state.getBlock() != this)
				return super.canSurvive(state, level, pos);
			return belowstate.is(this) && belowstate.getValue(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}
}