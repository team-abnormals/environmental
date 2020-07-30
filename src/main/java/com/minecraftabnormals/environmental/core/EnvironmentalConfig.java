package com.minecraftabnormals.environmental.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author SmellyModder(Luke Tonon)
 */
public class EnvironmentalConfig {

    public static class Common {
        public final ConfigValue<Boolean> generateExternalWisterias;
        public final ConfigValue<Boolean> giantMushroomsInSwamps;
        public final ConfigValue<Boolean> giantTallGrass;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common Config")
                    .push("common");

            generateExternalWisterias = builder
                    .comment("If Wisteria Trees generate outside of Flower Forests; Default: False")
                    .translation(makeTranslation("generateExternalWisterias"))
                    .define("generateExternalWisterias", false);

            giantMushroomsInSwamps = builder
                    .comment("If Giant Mushrooms generate in Swamps, for bedrock parity; Default: True")
                    .translation(makeTranslation("giantMushroomsInSwamps"))
                    .define("giantMushroomsInSwamps", false);

            giantTallGrass = builder
                    .comment("If Giant Tall Grass generates; Default: True")
                    .translation(makeTranslation("giantTallGrass"))
                    .define("giantTallGrass", false);

            builder.pop();
        }
    }

    private static String makeTranslation(String name) {
        return "environmental.config." + name;
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class ValuesHolder {
        private static boolean generateExternalWisterias;
        private static boolean giantMushroomsInSwamps;
        private static boolean giantTallGrass;

        public static void updateCommonValuesFromConfig(ModConfig config) {
            generateExternalWisterias = EnvironmentalConfig.COMMON.generateExternalWisterias.get();
            giantMushroomsInSwamps = EnvironmentalConfig.COMMON.giantMushroomsInSwamps.get();
            giantTallGrass = EnvironmentalConfig.COMMON.giantTallGrass.get();
        }

        public static boolean generateExternalWisterias() {
            return generateExternalWisterias;
        }

        public static boolean giantMushroomsInSwamps() {
            return giantMushroomsInSwamps;
        }

        public static boolean giantTallGrass() {
            return giantTallGrass;
        }
    }

}