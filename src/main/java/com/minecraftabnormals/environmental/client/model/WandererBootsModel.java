package com.minecraftabnormals.environmental.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * WandererBootsModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class WandererBootsModel<T extends LivingEntity> extends BipedModel<T> {
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftToe;
	private final ModelRenderer rightLeg;
	private final ModelRenderer rightToe;

    public WandererBootsModel(float modelSize) {
    	super(modelSize, 0.0F, 32, 16);
    	
		this.leftLeg = new ModelRenderer(this);
		this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftLeg.setTextureOffset(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, true);

		this.leftToe = new ModelRenderer(this);
		this.leftToe.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftLeg.addChild(leftToe);
		this.leftToe.setTextureOffset(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, 0.0F, true);

		this.rightLeg = new ModelRenderer(this);
		this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightLeg.setTextureOffset(0, 0).addBox(-2.5F, 1.75F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, false);

		this.rightToe = new ModelRenderer(this);
		this.rightToe.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightLeg.addChild(rightToe);
		this.rightToe.setTextureOffset(20, 13).addBox(-2.5F, 10.75F, -3.5F, 5.0F, 2.0F, 1.0F, 0.0F, false);
    }

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.leftLeg.copyModelAngles(this.bipedLeftLeg);
		this.rightLeg.copyModelAngles(this.bipedRightLeg);
		
		matrixStack.push();
		matrixStack.scale(1.1F, 1.0F, 1.1F);
		this.leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.pop();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}