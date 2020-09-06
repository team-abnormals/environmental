package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelSlabfish - Farcr
 * Created using Tabula 7.1.0
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class SlabfishModel<E extends SlabfishEntity> extends AgeableModel<E> {
    Entity entity;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer fin;
    public ModelRenderer backpack;
    public float partialTicks;

    public SlabfishModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.leftArm = new ModelRenderer(this, 16, 14);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(5.0F, -4.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.4363323129985824F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.body.addBox(-5.0F, -10.0F, -2.0F, 10, 10, 4, 0.0F);

        this.rightArm = new ModelRenderer(this, 16, 14);
        this.rightArm.setRotationPoint(-5.0F, -4.0F, 0.0F);
        this.rightArm.addBox(-1.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.4363323129985824F);
        this.fin = new ModelRenderer(this, 24, 12);
        this.fin.setRotationPoint(0.0F, -4.0F, 2.0F);
        this.fin.addBox(0.0F, -1.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(fin, -0.2181661564992912F, 0.0F, 0.0F);
        this.backpack = new ModelRenderer(this, 8, 20);
        this.backpack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backpack.addBox(-4.0F, -8.0F, 2.0F, 8, 8, 4, 0.0F);

        this.leftLeg = new ModelRenderer(this, 0, 14);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.5F, 19.0F, 1.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 14);
        this.rightLeg.setRotationPoint(-2.5F, 19.0F, 1.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);

        this.body.addChild(this.leftArm);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.fin);
        this.body.addChild(this.backpack);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.entity = entityIn;

        if (!entityIn.isInWater()) {
            if (entityIn.isPartying()) {
                float f = MathHelper.cos((float) entityIn.ticksExisted + partialTicks);
                float f1 = MathHelper.sin((float) entityIn.ticksExisted + partialTicks);
                this.body.rotationPointZ = f;
                this.body.rotationPointY = 19.75F - f1;
            }

            this.rightArm.rotateAngleZ = ageInTicks;
            this.leftArm.rotateAngleZ = -ageInTicks;
            this.rightLeg.rotateAngleX = (entityIn.func_233684_eK_() || entityIn.getRidingEntity() != null) ? -1.57F : MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
            this.leftLeg.rotateAngleX = (entityIn.func_233684_eK_() || entityIn.getRidingEntity() != null) ? -1.57F : MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.5F * limbSwingAmount;

        } else {
            this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.7F * limbSwingAmount;
            this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.7F * limbSwingAmount;
            this.rightArm.rotateAngleZ = ageInTicks;
            this.leftArm.rotateAngleZ = -ageInTicks;
        }
    }

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}
}
