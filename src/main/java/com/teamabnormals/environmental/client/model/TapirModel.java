package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class TapirModel<T extends Entity> extends AgeableListModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_hind_leg;

	public TapirModel(ModelPart root) {
		super(false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_hind_leg = root.getChild("right_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(50, 9).mirror().addBox(3.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(50, 9).addBox(-6.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -7.0F));
		PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(50, 0).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -7.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 4).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, -8.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 18.0F, -5.0F));
		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 18.0F, -5.0F));
		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 18.0F, 6.0F));
		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 18.0F, 6.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.right_hind_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.left_hind_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.right_hind_leg, this.left_hind_leg, this.right_front_leg, this.left_front_leg);
	}
}