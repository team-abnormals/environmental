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
	private final ModelRenderer hood;
	private final ModelRenderer hoodBack;
	private final ModelRenderer pauldron;
	private final ModelRenderer rightShoulder;
	private final ModelRenderer leftShoulder;

	public ThiefHoodModel(float modelSize) {
		super(modelSize, 0.0F, 64, 32);

		this.hood = new ModelRenderer(this);
		this.hood.setPos(0.0F, 0.0F, 0.0F);
		this.hood.texOffs(0, 0).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 10.0F, 9.0F, 0.0F, false);

		this.hoodBack = new ModelRenderer(this);
		this.hoodBack.setPos(0.0F, 0.0F, 0.0F);
		this.hood.addChild(hoodBack);
		this.setRotationAngle(hoodBack, -0.2618F, 0.0F, 0.0F);
		this.hoodBack.texOffs(46, 12).addBox(-3.5F, -10.25F, 1.7247F, 7.0F, 2.0F, 2.0F, 0.0F, false);

		this.pauldron = new ModelRenderer(this);
		this.pauldron.setPos(0.0F, 0.0F, 0.0F);
		this.pauldron.texOffs(20, 19).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 8.0F, 5.0F, 0.0F, false);

		this.rightShoulder = new ModelRenderer(this);
		this.rightShoulder.setPos(-5.0F, 0.0F, 0.0F);
		this.rightShoulder.texOffs(0, 19).addBox(-3.25F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, false);

		this.leftShoulder = new ModelRenderer(this);
		this.leftShoulder.setPos(5.0F, 0.0F, 0.0F);
		this.leftShoulder.texOffs(0, 19).addBox(-1.75F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.hood.copyFrom(this.head);
		this.rightShoulder.copyFrom(this.rightArm);
		this.leftShoulder.copyFrom(this.leftArm);
		this.pauldron.copyFrom(this.body);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.hood);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.pauldron, this.rightShoulder, this.leftShoulder);
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