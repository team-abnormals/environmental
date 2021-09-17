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
 * ModelPenguin - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class PenguinModel<T extends Entity> extends EntityModel<T> {
	public ModelRenderer RightFoot;
	public ModelRenderer LeftFoot;
	public ModelRenderer Body;
	public ModelRenderer Head;
	public ModelRenderer RightWing;
	public ModelRenderer LeftWing;
	public ModelRenderer Beak;
	public ModelRenderer Fluff;

	public PenguinModel() {
		this.texWidth = 64;
		this.texHeight = 32;
		this.Beak = new ModelRenderer(this, 0, 28);
		this.Beak.setPos(0.0F, 0.0F, 0.0F);
		this.Beak.addBox(-2.0F, 0.0F, -5.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.RightWing = new ModelRenderer(this, 24, 0);
		this.RightWing.setPos(-3.0F, 0.0F, 0.0F);
		this.RightWing.addBox(-1.0F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(RightWing, 0.0F, 0.0F, 0.2181661564992912F);
		this.LeftWing = new ModelRenderer(this, 34, 0);
		this.LeftWing.setPos(3.0F, 0.0F, 0.0F);
		this.LeftWing.addBox(0.0F, 0.0F, -2.0F, 1.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftWing, 0.0F, 0.0F, -0.2181661564992912F);
		this.Body = new ModelRenderer(this, 0, 16);
		this.Body.setPos(0.0F, 14.0F, 0.0F);
		this.Body.addBox(-3.0F, 0.0F, -2.0F, 6.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.LeftFoot = new ModelRenderer(this, 30, 12);
		this.LeftFoot.setPos(2.5F, 22.0F, 0.0F);
		this.LeftFoot.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftFoot, 0.0F, -0.2181661564992912F, 0.0F);
		this.RightFoot = new ModelRenderer(this, 16, 12);
		this.RightFoot.setPos(-2.5F, 22.0F, 0.0F);
		this.RightFoot.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(RightFoot, 0.0F, 0.2181661564992912F, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setPos(0.0F, 11.0F, 0.0F);
		this.Head.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.Fluff = new ModelRenderer(this, 14, 24);
		this.Fluff.setPos(0.0F, 0.0F, 0.0F);
		this.Fluff.addBox(-3.0F, -5.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.Head.addChild(this.Beak);
		this.Body.addChild(this.RightWing);
		this.Body.addChild(this.LeftWing);
		this.Head.addChild(this.Fluff);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.Body, this.LeftFoot, this.RightFoot, this.Head).forEach((modelRenderer) -> {
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
