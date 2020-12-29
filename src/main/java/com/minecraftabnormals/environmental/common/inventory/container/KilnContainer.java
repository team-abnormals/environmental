package com.minecraftabnormals.environmental.common.inventory.container;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalContainers;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class KilnContainer extends AbstractFurnaceContainer {
	public KilnContainer(int windowId, PlayerInventory playerInventory) {
		super(EnvironmentalContainers.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, RecipeBookCategory.FURNACE, windowId, playerInventory);
	}

	public KilnContainer(int windowId, PlayerInventory playerInventory, IInventory kilnInventory, IIntArray furnaceData) {
		super(EnvironmentalContainers.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, RecipeBookCategory.FURNACE, windowId, playerInventory, kilnInventory, furnaceData);
	}
}