package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnvironmentalMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Environmental.MOD_ID);

	public static final DeferredHolder<MobEffect, MobEffect> SERENITY = MOB_EFFECTS.register("serenity", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 15494786));
}