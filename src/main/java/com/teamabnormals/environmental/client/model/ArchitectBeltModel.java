package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

/**
 * ArchitectBeltModel - Farcr
 */
public class ArchitectBeltModel extends HumanoidModel<LivingEntity> {
	public static final ArchitectBeltModel INSTANCE = new ArchitectBeltModel(createBodyLayer().bakeRoot());

	private final ModelPart rightKnee;
	private final ModelPart belt;
	private final ModelPart pouch;
	private final ModelPart leftKnee;

	public ArchitectBeltModel(ModelPart root) {
		super(root);
		this.rightKnee = root.getChild("rightKnee");
		this.belt = root.getChild("belt");
		this.pouch = this.belt.getChild("pouch");
		this.leftKnee = root.getChild("leftKnee");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition rightKnee = root.addOrReplaceChild("rightKnee", CubeListBuilder.create().texOffs(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, false), PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition belt = root.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 14.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition pouch = belt.addOrReplaceChild("pouch", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 4.0F, false), PartPose.offsetAndRotation(6.0F, 8.0F, -1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftKnee = root.addOrReplaceChild("leftKnee", CubeListBuilder.create().texOffs(12, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, true), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.belt.copyFrom(this.body);
		this.rightKnee.copyFrom(this.rightLeg);
		this.leftKnee.copyFrom(this.leftLeg);
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.0F, 1.0F, 1.01F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.belt, this.rightKnee, this.leftKnee);
	}
}