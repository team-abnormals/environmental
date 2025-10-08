package com.teamabnormals.environmental.common.slabfish;

import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SlabfishHelper {

	public static Registry<SlabfishVariant> slabfishTypes(RegistryAccess access) {
		return access.registryOrThrow(EnvironmentalRegistries.SLABFISH_TYPE);
	}

	public static Registry<SlabfishSweater> slabfishSweaters(RegistryAccess access) {
		return access.registryOrThrow(EnvironmentalRegistries.SLABFISH_SWEATER);
	}

	public static Registry<SlabfishBackpack> slabfishBackpacks(RegistryAccess access) {
		return access.registryOrThrow(EnvironmentalRegistries.SLABFISH_BACKPACK);
	}
	
	public static Registry<SlabfishOverlay> slabfishOverlays(RegistryAccess access) {
		return access.registryOrThrow(EnvironmentalRegistries.SLABFISH_OVERLAY);
	}

	public static Optional<Reference<SlabfishVariant>> getSlabfishType(Registry<SlabfishVariant> registry, SlabfishConditionContext context) {
		return getSlabfishType(registry, __ -> true, context);
	}

	public static Optional<Reference<SlabfishVariant>> getSlabfishType(Registry<SlabfishVariant> registry, Predicate<SlabfishVariant> predicate, SlabfishConditionContext context) {
		return registry.holders().filter(slabfishType -> predicate.test(slabfishType.value()) && slabfishType.value().test(context)).max(Comparator.comparingInt(ref -> ref.value().priority()));
	}

	public static Optional<Reference<SlabfishSweater>> getSweaterType(Registry<SlabfishSweater> registry, ItemStack stack) {
		return registry.holders().filter(sweaterType -> sweaterType.value().test(stack)).findFirst();
	}

	public static Optional<Reference<SlabfishBackpack>> getBackpackType(Registry<SlabfishBackpack> registry, ItemStack stack) {
		return registry.holders().filter(backpackType -> backpackType.value().test(stack)).findFirst();
	}

	public static Optional<Holder<SlabfishVariant>> getRandomSlabfishType(Registry<SlabfishVariant> registry, Predicate<Holder<SlabfishVariant>> predicate, RandomSource random) {
		return Util.getRandomSafe(registry.holders().filter(predicate).collect(Collectors.toList()), random);
	}
}
