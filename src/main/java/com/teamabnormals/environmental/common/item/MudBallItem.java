package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class MudBallItem extends Item implements ProjectileItem {

	public MudBallItem(Item.Properties builder) {
		super(builder);
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.MUD_HIT, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			ThrownMudBall mudBall = new ThrownMudBall(level, player);
			mudBall.setItem(new ItemStack(EnvironmentalItems.MUD_BALL.get()));
			mudBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.6F, 1.0F);
			level.addFreshEntity(mudBall);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		ThrownMudBall mudBall = new ThrownMudBall(level, pos.x(), pos.y(), pos.z());
		mudBall.setItem(stack);
		return mudBall;
	}
}
