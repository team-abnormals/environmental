package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.levelgen.feature.placement.NoiseDensityPlacement;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Environmental.MOD_ID);

	public static final RegistryObject<PlacementModifierType<NoiseDensityPlacement>> NOISE_DENSITY = PLACEMENT_MODIFIER_TYPES.register("noise_density", () -> () -> NoiseDensityPlacement.CODEC);
}