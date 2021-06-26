package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.FennecFoxModel;
import com.minecraftabnormals.environmental.client.render.layer.FennecFoxHeldItemLayer;
import com.minecraftabnormals.environmental.common.entity.FennecFoxEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class FennecFoxRenderer extends MobRenderer<FennecFoxEntity, FennecFoxModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Environmental.MOD_ID, "textures/entity/fennec_fox.png");

    public FennecFoxRenderer(EntityRendererManager manager) {
        super(manager, new FennecFoxModel(), 0.4F);
        this.addLayer(new FennecFoxHeldItemLayer(this));
    }

    protected void setupRotations(FennecFoxEntity entity, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks);
        if (entity.isPouncing() || entity.isFaceplanted()) {
            float f = -MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot);
            stack.mulPose(Vector3f.XP.rotationDegrees(f));
        }

    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(FennecFoxEntity entity) {
        return TEXTURE;
    }
}
