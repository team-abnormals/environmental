package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in a currently ongoing raid.</p>
 *
 * @author Ocelot
 */
public record SlabfishRaidCondition() implements SlabfishCondition {
	public static final MapCodec<SlabfishRaidCondition> CODEC = MapCodec.unit(SlabfishRaidCondition::new);

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.isInRaid();
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.RAID.get();
	}
}
