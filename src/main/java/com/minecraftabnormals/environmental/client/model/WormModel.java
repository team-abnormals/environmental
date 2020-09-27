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
 * ModelWorm - Undefined Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class WormModel<T extends Entity> extends EntityModel<T> {
	public ModelRenderer head;
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer tail;

	public WormModel() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 23.0F, -3.0F);
		this.head.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.body1 = new ModelRenderer(this, 0, 5);
		this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body1.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.tail = new ModelRenderer(this, 0, 15);
		this.tail.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.tail.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.body2 = new ModelRenderer(this, 0, 10);
		this.body2.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.body2.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.addChild(this.body1);
		this.body2.addChild(this.tail);
		this.body1.addChild(this.body2);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.head).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
