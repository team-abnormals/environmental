package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.DuckModel;
import com.minecraftabnormals.environmental.common.entity.DuckEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<DuckEntity, DuckModel<DuckEntity>> {

	public DuckRenderer(EntityRendererManager manager) {
		super(manager, new DuckModel<>(), 0.45F);
	}

	@Override
	public ResourceLocation getEntityTexture(DuckEntity entity) {
		return new ResourceLocation(Environmental.MODID, "textures/entity/duck.png");
	}

	@Override
	protected void applyRotations(DuckEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		if (entityLiving.isInWater())
			matrixStackIn.translate(0.0D, (double)(MathHelper.cos(ageInTicks * 0.08F) * 0.02F), 0.0D);
		
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}
}
