package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelFennecFox - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class FennecFoxModel<T extends Entity> extends EntityModel<T> {
	public ModelRenderer head;
	public ModelRenderer rightFrontLeg;
	public ModelRenderer leftFrontLeg;
	public ModelRenderer rightBackLeg;
	public ModelRenderer leftBackLeg;
	public ModelRenderer torso;
	public ModelRenderer rightEar;
	public ModelRenderer leftEar;
	public ModelRenderer tail;

	public FennecFoxModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.tail = new ModelRenderer(this, 0, 0);
		this.tail.setRotationPoint(0.0F, 7.0F, -1.0F);
		this.tail.setTextureOffset(0, 15).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tail, -0.10471975511965977F, 0.0F, 0.0F);
		this.rightEar = new ModelRenderer(this, 0, 0);
		this.rightEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEar.setTextureOffset(24, 0).addBox(0.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(rightEar, 0.0F, 0.4363323129985824F, 0.4363323129985824F);
		this.leftBackLeg = new ModelRenderer(this, 0, 0);
		this.leftBackLeg.setRotationPoint(1.4F, 20.0F, 7.0F);
		this.leftBackLeg.setTextureOffset(36, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.leftFrontLeg = new ModelRenderer(this, 0, 0);
		this.leftFrontLeg.setRotationPoint(1.4F, 20.0F, 2.1F);
		this.leftFrontLeg.setTextureOffset(20, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.leftEar = new ModelRenderer(this, 0, 0);
		this.leftEar.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEar.setTextureOffset(32, 0).addBox(-3.0F, -8.0F, -1.0F, 3.0F, 6.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(leftEar, 0.0F, -0.4363323129985824F, -0.4363323129985824F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 17.0F, 0.0F);
		this.head.addBox(-3.5F, -2.0F, -4.0F, 7.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.head.setTextureOffset(0, 10).addBox(-1.5F, 1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.torso = new ModelRenderer(this, 0, 0);
		this.torso.setRotationPoint(0.0F, 18.0F, 0.0F);
		this.torso.setTextureOffset(12, 10).addBox(-2.5F, 1.0F, -3.0F, 5.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(torso, 1.5707963267948966F, 0.0F, 0.0F);
		this.rightBackLeg = new ModelRenderer(this, 0, 0);
		this.rightBackLeg.setRotationPoint(-1.4F, 20.0F, 7.0F);
		this.rightBackLeg.setTextureOffset(28, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.rightFrontLeg = new ModelRenderer(this, 0, 0);
		this.rightFrontLeg.setRotationPoint(-1.4F, 20.0F, 2.1F);
		this.rightFrontLeg.setTextureOffset(12, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.torso.addChild(this.tail);
		this.head.addChild(this.rightEar);
		this.head.addChild(this.leftEar);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.leftBackLeg, this.leftFrontLeg, this.head, this.torso, this.rightBackLeg, this.rightFrontLeg).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
