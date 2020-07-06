package com.team_abnormals.environmental.common.world.gen.util;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

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

	public static boolean isAir(IWorldGenerationReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IBlockReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, BlockState::isAir);
        else return worldIn.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader)worldIn, pos));
    }

    protected static boolean isLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
            return p_214579_0_.func_235714_a_(BlockTags.LEAVES);
        });
    }
    
    public static boolean isLog(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, (p_214579_0_) -> {
           return p_214579_0_.func_235714_a_(BlockTags.LOGS);
        });
     }
    

    public static void placeVines(IWorldGenerationReader world, Random random, BlockPos pos, BlockState leaf, BlockState vineLower, BlockState vineUpper, BaseTreeFeatureConfig config) {
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
				if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, config, random);
				break;
			case 4:
				if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, config, random);
				if (WisteriaTreeUtils.isAir(world, pos.down())) placeLeafAt(world, pos.down(), config, random);
				break;
			case 5:
				if (WisteriaTreeUtils.isAir(world, pos)) placeLeafAt(world, pos, config, random);
				if (WisteriaTreeUtils.isAir(world, pos.down())) placeLeafAt(world, pos.down(), config, random);
				if (WisteriaTreeUtils.isAir(world, pos.down(2))) placeLeafAt(world, pos.down(2), config, random);
				break;
			}
		}
	}

    public static void placeLeafAt(IWorldGenerationReader worldIn, BlockPos pos, BaseTreeFeatureConfig config, Random random) {
		if (WisteriaTreeUtils.isAirOrLeaves(worldIn, pos)) {
			setForcedState(worldIn, pos, config.leavesProvider.getBlockState(random, pos));
		}
	}

    public static boolean isAirOrLeaves(IWorldGenerationReader worldIn, BlockPos pos) {
		return isAir(worldIn, pos) || isLeaves(worldIn, pos);
	}

	public static boolean isLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos, BaseTreeFeatureConfig config, Random random) {
		if (worldIn instanceof net.minecraft.world.IWorldReader) // FORGE: Redirect to state method when possible
			return worldIn.hasBlockState(pos, state -> state == config.leavesProvider.getBlockState(random, pos));
		return worldIn.hasBlockState(pos, (p_227223_0_) -> {
			return config.leavesProvider.getBlockState(random, pos) == p_227223_0_;
		});
	}

	public static void setForcedState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlockState(pos, state, 18);
	}
}