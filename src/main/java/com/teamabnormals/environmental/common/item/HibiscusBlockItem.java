package com.teamabnormals.environmental.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;

public class HibiscusBlockItem extends StandingAndWallBlockItem {

	public HibiscusBlockItem(Block block, Block wallBlock, Properties properties) {
		super(block, wallBlock, properties);
	}

	@Nullable
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState blockstate = this.wallBlock.getStateForPlacement(context);
		BlockState blockstate1 = null;
		LevelReader level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();

		for (Direction direction : context.getNearestLookingDirections()) {
			boolean flag = direction == Direction.DOWN && !(level.getBlockState(blockpos.below()).getBlock() instanceof LeavesBlock);
			BlockState blockstate2 = flag ? this.getBlock().getStateForPlacement(context) : blockstate;
			if (blockstate2 != null && blockstate2.canSurvive(level, blockpos)) {
				blockstate1 = blockstate2;
				break;
			}
		}

		return blockstate1 != null && level.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
	}
}