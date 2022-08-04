package com.teamabnormals.environmental.common.levelgen.util;

import com.teamabnormals.blueprint.core.util.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

import java.util.Random;

public class WisteriaTreeUtil {

	public static int getLengthByNeighbors(LevelSimulatedRW world, Random random, BlockPos pos) {
		int length = random.nextInt(6); // max 4
		for (Direction direction : Direction.values()) {
			if (direction != Direction.UP && direction != Direction.DOWN) {
				if (Feature.isAir(world, pos.relative(direction))) length++; // max 2 min 1
				else if (TreeUtil.isLeaves(world, pos.relative(direction))) length--; // max 4 min 2
			}
		}
		return length;
	}

	public static void placeVines(LevelSimulatedRW world, Random random, BlockPos pos, BlockState leaf, BlockState vineLower, BlockState vineUpper) {
		int length = WisteriaTreeUtil.getLengthByNeighbors(world, random, pos);
		if (random.nextInt(6) != 5 && TreeFeature.isAir(world, pos) && !TreeUtil.isLog(world, pos)) {
			switch (length) {
				case 0:
				case 1:
				case 2:
					break;
				case 3:
					if (Feature.isAir(world, pos)) placeLeafAt(world, pos, leaf);
					break;
				case 4:
					if (Feature.isAir(world, pos)) placeLeafAt(world, pos, leaf);
					if (Feature.isAir(world, pos.below())) placeLeafAt(world, pos.below(), leaf);
					break;
				case 5:
					if (Feature.isAir(world, pos)) placeLeafAt(world, pos, leaf);
					if (Feature.isAir(world, pos.below())) placeLeafAt(world, pos.below(), leaf);
					if (Feature.isAir(world, pos.below(2))) placeLeafAt(world, pos.below(2), leaf);
					break;
			}
		}
	}

	public static boolean isAirOrLeavesOrReplaceable(LevelSimulatedReader level, BlockPos pos) {
		return level.isStateAtPosition(pos, Feature.isReplaceable(BlockTags.REPLACEABLE_PLANTS));
	}

	public static void placeLeafAt(LevelSimulatedRW worldIn, BlockPos pos, BlockState leaf) {
		if (isAirOrLeavesOrReplaceable(worldIn, pos)) {
			TreeUtil.setForcedState(worldIn, pos, leaf);
		}
	}
}