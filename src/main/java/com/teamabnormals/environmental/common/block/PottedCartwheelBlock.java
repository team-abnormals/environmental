package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID)
public class PottedCartwheelBlock extends FlowerPotBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public PottedCartwheelBlock(Block flower, Properties properties) {
		super(flower, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
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
	public static void interact(PlayerInteractEvent.RightClickBlock event) {
		Level level = event.getLevel();
		ItemStack stack = event.getItemStack();
		BlockPos pos = event.getPos();
		if (level.getBlockState(pos).getBlock() == Blocks.FLOWER_POT && stack.getItem() == EnvironmentalBlocks.CARTWHEEL.get().asItem()) {
			System.out.println(event.getEntity().getDirection().getOpposite());
			level.setBlock(pos, EnvironmentalBlocks.POTTED_CARTWHEEL.get().defaultBlockState().setValue(FACING, event.getEntity().getDirection().getOpposite()), 0);
			event.getEntity().awardStat(Stats.POT_FLOWER);
			if (!event.getEntity().getAbilities().instabuild) {
				stack.shrink(1);
			}
			event.setUseBlock(Event.Result.DENY);
			event.setCancellationResult(InteractionResult.SUCCESS);
			event.setCanceled(true);
		}
	}
}
