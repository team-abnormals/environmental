package com.minecraftabnormals.environmental.client.gui.screen.inventory;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlabfishInventoryScreen extends ContainerScreen<SlabfishInventoryContainer> {

	private static final ResourceLocation SLABFISH_GUI_TEXTURE = new ResourceLocation(Environmental.MOD_ID, "textures/gui/container/slabfish.png");
	private final SlabfishManager slabfishManager;
	private final SlabfishEntity slabfish;

	public SlabfishInventoryScreen(SlabfishInventoryContainer screenContainer, PlayerInventory playerInventory, SlabfishEntity slabfish) {
		super(screenContainer, playerInventory, slabfish.getDisplayName());
		this.slabfishManager = SlabfishManager.get(slabfish.getCommandSenderWorld());
		this.slabfish = slabfish;
		this.passEvents = false;
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.getMinecraft().getTextureManager().bind(SLABFISH_GUI_TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		if (this.slabfish.hasBackpack()) {
			this.blit(matrixStack, i + 79, j + 17, 0, this.imageHeight, 5 * 18, 54);

			SlabfishType slabfishType = this.slabfishManager.getSlabfishType(this.slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
			if (slabfishType.getCustomBackpack() == null || !this.slabfishManager.getBackpackType(slabfishType.getCustomBackpack()).isPresent())
				this.blit(matrixStack, i + 7, j + 53, 0, 220, 18, 18);
		}

		InventoryScreen.renderEntityInInventory(i + 51, j + 60, 32, i + 51 - mouseX, j + 25 - mouseY, this.slabfish);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
}
