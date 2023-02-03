package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolEntriesModifier;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.Collections;

public class EnvironmentalLootModifierProvider extends LootModifierProvider {

	public EnvironmentalLootModifierProvider(DataGenerator generator) {
		super(generator, Environmental.MOD_ID);
	}

	@Override
	protected void registerEntries() {
		this.entry("simple_dungeon").selects(BuiltInLootTables.SIMPLE_DUNGEON).addModifier(new LootPoolEntriesModifier(false, 0, Collections.singletonList(LootItem.lootTableItem(EnvironmentalItems.THIEF_HOOD.get()).setWeight(10).build())));
	}
}