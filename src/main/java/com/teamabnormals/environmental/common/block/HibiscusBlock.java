package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamabnormals.blueprint.common.block.BlueprintFlowerBlock;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HibiscusBlock extends BlueprintFlowerBlock implements BonemealableBlock {
	public static final HashMap<DyeColor, Pair<Supplier<Block>, Supplier<Block>>> COLOR_TO_HIBISCUS = Util.make(Maps.newHashMap(), (list) -> {
		list.put(DyeColor.YELLOW, Pair.of(EnvironmentalBlocks.YELLOW_HIBISCUS, EnvironmentalBlocks.YELLOW_WALL_HIBISCUS));
		list.put(DyeColor.ORANGE, Pair.of(EnvironmentalBlocks.ORANGE_HIBISCUS, EnvironmentalBlocks.ORANGE_WALL_HIBISCUS));
		list.put(DyeColor.RED, Pair.of(EnvironmentalBlocks.RED_HIBISCUS, EnvironmentalBlocks.RED_WALL_HIBISCUS));
		list.put(DyeColor.PINK, Pair.of(EnvironmentalBlocks.PINK_HIBISCUS, EnvironmentalBlocks.PINK_WALL_HIBISCUS));
		list.put(DyeColor.MAGENTA, Pair.of(EnvironmentalBlocks.MAGENTA_HIBISCUS, EnvironmentalBlocks.MAGENTA_WALL_HIBISCUS));
		list.put(DyeColor.PURPLE, Pair.of(EnvironmentalBlocks.PURPLE_HIBISCUS, EnvironmentalBlocks.PURPLE_WALL_HIBISCUS));
	});
	private final DyeColor color;

	public HibiscusBlock(DyeColor color) {
		super(() -> MobEffects.GLOWING, 8, PropertyUtil.FLOWER);
		this.color = color;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getBlock() == EnvironmentalBlocks.HIBISCUS_LEAVES.get() || super.mayPlaceOn(state, level, pos);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos pos, BlockState state, boolean isClient) {
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
		for (Direction direction : Direction.values()) {
			if (direction != Direction.DOWN) {
				BlockPos blockpos = pos.relative(direction);
				if (level.getBlockState(blockpos).isAir()) {
					validdirections.add(direction);
				}
			}
		}

		if (!validdirections.isEmpty()) {
			Direction direction = validdirections.get(random.nextInt(validdirections.size()));
			BlockPos blockpos = pos.relative(direction);
			Pair<Supplier<Block>, Supplier<Block>> hibiscuses = COLOR_TO_HIBISCUS.get(this.color);
			if (direction == Direction.UP) {
				level.setBlockAndUpdate(blockpos, hibiscuses.getLeft().get().defaultBlockState());
			} else {
				level.setBlockAndUpdate(blockpos, hibiscuses.getRight().get().defaultBlockState().setValue(WallHibiscusBlock.FACING, direction.getOpposite()));
			}
		}
	}
}
