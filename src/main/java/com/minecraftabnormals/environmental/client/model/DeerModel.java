package com.minecraftabnormals.environmental.client.model;

import com.minecraftabnormals.environmental.common.entity.DeerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * DeerModel - hatsondogs
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class DeerModel<E extends DeerEntity> extends EntityModel<E> {
	protected E entity;

	public final ModelRenderer body;
	public final ModelRenderer tail;
	public final ModelRenderer neck;
	public final ModelRenderer head;
	public final ModelRenderer ear1;
	public final ModelRenderer ear2;
	public final ModelRenderer antler1;
	public final ModelRenderer antler2;
	public final ModelRenderer leg1;
	public final ModelRenderer leg2;
	public final ModelRenderer leg3;
	public final ModelRenderer leg4;

	public DeerModel() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 17.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -4.0F, 8.0F);
		body.addChild(tail);
		setRotationAngle(tail, 0.2618F, 0.0F, 0.0F);
		tail.setTextureOffset(0, 40).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 1.0F, -8.75F);
		setRotationAngle(head, 0.0F, 0.0F, 0.0F);
		head.setTextureOffset(33, 0).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 5.0F, 6.0F, 0.05F, false);
		head.setTextureOffset(32, 25).addBox(-1.5F, -3.0F, -8.0F, 3.0F, 3.0F, 4.0F, 0.0F, false);

		ear1 = new ModelRenderer(this);
		ear1.setRotationPoint(2.0F, -3.0F, 1.0F);
		head.addChild(ear1);
		setRotationAngle(ear1, 0.0F, 0.7854F, 0.0F);
		ear1.setTextureOffset(33, 0).addBox(0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);

		ear2 = new ModelRenderer(this);
		ear2.setRotationPoint(-2.0F, -3.0F, 1.0F);
		head.addChild(ear2);
		setRotationAngle(ear2, 0.0F, -0.7854F, 0.0F);
		ear2.setTextureOffset(33, 0).addBox(-1.5F, -4.0F, 0.0F, 1.0F, 4.0F, 2.0F, 0.0F, true);

		antler1 = new ModelRenderer(this);
		antler1.setRotationPoint(0.0F, -4.0F, 0.25F);
		head.addChild(antler1);
		setRotationAngle(antler1, 0.0F, 0.7854F, 0.0F);
		antler1.setTextureOffset(0, 39).addBox(0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, 0.0F, false);

		antler2 = new ModelRenderer(this);
		antler2.setRotationPoint(0.0F, -4.0F, 0.25F);
		head.addChild(antler2);
		setRotationAngle(antler2, 0.0F, -0.7854F, 0.0F);
		antler2.setTextureOffset(0, 39).addBox(-0.5F, -10.0F, 0.0F, 0.0F, 11.0F, 7.0F, 0.0F, true);

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, 10.0F, -8.0F);
		setRotationAngle(neck, 0.2618F, 0.0F, 0.0F);
		neck.setTextureOffset(0, 25).addBox(-2.5F, -10.0F, -1.0F, 5.0F, 10.0F, 5.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(3.5F, 14.0F, -5.0F);
		leg1.setTextureOffset(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-3.5F, 14.0F, -5.0F);
		leg2.setTextureOffset(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, 0.0F, true);

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(3.5F, 14.0F, 6.0F);
		leg3.setTextureOffset(20, 25).addBox(-2.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, 0.0F, false);

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(-3.5F, 14.0F, 6.0F);
		leg4.setTextureOffset(20, 25).addBox(-0.5F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.entity = entity;

		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (!this.entity.hasAntlers() || entity.isChild()) {
			antler1.showModel = false;
			antler2.showModel = false;
		} else {
			antler1.showModel = true;
			antler2.showModel = true;
		}

		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		neck.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay);
		leg4.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}