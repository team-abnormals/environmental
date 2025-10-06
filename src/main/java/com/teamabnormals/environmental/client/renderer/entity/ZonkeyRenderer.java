package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.environmental.client.model.ZonkeyModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZonkeyStripesRenderLayer;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zonkey;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZonkeyRenderer extends MobRenderer<Zonkey, ZonkeyModel<Zonkey>> {
	private static final ResourceLocation DONKEY_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/horse/donkey.png");

	public ZonkeyRenderer(EntityRendererProvider.Context context) {
		super(context, new ZonkeyModel<>(context.bakeLayer(EnvironmentalModelLayers.ZONKEY)), 0.75F);
		this.addLayer(new ZonkeyStripesRenderLayer(this));
	}

	@Override
	protected void scale(Zonkey zonkey, PoseStack poseStack, float partialTick) {
		poseStack.scale(0.87F, 0.87F, 0.87F);
		super.scale(zonkey, poseStack, partialTick);
	}

	@Override
	public ResourceLocation getTextureLocation(Zonkey zonkey) {
		return DONKEY_LOCATION;
	}
}