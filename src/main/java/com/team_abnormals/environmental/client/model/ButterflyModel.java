package com.team_abnormals.environmental.client.model;
//package com.pugz.environmental.client.model;
//
//import com.pugz.environmental.common.entity.ButterflyEntity;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.client.renderer.entity.model.RendererModel;
//import net.minecraft.util.math.MathHelper;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class ButterflyModel extends EntityModel<ButterflyEntity> {
//    public RendererModel body;
//    public RendererModel wing1;
//    public RendererModel wing2;
//
//    public ButterflyModel() {
//        textureWidth = 32;
//        textureHeight = 32;
//        body = new RendererModel(this, 0, 0);
//        body.setRotationPoint(0.0F, 10.0F, -4.0F);
//        body.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
//        wing2 = new RendererModel(this, 7, 0);
//        wing2.setRotationPoint(0.0F, 10.0F, -6.0F);
//        wing2.addBox(-6.0F, 0.0F, 0.0F, 6, 0, 12, 0.0F);
//        wing1 = new RendererModel(this, 7, 0);
//        wing1.mirror = true;
//        wing1.setRotationPoint(1.0F, 10.0F, -6.0F);
//        wing1.addBox(0.0F, 0.0F, 0.0F, 6, 0, 12, 0.0F);
//    }
//
//    @Override
//    public void render(ButterflyEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
//        body.render(f5);
//        wing2.render(f5);
//        wing1.render(f5);
//        setRotationAngles(f2);
//    }
//
//    public void setRotationAngles(float f2) {
//        wing1.rotateAngleZ =-MathHelper.cos(f2 * 1.3F) * 3.1415927F * 0.25F;
//        wing2.rotateAngleZ = -wing1.rotateAngleZ;
//    }
//}