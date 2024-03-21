package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.ZebraModel;
import com.teamabnormals.environmental.common.entity.animal.Zebra;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZebraRenderer extends MobRenderer<Zebra, ZebraModel<Zebra>> {
	private static final ResourceLocation ZEBRA_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/entity/zebra/zebra.png");

	public ZebraRenderer(EntityRendererProvider.Context context) {
		super(context, new ZebraModel<>(context.bakeLayer(EnvironmentalModelLayers.ZEBRA)), 0.75F);
	}

	@Override
	public ResourceLocation getTextureLocation(Zebra p_114482_) {
		return ZEBRA_LOCATION;
	}
}