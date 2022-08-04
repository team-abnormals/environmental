package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeTypes;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

	//TODO: Remove, add recipe book
	@Inject(at = @At("HEAD"), method = "getCategory", cancellable = true)
	private static void getCategory(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
		if (recipe.getType() == EnvironmentalRecipeTypes.BAKING.get() || recipe.getType() == EnvironmentalRecipeTypes.SAWING.get()) {
			cir.setReturnValue(RecipeBookCategories.UNKNOWN);
		}
	}
}
