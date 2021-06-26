package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.common.entity.MudBallEntity;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MudBallItem extends Item {
	public MudBallItem(Item.Properties builder) {
		super(builder);
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (!playerIn.abilities.instabuild) {
			itemstack.shrink(1);
		}

		worldIn.playSound((PlayerEntity) null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SLIME_BLOCK_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			MudBallEntity mudballentity = new MudBallEntity(worldIn, playerIn);
			mudballentity.setItem(new ItemStack(EnvironmentalItems.MUD_BALL.get()));
			mudballentity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 0.6F, 1.0F);
			worldIn.addFreshEntity(mudballentity);
		}

		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}
}
