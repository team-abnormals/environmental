package com.teamabnormals.environmental.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.environmental.client.model.*;
import com.teamabnormals.environmental.client.renderer.entity.*;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.network.message.C2SZebraJumpMessage;
import com.teamabnormals.environmental.common.network.message.CAcknowledgeEnvironmentalMessage;
import com.teamabnormals.environmental.common.network.message.EnvironmentalLoginMessage;
import com.teamabnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.teamabnormals.environmental.core.data.client.EnvironmentalBlockStateProvider;
import com.teamabnormals.environmental.core.data.client.EnvironmentalItemModelProvider;
import com.teamabnormals.environmental.core.data.client.EnvironmentalSpriteSourceProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalAdvancementProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalDatapackBuiltinEntriesProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalLootTableProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalRecipeProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.EnvironmentalAdvancementModifierProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.EnvironmentalChunkGeneratorModifierProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.EnvironmentalLootModifierProvider;
import com.teamabnormals.environmental.core.data.server.tags.*;
import com.teamabnormals.environmental.core.other.*;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.gallery.core.data.client.GalleryAssetsRemolderProvider;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod(Environmental.MOD_ID)
@EventBusSubscriber(modid = Environmental.MOD_ID)
public class Environmental {
	public static final String MOD_ID = "environmental";
	public static final String NETWORK_PROTOCOL = "ENV1";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public static final SimpleChannel PLAY = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MOD_ID, "play")).networkProtocolVersion(() -> NETWORK_PROTOCOL).clientAcceptedVersions(NETWORK_PROTOCOL::equals).serverAcceptedVersions(NETWORK_PROTOCOL::equals).simpleChannel();
	public static final SimpleChannel LOGIN = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MOD_ID, "login")).networkProtocolVersion(() -> NETWORK_PROTOCOL).clientAcceptedVersions(NETWORK_PROTOCOL::equals).serverAcceptedVersions(NETWORK_PROTOCOL::equals).simpleChannel();

	public Environmental() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext context = ModLoadingContext.get();

		this.setupPlayMessages();
		EnvironmentalDataProcessors.registerTrackedData();

		REGISTRY_HELPER.register(bus);
		EnvironmentalPaintingVariants.PAINTING_VARIANTS.register(bus);
		EnvironmentalFeatures.FEATURES.register(bus);
		EnvironmentalFeatures.TREE_DECORATORS.register(bus);
		EnvironmentalPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		EnvironmentalAttributes.ATTRIBUTES.register(bus);
		EnvironmentalMobEffects.MOB_EFFECTS.register(bus);
		EnvironmentalMenuTypes.MENU_TYPES.register(bus);
		EnvironmentalParticleTypes.PARTICLE_TYPES.register(bus);
		EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.register(bus);
		EnvironmentalDataSerializers.DATA_SERIALIZERS.register(bus);
		EnvironmentalBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(bus);
		EnvironmentalBannerPatterns.BANNER_PATTERNS.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(EnvironmentalRegistries::registerRegistries);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			EnvironmentalItems.setupTabEditors();
			EnvironmentalBlocks.setupTabEditors();
			SlabfishSpriteUploader.init(bus);
			bus.addListener(this::registerLayerDefinitions);
			bus.addListener(this::registerRenderers);
		});

		context.registerConfig(ModConfig.Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			this.setupLoginMessages();
			EnvironmentalCompat.registerCompat();
			EnvironmentalVillagers.registerVillagerTypes();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			EnvironmentalClientCompat.registerClientCompat();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();

		EnvironmentalDatapackBuiltinEntriesProvider datapackEntries = new EnvironmentalDatapackBuiltinEntriesProvider(output, provider);
		generator.addProvider(server, datapackEntries);
		provider = datapackEntries.getRegistryProvider();

		EnvironmentalBlockTagsProvider blockTags = new EnvironmentalBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new EnvironmentalItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new EnvironmentalEntityTypeTagsProvider(output, provider, helper));
		generator.addProvider(server, new EnvironmentalBiomeTagsProvider(output, provider, helper));
		generator.addProvider(server, new EnvironmentalBannerPatternTagsProvider(output, provider, helper));
		generator.addProvider(server, new EnvironmentalPaintingVariantTagsProvider(output, provider, helper));
		generator.addProvider(server, new EnvironmentalSlabfishTypeTagsProvider(output, provider, helper));
		generator.addProvider(server, new EnvironmentalRecipeProvider(output));
		generator.addProvider(server, EnvironmentalAdvancementProvider.create(output, provider, helper));
		generator.addProvider(server, new EnvironmentalAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new EnvironmentalChunkGeneratorModifierProvider(output, provider));
		generator.addProvider(server, new EnvironmentalLootTableProvider(output));
		generator.addProvider(server, new EnvironmentalLootModifierProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new EnvironmentalItemModelProvider(output, helper));
		generator.addProvider(client, new EnvironmentalBlockStateProvider(output, helper));
		generator.addProvider(client, new EnvironmentalSpriteSourceProvider(output, helper));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper));
		generator.addProvider(client, new GalleryAssetsRemolderProvider(MOD_ID, output, provider));
	}

	private void setupPlayMessages() {
		PLAY.registerMessage(3, SOpenSlabfishInventoryMessage.class, SOpenSlabfishInventoryMessage::serialize, SOpenSlabfishInventoryMessage::deserialize, SOpenSlabfishInventoryMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(4, C2SZebraJumpMessage.class, C2SZebraJumpMessage::serialize, C2SZebraJumpMessage::deserialize, C2SZebraJumpMessage::handle);
	}

	private void setupLoginMessages() {
		LOGIN.messageBuilder(CAcknowledgeEnvironmentalMessage.class, 99, NetworkDirection.LOGIN_TO_SERVER).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(CAcknowledgeEnvironmentalMessage::encode).decoder(CAcknowledgeEnvironmentalMessage::decode).consumerNetworkThread(HandshakeHandler.indexFirst(CAcknowledgeEnvironmentalMessage::handle)).add();
	}

	@OnlyIn(Dist.CLIENT)
	private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(EnvironmentalModelLayers.DUCK, DuckModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.DEER, DeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.REINDEER, ReindeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.KOI, KoiModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.SLABFISH, SlabfishModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.YAK, YakModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.TAPIR, TapirModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.ZEBRA, () -> LayerDefinition.create(ZebraModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZORSE, () -> LayerDefinition.create(ZorseModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZONKEY, () -> LayerDefinition.create(ZonkeyModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZORSE_ARMOR, () -> LayerDefinition.create(ZorseModel.createBodyMesh(new CubeDeformation(0.1F)), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.MULE_ARMOR, () -> MuleArmorModel.createBodyLayer(new CubeDeformation(0.1F)));
		event.registerLayerDefinition(EnvironmentalModelLayers.PINECONE_GOLEM, PineconeGolemModel::createBodyLayer);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EnvironmentalEntityTypes.SLABFISH.get(), SlabfishRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK.get(), DuckRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DEER.get(), DeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.REINDEER.get(), ReindeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.YAK.get(), YakRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.KOI.get(), KoiRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.TAPIR.get(), TapirRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZEBRA.get(), ZebraRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZORSE.get(), ZorseRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZONKEY.get(), ZonkeyRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.PINECONE_GOLEM.get(), PineconeGolemRenderer::new);

		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK_EGG.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.MUD_BALL.get(), ThrownItemRenderer::new);
	}
}