package com.teamabnormals.environmental.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PineconeBlock extends FallingBlock {

    public PineconeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (canFall(level, pos) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
            this.falling(fallingblockentity);
        }
    }

    public static boolean canFall(ServerLevel level, BlockPos pos) {
        return !supportedFromAbove(level, pos) && isFree(level.getBlockState(pos.below()));
    }

    public static boolean supportedFromAbove(ServerLevel level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.above());
        return Block.isFaceFull(blockstate.getCollisionShape(level, pos.above()), Direction.DOWN) && !(blockstate.getBlock() instanceof FallingBlock);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    }
}