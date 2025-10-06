package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class AbstractHibiscusBlock extends FlowerBlock implements BonemealableBlock {

	public AbstractHibiscusBlock(Holder<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
	}

	protected Block getWallHibiscus() {
		return null;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader blockGetter, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, EnvironmentalBlocks.HIBISCUS_LEAVES.get().defaultBlockState());

		List<Direction> validdirections = Lists.newArrayList();
		int i = 2 + random.nextInt(2) + random.nextInt(2);

		for (Direction direction : Direction.values()) {
			BlockPos blockpos = pos.relative(direction);
			if (level.getBlockState(blockpos).isAir()) {
				validdirections.add(direction);
			}
		}

		for (int j = 0; j < i && !validdirections.isEmpty(); j++) {
			Direction direction = validdirections.get(random.nextInt(validdirections.size()));
			level.setBlockAndUpdate(pos.relative(direction), WallHibiscusBlock.setPropertiesForDirection(this.getWallHibiscus().defaultBlockState(), direction, random));
			validdirections.remove(direction);
		}
	}
}
