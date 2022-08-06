package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the player that bred two slabfish together has insomnia.</p>
 *
 * @author Ocelot
 */
public class SlabfishInsomniaCondition implements SlabfishCondition {

	public static final Codec<SlabfishInsomniaCondition> CODEC = Codec.unit(SlabfishInsomniaCondition::new);

	public SlabfishInsomniaCondition() {
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.isBreederInsomnia();
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.INSOMNIA.get();
	}
}
