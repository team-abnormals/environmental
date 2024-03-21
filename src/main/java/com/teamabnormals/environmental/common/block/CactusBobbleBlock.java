package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactusBobbleBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);

	public CactusBobbleBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState p_152917_, BlockGetter p_152918_, BlockPos p_152919_, CollisionContext p_152920_) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState p_152926_, Direction p_152927_, BlockState p_152928_, LevelAccessor p_152929_, BlockPos p_152930_, BlockPos p_152931_) {
		return !p_152926_.canSurvive(p_152929_, p_152930_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152926_, p_152927_, p_152928_, p_152929_, p_152930_, p_152931_);
	}

	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(EnvironmentalBlockTags.CACTUS_BOBBLE_PLANTABLE_ON);
	}
}