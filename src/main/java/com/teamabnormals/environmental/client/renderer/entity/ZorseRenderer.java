package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.ZorseModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseArmorLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseMarkingsLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.ZorseStripesRenderLayer;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZorseRenderer extends MobRenderer<Zorse, ZorseModel<Zorse>> {

	public ZorseRenderer(EntityRendererProvider.Context context) {
		super(context, new ZorseModel<>(context.bakeLayer(EnvironmentalModelLayers.ZORSE)), 0.75F);
		this.addLayer(new ZorseStripesRenderLayer(this));
		this.addLayer(new ZorseMarkingsLayer(this));
		this.addLayer(new ZorseArmorLayer(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(Zorse zorse) {
		return HorseRenderer.LOCATION_BY_VARIANT.get(zorse.getVariant());
	}
}