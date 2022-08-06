package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.Arrays;
import java.util.List;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if spawned under the specified actions.</p>
 *
 * @author Ocelot
 */
public class SlabfishEventCondition implements SlabfishCondition {

    public static final Codec<SlabfishEventCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        SlabfishConditionContext.Event.CODEC.listOf().xmap(list -> list.toArray(SlabfishConditionContext.Event[]::new), Arrays::asList).fieldOf("events").forGetter(SlabfishEventCondition::getEvents)
    ).apply(instance, SlabfishEventCondition::new));

    private final SlabfishConditionContext.Event[] events;
    private final byte flag;

    public SlabfishEventCondition(SlabfishConditionContext.Event... events) {
        this.events = events;
        byte flag = 0;
        for (SlabfishConditionContext.Event event : events)
            flag |= (1 << event.ordinal());
        this.flag = flag;
    }

    public SlabfishConditionContext.Event[] getEvents() {
        return events;
    }

    public byte getFlag() {
        return flag;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return ((this.flag >> context.getEvent().ordinal()) & 1) > 0;
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.EVENT.get();
    }
}
