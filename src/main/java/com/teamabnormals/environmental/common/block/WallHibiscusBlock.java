package com.teamabnormals.environmental.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public class WallHibiscusBlock extends AbstractHibiscusBlock {
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 1.0D), Direction.SOUTH, Block.box(3.0D, 3.0D, 15.0D, 13.0D, 13.0D, 16.0D), Direction.WEST, Block.box(0.0D, 3.0D, 3.0D, 1.0D, 13.0D, 13.0D), Direction.EAST, Block.box(15.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D), Direction.UP, Block.box(3.0D, 15.0D, 3.0D, 13.0D, 16.0D, 13.0D), Direction.DOWN, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));

	public WallHibiscusBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
	}

	@Override
	public String getDescriptionId() {
		return this.asItem().getDescriptionId();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABBS.get(getConnectedDirection(state).getOpposite());
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
		return canAttach(level, pos, getConnectedDirection(state).getOpposite());
	}

	public boolean canAttach(LevelReader level, BlockPos pos, Direction direction) {
		BlockPos offsetPos = pos.relative(direction);
		BlockState offsetState = level.getBlockState(offsetPos);
		return offsetState.isFaceSturdy(level, offsetPos, direction.getOpposite()) || this.mayPlaceOn(offsetState, level, offsetPos);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		for (Direction direction : context.getNearestLookingDirections()) {
			BlockState state;
			if (direction.getAxis() == Direction.Axis.Y) {
				state = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection().getOpposite());
			} else {
				state = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
			}

			if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
				return state;
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
		builder.add(FACING, FACE);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
		return getConnectedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, otherState, level, pos, otherPos);
	}

	public static BlockState setPropertiesForDirection(BlockState state, Direction direction, RandomSource random) {
		return state.setValue(WallHibiscusBlock.FACE, direction == Direction.UP ? AttachFace.FLOOR : direction == Direction.DOWN ? AttachFace.CEILING : AttachFace.WALL).setValue(WallHibiscusBlock.FACING, direction.getAxis() == Axis.Y ? Plane.HORIZONTAL.getRandomDirection(random) : direction);
	}

	protected static Direction getConnectedDirection(BlockState state) {
		return switch (state.getValue(FACE)) {
			case CEILING -> Direction.DOWN;
			case FLOOR -> Direction.UP;
			default -> state.getValue(FACING);
		};
	}
}