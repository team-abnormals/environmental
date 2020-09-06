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
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class DeerModel<E extends DeerEntity> extends EntityModel<E> {
    protected E entity;

    public final ModelRenderer neck;
    public final ModelRenderer head;
    public final ModelRenderer rightEar;
    public final ModelRenderer leftEar;
    public final ModelRenderer rightAntler;
    public final ModelRenderer leftAntler;
    public final ModelRenderer snout;
    public final ModelRenderer body;
    public final ModelRenderer backRightLeg;
    public final ModelRenderer backLeftLeg;
    public final ModelRenderer frontRightLeg;
    public final ModelRenderer frontLeftLeg;

    public DeerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.neck = new ModelRenderer(this);
        this.neck.setRotationPoint(0.0F, 10.0F, -7.0F);
        this.setRotationAngle(this.neck, 0.2618F, 0.0F, 0.0F);
        this.neck.setTextureOffset(0, 41).addBox(-2.5F, -9.2235F, -2.1022F, 5.0F, 10.0F, 5.0F, 0.0F, false);

        this.head = new ModelRenderer(this);
        this.head.setRotationPoint(0.0F, -8.2235F, 0.3978F);
        this.neck.addChild(this.head);
        this.setRotationAngle(this.head, -0.2618F, 0.0F, 0.0F);
        this.head.setTextureOffset(0, 22).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        this.rightEar = new ModelRenderer(this);
        this.rightEar.setRotationPoint(-3.0F, -5.0F, 1.0F);
        this.head.addChild(this.rightEar);
        this.setRotationAngle(this.rightEar, 0.0F, 0.4363F, 0.0F);
        this.rightEar.setTextureOffset(0, 17).addBox(-3.0F, -3.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);

        this.leftEar = new ModelRenderer(this);
        this.leftEar.setRotationPoint(3.0F, -5.0F, 1.0F);
        this.head.addChild(this.leftEar);
        this.setRotationAngle(this.leftEar, 0.0F, -0.4363F, 0.0F);
        this.leftEar.setTextureOffset(0, 17).addBox(0.0F, -3.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, true);

        this.rightAntler = new ModelRenderer(this);
        this.rightAntler.setRotationPoint(-2.5F, -4.0F, 2.5F);
        this.head.addChild(this.rightAntler);
        this.setRotationAngle(this.rightAntler, 0.0F, 0.4363F, 0.0F);
        this.rightAntler.setTextureOffset(0, 7).addBox(-8.0F, -7.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.0F, false);

        this.leftAntler = new ModelRenderer(this);
        this.leftAntler.setRotationPoint(2.5F, -5.0F, 2.5F);
        this.head.addChild(this.leftAntler);
        this.setRotationAngle(this.leftAntler, 0.0F, -0.4363F, 0.0F);
        this.leftAntler.setTextureOffset(0, 7).addBox(-2.0F, -6.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.0F, true);

        this.snout = new ModelRenderer(this);
        this.snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addChild(this.snout);
        this.snout.setTextureOffset(0, 33).addBox(-2.5F, -3.0F, -8.0F, 5.0F, 3.0F, 5.0F, 0.0F, false);

        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(3.5F, 14.0F, -9.0F);
        this.body.setTextureOffset(0, 36).addBox(-7.0F, -8.0F, 0.0F, 7.0F, 8.0F, 20.0F, 0.0F, false);

        this.backRightLeg = new ModelRenderer(this);
        this.backRightLeg.setRotationPoint(-2.0F, 10.0F, 8.0F);
        this.backRightLeg.setTextureOffset(34, 38).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

        this.backLeftLeg = new ModelRenderer(this);
        this.backLeftLeg.setRotationPoint(2.0F, 10.0F, 8.0F);
        this.backLeftLeg.setTextureOffset(34, 38).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, true);

        this.frontRightLeg = new ModelRenderer(this);
        this.frontRightLeg.setRotationPoint(-2.0F, 11.0F, -6.5F);
        this.frontRightLeg.setTextureOffset(48, 40).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, 0.0F, false);

        this.frontLeftLeg = new ModelRenderer(this);
        this.frontLeftLeg.setRotationPoint(2.0F, 11.0F, -6.5F);
        this.frontLeftLeg.setTextureOffset(48, 40).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.entity = entity;

        this.neck.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
        this.frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
        this.frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!this.entity.hasAntlers() || entity.isChild()) {
            leftAntler.showModel = false;
            rightAntler.showModel = false;
        } else {
            leftAntler.showModel = true;
            rightAntler.showModel = true;
        }
        neck.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        backRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        backLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        frontRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        frontLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}