package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.levelgen.feature.placement.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnvironmentalPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Environmental.MOD_ID);

	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<NoiseDensityPlacement>> NOISE_DENSITY = PLACEMENT_MODIFIER_TYPES.register("noise_density", () -> () -> NoiseDensityPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CedarSwampTreePlacement>> CEDAR_SWAMP_TREE = PLACEMENT_MODIFIER_TYPES.register("cedar_swamp_tree", () -> () -> CedarSwampTreePlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CedarSwampDarknessPlacement>> CEDAR_SWAMP_DARKNESS = PLACEMENT_MODIFIER_TYPES.register("cedar_swamp_darkness", () -> () -> CedarSwampDarknessPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CedarSwampSmoothcapMossPlacement>> CEDAR_SWAMP_SMOOTHCAP_MOSS = PLACEMENT_MODIFIER_TYPES.register("cedar_swamp_smoothcap_moss", () -> () -> CedarSwampSmoothcapMossPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CedarSwampShrubPlacement>> CEDAR_SWAMP_SHRUB = PLACEMENT_MODIFIER_TYPES.register("cedar_swamp_shrub", () -> () -> CedarSwampShrubPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CedarSwampMushroomPlacement>> CEDAR_SWAMP_MUSHROOM = PLACEMENT_MODIFIER_TYPES.register("cedar_swamp_mushroom", () -> () -> CedarSwampMushroomPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<WeirdnessBandPlacement>> WEIRDNESS_BAND = PLACEMENT_MODIFIER_TYPES.register("weirdness_band", () -> () -> WeirdnessBandPlacement.CODEC);
}