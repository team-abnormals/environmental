package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.client.model.SlabfishBucketModel;
import com.minecraftabnormals.environmental.client.particle.*;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleManager.IParticleMetaFactory;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientRegistry {

	@SubscribeEvent
	public static void registerParticleFactorys(ParticleFactoryRegisterEvent event) {
		ParticleManager manager = Minecraft.getInstance().particleEngine;
		registerParticleFactory(manager, EnvironmentalParticles.KILN_SMOKE, KilnSmokeParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticles.CHERRY_BLOSSOM, CherryBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticles.RED_LOTUS_BLOSSOM, LotusBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticles.WHITE_LOTUS_BLOSSOM, LotusBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticles.PIG_FINDS_TRUFFLE, PigFindsTruffleParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticles.SLABFISH_FINDS_EFFIGY, SlabfishEffigyParticle.Factory::new); // TODO should probably give this it's own renderer
	}

	@SubscribeEvent
	public static void onEvent(ModelRegistryEvent event) {
		for (ResourceLocation location : Minecraft.getInstance().getResourceManager().listResources("models/item/slabfish_bucket", s -> s.endsWith(".json")))
			ModelLoader.addSpecialModel(new ResourceLocation(location.getNamespace(), location.getPath().substring("models/".length(), location.getPath().length() - ".json".length())));
	}

	@SubscribeEvent
	public static void onEvent(ModelBakeEvent event) {
		event.getModelRegistry().put(new ModelResourceLocation(EnvironmentalItems.SLABFISH_BUCKET.getId(), "inventory"), new SlabfishBucketModel(event.getModelManager()));
	}

	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, color) -> color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack), EnvironmentalItems.THIEF_HOOD.get(), EnvironmentalItems.HEALER_POUCH.get(), EnvironmentalItems.ARCHITECT_BELT.get(), EnvironmentalItems.WANDERER_BOOTS.get());
	}

	private static <T extends IParticleData> void registerParticleFactory(ParticleManager manager, RegistryObject<BasicParticleType> particleTypeIn, IParticleMetaFactory<BasicParticleType> particleMetaFactoryIn) {
		if (checkForNonNull(particleTypeIn)) manager.register(particleTypeIn.get(), particleMetaFactoryIn);
	}

	private static boolean checkForNonNull(RegistryObject<BasicParticleType> registryObject) {
		return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, registryObject, "value") != null;
	}
}
