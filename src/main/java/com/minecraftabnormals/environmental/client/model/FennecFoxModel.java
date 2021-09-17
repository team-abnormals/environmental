package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.entity.FennecFoxEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FennecFoxModel extends AgeableModel<FennecFoxEntity> {
	public final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer legBackRight;
	private final ModelRenderer legBackLeft;
	private final ModelRenderer legFrontRight;
	private final ModelRenderer legFrontLeft;
	private final ModelRenderer tail;
	private float legMotion;

	public FennecFoxModel() {
		super(true, 9.35F, 1F);
		this.texWidth = 64;
		this.texHeight = 32;

		this.head = new ModelRenderer(this);
		this.head.setPos(-1.0F, 16.0F, -3.0F);
		this.head.texOffs(0, 0).addBox(-2.5F, -1.0F, -5.0F, 7.0F, 5.0F, 5.0F, 0.0F, false);
		this.head.texOffs(0, 10).addBox(-0.5F, 2.0F, -8.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

		ModelRenderer rightEar = new ModelRenderer(this);
		rightEar.setPos(1.0F, 1.0F, -1.0F);
		this.head.addChild(rightEar);
		this.setRotationAngle(rightEar, 0.0F, -0.4363F, -0.4363F);
		rightEar.texOffs(24, 0).addBox(-3.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		ModelRenderer leftEar = new ModelRenderer(this);
		leftEar.setPos(1.0F, 1.0F, -1.0F);
		this.head.addChild(leftEar);
		this.setRotationAngle(leftEar, 0.0F, 0.4363F, 0.4363F);
		leftEar.texOffs(32, 0).addBox(0.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		this.legFrontRight = new ModelRenderer(this);
		this.legFrontRight.setPos(0.5F, 19.5F, -2.0F);
		this.legFrontRight.texOffs(12, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legFrontLeft = new ModelRenderer(this);
		this.legFrontLeft.setPos(3.5F, 19.5F, -2.0F);
		this.legFrontLeft.texOffs(20, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legBackRight = new ModelRenderer(this);
		this.legBackRight.setPos(0.5F, 19.5F, 2.0F);
		this.legBackRight.texOffs(28, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legBackLeft = new ModelRenderer(this);
		this.legBackLeft.setPos(3.5F, 19.5F, 2.0F);
		this.legBackLeft.texOffs(36, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setPos(0.0F, 18.0F, -5.0F);
		this.setRotationAngle(this.body, 1.5708F, 0.0F, 0.0F);
		this.body.texOffs(12, 10).addBox(-2.5F, 2.0F, -3.0F, 5.0F, 6.0F, 4.0F, 0.0F, false);

		this.tail = new ModelRenderer(this);
		this.tail.setPos(2.0F, 8.0F, -1.0F);
		this.body.addChild(this.tail);
		this.setRotationAngle(this.tail, -0.0524F, 0.0F, 0.0F);
		this.tail.texOffs(0, 15).addBox(-3.5F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void prepareMobModel(FennecFoxEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.body.xRot = ((float) Math.PI / 2F);
		this.tail.xRot = -0.05235988F;
		this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.head.setPos(-1F, 16.0F, -3F);
		this.head.yRot = 0.0F;
		this.head.zRot = entityIn.getHeadRollAngle(partialTick);
		this.legBackRight.visible = true;
		this.legBackLeft.visible = true;
		this.legFrontRight.visible = true;
		this.legFrontLeft.visible = true;
		this.body.setPos(0.0F, 18.0F, -5.0F);
		this.body.zRot = 0.0F;
		this.legBackRight.setPos(0.5F, 19.5F, 2.0F);
		this.legBackLeft.setPos(3.5F, 19.5F, 2.0F);
		if (entityIn.isCrouching()) {
			this.body.xRot = 1.6755161F;
			float f = entityIn.getCrouchAmount(partialTick);
			this.body.setPos(0.0F, 16.0F + entityIn.getCrouchAmount(partialTick), -5.0F);
			this.head.setPos(-1.0F, 15F + f, -3.0F);
			this.head.yRot = 0.0F;
		} else if (entityIn.isSleeping()) {
			this.body.zRot = (-(float) Math.PI / 2F);
			this.body.setPos(-3.0F, 21.0F, -3.0F);
			this.tail.xRot = -1.39626F;
			this.tail.setPos(2.0F, 6.0F, -1.0F);
			if (this.young) {
				this.body.setPos(-3.0F, 21.0F, -1.0F);
			}

			this.head.setPos(-1F, 19.49F, -4.0F);
			this.head.xRot = 0.0F;
			this.head.yRot = -2.0943952F;
			this.head.zRot = 0.0F;
			this.legBackRight.visible = false;
			this.legBackLeft.visible = false;
			this.legFrontRight.visible = false;
			this.legFrontLeft.visible = false;
		} else if (entityIn.isSitting()) {
			this.body.xRot = 0.523599F;
			this.body.setPos(0.0F, 15.0F, -2F);
			this.tail.xRot = 0.872665F;
			this.tail.setPos(2F, 8F, -1.0F);
			this.head.setPos(-1.0F, 14.0F, -1F);
			this.head.xRot = 0.0F;
			this.head.yRot = 0.0F;
			if (this.young) {
				this.head.setPos(-1.0F, 14.5F, -1.5F);
			}

			this.legBackRight.xRot = -1.5708F;
			this.legBackRight.setPos(0.5F, 23F, 2F);
			this.legBackLeft.xRot = -1.5708F;
			this.legBackLeft.setPos(3.5F, 23F, 2F);
			this.legFrontRight.xRot = -0.436332F;
			this.legFrontLeft.xRot = -0.436332F;
		}
	}

	@Override
	public void setupAnim(FennecFoxEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityIn.isSleeping() && !entityIn.isFaceplanted() && !entityIn.isCrouching()) {
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		}

		if (entityIn.isSleeping()) {
			this.head.xRot = 0.0F;
			this.head.yRot = -2.0943952F;
			this.head.zRot = MathHelper.cos(ageInTicks * 0.027F) / 22.0F;
		}

		if (entityIn.isCrouching()) {
			float f = MathHelper.cos(ageInTicks) * 0.01F;
			this.body.yRot = f;
			this.legBackRight.zRot = f;
			this.legBackLeft.zRot = f;
			this.legFrontRight.zRot = f / 2.0F;
			this.legFrontLeft.zRot = f / 2.0F;
		}

		if (entityIn.isFaceplanted()) {
			this.legMotion += 0.67F;
			this.legBackRight.xRot = MathHelper.cos(this.legMotion * 0.4662F) * 0.1F;
			this.legBackLeft.xRot = MathHelper.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontRight.xRot = MathHelper.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontLeft.xRot = MathHelper.cos(this.legMotion * 0.4662F) * 0.1F;
		}
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
