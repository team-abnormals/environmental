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
 * ThiefHoodModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class ThiefHoodModel<T extends LivingEntity> extends BipedModel<T> {
	private static final Map<Float, ThiefHoodModel<?>> MODEL_CACHE = new HashMap<>();
	private final ModelRenderer head;
	private final ModelRenderer headBack;
	private final ModelRenderer body;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;

	public ThiefHoodModel(float modelSize) {
		super(modelSize, 0.0F, 64, 32);

		this.head = new ModelRenderer(this);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.texOffs(0, 0).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 10.0F, 9.0F, 0.0F, false);

		this.headBack = new ModelRenderer(this);
		this.headBack.setPos(0.0F, 0.0F, 0.0F);
		this.head.addChild(headBack);
		this.setRotationAngle(headBack, -0.2618F, 0.0F, 0.0F);
		this.headBack.texOffs(46, 12).addBox(-3.5F, -10.25F, 1.7247F, 7.0F, 2.0F, 2.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setPos(0.0F, 0.0F, 0.0F);
		this.body.texOffs(20, 19).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 8.0F, 5.0F, 0.0F, false);

		this.rightArm = new ModelRenderer(this);
		this.rightArm.setPos(-5.0F, 0.0F, 0.0F);
		this.rightArm.texOffs(0, 19).addBox(-3.25F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		this.leftArm = new ModelRenderer(this);
		this.leftArm.setPos(5.0F, 0.0F, 0.0F);
		this.leftArm.texOffs(0, 19).addBox(-1.75F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.head.copyFrom(this.head);
		this.rightArm.copyFrom(this.rightArm);
		this.leftArm.copyFrom(this.leftArm);
		this.body.copyFrom(this.body);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightArm, this.leftArm);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@SuppressWarnings("unchecked")
	public static <A extends BipedModel<?>> A get(float modelSize) {
		return (A) MODEL_CACHE.computeIfAbsent(modelSize, ThiefHoodModel::new);
	}
}