package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.entity.animal.koi.KoiVariant;
import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public final class EnvironmentalRegistries {
	public static final ResourceKey<Registry<KoiVariant>> KOI_VARIANT = create("koi_variant");
	public static final ResourceKey<Registry<SlabfishType>> DEER_VARIANT = create("deer_variant");
	public static final ResourceKey<Registry<SlabfishType>> DEER_MARKINGS = create("deer_markings");

	public static final ResourceKey<Registry<SlabfishType>> SLABFISH_TYPE = create("slabfish/type");
	public static final ResourceKey<Registry<BackpackType>> SLABFISH_BACKPACK = create("slabfish/backpack");
	public static final ResourceKey<Registry<SweaterType>> SLABFISH_SWEATER = create("slabfish/sweater");

	public static void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(KOI_VARIANT, KoiVariant.DIRECT_CODEC, KoiVariant.DIRECT_CODEC);

		event.dataPackRegistry(SLABFISH_TYPE, SlabfishType.CODEC, SlabfishType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_BACKPACK, BackpackType.CODEC, BackpackType.NETWORK_CODEC);
		event.dataPackRegistry(SLABFISH_SWEATER, SweaterType.CODEC, SweaterType.NETWORK_CODEC);
	}

	private static <T> ResourceKey<Registry<T>> create(String name) {
		return ResourceKey.createRegistryKey(Environmental.location(name));
	}
}