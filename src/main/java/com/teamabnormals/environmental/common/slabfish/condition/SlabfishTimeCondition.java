package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the time is day or night.</p>
 *
 * @author Ocelot
 */
public class SlabfishTimeCondition implements SlabfishCondition {

    public static final Codec<SlabfishTimeCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        SlabfishConditionContext.Time.CODEC.fieldOf("time").forGetter(SlabfishTimeCondition::getTime)
    ).apply(instance, SlabfishTimeCondition::new));

    private final SlabfishConditionContext.Time time;

    private SlabfishTimeCondition(SlabfishConditionContext.Time time) {
        this.time = time;
    }

    public SlabfishConditionContext.Time getTime() {
        return time;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return this.time == context.getTime();
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.TIME.get();
    }
}
