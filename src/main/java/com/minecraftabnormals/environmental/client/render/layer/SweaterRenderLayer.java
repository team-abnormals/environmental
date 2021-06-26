package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.environmental.client.model.SlabfishModel;
import com.minecraftabnormals.environmental.client.render.SlabfishSpriteUploader;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SweaterType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SweaterRenderLayer<E extends SlabfishEntity, M extends SlabfishModel<E>> extends LayerRenderer<E, M> {

	public SweaterRenderLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn, E slabby, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!slabby.hasSweater()) return;

		SweaterType sweaterType = SlabfishManager.get(slabby.getCommandSenderWorld()).getSweaterType(slabby.getSweater()).orElse(SlabfishManager.EMPTY_SWEATER);
		if (sweaterType == SlabfishManager.EMPTY_SWEATER)
			return;

		IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutoutNoCull(SlabfishSpriteUploader.ATLAS_LOCATION));
		this.getParentModel().sprite = SlabfishSpriteUploader.instance().getSprite(sweaterType.getTextureLocation());
		this.getParentModel().setupAnim(slabby, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getParentModel().renderToBuffer(matrixStack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

}
