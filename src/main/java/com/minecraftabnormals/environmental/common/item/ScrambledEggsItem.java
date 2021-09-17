package com.minecraftabnormals.environmental.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SoupItem;
import net.minecraft.world.World;

public class ScrambledEggsItem extends SoupItem {
	public ScrambledEggsItem(Item.Properties properties) {
		super(properties);
	}

	public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.heal(2.0F);
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}