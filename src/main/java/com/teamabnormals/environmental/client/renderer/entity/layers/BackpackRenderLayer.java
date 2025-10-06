package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.client.model.SlabfishModel;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Registry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BackpackRenderLayer<E extends Slabfish, M extends SlabfishModel<E>> extends RenderLayer<E, M> {

	public BackpackRenderLayer(RenderLayerParent<E, M> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLightIn, E slabby, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!slabby.hasBackpack()) return;

		Registry<BackpackType> backpacks = SlabfishHelper.slabfishBackpacks(slabby.level());
		BackpackType backpackType = backpacks.get(slabby.getBackpackLocation());
		VertexConsumer builder = buffer.getBuffer(RenderType.entityCutoutNoCull(SlabfishSpriteUploader.ATLAS_LOCATION));
		this.getParentModel().sprite = SlabfishSpriteUploader.instance().getSprite(backpackType.texture());
		this.getParentModel().setupAnim(slabby, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.getParentModel().renderToBuffer(matrixStack, builder, packedLightIn, OverlayTexture.NO_OVERLAY);
	}
}
