package com.team_abnormals.environmental.integration.jei;

import com.team_abnormals.environmental.common.item.crafting.BakingRecipe;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.plugins.vanilla.cooking.AbstractCookingCategory;
import net.minecraft.util.ResourceLocation;

public class BakingCategory extends AbstractCookingCategory<BakingRecipe> {
	public static final ResourceLocation BAKING = new ResourceLocation(Environmental.MODID, "baking");
	
	public BakingCategory(IGuiHelper guiHelper) {
		super(guiHelper, EnvironmentalBlocks.KILN.get(), "gui.jei.category.baking", 100);
	}

	@Override
	public ResourceLocation getUid() {
		return BAKING;
	}

	@Override
	public Class<? extends BakingRecipe> getRecipeClass() {
		return BakingRecipe.class;
	}
}