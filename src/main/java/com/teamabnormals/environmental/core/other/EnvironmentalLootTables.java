package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class EnvironmentalLootTables {
	public static final ResourceKey<LootTable> LOG_CABIN = create("chests/log_cabin");
	public static final ResourceKey<LootTable> LOG_CABIN_JUNK = create("chests/log_cabin_junk");
	public static final ResourceKey<LootTable> LOG_CABIN_DISPENSER = create("chests/log_cabin_dispenser");
	public static final ResourceKey<LootTable> LOG_CABIN_DROPPER = create("chests/log_cabin_dropper");

	private static ResourceKey<LootTable> create(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, Environmental.location(name));
	}
}