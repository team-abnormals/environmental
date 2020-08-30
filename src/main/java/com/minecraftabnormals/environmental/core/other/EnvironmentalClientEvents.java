package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientEvents {
    
    @SubscribeEvent
    public static void onEvent(TextureStitchEvent.Pre event) {
        AtlasTexture texture = event.getMap();
        if (PlayerContainer.LOCATION_BLOCKS_TEXTURE.equals(texture.getTextureLocation())) {
            event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_sweater_slot"));
            event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_backpack_slot"));
            event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_backpack_type_slot"));
        }
    }
}
