package com.minecraftabnormals.environmental.core.registry;

import com.google.common.collect.ImmutableSet;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.task.GiveHeroGiftsTask;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Environmental.MOD_ID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<PointOfInterestType> KILN = POI_TYPES.register("kiln", () -> new PointOfInterestType("ceramist", PointOfInterestType.getAllStates(EnvironmentalBlocks.KILN.get()), 1, 1));
	public static final RegistryObject<PointOfInterestType> SAWMILL = POI_TYPES.register("sawmill", () -> new PointOfInterestType("carpenter", PointOfInterestType.getAllStates(EnvironmentalBlocks.SAWMILL.get()), 1, 1));

	public static final RegistryObject<VillagerProfession> CERAMIST = PROFESSIONS.register("ceramist", () -> new VillagerProfession("ceramist", KILN.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER));
	public static final RegistryObject<VillagerProfession> CARPENTER = PROFESSIONS.register("carpenter", () -> new VillagerProfession("carpenter", SAWMILL.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_MASON));

	public static void registerVillagerTypes() {
		VillagerTrades.VILLAGER_DEFAULT_TRADES.isEmpty();
		registerVillagerType(createType("ice_spikes"), Biomes.ICE_SPIKES);
		registerVillagerType(createType("flower_forest"), Biomes.FLOWER_FOREST);
		registerVillagerType(createType("blossom"), EnvironmentalBiomes.BLOSSOM_WOODS.getKey(), EnvironmentalBiomes.BLOSSOM_HILLS.getKey(), EnvironmentalBiomes.BLOSSOM_HIGHLANDS.getKey(), EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey());
		registerVillagerType(createType("forest"), Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);

		GiveHeroGiftsTask.GIFTS.put(CERAMIST.get(), new ResourceLocation(Environmental.MOD_ID, "gameplay/hero_of_the_village/ceramist_gift"));
		GiveHeroGiftsTask.GIFTS.put(CARPENTER.get(), new ResourceLocation(Environmental.MOD_ID, "gameplay/hero_of_the_village/carpenter_gift"));

		setupVillagerHouses();
	}

	private static VillagerType createType(String type) {
		return VillagerType.register(Environmental.MOD_ID + ":" + type);
	}

	private static void registerVillagerType(VillagerType type, RegistryKey<Biome>... biomes) {
		for (RegistryKey<Biome> biome : biomes) {
			VillagerType.BY_BIOME.put(biome, type);
		}
	}

	public static void registerPOIs() {
		try {
			ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class).invoke(null, KILN.get());
			ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class).invoke(null, SAWMILL.get());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static void setupVillagerHouses() {
		PlainsVillagePools.init();
		SnowyVillagePools.init();
		SavannaVillagePools.init();
		DesertVillagePools.init();
		TaigaVillagePools.init();

		addVillagerHouse("ceramist", "plains", 2);
		addVillagerHouse("ceramist", "snowy", 4);
		addVillagerHouse("ceramist", "savanna", 6);
		addVillagerHouse("ceramist", "desert", 5);
		addVillagerHouse("ceramist", "taiga", 7);

		addVillagerHouse("carpenter", "plains", 10);
		addVillagerHouse("carpenter", "snowy", 11);
		addVillagerHouse("carpenter", "savanna", 7);
		addVillagerHouse("carpenter", "desert", 9);
		addVillagerHouse("carpenter", "taiga", 5);
	}

	private static void addVillagerHouse(String type, String biome, int weight) {
		addToPool(new ResourceLocation("village/" + biome + "/houses"), new ResourceLocation(Environmental.MOD_ID, "village/" + type + "_house_" + biome + "_1"), 5);
	}

	private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
		JigsawPattern old = WorldGenRegistries.JIGSAW_POOL.getOrDefault(pool);
		List<JigsawPiece> shuffled = old.getShuffledPieces(new Random());
		List<Pair<JigsawPiece, Integer>> newPieces = new ArrayList<>();
		for (JigsawPiece p : shuffled) newPieces.add(new Pair<>(p, 1));
		newPieces.add(Pair.of(new LegacySingleJigsawPiece(Either.left(toAdd), () -> ProcessorLists.field_244101_a, JigsawPattern.PlacementBehaviour.RIGID), weight));
		ResourceLocation name = old.getName();
		Registry.register(WorldGenRegistries.JIGSAW_POOL, pool, new JigsawPattern(pool, name, newPieces));
	}
}