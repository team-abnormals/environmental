package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish type is the same as any of the specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishTypeCondition implements SlabfishCondition {

    public static final Codec<SlabfishTypeCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.listOf().xmap(list -> list.toArray(ResourceLocation[]::new), Arrays::asList).fieldOf("types").forGetter(SlabfishTypeCondition::getTypes)
    ).apply(instance, SlabfishTypeCondition::new));

    private final ResourceLocation[] types;

    public SlabfishTypeCondition(ResourceLocation... types) {
        this.types = types;
    }

    public ResourceLocation[] getTypes() {
        return types;
    }

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
