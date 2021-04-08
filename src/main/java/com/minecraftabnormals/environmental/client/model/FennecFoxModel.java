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
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(-1.0F, 16.0F, -3.0F);
		this.head.setTextureOffset(0, 0).addBox(-2.5F, -1.0F, -5.0F, 7.0F, 5.0F, 5.0F, 0.0F, false);
		this.head.setTextureOffset(0, 10).addBox(-0.5F, 2.0F, -8.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

		ModelRenderer rightEar = new ModelRenderer(this);
		rightEar.setRotationPoint(1.0F, 1.0F, -1.0F);
		this.head.addChild(rightEar);
		this.setRotationAngle(rightEar, 0.0F, -0.4363F, -0.4363F);
		rightEar.setTextureOffset(24, 0).addBox(-3.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		ModelRenderer leftEar = new ModelRenderer(this);
		leftEar.setRotationPoint(1.0F, 1.0F, -1.0F);
		this.head.addChild(leftEar);
		this.setRotationAngle(leftEar, 0.0F, 0.4363F, 0.4363F);
		leftEar.setTextureOffset(32, 0).addBox(0.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		this.legFrontRight = new ModelRenderer(this);
		this.legFrontRight.setRotationPoint(0.5F, 19.5F, -2.0F);
		this.legFrontRight.setTextureOffset(12, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legFrontLeft = new ModelRenderer(this);
		this.legFrontLeft.setRotationPoint(3.5F, 19.5F, -2.0F);
		this.legFrontLeft.setTextureOffset(20, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legBackRight = new ModelRenderer(this);
		this.legBackRight.setRotationPoint(0.5F, 19.5F, 2.0F);
		this.legBackRight.setTextureOffset(28, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.legBackLeft = new ModelRenderer(this);
		this.legBackLeft.setRotationPoint(3.5F, 19.5F, 2.0F);
		this.legBackLeft.setTextureOffset(36, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 18.0F, -5.0F);
		this.setRotationAngle(this.body, 1.5708F, 0.0F, 0.0F);
		this.body.setTextureOffset(12, 10).addBox(-2.5F, 2.0F, -3.0F, 5.0F, 6.0F, 4.0F, 0.0F, false);

		this.tail = new ModelRenderer(this);
		this.tail.setRotationPoint(2.0F, 8.0F, -1.0F);
		this.body.addChild(this.tail);
		this.setRotationAngle(this.tail, -0.0524F, 0.0F, 0.0F);
		this.tail.setTextureOffset(0, 15).addBox(-3.5F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setLivingAnimations(FennecFoxEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.body.rotateAngleX = ((float) Math.PI / 2F);
		this.tail.rotateAngleX = -0.05235988F;
		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.head.setRotationPoint(-1F, 16.0F, -3F);
		this.head.rotateAngleY = 0.0F;
		this.head.rotateAngleZ = entityIn.func_213475_v(partialTick);
		this.legBackRight.showModel = true;
		this.legBackLeft.showModel = true;
		this.legFrontRight.showModel = true;
		this.legFrontLeft.showModel = true;
		this.body.setRotationPoint(0.0F, 18.0F, -5.0F);
		this.body.rotateAngleZ = 0.0F;
		this.legBackRight.setRotationPoint(0.5F, 19.5F, 2.0F);
		this.legBackLeft.setRotationPoint(3.5F, 19.5F, 2.0F);
		if (entityIn.isCrouching()) {
			this.body.rotateAngleX = 1.6755161F;
			float f = entityIn.func_213503_w(partialTick);
			this.body.setRotationPoint(0.0F, 16.0F + entityIn.func_213503_w(partialTick), -5.0F);
			this.head.setRotationPoint(-1.0F, 15F + f, -3.0F);
			this.head.rotateAngleY = 0.0F;
		} else if (entityIn.isSleeping()) {
			this.body.rotateAngleZ = (-(float) Math.PI / 2F);
			this.body.setRotationPoint(-3.0F, 21.0F, -3.0F);
			this.tail.rotateAngleX = -1.39626F;
			this.tail.setRotationPoint(2.0F, 6.0F, -1.0F);
			if (this.isChild) {
				this.body.setRotationPoint(-3.0F, 21.0F, -1.0F);
			}

			this.head.setRotationPoint(-1F, 19.49F, -4.0F);
			this.head.rotateAngleX = 0.0F;
			this.head.rotateAngleY = -2.0943952F;
			this.head.rotateAngleZ = 0.0F;
			this.legBackRight.showModel = false;
			this.legBackLeft.showModel = false;
			this.legFrontRight.showModel = false;
			this.legFrontLeft.showModel = false;
		} else if (entityIn.isSitting()) {
			this.body.rotateAngleX = 0.523599F;
			this.body.setRotationPoint(0.0F, 15.0F, -2F);
			this.tail.rotateAngleX = 0.872665F;
			this.tail.setRotationPoint(2F, 8F, -1.0F);
			this.head.setRotationPoint(-1.0F, 14.0F, -1F);
			this.head.rotateAngleX = 0.0F;
			this.head.rotateAngleY = 0.0F;
			if (this.isChild) {
				this.head.setRotationPoint(-1.0F, 14.5F, -1.5F);
			}

			this.legBackRight.rotateAngleX = -1.5708F;
			this.legBackRight.setRotationPoint(0.5F, 23F, 2F);
			this.legBackLeft.rotateAngleX = -1.5708F;
			this.legBackLeft.setRotationPoint(3.5F, 23F, 2F);
			this.legFrontRight.rotateAngleX = -0.436332F;
			this.legFrontLeft.rotateAngleX = -0.436332F;
		}
	}

	@Override
	public void setRotationAngles(FennecFoxEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityIn.isSleeping() && !entityIn.isStuck() && !entityIn.isCrouching()) {
			this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
			this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		}

		if (entityIn.isSleeping()) {
			this.head.rotateAngleX = 0.0F;
			this.head.rotateAngleY = -2.0943952F;
			this.head.rotateAngleZ = MathHelper.cos(ageInTicks * 0.027F) / 22.0F;
		}

		if (entityIn.isCrouching()) {
			float f = MathHelper.cos(ageInTicks) * 0.01F;
			this.body.rotateAngleY = f;
			this.legBackRight.rotateAngleZ = f;
			this.legBackLeft.rotateAngleZ = f;
			this.legFrontRight.rotateAngleZ = f / 2.0F;
			this.legFrontLeft.rotateAngleZ = f / 2.0F;
		}

		if (entityIn.isStuck()) {
			this.legMotion += 0.67F;
			this.legBackRight.rotateAngleX = MathHelper.cos(this.legMotion * 0.4662F) * 0.1F;
			this.legBackLeft.rotateAngleX = MathHelper.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontRight.rotateAngleX = MathHelper.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontLeft.rotateAngleX = MathHelper.cos(this.legMotion * 0.4662F) * 0.1F;
		}
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
