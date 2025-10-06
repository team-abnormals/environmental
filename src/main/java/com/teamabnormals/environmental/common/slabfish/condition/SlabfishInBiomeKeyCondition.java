package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public record SlabfishInBiomeKeyCondition(ResourceLocation biome) implements SlabfishCondition {
	public static final MapCodec<SlabfishInBiomeKeyCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("biome").forGetter(entry -> entry.biome)
	).apply(instance, SlabfishInBiomeKeyCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getBiome().is(this.biome);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_BIOME_KEY.get();
	}
}
