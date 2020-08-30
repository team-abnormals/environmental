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
 * ModelSeal - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SealModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightFlipper;
    public ModelRenderer leftFlipper;
    public ModelRenderer tail;
    public ModelRenderer fin;

    public SealModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 0, 4);
        this.head.setRotationPoint(0.0F, 20.0F, 1.0F);
        this.head.addBox(-2.5F, -3.0F, -4.0F, 5.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 23);
        this.tail.setRotationPoint(0.0F, 1.0F, 10.0F);
        this.tail.addBox(-2.0F, -1.0F, 0.0F, 4.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightFlipper = new ModelRenderer(this, 18, 0);
        this.rightFlipper.setRotationPoint(-4.0F, 20.0F, 4.0F);
        this.rightFlipper.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFlipper, 0.0F, 0.0F, 0.7853981633974483F);
        this.body = new ModelRenderer(this, 28, 16);
        this.body.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.body.addBox(-4.0F, -3.0F, 0.0F, 8.0F, 6.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.leftFlipper = new ModelRenderer(this, 28, 0);
        this.leftFlipper.setRotationPoint(4.0F, 20.0F, 4.0F);
        this.leftFlipper.addBox(-0.5F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftFlipper, 0.0F, 0.0F, -0.7853981633974483F);
        this.fin = new ModelRenderer(this, 0, 14);
        this.fin.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.fin.addBox(-5.0F, 0.0F, -2.0F, 10.0F, 1.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.tail);
        this.tail.addChild(this.fin);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.head, this.rightFlipper, this.body, this.leftFlipper).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
