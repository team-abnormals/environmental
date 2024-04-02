package com.teamabnormals.environmental.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.environmental.client.model.*;
import com.teamabnormals.environmental.client.renderer.entity.*;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.network.message.*;
import com.teamabnormals.environmental.common.slabfish.SlabfishLoader;
import com.teamabnormals.environmental.core.data.client.EnvironmentalBlockStateProvider;
import com.teamabnormals.environmental.core.data.client.EnvironmentalItemModelProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalAdvancementProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalLootTableProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalRecipeProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.*;
import com.teamabnormals.environmental.core.data.server.tags.*;
import com.teamabnormals.environmental.core.other.*;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
		EnvironmentalConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
		EnvironmentalPlacedFeatures.PLACED_FEATURES.register(bus);
		EnvironmentalNoiseParameters.NOISE_PARAMETERS.register(bus);
		EnvironmentalPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		EnvironmentalAttributes.ATTRIBUTES.register(bus);
		EnvironmentalMobEffects.MOB_EFFECTS.register(bus);
		EnvironmentalMenuTypes.MENU_TYPES.register(bus);
		EnvironmentalParticleTypes.PARTICLE_TYPES.register(bus);
		EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.register(bus);
		EnvironmentalDataSerializers.DATA_SERIALIZERS.register(bus);
		EnvironmentalBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			SlabfishSpriteUploader.init(bus);
			bus.addListener(this::stitchTextures);
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
			EnvironmentalEntityTypes.registerSpawns();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			EnvironmentalClientCompat.registerClientCompat();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean includeServer = event.includeServer();
		EnvironmentalBlockTagsProvider blockTags = new EnvironmentalBlockTagsProvider(generator, helper);
		generator.addProvider(includeServer, blockTags);
		generator.addProvider(includeServer, new EnvironmentalItemTagsProvider(generator, blockTags, helper));
		generator.addProvider(includeServer, new EnvironmentalEntityTypeTagsProvider(generator, helper));
		generator.addProvider(includeServer, new EnvironmentalStructureTagsProvider(generator, helper));
		generator.addProvider(includeServer, new EnvironmentalBiomeTagsProvider(generator, helper));
		generator.addProvider(includeServer, new EnvironmentalPaintingVariantTagsProvider(generator, helper));
		generator.addProvider(includeServer, new EnvironmentalRecipeProvider(generator));
		generator.addProvider(includeServer, new EnvironmentalAdvancementProvider(generator, helper));
		generator.addProvider(includeServer, new EnvironmentalAdvancementModifierProvider(generator));
		generator.addProvider(includeServer, new EnvironmentalChunkGeneratorModifierProvider(generator));
		generator.addProvider(includeServer, new EnvironmentalModdedBiomeSliceProvider(generator));
		generator.addProvider(includeServer, new EnvironmentalLootTableProvider(generator));
		generator.addProvider(includeServer, new EnvironmentalLootModifierProvider(generator));
		generator.addProvider(includeServer, EnvironmentalBiomeModifierProvider.create(generator, helper));
		//generator.addProvider(new SlabfishProvider(generator, MOD_ID, existingFileHelper));

		boolean includeClient = event.includeClient();
		generator.addProvider(includeClient, new EnvironmentalItemModelProvider(generator, helper));
		generator.addProvider(includeClient, new EnvironmentalBlockStateProvider(generator, helper));
	}

	private void setupPlayMessages() {
		PLAY.registerMessage(0, SSyncSlabfishTypeMessage.class, SSyncSlabfishTypeMessage::encode, SSyncSlabfishTypeMessage::decode, SSyncSlabfishTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(1, SSyncSweaterTypeMessage.class, SSyncSweaterTypeMessage::encode, SSyncSweaterTypeMessage::decode, SSyncSweaterTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(2, SSyncBackpackTypeMessage.class, SSyncBackpackTypeMessage::encode, SSyncBackpackTypeMessage::decode, SSyncBackpackTypeMessage::handlePlay, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(3, SOpenSlabfishInventoryMessage.class, SOpenSlabfishInventoryMessage::serialize, SOpenSlabfishInventoryMessage::deserialize, SOpenSlabfishInventoryMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		PLAY.registerMessage(4, C2SZebraJumpMessage.class, C2SZebraJumpMessage::serialize, C2SZebraJumpMessage::deserialize, C2SZebraJumpMessage::handle);
	}

	private void setupLoginMessages() {
		LOGIN.messageBuilder(CAcknowledgeEnvironmentalMessage.class, 99, NetworkDirection.LOGIN_TO_SERVER).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(CAcknowledgeEnvironmentalMessage::encode).decoder(CAcknowledgeEnvironmentalMessage::decode).consumerNetworkThread(HandshakeHandler.indexFirst(CAcknowledgeEnvironmentalMessage::handle)).add();
		LOGIN.messageBuilder(SSyncSlabfishTypeMessage.class, 0, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncSlabfishTypeMessage::encode).decoder(SSyncSlabfishTypeMessage::decode).markAsLoginPacket().consumerMainThread(HandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSlabfishTypeMessage.handleLogin(msg, ctx))).add();
		LOGIN.messageBuilder(SSyncSweaterTypeMessage.class, 1, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncSweaterTypeMessage::encode).decoder(SSyncSweaterTypeMessage::decode).markAsLoginPacket().consumerMainThread(HandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncSweaterTypeMessage.handleLogin(msg, ctx))).add();
		LOGIN.messageBuilder(SSyncBackpackTypeMessage.class, 2, NetworkDirection.LOGIN_TO_CLIENT).loginIndex(EnvironmentalLoginMessage::getLoginIndex, EnvironmentalLoginMessage::setLoginIndex).encoder(SSyncBackpackTypeMessage::encode).decoder(SSyncBackpackTypeMessage::decode).markAsLoginPacket().consumerMainThread(HandshakeHandler.biConsumerFor((__, msg, ctx) -> SSyncBackpackTypeMessage.handleLogin(msg, ctx))).add();
	}

	@SubscribeEvent
	public void onEvent(AddReloadListenerEvent event) {
		event.addListener(new SlabfishLoader());
	}

	@OnlyIn(Dist.CLIENT)
	private void stitchTextures(TextureStitchEvent.Pre event) {
		TextureAtlas texture = event.getAtlas();
		if (InventoryMenu.BLOCK_ATLAS.equals(texture.location())) {
			event.addSprite(new ResourceLocation(Environmental.MOD_ID, "item/slabfish_sweater_slot"));
			event.addSprite(new ResourceLocation(Environmental.MOD_ID, "item/slabfish_backpack_slot"));
			event.addSprite(new ResourceLocation(Environmental.MOD_ID, "item/slabfish_backpack_type_slot"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(EnvironmentalModelLayers.DUCK, DuckModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.DEER, DeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.REINDEER, ReindeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.FENNEC_FOX, FennecFoxModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.KOI, KoiModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.SLABFISH, SlabfishModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.YAK, YakModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.TAPIR, TapirModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.ZEBRA, () -> LayerDefinition.create(ZebraModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.PINECONE_GOLEM, PineconeGolemModel::createBodyLayer);

		event.registerLayerDefinition(EnvironmentalModelLayers.THIEF_HOOD, ThiefHoodModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.HEALER_POUCH, HealerPouchModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.ARCHITECT_BELT, ArchitectBeltModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.WANDERER_BOOTS, WandererBootsModel::createBodyLayer);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EnvironmentalEntityTypes.SLABFISH.get(), SlabfishRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK.get(), DuckRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DEER.get(), DeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.REINDEER.get(), ReindeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.YAK.get(), YakRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.KOI.get(), KoiRenderer::new);
		// event.registerEntityRenderer(EnvironmentalEntityTypes.FENNEC_FOX.get(), FennecFoxRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.TAPIR.get(), TapirRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZEBRA.get(), ZebraRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.PINECONE_GOLEM.get(), PineconeGolemRenderer::new);

		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK_EGG.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.MUD_BALL.get(), ThrownItemRenderer::new);
	}
}