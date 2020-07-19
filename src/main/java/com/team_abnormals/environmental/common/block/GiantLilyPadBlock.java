package com.team_abnormals.environmental.common.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GiantLilyPadBlock extends LargeLilyPadBlock {
	protected static final VoxelShape GIANT_LILY_PAD_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);

	public GiantLilyPadBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return GIANT_LILY_PAD_AABB;
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
	
//	@Override
//	public void bounce(Entity entity) {
//		Vector3d vector3d = entity.getMotion();
//		if (vector3d.y < 0.0D) {
//			double d0 = entity instanceof LivingEntity ? 2.0D : 1.6D;
//			entity.setMotion(vector3d.x, -vector3d.y * d0, vector3d.z);
//		}	
//	}
}