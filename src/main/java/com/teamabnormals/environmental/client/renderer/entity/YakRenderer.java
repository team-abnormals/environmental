package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.YakModel;
import com.teamabnormals.environmental.common.entity.animal.Yak;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YakRenderer extends MobRenderer<Yak, YakModel<Yak>> {

	public YakRenderer(EntityRendererProvider.Context context) {
		super(context, new YakModel<>(context.bakeLayer(EnvironmentalModelLayers.YAK)), 0.8F);
	}

	@Override
	public ResourceLocation getTextureLocation(Yak entity) {
		if (entity.getSheared())
			return new ResourceLocation(Environmental.MOD_ID, "textures/entity/yak/yak_sheared.png");
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/yak/yak.png");
	}
}