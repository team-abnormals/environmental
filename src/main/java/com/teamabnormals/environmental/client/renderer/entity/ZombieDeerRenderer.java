package com.teamabnormals.environmental.client.renderer.entity;

import com.teamabnormals.environmental.client.model.DeerModel;
import com.teamabnormals.environmental.common.entity.animal.deer.ZombieDeer;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieDeerRenderer extends MobRenderer<ZombieDeer, DeerModel<ZombieDeer>> {

	public ZombieDeerRenderer(EntityRendererProvider.Context context) {
		super(context, new DeerModel<>(context.bakeLayer(EnvironmentalModelLayers.DEER)), 0.6F);
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieDeer entity) {
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/zombie.png");
	}
}
