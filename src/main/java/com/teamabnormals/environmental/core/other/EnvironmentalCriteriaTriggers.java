package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class EnvironmentalCriteriaTriggers {
	public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Environmental.MOD_ID);

	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> BACKPACK_SLABFISH = TRIGGERS.register("backpack_slabfish", PlayerTrigger::new);
	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> WHEN_PIGS_FLY = TRIGGERS.register("when_pigs_fly", PlayerTrigger::new);

	public static Criterion<TriggerInstance> backpackSlabfish() {
		return BACKPACK_SLABFISH.get().createCriterion(new PlayerTrigger.TriggerInstance(EntityPredicate.wrap(Optional.empty())));
	}

	public static Criterion<TriggerInstance> whenPigsFly() {
		return WHEN_PIGS_FLY.get().createCriterion(new PlayerTrigger.TriggerInstance(EntityPredicate.wrap(Optional.empty())));
	}
}	
