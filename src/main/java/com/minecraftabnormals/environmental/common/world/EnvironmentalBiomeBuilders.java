package com.minecraftabnormals.environmental.common.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class EnvironmentalBiomeBuilders {
    public static final Biome.Builder MARSH         = createMarshBiome(-0.25F, 0.0F);
    public static final Biome.Builder BLOSSOM_WOODS = createBlossomBiome(0.1F, 0.2F);
    public static final Biome.Builder BLOSSOM_HILLS = createBlossomBiome(0.45F, 0.3F);

    private static Biome.Builder createMarshBiome(float depth, float scale) {
        return (new Biome.Builder())
        .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
        .precipitation(Biome.RainType.RAIN)
        .category(Biome.Category.SWAMP)
        .depth(depth)
        .scale(scale)
        .temperature(0.8F)
        .downfall(0.9F)
        .func_235097_a_((new BiomeAmbience.Builder())
                .setWaterColor(6134398)
                .setWaterFogColor(2302743)
                .setFogColor(12638463)
                .setMoodSound(MoodSoundAmbience.field_235027_b_)
                .build()).parent((String) null);
    }
    
    private static Biome.Builder createBlossomBiome(float depth, float scale) {
        return (new Biome.Builder())
        .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
        .precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST)
        .depth(0.1F)
        .scale(0.2F)
        .temperature(0.75F)
        .downfall(0.8F)
        .func_235097_a_((new BiomeAmbience.Builder())
                .setWaterColor(5216182)
                .setWaterFogColor(335411)
                .setFogColor(12638463)
                .setMoodSound(MoodSoundAmbience.field_235027_b_)
                .build())
        .parent((String)null);
    }
}
