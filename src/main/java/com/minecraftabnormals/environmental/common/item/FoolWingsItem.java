package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.client.model.FoolWingsModel;
import com.minecraftabnormals.environmental.common.network.message.CFlapMessage;
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
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.UUID;

@EventBusSubscriber(modid = Environmental.MODID)
public class FoolWingsItem extends ArmorItem {
	private static final HashMap<UUID, Boolean> hasJumpedMap = new HashMap<>();

	public FoolWingsItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.CHEST, properties);
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return FoolWingsModel.get(1.0F);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/fool_wings.png";
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		if (!event.getEntityLiving().world.isRemote())
			return;

		if (event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			UUID uuid = player.getUniqueID();
			ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);

			if (!hasJumpedMap.containsKey(uuid) || player.isOnGround())
				hasJumpedMap.put(uuid, false);
			if (!(stack.getItem() instanceof FoolWingsItem))
				return;

			if (!player.isOnGround() && !player.isElytraFlying() && !player.abilities.isFlying) {
				if (player.isJumping && !hasJumpedMap.get(uuid) && player.getMotion().getY() < 0) {
					player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.4F, 2.0F);
					player.setMotion(player.getMotion().getX(), 0.5D, player.getMotion().getZ());
					hasJumpedMap.put(uuid, true);
					Environmental.PLAY.send(PacketDistributor.SERVER.noArg(), new CFlapMessage());
				}
			}
		}
	}

	public static void tryJump(PlayerEntity player) {
		ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
		if (!(stack.getItem() instanceof FoolWingsItem))
			return;

		player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.4F, 2.0F);
		stack.damageItem(1, player, p -> p.sendBreakAnimation(EquipmentSlotType.CHEST));
	}
}
