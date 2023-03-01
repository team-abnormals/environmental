package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiModel<T extends Koi> extends EntityModel<T> {
	private final ModelPart front;
	private final ModelPart back;
	private final ModelPart tail;

	public KoiModel(ModelPart root) {
		this.front = root.getChild("front");
		this.back = root.getChild("back");
		this.tail = root.getChild("back").getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition front = root.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 1.0F));
		PartDefinition head = front.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 3).addBox(-1.5F, -4.0F, -4.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));
		PartDefinition leftWhisker = head.addOrReplaceChild("leftWhisker", CubeListBuilder.create().texOffs(20, 9).addBox(1.5F, -3.0F, -8.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 4.0F));
		PartDefinition rightWhisker = head.addOrReplaceChild("rightWhisker", CubeListBuilder.create().texOffs(20, 9).addBox(-1.5F, -3.0F, -8.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 4.0F));
		PartDefinition bodyFront = front.addOrReplaceChild("bodyFront", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition frontFin = bodyFront.addOrReplaceChild("frontFin", CubeListBuilder.create().texOffs(14, -1).addBox(0.0F, -10.0F, -3.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition frontLeftFin = bodyFront.addOrReplaceChild("frontLeftFin", CubeListBuilder.create().texOffs(6, 14).addBox(2.0F, -3.0F, -5.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition frontRightFin = bodyFront.addOrReplaceChild("frontRightFin", CubeListBuilder.create().texOffs(6, 14).addBox(-2.0F, -3.0F, -5.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition back = root.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 1.0F));
		PartDefinition bodyBack = back.addOrReplaceChild("bodyBack", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition backFin = bodyBack.addOrReplaceChild("backFin", CubeListBuilder.create().texOffs(15, 9).addBox(0.0F, -10.0F, 1.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition backRightFin = bodyBack.addOrReplaceChild("backRightFin", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -3.0F, 3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition backLeftFin = bodyBack.addOrReplaceChild("backLeftFin", CubeListBuilder.create().texOffs(0, 18).addBox(2.0F, -3.0F, 3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition tail = back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 12).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 7.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.front, this.back).forEach((modelRenderer) -> modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInWater()) {
			front.yRot = Mth.sin(0.6F * ageInTicks) * 0.3F * limbSwingAmount;
			back.yRot = Mth.cos(0.6F * ageInTicks) * 0.7F * limbSwingAmount;
			tail.yRot = Mth.sin(0.6F * ageInTicks) * 0.75F * limbSwingAmount;
		} else {
			back.yRot = Mth.cos(1.7F * 0.6F * ageInTicks) * 0.325F;
		}
	}
}
