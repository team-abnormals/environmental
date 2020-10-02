package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.KoiModel;
import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<KoiEntity, KoiModel<KoiEntity>> {
    
    public KoiRenderer(EntityRendererManager manager) {
        super(manager, new KoiModel<KoiEntity>(), 0);
    }
    
    @Override
    public ResourceLocation getEntityTexture(KoiEntity entity) {
        return new ResourceLocation(Environmental.MODID, "textures/entity/koi.png");
    }
}
