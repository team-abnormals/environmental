package com.minecraftabnormals.environmental.client.model;

import com.minecraftabnormals.environmental.common.entity.YakEntity;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * YakModel - Hatsondogs
 * Created using Blockbench
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class YakModel<E extends YakEntity> extends QuadrupedModel<E> {
	private static final float RELATIVE_HEAD_Y = 3.0F;
	private static final float ADULT_EAT_OFFSET_MULTIPLIER = 9.0F;
	private static final float CHILD_EAT_OFFSET_MULTIPLIER = 4.5F;

	public YakModel() {
		super(12, 0.0F, false, 10.0F, 5.5F, 2.0F, 2.0F, 24);
		texWidth = 128;
		texHeight = 128;

		this.body = new ModelRenderer(this);
		this.body.setPos(0.0F, 5.0F, 2.0F);
		this.body.texOffs(0, 42).addBox(-6.0F, -13.0F, -7.0F, 12.0F, 22.0F, 12.0F, 0.0F, false);
		this.body.texOffs(0, 0).addBox(-6.0F, -13.0F, -15.0F, 12.0F, 22.0F, 20.0F, 0.5F, false);

		this.head = new ModelRenderer(this);
		this.head.setPos(0.0F, 3.0F, -8.0F);
		this.head.texOffs(44, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, 0.0F, false);
		this.head.texOffs(72, 0).addBox(-4.0F, -4.0F, -9.0F, 8.0F, 8.0F, 6.0F, 0.5F, false);

		ModelRenderer leftHorn = new ModelRenderer(this);
		leftHorn.setPos(4.0F, -2.0F, -5.0F);
		this.head.addChild(leftHorn);
		leftHorn.texOffs(0, 16).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, 0.0F, true);
		leftHorn.texOffs(0, 42).addBox(3.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		ModelRenderer rightHorn = new ModelRenderer(this);
		rightHorn.setPos(-4.0F, -2.0F, -5.0F);
		this.head.addChild(rightHorn);
		rightHorn.texOffs(0, 16).addBox(-5.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, 0.0F, false);
		rightHorn.texOffs(0, 42).addBox(-5.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		ModelRenderer snout = new ModelRenderer(this);
		snout.setPos(0.0F, 0.0F, -6.0F);
		this.head.addChild(snout);
		snout.texOffs(36, 42).addBox(-3.0F, -0.7765F, -5.8978F, 6.0F, 4.0F, 3.0F, 0.0F, false);

		ModelRenderer leftEar = new ModelRenderer(this);
		leftEar.setPos(0.0F, 0.0F, 0.0F);
		this.head.addChild(leftEar);
		leftEar.texOffs(12, 0).addBox(4.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		ModelRenderer rightEar = new ModelRenderer(this);
		rightEar.setPos(0.0F, 0.0F, 0.0F);
		this.head.addChild(rightEar);
		rightEar.texOffs(12, 0).addBox(-7.0F, -2.0F, -8.0F, 3.0F, 3.0F, 1.0F, 0.0F, true);

		this.leg1 = new ModelRenderer(this);
		this.leg1.setPos(4.0F, 12.0F, 7.0F);
		this.leg1.texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		this.leg0 = new ModelRenderer(this);
		this.leg0.setPos(-4.0F, 12.0F, 7.0F);
		this.leg0.texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		this.leg3 = new ModelRenderer(this);
		this.leg3.setPos(4.0F, 12.0F, -7.0F);
		this.leg3.texOffs(0, 0).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		this.leg2 = new ModelRenderer(this);
		this.leg2.setPos(-4.0F, 12.0F, -7.0F);
		this.leg2.texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		--this.leg0.x;
		++this.leg1.x;
		this.leg0.z += 0.0F;
		this.leg1.z += 0.0F;
		--this.leg2.x;
		++this.leg3.x;
		--this.leg2.z;
		--this.leg3.z;
	}

	@Override
	public void setupAnim(E yak, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch) {
		super.setupAnim(yak, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch);
		float partialTicks = ageInTicks - yak.tickCount;
		this.head.y = RELATIVE_HEAD_Y + yak.getHeadEatingOffset(partialTicks) * (yak.isBaby() ? CHILD_EAT_OFFSET_MULTIPLIER : ADULT_EAT_OFFSET_MULTIPLIER);
		this.head.xRot = yak.getHeadPitch(partialTicks);
	}
}