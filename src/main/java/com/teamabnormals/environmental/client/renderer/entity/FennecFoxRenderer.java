package com.teamabnormals.environmental.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabnormals.environmental.client.model.FennecFoxModel;
import com.teamabnormals.environmental.client.renderer.entity.layers.FennecFoxHeldItemLayer;
import com.teamabnormals.environmental.common.entity.animal.FennecFox;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FennecFoxRenderer extends MobRenderer<FennecFox, FennecFoxModel<FennecFox>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Environmental.MOD_ID, "textures/entity/fennec_fox.png");

	public FennecFoxRenderer(EntityRendererProvider.Context context) {
		super(context, new FennecFoxModel<>(context.bakeLayer(EnvironmentalModelLayers.FENNEC_FOX)), 0.4F);
		this.addLayer(new FennecFoxHeldItemLayer(this));
	}

	protected void setupRotations(FennecFox entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks);
		if (entity.isPouncing() || entity.isFaceplanted()) {
			float f = -Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
			stack.mulPose(Vector3f.XP.rotationDegrees(f));
		}

	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(FennecFox entity) {
		return TEXTURE;
	}
}
