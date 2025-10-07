package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public record SlabfishInBiomeCondition(HolderSet<Biome> biomes) implements SlabfishCondition {
	public static final MapCodec<SlabfishInBiomeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(entry -> entry.biomes)
	).apply(instance, SlabfishInBiomeCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.biomes.contains(context.getBiome());
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_BIOME.get();
	}
}
