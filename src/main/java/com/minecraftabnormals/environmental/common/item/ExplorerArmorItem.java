package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTiers;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;

public class ExplorerArmorItem extends DyeableArmorItem {
	
	public ExplorerArmorItem(EquipmentSlotType slot, Properties properties) {
		super(EnvironmentalTiers.Armor.EXPLORER, slot, properties);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return getCorrectTexture(this.getRegistryName().getPath(), type);
	}

	public static String getCorrectTexture(String armor, String type) {
		if ("overlay".equals(type)) return Environmental.MODID + ":textures/models/armor/" + armor + "_overlay.png";
		else return Environmental.MODID + ":textures/models/armor/" + armor + ".png";
	}
}
