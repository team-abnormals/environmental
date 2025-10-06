package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

public record SlabfishBreedCondition(ResourceLocation parent, @Nullable ResourceLocation partner) implements SlabfishCondition {
	public static final MapCodec<SlabfishBreedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("parent").forGetter(SlabfishBreedCondition::parent),
			ResourceLocation.CODEC.optionalFieldOf("partner").forGetter(c -> Optional.ofNullable(c.partner()))
	).apply(instance, (parent, partner) -> new SlabfishBreedCondition(parent, partner.orElse(null))));


	@Override
	public boolean test(SlabfishConditionContext context) {
		Pair<ResourceLocation, ResourceLocation> parentTypes = context.getParentTypes();
		if (parentTypes == null)
			return false;

		if (!this.parent.equals(parentTypes.getLeft()) && !this.parent.equals(parentTypes.getRight()))
			return false;

		if (this.partner == null)
			return true;

		return (this.parent.equals(parentTypes.getLeft()) && this.partner.equals(parentTypes.getRight())) || (this.parent.equals(parentTypes.getRight()) && this.partner.equals(parentTypes.getLeft()));
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.BREED.get();
	}
}