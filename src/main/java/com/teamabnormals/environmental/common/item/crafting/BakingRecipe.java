package com.teamabnormals.environmental.common.item.crafting;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeSerializers;
import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class BakingRecipe extends AbstractCookingRecipe {
	public BakingRecipe(ResourceLocation p_i50022_1_, String p_i50022_2_, Ingredient p_i50022_3_, ItemStack p_i50022_4_, float p_i50022_5_, int p_i50022_6_) {
		super(EnvironmentalRecipeTypes.BAKING.get(), p_i50022_1_, p_i50022_2_, p_i50022_3_, p_i50022_4_, p_i50022_5_, p_i50022_6_);
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(EnvironmentalBlocks.KILN.get());
	}

	public RecipeSerializer<?> getSerializer() {
		return EnvironmentalRecipeSerializers.BAKING.get();
	}

	public static class Serializer extends SimpleCookingSerializer<BakingRecipe> {

		public Serializer() {
			super(BakingRecipe::new, 100);
		}
	}
}