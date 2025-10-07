package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.client.model.ZonkeyModel;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zonkey;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZonkeyStripesRenderLayer extends RenderLayer<Zonkey, ZonkeyModel<Zonkey>> {
	private static final ResourceLocation STRIPES_LOCATION = Environmental.location("textures/entity/zebroid/zonkey_overlay.png");

	public ZonkeyStripesRenderLayer(RenderLayerParent<Zonkey, ZonkeyModel<Zonkey>> entityRenderer) {
		super(entityRenderer);
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Zonkey zonkey, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!zonkey.isInvisible()) {
			VertexConsumer builder = buffer.getBuffer(RenderType.entityTranslucent(STRIPES_LOCATION));
			float f = Mth.clamp(zonkey.getStripeOpacity() / 100.0F, 0.0F, 1.0F);
			this.getParentModel().renderToBuffer(poseStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(zonkey, 0.0F), FastColor.ARGB32.colorFromFloat(f, 1.0F, 1.0F, 1.0F));
		}
	}
}