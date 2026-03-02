package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class SmoothcapMossPlantBlock extends MultifaceBlock implements BonemealableBlock, SimpleWaterloggedBlock {
	public static final MapCodec<SmoothcapMossPlantBlock> CODEC = simpleCodec(SmoothcapMossPlantBlock::new);
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape[] AGE_SHAPES = {
			Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
	};
	private final MultifaceSpreader spreader = new MultifaceSpreader(this);

	public SmoothcapMossPlantBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(BlockStateProperties.AGE_3, 0));
	}

	@Override
	public MapCodec<SmoothcapMossPlantBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean isFaceSupported(Direction face) {
		return face != Direction.UP;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED, BlockStateProperties.AGE_3);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		// TODO: Maybe change
		if (state.getValue(PipeBlock.DOWN)) {
			for (BooleanProperty directionProperty : PipeBlock.PROPERTY_BY_DIRECTION.values()) {
				if (directionProperty != PipeBlock.DOWN && directionProperty != PipeBlock.UP && state.getValue(directionProperty))
					return Shapes.block();
			}
			return AGE_SHAPES[state.getValue(BlockStateProperties.AGE_3)];
		}
		return super.getShape(state, level, pos, context);
	}

	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		return !useContext.getItemInHand().is(this.asItem()) || super.canBeReplaced(state, useContext);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(BlockStateProperties.AGE_3) < 3;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		if (context.replacingClickedOnBlock() && state.is(this)) {
			int age = state.getValue(BlockStateProperties.AGE_3);
			if (age < 3)
				return state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1);
		}
		return Arrays.stream(context.getNearestLookingDirections())
				.map(direction -> this.getStateForPlacement(state, level, pos, direction))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	@Override
	public boolean isValidStateForPlacement(BlockGetter level, BlockState state, BlockPos pos, Direction direction) {
		if (this.isFaceSupported(direction) && (!state.is(this) || !hasFace(state, direction))) {
			BlockPos blockpos = pos.relative(direction);
			BlockState stateAttachedTo = level.getBlockState(blockpos);
			return canAttachTo(level, direction, blockpos, stateAttachedTo);
		} else {
			return false;
		}
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}
}
