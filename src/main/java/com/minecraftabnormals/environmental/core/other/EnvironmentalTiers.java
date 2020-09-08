package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.common.item.material.EnvironmentalArmorMaterial;
import com.minecraftabnormals.environmental.common.item.material.EnvironmentalItemTier;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;

public class EnvironmentalTiers 
{
	public static class Armor 
	{
		public static final EnvironmentalArmorMaterial EXPLORER = new EnvironmentalArmorMaterial("explorer", 12, new int[] { 2, 3, 2, 3 }, 17, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.fromItems(Items.LEATHER));
		public static final EnvironmentalArmorMaterial YAK = new EnvironmentalArmorMaterial("yak", 5, new int[] { 3, 3, 3, 3 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.fromItems(EnvironmentalItems.YAK_HAIR.get()));
	}

	public static class Item 
	{
		public static final EnvironmentalItemTier EXECUTIONER = new EnvironmentalItemTier(0, 953, 0, 7.5f, 10, () -> Ingredient.fromItems(Items.IRON_INGOT));
	}
}
