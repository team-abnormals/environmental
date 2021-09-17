package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelKoi - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class KoiModel<T extends KoiEntity> extends EntityModel<T> {

	public ModelRenderer Body1;
	public ModelRenderer Body2;
	public ModelRenderer TopFin1;
	public ModelRenderer Nose;
	public ModelRenderer Whisker1;
	public ModelRenderer Whisker2;
	public ModelRenderer RightFin;
	public ModelRenderer LeftFin;
	public ModelRenderer Tail;
	public ModelRenderer TopFin2;
	public ModelRenderer BottomFin;

	public KoiModel() {
		this.texWidth = 64;
		this.texHeight = 32;
		this.Body1 = new ModelRenderer(this, 0, 0);
		this.Body1.setPos(0.0F, 21.0F, 0.0F);
		this.Body1.addBox(-1.5F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.Whisker1 = new ModelRenderer(this, 0, 4);
		this.Whisker1.setPos(-1.5F, 0.0F, -8.5F);
		this.Whisker1.addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(Whisker1, 0.0F, 0.7853981633974483F, -0.2617993877991494F);
		this.Tail = new ModelRenderer(this, 0, 16);
		this.Tail.setPos(0.0F, 0.0F, 8.0F);
		this.Tail.addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.BottomFin = new ModelRenderer(this, 14, -3);
		this.BottomFin.setPos(0.0F, 2.0F, 4.0F);
		this.BottomFin.addBox(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.Nose = new ModelRenderer(this, 0, 0);
		this.Nose.setPos(0.0F, 0.0F, 0.0F);
		this.Nose.addBox(-1.5F, -1.0F, -9.0F, 3.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.LeftFin = new ModelRenderer(this, -6, 12);
		this.LeftFin.setPos(1.0F, 1.0F, -5.0F);
		this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftFin, 0.0F, 0.2617993877991494F, 0.7853981633974483F);
		this.Body2 = new ModelRenderer(this, 0, 12);
		this.Body2.setPos(0.0F, 0.0F, 0.0F);
		this.Body2.addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.TopFin2 = new ModelRenderer(this, 32, -8);
		this.TopFin2.setPos(0.0F, -2.0F, 4.0F);
		this.TopFin2.addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.RightFin = new ModelRenderer(this, -6, 12);
		this.RightFin.setPos(-1.0F, 1.0F, -5.0F);
		this.RightFin.addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(RightFin, 0.0F, -0.2617993877991494F, -0.7853981633974483F);
		this.TopFin1 = new ModelRenderer(this, 14, -8);
		this.TopFin1.setPos(0.0F, -2.0F, -4.0F);
		this.TopFin1.addBox(0.0F, -3.0F, -4.0F, 0.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.Whisker2 = new ModelRenderer(this, 0, 4);
		this.Whisker2.mirror = true;
		this.Whisker2.setPos(1.5F, 0.0F, -8.5F);
		this.Whisker2.addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(Whisker2, 0.0F, -0.7853981633974483F, 0.2617993877991494F);
		this.Body1.addChild(this.Whisker1);
		this.Body2.addChild(this.Tail);
		this.Body2.addChild(this.BottomFin);
		this.Body1.addChild(this.Nose);
		this.Body1.addChild(this.LeftFin);
		this.Body1.addChild(this.Body2);
		this.Body2.addChild(this.TopFin2);
		this.Body1.addChild(this.RightFin);
		this.Body1.addChild(this.TopFin1);
		this.Body1.addChild(this.Whisker2);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.Body1).forEach((modelRenderer) -> modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isInWater()) {
			setRotateAngle(Body1, Body1.xRot, MathHelper.sin(0.6F * ageInTicks) * 0.3F * limbSwingAmount, Body1.zRot);
			setRotateAngle(Body2, Body2.xRot, MathHelper.cos(0.6F * ageInTicks) * 0.7F * limbSwingAmount, Body2.zRot);
			setRotateAngle(Tail, Tail.xRot, MathHelper.sin(0.6F * ageInTicks) * 0.75F * limbSwingAmount, Tail.zRot);
		} else {
			setRotateAngle(Body1, Body1.xRot, -MathHelper.sin(0.6F * ageInTicks) * 0.45F, Body1.zRot);
			setRotateAngle(Body2, Body2.xRot, MathHelper.cos(0.6F * ageInTicks) * 0.2F, Body2.zRot);
			setRotateAngle(Tail, Tail.xRot, -MathHelper.sin(0.6F * ageInTicks) * 0.3F, Tail.zRot);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
