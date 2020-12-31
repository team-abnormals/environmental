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
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/koi.png");
	}

	@Override
	public void applyRotations(KoiEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, entityLiving.prevRotationPitch, entityLiving.rotationPitch)));
		float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
		if (!entityLiving.isInWater()) {
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
			matrixStackIn.translate((double) 0.1F, (double) 0.1F, (double) -0.1F);
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
}
