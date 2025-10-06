package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the player that bred two slabfish together has insomnia.</p>
 *
 * @author Ocelot
 */
public record SlabfishRandomCondition(float chance) implements SlabfishCondition {
	public static final MapCodec<SlabfishRandomCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.FLOAT.fieldOf("chance").forGetter(SlabfishRandomCondition::chance)
	).apply(instance, SlabfishRandomCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getRandom().nextFloat() <= this.chance;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.RANDOM.get();
	}
}