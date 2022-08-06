package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in a currently ongoing raid.</p>
 *
 * @author Ocelot
 */
public class SlabfishRaidCondition implements SlabfishCondition {

    public static final Codec<SlabfishRaidCondition> CODEC = Codec.unit(SlabfishRaidCondition::new);

    public SlabfishRaidCondition() {
    }


    @Override
    public boolean test(SlabfishConditionContext context) {
        return context.isInRaid();
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.RAID.get();
    }
}
