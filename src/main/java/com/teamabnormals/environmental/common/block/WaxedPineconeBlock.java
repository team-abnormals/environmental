package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.NoteBlockEvent.Play;

public class WaxedPineconeBlock extends FallingBlock {

	public WaxedPineconeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (canFall(level, pos) && pos.getY() >= level.getMinBuildHeight()) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
			this.falling(fallingblockentity);
		}
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate) {
		if (action == ToolActions.AXE_STRIP && !(this instanceof PineconeBlock)) {
			Level level = context.getLevel();
			Player player = context.getPlayer();
			BlockPos pos = context.getClickedPos();
			level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3004, pos, 0);
			return EnvironmentalBlocks.PINECONE.get().defaultBlockState();
		}

		return super.getToolModifiedState(state, context, action, simulate);
	}

	public static boolean canFall(ServerLevel level, BlockPos pos) {
		return !supportedFromAbove(level, pos) && isFree(level.getBlockState(pos.below()));
	}

	public static boolean supportedFromAbove(ServerLevel level, BlockPos pos) {
		BlockState blockstate = level.getBlockState(pos.above());
		return Block.isFaceFull(blockstate.getCollisionShape(level, pos.above()), Direction.DOWN) && !(blockstate.getBlock() instanceof FallingBlock);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
	}
}