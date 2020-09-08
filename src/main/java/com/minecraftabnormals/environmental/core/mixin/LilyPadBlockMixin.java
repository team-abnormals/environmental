package com.minecraftabnormals.environmental.core.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

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

@Mixin(LilyPadBlock.class)
public class LilyPadBlockMixin extends Block implements IGrowable {

	public LilyPadBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if (rand.nextInt(3) == 0) {
			if (rand.nextBoolean()) {
				if (LargeLilyPadBlock.checkPositions(worldIn, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().getDefaultState())) {
					LargeLilyPadBlock.placeAt(worldIn, pos, EnvironmentalBlocks.LARGE_LILY_PAD.get().getDefaultState(), 2);
				}
			} else {
				if (GiantLilyPadBlock.checkPositions(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().getDefaultState())) {
					GiantLilyPadBlock.placeAt(worldIn, pos, EnvironmentalBlocks.GIANT_LILY_PAD.get().getDefaultState(), 2);
				}
			}
		}
	}
}
