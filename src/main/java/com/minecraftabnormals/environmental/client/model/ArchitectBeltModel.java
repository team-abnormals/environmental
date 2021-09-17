package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * ArchitectBeltModel - Farcr
 */
public class ArchitectBeltModel<T extends LivingEntity> extends BipedModel<T> {
	private static final Map<Float, ArchitectBeltModel<?>> MODEL_CACHE = new HashMap<>();
	private final ModelRenderer rightKnee;
	private final ModelRenderer belt;
	private final ModelRenderer pouch;
	private final ModelRenderer leftKnee;

	public ArchitectBeltModel(float modelSize) {
		super(modelSize, 0.0F, 32, 32);

		this.belt = new ModelRenderer(this);
		this.belt.setPos(0.0F, 0.0F, 0.0F);
		this.belt.texOffs(0, 13).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 14.0F, 5.0F, 0.0F, false);

		this.pouch = new ModelRenderer(this);
		this.pouch.setPos(6.0F, 8.0F, -1.0F);
		this.belt.addChild(this.pouch);
		this.pouch.texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 4.0F, 0.0F, false);

		this.rightKnee = new ModelRenderer(this);
		this.rightKnee.setPos(-2.0F, 12.0F, 0.0F);
		this.rightKnee.texOffs(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, false);

		this.leftKnee = new ModelRenderer(this);
		this.leftKnee.setPos(2.0F, 12.0F, 0.0F);
		this.leftKnee.texOffs(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.belt.copyFrom(this.body);
		this.rightKnee.copyFrom(this.rightLeg);
		this.leftKnee.copyFrom(this.leftLeg);
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.0F, 1.0F, 1.01F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.belt, this.rightKnee, this.leftKnee);
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, ArchitectBeltModel::new);
	}
}