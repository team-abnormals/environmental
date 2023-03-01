package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.KoiModel;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<Koi, KoiModel<Koi>> {

	public KoiRenderer(EntityRendererProvider.Context context) {
		super(context, new KoiModel<>(context.bakeLayer(EnvironmentalModelLayers.KOI)), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(Koi koi) {
		return KoiBreed.byId(koi.getVariant()).getTextureLocation();
	}

	@Override
	public void setupRotations(Koi koi, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(koi, poseStack, ageInTicks, rotationYaw, partialTicks);
		if (koi.isInWater()) {
			poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(partialTicks, koi.xRotO, koi.getXRot())));
		} else {
			poseStack.translate(0.2F, 0.1F, 0.0D);
			poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
}
