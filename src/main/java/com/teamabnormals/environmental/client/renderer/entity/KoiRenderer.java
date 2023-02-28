package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.KoiModel;
import com.teamabnormals.environmental.common.entity.animal.Koi;
import com.teamabnormals.environmental.core.Environmental;
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
	public ResourceLocation getTextureLocation(Koi entity) {
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/koi.png");
	}

	@Override
	public void setupRotations(Koi entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (entityLiving.isInWater()) {
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(partialTicks, entityLiving.xRotO, entityLiving.getXRot())));
		} else {
			matrixStackIn.translate(0.2F, 0.1F, 0.0D);
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
}
