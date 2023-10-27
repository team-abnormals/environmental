package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.blueprint.common.block.wood.BlueprintLeavesBlock;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class HibiscusLeavesBlock extends BlueprintLeavesBlock implements BonemealableBlock {

	public HibiscusLeavesBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)));
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean isClient) {
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
			ForgeRegistries.BLOCKS.tags().getTag(EnvironmentalBlockTags.WALL_HIBISCUSES).getRandomElement(random).ifPresent((block) -> {
				level.setBlockAndUpdate(pos.relative(direction), WallHibiscusBlock.setPropertiesForDirection(block.defaultBlockState(), direction, random));
			});
		}
	}
}