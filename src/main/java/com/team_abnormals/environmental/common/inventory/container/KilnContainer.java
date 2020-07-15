package com.team_abnormals.environmental.common.inventory.container;

import com.team_abnormals.environmental.core.registry.EnvironmentalContainerTypes;
import com.team_abnormals.environmental.core.registry.EnvironmentalRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.IIntArray;

public class KilnContainer extends AbstractFurnaceContainer {
    public KilnContainer(int p_i50061_1_, PlayerInventory p_i50061_2_) {
        super(EnvironmentalContainerTypes.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, p_i50061_1_, p_i50061_2_);
    }

    public KilnContainer(int p_i50062_1_, PlayerInventory p_i50062_2_, IInventory p_i50062_3_, IIntArray p_i50062_4_) {
        super(EnvironmentalContainerTypes.KILN.get(), EnvironmentalRecipes.RecipeTypes.BAKING, p_i50062_1_, p_i50062_2_, p_i50062_3_, p_i50062_4_);
    }
}