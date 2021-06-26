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
 * WandererBootsModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class WandererBootsModel<T extends LivingEntity> extends BipedModel<T> {
	private static final Map<Float, WandererBootsModel<?>> MODEL_CACHE = new HashMap<>();
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftToe;
	private final ModelRenderer rightLeg;
	private final ModelRenderer rightToe;

	public WandererBootsModel(float modelSize) {
		super(modelSize, 0.0F, 32, 16);

		this.leftLeg = new ModelRenderer(this);
		this.leftLeg.setPos(0.0F, 0.0F, 0.0F);
		this.leftLeg.texOffs(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, true);

		this.leftToe = new ModelRenderer(this);
		this.leftToe.setPos(0.0F, 0.0F, 0.0F);
		this.leftLeg.addChild(leftToe);
		this.leftToe.texOffs(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, 0.0F, true);

		this.rightLeg = new ModelRenderer(this);
		this.rightLeg.setPos(0.0F, 0.0F, 0.0F);
		this.rightLeg.texOffs(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);

		this.rightToe = new ModelRenderer(this);
		this.rightToe.setPos(0.0F, 0.0F, 0.0F);
		this.rightLeg.addChild(rightToe);
		this.rightToe.texOffs(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.leftLeg.copyFrom(this.leftLeg);
		this.rightLeg.copyFrom(this.rightLeg);

		matrixStackIn.pushPose();
		matrixStackIn.scale(1.1F, 1.0F, 1.1F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.leftLeg, this.rightLeg);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, WandererBootsModel::new);
	}
}