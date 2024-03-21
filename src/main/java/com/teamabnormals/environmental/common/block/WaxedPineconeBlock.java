package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class WaxedPineconeBlock extends Block {

	public WaxedPineconeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate) {
		if (action == ToolActions.AXE_STRIP) {
			Level level = context.getLevel();
			Player player = context.getPlayer();
			BlockPos pos = context.getClickedPos();
			level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3004, pos, 0);
			return EnvironmentalBlocks.PINECONE.get().defaultBlockState();
		}

		return super.getToolModifiedState(state, context, action, simulate);
	}
}