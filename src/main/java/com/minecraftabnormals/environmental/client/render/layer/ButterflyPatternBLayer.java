package com.minecraftabnormals.environmental.client.render.layer;
//package com.pugz.environmental.client.render.layer;
//
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.pugz.environmental.common.entity.ButterflyEntity;
//import net.minecraft.client.renderer.entity.IEntityRenderer;
//import net.minecraft.client.renderer.entity.layers.LayerRenderer;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.awt.*;
//
//@OnlyIn(Dist.CLIENT)
//public class ButterflyPatternBLayer<T extends ButterflyEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
//    public ButterflyPatternBLayer(IEntityRenderer<T, M> renderer) {
//        super(renderer);
//    }
//
//    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
//        if (!entity.isInvisible()) {
//            bindTexture(entity.getPatternTextureB());
//            Color colorB = entity.getPatternColorB();
//            GlStateManager.color3f(colorB.getRed(), colorB.getGreen(), colorB.getBlue());
//            getEntityModel().setModelAttributes(getEntityModel());
//            (getEntityModel()).setLivingAnimations(entity, f, f1, f2);
//            (getEntityModel()).render(entity, f, f1, f3, f4, f5, f6);
//        }
//    }
//
//    public boolean shouldCombineTextures() {
//        return true;
//    }
//}