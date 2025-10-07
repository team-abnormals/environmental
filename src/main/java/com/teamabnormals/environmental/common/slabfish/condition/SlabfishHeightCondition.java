package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.common.slabfish.XorMapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish within the height range specified.</p>
 *
 * @author Ocelot
 */
public record SlabfishHeightCondition(int min, int max) implements SlabfishCondition {
	private static final MapCodec<SlabfishHeightCondition> VALUE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.fieldOf("value").forGetter(SlabfishHeightCondition::max)
	).apply(instance, value -> new SlabfishHeightCondition(value, value)));

	private static final MapCodec<SlabfishHeightCondition> MIN_MAX_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.optionalFieldOf("min", Integer.MIN_VALUE).forGetter(SlabfishHeightCondition::min),
			Codec.INT.optionalFieldOf("max", Integer.MAX_VALUE).forGetter(SlabfishHeightCondition::max)
	).apply(instance, SlabfishHeightCondition::new));

	public static final MapCodec<SlabfishHeightCondition> CODEC = XorMapCodec.xor(SlabfishHeightCondition.VALUE_CODEC, SlabfishHeightCondition.MIN_MAX_CODEC).xmap(
			c -> c.left().isPresent() ? c.left().get() : c.right().orElse(null),
			c -> c.min() == c.max() ? Either.left(c) : Either.right(c)
	);

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getPos().getY() >= this.min && context.getPos().getY() <= this.max;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.HEIGHT.get();
	}
}