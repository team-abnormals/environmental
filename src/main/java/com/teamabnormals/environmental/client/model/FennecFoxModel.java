package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.entity.animal.FennecFox;
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

@OnlyIn(Dist.CLIENT)
public class FennecFoxModel<E extends FennecFox> extends AgeableListModel<E> {
	public final ModelPart head;
	public final ModelPart rightEar;
	public final ModelPart leftEar;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart tail;
	private float legMotion;

	public FennecFoxModel(ModelPart root) {
		this.head = root.getChild("head");
		this.rightEar = this.head.getChild("right_ear");
		this.leftEar = this.head.getChild("left_ear");
		this.body = root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.0F, -5.0F, 7.0F, 5.0F, 5.0F, false).texOffs(0, 10).addBox(-0.5F, 2.0F, -8.0F, 3.0F, 2.0F, 3.0F, false), PartPose.offsetAndRotation(-1.0F, 16.0F, -3.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightEar = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, -0.4363F, -0.4363F));
		PartDefinition leftEar = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, false), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 0.4363F, 0.4363F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 10).addBox(-2.5F, 2.0F, -3.0F, 5.0F, 6.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 18.0F, -5.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, false), PartPose.offsetAndRotation(2.0F, 8.0F, -1.0F, -0.0524F, 0.0F, 0.0F));
		PartDefinition rightHindLeg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(28, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.5F, 19.5F, 2.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftHindLeg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(36, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(3.5F, 19.5F, 2.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightFrontLeg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(12, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.5F, 19.5F, -2.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftFrontLeg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(20, 20).addBox(-3.0F, 0.5F, -1.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(3.5F, 19.5F, -2.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void prepareMobModel(E entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.body.xRot = ((float) Math.PI / 2F);
		this.tail.xRot = -0.05235988F;
		this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.head.setPos(-1F, 16.0F, -3F);
		this.head.yRot = 0.0F;
		this.head.zRot = entityIn.getHeadRollAngle(partialTick);
		this.rightHindLeg.visible = true;
		this.leftHindLeg.visible = true;
		this.rightFrontLeg.visible = true;
		this.leftFrontLeg.visible = true;
		this.body.setPos(0.0F, 18.0F, -5.0F);
		this.body.zRot = 0.0F;
		this.rightHindLeg.setPos(0.5F, 19.5F, 2.0F);
		this.leftHindLeg.setPos(3.5F, 19.5F, 2.0F);
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
			this.rightHindLeg.visible = false;
			this.leftHindLeg.visible = false;
			this.rightFrontLeg.visible = false;
			this.leftFrontLeg.visible = false;
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

			this.rightHindLeg.xRot = -1.5708F;
			this.rightHindLeg.setPos(0.5F, 23F, 2F);
			this.leftHindLeg.xRot = -1.5708F;
			this.leftHindLeg.setPos(3.5F, 23F, 2F);
			this.rightFrontLeg.xRot = -0.436332F;
			this.leftFrontLeg.xRot = -0.436332F;
		}
	}

	@Override
	public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityIn.isSleeping() && !entityIn.isFaceplanted() && !entityIn.isCrouching()) {
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		}

		if (entityIn.isSleeping()) {
			this.head.xRot = 0.0F;
			this.head.yRot = -2.0943952F;
			this.head.zRot = Mth.cos(ageInTicks * 0.027F) / 22.0F;
		}

		if (entityIn.isCrouching()) {
			float f = Mth.cos(ageInTicks) * 0.01F;
			this.body.yRot = f;
			this.rightHindLeg.zRot = f;
			this.leftHindLeg.zRot = f;
			this.rightFrontLeg.zRot = f / 2.0F;
			this.leftFrontLeg.zRot = f / 2.0F;
		}

		if (entityIn.isFaceplanted()) {
			this.legMotion += 0.67F;
			this.rightHindLeg.xRot = Mth.cos(this.legMotion * 0.4662F) * 0.1F;
			this.leftHindLeg.xRot = Mth.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.rightFrontLeg.xRot = Mth.cos(this.legMotion * 0.4662F + (float) Math.PI) * 0.1F;
			this.leftFrontLeg.xRot = Mth.cos(this.legMotion * 0.4662F) * 0.1F;
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
	}
}
