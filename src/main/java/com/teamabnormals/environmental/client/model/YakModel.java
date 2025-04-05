package com.teamabnormals.environmental.client.model;

import com.teamabnormals.environmental.common.entity.animal.Yak;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * YakModel - Hatsondogs
 * Created using Blockbench
 */
@OnlyIn(Dist.CLIENT)
public class YakModel<E extends Yak> extends QuadrupedModel<E> {
	private static final float RELATIVE_HEAD_Y = 8.0F;
	private static final float ADULT_EAT_OFFSET_MULTIPLIER = 9.0F;
	private static final float CHILD_EAT_OFFSET_MULTIPLIER = 3.0F;

	public ModelPart coat;

	public YakModel(ModelPart root) {
		super(root, false, 8.0F, 5.0F, 2.0F, 2.0F, 24);
		this.coat = root.getChild("body").getChild("coat");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 8.0F, -12.0F));
		head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 18).addBox(4.0F, -15.0F, -13.0F, 6.0F, 3.0F, 3.0F).texOffs(0, 24).addBox(8.0F, -19.0F, -12.0F, 2.0F, 4.0F, 2.0F).texOffs(0, 32).addBox(6.0F, -19.0F, -12.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 8.0F));
		head.addOrReplaceChild("right_horn", CubeListBuilder.create().mirror().texOffs(0, 18).addBox(-10.0F, -15.0F, -13.0F, 6.0F, 3.0F, 3.0F).texOffs(0, 24).addBox(-10.0F, -19.0F, -12.0F, 2.0F, 4.0F, 2.0F).texOffs(0, 32).addBox(-8.0F, -19.0F, -12.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 12.0F, 8.0F));
		head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(32, 12).addBox(-5.0F, -4.0F, -1.0F, 6.0F, 4.0F, 2.0F), PartPose.offset(2.0F, 5.0F, -7.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(60, 0).addBox(-7.0F, -16.0F, -6.0F, 14.0F, 19.0F, 20.0F).texOffs(80, 44).addBox(-7.0F, -2.0F, -6.0F, 14.0F, 0.0F, 20.0F), PartPose.offset(0.0F, 19.0F, -4.0F));
		body.addOrReplaceChild("coat", CubeListBuilder.create().texOffs(0, 18).addBox(-9.0F, -17.0F, -8.0F, 18.0F, 17.0F, 24.0F), PartPose.ZERO);

		root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().mirror().texOffs(48, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F), PartPose.offset(-4.0F, 17.0F, 7.0F));
		root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F), PartPose.offset(4.0F, 17.0F, 7.0F));
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().mirror().texOffs(48, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F), PartPose.offset(-4.0F, 17.0F, -7.0F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F), PartPose.offset(4.0F, 17.0F, -7.0F));

		return LayerDefinition.create(mesh, 128, 64);
	}

	@Override
	public void prepareMobModel(E yak, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(yak, limbSwing, limbSwingAmount, partialTicks);
		this.coat.visible = !yak.isSheared();

	}

	@Override
	public void setupAnim(E yak, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch) {
		super.setupAnim(yak, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch);
		float partialTicks = ageInTicks - yak.tickCount;
		this.head.y = RELATIVE_HEAD_Y + yak.getHeadEatingOffset(partialTicks) * (yak.isBaby() ? CHILD_EAT_OFFSET_MULTIPLIER : ADULT_EAT_OFFSET_MULTIPLIER);
		this.head.xRot = yak.getHeadPitch(partialTicks);
	}
}