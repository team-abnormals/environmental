package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeBuilders;
import com.minecraftabnormals.environmental.common.world.biome.blossom.BlossomHighlandsBiome;
import com.minecraftabnormals.environmental.common.world.biome.blossom.BlossomHillsBiome;
import com.minecraftabnormals.environmental.common.world.biome.blossom.BlossomValleysBiome;
import com.minecraftabnormals.environmental.common.world.biome.blossom.BlossomWoodsBiome;
import com.minecraftabnormals.environmental.common.world.biome.marsh.MarshBiome;
import com.minecraftabnormals.environmental.common.world.biome.marsh.MushroomMarshBiome;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Environmental.MODID);

    public static final RegistryObject<Biome> MARSH 			= BIOMES.register("marsh", () -> new MarshBiome(EnvironmentalBiomeBuilders.MARSH));
    public static final RegistryObject<Biome> MUSHROOM_MARSH 	= BIOMES.register("mushroom_marsh", () -> new MushroomMarshBiome(EnvironmentalBiomeBuilders.MARSH));
    
    public static final RegistryObject<Biome> BLOSSOM_WOODS 	= BIOMES.register("blossom_woods", () -> new BlossomWoodsBiome(EnvironmentalBiomeBuilders.BLOSSOM_WOODS));
    public static final RegistryObject<Biome> BLOSSOM_HILLS 	= BIOMES.register("blossom_hills", () -> new BlossomHillsBiome(EnvironmentalBiomeBuilders.BLOSSOM_HILLS));
    public static final RegistryObject<Biome> BLOSSOM_HIGHLANDS	= BIOMES.register("blossom_highlands", () -> new BlossomHighlandsBiome(EnvironmentalBiomeBuilders.BLOSSOM_HIGHLANDS));
    public static final RegistryObject<Biome> BLOSSOM_VALLEYS	= BIOMES.register("blossom_valleys", () -> new BlossomValleysBiome(EnvironmentalBiomeBuilders.BLOSSOM_VALLEYS));
    
    public static void addBiomesToGeneration() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MARSH.get(), EnvironmentalConfig.COMMON.marshWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MUSHROOM_MARSH.get(), EnvironmentalConfig.COMMON.mushroomMarshWeight.get()));
        
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_WOODS.get(), EnvironmentalConfig.COMMON.blossomWoodsWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_HILLS.get(), EnvironmentalConfig.COMMON.blossomHillsWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_HIGHLANDS.get(), EnvironmentalConfig.COMMON.blossomHighlandsWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_VALLEYS.get(), EnvironmentalConfig.COMMON.blossomValleysWeight.get()));
    }

    public static void addBiomeTypes() {
        BiomeDictionary.addTypes(MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WET, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(MUSHROOM_MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.RARE, BiomeDictionary.Type.WET, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(BLOSSOM_WOODS.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.RARE);
        BiomeDictionary.addTypes(BLOSSOM_HILLS.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.RARE, BiomeDictionary.Type.HILLS);
        BiomeDictionary.addTypes(BLOSSOM_HIGHLANDS.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.RARE, BiomeDictionary.Type.MOUNTAIN);
        BiomeDictionary.addTypes(BLOSSOM_VALLEYS.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.RARE, BiomeDictionary.Type.PLAINS);
    }
}
