package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.SlabfishModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.BackpackOverlayRenderLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.BackpackRenderLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.OverlayRenderLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.SweaterRenderLayer;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlabfishRenderer extends MobRenderer<Slabfish, SlabfishModel<Slabfish>> {

	public SlabfishRenderer(EntityRendererProvider.Context context) {
		super(context, new SlabfishModel<>(context.bakeLayer(EnvironmentalModelLayers.SLABFISH)), 0.3F);
		this.addLayer(new SweaterRenderLayer<>(this));
		this.addLayer(new BackpackRenderLayer<>(this));
		this.addLayer(new OverlayRenderLayer<>(this));
		this.addLayer(new BackpackOverlayRenderLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Slabfish slabby) {
		return SlabfishSpriteUploader.ATLAS_LOCATION;
	}

	@Override
	protected RenderType getRenderType(Slabfish slabby, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation texture = this.getTextureLocation(slabby);
		if (p_230496_3_) {
			return RenderType.itemEntityTranslucentCull(texture);
		} else if (p_230496_2_) {
			return SlabfishManager.get(slabby.level).getSlabfishType(slabby.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).isTranslucent() ? RenderType.entityTranslucent(texture) : this.model.renderType(texture);
		} else {
			return p_230496_4_ ? RenderType.outline(texture) : null;
		}
	}

	protected float getBob(Slabfish livingBase, float partialTicks) {
		float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (Mth.sin(f) + 1.0F) * f1;
	}

	@Override
	protected void scale(Slabfish slabfish, PoseStack matrixStack, float partialTickTime) {
		this.model.sprite = SlabfishSpriteUploader.instance().getSprite(SlabfishManager.get(slabfish.level).getSlabfishType(slabfish.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH).getTextureLocation());
		if (slabfish.isInSittingPose() || slabfish.getVehicle() != null)
			matrixStack.translate(0F, slabfish.isBaby() ? 0.15625F : 0.3125F, 0F);
		if (slabfish.isInWater()) {
			matrixStack.translate(0F, -0.35, slabfish.isBaby() ? 0.25F : 0.5F);
			matrixStack.mulPose(Vector3f.XP.rotation((float) (Math.PI / 2)));
		}
	}
}
