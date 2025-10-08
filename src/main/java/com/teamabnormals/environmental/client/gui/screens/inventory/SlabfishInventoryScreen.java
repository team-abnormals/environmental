package com.teamabnormals.environmental.client.gui.screens.inventory;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlabfishInventoryScreen extends AbstractContainerScreen<SlabfishInventoryMenu> {
	private static final ResourceLocation SLABFISH_GUI_TEXTURE = Environmental.location("textures/gui/container/slabfish.png");
	private final Slabfish slabfish;
	private float xMouse;
	private float yMouse;

	public SlabfishInventoryScreen(SlabfishInventoryMenu screenContainer, Inventory playerInventory, Slabfish slabfish) {
		super(screenContainer, playerInventory, slabfish.getDisplayName());
		this.slabfish = slabfish;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		guiGraphics.blit(SLABFISH_GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);

		if (this.slabfish.hasBackpack()) {
			guiGraphics.blit(SLABFISH_GUI_TEXTURE, i + 79, j + 17, 0, this.imageHeight, 5 * 18, 54);

			Holder<SlabfishVariant> slabfishVariant = this.slabfish.getVariant();
			if (slabfishVariant.value().backpackOverride().isEmpty())
				guiGraphics.blit(SLABFISH_GUI_TEXTURE, i + 7, j + 53, 0, 220, 18, 18);
		}

		InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 26, j + 18, i + 78, j + 70, 32, 0.125F, this.xMouse, this.yMouse, this.slabfish);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.xMouse = (float) mouseX;
		this.yMouse = (float) mouseY;
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}
}
