package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.DeerModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.DeerMarkingsRenderLayer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.common.entity.animal.deer.DeerCoatColors;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<Deer, DeerModel<Deer>> {

	public DeerRenderer(EntityRendererProvider.Context context) {
		super(context, new DeerModel<>(context.bakeLayer(EnvironmentalModelLayers.DEER)), 0.6F);
		this.addLayer(new DeerMarkingsRenderLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Deer entity) {
		DeerCoatColors coatType = DeerCoatColors.byId(entity.getCoatColor());
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/deer_" + coatType.name().toLowerCase(Locale.ROOT) + ".png");
	}
}
