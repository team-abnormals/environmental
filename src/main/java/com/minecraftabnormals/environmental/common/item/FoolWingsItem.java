package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.client.model.FoolWingsModel;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class FoolWingsItem extends ArmorItem {

	public FoolWingsItem(Properties properties) {
		super(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, properties);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack, EquipmentSlotType armorSlot, A _default) {
		return (A) new FoolWingsModel(1.0F);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return Environmental.MODID + ":textures/models/armor/fool_wings.png";
	}
}
