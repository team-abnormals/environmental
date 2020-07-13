package com.team_abnormals.environmental.integration.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.team_abnormals.environmental.common.inventory.container.KilnContainer;
import com.team_abnormals.environmental.common.item.crafting.BakingRecipe;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalRecipes;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.util.ErrorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class EnvironmentalJEIPlugin implements IModPlugin {
	@Nullable
	private BakingCategory bakingCategory;
	
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Environmental.MODID, Environmental.MODID);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		bakingCategory = new BakingCategory(guiHelper);
		registration.addRecipeCategories(bakingCategory);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ErrorUtil.checkNotNull(bakingCategory, "bakingCategory");

		Results recipes = getValidRecipes(bakingCategory);
		registration.addRecipes(recipes.getBakingRecipes(), BakingCategory.BAKING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(KilnScreen.class, 78, 32, 28, 23, BakingCategory.BAKING, VanillaRecipeCategoryUid.FUEL);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(KilnContainer.class, BakingCategory.BAKING, 0, 1, 3, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(EnvironmentalBlocks.KILN.get()), BakingCategory.BAKING, VanillaRecipeCategoryUid.FUEL);
	}
	
	public static class Results {
		private final List<BakingRecipe> bakingRecipes = new ArrayList<>();
		private final List<BakingRecipe> sawingRecipes = new ArrayList<>();

		public List<BakingRecipe> getBakingRecipes() {
			return bakingRecipes;
		}
		
		public List<BakingRecipe> getSawingRecipes() {
			return sawingRecipes;
		}
	}

	public static Results getValidRecipes(IRecipeCategory<BakingRecipe> bakingCategory) {

		Results results = new Results();
		ClientWorld world = Minecraft.getInstance().world;
		RecipeManager recipeManager = world.getRecipeManager();

		for (BakingRecipe recipe : getRecipes(recipeManager, EnvironmentalRecipes.RecipeTypes.BAKING)) {
			results.bakingRecipes.add(recipe);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	private static <C extends IInventory, T extends IRecipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, IRecipeType<T> recipeType) {
		Map<ResourceLocation, IRecipe<C>> recipesMap = recipeManager.getRecipes(recipeType);
		return (Collection<T>) recipesMap.values();
	}

}