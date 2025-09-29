package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EnvironmentalDamageTypes {
	public static final ResourceKey<DamageType> RIDING_ZEBRA = createKey("riding_zebra");

	public static void bootstrap(BootstapContext<DamageType> context) {
		register(context, RIDING_ZEBRA, "ridingZebra", 0.1F);
	}

	public static DamageSource ridingZebra(Level level, Entity mount, @Nullable Entity rider) {
		return level.damageSources().source(RIDING_ZEBRA, mount, rider);
	}

	public static Reference<DamageType> register(BootstapContext<DamageType> context, ResourceKey<DamageType> key, String localizationKey, float exhaustion) {
		return context.register(key, new DamageType(Environmental.MOD_ID + "." + localizationKey, exhaustion));
	}

	public static ResourceKey<DamageType> createKey(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, Environmental.location(name));
	}

}
