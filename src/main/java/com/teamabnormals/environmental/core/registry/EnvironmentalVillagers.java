package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalVillagers {

	public static void registerVillagerTypes() {
		VillagerTrades.TRADES.isEmpty();
		registerVillagerType(createType("marsh"), EnvironmentalBiomes.MARSH.getKey());
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
}