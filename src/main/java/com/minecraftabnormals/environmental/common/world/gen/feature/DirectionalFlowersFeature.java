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
    public boolean isValidPosition(IWorld world, BlockPos pos, U config) {
        return !config.blacklist.contains(world.getBlockState(pos));
    }

    @Override
    public int getFlowerCount(U config) {
        return config.tryCount;
    }

    @Override
    public BlockPos getNearbyPos(Random rand, BlockPos pos, U config) {
        return pos.add(rand.nextInt(config.xSpread) - rand.nextInt(config.xSpread), rand.nextInt(config.ySpread) - rand.nextInt(config.ySpread), rand.nextInt(config.zSpread) - rand.nextInt(config.zSpread));
    }

    @Override
    public BlockState getFlowerToPlace(Random rand, BlockPos pos, U config) {
        return config.stateProvider.getBlockState(rand, pos).with(CartwheelBlock.FACING, Direction.Plane.HORIZONTAL.random(rand));
    }
}