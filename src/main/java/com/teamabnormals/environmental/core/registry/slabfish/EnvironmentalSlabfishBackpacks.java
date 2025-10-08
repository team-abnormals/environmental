package com.teamabnormals.environmental.core.registry.slabfish;

import com.teamabnormals.environmental.common.slabfish.SlabfishBackpack;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishBackpacks {
	public static final ResourceKey<SlabfishBackpack> BROWN = create("brown");

	public static void bootstrap(BootstrapContext<SlabfishBackpack> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishBackpack> key = create(color.getName());
			context.register(key, SlabfishBackpack.create(
					Component.translatable(Util.makeDescriptionId("slabfish.backpack", key.location())),
					ResourceLocation.fromNamespaceAndPath(key.location().getNamespace(), "backpack/" + key.location().getPath()),
					color.getTag()));
		}
	}

	public static ResourceKey<SlabfishBackpack> create(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_BACKPACK, Environmental.location(name));
	}
}
