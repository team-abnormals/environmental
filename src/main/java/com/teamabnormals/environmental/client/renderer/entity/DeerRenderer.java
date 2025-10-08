package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.DeerModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.DeerSpotsLayer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<Deer, DeerModel<Deer>> {

	public DeerRenderer(EntityRendererProvider.Context context) {
		super(context, new DeerModel<>(context.bakeLayer(EnvironmentalModelLayers.DEER)), 0.6F);
		this.addLayer(new DeerSpotsLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Deer entity) {
		return entity.getVariant().value().texture().withPrefix("textures/").withSuffix(".png");
	}
}
