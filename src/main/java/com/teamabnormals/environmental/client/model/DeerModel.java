package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * DeerModel - hatsondogs
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class DeerModel<E extends Deer> extends AgeableListModel<E> {
	protected E entity;
	private float neckAngle;

	public final ModelPart body;
	public final ModelPart tail;
	public final ModelPart neck;
	public final ModelPart head;
	public final ModelPart ear1;
	public final ModelPart ear2;
	public final ModelPart antler1;
	public final ModelPart antler2;
	public final ModelPart leg1;
	public final ModelPart leg2;
	public final ModelPart leg3;
	public final ModelPart leg4;

	public DeerModel(ModelPart root) {
		super(false, 6.0F, 2.5F);
		this.body = root.getChild("body");
		this.tail = this.body.getChild("tail");
		this.neck = root.getChild("neck");
		this.head = root.getChild("head");
		this.ear1 = this.head.getChild("ear1");
		this.ear2 = this.head.getChild("ear2");
		this.antler1 = this.head.getChild("antler1");
		this.antler2 = this.head.getChild("antler2");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 17.0F, false), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.2618F, 0.0F, 0.0F));
		root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -10.0F, -2.0F, 5.0F, 10.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 10.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.05F)).texOffs(32, 25).addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, -5.75F, 0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(33, 0).addBox(0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -3.0F, 1.0F, 0.0F, 0.7854F, 0.0F));
		head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(33, 0).addBox(-1.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, -3.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
		head.addOrReplaceChild("antler1", CubeListBuilder.create().texOffs(0, 39).addBox(0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, 0.7854F, 0.0F));
		head.addOrReplaceChild("antler2", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, true), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, -0.7854F, 0.0F));
		root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.entity = entity;
		this.neckAngle = entity.getNeckAngle(partialTick);
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = this.neckAngle * Mth.DEG_TO_RAD;
		float f1 = this.young ? 0.5F : 1.0F;
		float f2 = Mth.clamp((this.neckAngle - 90F) / 40F, 0F, 1F);
		float f3 = 1F - f2;

		this.head.y = 12F - f1 * 11F * Mth.cos(f);
		this.head.z = -5.5F - f1 * 12F * Mth.sin(f);

		this.head.xRot = f3 * headPitch * Mth.DEG_TO_RAD;
		this.head.xRot += f2 * (Mth.sin(ageInTicks * 0.7F) * 0.2F + 0.6F);
		this.head.yRot = f3 * netHeadYaw * Mth.DEG_TO_RAD;

		this.neck.xRot = f;

		this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (!this.entity.hasAntlers() || this.entity.isBaby()) {
			this.antler1.visible = false;
			this.antler2.visible = false;
		} else {
			this.antler1.visible = true;
			this.antler2.visible = true;
		}

		super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.neck, this.body, this.leg1, this.leg2, this.leg3, this.leg4);
	}

}