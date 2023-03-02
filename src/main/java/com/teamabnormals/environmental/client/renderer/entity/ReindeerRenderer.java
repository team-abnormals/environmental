package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.DeerModel;
import com.teamabnormals.environmental.client.model.ReindeerModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.HolidayReindeerLayer;
import com.teamabnormals.environmental.common.entity.animal.deer.Reindeer;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReindeerRenderer extends MobRenderer<Reindeer, DeerModel<Reindeer>> {

	public ReindeerRenderer(EntityRendererProvider.Context context) {
		super(context, new ReindeerModel(context.bakeLayer(EnvironmentalModelLayers.REINDEER)), 0.6F);
		this.addLayer(new HolidayReindeerLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Reindeer entity) {
		if (entity.isHoliday())
			return new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/holiday.png");
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/reindeer.png");
	}
}
