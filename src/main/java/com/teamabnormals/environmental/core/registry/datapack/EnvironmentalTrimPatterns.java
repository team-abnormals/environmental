package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class EnvironmentalTrimPatterns {
	public static final ResourceKey<TrimPattern> KEEPER = createKey("keeper");

	public static void bootstrap(BootstrapContext<TrimPattern> context) {
		register(context, KEEPER, EnvironmentalItems.KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
	}

	public static ResourceKey<TrimPattern> createKey(String name) {
		return ResourceKey.create(Registries.TRIM_PATTERN, Environmental.location(name));
	}

	private static void register(BootstrapContext<TrimPattern> context, ResourceKey<TrimPattern> key, Item item) {
		context.register(key, new TrimPattern(key.location(), BuiltInRegistries.ITEM.wrapAsHolder(item), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false));
	}
}
