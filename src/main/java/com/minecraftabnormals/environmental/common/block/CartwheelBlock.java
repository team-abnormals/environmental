package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.Effect;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class CartwheelBlock extends AbnormalsFlowerBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public CartwheelBlock(Supplier<Effect> effect, int effectDuration, Properties properties) {
		super(effect, effectDuration, properties);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}