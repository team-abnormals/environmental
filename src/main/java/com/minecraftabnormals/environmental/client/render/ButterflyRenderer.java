package com.minecraftabnormals.environmental.client.render;
//package com.pugz.environmental.client.render;
//
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.pugz.environmental.client.model.ButterflyModel;
//import com.pugz.environmental.client.render.layer.ButterflyPatternALayer;
//import com.pugz.environmental.client.render.layer.ButterflyPatternBLayer;
//import com.pugz.environmental.common.entity.ButterflyEntity;
//import net.minecraft.client.renderer.entity.EntityRendererManager;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.MathHelper;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class ButterflyRenderer extends MobRenderer<ButterflyEntity, EntityModel<ButterflyEntity>> {
//    private static final ResourceLocation BODY = new ResourceLocation("environmental", "textures/entity/butterfly/body.png");
//
//    public ButterflyRenderer(EntityRendererManager rendererManager) {
//        super(rendererManager, new ButterflyModel(),0.25F);
//        addLayer(new ButterflyPatternALayer<>(this));
//        addLayer(new ButterflyPatternBLayer<>(this));
//    }
//
//    public ResourceLocation getEntityTexture(ButterflyEntity entity) {
//        return BODY;
//    }
//
//    protected void applyRotations(ButterflyEntity entity, float f, float f1, float f2) {
//        GlStateManager.translatef(0.0F, MathHelper.cos(f * 0.3F) * 0.1F, 0.0F);
//        super.applyRotations(entity, f, f1, f2);
//    }
//}