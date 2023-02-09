package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.block.LargeLilyPadBlock;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

public class LargeLilyPadItem extends BlockItem {
	
	public LargeLilyPadItem(Item.Properties builder) {
		super(EnvironmentalBlocks.LARGE_LILY_PAD.get(), builder);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		BlockHitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
		if (result.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(stack);
		} else {
			if (result.getType() == HitResult.Type.BLOCK) {
				BlockPos pos = result.getBlockPos();
				Direction direction = result.getDirection();
				if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos.relative(direction), direction, stack)) {
					return InteractionResultHolder.fail(stack);
				}

				BlockPos abovePos = pos.above();
				if (LargeLilyPadBlock.isValidBlock(level.getBlockState(pos), level, pos) && LargeLilyPadBlock.checkPositions(level, abovePos, this.getBlock().defaultBlockState())) {
					BlockSnapshot blocksnapshot = BlockSnapshot.create(level.dimension(), level, abovePos);
					if (!level.isClientSide())
						LargeLilyPadBlock.placeAt(level, abovePos, this.getBlock().defaultBlockState(), 18);
					if (ForgeEventFactory.onBlockPlace(player, blocksnapshot, Direction.UP)) {
						blocksnapshot.restore(true, false);
						return InteractionResultHolder.fail(stack);
					}

					if (player instanceof ServerPlayer) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, abovePos, stack);
					}

					if (!player.getAbilities().instabuild) {
						stack.shrink(1);
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					level.playSound(player, pos, SoundEvents.LILY_PAD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
					return InteractionResultHolder.success(stack);
				}
			}

			return InteractionResultHolder.fail(stack);
		}
	}
}