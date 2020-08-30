package com.minecraftabnormals.environmental.core.registry;

import java.lang.reflect.InvocationTargetException;

import com.google.common.collect.ImmutableSet;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Environmental.MODID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Environmental.MODID);

	public static final RegistryObject<PointOfInterestType> KILN = POI_TYPES.register("kiln", () -> new PointOfInterestType("ceramist", PointOfInterestType.getAllStates(EnvironmentalBlocks.KILN.get()), 1, 1));
	public static final RegistryObject<PointOfInterestType> SAWMILL = POI_TYPES.register("sawmill", () -> new PointOfInterestType("carpenter", PointOfInterestType.getAllStates(EnvironmentalBlocks.SAWMILL.get()), 1, 1));

	public static final RegistryObject<VillagerProfession> CERAMIST = PROFESSIONS.register("ceramist", () -> new VillagerProfession("ceramist", KILN.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER));
	public static final RegistryObject<VillagerProfession> CARPENTER = PROFESSIONS.register("carpenter", () -> new VillagerProfession("carpenter", SAWMILL.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_MASON));

	public static void registerVillagerTypes() {
		VillagerTrades.VILLAGER_DEFAULT_TRADES.isEmpty();
		
		IVillagerType iceSpikes = IVillagerType.register(Environmental.MODID + ":ice_spikes");
		IVillagerType blossom = IVillagerType.register(Environmental.MODID + ":blossom");
		IVillagerType forest = IVillagerType.register(Environmental.MODID + ":forest");
		IVillagerType flowerForest = IVillagerType.register(Environmental.MODID + ":flower_forest");
		
		IVillagerType.BY_BIOME.put(Biomes.ICE_SPIKES, iceSpikes);

		IVillagerType.BY_BIOME.put(Biomes.FOREST, forest);
		IVillagerType.BY_BIOME.put(Biomes.BIRCH_FOREST, forest);
		IVillagerType.BY_BIOME.put(Biomes.BIRCH_FOREST_HILLS, forest);
		IVillagerType.BY_BIOME.put(Biomes.TALL_BIRCH_FOREST, forest);
		IVillagerType.BY_BIOME.put(Biomes.TALL_BIRCH_HILLS, forest);

		IVillagerType.BY_BIOME.put(Biomes.FLOWER_FOREST, flowerForest);
		
		IVillagerType.BY_BIOME.put(EnvironmentalBiomes.BLOSSOM_WOODS.get(), blossom);
		IVillagerType.BY_BIOME.put(EnvironmentalBiomes.BLOSSOM_HILLS.get(), blossom);
		IVillagerType.BY_BIOME.put(EnvironmentalBiomes.BLOSSOM_HIGHLANDS.get(), blossom);
		IVillagerType.BY_BIOME.put(EnvironmentalBiomes.BLOSSOM_VALLEYS.get(), blossom);
	}

	public static void registerPOIs() {
		try {
			ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class).invoke(null, KILN.get());
			ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class).invoke(null, SAWMILL.get());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}