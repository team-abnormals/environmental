package com.teamabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ZebraModel<T extends AbstractHorse> extends AgeableListModel<T> {
	protected final ModelPart body;
	protected final ModelPart headParts;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindBabyLeg;
	private final ModelPart leftHindBabyLeg;
	private final ModelPart rightFrontBabyLeg;
	private final ModelPart leftFrontBabyLeg;
	private final ModelPart tail;
	private final ModelPart[] saddleParts;
	private final ModelPart[] ridingParts;

	public ZebraModel(ModelPart part) {
		super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
		this.body = part.getChild("body");
		this.headParts = part.getChild("head_parts");
		this.rightHindLeg = part.getChild("right_hind_leg");
		this.leftHindLeg = part.getChild("left_hind_leg");
		this.rightFrontLeg = part.getChild("right_front_leg");
		this.leftFrontLeg = part.getChild("left_front_leg");
		this.rightHindBabyLeg = part.getChild("right_hind_baby_leg");
		this.leftHindBabyLeg = part.getChild("left_hind_baby_leg");
		this.rightFrontBabyLeg = part.getChild("right_front_baby_leg");
		this.leftFrontBabyLeg = part.getChild("left_front_baby_leg");
		this.tail = this.body.getChild("tail");
		ModelPart modelpart = this.body.getChild("saddle");
		ModelPart modelpart1 = this.headParts.getChild("left_saddle_mouth");
		ModelPart modelpart2 = this.headParts.getChild("right_saddle_mouth");
		ModelPart modelpart3 = this.headParts.getChild("left_saddle_line");
		ModelPart modelpart4 = this.headParts.getChild("right_saddle_line");
		ModelPart modelpart5 = this.headParts.getChild("head_saddle");
		ModelPart modelpart6 = this.headParts.getChild("mouth_saddle_wrap");
		this.saddleParts = new ModelPart[]{modelpart, modelpart1, modelpart2, modelpart5, modelpart6};
		this.ridingParts = new ModelPart[]{modelpart3, modelpart4};
	}

	public static MeshDefinition createBodyMesh(CubeDeformation deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 35).addBox(-4.5F, -6.0F, -17.0F, 9.0F, 9.0F, 20.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 10.0F, 5.5F));
		PartDefinition headParts = partdefinition.addOrReplaceChild("head_parts", CubeListBuilder.create().texOffs(0, 38).addBox(-2.05F, -6.0F, -3.0F, 4.0F, 11.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 6.0F, -12.0F, (Mth.PI / 6F), 0.0F, 0.0F));
		PartDefinition head = headParts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -11.0F, -4.0F, 6.0F, 5.0F, 7.0F, deformation), PartPose.ZERO);
		headParts.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(50, 36).addBox(-1.0F, -11.0F, 3.01F, 2.0F, 15.0F, 3.0F, deformation), PartPose.ZERO);
		headParts.addOrReplaceChild("upper_mouth", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, -11.0F, -9.0F, 4.0F, 5.0F, 5.0F, deformation), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(48, 23).mirror().addBox(-2.5F, -0.01F, -1.5F, 3.0F, 10.0F, 3.0F, deformation), PartPose.offset(4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(48, 23).addBox(-0.5F, -0.01F, -1.5F, 3.0F, 10.0F, 3.0F, deformation), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 23).mirror().addBox(-2.5F, -0.01F, -1.4F, 3.0F, 10.0F, 3.0F, deformation), PartPose.offset(4.0F, 14.0F, -10.0F));
		partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(48, 23).addBox(-0.5F, -0.01F, -1.4F, 3.0F, 10.0F, 3.0F, deformation), PartPose.offset(-4.0F, 14.0F, -10.0F));
		CubeDeformation cubedeformation = deformation.extend(0.0F, 5.0F, 0.0F);
		partdefinition.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(48, 23).mirror().addBox(-2.5F, -0.01F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offset(4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(48, 23).addBox(-0.5F, -0.01F, -2.0F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(48, 23).mirror().addBox(-2.5F, -0.01F, 0.1F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offset(4.0F, 14.0F, -12.0F));
		partdefinition.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(48, 23).addBox(-0.5F, -0.01F, 0.1F, 3.0F, 10.0F, 3.0F, cubedeformation), PartPose.offset(-4.0F, 14.0F, -12.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(38, 38).addBox(-1.5F, -1.0F, 0.5F, 3.0F, 14.0F, 3.0F, deformation), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, (Mth.PI / 6F), 0.0F, 0.0F));
		body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-4.5F, -6.0F, -9.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
		headParts.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -10.0F, -7.0F, 1.0F, 2.0F, 2.0F, deformation), PartPose.ZERO);
		headParts.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -10.0F, -7.0F, 1.0F, 2.0F, 2.0F, deformation), PartPose.ZERO);
		headParts.addOrReplaceChild("left_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -6.5F, -9.5F, 0.0F, 3.0F, 16.0F, deformation), PartPose.rotation((-Mth.PI / 6F), 0.0F, 0.0F));
		headParts.addOrReplaceChild("right_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -6.5F, -9.5F, 0.0F, 3.0F, 16.0F, deformation), PartPose.rotation((-Mth.PI / 6F), 0.0F, 0.0F));
		headParts.addOrReplaceChild("head_saddle", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -11.0F, -3.9F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
		headParts.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -11.0F, -6.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
		head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(19, 19).addBox(0.55F, -14.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);
		head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 19).addBox(-2.55F, -14.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);
		return meshdefinition;
	}

	@Override
	public void setupAnim(T zebra, float p_102786_, float p_102787_, float p_102788_, float p_102789_, float p_102790_) {
		boolean isSaddled = zebra.isSaddled();
		boolean isVehicle = zebra.isVehicle();

		for (ModelPart modelpart : this.saddleParts) {
			modelpart.visible = isSaddled;
		}

		for (ModelPart modelpart1 : this.ridingParts) {
			modelpart1.visible = isVehicle && isSaddled;
		}

		this.body.y = 11.0F;
	}

	@Override
	public Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.headParts);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightHindBabyLeg, this.leftHindBabyLeg, this.rightFrontBabyLeg, this.leftFrontBabyLeg);
	}

	@Override
	public void prepareMobModel(T zebra, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(zebra, limbSwing, limbSwingAmount, partialTick);
		float bodyrot = Mth.rotlerp(zebra.yBodyRotO, zebra.yBodyRot, partialTick);
		float headrot = Mth.rotlerp(zebra.yHeadRotO, zebra.yHeadRot, partialTick);
		float deltarot = headrot - bodyrot;
		float headxrot = Mth.lerp(partialTick, zebra.xRotO, zebra.getXRot()) * Mth.DEG_TO_RAD;

		if (deltarot > 20.0F) {
			deltarot = 20.0F;
		}

		if (deltarot < -20.0F) {
			deltarot = -20.0F;
		}

		if (limbSwingAmount > 0.2F) {
			headxrot += Mth.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
		}

		float eatanim = zebra.getEatAnim(partialTick);
		float standanim = zebra.getStandAnim(partialTick);
		float nostandanim = 1.0F - standanim;
		float mouthanim = zebra.getMouthAnim(partialTick);
		boolean movingtail = zebra.tailCounter != 0;
		float ageinticks = (float) zebra.tickCount + partialTick;

		this.headParts.y = 6.0F;
		this.headParts.z = -10.5F;
		this.body.xRot = 0.0F;
		this.headParts.xRot = (Mth.PI / 6F) + headxrot;
		this.headParts.yRot = deltarot * Mth.DEG_TO_RAD;

		float waterlegswing = zebra.isInWater() ? 0.2F : 1.0F;
		float hindlegswing = Mth.cos(waterlegswing * limbSwing * 0.6662F + Mth.PI);
		float frontlegswing = hindlegswing * 0.8F * limbSwingAmount;
		float headangle = (1.0F - Math.max(standanim, eatanim)) * ((Mth.PI / 6F) + headxrot + mouthanim * Mth.sin(ageinticks) * 0.05F);

		this.headParts.xRot = standanim * (0.2617994F + headxrot) + eatanim * (2.1816616F + Mth.sin(ageinticks) * 0.05F) + headangle;
		this.headParts.yRot = standanim * deltarot * Mth.DEG_TO_RAD + (1.0F - Math.max(standanim, eatanim)) * this.headParts.yRot;
		this.headParts.y = standanim * -4.0F + eatanim * 11.0F + (1.0F - Math.max(standanim, eatanim)) * this.headParts.y;
		this.headParts.z = standanim * -4.0F + eatanim * -12.0F + (1.0F - Math.max(standanim, eatanim)) * this.headParts.z;
		this.body.xRot = standanim * (-Mth.PI / 4F) + nostandanim * this.body.xRot;

		float standanim1 = 0.2617994F * standanim;
		float standswing = Mth.cos(ageinticks * 0.6F + Mth.PI);

		this.leftFrontLeg.y = 2.0F * standanim + 14.0F * nostandanim;
		this.leftFrontLeg.z = -6.0F * standanim - 10.0F * nostandanim;
		this.rightFrontLeg.y = this.leftFrontLeg.y;
		this.rightFrontLeg.z = this.leftFrontLeg.z;

		float leftfrontlegangle = ((-Mth.PI / 3F) + standswing) * standanim + frontlegswing * nostandanim;
		float rightfrontlegangle = ((-Mth.PI / 3F) - standswing) * standanim - frontlegswing * nostandanim;

		this.leftHindLeg.xRot = standanim1 - hindlegswing * 0.5F * limbSwingAmount * nostandanim;
		this.rightHindLeg.xRot = standanim1 + hindlegswing * 0.5F * limbSwingAmount * nostandanim;
		this.leftFrontLeg.xRot = leftfrontlegangle;
		this.rightFrontLeg.xRot = rightfrontlegangle;
		this.tail.xRot = (Mth.PI / 6F) + limbSwingAmount * 0.75F;
		this.tail.y = -2.5F + limbSwingAmount;
		this.tail.z = 1.0F + limbSwingAmount * 2.0F;
		
		if (movingtail) {
			this.tail.yRot = Mth.cos(ageinticks * 0.7F);
		} else {
			this.tail.yRot = 0.0F;
		}

		this.rightHindBabyLeg.y = this.rightHindLeg.y;
		this.rightHindBabyLeg.z = this.rightHindLeg.z;
		this.rightHindBabyLeg.xRot = this.rightHindLeg.xRot;
		this.leftHindBabyLeg.y = this.leftHindLeg.y;
		this.leftHindBabyLeg.z = this.leftHindLeg.z;
		this.leftHindBabyLeg.xRot = this.leftHindLeg.xRot;
		this.rightFrontBabyLeg.y = this.rightFrontLeg.y;
		this.rightFrontBabyLeg.z = this.rightFrontLeg.z;
		this.rightFrontBabyLeg.xRot = this.rightFrontLeg.xRot;
		this.leftFrontBabyLeg.y = this.leftFrontLeg.y;
		this.leftFrontBabyLeg.z = this.leftFrontLeg.z;
		this.leftFrontBabyLeg.xRot = this.leftFrontLeg.xRot;
		
		boolean isbaby = zebra.isBaby();
		
		this.rightHindLeg.visible = !isbaby;
		this.leftHindLeg.visible = !isbaby;
		this.rightFrontLeg.visible = !isbaby;
		this.leftFrontLeg.visible = !isbaby;
		this.rightHindBabyLeg.visible = isbaby;
		this.leftHindBabyLeg.visible = isbaby;
		this.rightFrontBabyLeg.visible = isbaby;
		this.leftFrontBabyLeg.visible = isbaby;
		this.body.y = isbaby ? 10.8F : 0.0F;
	}
}