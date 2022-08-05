package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish parents are the same as the ones specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishBreedCondition implements SlabfishCondition {

    public static final Codec<SlabfishBreedCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("parent").forGetter(SlabfishBreedCondition::getParent),
        ResourceLocation.CODEC.optionalFieldOf("partner").forGetter(c -> Optional.ofNullable(c.getPartner()))
    ).apply(instance, (parent, partner) -> new SlabfishBreedCondition(parent, partner.orElse(null))));

    private final ResourceLocation parent;
    @Nullable
    private final ResourceLocation partner;

    private SlabfishBreedCondition(ResourceLocation parent, @Nullable ResourceLocation partner) {
        this.parent = parent;
        this.partner = partner;
    }

    public ResourceLocation getParent() {
        return parent;
    }

    @Nullable
    public ResourceLocation getPartner() {
        return partner;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        Pair<SlabfishType, SlabfishType> parentTypes = context.getParentTypes();
        if (parentTypes == null)
            return false;

        if (!this.parent.equals(parentTypes.getLeft().registryName()) && !this.parent.equals(parentTypes.getRight().registryName()))
            return false;

        if (this.partner == null)
            return true;

        return (this.parent.equals(parentTypes.getLeft().registryName()) && this.partner.equals(parentTypes.getRight().registryName())) || (this.parent.equals(parentTypes.getRight().registryName()) && this.partner.equals(parentTypes.getLeft().registryName()));
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.BREED.get();
    }
}
