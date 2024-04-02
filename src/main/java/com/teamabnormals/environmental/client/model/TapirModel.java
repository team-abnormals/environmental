package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class TapirModel<T extends Tapir> extends AgeableListModel<T> {
	private final ModelPart head;
	private final ModelPart snout;
	private final ModelPart body;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;

	public TapirModel(ModelPart root) {
		super(false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
		this.head = root.getChild("head");
		this.snout = this.head.getChild("snout");
		this.body = root.getChild("body");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightHindLeg = root.getChild("right_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 7.0F, 7.0F).texOffs(30, 9).mirror().addBox(3.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F).mirror(false).texOffs(30, 9).addBox(-6.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 11.0F, -8.0F));
		head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 5.0F, 4.0F).texOffs(46, 1).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 1.0F, -5.0F));

		root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 19.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 13.0F, -8.0F, Mth.HALF_PI, 0.0F, 0.0F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(30, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(3.0F, 18.0F, -6.0F));
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(30, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(-3.0F, 18.0F, -6.0F));
		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(30, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(3.0F, 18.0F, 6.0F));
		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(30, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), PartPose.offset(-3.0F, 18.0F, 6.0F));

		return LayerDefinition.create(meshdefinition, 64, 48);
	}

	@Override
	public void setupAnim(T tapir, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialTick = ageInTicks - tapir.tickCount;

		float snoutraiseanim = tapir.getSnoutRaiseAmount(partialTick);
		float shakeheadanim = tapir.getHeadShakeAnim(partialTick);
		float neckangle = tapir.getNeckAngle(partialTick);
		float noanimamount = 1F - tapir.getNoAnimAmount(partialTick);

		this.head.y = 11F;
		this.head.z = -8F;
		this.head.xRot = headPitch * Mth.DEG_TO_RAD * noanimamount;
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;

		this.snout.xRot = 0F;
		this.snout.yRot = 0F;

		this.head.y += neckangle;
		this.head.z += -neckangle;
		this.head.xRot += neckangle * 0.7F;

		if (tapir.isSniffing()) {
			this.head.xRot += Mth.sin(ageInTicks * 0.8F) * 0.015F;
			this.snout.xRot = Mth.sin(ageInTicks * 0.7F) * Mth.sin(ageInTicks * 0.2F) * 0.3F;
			this.snout.yRot = Mth.cos(ageInTicks * 0.6F) * 0.05F;
		} else if (tapir.isGrazing()) {
			this.head.xRot += Mth.sin(ageInTicks * 0.5F) * 0.05F;
			this.snout.xRot = Mth.sin(ageInTicks * 0.5F) * 0.3F;
		}

		this.snout.xRot += -0.4F * snoutraiseanim;

		if (shakeheadanim > 0) {
			this.head.yRot = Mth.sin(shakeheadanim * Mth.PI / 4) * 0.3F * noanimamount;
		}

		this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;
		this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;
		this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
	}
}