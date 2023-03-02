package com.teamabnormals.environmental.client.model;

import com.teamabnormals.environmental.common.entity.animal.deer.Reindeer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ReindeerModel extends DeerModel<Reindeer> {

	public ReindeerModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 17.0F).texOffs(14, 39).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 17.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 37).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.2618F, 0.0F, 0.0F));
		root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -10.0F, -2.0F, 5.0F, 10.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 10.0F, -7.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.05F)).texOffs(32, 25).addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -5.75F, 0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(33, 0).mirror().addBox(-1.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(-2.0F, -3.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(33, 0).addBox(0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F), PartPose.offsetAndRotation(2.0F, -3.0F, 1.0F, 0.0F, 0.7854F, 0.0F));
		head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-0.5F, -9.0F, 1.0F, 0.0F, 10.0F, 14.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, -0.7854F, 0.0F));
		head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(0, 29).addBox(0.5F, -9.0F, 1.0F, 0.0F, 10.0F, 14.0F), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, 0.7854F, 0.0F));

		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(46, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(46, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(20, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));

		CubeDeformation cubedeformation = new CubeDeformation(0.0F, 3.0F, 0.0F);
		root.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(20, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(20, 25).mirror().addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
