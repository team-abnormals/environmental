package com.teamabnormals.environmental.core.registry.slabfish;

import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishBackpacks {
	public static final ResourceKey<BackpackType> BROWN = createKey("brown");

	public static void bootstrap(BootstapContext<BackpackType> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<BackpackType> key = createKey(color.getName());
			context.register(key, BackpackType.create(
					Component.translatable(Util.makeDescriptionId("slabfish.backpack", key.location())),
					new ResourceLocation(key.location().getNamespace(), "backpack/" + key.location().getPath()),
					color.getTag()));
		}
	}

	public static ResourceKey<BackpackType> createKey(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_BACKPACK, Environmental.location(name));
	}
}
