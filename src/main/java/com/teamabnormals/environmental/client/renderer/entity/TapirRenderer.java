package com.teamabnormals.environmental.client.renderer.entity;

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
	private static final ResourceLocation TAPIR_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/entity/tapir.png");

	public TapirRenderer(EntityRendererProvider.Context context) {
		super(context, new TapirModel<>(context.bakeLayer(EnvironmentalModelLayers.TAPIR)), 0.7F);
	}

	public ResourceLocation getTextureLocation(Tapir tapir) {
		return TAPIR_LOCATION;
	}
}