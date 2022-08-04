package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelSlabfish - Farcr
 * Created using Tabula 7.1.0
 *
 * @param <E>
 */
@OnlyIn(Dist.CLIENT)
public class SlabfishModel<E extends Slabfish> extends AgeableListModel<E> {

	public ModelPart body;
	public ModelPart rightLeg;
	public ModelPart leftLeg;
	public ModelPart rightArm;
	public ModelPart leftArm;
	public ModelPart fin;
	public ModelPart backpack;
	public TextureAtlasSprite sprite;
	private float partialTicks;

	public SlabfishModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leftArm = this.body.getChild("leftArm");
		this.rightArm = this.body.getChild("rightArm");
		this.fin = this.body.getChild("fin");
		this.backpack = this.body.getChild("backpack");
		this.rightLeg = root.getChild("rightLeg");
		this.leftLeg = root.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -2.0F, 10.0F, 10.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, true), PartPose.offsetAndRotation(5.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.43633232F));
		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, false), PartPose.offsetAndRotation(-5.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.43633232F));
		PartDefinition fin = body.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(24, 12).addBox(0.0F, -1.0F, 0.0F, 0.0F, 4.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -4.0F, 2.0F, -0.21816616F, 0.0F, 0.0F));
		PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(8, 20).addBox(-4.0F, -8.0F, 2.0F, 8.0F, 8.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, false), PartPose.offsetAndRotation(-2.5F, 19.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, true), PartPose.offsetAndRotation(2.5F, 19.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
		this.partialTicks = partialTicks;
	}

	@Override
	public void setupAnim(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.fin.visible = !entityIn.hasBackpack();
		this.body.zRot = 0.1F * Mth.sin(limbSwing * 0.5F) * 1.25F * limbSwingAmount;

		if (!entityIn.isInWater()) {
			if (entityIn.isPartying()) {
				float f = Mth.cos((float) entityIn.tickCount + partialTicks);
				float f1 = Mth.sin((float) entityIn.tickCount + partialTicks);
				this.body.z = f;
				this.body.y = 19.75F - f1;
			}

			this.rightArm.zRot = ageInTicks;
			this.leftArm.zRot = -ageInTicks;
			this.rightLeg.xRot = (entityIn.isInSittingPose() || entityIn.getVehicle() != null) ? -1.57F : Mth.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
			this.leftLeg.xRot = (entityIn.isInSittingPose() || entityIn.getVehicle() != null) ? -1.57F : Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.5F * limbSwingAmount;

		} else {
			this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.7F * limbSwingAmount;
			this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.7F * limbSwingAmount;
			this.rightArm.zRot = ageInTicks;
			this.leftArm.zRot = -ageInTicks;
		}
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(matrixStack, this.sprite.wrap(buffer), packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}
}
