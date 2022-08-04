package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.environmental.client.model.DuckModel;
import com.teamabnormals.environmental.common.entity.animal.Duck;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<Duck, DuckModel<Duck>> {

	public DuckRenderer(EntityRendererProvider.Context context) {
		super(context, new DuckModel<>(context.bakeLayer(EnvironmentalModelLayers.DUCK)), 0.45F);
	}

	@Override
	public ResourceLocation getTextureLocation(Duck entity) {
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/duck.png");
	}

	@Override
	protected void setupRotations(Duck entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		if (entityLiving.isInWater())
			matrixStackIn.translate(0.0D, Mth.cos(ageInTicks * 0.08F) * 0.02F, 0.0D);

		super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}
}