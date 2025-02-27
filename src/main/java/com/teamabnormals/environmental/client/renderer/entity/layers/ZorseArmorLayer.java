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
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.violetmoon.quark.content.tools.module.ColorRunesModule;

@OnlyIn(Dist.CLIENT)
public class ZorseArmorLayer extends RenderLayer<Zorse, ZorseModel<Zorse>> {
	private final ZorseModel<Zorse> model;

	public ZorseArmorLayer(RenderLayerParent<Zorse, ZorseModel<Zorse>> entityRenderer, EntityModelSet modelSet) {
		super(entityRenderer);
		this.model = new ZorseModel<>(modelSet.bakeLayer(EnvironmentalModelLayers.ZORSE_ARMOR));
	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Zorse zorse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack itemstack = zorse.getArmor();
		if (itemstack.getItem() instanceof HorseArmorItem) {
			HorseArmorItem horsearmoritem = (HorseArmorItem) itemstack.getItem();
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(zorse, limbSwing, limbSwingAmount, partialTick);
			this.model.setupAnim(zorse, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			float f;
			float f1;
			float f2;
			if (horsearmoritem instanceof DyeableHorseArmorItem) {
				int i = ((DyeableHorseArmorItem) horsearmoritem).getColor(itemstack);
				f = (float) (i >> 16 & 255) / 255.0F;
				f1 = (float) (i >> 8 & 255) / 255.0F;
				f2 = (float) (i & 255) / 255.0F;
			} else {
				f = 1.0F;
				f1 = 1.0F;
				f2 = 1.0F;
			}

			ItemStack stack = zorse.getArmor();
			setColorRuneTarget(stack);
			HorseArmorItem horseArmorItem = (HorseArmorItem) stack.getItem();
			VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityCutoutNoCull(horseArmorItem.getTexture()), false, stack.hasFoil());
			this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
		}
	}

	private static void setColorRuneTarget(ItemStack stack) {
		if (ModList.get().isLoaded("quark")) ColorRunesModule.setTargetStack(stack);
	}
}