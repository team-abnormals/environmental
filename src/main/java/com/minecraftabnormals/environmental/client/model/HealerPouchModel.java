package com.minecraftabnormals.environmental.client.model;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * HealerPouchModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class HealerPouchModel<T extends LivingEntity> extends BipedModel<T> {
	private static final Map<Float, HealerPouchModel<?>> MODEL_CACHE = new HashMap<>();
	private final ModelRenderer body;
	private final ModelRenderer pouch;

	public HealerPouchModel(float modelSize) {
		super(modelSize, 0.0F, 32, 32);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(0, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 9.0F, 5.0F, 0.0F, false);

		pouch = new ModelRenderer(this);
		pouch.setRotationPoint(-1.0F, 23.0F, -1.0F);
		body.addChild(pouch);
		pouch.setTextureOffset(0, 3).addBox(-2.5F, -22.5F, 3.5F, 7.0F, 9.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.body.copyModelAngles(this.bipedBody);
		matrixStackIn.push();
		matrixStackIn.scale(1.01F, 1.01F, 1.01F);
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.pop();
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body);
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, HealerPouchModel::new);
	}
}