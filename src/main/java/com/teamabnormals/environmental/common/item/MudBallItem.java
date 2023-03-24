package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MudBallItem extends Item {

	public MudBallItem(Item.Properties builder) {
		super(builder);
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (!playerIn.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.MUD_HIT, SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			ThrownMudBall mudballentity = new ThrownMudBall(worldIn, playerIn);
			mudballentity.setItem(new ItemStack(EnvironmentalItems.MUD_BALL.get()));
			mudballentity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 0.6F, 1.0F);
			worldIn.addFreshEntity(mudballentity);
		}

		playerIn.awardStat(Stats.ITEM_USED.get(this));
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}
}
