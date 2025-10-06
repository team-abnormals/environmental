package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.Arrays;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if all of the child conditions are true.</p>
 *
 * @author Ocelot
 */
public record SlabfishAndCondition(SlabfishCondition[] conditions) implements SlabfishCondition {
	public static final MapCodec<SlabfishAndCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(SlabfishAndCondition::conditions)
	).apply(instance, SlabfishAndCondition::new));


	@Override
	public boolean test(SlabfishConditionContext context) {
		for (SlabfishCondition condition : this.conditions)
			if (!condition.test(context))
				return false;
		return true;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.AND.get();
	}
}
