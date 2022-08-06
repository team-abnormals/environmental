package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.Arrays;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if all of the child conditions are true.</p>
 *
 * @author Ocelot
 */
public class SlabfishAndCondition implements SlabfishCondition {

    public static final Codec<SlabfishAndCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(SlabfishAndCondition::getConditions)
    ).apply(instance, SlabfishAndCondition::new));

    private final SlabfishCondition[] conditions;

    public SlabfishAndCondition(SlabfishCondition... conditions) {
        this.conditions = conditions;
    }

    public SlabfishCondition[] getConditions() {
        return conditions;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        for (SlabfishCondition condition : this.conditions)
            if (!condition.test(context))
                return false;
        return true;
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.AND.get();
    }
}
