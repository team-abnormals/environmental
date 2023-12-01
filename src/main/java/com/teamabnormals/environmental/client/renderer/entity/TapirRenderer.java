package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.environmental.client.model.TapirModel;
import com.teamabnormals.environmental.common.entity.animal.Tapir;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TapirRenderer extends MobRenderer<Tapir, TapirModel<Tapir>> {
	private static final ResourceLocation TAPIR = new ResourceLocation(Environmental.MOD_ID, "textures/entity/tapir/tapir.png");
	private static final ResourceLocation BABY_TAPIR = new ResourceLocation(Environmental.MOD_ID, "textures/entity/tapir/baby_tapir.png");

	public TapirRenderer(EntityRendererProvider.Context context) {
		super(context, new TapirModel<>(context.bakeLayer(EnvironmentalModelLayers.TAPIR)), 0.7F);
	}

	@Override
	protected void scale(Tapir tapir, PoseStack poseStack, float partialTicks) {
		super.scale(tapir, poseStack, partialTicks);
		poseStack.scale(0.99F, 0.99F, 0.99F);
	}

	@Override
	public ResourceLocation getTextureLocation(Tapir tapir) {
		return tapir.hasBabyPattern() || tapir.isBaby() ? BABY_TAPIR : TAPIR;
	}
}