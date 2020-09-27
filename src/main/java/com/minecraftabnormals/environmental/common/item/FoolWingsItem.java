package com.minecraftabnormals.environmental.common.item;

import java.util.HashMap;
import java.util.UUID;

import com.minecraftabnormals.environmental.client.model.FoolWingsModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class FoolWingsItem extends ArmorItem {
	private static HashMap<UUID, Boolean> hasJumpedMap = new HashMap<UUID, Boolean>();

	public FoolWingsItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.CHEST, properties);
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return (A) new FoolWingsModel(1.0F);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/fool_wings.png";
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		UUID uuid = event.getEntityLiving().getUniqueID();

		if (event.getEntityLiving() instanceof PlayerEntity && hasJumpedMap.containsKey(uuid)) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);

			if (player.isOnGround()) 
				hasJumpedMap.put(uuid, false);
			if (!(stack.getItem() instanceof FoolWingsItem)) 
				return;

			if (!player.isOnGround() && !hasJumpedMap.get(uuid) && !player.isElytraFlying() && !player.abilities.isFlying) {
				if (player.isJumping && player.getMotion().getY() < 0) {
					player.setMotion(player.getMotion().getX(), 0.5D, player.getMotion().getZ());
					hasJumpedMap.put(uuid, true);
					player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.4F, 2.0F);
					stack.damageItem(1, player, (p_233654_0_) -> {
						p_233654_0_.sendBreakAnimation(EquipmentSlotType.CHEST);
					});
				}

			}
		} else if (event.getEntityLiving() instanceof PlayerEntity) {
			hasJumpedMap.put(uuid, false);
		}
	}
}
