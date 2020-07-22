package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.client.model.SlabfishBucketModel;
import com.team_abnormals.environmental.client.particle.KilnSmokeParticle;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientRegistry
{

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        if (checkForNonNullWithReflectionCauseForgeIsBaby(EnvironmentalParticles.KILN_SMOKE))
        {
            particleManager.registerFactory(EnvironmentalParticles.KILN_SMOKE.get(), KilnSmokeParticle.KilnSmokeFactory::new);
        }
    }

    @SubscribeEvent
    public static void onEvent(ModelRegistryEvent event)
    {
        for (ResourceLocation location : Minecraft.getInstance().getResourceManager().getAllResourceLocations("models/item/slabfish_bucket", s -> s.endsWith(".json")))
            ModelLoader.addSpecialModel(new ResourceLocation(location.getNamespace(), location.getPath().substring("models/".length(), location.getPath().length() - ".json".length())));
    }

    @SubscribeEvent
    public static void onEvent(ModelBakeEvent event)
    {
        event.getModelRegistry().put(new ModelResourceLocation(EnvironmentalItems.SLABFISH_BUCKET.getId(), "inventory"), new SlabfishBucketModel(event.getModelManager()));
    }

    private static boolean checkForNonNullWithReflectionCauseForgeIsBaby(RegistryObject<BasicParticleType> registryObject)
    {
        return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, registryObject, "value") != null;
    }
}
