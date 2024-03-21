package com.teamabnormals.environmental.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class MuddyPigDecorationLayer<T extends Pig> extends RenderLayer<T, PigModel<T>> {
	private final BlockRenderDispatcher blockRenderer;

	public MuddyPigDecorationLayer(RenderLayerParent<T, PigModel<T>> parent, BlockRenderDispatcher renderer) {
		super(parent);
		this.blockRenderer = renderer;
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_117258_, T pig, float p_117260_, float p_117261_, float p_117262_, float p_117263_, float p_117264_, float p_117265_) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = minecraft.shouldEntityAppearGlowing(pig) && pig.isInvisible();
		IDataManager dataManager = (IDataManager) pig;
		ResourceLocation flower = dataManager.getValue(EnvironmentalDataProcessors.MUDDY_PIG_DECORATION);
		if (EnvironmentalConfig.COMMON.muddyPigs.get() && !flower.equals(new ResourceLocation("empty")) && ForgeRegistries.BLOCKS.getValue(flower) != null && (!pig.isInvisible() || flag)) {
			BlockState state = ForgeRegistries.BLOCKS.getValue(flower).defaultBlockState();
			int i = LivingEntityRenderer.getOverlayCoords(pig, 0.0F);
			BakedModel bakedmodel = this.blockRenderer.getBlockModel(state);
			poseStack.pushPose();

			if (pig.isBaby()) {
				poseStack.translate(0.0D, 0.25F, 0.25F);
			}

			this.getParentModel().head.translateAndRotate(poseStack);
			poseStack.translate(0.1D, -0.5625F, -0.35F);
			poseStack.scale(-0.625F, -0.625F, 0.625F);
			poseStack.translate(-0.5D, -0.5D, -0.5D);
			this.renderDecoration(poseStack, bufferSource, p_117258_, flag, state, i, bakedmodel);
			poseStack.popPose();
		}
	}

	private void renderDecoration(PoseStack p_234853_, MultiBufferSource p_234854_, int p_234855_, boolean p_234856_, BlockState p_234857_, int p_234858_, BakedModel p_234859_) {
		if (p_234856_) {
			this.blockRenderer.getModelRenderer().renderModel(p_234853_.last(), p_234854_.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), p_234857_, p_234859_, 0.0F, 0.0F, 0.0F, p_234855_, p_234858_);
		} else {
			this.blockRenderer.renderSingleBlock(p_234857_, p_234853_, p_234854_, p_234855_, p_234858_);
		}
	}
}