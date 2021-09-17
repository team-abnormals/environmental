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
	public ResourceLocation getTextureLocation(SlabfishEntity slabby) {
		return SlabfishSpriteUploader.ATLAS_LOCATION;
	}

	@Override
	protected RenderType getRenderType(SlabfishEntity slabby, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation texture = this.getTextureLocation(slabby);
		if (p_230496_3_) {
			return RenderType.itemEntityTranslucentCull(texture);
		} else if (p_230496_2_) {
			return SlabfishManager.get(slabby.level).getSlabfishType(slabby.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).isTranslucent() ? RenderType.entityTranslucent(texture) : this.model.renderType(texture);
		} else {
			return p_230496_4_ ? RenderType.outline(texture) : null;
		}
	}

	protected float getBob(SlabfishEntity livingBase, float partialTicks) {
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@Override
	protected void scale(SlabfishEntity slabfish, MatrixStack matrixStack, float partialTickTime) {
		this.model.sprite = SlabfishSpriteUploader.instance().getSprite(SlabfishManager.get(slabfish.level).getSlabfishType(slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).getTextureLocation());
		if (slabfish.isInSittingPose() || slabfish.getVehicle() != null)
			matrixStack.translate(0F, slabfish.isBaby() ? 0.15625F : 0.3125F, 0F);
		if (slabfish.isInWater()) {
			matrixStack.translate(0F, slabfish.isBaby() ? -0.8F : -0.4F, 0.5F);
			matrixStack.mulPose(Vector3f.XP.rotation((float) (Math.PI / 2)));
		}
	}
}
