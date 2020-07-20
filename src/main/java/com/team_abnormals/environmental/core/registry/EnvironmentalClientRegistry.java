package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.client.particle.KilnSmokeParticle;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientRegistry {

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        if(checkForNonNullWithReflectionCauseForgeIsBaby(EnvironmentalParticles.KILN_SMOKE)) {
            particleManager.registerFactory(EnvironmentalParticles.KILN_SMOKE.get(), KilnSmokeParticle.KilnSmokeFactory::new);
        }
    }

	private static boolean checkForNonNullWithReflectionCauseForgeIsBaby(RegistryObject<BasicParticleType> registryObject) {
		return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, registryObject, "value") != null;
	}
}
