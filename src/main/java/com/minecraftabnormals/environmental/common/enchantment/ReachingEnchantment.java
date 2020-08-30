package com.minecraftabnormals.environmental.common.enchantment;

import com.minecraftabnormals.environmental.common.item.ArchitectBeltItem;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ReachingEnchantment extends Enchantment {

	public ReachingEnchantment() {
		super(Rarity.RARE, EnchantmentType.ARMOR, new EquipmentSlotType[] { EquipmentSlotType.LEGS });
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
		return stack.getItem() instanceof ArchitectBeltItem && canApplyAtEnchantingTable(stack);
	}
}
