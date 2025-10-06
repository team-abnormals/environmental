package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

import java.util.function.Predicate;

public interface SlabfishCondition extends Predicate<SlabfishConditionContext> {
	Codec<SlabfishCondition> CODEC = Codec.lazyInitialized(() -> EnvironmentalSlabfishConditions.SLABFISH_CONDITIONS_REGISTRY.byNameCodec().dispatch(SlabfishCondition::getType, SlabfishConditionType::codec));

	@Override
	boolean test(SlabfishConditionContext context);

	SlabfishConditionType getType();
}
