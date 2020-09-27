package com.minecraftabnormals.environmental.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuckEggRenderer<T extends Entity & IRendersAsItem> extends EntityRenderer<T> {
	private final net.minecraft.client.renderer.ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	private final float scale;
	private final boolean field_229126_f_;

	public DuckEggRenderer(EntityRendererManager p_i226035_1_, float p_i226035_3_, boolean p_i226035_4_) {
		super(p_i226035_1_);
		this.scale = p_i226035_3_;
		this.field_229126_f_ = p_i226035_4_;
	}

	public DuckEggRenderer(EntityRendererManager renderManagerIn) {
		this(renderManagerIn, 1.0F, false);
	}

	@Override
	protected int getBlockLight(T entityIn, BlockPos partialTicks) {
		return this.field_229126_f_ ? 15 : super.getBlockLight(entityIn, partialTicks);
	}

	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(this.scale, this.scale, this.scale);
		matrixStackIn.rotate(this.renderManager.getCameraOrientation());
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
		this.itemRenderer.renderItem(((IRendersAsItem) entityIn).getItem(), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getEntityTexture(Entity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}
