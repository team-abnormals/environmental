package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.FennecFoxModel;
import com.teamabnormals.environmental.common.entity.animal.FennecFox;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class FennecFoxHeldItemLayer extends RenderLayer<FennecFox, FennecFoxModel<FennecFox>> {
	private final ItemInHandRenderer itemInHandRenderer;

	public FennecFoxHeldItemLayer(RenderLayerParent<FennecFox, FennecFoxModel<FennecFox>> renderer, ItemInHandRenderer itemInHandRenderer) {
		super(renderer);
		this.itemInHandRenderer = itemInHandRenderer;
	}

	public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, FennecFox entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
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

		ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
		itemInHandRenderer.renderItem(entity, itemstack, ItemTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
		stack.popPose();
	}
}