package com.minecraftabnormals.environmental.common.world.gen.util;

import java.util.Random;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraftforge.common.IPlantable;

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

    public static boolean isAir(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IBlockReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, BlockState::isAir);
        else return worldIn.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader) worldIn, pos));
    }

    public static boolean isLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
            return p_214579_0_.isIn(BlockTags.LEAVES);
        });
    }

    public static boolean isAirOrLeavesOrVines(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IWorldReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, (state) -> {
                return state.isAir() || state.isIn(BlockTags.LEAVES);
            });
        else
            return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
    }

    public static boolean isLog(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
            return p_214579_0_.isIn(BlockTags.LOGS);
        });
    }

    public static boolean isAirOrLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (worldIn instanceof net.minecraft.world.IWorldReader) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
        return worldIn.hasBlockState(pos, (state) -> {
            return state.isAir() || state.isIn(BlockTags.LEAVES);
        });
    }


    public static void placeVines(IWorldGenerationReader world, Random random, BlockPos pos, BlockState leaf, BlockState vineLower, BlockState vineUpper) {
        int length = WisteriaTreeUtils.getLengthByNeighbors(world, random, pos);
        if (random.nextInt(6) != 5 && WisteriaTreeUtils.isAir(world, pos) && !WisteriaTreeUtils.isLog(world, pos)) {
            switch (length) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, leaf);
                    break;
                case 4:
                    if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, leaf);
                    if (WisteriaTreeUtils.isAir(world, pos.down())) placeLeafAt(world, pos.down(), leaf);
                    break;
                case 5:
                    if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, leaf);
                    if (WisteriaTreeUtils.isAir(world, pos.down())) placeLeafAt(world, pos.down(), leaf);
                    if (WisteriaTreeUtils.isAir(world, pos.down(2))) placeLeafAt(world, pos.down(2), leaf);
                    break;
            }
        }
    }

    public static void placeLeafAt(IWorldGenerationReader worldIn, BlockPos pos, BlockState leaf) {
        if (WisteriaTreeUtils.isAirOrLeavesOrVines(worldIn, pos)) {
            setForcedState(worldIn, pos, leaf);
        }
    }

    public static void setForcedState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state, 18);
    }

    public static void setDirtAt(IWorld worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            setForcedState(worldIn, pos, Blocks.DIRT.getDefaultState());
        }
    }
    
	public static boolean isValidGround(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable)EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get());
	}
}