package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.common.block.GiantLilyPadBlock;
import com.minecraftabnormals.environmental.common.block.LargeLilyPadBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(LilyPadBlock.class)
public class LilyPadBlockMixin extends Block implements IGrowable {

	public LilyPadBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if (rand.nextInt(3) == 0) {
			if (rand.nextBoolean()) {
				if (LargeLilyPadBlock.checkPositions(worldIn, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().defaultBlockState())) {
					LargeLilyPadBlock.placeAt(worldIn, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().defaultBlockState(), 2);
				}
			} else {
				if (GiantLilyPadBlock.checkPositions(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().defaultBlockState())) {
					GiantLilyPadBlock.placeAt(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().defaultBlockState(), 2);
				}
			}
		}
	}
}
