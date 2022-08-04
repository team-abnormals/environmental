package com.teamabnormals.environmental.integration.jei;

import com.teamabnormals.environmental.client.gui.screens.inventory.KilnScreen;
import com.teamabnormals.environmental.common.inventory.KilnMenu;
import com.teamabnormals.environmental.common.item.crafting.BakingRecipe;
import com.teamabnormals.environmental.common.item.crafting.SawmillRecipe;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class EnvironmentalPlugin implements IModPlugin {
	public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
	public static final RecipeType<SawmillRecipe> SAWING = RecipeType.create(Environmental.MOD_ID, "sawing", SawmillRecipe.class);
	public static final RecipeType<BakingRecipe> BAKING = RecipeType.create(Environmental.MOD_ID, "baking", BakingRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Environmental.MOD_ID, Environmental.MOD_ID);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, EnvironmentalItems.SLABFISH_BUCKET.get(), (stack, context) -> stack.getTag() != null && stack.getTag().contains("SlabfishType", Tag.TAG_STRING) ? stack.getTag().getString("SlabfishType") : IIngredientSubtypeInterpreter.NONE);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registration.addRecipeCategories(new BakingRecipeCategory(guiHelper));
		registration.addRecipeCategories(new SawingRecipeCategory(guiHelper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<SawmillRecipe> sawmillRecipes = new ArrayList<>();
		sawmillRecipes.addAll(getRecipes(Minecraft.getInstance().level.getRecipeManager(), EnvironmentalRecipeTypes.SAWING.get()));
		registration.addRecipes(SAWING, sawmillRecipes);

		List<BakingRecipe> bakingRecipes = new ArrayList<>();
		bakingRecipes.addAll(getRecipes(Minecraft.getInstance().level.getRecipeManager(), EnvironmentalRecipeTypes.BAKING.get()));
		registration.addRecipes(BAKING, bakingRecipes);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(KilnScreen.class, 78, 32, 28, 23, BAKING, RecipeTypes.FUELING);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(KilnMenu.class, BAKING, 0, 1, 3, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(EnvironmentalBlocks.KILN.get()), BAKING, RecipeTypes.FUELING);
		registration.addRecipeCatalyst(new ItemStack(EnvironmentalBlocks.SAWMILL.get()), SAWING);
	}

	@SuppressWarnings("unchecked")
	private static <C extends Container, T extends Recipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, net.minecraft.world.item.crafting.RecipeType<T> recipeType) {
		Map<ResourceLocation, Recipe<C>> recipesMap = recipeManager.byType(recipeType);
		return (Collection<T>) recipesMap.values();
	}

}