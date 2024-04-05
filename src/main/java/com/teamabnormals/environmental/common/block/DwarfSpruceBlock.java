package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DwarfSpruceBlock extends BushBlock implements BonemealableBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public DwarfSpruceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(level, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos belowpos = context.getClickedPos().below();
		BlockState belowstate = context.getLevel().getBlockState(belowpos);
		if (belowstate.is(this)) {
			context.getLevel().setBlockAndUpdate(belowpos, EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
			return EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (direction == Direction.UP && offsetState.is(EnvironmentalBlocks.TALL_DWARF_SPRUCE.get()) && offsetState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER)
			return EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
		return super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		if (level.isEmptyBlock(pos.above())) {
			level.setBlock(pos, EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2);
			level.setBlock(pos.above(), EnvironmentalBlocks.TALL_DWARF_SPRUCE.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
		}
	}
}