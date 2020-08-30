package com.minecraftabnormals.environmental.client.model;

import com.minecraftabnormals.environmental.common.entity.YakEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * YakModel - Hatsondogs
 * Created using Blockbench
 *
 * @param <E>
 */
public class YakModel<E extends YakEntity> extends QuadrupedModel<E> {

	//private float headRotationAngleX;

	public YakModel() {
		super(12, 0.0F, false, 10.0F, 5.5F, 2.0F, 2.0F, 24);
		textureWidth = 128;
		textureHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
		this.body.setTextureOffset(0, 42).addBox(-6.0F, -13.0F, -7.0F, 12.0F, 22.0F, 12.0F, 0.0F, false);
		this.body.setTextureOffset(0, 0).addBox(-6.0F, -13.0F, -15.0F, 12.0F, 22.0F, 20.0F, 0.5F, false);

		this.headModel = new ModelRenderer(this);
		this.headModel.setRotationPoint(0.0F, 3.0F, -8.0F);
		this.headModel.setTextureOffset(44, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, 0.0F, false);
		this.headModel.setTextureOffset(72, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, 0.5F, false);

		ModelRenderer leftHorn = new ModelRenderer(this);
		leftHorn.setRotationPoint(4.0F, -2.0F, -5.0F);
		this.headModel.addChild(leftHorn);
		leftHorn.setTextureOffset(0, 16).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, 0.0F, true);
		leftHorn.setTextureOffset(0, 42).addBox(3.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		ModelRenderer rightHorn = new ModelRenderer(this);
		rightHorn.setRotationPoint(-4.0F, -2.0F, -5.0F);
		this.headModel.addChild(rightHorn);
		rightHorn.setTextureOffset(0, 16).addBox(-5.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, 0.0F, false);
		rightHorn.setTextureOffset(0, 42).addBox(-5.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		ModelRenderer snout = new ModelRenderer(this);
		snout.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.headModel.addChild(snout);
		snout.setTextureOffset(36, 42).addBox(-3.0F, -0.7765F, -5.8978F, 6.0F, 4.0F, 3.0F, 0.0F, false);

		ModelRenderer leftEar = new ModelRenderer(this);
		leftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.headModel.addChild(leftEar);
		leftEar.setTextureOffset(12, 0).addBox(4.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		ModelRenderer rightEar = new ModelRenderer(this);
		rightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.headModel.addChild(rightEar);
		rightEar.setTextureOffset(12, 0).addBox(-7.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, 0.0F, true);

		this.legBackLeft = new ModelRenderer(this);
		this.legBackLeft.setRotationPoint(4.0F, 12.0F, 7.0F);
		this.legBackLeft.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		this.legBackRight = new ModelRenderer(this);
		this.legBackRight.setRotationPoint(-4.0F, 12.0F, 7.0F);
		this.legBackRight.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		this.legFrontLeft = new ModelRenderer(this);
		this.legFrontLeft.setRotationPoint(4.0F, 12.0F, -7.0F);
		this.legFrontLeft.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		this.legFrontRight = new ModelRenderer(this);
		this.legFrontRight.setRotationPoint(-4.0F, 12.0F, -7.0F);
		this.legFrontRight.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		--this.legBackRight.rotationPointX;
		++this.legBackLeft.rotationPointX;
		this.legBackRight.rotationPointZ += 0.0F;
		this.legBackLeft.rotationPointZ += 0.0F;
		--this.legFrontRight.rotationPointX;
		++this.legFrontLeft.rotationPointX;
		--this.legFrontRight.rotationPointZ;
		--this.legFrontLeft.rotationPointZ;
	}

//	@Override
//	public void setLivingAnimations(E entity, float limbSwing, float limbSwingAmount, float partialTicks) {
//		super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
//		this.headModel.rotationPointY = 6.0F + entity.getHeadRotationPointY(partialTicks) * 9.0F;
//		this.headRotationAngleX = entity.getHeadRotationAngleX(partialTicks);
//	}
//
//	@Override
//	public void setRotationAngles(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//		this.headModel.rotateAngleX = this.headRotationAngleX;
//	}
}