package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.block.*;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin extends Block implements IGrowable {

	public DoublePlantBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		DoublePlantBlock giantTallGrass = (DoublePlantBlock) EnvironmentalBlocks.GIANT_TALL_GRASS.get();
		if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) giantTallGrass.placeAt(worldIn, pos, 2);
		else giantTallGrass.placeAt(worldIn, pos.below(), 2);
	}
}
