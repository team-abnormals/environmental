package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class EnvironmentalTrimMaterials {
	public static final ResourceKey<TrimMaterial> BOG_IRON = createKey("bog_iron");

	public static void bootstrap(BootstrapContext<TrimMaterial> context) {
		register(context, BOG_IRON, EnvironmentalItems.BOG_IRON.get(), Style.EMPTY.withColor(0x915D4A), Map.of());
	}

	private static ResourceKey<TrimMaterial> createKey(String name) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, Environmental.location(name));
	}

	private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> key, Item item, Style style, Map<Holder<ArmorMaterial>, String> overrides) {
		ResourceLocation location = key.location();
		String path = location.getPath();
		context.register(key, new TrimMaterial(location.getNamespace() + "_" + path, BuiltInRegistries.ITEM.wrapAsHolder(item), -1.0F, overrides, Component.translatable(Util.makeDescriptionId("trim_material", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path))).withStyle(style)));
	}
}
