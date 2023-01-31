package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * DeerModel - hatsondogs
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class DeerModel<E extends AbstractDeer> extends AgeableListModel<E> {
	private boolean hasAntlers;
	private float neckAngle;
	private float sprintAmount;
	private float hopAmount;
	private float hopAngle;

	public final ModelPart body;
	public final ModelPart tail;
	public final ModelPart neck;
	public final ModelPart head;
	public final ModelPart rightAntler;
	public final ModelPart leftAntler;
	public final ModelPart rightFrontLeg;
	public final ModelPart leftFrontLeg;
	public final ModelPart rightHindLeg;
	public final ModelPart leftHindLeg;
	public final ModelPart rightFrontBabyLeg;
	public final ModelPart leftFrontBabyLeg;
	public final ModelPart rightHindBabyLeg;
	public final ModelPart leftHindBabyLeg;

	public DeerModel(ModelPart root) {
		super(true, 14.5F, 2.0F);
		this.body = root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.neck = root.getChild("neck");
		this.head = root.getChild("head");
		this.rightAntler = this.head.getChild("right_antler");
		this.leftAntler = this.head.getChild("left_antler");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightFrontBabyLeg = root.getChild("right_front_baby_leg");
		this.leftFrontBabyLeg = root.getChild("left_front_baby_leg");
		this.rightHindBabyLeg = root.getChild("right_hind_baby_leg");
		this.leftHindBabyLeg = root.getChild("left_hind_baby_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 17.0F, false), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.2618F, 0.0F, 0.0F));
		root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -10.0F, -2.0F, 5.0F, 10.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 10.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.05F)).texOffs(32, 25).addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -5.75F, 0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(33, 0).addBox(-1.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, -3.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(33, 0).addBox(0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -3.0F, 1.0F, 0.0F, 0.7854F, 0.0F));
		head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, true), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, -0.7854F, 0.0F));
		head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(0, 39).addBox(0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, 0.7854F, 0.0F));
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		CubeDeformation cubedeformation = new CubeDeformation(0.0F, 3.0F, 0.0F);
		root.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(20, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(20, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.hasAntlers = entity.hasAntlers();
		this.neckAngle = entity.getNeckAngle(partialTick);
		this.hopAngle = entity.getHopAngle(partialTick);
		this.hopAmount = entity.getHopAmount(partialTick);
		this.sprintAmount = entity.getSprintAmount(partialTick) *  (1F - this.hopAmount);

		boolean flag = this.young;
		boolean flag1 = this.hasAntlers && !flag;
		this.rightAntler.visible = flag1;
		this.leftAntler.visible = flag1;
		this.rightFrontLeg.visible = !flag;
		this.leftFrontLeg.visible = !flag;
		this.rightHindLeg.visible = !flag;
		this.leftHindLeg.visible = !flag;
		this.rightFrontBabyLeg.visible = flag;
		this.leftFrontBabyLeg.visible = flag;
		this.rightHindBabyLeg.visible = flag;
		this.leftHindBabyLeg.visible = flag;

		this.body.y = flag ? 8F : 14F;
		this.neck.y = flag ? 4F : 10F;
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float neckanglerad = this.neckAngle * Mth.DEG_TO_RAD;
		float eatamount = Mth.clamp((this.neckAngle - 90F) / 40F, 0F, 1F);
		float noeatamount = 1F - eatamount;
		float noanimamount = Math.min(1F - this.sprintAmount, 1F - this.hopAmount);

		this.neck.xRot = neckanglerad;
		this.body.xRot = Mth.cos(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * this.sprintAmount;

		if (this.hopAngle != 0F) {
			this.body.xRot += this.hopAngle;
		}

		this.neck.y += (float) Math.tan(this.body.xRot) * 7F;
		this.neck.z = -7F - (float) Math.tan(this.body.xRot) * 4F;
		this.head.y = 2F - (this.young ? 0.7F : 1.0F) * 11F * Mth.cos(neckanglerad) + this.neck.y;
		this.head.z = 1.5F - (this.young ? 0.6F : 1.0F) * 12F * Mth.sin(neckanglerad) + this.neck.z;

		this.head.xRot = headPitch * Mth.DEG_TO_RAD * noeatamount;
		this.head.xRot += (Mth.sin(ageInTicks * 0.7F) * 0.15F + 0.65F) * eatamount;
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD * noeatamount;

		this.rightFrontLeg.y = 14F + (float) Math.tan(this.body.xRot) * 5F;
		this.leftFrontLeg.y = this.rightFrontLeg.y;
		this.rightHindLeg.y = 14F - (float) Math.tan(this.body.xRot) * 6F;
		this.leftHindLeg.y = this.rightHindLeg.y;

		this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount * noanimamount;
		this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.2F * limbSwingAmount * noanimamount;
		this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.2F * limbSwingAmount * noanimamount;
		this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount * noanimamount;

		if (this.sprintAmount > 0F) {
			this.rightFrontLeg.xRot += (-Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + Mth.PI)) * limbSwingAmount * this.sprintAmount;
			this.leftFrontLeg.xRot += (-Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + Mth.PI + 0.6F)) * limbSwingAmount * this.sprintAmount;
			this.rightHindLeg.xRot += (Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + 0.6F)) * limbSwingAmount * this.sprintAmount;
			this.leftHindLeg.xRot += (Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F)) * limbSwingAmount * this.sprintAmount;
		}

		if (this.hopAngle != 0F) {
			this.rightFrontLeg.xRot += -this.hopAngle * 3F;
			this.leftFrontLeg.xRot += -this.hopAngle * 3F * 0.85F;
			this.rightHindLeg.xRot += -this.hopAngle * 2.5F * 0.85F;
			this.leftHindLeg.xRot += -this.hopAngle * 2.5F;
		}

		if (this.young) {
			this.rightFrontBabyLeg.copyFrom(this.rightFrontLeg);
			this.leftFrontBabyLeg.copyFrom(this.leftFrontLeg);
			this.rightHindBabyLeg.copyFrom(this.rightHindLeg);
			this.leftHindBabyLeg.copyFrom(this.leftHindLeg);
			this.rightFrontBabyLeg.y -= 3F;
			this.leftFrontBabyLeg.y -= 3F;
			this.rightHindBabyLeg.y -= 3F;
			this.leftHindBabyLeg.y -= 3F;
		}
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.neck, this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftHindLeg, this.rightHindLeg, this.leftFrontBabyLeg, this.rightFrontBabyLeg, this.leftHindBabyLeg, this.rightHindBabyLeg);
	}

}