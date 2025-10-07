package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class PottedCartwheelBlock extends FlowerPotBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public PottedCartwheelBlock(Block flower, Properties properties) {
		super(flower, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@SubscribeEvent
	public static void interact(RightClickBlock event) {
		Level level = event.getLevel();
		ItemStack stack = event.getItemStack();
		BlockPos pos = event.getPos();
		Player player = event.getEntity();

		if (level.getBlockState(pos).is(Blocks.FLOWER_POT) && stack.is(EnvironmentalBlocks.CARTWHEEL.get().asItem())) {
			level.setBlock(pos, EnvironmentalBlocks.POTTED_CARTWHEEL.get().defaultBlockState().setValue(FACING, player.getDirection().getOpposite()), 3);
			level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			player.awardStat(Stats.POT_FLOWER);
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			event.setUseBlock(TriState.FALSE);
			event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
			event.setCanceled(true);
		}
	}
}
