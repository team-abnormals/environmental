package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;


/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInFluidCondition implements SlabfishCondition {

    public static final Codec<SlabfishInFluidCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.either(ForgeRegistries.FLUIDS.getCodec(), TagKey.hashedCodec(ForgeRegistries.FLUIDS.getRegistryKey())).fieldOf("fluid").forGetter(c -> {
            if (c.getFluid() != null) return Either.left(c.getFluid());
            else return Either.right(c.getTag());
        })
    ).apply(instance, (either) -> new SlabfishInFluidCondition(either.left().orElse(null), either.right().orElse(null))));

    private final Fluid fluid;
    private final TagKey<Fluid> tag;

    private SlabfishInFluidCondition(@Nullable Fluid fluid, @Nullable TagKey<Fluid> tag) {
        this.fluid = fluid;
        this.tag = tag;
    }

    @Nullable
    public Fluid getFluid() {
        return fluid;
    }

    @Nullable
    public TagKey<Fluid> getTag() {
        return tag;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return this.fluid != null ? context.isInFluid(this.fluid) : context.isInFluid(this.tag);
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.IN_FLUID.get();
    }
}
