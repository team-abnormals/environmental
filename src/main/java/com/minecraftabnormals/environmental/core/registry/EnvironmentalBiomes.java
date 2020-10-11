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
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
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
        BiomeDictionary.addTypes(MARSH.get(), Type.OVERWORLD, Type.PLAINS, Type.WET, Type.SWAMP);
        BiomeDictionary.addTypes(MUSHROOM_MARSH.get(), Type.OVERWORLD, Type.RARE, Type.WET, Type.PLAINS, Type.SWAMP);
        BiomeDictionary.addTypes(BLOSSOM_WOODS.get(), Type.OVERWORLD, Type.FOREST, Type.RARE);
        BiomeDictionary.addTypes(BLOSSOM_HILLS.get(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.HILLS);
        BiomeDictionary.addTypes(BLOSSOM_HIGHLANDS.get(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.MOUNTAIN);
        BiomeDictionary.addTypes(BLOSSOM_VALLEYS.get(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.PLAINS);
    }
    
    public static void addVanillaBiomeTypes() {
    	BiomeDictionary.addTypes(Biomes.BAMBOO_JUNGLE, Type.HOT, Type.WET, Type.JUNGLE, Type.FOREST, Type.RARE, Type.OVERWORLD);
    	BiomeDictionary.addTypes(Biomes.BAMBOO_JUNGLE_HILLS, Type.HOT, Type.WET, Type.JUNGLE, Type.FOREST, Type.RARE, Type.HILLS, Type.OVERWORLD);
    
    	BiomeDictionary.addTypes(Biomes.DEEP_FROZEN_OCEAN, Type.SNOWY);

    	BiomeDictionary.addTypes(Biomes.NETHER_WASTES, Type.WASTELAND);
    	BiomeDictionary.addTypes(Biomes.CRIMSON_FOREST, Type.HOT, Type.DRY, Type.FOREST, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.WARPED_FOREST, Type.HOT, Type.DRY, Type.FOREST, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.SOUL_SAND_VALLEY, Type.HOT, Type.DRY, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.BASALT_DELTAS, Type.HOT, Type.DRY, Type.NETHER);
    }
}
