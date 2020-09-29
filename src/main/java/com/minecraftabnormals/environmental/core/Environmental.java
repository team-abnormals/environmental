package com.minecraftabnormals.environmental.core;

import static com.teamabnormals.abnormals_core.core.AbnormalsCore.NETWORK_PROTOCOL;

import com.minecraftabnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.minecraftabnormals.environmental.client.gui.screen.inventory.SawmillScreen;
import com.minecraftabnormals.environmental.common.network.message.CAcknowledgeEnvironmentalMessage;
import com.minecraftabnormals.environmental.common.network.message.EnvironmentalLoginMessage;
import com.minecraftabnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishLoader;
import com.minecraftabnormals.environmental.core.other.EnvironmentalClient;
import com.minecraftabnormals.environmental.core.other.EnvironmentalCompat;
import com.minecraftabnormals.environmental.core.other.EnvironmentalData;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalContainerTypes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEnchantments;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalParticles;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalRecipes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalVillagers;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
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

@Mod(Environmental.MODID)
@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class Environmental
{
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

    public Environmental()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        this.setupPlayMessages();
        this.setupLoginMessages();

        REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredEntityRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredTileEntityRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredSoundRegister().register(modEventBus);

        EnvironmentalBlocks.PAINTINGS.register(modEventBus);
        EnvironmentalBiomes.BIOMES.register(modEventBus);
        EnvironmentalFeatures.FEATURES.register(modEventBus);
        
        EnvironmentalVillagers.POI_TYPES.register(modEventBus);
        EnvironmentalVillagers.PROFESSIONS.register(modEventBus);

        EnvironmentalContainerTypes.CONTAINER_TYPES.register(modEventBus);
        EnvironmentalRecipes.Serailizers.RECIPE_SERIALIZERS.register(modEventBus);

        EnvironmentalParticles.PARTICLE_TYPES.register(modEventBus);
        EnvironmentalEnchantments.ENCHANTMENTS.register(modEventBus);
        
        modEventBus.addListener(this::setupCommon);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> 
        {
            modEventBus.addListener(this::setupClient);
            modEventBus.addListener(this::stitchTextures);
            modEventBus.addListener(this::registerItemColors);
        });

        modEventBus.addListener((ModConfig.ModConfigEvent event) -> 
        {
			if (event.getConfig().getSpec() == EnvironmentalConfig.CLIENT_SPEC) {
				EnvironmentalConfig.onConfigReload(event);
			}
		});

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EnvironmentalConfig.CLIENT_SPEC);
    }

    private void setupCommon(final FMLCommonSetupEvent event) 
    {
    	DeferredWorkQueue.runLater(() -> 
    	{
    		EnvironmentalCompat.registerCompostables();
    		EnvironmentalCompat.registerFlammables();
            EnvironmentalCompat.registerDispenserBehaviors();

            EnvironmentalData.registerDataSerializers();
            
    		EnvironmentalBiomes.addBiomeTypes();
    		EnvironmentalBiomes.addBiomesToGeneration();
    		EnvironmentalFeatures.generateFeatures();
    		
    		EnvironmentalVillagers.registerVillagerTypes();
    		EnvironmentalVillagers.registerPOIs();
    		
    		EnvironmentalEntities.registerSpawns();
    		EnvironmentalEntities.registerAttributes();
        });
    }

    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event)
    {
        EnvironmentalEntities.registerRendering();
        DeferredWorkQueue.runLater(() ->
        {
            EnvironmentalClient.setRenderLayers();
            EnvironmentalClient.registerBlockColors();
            ScreenManager.registerFactory(EnvironmentalContainerTypes.KILN.get(), KilnScreen::new);
            ScreenManager.registerFactory(EnvironmentalContainerTypes.SAWMILL.get(), SawmillScreen::new);
        });
    }

    private void setupPlayMessages()
    {
        PLAY.messageBuilder(SSyncSlabfishTypeMessage.class, 0, NetworkDirection.PLAY_TO_CLIENT).
                encoder(SSyncSlabfishTypeMessage::serialize).
                decoder(SSyncSlabfishTypeMessage::deserialize).
                consumer(SSyncSlabfishTypeMessage::handlePlay).
                add();

        PLAY.messageBuilder(SSyncSweaterTypeMessage.class, 1, NetworkDirection.PLAY_TO_CLIENT).
                encoder(SSyncSweaterTypeMessage::serialize).
                decoder(SSyncSweaterTypeMessage::deserialize).
                consumer(SSyncSweaterTypeMessage::handlePlay).
                add();

        PLAY.messageBuilder(SSyncBackpackTypeMessage.class, 2, NetworkDirection.PLAY_TO_CLIENT).
                encoder(SSyncBackpackTypeMessage::serialize).
                decoder(SSyncBackpackTypeMessage::deserialize).
                consumer(SSyncBackpackTypeMessage::handlePlay).
                add();

        PLAY.messageBuilder(SOpenSlabfishInventoryMessage.class, 3, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SOpenSlabfishInventoryMessage::serialize).decoder(SOpenSlabfishInventoryMessage::deserialize)
                .consumer(SOpenSlabfishInventoryMessage::handle)
                .add();
    }

    private void setupLoginMessages()
    {
        LOGIN.messageBuilder(CAcknowledgeEnvironmentalMessage.class, 99, NetworkDirection.LOGIN_TO_SERVER).
                loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).
                encoder(CAcknowledgeEnvironmentalMessage::serialize).
                decoder(CAcknowledgeEnvironmentalMessage::deserialize).
                consumer(FMLHandshakeHandler.indexFirst(CAcknowledgeEnvironmentalMessage::handle)).
                add();

        LOGIN.messageBuilder(SSyncSlabfishTypeMessage.class, 0, NetworkDirection.LOGIN_TO_CLIENT).
                loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).
                encoder(SSyncSlabfishTypeMessage::serialize).
                decoder(SSyncSlabfishTypeMessage::deserialize).
                markAsLoginPacket().
                consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSlabfishTypeMessage.handleLogin(msg, ctx))).
                add();

        LOGIN.messageBuilder(SSyncSweaterTypeMessage.class, 1, NetworkDirection.LOGIN_TO_CLIENT).
                loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).
                encoder(SSyncSweaterTypeMessage::serialize).
                decoder(SSyncSweaterTypeMessage::deserialize).
                markAsLoginPacket().
                consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSweaterTypeMessage.handleLogin(msg, ctx))).
                add();

        LOGIN.messageBuilder(SSyncBackpackTypeMessage.class, 2, NetworkDirection.LOGIN_TO_CLIENT).
                loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).
                encoder(SSyncBackpackTypeMessage::serialize).
                decoder(SSyncBackpackTypeMessage::deserialize).
                markAsLoginPacket().
                consumer(FMLHandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncBackpackTypeMessage.handleLogin(msg, ctx))).
                add();
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
    private void registerItemColors(ColorHandlerEvent.Item event)
    {
        REGISTRY_HELPER.processSpawnEggColors(event);
    }

    @SubscribeEvent
    public void onEvent(AddReloadListenerEvent event)
    {
        event.addListener(new SlabfishLoader());
    }
}