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

/**
 * ModelKoi - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class KoiModel<T extends Koi> extends EntityModel<T> {
	public ModelPart Body1;
	public ModelPart Body2;
	public ModelPart TopFin1;
	public ModelPart Nose;
	public ModelPart Whisker1;
	public ModelPart Whisker2;
	public ModelPart RightFin;
	public ModelPart LeftFin;
	public ModelPart Tail;
	public ModelPart TopFin2;
	public ModelPart BottomFin;

	public KoiModel(ModelPart root) {
		this.Body1 = root.getChild("Body1");
		this.Whisker1 = this.Body1.getChild("Whisker1");
		this.Nose = this.Body1.getChild("Nose");
		this.LeftFin = this.Body1.getChild("LeftFin");
		this.Body2 = this.Body1.getChild("Body2");
		this.Tail = this.Body2.getChild("Tail");
		this.BottomFin = this.Body2.getChild("BottomFin");
		this.TopFin2 = this.Body2.getChild("TopFin2");
		this.RightFin = this.Body1.getChild("RightFin");
		this.TopFin1 = this.Body1.getChild("TopFin1");
		this.Whisker2 = this.Body1.getChild("Whisker2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition Body1 = root.addOrReplaceChild("Body1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition Whisker1 = Body1.addOrReplaceChild("Whisker1", CubeListBuilder.create().texOffs(0, 4).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, false), PartPose.offsetAndRotation(-1.5F, 0.0F, -8.5F, 0.0F, 0.7853982F, -0.2617994F));
		PartDefinition Nose = Body1.addOrReplaceChild("Nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -9.0F, 3.0F, 3.0F, 1.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition LeftFin = Body1.addOrReplaceChild("LeftFin", CubeListBuilder.create().texOffs(-6, 12).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, true), PartPose.offsetAndRotation(1.0F, 1.0F, -5.0F, 0.0F, 0.2617994F, 0.7853982F));
		PartDefinition Body2 = Body1.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition Tail = Body2.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition BottomFin = Body2.addOrReplaceChild("BottomFin", CubeListBuilder.create().texOffs(14, -3).addBox(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition TopFin2 = Body2.addOrReplaceChild("TopFin2", CubeListBuilder.create().texOffs(32, -8).addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition RightFin = Body1.addOrReplaceChild("RightFin", CubeListBuilder.create().texOffs(-6, 12).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, false), PartPose.offsetAndRotation(-1.0F, 1.0F, -5.0F, 0.0F, -0.2617994F, -0.7853982F));
		PartDefinition TopFin1 = Body1.addOrReplaceChild("TopFin1", CubeListBuilder.create().texOffs(14, -8).addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition Whisker2 = Body1.addOrReplaceChild("Whisker2", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, true), PartPose.offsetAndRotation(1.5F, 0.0F, -8.5F, 0.0F, -0.7853982F, 0.2617994F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.Body1).forEach((modelRenderer) -> modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInWater()) {
			setRotateAngle(Body1, Body1.xRot, Mth.sin(0.6F * ageInTicks) * 0.3F * limbSwingAmount, Body1.zRot);
			setRotateAngle(Body2, Body2.xRot, Mth.cos(0.6F * ageInTicks) * 0.7F * limbSwingAmount, Body2.zRot);
			setRotateAngle(Tail, Tail.xRot, Mth.sin(0.6F * ageInTicks) * 0.75F * limbSwingAmount, Tail.zRot);
		} else {
			setRotateAngle(Body1, Body1.xRot, -Mth.sin(0.6F * ageInTicks) * 0.45F, Body1.zRot);
			setRotateAngle(Body2, Body2.xRot, Mth.cos(0.6F * ageInTicks) * 0.2F, Body2.zRot);
			setRotateAngle(Tail, Tail.xRot, -Mth.sin(0.6F * ageInTicks) * 0.3F, Tail.zRot);
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
