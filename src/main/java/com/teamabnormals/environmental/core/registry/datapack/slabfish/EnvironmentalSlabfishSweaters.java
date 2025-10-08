package com.teamabnormals.environmental.core.registry.datapack.slabfish;

import com.teamabnormals.environmental.common.slabfish.SlabfishSweater;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class EnvironmentalSlabfishSweaters {

	public static void bootstrap(BootstrapContext<SlabfishSweater> context) {
		for (DyeColor color : DyeColor.values()) {
			ResourceKey<SlabfishSweater> key = create(color.getName());
			context.register(key, SlabfishSweater.create(
					Component.translatable(Util.makeDescriptionId("slabfish_sweater", key.location())),
					ResourceLocation.fromNamespaceAndPath(key.location().getNamespace(), "sweater/" + key.location().getPath()),
					Sheep.ITEM_BY_DYE.get(color).asItem()));
		}
	}

	public static ResourceKey<SlabfishSweater> create(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_SWEATER, Environmental.location(name));
	}
}
