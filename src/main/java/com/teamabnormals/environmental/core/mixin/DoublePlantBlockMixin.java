package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin extends Block implements BonemealableBlock {

	public DoublePlantBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return this == Blocks.TALL_GRASS;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockState giantTallGrass = EnvironmentalBlocks.GIANT_TALL_GRASS.get().defaultBlockState();
		if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER)
			DoublePlantBlock.placeAt(worldIn, giantTallGrass, pos, 2);
		else DoublePlantBlock.placeAt(worldIn, giantTallGrass, pos.below(), 2);
	}
}
