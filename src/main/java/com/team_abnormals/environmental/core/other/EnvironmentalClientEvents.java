package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.common.block.fluid.MudFluid;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.fluid.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT)
public class EnvironmentalClientEvents {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onFogColor(FogColors event) {
        ActiveRenderInfo info = event.getInfo();
        FluidState state = info.getFluidState();
        if (state.getFluid() instanceof MudFluid) {
            event.setRed(0.140625F);
            event.setGreen(0.0625F);
            event.setBlue(0.015625F);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onFogDensity(FogDensity event) {
        ActiveRenderInfo info = event.getInfo();
        FluidState state = info.getFluidState();
        if (state.getFluid() instanceof MudFluid) {
            event.setDensity(1.0F);
            event.setCanceled(true);
        }
    }
}
