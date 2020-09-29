package com.minecraftabnormals.environmental.core.registry;

import java.util.HashMap;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
    
    	BiomeDictionary.addTypes(Biomes.NETHER_WASTES, Type.WASTELAND);
    	BiomeDictionary.addTypes(Biomes.CRIMSON_FOREST, Type.HOT, Type.DRY, Type.FOREST, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.WARPED_FOREST, Type.HOT, Type.DRY, Type.FOREST, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.SOUL_SAND_VALLEY, Type.HOT, Type.DRY, Type.NETHER);
    	BiomeDictionary.addTypes(Biomes.BASALT_DELTAS, Type.HOT, Type.DRY, Type.NETHER);
    }
    
    private static final HashMap<Biome, Integer> BIOME_WATER_MAP = new HashMap<>();
    private static final HashMap<Biome, Integer> BIOME_WATER_FOG_MAP = new HashMap<>();
    
    @OnlyIn(Dist.CLIENT)
	public static void replaceBiomeFogColors() {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {			
			if(biome.getCategory() == Biome.Category.DESERT) 
				replaceFogValue(biome, EnvironmentalConfig.CLIENT.desertFog.get());
			if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) 
				replaceFogValue(biome, EnvironmentalConfig.CLIENT.jungleFog.get());
			if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)) 
				replaceFogValue(biome, EnvironmentalConfig.CLIENT.snowyFog.get());
			if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) 
				replaceFogValue(biome, EnvironmentalConfig.CLIENT.swampFog.get());
		}
	}
	
    @OnlyIn(Dist.CLIENT)
	public static void replaceBiomeWaterColors() {
		replaceWaterColors(0x44AFF5, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS);
		replaceWaterColors(0x32A598, Biomes.DESERT);
		
		replaceWaterColors(0x1787D4, 0x1165b0, Biomes.OCEAN);
		replaceWaterColors(0x1787D4, 0x1463a5, Biomes.DEEP_OCEAN);
		
		replaceWaterColors(0x905957, Biomes.NETHER_WASTES, Biomes.SOUL_SAND_VALLEY, Biomes.WARPED_FOREST, Biomes.CRIMSON_FOREST, Biomes.BASALT_DELTAS);
		replaceWaterColors(0x62529e, Biomes.THE_END, Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS);
	}
    
    @OnlyIn(Dist.CLIENT)
    private static void replaceFogValue(Biome biome, int color) {
    	if (EnvironmentalConfig.CLIENT.customFogColors.get()) {
    		biome.field_235052_p_.fogColor = color;
    	} else {
    		biome.field_235052_p_.fogColor = 12638463;
    	}
    }
    
    @OnlyIn(Dist.CLIENT)
    private static void replaceWaterColors(int color, Biome... biomes) {
    	replaceWaterColors(color, color, biomes);
    }
    
    @OnlyIn(Dist.CLIENT)
    private static void replaceWaterColors(int waterColor, int fogColor, Biome... biomes) {
    	for (Biome biome : biomes) {
    		if (EnvironmentalConfig.CLIENT.bedrockWaterColors.get()) {
    			if (!BIOME_WATER_MAP.containsKey(biome))
    				BIOME_WATER_MAP.put(biome, biome.getWaterColor());
    			if (!BIOME_WATER_FOG_MAP.containsKey(biome))
    				BIOME_WATER_FOG_MAP.put(biome, biome.getWaterFogColor());
        		biome.field_235052_p_.waterColor = waterColor;
        		biome.field_235052_p_.waterFogColor = fogColor;
        	} else if (!BIOME_WATER_MAP.isEmpty() && !BIOME_WATER_FOG_MAP.isEmpty()) {
        		biome.field_235052_p_.waterColor = BIOME_WATER_MAP.get(biome);
        		biome.field_235052_p_.waterFogColor = BIOME_WATER_FOG_MAP.get(biome);
        	}
    	}
    }
}
