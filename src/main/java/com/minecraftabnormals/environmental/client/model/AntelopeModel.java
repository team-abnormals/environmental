package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelAntelope - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class AntelopeModel<T extends Entity> extends EntityModel<T> {
	public ModelRenderer RightFrontFoot;
	public ModelRenderer LeftFrontFoot;
	public ModelRenderer RightBackFoot;
	public ModelRenderer LeftBackFoot;
	public ModelRenderer Body;
	public ModelRenderer Neck;
	public ModelRenderer Head;
	public ModelRenderer Snout;
	public ModelRenderer RightAntler;
	public ModelRenderer LeftAntler;

	public AntelopeModel() {
		this.texWidth = 64;
		this.texHeight = 64;
		this.Snout = new ModelRenderer(this, 0, 31);
		this.Snout.setPos(0.0F, 0.0F, 0.0F);
		this.Snout.addBox(-2.0F, -2.0F, -9.5F, 4.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.RightFrontFoot = new ModelRenderer(this, 36, 38);
		this.RightFrontFoot.setPos(-2.5F, 12.0F, 0.0F);
		this.RightFrontFoot.addBox(-1.5F, -2.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.Head = new ModelRenderer(this, 0, 19);
		this.Head.setPos(0.0F, -9.0F, 0.0F);
		this.Head.addBox(-3.0F, -4.0F, -4.5F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(Head, -0.2181661564992912F, 0.0F, 0.0F);
		this.LeftBackFoot = new ModelRenderer(this, 50, 18);
		this.LeftBackFoot.setPos(3.0F, 12.0F, 15.0F);
		this.LeftBackFoot.addBox(-1.5F, -4.0F, -1.5F, 3.0F, 16.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.Body = new ModelRenderer(this, 0, 33);
		this.Body.setPos(0.0F, 6.0F, 16.1F);
		this.Body.addBox(-3.5F, -2.0F, -19.0F, 7.0F, 9.0F, 22.0F, 0.0F, 0.0F, 0.0F);
		this.LeftAntler = new ModelRenderer(this, 14, 0);
		this.LeftAntler.setPos(2.95F, 0.5F, 0.0F);
		this.LeftAntler.addBox(-6.0F, -14.0F, 0.0F, 6.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftAntler, -0.4363323129985824F, 0.0F, 0.7853981633974483F);
		this.LeftFrontFoot = new ModelRenderer(this, 52, 38);
		this.LeftFrontFoot.setPos(2.5F, 12.0F, 0.0F);
		this.LeftFrontFoot.addBox(-1.5F, -2.0F, -1.5F, 3.0F, 14.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.RightBackFoot = new ModelRenderer(this, 36, 18);
		this.RightBackFoot.setPos(-3.0F, 12.0F, 15.0F);
		this.RightBackFoot.addBox(-1.5F, -4.0F, -1.5F, 3.0F, 16.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.RightAntler = new ModelRenderer(this, 0, 0);
		this.RightAntler.setPos(-2.95F, 0.5F, 0.0F);
		this.RightAntler.addBox(0.0F, -14.0F, 0.0F, 6.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(RightAntler, -0.4363323129985824F, 0.0F, -0.7853981633974483F);
		this.Neck = new ModelRenderer(this, 0, 40);
		this.Neck.setPos(0.0F, 6.0F, 1.0F);
		this.Neck.addBox(-2.0F, -10.0F, -4.0F, 4.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(Neck, 0.4363323129985824F, 0.0F, 0.0F);
		this.Head.addChild(this.Snout);
		this.Neck.addChild(this.Head);
		this.Head.addChild(this.LeftAntler);
		this.Head.addChild(this.RightAntler);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.RightFrontFoot, this.LeftBackFoot, this.Body, this.LeftFrontFoot, this.RightBackFoot, this.Neck).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
