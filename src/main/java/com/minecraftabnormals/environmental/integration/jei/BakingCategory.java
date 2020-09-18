package com.minecraftabnormals.environmental.integration.jei;

import com.minecraftabnormals.environmental.common.item.crafting.BakingRecipe;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
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
    public static final ResourceLocation BAKING = new ResourceLocation(Environmental.MODID, "baking");
    private final IDrawableAnimated animatedFlame;
    private final IDrawableAnimated arrow;

    private static final int inputSlot = 0;
    private static final int outputSlot = 2;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public BakingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 0, 114, 82, 54);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(EnvironmentalBlocks.KILN.get()));
        this.localizedName = I18n.format("gui." + Environmental.MODID + ".category.baking");
        IDrawableStatic staticFlame = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 82, 114, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        this.arrow = guiHelper.drawableBuilder(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 82, 128, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(BakingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        this.animatedFlame.draw(matrixStack, 1, 20);
        this.arrow.draw(matrixStack, 24, 18);
        float experience = recipe.getExperience();
        if (experience > 0.0F) {
            TranslationTextComponent experienceString = new TranslationTextComponent("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.func_238414_a_(experienceString);
            fontRenderer.func_238422_b_(matrixStack, experienceString, (float) (this.background.getWidth() - stringWidth), 0.0F, -8355712);
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
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BakingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(inputSlot, true, 0, 0);
        guiItemStacks.init(outputSlot, false, 60, 18);
        guiItemStacks.set(ingredients);
    }
}