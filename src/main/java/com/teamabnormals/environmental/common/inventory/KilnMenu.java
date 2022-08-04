package com.teamabnormals.environmental.common.inventory;

import com.teamabnormals.environmental.core.registry.EnvironmentalMenuTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRecipes.EnvironmentalRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;

public class KilnMenu extends AbstractFurnaceMenu {
	public KilnMenu(int windowId, Inventory playerInventory) {
		super(EnvironmentalMenuTypes.KILN.get(), EnvironmentalRecipeTypes.BAKING.get(), RecipeBookType.FURNACE, windowId, playerInventory);
	}

	public KilnMenu(int windowId, Inventory playerInventory, Container kilnInventory, ContainerData furnaceData) {
		super(EnvironmentalMenuTypes.KILN.get(), EnvironmentalRecipeTypes.BAKING.get(), RecipeBookType.FURNACE, windowId, playerInventory, kilnInventory, furnaceData);
	}
}