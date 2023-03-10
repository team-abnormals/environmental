package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin extends SpreadingSnowyDirtBlock implements BonemealableBlock {

	public MyceliumBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return worldIn.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
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
