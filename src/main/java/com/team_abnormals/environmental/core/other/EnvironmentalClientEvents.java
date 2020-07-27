package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientEvents {

//    @SubscribeEvent
//    public static void onFogColor(FogColors event) {
//        ActiveRenderInfo info = event.getInfo();
//        FluidState state = info.getFluidState();
//        if (state.getFluid() instanceof MudFluid) {
//            event.setRed(0.140625F);
//            event.setGreen(0.0625F);
//            event.setBlue(0.015625F);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onFogDensity(FogDensity event) {
//        ActiveRenderInfo info = event.getInfo();
//        FluidState state = info.getFluidState();
//        if (state.getFluid() instanceof MudFluid) {
//            event.setDensity(1.0F);
//            event.setCanceled(true);
//        }
//    }

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
