package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.SlabfishModel;
import com.minecraftabnormals.environmental.client.render.layer.BackpackOverlayRenderLayer;
import com.minecraftabnormals.environmental.client.render.layer.BackpackRenderLayer;
import com.minecraftabnormals.environmental.client.render.layer.OverlayRenderLayer;
import com.minecraftabnormals.environmental.client.render.layer.SweaterRenderLayer;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		return SlabfishSpriteUploader.ATLAS_LOCATION;
	}

	@Override
	protected RenderType func_230496_a_(SlabfishEntity slabby, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation texture = this.getEntityTexture(slabby);
		if (p_230496_3_) {
			return RenderType.getItemEntityTranslucentCull(texture);
		} else if (p_230496_2_) {
			return SlabfishManager.get(slabby.world).getSlabfishType(slabby.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).isTranslucent() ? RenderType.getEntityTranslucent(texture) : this.entityModel.getRenderType(texture);
		} else {
			return p_230496_4_ ? RenderType.getOutline(texture) : null;
		}
	}

	protected float handleRotationFloat(SlabfishEntity livingBase, float partialTicks) {
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@Override
	protected void preRenderCallback(SlabfishEntity slabfish, MatrixStack matrixStack, float partialTickTime) {
		this.entityModel.sprite = SlabfishSpriteUploader.instance().getSprite(SlabfishManager.get(slabfish.world).getSlabfishType(slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).getTextureLocation());
		if (slabfish.isEntitySleeping() || slabfish.getRidingEntity() != null) matrixStack.translate(0F, 0.3125F, 0F);
		if (slabfish.isInWater()) {
			matrixStack.translate(0F, slabfish.isChild() ? -0.8F : -0.4F, 0.5F);
			matrixStack.rotate(Vector3f.XP.rotation((float) (Math.PI / 2)));
		}
	}
}
