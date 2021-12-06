package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.abnormals_core.client.ACRenderTypes;
import com.minecraftabnormals.environmental.common.entity.DeerEntity;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
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
public class RedNoseReindeerLayer<E extends DeerEntity, M extends EntityModel<E>> extends LayerRenderer<E, M> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/holiday_overlay.png");

	public RedNoseReindeerLayer(IEntityRenderer<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isHoliday()) return;
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.eyes(TEXTURE));
		this.getParentModel().setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getParentModel().renderToBuffer(matrixStackIn, builder, packedLightIn, LivingRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
