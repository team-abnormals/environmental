package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

import static net.minecraft.BlockUtil.getTopConnectedBlock;

public class CattailStalkBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final IntegerProperty CATTAILS = CattailBlock.CATTAILS;

	public CattailStalkBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CATTAILS, 1).setValue(BOTTOM, true).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState aboveState = level.getBlockState(pos.above());
		return this.mayPlaceOn(level.getBlockState(pos.below()), level, pos) && (aboveState.is(this) || aboveState.is(this.getHeadBlock()));
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(Blocks.FARMLAND) || state.is(this);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CATTAILS, BOTTOM, WATERLOGGED);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return new ItemStack(this.getHeadBlock());
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		Optional<BlockPos> optional = this.getHeadPos(level, pos, state.getBlock());
		return optional.isPresent() && this.getHeadBlock().canGrowInto(level.getBlockState(optional.get().above()));
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		Optional<BlockPos> optional = this.getHeadPos(level, pos, state.getBlock());
		if (optional.isPresent()) {
			BlockState headState = level.getBlockState(optional.get());
			((CattailBlock) headState.getBlock()).performBonemeal(level, random, optional.get(), headState);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
			level.scheduleTick(pos, this, 1);
		}

		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, facingState, level, pos, facingPos);
	}

	private Optional<BlockPos> getHeadPos(BlockGetter level, BlockPos pos, Block block) {
		return getTopConnectedBlock(level, pos, block, Direction.UP, this.getHeadBlock());
	}

	public CattailBlock getHeadBlock() {
		return (CattailBlock) EnvironmentalBlocks.CATTAIL.get();
	}
}