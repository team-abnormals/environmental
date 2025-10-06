package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the time is day or night.</p>
 *
 * @author Ocelot
 */
public record SlabfishTimeCondition(SlabfishConditionContext.Time time) implements SlabfishCondition {
	public static final MapCodec<SlabfishTimeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			SlabfishConditionContext.Time.CODEC.fieldOf("time").forGetter(SlabfishTimeCondition::time)
	).apply(instance, SlabfishTimeCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.time == context.getTime();
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.TIME.get();
	}
}
