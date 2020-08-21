package com.minecraftabnormals.environmental.core;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EnvironmentalConfig {

    public static class Common {
        public final ConfigValue<Boolean> generateExtraWisterias;
        public final ConfigValue<Boolean> generateGiantMushroomsInSwamps;
        public final ConfigValue<Boolean> generateGiantTallGrass;

        public final ConfigValue<Integer> marshWeight;
        public final ConfigValue<Integer> mushroomMarshWeight;
        public final ConfigValue<Integer> cherryBlossomForestWeight;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Biome Weight Config")
            .push("biomes");

            marshWeight = builder
                    .comment("The weight of Marsh biomes; Default: 6")
                    .define("Marsh weight", 6);
            
            mushroomMarshWeight = builder
                    .comment("The weight of Mushroom Marsh biomes; Default: 0")
                    .define("Mushroom Marsh weight", 0);

            cherryBlossomForestWeight = builder
                    .comment("The weight of Cherry Blossom Forest biomes; Default: 3")
                    .define("Cherry Blossom Forest weight", 3);

            builder.pop();
            
            builder.comment("Feature Generation Config")
                    .push("features");

            generateExtraWisterias = builder
                    .comment("If Wisteria Trees generate outside of Flower Forests; Default: False")
                    .define("Wisteria Tree generation out of Flower Forests", false);

            generateGiantMushroomsInSwamps = builder
                    .comment("If Giant Mushrooms generate in Swamps, for bedrock parity; Default: True")
                    .define("Giant Mushroom generation in Swamps", true);

            generateGiantTallGrass = builder
                    .comment("If Giant Tall Grass generates; Default: True")
                    .define("Giant Tall Grass generation", true);

            builder.pop();
        }
    }
    
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}