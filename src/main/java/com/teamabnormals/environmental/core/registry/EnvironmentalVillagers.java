package com.teamabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableSet;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Environmental.MOD_ID);
	public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<PoiType> KILN = POI_TYPES.register("kiln", () -> new PoiType("ceramist", PoiType.getBlockStates(EnvironmentalBlocks.KILN.get()), 1, 1));

	public static final RegistryObject<VillagerProfession> CERAMIST = PROFESSIONS.register("ceramist", () -> new VillagerProfession("environmental:ceramist", KILN.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER));

	public static void registerVillagerTypes() {
		VillagerTrades.TRADES.isEmpty();

		registerVillagerType(createType("ice_spikes"), Biomes.ICE_SPIKES);
		registerVillagerType(createType("flower_forest"), Biomes.FLOWER_FOREST);
		registerVillagerType(createType("blossom"), EnvironmentalBiomes.BLOSSOM_WOODS.getKey(), EnvironmentalBiomes.BLOSSOM_HILLS.getKey(), EnvironmentalBiomes.BLOSSOM_HIGHLANDS.getKey(), EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey());
		registerVillagerType(createType("forest"), Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
		registerVillagerType(createType("marsh"), EnvironmentalBiomes.MARSH.getKey(), EnvironmentalBiomes.MUSHROOM_MARSH.getKey());

		DataUtil.registerVillagerGift(CERAMIST.get());

		setupVillagerHouses();
	}

	private static VillagerType createType(String type) {
		return VillagerType.register(Environmental.MOD_ID + ":" + type);
	}

	@SafeVarargs
	private static void registerVillagerType(VillagerType type, ResourceKey<Biome>... biomes) {
		for (ResourceKey<Biome> biome : biomes) {
			VillagerType.BY_BIOME.put(biome, type);
		}
	}

	private static void setupVillagerHouses() {
		PlainVillagePools.bootstrap();
		SnowyVillagePools.bootstrap();
		SavannaVillagePools.bootstrap();
		DesertVillagePools.bootstrap();
		TaigaVillagePools.bootstrap();

		addVillagerHouse("ceramist", "plains", 2);
		addVillagerHouse("ceramist", "snowy", 4);
		addVillagerHouse("ceramist", "savanna", 6);
		addVillagerHouse("ceramist", "desert", 5);
		addVillagerHouse("ceramist", "taiga", 7);
	}

	private static void addVillagerHouse(String type, String biome, int weight) {
		DataUtil.addToJigsawPattern(new ResourceLocation("village/" + biome + "/houses"), StructurePoolElement.single(Environmental.MOD_ID + ":village/" + type + "_house_" + biome + "_1").apply(StructureTemplatePool.Projection.RIGID), weight);
	}
}