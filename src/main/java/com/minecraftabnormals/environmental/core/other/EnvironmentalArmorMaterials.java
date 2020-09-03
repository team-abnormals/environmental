package com.minecraftabnormals.environmental.core.other;

import com.google.common.base.Supplier;
import com.minecraftabnormals.environmental.common.item.material.EnvironmentalArmorMaterial;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class EnvironmentalArmorMaterials {
	public static final EnvironmentalArmorMaterial EXPLORER	= createMaterial("explorer", 12, new int[] {2, 3, 2, 3}, 17, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.fromItems(Items.LEATHER));
	public static final EnvironmentalArmorMaterial YAK 		= createMaterial("yak", 5, 3, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.fromItems(EnvironmentalItems.YAK_HAIR.get()));
	
	private static EnvironmentalArmorMaterial createMaterial(String name, int maxDamageFactor, int damageReductionAmount, int enchantability, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> item) {
		return new EnvironmentalArmorMaterial(name, maxDamageFactor, new int[]{damageReductionAmount, damageReductionAmount, damageReductionAmount, damageReductionAmount}, enchantability, sound, toughness, knockbackResistance, item);
	};
	
	private static EnvironmentalArmorMaterial createMaterial(String name, int maxDamageFactor, int[] damageReductionAmount, int enchantability, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> item) {
		return new EnvironmentalArmorMaterial(name, maxDamageFactor, damageReductionAmount, enchantability, sound, toughness, knockbackResistance, item);
	};
}
