package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish type is the same as any of the specified.</p>
 *
 * @author Ocelot
 */
public record SlabfishTypeCondition(ResourceLocation... types) implements SlabfishCondition {
	public static final MapCodec<SlabfishTypeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.listOf().xmap(list -> list.toArray(ResourceLocation[]::new), Arrays::asList).fieldOf("types").forGetter(SlabfishTypeCondition::types)
	).apply(instance, SlabfishTypeCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		for (ResourceLocation slabfishType : this.types)
			if (slabfishType.equals(context.getSlabfishType()))
				return true;
		return false;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.SLABFISH_TYPE.get();
	}
}
