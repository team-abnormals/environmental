package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.environmental.common.entity.DeerEntity;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatTypes;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerMarkingsRenderLayer<E extends DeerEntity, M extends EntityModel<E>> extends LayerRenderer<E, M> {

    public DeerMarkingsRenderLayer(IEntityRenderer<E, M> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getCoatType() == 0) return;
        
        DeerCoatTypes coatType = DeerCoatTypes.byId(entity.getCoatType());
        ResourceLocation texture = new ResourceLocation(Environmental.MODID, "textures/entity/deer/deer_markings_" + coatType.name().toLowerCase() + ".png");

        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(texture));
        this.getEntityModel().setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
