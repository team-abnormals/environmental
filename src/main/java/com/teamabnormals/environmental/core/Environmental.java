package com.teamabnormals.environmental.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.environmental.client.resources.SlabfishSpriteUploader;
import com.teamabnormals.environmental.common.network.message.OpenSlabfishInventoryPayload;
import com.teamabnormals.environmental.common.network.message.ZebraJumpPayload;
import com.teamabnormals.environmental.core.data.client.EnvironmentalBlockStateProvider;
import com.teamabnormals.environmental.core.data.client.EnvironmentalItemModelProvider;
import com.teamabnormals.environmental.core.data.client.EnvironmentalSpriteSourceProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalAdvancementProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalDatapackProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalLootTableProvider;
import com.teamabnormals.environmental.core.data.server.EnvironmentalRecipeProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.EnvironmentalAdvancementModifierProvider;
import com.teamabnormals.environmental.core.data.server.modifiers.EnvironmentalChunkGeneratorModifierProvider;
import com.teamabnormals.environmental.core.data.server.tags.*;
import com.teamabnormals.environmental.core.other.*;
import com.teamabnormals.environmental.core.registry.*;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.concurrent.CompletableFuture;

@Mod(Environmental.MOD_ID)
public class Environmental {
	public static final String MOD_ID = "environmental";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Environmental(IEventBus bus, ModContainer container) {
		EnvironmentalDataProcessors.registerTrackedData();

		EnvironmentalBlocks.BLOCKS.register(bus);
		EnvironmentalItems.ITEMS.register(bus);
		EnvironmentalEntityTypes.ENTITY_TYPES.register(bus);
		EnvironmentalSoundEvents.SOUND_EVENTS.register(bus);
		EnvironmentalFeatures.FEATURES.register(bus);
		EnvironmentalFeatures.TREE_DECORATORS.register(bus);
		EnvironmentalMobEffects.MOB_EFFECTS.register(bus);
		EnvironmentalMenuTypes.MENUS.register(bus);
		EnvironmentalParticleTypes.PARTICLE_TYPES.register(bus);
		EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.register(bus);
		EnvironmentalDataSerializers.DATA_SERIALIZERS.register(bus);
		EnvironmentalPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		EnvironmentalBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(bus);
		EnvironmentalMemoryModuleTypes.MEMORY_MODULE_TYPES.register(bus);
		EnvironmentalSensorTypes.SENSOR_TYPES.register(bus);
		EnvironmentalCriteriaTriggers.TRIGGERS.register(bus);
		EnvironmentalArmorMaterials.ARMOR_MATERIALS.register(bus);

		bus.addListener(this::registerCapabilities);
		bus.addListener(this::registerPayloadHandlers);
		bus.addListener(EnvironmentalRegistries::registerRegistries);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			SlabfishSpriteUploader.init(bus);
		}

		container.registerConfig(Type.COMMON, EnvironmentalConfig.COMMON_SPEC);
		container.registerConfig(Type.CLIENT, EnvironmentalConfig.CLIENT_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EnvironmentalCompat.register();
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

		EnvironmentalDatapackProvider datapackEntries = new EnvironmentalDatapackProvider(output, provider);
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
		generator.addProvider(server, new EnvironmentalRecipeProvider(output, provider));
		generator.addProvider(server, EnvironmentalAdvancementProvider.create(output, provider, helper));
		generator.addProvider(server, new EnvironmentalAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new EnvironmentalChunkGeneratorModifierProvider(output, provider));
		generator.addProvider(server, new EnvironmentalLootTableProvider(output, provider));
		// generator.addProvider(server, new EnvironmentalLootModifierProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new EnvironmentalItemModelProvider(output, helper));
		generator.addProvider(client, new EnvironmentalBlockStateProvider(output, helper));
		generator.addProvider(client, new EnvironmentalSpriteSourceProvider(output, provider, helper));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper, provider));
	}

	private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(ZebraJumpPayload.TYPE, ZebraJumpPayload.STREAM_CODEC, ZebraJumpPayload::handle);
		registrar.playToClient(OpenSlabfishInventoryPayload.TYPE, OpenSlabfishInventoryPayload.STREAM_CODEC, OpenSlabfishInventoryPayload::handle);
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerEntity(Capabilities.ItemHandler.ENTITY, EnvironmentalEntityTypes.SLABFISH.get(), (entity, ctx) -> new InvWrapper(entity.slabfishBackpack));
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}