package com.team_abnormals.environmental.integration.jei;

import com.team_abnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.team_abnormals.environmental.common.inventory.container.KilnContainer;
import com.team_abnormals.environmental.common.item.crafting.BakingRecipe;
import com.team_abnormals.environmental.common.item.crafting.SawingRecipe;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;
import com.team_abnormals.environmental.core.registry.EnvironmentalRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class EnvironmentalPlugin implements IModPlugin {

    public static final ResourceLocation RECIPE_GUI_ENVIRONMENTAL = new ResourceLocation(Environmental.MODID, "textures/gui/jei.png");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Environmental.MODID, Environmental.MODID);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(EnvironmentalItems.SLABFISH_BUCKET.get(), stack -> stack.getTag() != null && stack.getTag().contains("SlabfishType", Constants.NBT.TAG_STRING) ? stack.getTag().getString("SlabfishType") : ISubtypeInterpreter.NONE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(new BakingCategory(guiHelper));
        registration.addRecipeCategories(new SawingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Results recipes = getAllRecipes();

        registration.addRecipes(recipes.getBakingRecipes(), BakingCategory.BAKING);
        registration.addRecipes(recipes.getSawingRecipes(), SawingCategory.SAWING);
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
        registration.addRecipeCatalyst(new ItemStack(EnvironmentalBlocks.SAWMILL.get()), SawingCategory.SAWING);
    }

    public static class Results {
        private final List<BakingRecipe> bakingRecipes = new ArrayList<>();
        private final List<SawingRecipe> sawingRecipes = new ArrayList<>();

        public List<BakingRecipe> getBakingRecipes() {
            return bakingRecipes;
        }

        public List<SawingRecipe> getSawingRecipes() {
            return sawingRecipes;
        }
    }

    public static Results getAllRecipes() {

        Results results = new Results();
        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipeManager = world.getRecipeManager();

        results.bakingRecipes.addAll(getRecipes(recipeManager, EnvironmentalRecipes.RecipeTypes.BAKING));
        results.sawingRecipes.addAll(getRecipes(recipeManager, EnvironmentalRecipes.RecipeTypes.SAWING));

        return results;
    }

    @SuppressWarnings("unchecked")
    private static <C extends IInventory, T extends IRecipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, IRecipeType<T> recipeType) {
        Map<ResourceLocation, IRecipe<C>> recipesMap = recipeManager.getRecipes(recipeType);
        return (Collection<T>) recipesMap.values();
    }

}