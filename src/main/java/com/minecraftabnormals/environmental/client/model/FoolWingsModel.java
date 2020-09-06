package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoolWingsModel<T extends LivingEntity> extends BipedModel<T> {
	private final ModelRenderer rightWing;
	private final ModelRenderer rightBackBottom;
	private final ModelRenderer rightBackTop;
	private final ModelRenderer leftWing;
	private final ModelRenderer leftBackBottom;
	private final ModelRenderer leftBackTop;

	public FoolWingsModel(float modelSize) {
		super(modelSize, 0.0F, 64, 64);

		this.rightWing = new ModelRenderer(this);
		this.rightWing.setRotationPoint(4.0F, 2.0F, 4.0F);
		this.setRotationAngle(this.rightWing, 0.3642F, 0.1278F, -0.22F);
		this.rightWing.setTextureOffset(0, 22).addBox(-5.0F, -1.0F, -1.5F, 12.0F, 21.0F, 0.0F, 0.0F, false);

		this.rightBackBottom = new ModelRenderer(this);
		this.rightBackBottom.setRotationPoint(-2.0F, 22.0F, -3.0F);
		this.rightWing.addChild(this.rightBackBottom);
		this.rightBackBottom.setTextureOffset(46, 28).addBox(-1.0F, -19.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		this.rightBackTop = new ModelRenderer(this);
		this.rightBackTop.setRotationPoint(-2.0F, 22.0F, -3.0F);
		this.rightWing.addChild(this.rightBackTop);
		this.rightBackTop.setTextureOffset(46, 49).addBox(-1.0F, -25.0F, 0.0F, 6.0F, 6.0F, 3.0F, 0.0F, false);

		this.leftWing = new ModelRenderer(this);
		this.leftWing.setRotationPoint(-4.0F, 2.0F, 4.0F);
		setRotationAngle(this.leftWing, 0.3642F, -0.1278F, 0.22F);
		this.leftWing.setTextureOffset(0, 22).addBox(-7.0F, -1.0F, -1.5F, 12.0F, 21.0F, 0.0F, 0.0F, true);

		this.leftBackBottom = new ModelRenderer(this);
		this.leftBackBottom.setRotationPoint(2.0F, 22.0F, -3.0F);
		this.leftWing.addChild(this.leftBackBottom);
		this.leftBackBottom.setTextureOffset(46, 28).addBox(-2.0F, -19.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);

		this.leftBackTop = new ModelRenderer(this);
		this.leftBackTop.setRotationPoint(2.0F, 22.0F, -3.0F);
		this.leftWing.addChild(this.leftBackTop);
		this.leftBackTop.setTextureOffset(46, 49).addBox(-5.0F, -25.0F, 0.0F, 6.0F, 6.0F, 3.0F, 0.0F, true);
	}
	
	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.leftWing, this.rightWing);
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rightWing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftWing.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}