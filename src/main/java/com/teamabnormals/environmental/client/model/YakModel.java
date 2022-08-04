package com.teamabnormals.environmental.client.model;

import com.teamabnormals.environmental.common.entity.animal.Yak;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * YakModel - Hatsondogs
 * Created using Blockbench
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class YakModel<E extends Yak> extends QuadrupedModel<E> {
	private static final float RELATIVE_HEAD_Y = 3.0F;
	private static final float ADULT_EAT_OFFSET_MULTIPLIER = 9.0F;
	private static final float CHILD_EAT_OFFSET_MULTIPLIER = 4.5F;

	public ModelPart leftHorn;
	public ModelPart rightHorn;
	public ModelPart snout;
	public ModelPart leftEar;
	public ModelPart rightEar;

	public YakModel(ModelPart root) {
		super(root, false, 10.0F, 5.5F, 2.0F, 2.0F, 24);
		this.leftHorn = this.head.getChild("left_horn");
		this.rightHorn = this.head.getChild("right_horn");
		this.snout = this.head.getChild("snout");
		this.leftEar = this.head.getChild("left_ear");
		this.rightEar = this.head.getChild("right_ear");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, false).texOffs(72, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 3.0F, -8.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftHorn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, true).texOffs(0, 42).addBox(3.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, false), PartPose.offsetAndRotation(4.0F, -2.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightHorn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, false).texOffs(0, 42).addBox(-5.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, true), PartPose.offsetAndRotation(-4.0F, -2.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(36, 42).addBox(-3.0F, -0.7765F, -5.8978F, 6.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftEar = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(12, 0).addBox(4.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightEar = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(12, 0).addBox(-7.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -13.0F, -7.0F, 12.0F, 22.0F, 12.0F, false).texOffs(0, 0).addBox(-6.0F, -13.0F, -15.0F, 12.0F, 22.0F, 20.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
		PartDefinition rightHindLeg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, true), PartPose.offsetAndRotation(-5.0F, 12.0F, 7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftHindLeg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(5.0F, 12.0F, 7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightFrontLeg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, true), PartPose.offsetAndRotation(-5.0F, 12.0F, -8.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftFrontLeg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(5.0F, 12.0F, -8.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(E yak, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch) {
		super.setupAnim(yak, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch);
		float partialTicks = ageInTicks - yak.tickCount;
		this.head.y = RELATIVE_HEAD_Y + yak.getHeadEatingOffset(partialTicks) * (yak.isBaby() ? CHILD_EAT_OFFSET_MULTIPLIER : ADULT_EAT_OFFSET_MULTIPLIER);
		this.head.xRot = yak.getHeadPitch(partialTicks);
	}
}