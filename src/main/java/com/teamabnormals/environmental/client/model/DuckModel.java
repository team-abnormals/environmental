package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.entity.animal.Duck;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelDuck - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DuckModel<T extends Duck> extends AgeableListModel<T> {
	public ModelPart head;
	public ModelPart body;
	public ModelPart rightLeg;
	public ModelPart leftLeg;
	public ModelPart rightWing;
	public ModelPart leftWing;

	public DuckModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.rightWing = root.getChild("right_wing");
		this.leftWing = root.getChild("left_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 8.0F, 3.0F, false).texOffs(0, 11).addBox(-1.5F, -4.0F, -5.0F, 3.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 16.0F, -3.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 0).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(-1.5F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 0).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(1.5F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightWing = root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(14, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, false), PartPose.offsetAndRotation(-3.5F, 15.0F, -1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftWing = root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(30, 13).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, false), PartPose.offsetAndRotation(3.5F, 15.0F, -1.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
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
		this.head.xRot += f1 * 1.4F + Mth.cos(ageInTicks) * 0.15F * f1;
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F) * f2;

		this.head.y = 16.0F + f1;
		this.head.z = -3.0F - f1;

		if (!entityIn.isInWater()) {
			this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

			this.rightLeg.y = 20.0F;
			this.leftLeg.y = 20.0F;
		} else {
			float f3 = !entityIn.isBaby() ? 0.2F : 0.4F;

			this.rightLeg.xRot = 0.5F + Mth.cos(ageInTicks * f3) * 0.8F * (limbSwingAmount + 0.2F);
			this.leftLeg.xRot = 0.5F + Mth.cos(ageInTicks * f3 + (float) Math.PI) * 0.8F * (limbSwingAmount + 0.2F);

			this.rightLeg.y = 18.0F;
			this.leftLeg.y = 18.0F;
		}
	}
}
