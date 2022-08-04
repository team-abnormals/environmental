package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * HealerPouchModel - Farcr
 */
@OnlyIn(Dist.CLIENT)
public class HealerPouchModel extends HumanoidModel<LivingEntity> {
	public static final HealerPouchModel INSTANCE = new HealerPouchModel(createBodyLayer().bakeRoot());

	private final ModelPart straps;
	private final ModelPart pouch;

	public HealerPouchModel(ModelPart root) {
		super(root);
		this.straps = root.getChild("straps");
		this.pouch = this.straps.getChild("pouch");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition straps = root.addOrReplaceChild("straps", CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 9.0F, 5.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition pouch = straps.addOrReplaceChild("pouch", CubeListBuilder.create().texOffs(0, 3).addBox(-2.5F, -22.5F, 3.5F, 7.0F, 9.0F, 4.0F, false), PartPose.offsetAndRotation(-1.0F, 23.0F, -1.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.straps.copyFrom(this.body);
		matrixStackIn.pushPose();
		matrixStackIn.scale(1.01F, 1.01F, 1.02F);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.straps);
	}
}