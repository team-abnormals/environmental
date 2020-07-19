package com.team_abnormals.environmental.client.render;

import com.team_abnormals.environmental.client.model.DuckModel;
import com.team_abnormals.environmental.common.entity.DuckEntity;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<DuckEntity, DuckModel<DuckEntity>> {

	public DuckRenderer(EntityRendererManager manager) {
		super(manager, new DuckModel<>(), 0.45F);
	}
	
	@Override
	public ResourceLocation getEntityTexture(DuckEntity entity) {
		return new ResourceLocation(Environmental.MODID, "textures/entity/duck.png");
	}
	
	@Override
	protected float handleRotationFloat(DuckEntity livingBase, float partialTicks) {
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
}
