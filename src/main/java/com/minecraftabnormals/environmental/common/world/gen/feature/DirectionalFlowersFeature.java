package com.minecraftabnormals.environmental.common.world.gen.feature;

import com.minecraftabnormals.environmental.common.block.CartwheelBlock;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.FlowersFeature;

import java.util.Random;

public class DirectionalFlowersFeature<U extends BlockClusterFeatureConfig> extends FlowersFeature<U> {
	public DirectionalFlowersFeature(Codec<U> codec) {
		super(codec);
	}

	@Override
	public boolean isValid(IWorld world, BlockPos pos, U config) {
		return !config.blacklist.contains(world.getBlockState(pos));
	}

	@Override
	public int getCount(U config) {
		return config.tries;
	}

	@Override
	public BlockPos getPos(Random rand, BlockPos pos, U config) {
		return pos.offset(rand.nextInt(config.xspread) - rand.nextInt(config.xspread), rand.nextInt(config.yspread) - rand.nextInt(config.yspread), rand.nextInt(config.zspread) - rand.nextInt(config.zspread));
	}

	@Override
	public BlockState getRandomFlower(Random rand, BlockPos pos, U config) {
		return config.stateProvider.getState(rand, pos).setValue(CartwheelBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(rand));
	}
}