package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class WaxedPineconeBlock extends Block {

	public WaxedPineconeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility action, boolean simulate) {
		if (action == ItemAbilities.AXE_STRIP) {
			Level level = context.getLevel();
			Player player = context.getPlayer();
			BlockPos pos = context.getClickedPos();
			level.levelEvent(player, 3004, pos, 0);
			return EnvironmentalBlocks.PINECONE.get().defaultBlockState();
		}

		return super.getToolModifiedState(state, context, action, simulate);
	}
}