package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.client.model.ThiefHoodModel;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class ThiefHoodItem extends ArmorItem {

	public ThiefHoodItem(Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, EquipmentSlotType.HEAD, properties);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return (A) new ThiefHoodModel(1.0F);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/thief_hood.png";
	}
	
	@Override
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity) {
		return true;
	}

	@SubscribeEvent
	public static void playerNameEvent(PlayerEvent.NameFormat event) {
		ItemStack stack = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD);
		if (stack.getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
			event.setDisplayname(stack.hasDisplayName() ? new StringTextComponent(stack.getDisplayName().getString()).mergeStyle(TextFormatting.ITALIC) : new StringTextComponent("???"));
		}
	}

	@SubscribeEvent
	public static void hoodEquippedEvent(LivingEquipmentChangeEvent event) {
		if (event.getTo().getItem() == EnvironmentalItems.THIEF_HOOD.get() || event.getFrom().getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				((PlayerEntity) event.getEntityLiving()).refreshDisplayName();
			}
		}
	}
}
