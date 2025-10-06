package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in the specified dimension.</p>
 *
 * @author Ocelot
 */
public record SlabfishDimensionCondition(ResourceLocation dimensionRegistryName) implements SlabfishCondition {
	public static final MapCodec<SlabfishDimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("dimension").forGetter(SlabfishDimensionCondition::dimensionRegistryName)
	).apply(instance, SlabfishDimensionCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getDimension().equals(this.dimensionRegistryName);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.DIMENSION.get();
	}
}
