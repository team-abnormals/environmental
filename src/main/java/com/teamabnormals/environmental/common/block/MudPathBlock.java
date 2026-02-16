package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MudPathBlock extends DirtPathBlock {
	public static final MapCodec<DirtPathBlock> CODEC = simpleCodec(MudPathBlock::new);
	private static final VoxelShape COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);

	public MudPathBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return COLLISION_SHAPE;
	}

	@Override
	protected VoxelShape getBlockSupportShape(BlockState p_221566_, BlockGetter p_221567_, BlockPos p_221568_) {
		return SHAPE;
	}

	@Override
	protected VoxelShape getVisualShape(BlockState p_221556_, BlockGetter p_221557_, BlockPos p_221558_, CollisionContext p_221559_) {
		return SHAPE;
	}

	@Override
	public MapCodec<DirtPathBlock> codec() {
		return CODEC;
	}
}
