package com.minecraftabnormals.environmental.common.world.biome;

import net.minecraft.world.biome.Biome;

public abstract class EnvironmentalBiome extends Biome {

    protected EnvironmentalBiome(Builder biomeBuilder) {
        super(biomeBuilder);
    }

    public void addFeatures() {}
    public void addSpawns() {}
}
