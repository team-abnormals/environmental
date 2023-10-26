package com.teamabnormals.environmental.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public class WallHibiscusBlock extends AbstractHibiscusBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 1.0D), Direction.SOUTH, Block.box(3.0D, 3.0D, 15.0D, 13.0D, 13.0D, 16.0D), Direction.WEST, Block.box(0.0D, 3.0D, 3.0D, 1.0D, 13.0D, 13.0D), Direction.EAST, Block.box(15.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D), Direction.UP, Block.box(3.0D, 15.0D, 3.0D, 13.0D, 16.0D, 13.0D), Direction.DOWN, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));

	public WallHibiscusBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	}

	@Override
	public String getDescriptionId() {
		return this.asItem().getDescriptionId();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABBS.get(state.getValue(FACING));
	}

	@Override
	protected Block getWallHibiscus() {
		return this;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getBlock() instanceof LeavesBlock;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockpos = pos.relative(state.getValue(FACING));
		BlockState otherState = level.getBlockState(blockpos);
		return this.mayPlaceOn(otherState, level, blockpos) || otherState.isFaceSturdy(level, blockpos, state.getValue(FACING).getOpposite());
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = this.defaultBlockState();
		LevelReader level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();

		for (Direction direction : context.getNearestLookingDirections()) {
			blockstate = blockstate.setValue(FACING, direction);
			if (blockstate.canSurvive(level, blockpos)) {
				return blockstate;
			}
		}

		return null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}