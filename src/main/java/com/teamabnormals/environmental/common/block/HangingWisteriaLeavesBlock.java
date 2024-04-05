package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;

public class HangingWisteriaLeavesBlock extends HangingLeavesBlock implements IForgeShearable, WisteriaLeafColorBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	protected static final VoxelShape TOP_SHAPE = Block.box(1, 0, 1, 15, 16, 15);

	private final WisteriaColor color;

	public HangingWisteriaLeavesBlock(Block.Properties properties, WisteriaColor color) {
		super(properties);
		this.color = color;
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(HALF) == DoubleBlockHalf.UPPER ? TOP_SHAPE : SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return (level.getBlockState(pos.above()) == this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER) && !level.isWaterAt(pos)) || super.canSurvive(state, level, pos);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER && !level.getBlockState(pos.below()).is(this)) {
			level.setBlockAndUpdate(pos, state.setValue(HALF, DoubleBlockHalf.LOWER));
		}
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if (level.getBlockState(pos.above()) == this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER) && !level.isWaterAt(pos)) {
			level.setBlock(pos.above(), level.getBlockState(pos.above()).setValue(HALF, DoubleBlockHalf.UPPER), 2);
		}
		return super.getStateForPlacement(context);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF);
	}

	@Override
	public WisteriaColor getColor() {
		return this.color;
	}
}