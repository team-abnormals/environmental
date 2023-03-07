package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.function.Supplier;

public class CartwheelBlock extends BlueprintFlowerBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public CartwheelBlock(Supplier<MobEffect> effect, int effectDuration, Properties properties) {
		super(effect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
		return !state.getCollisionShape(getter, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(getter, pos, Direction.UP);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}