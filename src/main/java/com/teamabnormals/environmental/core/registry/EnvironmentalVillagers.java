package com.teamabnormals.environmental.core.registry;

import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalVillagers {

	public static void registerVillagerTypes() {
		VillagerType.BY_BIOME.put(EnvironmentalBiomes.MARSH.getKey(), VillagerType.SWAMP);
	}
}