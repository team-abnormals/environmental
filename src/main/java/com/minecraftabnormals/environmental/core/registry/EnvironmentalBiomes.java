package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.world.biome.CherryBlossomForestBiome;
import com.minecraftabnormals.environmental.common.world.biome.MarshBiome;
import com.minecraftabnormals.environmental.common.world.biome.MushroomMarshBiome;
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

    public static final RegistryObject<Biome> MARSH = BIOMES.register("marsh", () -> new MarshBiome());
    public static final RegistryObject<Biome> MUSHROOM_MARSH = BIOMES.register("mushroom_marsh", () -> new MushroomMarshBiome());
    public static final RegistryObject<Biome> CHERRY_BLOSSOM_FOREST = BIOMES.register("cherry_blossom_forest", () -> new CherryBlossomForestBiome());
    
    public static void registerBiomesToDictionary() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MARSH.get(), EnvironmentalConfig.COMMON.marshWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MUSHROOM_MARSH.get(), EnvironmentalConfig.COMMON.mushroomMarshWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_FOREST.get(), EnvironmentalConfig.COMMON.cherryBlossomForestWeight.get()));
    }

    public static void addBiomeTypes() {
        BiomeDictionary.addTypes(MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WET, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(MUSHROOM_MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.RARE, BiomeDictionary.Type.WET, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(CHERRY_BLOSSOM_FOREST.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST);
    }
}
