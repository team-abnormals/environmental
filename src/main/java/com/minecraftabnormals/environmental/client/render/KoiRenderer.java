package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.KoiModel;
import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<KoiEntity, KoiModel<KoiEntity>> {

	public KoiRenderer(EntityRendererManager manager) {
		super(manager, new KoiModel<KoiEntity>(), 0.3F);
	}

	@Override
	public ResourceLocation getEntityTexture(KoiEntity entity) {
		return new ResourceLocation(Environmental.MODID, "textures/entity/koi.png");
	}

	@Override
	public void applyRotations(KoiEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
		if (!entityLiving.isInWater()) {
			matrixStackIn.translate((double) 0.1F, (double) 0.1F, (double) -0.1F);
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
}
