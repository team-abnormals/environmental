package com.minecraftabnormals.environmental.common.enchantment;

import com.minecraftabnormals.environmental.common.item.WandererBootsItem;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class TravellingEnchantment extends Enchantment {

	public TravellingEnchantment() {
		super(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] { EquipmentSlotType.FEET });
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof WandererBootsItem && canApplyAtEnchantingTable(stack);
	}
}
