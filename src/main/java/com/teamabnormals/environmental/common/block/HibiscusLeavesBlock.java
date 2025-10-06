package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class HibiscusLeavesBlock extends LeavesBlock implements BonemealableBlock {

	public HibiscusLeavesBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, true));
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader blockGetter, BlockPos pos, BlockState state) {
		for (Direction direction : Direction.values()) {
			if (blockGetter.getBlockState(pos.relative(direction)).isAir()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		List<Direction> directions = Lists.newArrayList();
		for (Direction direction : Direction.values()) {
			if (level.getBlockState(pos.relative(direction)).isAir()) {
				directions.add(direction);
			}
		}

		if (!directions.isEmpty()) {
			Direction direction = directions.get(random.nextInt(directions.size()));
			BuiltInRegistries.BLOCK.getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).get().getRandomElement(random).ifPresent((block) -> {
				level.setBlockAndUpdate(pos.relative(direction), WallHibiscusBlock.setPropertiesForDirection(block.value().defaultBlockState(), direction, random));
			});
		}
	}
}