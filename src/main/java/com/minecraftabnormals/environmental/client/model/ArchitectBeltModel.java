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
		this.belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.belt.setTextureOffset(0, 13).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 14.0F, 5.0F, 0.0F, false);

		this.pouch = new ModelRenderer(this);
		this.pouch.setRotationPoint(6.0F, 8.0F, -1.0F);
		this.belt.addChild(this.pouch);
		this.pouch.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 4.0F, 0.0F, false);

		this.rightKnee = new ModelRenderer(this);
		this.rightKnee.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.rightKnee.setTextureOffset(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, false);

		this.leftKnee = new ModelRenderer(this);
		this.leftKnee.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.leftKnee.setTextureOffset(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, true);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.belt.copyModelAngles(this.bipedBody);
		this.rightKnee.copyModelAngles(this.bipedRightLeg);
		this.leftKnee.copyModelAngles(this.bipedLeftLeg);
		matrixStackIn.push();
		matrixStackIn.scale(1.0F, 1.0F, 1.01F);
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.pop();
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.belt, this.rightKnee, this.leftKnee);
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, ArchitectBeltModel::new);
	}
}