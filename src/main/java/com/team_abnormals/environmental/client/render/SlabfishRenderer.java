package com.team_abnormals.environmental.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.team_abnormals.environmental.client.model.SlabfishModel;
import com.team_abnormals.environmental.client.render.layer.BackpackOverlayRenderLayer;
import com.team_abnormals.environmental.client.render.layer.BackpackRenderLayer;
import com.team_abnormals.environmental.client.render.layer.OverlayRenderLayer;
import com.team_abnormals.environmental.client.render.layer.SweaterRenderLayer;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.entity.util.SlabfishType;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class SlabfishRenderer extends MobRenderer<SlabfishEntity, SlabfishModel<SlabfishEntity>> {	
	public SlabfishRenderer(EntityRendererManager renderManager) {
		super(renderManager, new SlabfishModel<>(), 0.3F);
		this.addLayer(new SweaterRenderLayer<>(this));
		this.addLayer(new BackpackRenderLayer<>(this));
		this.addLayer(new OverlayRenderLayer<>(this));
		this.addLayer(new BackpackOverlayRenderLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(SlabfishEntity slabby) {
		return new ResourceLocation(Environmental.MODID, "textures/entity/slabfish/slabfish_" + slabby.getSlabfishType().getString() + ".png");
	}
	
	@Override
	protected RenderType func_230496_a_(SlabfishEntity slabfish, boolean p_230042_2_, boolean p_230042_3_, boolean h) {
		if (slabfish.getSlabfishType() == SlabfishType.GHOST) {
			return RenderType.getEntityTranslucent(this.getEntityTexture(slabfish));
		} else {
			return super.func_230496_a_(slabfish, p_230042_2_, p_230042_3_, h);
		}
	}
	
	protected float handleRotationFloat(SlabfishEntity livingBase, float partialTicks) {
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
	
	@Override
	protected void preRenderCallback(SlabfishEntity slabfish, MatrixStack matrixStack, float partialTickTime) {
	    matrixStack.scale(1.0F, 1.0F, 1.0F);
	    if(slabfish.isChild()) matrixStack.scale(0.5F, 0.5F, 0.5F);
	    if(slabfish.func_233684_eK_() || slabfish.getRidingEntity() != null) matrixStack.translate(0F, 0.3125F, 0F);
	    if(slabfish.isInWater()) {
	    	matrixStack.translate(0F, slabfish.isChild() ? -0.8F : -0.4F, 0.5F);
	    	matrixStack.rotate(new Quaternion(90F, 0, 0, true));
	    }
	}
}
