package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in the specified dimension.</p>
 *
 * @author Ocelot
 */
public class SlabfishDimensionCondition implements SlabfishCondition {

	public static final Codec<SlabfishDimensionCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ResourceLocation.CODEC.fieldOf("dimension").forGetter(SlabfishDimensionCondition::getDimension)
	).apply(instance, SlabfishDimensionCondition::new));

	private final ResourceLocation dimension;

	private SlabfishDimensionCondition(ResourceLocation dimension) {
		this.dimension = dimension;
	}

	public ResourceLocation getDimension() {
		return dimension;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getDimension().equals(this.dimension);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.DIMENSION.get();
	}
}
