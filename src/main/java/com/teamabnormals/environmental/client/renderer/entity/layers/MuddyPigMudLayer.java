package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.MuddyPig;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Pig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MuddyPigMudLayer<T extends Pig, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation MUDDY_PIG_LOCATION = Environmental.location("textures/entity/pig/muddy_pig.png");
	private static final ResourceLocation DRIED_MUDDY_PIG_LOCATION = Environmental.location("textures/entity/pig/dried_muddy_pig.png");

	public MuddyPigMudLayer(RenderLayerParent<T, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLightIn, T pig, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (MuddyPig.enabled() && MuddyPig.isMuddy(pig)) {
			VertexConsumer dried = buffer.getBuffer(RenderType.entityTranslucent(DRIED_MUDDY_PIG_LOCATION));
			this.getParentModel().setupAnim(pig, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.getParentModel().renderToBuffer(poseStack, dried, packedLightIn, LivingEntityRenderer.getOverlayCoords(pig, 0.0F));

			VertexConsumer builder = buffer.getBuffer(RenderType.entityTranslucent(MUDDY_PIG_LOCATION));
			this.getParentModel().setupAnim(pig, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			float f = Mth.clamp((MuddyPig.getDryingTime(pig) - 60.0F) / (500.0F), 0.0F, 1.0F);
			this.getParentModel().renderToBuffer(poseStack, builder, packedLightIn, LivingEntityRenderer.getOverlayCoords(pig, 0.0F), FastColor.ARGB32.colorFromFloat(f, 1.0F, 1.0F, 1.0F));
		}
	}
}
