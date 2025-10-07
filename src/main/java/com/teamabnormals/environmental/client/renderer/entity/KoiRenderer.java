package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamabnormals.environmental.client.model.KoiModel;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<Koi, KoiModel<Koi>> {

	public KoiRenderer(EntityRendererProvider.Context context) {
		super(context, new KoiModel<>(context.bakeLayer(EnvironmentalModelLayers.KOI)), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(Koi koi) {
		return koi.getVariant().value().assetId().withPrefix("textures/").withSuffix(".png");
	}

	@Override
	public void setupRotations(Koi koi, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
		super.setupRotations(koi, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
		if (koi.isInWater()) {
			poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, koi.xRotO, koi.getXRot())));
		} else {
			poseStack.translate(0.2F, 0.1F, 0.0D);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}
}
