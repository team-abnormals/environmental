package com.team_abnormals.environmental.integration.jei;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.team_abnormals.environmental.common.inventory.container.KilnContainer;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.util.ErrorUtil;
import net.minecraft.item.ItemStack;
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

		EnvironmentalRecipeValidator.Results recipes = EnvironmentalRecipeValidator.getValidRecipes(bakingCategory);
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

}