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
		boolean sleeping = entity.isSleeping();
		boolean child = entity.isBaby();
		stack.pushPose();
		if (child) {
			stack.scale(0.75F, 0.75F, 0.75F);
			stack.translate(0.0D, 0.5D, 0.209375F);
		}

		stack.translate((this.getParentModel()).head.x / 16.0F, (this.getParentModel()).head.y / 16.0F, (this.getParentModel()).head.z / 16.0F);
		float f1 = entity.getHeadRollAngle(partialTicks);
		stack.mulPose(Vector3f.ZP.rotation(f1));
		stack.mulPose(Vector3f.YP.rotationDegrees(netHeadYaw));
		stack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
		if (entity.isBaby()) {
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

		stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
		if (sleeping) {
			stack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}

		ItemStack itemstack = entity.getItemBySlot(EquipmentSlotType.MAINHAND);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemstack, ItemCameraTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
		stack.popPose();
	}
}