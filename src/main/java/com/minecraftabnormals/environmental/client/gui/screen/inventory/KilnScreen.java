package com.minecraftabnormals.environmental.client.gui.screen.inventory;

import com.minecraftabnormals.environmental.common.inventory.container.KilnContainer;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class KilnScreen extends AbstractKilnScreen<KilnContainer> {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Environmental.MODID, "textures/gui/container/kiln.png");

	public KilnScreen(KilnContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, GUI_TEXTURE);
	}
}
