package com.minecraftabnormals.environmental.integration.jei;

import com.minecraftabnormals.environmental.common.item.crafting.SawingRecipe;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SawingCategory implements IRecipeCategory<SawingRecipe> {
	public static final ResourceLocation SAWING = new ResourceLocation(Environmental.MOD_ID, "sawing");

	private static final int inputSlot = 0;
	private static final int outputSlot = 1;

	public static final int width = 82;
	public static final int height = 34;

	private final IDrawable background;
	private final IDrawable icon;
	private final String localizedName;

	public SawingCategory(IGuiHelper guiHelper) {
		ResourceLocation location = EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL;
		background = guiHelper.createDrawable(location, 0, 220, width, height);
		icon = guiHelper.createDrawableIngredient(new ItemStack(EnvironmentalBlocks.SAWMILL.get()));
		this.localizedName = I18n.get("gui." + Environmental.MOD_ID + ".category.sawing");
	}

	@Override
	public ResourceLocation getUid() {
		return SAWING;
	}

	@Override
	public Class<? extends SawingRecipe> getRecipeClass() {
		return SawingRecipe.class;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(SawingRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SawingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(inputSlot, true, 0, 8);
		guiItemStacks.init(outputSlot, false, 60, 8);

		guiItemStacks.set(ingredients);
	}
}