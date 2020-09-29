package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.entity.DuckEntity;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelDuck - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DuckModel<T extends DuckEntity> extends AgeableModel<T> {
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;

	public DuckModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.leftLeg = new ModelRenderer(this, 0, 0);
		this.leftLeg.setRotationPoint(1.5F, 20.0F, 1.0F);
		this.leftLeg.setTextureOffset(48, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.body.setTextureOffset(14, 0).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(body, ((float)Math.PI / 2.0F), 0.0F, 0.0F);

		this.leftWing = new ModelRenderer(this, 0, 0);
		this.leftWing.setRotationPoint(3.5F, 15.0F, -1.0F);
		this.leftWing.setTextureOffset(30, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 16.0F, -3.0F);
		this.head.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.setTextureOffset(0, 11).addBox(-1.5F, -4.0F, -5.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 0, 0);
		this.rightLeg.setRotationPoint(-1.5F, 20.0F, 1.0F);
		this.rightLeg.setTextureOffset(36, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.rightWing = new ModelRenderer(this, 0, 0);
		this.rightWing.setRotationPoint(-3.5F, 15.0F, -1.0F);
		this.rightWing.setTextureOffset(14, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
	}

	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
	}

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
		float f = entityIn.getWingRotation(partialTick);
		
		this.rightWing.rotateAngleZ = f;
		this.leftWing.rotateAngleZ = -f;
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);

		if (!entityIn.isInWater()) {
			this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			
			this.rightLeg.rotationPointY = 20.0F;
			this.leftLeg.rotationPointY = 20.0F;
		}
		else {
			float f = !entityIn.isChild() ? 0.2F : 0.4F;
			
			this.rightLeg.rotateAngleX = 0.5F + MathHelper.cos(ageInTicks * f) * 0.8F * (limbSwingAmount + 0.2F);
			this.leftLeg.rotateAngleX = 0.5F + MathHelper.cos(ageInTicks * f + (float)Math.PI) * 0.8F * (limbSwingAmount + 0.2F);
			
			this.rightLeg.rotationPointY = 18.0F;
			this.leftLeg.rotationPointY = 18.0F;
		}
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
