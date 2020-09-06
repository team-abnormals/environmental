package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelAxolotl - MCVinnyq
 * Created using Tabula 7.1.0
 */
@OnlyIn(Dist.CLIENT)
public class AxolotlModel<T extends TameableEntity, E> extends AgeableModel<T> {
    public ModelRenderer Head;
    public ModelRenderer Body;
    public ModelRenderer RightFrontLeg;
    public ModelRenderer LeftFrontLeg;
    public ModelRenderer RightBackLeg;
    public ModelRenderer LeftbackLeg;
    public ModelRenderer Tail;
    public ModelRenderer Frills;

    public AxolotlModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.RightFrontLeg = new ModelRenderer(this, 0, 0);
        this.RightFrontLeg.setRotationPoint(-2.0F, 22.0F, 0.0F);
        this.RightFrontLeg.addBox(-1.0F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.Tail = new ModelRenderer(this, 46, 8);
        this.Tail.setRotationPoint(0.0F, 19.5F, 6.5F);
        this.Tail.addBox(-0.5F, -0.5F, 0.0F, 1, 2, 8, 0.0F);
        this.setRotateAngle(Tail, -0.40980330836826856F, 0.0F, 0.0F);
        this.Frills = new ModelRenderer(this, 28, 0);
        this.Frills.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Frills.addBox(-4.0F, -4.0F, 0.25F, 8, 6, 1, 0.0F);
        this.Head = new ModelRenderer(this, 46, 0);
        this.Head.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.Head.addBox(-2.5F, -2.0F, -3.0F, 5, 4, 4, 0.0F);
        this.LeftbackLeg = new ModelRenderer(this, 7, 5);
        this.LeftbackLeg.setRotationPoint(2.0F, 22.0F, 5.0F);
        this.LeftbackLeg.addBox(0.0F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.Body = new ModelRenderer(this, 13, 0);
        this.Body.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.Body.addBox(-2.0F, -2.0F, 0.0F, 4, 3, 7, 0.0F);
        this.LeftFrontLeg = new ModelRenderer(this, 0, 5);
        this.LeftFrontLeg.setRotationPoint(2.0F, 22.0F, 0.0F);
        this.LeftFrontLeg.addBox(0.0F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.RightBackLeg = new ModelRenderer(this, 7, 0);
        this.RightBackLeg.setRotationPoint(-2.0F, 22.0F, 5.0F);
        this.RightBackLeg.addBox(-1.0F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.Head.addChild(this.Frills);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	this.Head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
    }
    
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
    	if (entityIn.func_233685_eM_()) {
            this.Head.rotateAngleX = 1.2566371F;
            this.Head.rotateAngleY = 0.0F;
            this.Head.rotateAngleX = ((float)Math.PI / 4F);
            this.RightBackLeg.rotateAngleX = ((float)Math.PI * 1.5F);
            this.LeftbackLeg.rotateAngleX = ((float)Math.PI * 1.5F);
            this.RightFrontLeg.rotateAngleX = 5.811947F;
            this.LeftFrontLeg.rotateAngleX = 5.811947F;
         } else {
            this.Head.rotateAngleX = this.Body.rotateAngleX;
            this.RightBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.LeftbackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         }

    	this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
    

    @Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.Head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.RightFrontLeg, this.LeftbackLeg, this.Body, this.RightBackLeg, this.LeftFrontLeg, this.Tail);
	}
}
