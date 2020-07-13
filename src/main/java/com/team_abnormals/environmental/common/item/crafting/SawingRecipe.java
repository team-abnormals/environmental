package com.team_abnormals.environmental.common.item.crafting;

import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalRecipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SawingRecipe extends SingleItemRecipe {
	public SawingRecipe(ResourceLocation p_i50021_1_, String p_i50021_2_, Ingredient p_i50021_3_, ItemStack p_i50021_4_) {
		super(EnvironmentalRecipes.RecipeTypes.SAWING, EnvironmentalRecipes.Serailizers.SAWING.get(), p_i50021_1_, p_i50021_2_, p_i50021_3_, p_i50021_4_);
	}

	public boolean matches(IInventory inv, World worldIn) {
		return this.ingredient.test(inv.getStackInSlot(0));
	}

	public ItemStack getIcon() {
		return new ItemStack(EnvironmentalBlocks.SAWMILL.get());
	}
}