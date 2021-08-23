package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;
import java.util.List;

/**
 * ModelSlabfish - Farcr
 * Created using Tabula 7.1.0
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class SlabfishModel<E extends SlabfishEntity> extends AgeableModel<E> {

	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer fin;
	public ModelRenderer backpack;
	public TextureAtlasSprite sprite;
	private float partialTicks;

	public SlabfishModel() {
		this.texWidth = 32;
		this.texHeight = 32;
		this.leftArm = new ModelRenderer(this, 16, 14);
		this.leftArm.mirror = true;
		this.leftArm.setPos(5.0F, -4.0F, 0.0F);
		this.leftArm.addBox(0.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
		this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.4363323129985824F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setPos(0.0F, 19.0F, 0.0F);
		this.body.addBox(-5.0F, -10.0F, -2.0F, 10, 10, 4, 0.0F);

		this.rightArm = new ModelRenderer(this, 16, 14);
		this.rightArm.setPos(-5.0F, -4.0F, 0.0F);
		this.rightArm.addBox(-1.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
		this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.4363323129985824F);
		this.fin = new ModelRenderer(this, 24, 12);
		this.fin.setPos(0.0F, -4.0F, 2.0F);
		this.fin.addBox(0.0F, -1.0F, 0.0F, 0, 4, 4, 0.0F);
		this.setRotateAngle(fin, -0.2181661564992912F, 0.0F, 0.0F);
		this.backpack = new ModelRenderer(this, 8, 20);
		this.backpack.setPos(0.0F, 0.0F, 0.0F);
		this.backpack.addBox(-4.0F, -8.0F, 2.0F, 8, 8, 4, 0.0F);

		this.leftLeg = new ModelRenderer(this, 0, 14);
		this.leftLeg.mirror = true;
		this.leftLeg.setPos(2.5F, 19.0F, 1.0F);
		this.leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
		this.rightLeg = new ModelRenderer(this, 0, 14);
		this.rightLeg.setPos(-2.5F, 19.0F, 1.0F);
		this.rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);

		this.body.addChild(this.leftArm);
		this.body.addChild(this.rightArm);
		this.body.addChild(this.fin);
		this.body.addChild(this.backpack);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
		this.partialTicks = partialTicks;
	}

	@Override
	public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.fin.visible = !entityIn.hasBackpack();
		this.body.zRot = 0.1F * MathHelper.sin(limbSwing * 0.5F) * 1.25F * limbSwingAmount;

		if (!entityIn.isInWater()) {
			if (entityIn.isPartying()) {
				float f = MathHelper.cos((float) entityIn.tickCount + partialTicks);
				float f1 = MathHelper.sin((float) entityIn.tickCount + partialTicks);
				this.body.z = f;
				this.body.y = 19.75F - f1;
			}

			this.rightArm.zRot = ageInTicks;
			this.leftArm.zRot = -ageInTicks;
			this.rightLeg.xRot = (entityIn.isInSittingPose() || entityIn.getVehicle() != null) ? -1.57F : MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
			this.leftLeg.xRot = (entityIn.isInSittingPose() || entityIn.getVehicle() != null) ? -1.57F : MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.5F * limbSwingAmount;

		} else {
			this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.7F * limbSwingAmount;
			this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.7F * limbSwingAmount;
			this.rightArm.zRot = ageInTicks;
			this.leftArm.zRot = -ageInTicks;
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(matrixStack, this.sprite.wrap(buffer), packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}
}
