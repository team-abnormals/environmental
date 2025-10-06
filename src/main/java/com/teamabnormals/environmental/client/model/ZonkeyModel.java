package com.teamabnormals.environmental.client.model;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zonkey;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZonkeyModel<T extends Zonkey> extends ZebraModel<T> {
	private final ModelPart leftChest = this.body.getChild("left_chest");
	private final ModelPart rightChest = this.body.getChild("right_chest");

	public ZonkeyModel(ModelPart part) {
		super(part);
		this.isHybrid = true;
	}

	public static MeshDefinition createBodyMesh(CubeDeformation deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 11.0F, 5.0F));
		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(26, 21).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
		partdefinition1.addOrReplaceChild("left_chest", cubelistbuilder, PartPose.offsetAndRotation(6.0F, -8.0F, 0.0F, 0.0F, (-(float) Math.PI / 2F), 0.0F));
		partdefinition1.addOrReplaceChild("right_chest", cubelistbuilder, PartPose.offsetAndRotation(-6.0F, -8.0F, 0.0F, 0.0F, ((float) Math.PI / 2F), 0.0F));
		PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("head_parts", CubeListBuilder.create().texOffs(0, 35).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 4.0F, -12.0F, ((float) Math.PI / 6F), 0.0F, 0.0F));
		PartDefinition partdefinition3 = partdefinition2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, deformation), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, deformation), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("upper_mouth", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, deformation), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, deformation), PartPose.offset(4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, deformation), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, deformation), PartPose.offset(4.0F, 14.0F, -12.0F));
		partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, deformation), PartPose.offset(-4.0F, 14.0F, -12.0F));
		CubeDeformation cubedeformation = deformation.extend(0.0F, 5.5F, 0.0F);
		partdefinition.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.offset(4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partdefinition.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.offset(4.0F, 14.0F, -12.0F));
		partdefinition.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.offset(-4.0F, 14.0F, -12.0F));
		partdefinition1.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, deformation), PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, ((float) Math.PI / 6F), 0.0F, 0.0F));
		partdefinition1.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, deformation), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, deformation), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("left_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), PartPose.rotation((-(float) Math.PI / 6F), 0.0F, 0.0F));
		partdefinition2.addOrReplaceChild("right_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), PartPose.rotation((-(float) Math.PI / 6F), 0.0F, 0.0F));
		partdefinition2.addOrReplaceChild("head_saddle", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.22F)), PartPose.ZERO);
		partdefinition2.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
		CubeListBuilder cubelistbuilder1 = CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
		partdefinition3.addOrReplaceChild("left_ear", cubelistbuilder1, PartPose.offsetAndRotation(1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, 0.2617994F));
		partdefinition3.addOrReplaceChild("right_ear", cubelistbuilder1, PartPose.offsetAndRotation(-1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, -0.2617994F));
		return meshdefinition;
	}

	@Override
	public void setupAnim(T zonkey, float p_102367_, float p_102368_, float p_102369_, float p_102370_, float p_102371_) {
		super.setupAnim(zonkey, p_102367_, p_102368_, p_102369_, p_102370_, p_102371_);
		if (zonkey.hasChest()) {
			this.leftChest.visible = true;
			this.rightChest.visible = true;
		} else {
			this.leftChest.visible = false;
			this.rightChest.visible = false;
		}
	}
}