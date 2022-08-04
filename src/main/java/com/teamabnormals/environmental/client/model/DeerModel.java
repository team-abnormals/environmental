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
		super(false, 12.0F, 4.0F);
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
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 8.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition neck = root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -10.0F, -1.0F, 5.0F, 10.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 10.0F, -8.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 6.0F, false).texOffs(32, 25).addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 1.0F, -8.75F, 0.0F, 0.0F, 0.0F));
		PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(33, 0).addBox(0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(2.0F, -3.0F, 1.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(33, 0).addBox(-1.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, true), PartPose.offsetAndRotation(-2.0F, -3.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition antler1 = head.addOrReplaceChild("antler1", CubeListBuilder.create().texOffs(0, 39).addBox(0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, 0.7854F, 0.0F));
		PartDefinition antler2 = head.addOrReplaceChild("antler2", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, true), PartPose.offsetAndRotation(0.0F, -4.0F, 0.25F, 0.0F, -0.7854F, 0.0F));
		PartDefinition leg1 = root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leg2 = root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, -5.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leg3 = root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, false), PartPose.offsetAndRotation(3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leg4 = root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, true), PartPose.offsetAndRotation(-3.5F, 14.0F, 6.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.entity = entity;

		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (!this.entity.hasAntlers() || entity.isBaby()) {
			antler1.visible = false;
			antler2.visible = false;
		} else {
			antler1.visible = true;
			antler2.visible = true;
		}

		super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(neck, body, leg1, leg2, leg3, leg4);
	}

}