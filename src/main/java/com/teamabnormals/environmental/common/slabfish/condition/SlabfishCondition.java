package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Predicate;

/**
 * <p>A condition that can be used to determine whether or not a {@link SlabfishType} can be added.</p>
 *
 * @author Ocelot
 */
public interface SlabfishCondition extends Predicate<SlabfishConditionContext> {

    Codec<SlabfishCondition> CODEC = ExtraCodecs.lazyInitializedCodec(() -> EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS.get().getCodec().dispatch(SlabfishCondition::getType, SlabfishConditionType::getCodec));

    /**
     * Determines if this condition is met based on the specified context.
     *
     * @param context The context of the test
     * @return Whether or not this condition passed
     */
    @Override
    boolean test(SlabfishConditionContext context);

    /**
     * @return The condition type
     */
    SlabfishConditionType getType();
}
