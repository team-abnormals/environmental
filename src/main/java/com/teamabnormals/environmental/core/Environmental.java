package com.teamabnormals.environmental.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.network.message.C2SZebraJumpMessage;
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
import com.teamabnormals.environmental.core.other.EnvironmentalClientCompat;
import com.teamabnormals.environmental.core.other.EnvironmentalCompat;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import com.teamabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.gallery.core.data.client.GalleryAssetsRemolderProvider;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MOD_ID, "play")).networkProtocolVersion(() -> NETWORK_PROTOCOL).clientAcceptedVersions(NETWORK_PROTOCOL::equals).serverAcceptedVersions(NETWORK_PROTOCOL::equals).simpleChannel();

	public Environmental() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext context = ModLoadingContext.get();

		this.setupPlayMessages();
		EnvironmentalDataProcessors.registerTrackedData();

		REGISTRY_HELPER.register(bus);
		EnvironmentalPaintingVariants.PAINTING_VARIANTS.register(bus);
		EnvironmentalFeatures.FEATURES.register(bus);
		EnvironmentalFeatures.TREE_DECORATORS.register(bus);
		EnvironmentalAttributes.ATTRIBUTES.register(bus);
		EnvironmentalMobEffects.MOB_EFFECTS.register(bus);
		EnvironmentalMenuTypes.MENU_TYPES.register(bus);
		EnvironmentalParticleTypes.PARTICLE_TYPES.register(bus);
		EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.register(bus);
		EnvironmentalDataSerializers.DATA_SERIALIZERS.register(bus);
		EnvironmentalPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		EnvironmentalBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(bus);
		EnvironmentalBannerPatterns.BANNER_PATTERNS.register(bus);
		EnvironmentalMemoryModuleTypes.MEMORY_MODULE_TYPES.register(bus);
		EnvironmentalSensorTypes.SENSOR_TYPES.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(EnvironmentalRegistries::registerRegistries);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			EnvironmentalItems.setupTabEditors();
			EnvironmentalBlocks.setupTabEditors();
			SlabfishSpriteUploader.init(bus);
		});

		context.registerConfig(Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
		context.registerConfig(Type.CLIENT, EnvironmentalConfig.CLIENT_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EnvironmentalCompat.register();
			EnvironmentalVillagers.registerVillagerTypes();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(EnvironmentalClientCompat::register);
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
		CHANNEL.registerMessage(3, SOpenSlabfishInventoryMessage.class, SOpenSlabfishInventoryMessage::serialize, SOpenSlabfishInventoryMessage::deserialize, SOpenSlabfishInventoryMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(4, C2SZebraJumpMessage.class, C2SZebraJumpMessage::serialize, C2SZebraJumpMessage::deserialize, C2SZebraJumpMessage::handle);
	}

	public static ResourceLocation location(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}