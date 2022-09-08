package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
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
public class DeerModel<E extends Deer> extends AgeableListModel<E> {
	private boolean hasAntlers;
	private float neckAngle;
	private float sprintAmount;

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

	public DeerModel(ModelPart root) {
		super(false, 6.0F, 2.5F);
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
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.hasAntlers = entity.hasAntlers();
		this.neckAngle = entity.getNeckAngle(partialTick);
		this.sprintAmount = entity.getSprintAmount(partialTick);
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = this.neckAngle * Mth.DEG_TO_RAD;
		float f1 = this.young ? 0.5F : 1.0F;
		float f2 = Mth.clamp((this.neckAngle - 90F) / 40F, 0F, 1F);
		float f3 = 1F - f2;
		float f4 = 1F - this.sprintAmount;

		this.neck.xRot = f;
		this.body.xRot = Mth.cos(limbSwing * 0.6662F) * 0.25F * limbSwingAmount * this.sprintAmount;

		this.neck.y = 10F + (float) Math.tan(this.body.xRot) * 7F;
		this.neck.z = -7F - (float) Math.tan(this.body.xRot) * 4F;
		this.head.y = 2F - f1 * 11F * Mth.cos(f) + this.neck.y;
		this.head.z = 1.5F - f1 * 12F * Mth.sin(f) + this.neck.z;

		this.head.xRot = f3 * headPitch * Mth.DEG_TO_RAD;
		this.head.xRot += f2 * (Mth.sin(ageInTicks * 0.7F) * 0.2F + 0.6F);
		this.head.yRot = f3 * netHeadYaw * Mth.DEG_TO_RAD;

		this.rightFrontLeg.y = 14F + (float) Math.tan(this.body.xRot) * 5F;
		this.leftFrontLeg.y = this.rightFrontLeg.y;
		this.rightHindLeg.y = 14F - (float) Math.tan(this.body.xRot) * 6F;
		this.leftHindLeg.y = this.rightHindLeg.y;

		this.rightHindLeg.xRot = f4 * Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
		this.leftHindLeg.xRot = f4 * Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
		this.rightFrontLeg.xRot = f4 * Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
		this.leftFrontLeg.xRot = f4 * Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;

		if (this.sprintAmount > 0F) {
			this.rightFrontLeg.xRot += (-Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + Mth.PI)) * limbSwingAmount * this.sprintAmount;
			this.leftFrontLeg.xRot += (-Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + Mth.PI + 0.6F)) * limbSwingAmount * this.sprintAmount;
			this.rightHindLeg.xRot += (Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F + 0.6F)) * limbSwingAmount * this.sprintAmount;
			this.leftHindLeg.xRot += (Mth.PI / 6F + Mth.cos(ageInTicks * 0.6662F)) * limbSwingAmount * this.sprintAmount;
		}
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		boolean flag = this.hasAntlers && !this.young;
		this.rightAntler.visible = flag;
		this.leftAntler.visible = flag;

		super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.neck, this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftHindLeg, this.rightHindLeg);
	}

}