package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish within the height range specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishHeightCondition implements SlabfishCondition {

	private static final Codec<SlabfishHeightCondition> VALUE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("value").forGetter(SlabfishHeightCondition::getMax)
	).apply(instance, value -> new SlabfishHeightCondition(value, value)));

	private static final Codec<SlabfishHeightCondition> MIN_MAX_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.optionalFieldOf("min", Integer.MIN_VALUE).forGetter(SlabfishHeightCondition::getMin),
		Codec.INT.optionalFieldOf("max", Integer.MAX_VALUE).forGetter(SlabfishHeightCondition::getMax)
	).apply(instance, SlabfishHeightCondition::new));

	public static final Codec<SlabfishHeightCondition> CODEC = ExtraCodecs.xor(SlabfishHeightCondition.VALUE_CODEC, SlabfishHeightCondition.MIN_MAX_CODEC).xmap(
		c -> c.left().isPresent() ? c.left().get() : c.right().orElse(null),
		c -> c.getMin() == c.getMax() ? Either.left(c) : Either.right(c)
	);

	private final int min;
	private final int max;

	private SlabfishHeightCondition(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getPos().getY() >= this.min && context.getPos().getY() <= this.max;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.HEIGHT.get();
	}
}
