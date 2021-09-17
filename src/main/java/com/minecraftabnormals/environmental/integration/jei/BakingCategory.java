package com.minecraftabnormals.environmental.integration.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.minecraftabnormals.environmental.common.item.crafting.BakingRecipe;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class BakingCategory implements IRecipeCategory<BakingRecipe> {
	public static final ResourceLocation BAKING = new ResourceLocation(Environmental.MOD_ID, "baking");
	private final IDrawableAnimated animatedFlame;
	protected final LoadingCache<BakingRecipe, IDrawableAnimated> cachedArrows;

	private static final int inputSlot = 0;
	private static final int outputSlot = 2;

	private final IDrawable background;
	private final IDrawable icon;
	private final String localizedName;

	public BakingCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 0, 114, 82, 54);
		this.icon = guiHelper.createDrawableIngredient(new ItemStack(EnvironmentalBlocks.KILN.get()));
		this.localizedName = I18n.get("gui." + Environmental.MOD_ID + ".category.baking");
		IDrawableStatic staticFlame = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 82, 114, 14, 14);
		this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		this.cachedArrows = CacheBuilder.newBuilder()
				.maximumSize(25)
				.build(new CacheLoader<BakingRecipe, IDrawableAnimated>() {
					@Override
					public IDrawableAnimated load(BakingRecipe key) {
						int cookTime = key.getCookingTime();
						if (cookTime <= 0) {
							cookTime = 100;
						}
						return guiHelper.drawableBuilder(new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png"), 82, 128, 24, 17)
								.buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
					}
				});
	}

	@Override
	public void draw(BakingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		animatedFlame.draw(matrixStack, 1, 20);

		IDrawableAnimated arrow = cachedArrows.getUnchecked(recipe);
		arrow.draw(matrixStack, 24, 18);

		drawExperience(recipe, matrixStack, 0);
		drawCookTime(recipe, matrixStack, 45);
	}

	protected void drawExperience(BakingRecipe recipe, MatrixStack matrixStack, int y) {
		float experience = recipe.getExperience();
		if (experience > 0) {
			TranslationTextComponent experienceString = new TranslationTextComponent("gui.jei.category.smelting.experience", experience);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(experienceString);
			fontRenderer.draw(matrixStack, experienceString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}

	protected void drawCookTime(BakingRecipe recipe, MatrixStack matrixStack, int y) {
		int cookTime = recipe.getCookingTime();
		if (cookTime > 0) {
			int cookTimeSeconds = cookTime / 20;
			TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(timeString);
			fontRenderer.draw(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}

	@Override
	public ResourceLocation getUid() {
		return BAKING;
	}

	@Override
	public Class<? extends BakingRecipe> getRecipeClass() {
		return BakingRecipe.class;
	}

	@Override
	public String getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setIngredients(BakingRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BakingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);
		guiItemStacks.set(ingredients);
	}
}