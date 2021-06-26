package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.abnormals_core.core.api.AbnormalsArmorMaterial;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

public class EnvironmentalTiers {
	public static class Armor {
		public static final AbnormalsArmorMaterial EXPLORER = new AbnormalsArmorMaterial(new ResourceLocation(Environmental.MOD_ID, "explorer"), 12, new int[]{2, 3, 2, 3}, 17, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(Items.RABBIT_HIDE));
		public static final AbnormalsArmorMaterial YAK = new AbnormalsArmorMaterial(new ResourceLocation(Environmental.MOD_ID, "yak"), 5, new int[]{3, 3, 3, 3}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(EnvironmentalItems.YAK_HAIR.get()));
	}
}
