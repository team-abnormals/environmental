package com.team_abnormals.environmental.integration.jei;

import com.team_abnormals.environmental.common.item.crafting.BakingRecipe;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BakingCategory implements IRecipeCategory<BakingRecipe> {
    public static final ResourceLocation BAKING = new ResourceLocation(Environmental.MODID, "baking");
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    protected final IDrawableAnimated arrow;

    protected static final int inputSlot = 0;
    protected static final int outputSlot = 2;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public BakingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 0, 114, 82, 54);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(EnvironmentalBlocks.KILN.get()));
        this.localizedName = I18n.format("gui." + Environmental.MODID + ".category.baking");
        this.staticFlame = guiHelper.createDrawable(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 82, 114, 14, 14);
        this.animatedFlame = guiHelper.createAnimatedDrawable(this.staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        this.arrow = guiHelper.drawableBuilder(EnvironmentalPlugin.RECIPE_GUI_ENVIRONMENTAL, 82, 128, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
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