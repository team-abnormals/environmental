package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>false</code>.</p>
 *
 * @author Ocelot
 */
public record SlabfishImpossibleCondition() implements SlabfishCondition {
	public static final MapCodec<SlabfishImpossibleCondition> CODEC = MapCodec.unit(SlabfishImpossibleCondition::new);

	@Override
	public boolean test(SlabfishConditionContext context) {
		return false;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IMPOSSIBLE.get();
	}
}