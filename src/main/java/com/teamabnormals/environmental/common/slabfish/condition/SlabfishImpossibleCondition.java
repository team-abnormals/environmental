package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>false</code>.</p>
 *
 * @author Ocelot
 */
public class SlabfishImpossibleCondition implements SlabfishCondition {

    public static final Codec<SlabfishImpossibleCondition> CODEC = Codec.unit(SlabfishImpossibleCondition::new);

    public SlabfishImpossibleCondition() {
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return false;
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.IMPOSSIBLE.get();
    }
}
