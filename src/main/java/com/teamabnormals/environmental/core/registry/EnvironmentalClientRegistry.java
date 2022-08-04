package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.client.model.SlabfishBucketModel;
import com.teamabnormals.environmental.client.particle.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientRegistry {

	@SubscribeEvent
	public static void registerParticleFactorys(ParticleFactoryRegisterEvent event) {
		ParticleEngine manager = Minecraft.getInstance().particleEngine;
		registerParticleFactory(manager, EnvironmentalParticleTypes.CHERRY_BLOSSOM, CherryBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM, LotusBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM, LotusBlossomParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE, PigFindsTruffleParticle.Factory::new);
		registerParticleFactory(manager, EnvironmentalParticleTypes.SLABFISH_FINDS_EFFIGY, SlabfishEffigyParticle.Factory::new); // TODO should probably give this it's own renderer
	}

	@SubscribeEvent
	public static void onEvent(ModelRegistryEvent event) {
		for (ResourceLocation location : Minecraft.getInstance().getResourceManager().listResources("models/item/slabfish_bucket", s -> s.endsWith(".json")))
			ForgeModelBakery.addSpecialModel(new ResourceLocation(location.getNamespace(), location.getPath().substring("models/".length(), location.getPath().length() - ".json".length())));
	}

	@SubscribeEvent
	public static void onEvent(ModelBakeEvent event) {
		event.getModelRegistry().put(new ModelResourceLocation(EnvironmentalItems.SLABFISH_BUCKET.getId(), "inventory"), new SlabfishBucketModel(event.getModelManager()));
	}

	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, color) -> color > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), EnvironmentalItems.THIEF_HOOD.get(), EnvironmentalItems.HEALER_POUCH.get(), EnvironmentalItems.ARCHITECT_BELT.get(), EnvironmentalItems.WANDERER_BOOTS.get());
	}

	private static <T extends ParticleOptions> void registerParticleFactory(ParticleEngine manager, RegistryObject<SimpleParticleType> particleTypeIn, SpriteParticleRegistration<SimpleParticleType> particleMetaFactoryIn) {
		if (checkForNonNull(particleTypeIn)) manager.register(particleTypeIn.get(), particleMetaFactoryIn);
	}

	private static boolean checkForNonNull(RegistryObject<SimpleParticleType> registryObject) {
		return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, registryObject, "value") != null;
	}
}
