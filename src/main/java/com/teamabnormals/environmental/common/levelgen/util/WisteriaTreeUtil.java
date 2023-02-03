package com.teamabnormals.environmental.common.levelgen.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.teamabnormals.blueprint.core.util.TreeUtil;
import com.teamabnormals.environmental.common.block.WisteriaLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

import java.util.List;
import java.util.Random;
import java.util.Set;

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

	public static void placeVines(LevelSimulatedRW world, Random random, BlockPos pos, BlockState leaf) {
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

	public static void updateLeaves(LevelAccessor level, Set<BlockPos> logPositions) {
		List<Set<BlockPos>> list = Lists.newArrayList();

		for (int j = 0; j < 7; ++j) {
			list.add(Sets.newHashSet());
		}
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		for (BlockPos pos : Lists.newArrayList(logPositions)) {
			for (Direction direction : Direction.values()) {
				mutablePos.setWithOffset(pos, direction);
				if (!logPositions.contains(mutablePos)) {
					BlockState state = level.getBlockState(mutablePos);
					if (state.hasProperty(WisteriaLeavesBlock.DISTANCE)) {
						list.get(0).add(mutablePos.immutable());
						TreeUtil.setForcedState(level, mutablePos, state.setValue(WisteriaLeavesBlock.DISTANCE, 1));
					}
				}
			}
		}

		for (int l = 1; l < 7; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);

			for (BlockPos pos : set) {
				for (Direction direction1 : Direction.values()) {
					mutablePos.setWithOffset(pos, direction1);
					if (!set.contains(mutablePos) && !set1.contains(mutablePos)) {
						BlockState state = level.getBlockState(mutablePos);
						if (state.hasProperty(WisteriaLeavesBlock.DISTANCE)) {
							int k = state.getValue(WisteriaLeavesBlock.DISTANCE);
							if (k > l + 1) {
								TreeUtil.setForcedState(level, mutablePos, state.setValue(WisteriaLeavesBlock.DISTANCE, l + 1));
								set1.add(mutablePos.immutable());
							}
						}
					}
				}
			}
		}
	}
}