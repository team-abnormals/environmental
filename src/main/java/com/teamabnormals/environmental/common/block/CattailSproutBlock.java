package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.common.ForgeHooks;

public class CattailSproutBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty CATTAILS = CattailBlock.CATTAILS;

	public CattailSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CATTAILS, 1).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(Blocks.FARMLAND);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		FluidState fluidState = level.getFluidState(pos);
		if (state.is(this)) {
			return state.cycle(CATTAILS);
		} else {
			return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (ForgeHooks.onCropsGrowPre(level, pos, level.getBlockState(pos), random.nextDouble() < 0.2D)) {
			level.setBlock(pos, BlockUtil.transferAllBlockStates(state, EnvironmentalBlocks.CATTAIL.get().defaultBlockState()), 2);
			ForgeHooks.onCropsGrowPost(level, pos, level.getBlockState(pos));
		}
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(CATTAILS) < 3 || super.canBeReplaced(state, context);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CATTAILS, WATERLOGGED);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, BlockUtil.transferAllBlockStates(state, EnvironmentalBlocks.CATTAIL.get().defaultBlockState()));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		if (!state.canSurvive(level, pos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (state.getValue(WATERLOGGED)) {
				level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			}

			return super.updateShape(state, direction, facingState, level, pos, facingPos);
		}
	}
}