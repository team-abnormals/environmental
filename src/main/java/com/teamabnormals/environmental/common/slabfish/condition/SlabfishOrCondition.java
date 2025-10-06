package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.Arrays;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the child conditions are true.</p>
 *
 * @author Ocelot
 */
public record SlabfishOrCondition(SlabfishCondition[] conditions) implements SlabfishCondition {
	public static final MapCodec<SlabfishOrCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(SlabfishOrCondition::conditions)
	).apply(instance, SlabfishOrCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		for (SlabfishCondition condition : this.conditions)
			if (condition.test(context))
				return true;
		return false;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.OR.get();
	}
}
