package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class EnvironmentalTiers {
	public static class Armor {
		public static final BlueprintArmorMaterial EXPLORER = new BlueprintArmorMaterial(new ResourceLocation(Environmental.MOD_ID, "explorer"), 12, new int[]{2, 3, 2, 3}, 17, () -> SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));
		public static final BlueprintArmorMaterial YAK = new BlueprintArmorMaterial(new ResourceLocation(Environmental.MOD_ID, "yak"), 5, new int[]{3, 3, 3, 3}, 15, () -> SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(EnvironmentalItems.YAK_HAIR.get()));
	}
}
