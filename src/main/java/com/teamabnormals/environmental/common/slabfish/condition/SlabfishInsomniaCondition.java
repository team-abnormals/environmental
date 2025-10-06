package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the player that bred two slabfish together has insomnia.</p>
 *
 * @author Ocelot
 */
public record SlabfishInsomniaCondition() implements SlabfishCondition {
	public static final MapCodec<SlabfishInsomniaCondition> CODEC = MapCodec.unit(SlabfishInsomniaCondition::new);

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.isBreederInsomnia();
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.INSOMNIA.get();
	}
}
