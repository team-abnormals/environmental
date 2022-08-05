package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the player that bred two slabfish together has insomnia.</p>
 *
 * @author Ocelot
 */
public class SlabfishRandomCondition implements SlabfishCondition {

	public static final Codec<SlabfishRandomCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.FLOAT.fieldOf("chance").forGetter(SlabfishRandomCondition::getChance)
	).apply(instance, SlabfishRandomCondition::new));

	private final float chance;

	private SlabfishRandomCondition(float chance) {
		this.chance = chance;
	}

	public float getChance() {
		return chance;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getRandom().nextFloat() <= this.chance;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.RANDOM.get();
	}
}
