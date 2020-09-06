package com.minecraftabnormals.environmental.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ThiefHoodModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class ThiefHoodModel<T extends LivingEntity> extends BipedModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer headBack;
	private final ModelRenderer body;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;

    public ThiefHoodModel(float modelSize) {
    	super(modelSize, 0.0F, 64, 32);

		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.setTextureOffset(0, 0).addBox(-4.5F, -9.5F, -4.5F, 9.0F, 10.0F, 9.0F, 0.0F, false);

		this.headBack = new ModelRenderer(this);
		this.headBack.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addChild(headBack);
		this.setRotationAngle(headBack, -0.2618F, 0.0F, 0.0F);
		this.headBack.setTextureOffset(46, 12).addBox(-3.5F, -10.25F, 1.7247F, 7.0F, 2.0F, 2.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(20, 19).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 8.0F, 5.0F, 0.0F, false);

		this.rightArm = new ModelRenderer(this);
		this.rightArm.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.rightArm.setTextureOffset(0, 19).addBox(-3.25F, -3.0F, -2.5F, 5.0F, 8.0F, 5.0F, -0.1F, false);

		this.leftArm = new ModelRenderer(this);
		this.leftArm.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.leftArm.setTextureOffset(0, 19).addBox(-1.75F, -3.0F, -2.5F, 5.0F, 8.0F, 5.0F, -0.1F, true);
    }
    
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.head.copyModelAngles(this.bipedHead);
		this.rightArm.copyModelAngles(this.bipedRightArm);
		this.leftArm.copyModelAngles(this.bipedLeftArm);
		this.body.copyModelAngles(this.bipedBody);
		
		this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);	
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}