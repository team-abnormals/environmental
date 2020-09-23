package com.minecraftabnormals.environmental.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalRecipes;

import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.crafting.IRecipe;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

	@Inject(at = @At("HEAD"), method = "getCategory", cancellable = true)
	private static void getCategory(IRecipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
		if (recipe.getType() == EnvironmentalRecipes.RecipeTypes.BAKING || recipe.getType() == EnvironmentalRecipes.RecipeTypes.SAWING) {
			cir.setReturnValue(RecipeBookCategories.UNKNOWN);
		}
	}
}
