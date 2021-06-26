package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.environmental.client.model.SlabfishModel;
import com.minecraftabnormals.environmental.client.render.SlabfishSpriteUploader;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.BackpackType;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
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
public class BackpackRenderLayer<E extends SlabfishEntity, M extends SlabfishModel<E>> extends LayerRenderer<E, M> {

	public BackpackRenderLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn, E slabby, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!slabby.hasBackpack()) return;

		BackpackType backpackType = SlabfishManager.get(slabby.getCommandSenderWorld()).getBackpackType(slabby.getBackpack()).orElse(SlabfishManager.BROWN_BACKPACK);
		IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutoutNoCull(SlabfishSpriteUploader.ATLAS_LOCATION));
		this.getParentModel().sprite = SlabfishSpriteUploader.instance().getSprite(backpackType.getTextureLocation());
		this.getParentModel().setupAnim(slabby, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getParentModel().renderToBuffer(matrixStack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
