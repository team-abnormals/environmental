package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * WandererBootsModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class WandererBootsModel extends HumanoidModel<LivingEntity> {
	public static final WandererBootsModel INSTANCE = new WandererBootsModel(createBodyLayer().bakeRoot());

	private final ModelPart leftBoot;
	private final ModelPart leftToe;
	private final ModelPart rightBoot;
	private final ModelPart rightToe;

	public WandererBootsModel(ModelPart root) {
		super(root);
		this.leftBoot = root.getChild("left_boot");
		this.leftToe = this.leftBoot.getChild("left_toe");
		this.rightBoot = root.getChild("right_boot");
		this.rightToe = this.rightBoot.getChild("right_toe");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition leftBoot = root.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftToe = leftBoot.addOrReplaceChild("left_toe", CubeListBuilder.create().texOffs(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightBoot = root.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightToe = rightBoot.addOrReplaceChild("right_toe", CubeListBuilder.create().texOffs(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.leftBoot.copyFrom(this.leftLeg);
		this.rightBoot.copyFrom(this.rightLeg);

		matrixStackIn.pushPose();
		matrixStackIn.scale(1.1F, 1.0F, 1.1F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.leftBoot, this.rightBoot);
	}
}