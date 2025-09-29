package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DataPackRegistryEvent;

public final class EnvironmentalRegistries {
	public static final ResourceKey<Registry<SlabfishType>> SLABFISH_TYPE = key("slabfish/type");
	public static final ResourceKey<Registry<BackpackType>> SLABFISH_BACKPACK = key("slabfish/backpack");
	public static final ResourceKey<Registry<SweaterType>> SLABFISH_SWEATER = key("slabfish/sweater");

	public static void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(SLABFISH_TYPE, SlabfishType.CODEC, SlabfishType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_BACKPACK, BackpackType.CODEC, BackpackType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_SWEATER, SweaterType.CODEC, SweaterType.NETWORK_CODEC);
	}

	private static <T> ResourceKey<Registry<T>> key(String name) {
		return ResourceKey.createRegistryKey(Environmental.location(name));
	}
}