package com.team_abnormals.environmental.common.inventory.container;

import com.team_abnormals.environmental.core.registry.EnvironmentalContainerTypes;
import com.team_abnormals.environmental.core.registry.EnvironmentalRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.IIntArray;

public class KilnContainer extends AbstractFurnaceContainer {
    public KilnContainer(int windowId, PlayerInventory playerInventory) {
        super(EnvironmentalContainerTypes.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, windowId, playerInventory);
    }

    public KilnContainer(int windowId, PlayerInventory playerInventory, IInventory kilnInventory, IIntArray furnaceData) {
        super(EnvironmentalContainerTypes.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, windowId, playerInventory, kilnInventory, furnaceData);
    }
}