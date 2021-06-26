package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

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
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 9.0F, 5.0F, 0.0F, false);

		pouch = new ModelRenderer(this);
		pouch.setPos(-1.0F, 23.0F, -1.0F);
		body.addChild(pouch);
		pouch.texOffs(0, 3).addBox(-2.5F, -22.5F, 3.5F, 7.0F, 9.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.body.copyFrom(this.body);
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.01F, 1.01F, 1.02F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body);
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, HealerPouchModel::new);
	}
}