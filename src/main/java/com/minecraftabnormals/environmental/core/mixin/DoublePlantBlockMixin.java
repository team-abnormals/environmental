package com.minecraftabnormals.environmental.core.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin extends Block implements IGrowable {

	public DoublePlantBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		DoublePlantBlock giantTallGrass = (DoublePlantBlock) EnvironmentalBlocks.GIANT_TALL_GRASS.get();
		if(state.get(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) giantTallGrass.placeAt(worldIn, pos, 2);
		else giantTallGrass.placeAt(worldIn, pos.down(), 2);
	}
}
