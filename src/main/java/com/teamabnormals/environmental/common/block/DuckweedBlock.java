package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DuckweedBlock extends BushBlock implements BonemealableBlock {
	protected static final VoxelShape DUCKWEED_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

	public DuckweedBlock(Block.Properties builder) {
		super(builder);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return null;
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return DUCKWEED_AABB;
	}

	@Override
	public boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		FluidState ifluidstate = worldIn.getFluidState(pos);
		return ifluidstate.getType() == Fluids.WATER;
	}

	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
		return worldIn.getBlockState(pos.above()).isAir();
	}

	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos blockPos, BlockState state) {
		label:
		for (int x = 0; x < 64; ++x) {
			BlockPos newBlockPos = blockPos;
			for (int y = 0; y < x / 16; ++y) {
				newBlockPos = newBlockPos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
				if (state.canSurvive(world, newBlockPos) && world.isEmptyBlock(newBlockPos)) {
					world.setBlockAndUpdate(newBlockPos, state);
					break label;
				}
			}
		}
	}
}
