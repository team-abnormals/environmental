package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SlabfishConditionType extends ForgeRegistryEntry<SlabfishConditionType> {
    private final Codec<? extends SlabfishCondition> codec;

    public SlabfishConditionType(Codec<? extends SlabfishCondition> codec) {
        this.codec = codec;
    }

    public Codec<? extends SlabfishCondition> getCodec() {
        return codec;
    }
}
