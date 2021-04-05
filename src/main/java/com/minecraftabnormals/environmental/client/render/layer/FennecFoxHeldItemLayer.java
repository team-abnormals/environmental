package com.minecraftabnormals.environmental.client.render.layer;

import com.minecraftabnormals.environmental.client.model.FennecFoxModel;
import com.minecraftabnormals.environmental.common.entity.FennecFoxEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class FennecFoxHeldItemLayer extends LayerRenderer<FennecFoxEntity, FennecFoxModel> {
    public FennecFoxHeldItemLayer(IEntityRenderer<FennecFoxEntity, FennecFoxModel> renderer) {
        super(renderer);
    }

    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, FennecFoxEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // TODO Fix item position
        boolean sleeping = entity.isSleeping();
        boolean child = entity.isChild();
        stack.push();
        if (child) {
            stack.scale(0.75F, 0.75F, 0.75F);
            stack.translate(0.0D, 0.5D, 0.209375F);
        }

        stack.translate((this.getEntityModel()).head.rotationPointX / 16.0F, (this.getEntityModel()).head.rotationPointY / 16.0F, (this.getEntityModel()).head.rotationPointZ / 16.0F);
        float f1 = entity.func_213475_v(partialTicks);
        stack.rotate(Vector3f.ZP.rotation(f1));
        stack.rotate(Vector3f.YP.rotationDegrees(netHeadYaw));
        stack.rotate(Vector3f.XP.rotationDegrees(headPitch));
        if (entity.isChild()) {
            if (sleeping) {
                stack.translate(0.4F, 0.26F, 0.15F);
            } else {
                stack.translate(0.06F, 0.26F, -0.5D);
            }
        } else if (sleeping) {
            stack.translate(0.46F, 0.26F, 0.22F);
        } else {
            stack.translate(0.06F, 0.27F, -0.5D);
        }

        stack.rotate(Vector3f.XP.rotationDegrees(90.0F));
        if (sleeping) {
            stack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
        }

        ItemStack itemstack = entity.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, itemstack, ItemCameraTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
        stack.pop();
    }
}