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
		this.texWidth = 64;
		this.texHeight = 32;

		this.leftLeg = new ModelRenderer(this, 0, 0);
		this.leftLeg.setPos(1.5F, 20.0F, 1.0F);
		this.leftLeg.texOffs(48, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.setPos(0.0F, 20.0F, 0.0F);
		this.body.texOffs(14, 0).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(body, ((float) Math.PI / 2.0F), 0.0F, 0.0F);

		this.leftWing = new ModelRenderer(this, 0, 0);
		this.leftWing.setPos(3.5F, 15.0F, -1.0F);
		this.leftWing.texOffs(30, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.setPos(0.0F, 16.0F, -3.0F);
		this.head.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(0, 11).addBox(-1.5F, -4.0F, -5.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 0, 0);
		this.rightLeg.setPos(-1.5F, 20.0F, 1.0F);
		this.rightLeg.texOffs(36, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.rightWing = new ModelRenderer(this, 0, 0);
		this.rightWing.setPos(-3.5F, 15.0F, -1.0F);
		this.rightWing.texOffs(14, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
	}

	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
	}

	@Override
	public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);

		float f = entityIn.getWingRotation(partialTick);
		this.rightWing.zRot = f;
		this.leftWing.zRot = -f;

		if (entityIn.isEating()) {
			this.head.xRot = entityIn.getHeadLean(partialTick) * 2.5F;
		}
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = ageInTicks - (float) entityIn.tickCount;
		float f1 = entityIn.getHeadLean(f);
		float f2 = 1.0F - f1;

		this.head.xRot = headPitch * ((float) Math.PI / 180F) * f2;
		this.head.xRot += f1 * 1.4F + MathHelper.cos(ageInTicks) * 0.15F * f1;
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F) * f2;

		this.head.y = 16.0F + f1;
		this.head.z = -3.0F - f1;

		if (!entityIn.isInWater()) {
			this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

			this.rightLeg.y = 20.0F;
			this.leftLeg.y = 20.0F;
		} else {
			float f3 = !entityIn.isBaby() ? 0.2F : 0.4F;

			this.rightLeg.xRot = 0.5F + MathHelper.cos(ageInTicks * f3) * 0.8F * (limbSwingAmount + 0.2F);
			this.leftLeg.xRot = 0.5F + MathHelper.cos(ageInTicks * f3 + (float) Math.PI) * 0.8F * (limbSwingAmount + 0.2F);

			this.rightLeg.y = 18.0F;
			this.leftLeg.y = 18.0F;
		}
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
