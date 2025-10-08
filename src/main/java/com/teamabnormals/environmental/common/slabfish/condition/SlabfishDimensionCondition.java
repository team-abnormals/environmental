package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in the specified dimension.</p>
 *
 * @author Ocelot
 */
public record SlabfishDimensionCondition(ResourceKey<Level> dimension) implements SlabfishCondition {
	public static final MapCodec<SlabfishDimensionCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SlabfishDimensionCondition::dimension)
	).apply(instance, SlabfishDimensionCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getDimension().equals(this.dimension);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.DIMENSION.get();
	}
}
