package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.PineconeGolemModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.PineconeGolemHeldItemLayer;
import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PineconeGolemRenderer extends MobRenderer<PineconeGolem, PineconeGolemModel<PineconeGolem>> {
    private static final ResourceLocation PINECONE_GOLEM_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/entity/pinecone_golem.png");

    public PineconeGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new PineconeGolemModel<>(context.bakeLayer(EnvironmentalModelLayers.PINECONE_GOLEM)), 0.4F);
        this.addLayer(new PineconeGolemHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PineconeGolem pineconeGolem) {
        return PINECONE_GOLEM_LOCATION;
    }

    @Override
    protected void setupRotations(PineconeGolem pineconeGolem, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(pineconeGolem, poseStack, ageInTicks, rotationYaw, partialTicks);
        float limbswing = pineconeGolem.animationPosition - pineconeGolem.animationSpeed * (1.0F - partialTicks);
        float limbswingamount = Math.min(Mth.lerp(partialTicks, pineconeGolem.animationSpeedOld, pineconeGolem.animationSpeed), 1.0F);
        float rot = Mth.cos(limbswing * 0.6662F + Mth.PI + 0.4F) * 0.2F * limbswingamount;

        poseStack.mulPose(Vector3f.YP.rotation(rot));
    }
}