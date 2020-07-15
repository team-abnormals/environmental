package com.team_abnormals.environmental.core;

import com.team_abnormals.environmental.client.gui.screen.inventory.KilnScreen;
import com.team_abnormals.environmental.client.gui.screen.inventory.SawmillScreen;
import com.team_abnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.team_abnormals.environmental.core.other.EnvironmentalData;
import com.team_abnormals.environmental.core.registry.*;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static com.teamabnormals.abnormals_core.core.AbnormalsCore.NETWORK_PROTOCOL;

@SuppressWarnings("deprecation")
@Mod(Environmental.MODID)
@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class Environmental {
    public static final String MODID = "environmental";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public Environmental() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        setupMessages();

        REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredEntityRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredTileEntityRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredSoundRegister().register(modEventBus);

        EnvironmentalBlocks.PAINTINGS.register(modEventBus);
        EnvironmentalContainerTypes.CONTAINER_TYPES.register(modEventBus);
        EnvironmentalRecipes.Serailizers.RECIPE_SERIALIZERS.register(modEventBus);
        EnvironmentalFluids.FLUIDS.register(modEventBus);
        EnvironmentalBiomes.BIOMES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener((ModConfig.ModConfigEvent event) -> {
            final ModConfig config = event.getConfig();
            if (config.getSpec() == EnvironmentalConfig.COMMON_SPEC) {
                EnvironmentalConfig.ValuesHolder.updateCommonValuesFromConfig(config);
            }
        });

        modEventBus.addListener(this::setupCommon);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::setupClient);
            modEventBus.addListener(this::registerItemColors);
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            REGISTRY_HELPER.processSpawnEggDispenseBehaviors();
            EnvironmentalData.registerCompostables();
            EnvironmentalData.registerFlammables();
            EnvironmentalBiomes.addBiomeTypes();
            EnvironmentalBiomes.registerBiomesToDictionary();
            EnvironmentalFeatures.generateFeatures();
            EnvironmentalEntities.addEntitySpawns();
            EnvironmentalEntities.setupAttributes();
        });
    }

    private void setupClient(final FMLClientSetupEvent event) {
        EnvironmentalEntities.registerRendering();
        DeferredWorkQueue.runLater(() -> {
            EnvironmentalData.setRenderLayers();
            EnvironmentalData.registerBlockColors();
            EnvironmentalItems.setupProperties();
            ScreenManager.registerFactory(EnvironmentalContainerTypes.KILN.get(), KilnScreen::new);
            ScreenManager.registerFactory(EnvironmentalContainerTypes.SAWMILL.get(), SawmillScreen::new);
        });
    }

    private void setupMessages() {
        int id = -1;

        CHANNEL.messageBuilder(SOpenSlabfishInventoryMessage.class, id++)
                .encoder(SOpenSlabfishInventoryMessage::serialize).decoder(SOpenSlabfishInventoryMessage::deserialize)
                .consumer(SOpenSlabfishInventoryMessage::handle)
                .add();
    }

    @OnlyIn(Dist.CLIENT)
    private void registerItemColors(ColorHandlerEvent.Item event) {
        REGISTRY_HELPER.processSpawnEggColors(event);
    }
}