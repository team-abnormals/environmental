package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Maps;
import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.Map;
import java.util.function.Supplier;

public class ColoredWisteriaLeavesBlock extends LeavesBlock implements WisteriaLeafColorBlock {
	private static final Map<WisteriaColor, Supplier<Block>> LEAVES = Util.make(Maps.newHashMap(), (map) -> {
		map.put(WisteriaColor.PINK, EnvironmentalBlocks.PINK_WISTERIA_LEAVES);
		map.put(WisteriaColor.BLUE, EnvironmentalBlocks.BLUE_WISTERIA_LEAVES);
		map.put(WisteriaColor.PURPLE, EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES);
		map.put(WisteriaColor.WHITE, EnvironmentalBlocks.WHITE_WISTERIA_LEAVES);
	});
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	private final WisteriaColor color;

	public ColoredWisteriaLeavesBlock(Block.Properties properties, WisteriaColor color) {
		super(properties);
		this.color = color;
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, Half.BOTTOM).setValue(DISTANCE, 7).setValue(PERSISTENT, false).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HALF);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (direction == Direction.DOWN && state.getValue(HALF) == Half.TOP) {
			if (offsetState.getBlock() instanceof WisteriaLeafColorBlock leaves && leaves.causesBlendTexture(offsetState))
				return getLeavesFromColor(leaves.getColor()).defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(HALF, Half.TOP);
			else
				return EnvironmentalBlocks.WISTERIA_LEAVES.get().defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
		}
		return super.updateShape(state, direction, offsetState, level, pos, offsetPos);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return state.getValue(HALF) == Half.TOP ? new ItemStack(EnvironmentalBlocks.WISTERIA_LEAVES.get()) : super.getCloneItemStack(level, pos, state);
	}

	@Override
	public WisteriaColor getColor() {
		return this.color;
	}

	@Override
	public boolean causesBlendTexture(BlockState state) {
		return state.getValue(HALF) == Half.BOTTOM;
	}

	public static ColoredWisteriaLeavesBlock getLeavesFromColor(WisteriaColor color) {
		return (ColoredWisteriaLeavesBlock) LEAVES.get(color).get();
	}
}