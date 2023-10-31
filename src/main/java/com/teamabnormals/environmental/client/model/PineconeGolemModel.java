package com.teamabnormals.environmental.client.model;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PineconeGolemModel<T extends PineconeGolem> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart nose;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public PineconeGolemModel(ModelPart root) {
        this.root = root;
        this.nose = root.getChild("nose");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("sapling_1", CubeListBuilder.create().texOffs(4, 20).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.78539816F, 0.0F));
        root.addOrReplaceChild("sapling_2", CubeListBuilder.create().texOffs(4, 20).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -0.78539816F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-0.5F, -8.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-5.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(5.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getMainHandItem().isEmpty()) {
            this.rightArm.y = 23F;
            this.leftArm.y = 23F;
            this.rightArm.z = 0F;
            this.leftArm.z = 0F;

            this.rightArm.xRot = 0.8F + headPitch * Mth.DEG_TO_RAD * 0.3F;
            this.leftArm.xRot = this.rightArm.xRot;
            this.rightArm.yRot = Mth.cos(limbSwing) * 0.8F * limbSwingAmount + Mth.PI / 2;
            this.leftArm.yRot = Mth.cos(limbSwing) * 0.8F * limbSwingAmount - Mth.PI / 2;
        } else {
            this.rightArm.y = 22F;
            this.leftArm.y = 22F;
            this.rightArm.z = -4.5F;
            this.leftArm.z = -4.5F;

            this.rightArm.xRot = 0.8F;
            this.leftArm.xRot = 0.8F;
            this.rightArm.yRot = -0.6F;
            this.leftArm.yRot = 0.6F;
        }
    }
}