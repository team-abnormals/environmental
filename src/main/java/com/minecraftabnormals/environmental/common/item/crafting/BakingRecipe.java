package com.minecraftabnormals.environmental.common.item.crafting;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class BakingRecipe extends AbstractCookingRecipe {
	public BakingRecipe(ResourceLocation p_i50022_1_, String p_i50022_2_, Ingredient p_i50022_3_, ItemStack p_i50022_4_, float p_i50022_5_, int p_i50022_6_) {
		super(EnvironmentalRecipes.RecipeTypes.BAKING, p_i50022_1_, p_i50022_2_, p_i50022_3_, p_i50022_4_, p_i50022_5_, p_i50022_6_);
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(EnvironmentalBlocks.KILN.get());
	}

	public IRecipeSerializer<?> getSerializer() {
		return EnvironmentalRecipes.Serailizers.BAKING.get();
	}

	public static class Serializer extends CookingRecipeSerializer<BakingRecipe> {

		public Serializer() {
			super(BakingRecipe::new, 100);
		}
	}
}