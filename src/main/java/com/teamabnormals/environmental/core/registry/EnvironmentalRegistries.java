package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.entity.animal.deer.DeerVariant;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiVariant;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.common.slabfish.SlabfishBackpack;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.common.slabfish.SlabfishSweater;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public final class EnvironmentalRegistries {
	public static final ResourceKey<Registry<KoiVariant>> KOI_VARIANT = create("koi_variant");
	public static final ResourceKey<Registry<DeerVariant>> DEER_VARIANT = create("deer_variant");

	public static final ResourceKey<Registry<SlabfishVariant>> SLABFISH_TYPE = create("slabfish/slabfish_variant");
	public static final ResourceKey<Registry<SlabfishBackpack>> SLABFISH_BACKPACK = create("slabfish/slabfish_backpack");
	public static final ResourceKey<Registry<SlabfishSweater>> SLABFISH_SWEATER = create("slabfish/slabfish_sweater");
	public static final ResourceKey<Registry<SlabfishOverlay>> SLABFISH_OVERLAY = create("slabfish/slabfish_overlay");

	public static void registerRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(KOI_VARIANT, KoiVariant.DIRECT_CODEC, KoiVariant.DIRECT_CODEC);
		event.dataPackRegistry(DEER_VARIANT, DeerVariant.DIRECT_CODEC, DeerVariant.DIRECT_CODEC);

		event.dataPackRegistry(SLABFISH_TYPE, SlabfishVariant.DIRECT_CODEC, SlabfishVariant.DIRECT_CODEC);
		event.dataPackRegistry(SLABFISH_BACKPACK, SlabfishBackpack.DIRECT_CODEC, SlabfishBackpack.DIRECT_CODEC);
		event.dataPackRegistry(SLABFISH_SWEATER, SlabfishSweater.DIRECT_CODEC, SlabfishSweater.DIRECT_CODEC);
		event.dataPackRegistry(SLABFISH_OVERLAY, SlabfishOverlay.DIRECT_CODEC, SlabfishOverlay.DIRECT_CODEC);
	}

	private static <T> ResourceKey<Registry<T>> create(String name) {
		return ResourceKey.createRegistryKey(Environmental.location(name));
	}
}