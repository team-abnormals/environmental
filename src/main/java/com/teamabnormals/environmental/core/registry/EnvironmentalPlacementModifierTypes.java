package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, Environmental.MOD_ID);

	public static final RegistryObject<PlacementModifierType<BetterNoiseBasedCountPlacement>> BETTER_NOISE_BASED_COUNT = PLACEMENT_MODIFIER_TYPES.register("better_noise_based_count", () -> (PlacementModifierType<BetterNoiseBasedCountPlacement>) () -> BetterNoiseBasedCountPlacement.CODEC);
}