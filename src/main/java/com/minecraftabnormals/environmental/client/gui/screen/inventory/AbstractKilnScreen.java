package com.minecraftabnormals.environmental.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractKilnScreen<T extends AbstractFurnaceContainer> extends ContainerScreen<T> {
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");

    private final ResourceLocation guiTexture;

    public AbstractKilnScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, ResourceLocation guiTexture) {
        super(screenContainer, inv, titleIn);
        this.guiTexture = guiTexture;
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new ImageButton(this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, BUTTON_TEXTURE, (p_214087_1_) -> {
            ((ImageButton) p_214087_1_).setPosition(this.guiLeft + 20, this.height / 2 - 49);
        }));
        this.titleX = (this.xSize - this.font.func_238414_a_(this.title)) / 2;
    }

    public void tick() {
        super.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.guiTexture);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        if (this.container.isBurning()) {
            int k = this.container.getBurnLeftScaled();
            this.blit(matrixStack, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.container.getCookProgressionScaled();
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }
}