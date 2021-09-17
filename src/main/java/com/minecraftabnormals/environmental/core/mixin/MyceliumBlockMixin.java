package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin extends SpreadableSnowyDirtBlock implements IGrowable {

	public MyceliumBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return worldIn.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();

		for (int i = 0; i < 128; ++i) {
			BlockPos newPos = blockpos;

			for (int j = 0; j < i / 16; ++j) {
				newPos = newPos.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
				if (!worldIn.getBlockState(newPos.below()).is(this) || worldIn.getBlockState(newPos).isCollisionShapeFullBlock(worldIn, newPos)) {
					break;
				}
			}

			if (worldIn.getBlockState(newPos).isAir()) {
				if (EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState().canSurvive(worldIn, newPos)) {
					worldIn.setBlock(newPos, EnvironmentalBlocks.MYCELIUM_SPROUTS.get().defaultBlockState(), 3);
				}
			}
		}
	}
}
