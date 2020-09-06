package com.minecraftabnormals.environmental.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ArchitectBeltModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class ArchitectBeltModel<T extends LivingEntity> extends BipedModel<T> {
	private final ModelRenderer rightKnee;
	private final ModelRenderer belt;
	private final ModelRenderer pouch;
	private final ModelRenderer leftKnee;

	public ArchitectBeltModel(float modelSize) {
		super(modelSize, 0.0F, 48, 32);

		this.belt = new ModelRenderer(this);
		this.belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.belt.setTextureOffset(0, 12).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 14.0F, 6.0F, 0.0F, false);

		this.pouch = new ModelRenderer(this);
		this.pouch.setRotationPoint(6.0F, 8.0F, -1.0F);
		this.belt.addChild(this.pouch);
		this.pouch.setTextureOffset(0, 0).addBox(-2.0F, 0.0F, -1.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);

		this.rightKnee = new ModelRenderer(this);
		this.rightKnee.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.rightKnee.setTextureOffset(28, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, false);

		this.leftKnee = new ModelRenderer(this);
		this.leftKnee.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.leftKnee.setTextureOffset(28, 0).addBox(-2.5F, 1.5F, -2.5F, 5.0F, 5.0F, 5.0F, -0.2F, true);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.belt.copyModelAngles(this.bipedBody);
		this.rightKnee.copyModelAngles(this.bipedRightLeg);
		this.leftKnee.copyModelAngles(this.bipedLeftLeg);

		this.rightKnee.render(matrixStack, buffer, packedLight, packedOverlay);
		this.leftKnee.render(matrixStack, buffer, packedLight, packedOverlay);

		matrixStack.push();
		matrixStack.scale(0.95F, 1.0F, 0.95F);
		this.belt.render(matrixStack, buffer, packedLight, packedOverlay);
		matrixStack.pop();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}