package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.YakModel;
import com.minecraftabnormals.environmental.common.entity.YakEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YakRenderer extends MobRenderer<YakEntity, YakModel<YakEntity>> {
    public YakRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new YakModel<>(), 0.8F);
    }

    @Override
    public ResourceLocation getEntityTexture(YakEntity entity) {
        if(entity.getSheared())
            return new ResourceLocation(Environmental.MODID, "textures/entity/yak/yak_sheared.png");
        return new ResourceLocation(Environmental.MODID, "textures/entity/yak/yak.png");
    }
}