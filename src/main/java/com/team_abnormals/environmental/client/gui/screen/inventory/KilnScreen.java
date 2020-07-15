package com.team_abnormals.environmental.client.gui.screen.inventory;

import com.team_abnormals.environmental.client.gui.recipebook.KilnRecipeGui;
import com.team_abnormals.environmental.common.inventory.container.KilnContainer;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class KilnScreen extends AbstractFurnaceScreen<KilnContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Environmental.MODID, "textures/gui/container/kiln.png");

    public KilnScreen(KilnContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, new KilnRecipeGui(), inv, titleIn, GUI_TEXTURE);
    }
}