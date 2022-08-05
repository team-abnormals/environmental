package com.teamabnormals.environmental.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlabfishInventoryScreen extends AbstractContainerScreen<SlabfishInventoryMenu> {
    private static final ResourceLocation SLABFISH_GUI_TEXTURE = new ResourceLocation(Environmental.MOD_ID, "textures/gui/container/slabfish.png");
    private final SlabfishManager slabfishManager;
    private final Slabfish slabfish;

    public SlabfishInventoryScreen(SlabfishInventoryMenu screenContainer, Inventory playerInventory, Slabfish slabfish) {
        super(screenContainer, playerInventory, slabfish.getDisplayName());
        this.slabfishManager = SlabfishManager.get(slabfish.getCommandSenderWorld());
        this.slabfish = slabfish;
        this.passEvents = false;
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SLABFISH_GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.slabfish.hasBackpack()) {
            this.blit(matrixStack, i + 79, j + 17, 0, this.imageHeight, 5 * 18, 54);

            SlabfishType slabfishType = EnvironmentalSlabfishTypes.registryAccess().getOptional(this.slabfish.getSlabfishType()).orElse(EnvironmentalSlabfishTypes.SWAMP.get());
            if (slabfishType.getBackpack() == null || !this.slabfishManager.getBackpackType(slabfishType.getBackpack()).isPresent())
                this.blit(matrixStack, i + 7, j + 53, 0, 220, 18, 18);
        }

        InventoryScreen.renderEntityInInventory(i + 51, j + 60, 32, i + 51 - mouseX, j + 25 - mouseY, this.slabfish);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}
