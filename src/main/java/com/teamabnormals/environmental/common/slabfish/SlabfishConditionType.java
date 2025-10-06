package com.teamabnormals.environmental.common.slabfish;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition;

public record SlabfishConditionType(MapCodec<? extends SlabfishCondition> codec) {
}