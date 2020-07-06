package com.team_abnormals.environmental.core.other;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;

@SuppressWarnings("deprecation")
public class WisteriaTreeUtils {
    public static int getLengthByNeighbors(IWorldGenerationReader world, Random random, BlockPos pos) {
        int length = random.nextInt(6); // max 4
        for (Direction direction : Direction.values()) {
            if (direction != Direction.UP && direction != Direction.DOWN) {
                if (isAir(world, pos.offset(direction))) length++; // max 2 min 1
                else if (isLeaves(world, pos.offset(direction))) length--; // max 4 min 2
            }
        }
        return length;
    }

	protected static boolean isAir(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IBlockReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, BlockState::isAir);
        else return worldIn.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader)worldIn, pos));
    }

    protected static boolean isLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
            return p_214579_0_.isIn(BlockTags.LEAVES);
        });
    }
    
    public static boolean isLog(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
           return p_214579_0_.isIn(BlockTags.LOGS);
        });
     }
}