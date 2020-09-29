package com.minecraftabnormals.environmental.core;

import org.apache.commons.lang3.tuple.Pair;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBiomes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalConfig {

    public static class Common {
        public final ConfigValue<Boolean> generateExtraWisterias;
        public final ConfigValue<Boolean> generateGiantMushroomsInSwamps;
        public final ConfigValue<Boolean> generateGiantTallGrass;

        public final ConfigValue<Integer> marshWeight;
        public final ConfigValue<Integer> mushroomMarshWeight;
        
        public final ConfigValue<Integer> blossomWoodsWeight;
        public final ConfigValue<Integer> blossomHillsWeight;
        public final ConfigValue<Integer> blossomHighlandsWeight;
        public final ConfigValue<Integer> blossomValleysWeight;
        
        public final ConfigValue<Boolean> limitFarmAnimalSpawns;
        public final ConfigValue<Boolean> biomeVariantsAlwaysSpawn;

        Common(ForgeConfigSpec.Builder builder) {
        	builder.push("worldgen");
            	builder.push("biomes");
            		builder.push("marsh");
            			marshWeight = builder.define("Marsh weight", 6);
            			mushroomMarshWeight = builder.define("Mushroom Marsh weight", 0);
            		builder.pop();
            		builder.push("blossom");
            			blossomWoodsWeight = builder.define("Blossom Woods weight", 3);
            			blossomHillsWeight = builder.define("Blossom Hills weight", 0);
            			blossomHighlandsWeight = builder.define("Blossom Highlands weight", 1);
            			blossomValleysWeight = builder.define("Blossom Valleys weight", 0);
            			builder.pop();
            		builder.pop();
            	builder.push("features");
            		generateExtraWisterias = builder.define("Wisteria Tree generation out of Flower Forests", false);
            		generateGiantMushroomsInSwamps = builder.define("Giant Mushroom generation in Swamps", true);
            		generateGiantTallGrass = builder.define("Giant Tall Grass generation", true);            	
            	builder.pop();
            builder.pop();
            
            builder.push("entities");
            limitFarmAnimalSpawns = builder.comment("Make farm animals spawn in less biomes to allow new mobs to take their place and diversify biome spawns").define("Limit farm animal spawns", true);
            biomeVariantsAlwaysSpawn = builder.comment("Make biome variants of mobs like Husk always spawn in place of their original in their biomes").define("Biome variants always spawn", true);
            builder.pop();
        }
    }
    
    public static class Client {

        public final ConfigValue<Boolean> customFogColors;
        public final ConfigValue<Integer> desertFog;
        public final ConfigValue<Integer> jungleFog;
        public final ConfigValue<Integer> snowyFog;
        public final ConfigValue<Integer> swampFog;
        
        public final ConfigValue<Boolean> bedrockWaterColors;
    
        Client(ForgeConfigSpec.Builder builder) {
        	builder.push("ambience");
	    		bedrockWaterColors = builder.define("Enable Bedrock water colors", true);
	    		customFogColors = builder.define("Enable custom fog colors", true);
	    		builder.push("fogs");
	    			desertFog = builder.define("Desert Fog decimal value", 14539186);
	    			jungleFog = builder.define("Jungle Fog decimal value", 11591080);
	    			snowyFog = builder.define("Snowy Fog decimal value", 16777215);
	    			swampFog = builder.define("Swamp Fog decimal value", 11595468);
	    		builder.pop();
        	builder.pop();
        }
    }
    
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
        
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void onConfigReload(final ModConfig.ModConfigEvent event) {
		EnvironmentalBiomes.replaceBiomeFogColors();
		EnvironmentalBiomes.replaceBiomeWaterColors();
	}
}