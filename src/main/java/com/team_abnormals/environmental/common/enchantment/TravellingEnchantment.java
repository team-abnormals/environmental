package com.team_abnormals.environmental.common.enchantment;

import com.team_abnormals.environmental.core.registry.EnvironmentalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class TravellingEnchantment extends Enchantment {

	public TravellingEnchantment() {
		super(Rarity.RARE, EnvironmentalEnchantments.WANDERER_BOOTS, new EquipmentSlotType[] {EquipmentSlotType.FEET});
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
