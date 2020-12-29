package com.minecraftabnormals.environmental.core;

import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.minecraftabnormals.environmental.common.network.message.*;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishLoader;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.minecraftabnormals.environmental.core.other.EnvironmentalCompat;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.minecraftabnormals.environmental.core.registry.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Optional;

@Mod(Environmental.MODID)
@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class Environmental {
	public static final String NETWORK_PROTOCOL = "1";
	public static final String MODID = "environmental";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

	public static final SimpleChannel PLAY = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "play"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public static final SimpleChannel LOGIN = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "login"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public Environmental() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		this.setupPlayMessages();
		this.setupLoginMessages();

		REGISTRY_HELPER.register(bus);
		EnvironmentalBlocks.PAINTINGS.register(bus);
		EnvironmentalFeatures.FEATURES.register(bus);
		EnvironmentalAttributes.ATTRIBUTES.register(bus);
		EnvironmentalEffects.EFFECTS.register(bus);
		EnvironmentalVillagers.POI_TYPES.register(bus);
		EnvironmentalVillagers.PROFESSIONS.register(bus);
		EnvironmentalContainers.CONTAINER_TYPES.register(bus);
		EnvironmentalRecipes.Serailizers.RECIPE_SERIALIZERS.register(bus);
		EnvironmentalParticles.PARTICLE_TYPES.register(bus);
		EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.register(bus);
		EnvironmentalDataSerializers.SERIALIZERS.register(bus);

		bus.addListener(this::setupCommon);
		bus.addListener(this::registerRegistries);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			bus.addListener(this::setupClient);
			bus.addListener(this::stitchTextures);
			bus.addListener(this::registerItemColors);
		});

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EnvironmentalCompat.registerCompat();
			EnvironmentalEntities.registerAttributes();
			EnvironmentalBiomes.addBiomeTypes();
			EnvironmentalBiomes.addBiomesToGeneration();
			EnvironmentalVillagers.registerVillagerTypes();
			EnvironmentalVillagers.registerPOIs();
		});
	}

	private void setupClient(final FMLClientSetupEvent event) {
		EnvironmentalEntities.registerRendering();
		EnvironmentalCompat.setRenderLayers();
		event.enqueueWork(() -> {
			EnvironmentalCompat.registerBlockColors();
			EnvironmentalContainers.registerScreenFactories();
		});
	}

	private void setupPlayMessages() {
		PLAY.registerMessage(0, SSyncSlabfishTypeMessage.class, SSyncSlabfishTypeMessage::serialize, SSyncSlabfishTypeMessage::deserialize, SSyncSlabfishTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(1, SSyncSweaterTypeMessage.class, SSyncSweaterTypeMessage::serialize, SSyncSweaterTypeMessage::deserialize, SSyncSweaterTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(2, SSyncBackpackTypeMessage.class, SSyncBackpackTypeMessage::serialize, SSyncBackpackTypeMessage::deserialize, SSyncBackpackTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(3, SOpenSlabfishInventoryMessage.class, SOpenSlabfishInventoryMessage::serialize, SOpenSlabfishInventoryMessage::deserialize, SOpenSlabfishInventoryMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}

	private void setupLoginMessages() {
		LOGIN.messageBuilder(CAcknowledgeEnvironmentalMessage.class, 99, NetworkDirection.LOGIN_TO_SERVER).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(CAcknowledgeEnvironmentalMessage::serialize).decoder(CAcknowledgeEnvironmentalMessage::deserialize).consumer(FMLHandshakeHandler.indexFirst(CAcknowledgeEnvironmentalMessage::handle)).add();
		LOGIN.messageBuilder(SSyncSlabfishTypeMessage.class, 0, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncSlabfishTypeMessage::serialize).decoder(SSyncSlabfishTypeMessage::deserialize).markAsLoginPacket().consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSlabfishTypeMessage.handleLogin(msg, ctx))).add();
		LOGIN.messageBuilder(SSyncSweaterTypeMessage.class, 1, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncSweaterTypeMessage::serialize).decoder(SSyncSweaterTypeMessage::deserialize).markAsLoginPacket().consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSweaterTypeMessage.handleLogin(msg, ctx))).add();
		LOGIN.messageBuilder(SSyncBackpackTypeMessage.class, 2, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncBackpackTypeMessage::serialize).decoder(SSyncBackpackTypeMessage::deserialize).markAsLoginPacket().consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncBackpackTypeMessage.handleLogin(msg, ctx))).add();
	}

	private void registerRegistries(RegistryEvent.NewRegistry event) {
		new RegistryBuilder<SlabfishCondition.Factory>().setName(new ResourceLocation(MODID, "slabfish_condition")).setType(SlabfishCondition.Factory.class).setDefaultKey(new ResourceLocation(MODID, "impossible")).create();
	}

	private void stitchTextures(TextureStitchEvent.Pre event) {
		AtlasTexture texture = event.getMap();
		if (PlayerContainer.LOCATION_BLOCKS_TEXTURE.equals(texture.getTextureLocation())) {
			event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_sweater_slot"));
			event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_backpack_slot"));
			event.addSprite(new ResourceLocation(Environmental.MODID, "item/slabfish_backpack_type_slot"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register(
				(stack, color) -> color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack),
				EnvironmentalItems.THIEF_HOOD.get(), EnvironmentalItems.HEALER_POUCH.get(), EnvironmentalItems.ARCHITECT_BELT.get(), EnvironmentalItems.WANDERER_BOOTS.get());
	}

	@SubscribeEvent
	public void onEvent(AddReloadListenerEvent event) {
		event.addListener(new SlabfishLoader());
	}
}