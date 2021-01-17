package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.environmental.client.model.SlabfishModel;
import com.minecraftabnormals.environmental.client.render.SlabfishSpriteUploader;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BackpackOverlayRenderLayer<E extends SlabfishEntity, M extends SlabfishModel<E>> extends LayerRenderer<E, M> {

	public BackpackOverlayRenderLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn, E slabfish, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (slabfish.getSlabfishOverlay() == SlabfishOverlay.NONE || slabfish.getSlabfishOverlay() == SlabfishOverlay.EGG || !slabfish.hasBackpack())
			return;

		IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityCutoutNoCull(SlabfishSpriteUploader.ATLAS_LOCATION));
		this.getEntityModel().sprite = SlabfishSpriteUploader.instance().getSprite(slabfish.getSlabfishOverlay().getBackpackTextureLocation());
		this.getEntityModel().setRotationAngles(slabfish, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getEntityModel().render(matrixStack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
