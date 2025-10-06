package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.client.model.ZorseModel;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.core.other.EnvironmentalModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;

@OnlyIn(Dist.CLIENT)
public class ZorseArmorLayer extends RenderLayer<Zorse, ZorseModel<Zorse>> {
	private final ZorseModel<Zorse> model;

	public ZorseArmorLayer(RenderLayerParent<Zorse, ZorseModel<Zorse>> entityRenderer, EntityModelSet modelSet) {
		super(entityRenderer);
		this.model = new ZorseModel<>(modelSet.bakeLayer(EnvironmentalModelLayers.ZORSE_ARMOR));
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Zorse zorse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = zorse.getBodyArmorItem();
		if (stack.getItem() instanceof AnimalArmorItem animalArmor && animalArmor.getBodyType() == AnimalArmorItem.BodyType.EQUESTRIAN) {
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(zorse, limbSwing, limbSwingAmount, partialTick);
			this.model.setupAnim(zorse, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			int i = -1;
			if (stack.is(ItemTags.DYEABLE)) {
				i = FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(stack, -6265536));
			}

			setColorRuneTarget(stack);
			VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutoutNoCull(animalArmor.getTexture()), false, stack.hasFoil());
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, i);
		}
	}

	private static void setColorRuneTarget(ItemStack stack) {
		if (ModList.get().isLoaded("quark")) {
			// ColorRunesModule.setTargetStack(stack);
		}
	}
}