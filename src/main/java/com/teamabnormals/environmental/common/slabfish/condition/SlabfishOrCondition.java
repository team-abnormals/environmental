package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.Arrays;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the child conditions are true.</p>
 *
 * @author Ocelot
 */
public class SlabfishOrCondition implements SlabfishCondition {

    public static final Codec<SlabfishOrCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(SlabfishOrCondition::getConditions)
    ).apply(instance, SlabfishOrCondition::new));

    private final SlabfishCondition[] conditions;

    public SlabfishOrCondition(SlabfishCondition[] conditions) {
        this.conditions = conditions;
    }

    public SlabfishCondition[] getConditions() {
        return conditions;
    }

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
