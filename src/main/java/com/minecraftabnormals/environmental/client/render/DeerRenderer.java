package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.DeerModel;
import com.minecraftabnormals.environmental.client.render.layer.DeerMarkingsRenderLayer;
import com.minecraftabnormals.environmental.common.entity.DeerEntity;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel<DeerEntity>> {

    public DeerRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DeerModel<>(), 0.75F);
        this.addLayer(new DeerMarkingsRenderLayer<>(this));
    }
    
    @Override
    public ResourceLocation getEntityTexture(DeerEntity entity) {
        DeerCoatColors coatType = DeerCoatColors.byId(entity.getCoatColor());
        return new ResourceLocation(Environmental.MODID, "textures/entity/deer/deer_" + coatType.name().toLowerCase() + ".png");
    }
    
    @Override
    protected void preRenderCallback(DeerEntity entity, MatrixStack matrixStack, float partialTickTime) {
        if (entity.isChild()) matrixStack.scale(0.5F, 0.5F, 0.5F);
    }
}
