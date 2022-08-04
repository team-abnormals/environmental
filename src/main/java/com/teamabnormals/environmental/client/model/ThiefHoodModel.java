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
 * ThiefHoodModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class ThiefHoodModel extends HumanoidModel<LivingEntity> {
	public static final ThiefHoodModel INSTANCE = new ThiefHoodModel(createBodyLayer().bakeRoot());

	private final ModelPart hood;
	private final ModelPart hoodBack;
	private final ModelPart pauldron;
	private final ModelPart rightShoulder;
	private final ModelPart leftShoulder;

	public ThiefHoodModel(ModelPart root) {
		super(root);
		this.hood = root.getChild("hood");
		this.hoodBack = this.hood.getChild("hoodBack");
		this.pauldron = root.getChild("pauldron");
		this.rightShoulder = root.getChild("rightShoulder");
		this.leftShoulder = root.getChild("leftShoulder");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition hood = root.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 10.0F, 9.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition hoodBack = hood.addOrReplaceChild("hoodBack", CubeListBuilder.create().texOffs(46, 12).addBox(-3.5F, -10.25F, 1.7247F, 7.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		PartDefinition pauldron = root.addOrReplaceChild("pauldron", CubeListBuilder.create().texOffs(20, 19).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 8.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightShoulder = root.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(0, 19).addBox(-3.25F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, false), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftShoulder = root.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(0, 19).addBox(-1.75F, -2.9F, -2.5F, 5.0F, 8.0F, 5.0F, true), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.hood.copyFrom(this.head);
		this.rightShoulder.copyFrom(this.rightArm);
		this.leftShoulder.copyFrom(this.leftArm);
		this.pauldron.copyFrom(this.body);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.hood);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.pauldron, this.rightShoulder, this.leftShoulder);
	}
}