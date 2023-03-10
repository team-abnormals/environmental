package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.block.GiantLilyPadBlock;
import com.teamabnormals.environmental.common.block.LargeLilyPadBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WaterlilyBlock.class)
public class LilyPadBlockMixin extends Block implements BonemealableBlock {

	public LilyPadBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
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
