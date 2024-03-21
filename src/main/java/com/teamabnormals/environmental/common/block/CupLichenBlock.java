package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CupLichenBlock extends BushBlock implements BonemealableBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);

	public CupLichenBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(EnvironmentalBlockTags.CUP_LICHEN_PLANTABLE_ON);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean isClient) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.relative(direction);
			BlockPos belowpos = blockpos.below();
			if (blockGetter.getBlockState(blockpos).isAir() && this.mayPlaceOn(blockGetter.getBlockState(belowpos), blockGetter, belowpos))
				return true;
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		List<BlockPos> positions = Lists.newArrayList();
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.relative(direction);
			BlockPos belowpos = blockpos.below();
			if (level.getBlockState(blockpos).isAir() && this.mayPlaceOn(level.getBlockState(belowpos), level, belowpos))
				positions.add(blockpos);
		}

		if (!positions.isEmpty()) {
			BlockPos blockpos = positions.get(random.nextInt(positions.size()));
			level.setBlockAndUpdate(blockpos, this.defaultBlockState());
		}
	}
}
