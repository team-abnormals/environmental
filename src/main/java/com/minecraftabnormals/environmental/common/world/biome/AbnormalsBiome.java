package com.minecraftabnormals.environmental.common.world.biome;

import net.minecraft.world.biome.Biome;

public abstract class AbnormalsBiome extends Biome {
    
    public AbnormalsBiome(Builder biomeBuilder) {
        super(biomeBuilder);
    }
    
    public abstract void addFeatures();
    public abstract void addSpawns();
}
