package com.team_abnormals.environmental.common.enchantment;

import com.team_abnormals.environmental.core.registry.EnvironmentalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ReachingEnchantment extends Enchantment {

	public ReachingEnchantment() {
		super(Rarity.RARE, EnvironmentalEnchantments.CONSTRUCTOR_BELT, new EquipmentSlotType[] {EquipmentSlotType.LEGS});
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
}
