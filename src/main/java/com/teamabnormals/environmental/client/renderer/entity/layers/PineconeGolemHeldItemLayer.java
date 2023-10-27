package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.PineconeGolemModel;
import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;

public class PineconeGolemHeldItemLayer extends RenderLayer<PineconeGolem, PineconeGolemModel<PineconeGolem>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public PineconeGolemHeldItemLayer(RenderLayerParent<PineconeGolem, PineconeGolemModel<PineconeGolem>> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, PineconeGolem golem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = golem.getMainHandItem();

        if (!itemstack.isEmpty()) {
            stack.pushPose();
            stack.translate(0.0F, 1.0F, -0.55F);
            stack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            this.itemInHandRenderer.renderItem(golem, itemstack, ItemTransforms.TransformType.GROUND, false, stack, buffer, packedLight);
            stack.popPose();
        }
    }
}