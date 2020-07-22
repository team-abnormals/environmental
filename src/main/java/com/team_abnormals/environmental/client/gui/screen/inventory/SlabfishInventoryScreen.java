package com.team_abnormals.environmental.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlabfishInventoryScreen extends ContainerScreen<SlabfishInventoryContainer> {
    private static final ResourceLocation SLABFISH_GUI_TEXTURE = new ResourceLocation(Environmental.MODID, "textures/gui/container/slabfish.png");
    private final SlabfishEntity slabfish;
    private float mouseX;
    private float mouseY;

    public SlabfishInventoryScreen(SlabfishInventoryContainer screenContainer, PlayerInventory playerInventory, SlabfishEntity slabfish) {
        super(screenContainer, playerInventory, slabfish.getDisplayName());
        this.slabfish = slabfish;
        this.passEvents = false;
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(SLABFISH_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);

        if (this.slabfish.hasBackpack()) {
            this.blit(p_230450_1_, i + 79, j + 17, 0, this.ySize, 5 * 18, 54);
        }

        InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 32, (float) (i + 51) - this.mouseX, (float) (j + 75 - 50) - this.mouseY, this.slabfish);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(stack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(stack, mouseX, mouseY);
    }
}
