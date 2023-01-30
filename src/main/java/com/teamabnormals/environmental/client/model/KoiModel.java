package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.Koi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiModel<T extends Koi> extends EntityModel<T> {
	public ModelPart front;
	public ModelPart back;
	public ModelPart frontTopFin;
	public ModelPart nose;
	public ModelPart rightWhisker;
	public ModelPart leftWhisker;
	public ModelPart rightFin;
	public ModelPart leftFin;
	public ModelPart tail;
	public ModelPart backTopFin;
	public ModelPart bottomFin;

	public KoiModel(ModelPart root) {
		this.front = root.getChild("front");
		this.rightWhisker = this.front.getChild("right_whisker");
		this.nose = this.front.getChild("nose");
		this.leftFin = this.front.getChild("left_fin");
		this.back = this.front.getChild("back");
		this.tail = this.back.getChild("tail");
		this.bottomFin = this.back.getChild("bottom_fin");
		this.backTopFin = this.back.getChild("back_top_fin");
		this.rightFin = this.front.getChild("right_fin");
		this.frontTopFin = this.front.getChild("front_top_fin");
		this.leftWhisker = this.front.getChild("left_whisker");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition front = root.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftWhisker = front.addOrReplaceChild("left_whisker", CubeListBuilder.create().texOffs(0, 4).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(-1.5F, 0.0F, -8.5F, 0.0F, 0.7853982F, -0.2617994F));
		PartDefinition nose = front.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -9.0F, 3.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftFin = front.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(-6, 12).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, true), PartPose.offsetAndRotation(1.0F, 1.0F, -5.0F, 0.0F, 0.2617994F, 0.7853982F));
		PartDefinition back = front.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition tail = back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition bottomFin = back.addOrReplaceChild("bottom_fin", CubeListBuilder.create().texOffs(14, -3).addBox(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition backTopFin = back.addOrReplaceChild("back_top_fin", CubeListBuilder.create().texOffs(32, -8).addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightFin = front.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(-6, 12).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, false), PartPose.offsetAndRotation(-1.0F, 1.0F, -5.0F, 0.0F, -0.2617994F, -0.7853982F));
		PartDefinition frontTopFin = front.addOrReplaceChild("front_top_fin", CubeListBuilder.create().texOffs(14, -8).addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightWhisker = front.addOrReplaceChild("right_whisker", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, true), PartPose.offsetAndRotation(1.5F, 0.0F, -8.5F, 0.0F, -0.7853982F, 0.2617994F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.front).forEach((modelRenderer) -> modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInWater()) {
			setRotateAngle(front, front.xRot, Mth.sin(0.6F * ageInTicks) * 0.3F * limbSwingAmount, front.zRot);
			setRotateAngle(back, back.xRot, Mth.cos(0.6F * ageInTicks) * 0.7F * limbSwingAmount, back.zRot);
			setRotateAngle(tail, tail.xRot, Mth.sin(0.6F * ageInTicks) * 0.75F * limbSwingAmount, tail.zRot);
		} else {
			setRotateAngle(front, front.xRot, -Mth.sin(0.6F * ageInTicks) * 0.45F, front.zRot);
			setRotateAngle(back, back.xRot, Mth.cos(0.6F * ageInTicks) * 0.2F, back.zRot);
			setRotateAngle(tail, tail.xRot, -Mth.sin(0.6F * ageInTicks) * 0.3F, tail.zRot);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
