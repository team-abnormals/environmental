package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.minecraftabnormals.environmental.common.world.gen.feature.BigWisteriaTreeFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.CattailsFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.ChickenNestFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.DenseCattailsFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.DirectionalFlowersFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.DuckNestFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.FallenLeavesFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.RiceFeature;
import com.minecraftabnormals.environmental.common.world.gen.feature.WisteriaTreeFeature;
import com.minecraftabnormals.environmental.common.world.gen.util.WisteriaColor;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Environmental.MODID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> DIRECTIONAL_FLOWER = FEATURES.register("directional_flower", () -> new DirectionalFlowersFeature<BlockClusterFeatureConfig>(BlockClusterFeatureConfig.field_236587_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoFeatureConfig.field_236558_a_));

    public static final RegistryObject<Feature<NoFeatureConfig>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> RICE = FEATURES.register("rice", () -> new RiceFeature(NoFeatureConfig.field_236558_a_));
    
    public static final RegistryObject<Feature<NoFeatureConfig>> DUCK_NEST = FEATURES.register("duck_nest", () -> new DuckNestFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> CHICKEN_NEST = FEATURES.register("chicken_nest", () -> new ChickenNestFeature(NoFeatureConfig.field_236558_a_));
    
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BIG_WISTERIA_TREE = FEATURES.register("big_wisteria_tree", () -> new BigWisteriaTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG));

    public static void generateFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(EnvironmentalFeatures::generate);
    }

    public static void generate(Biome biome) {
        boolean wisterias = EnvironmentalConfig.COMMON.generateExtraWisterias.get();

        if (biome.getCategory() == Biome.Category.MUSHROOM) {
        	EnvironmentalBiomeFeatures.addMyceliumSprouts(biome);
        }
        
        if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
            EnvironmentalBiomeFeatures.overrideFeatures(biome);
            EnvironmentalBiomeFeatures.addMushrooms(biome);
            EnvironmentalBiomeFeatures.addWillowTrees(biome);
            EnvironmentalBiomeFeatures.addSwampOaks(biome);
            EnvironmentalBiomeFeatures.addCattails(biome);
            EnvironmentalBiomeFeatures.addDuckweed(biome, 0.15F);
            EnvironmentalBiomeFeatures.addMudDisks(biome);
            if (wisterias) EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
        }

        if (biome.getTempCategory() != Biome.TempCategory.COLD && (biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER)) {
            EnvironmentalBiomeFeatures.addCattails(biome);
            EnvironmentalBiomeFeatures.addDuckNests(biome);
        }

        if (biome.getCategory() == Biome.Category.SAVANNA) {
            EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 3);
            EnvironmentalBiomeFeatures.addShortFlower(Blocks.ALLIUM.getDefaultState(), biome, 3);
        }

        if (biome.getCategory() == Biome.Category.TAIGA) {
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.VIOLET.get().getDefaultState(), biome, 4);
        }
        
        if (biome.getRegistryName().toString().contains("rosewood")) {
            EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 3);
        }

        if (biome.getCategory() == Biome.Category.JUNGLE) {
            EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 5);
            EnvironmentalBiomeFeatures.addDoubleFlower(EnvironmentalBlocks.BIRD_OF_PARADISE.get().getDefaultState(), biome, 5);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.YELLOW_HIBISCUS.get().getDefaultState(), biome, 1);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.ORANGE_HIBISCUS.get().getDefaultState(), biome, 1);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.RED_HIBISCUS.get().getDefaultState(), biome, 1);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.PINK_HIBISCUS.get().getDefaultState(), biome, 1);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.MAGENTA_HIBISCUS.get().getDefaultState(), biome, 1);
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.PURPLE_HIBISCUS.get().getDefaultState(), biome, 1);
            if (wisterias) EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.PINK, 0, 0.1F, true);
        }

        if (biome.getCategory() == Biome.Category.PLAINS) {
            EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 1);
            if (wisterias)
                EnvironmentalBiomeFeatures.addWisteriaTreeBeehive(biome, WisteriaColor.PURPLE, 0, 0.001F, true);
        }

        if (biome.getCategory() == Biome.Category.FOREST) {
            if (biome == Biomes.FLOWER_FOREST) {
                EnvironmentalBiomeFeatures.addDirectionalFlower(EnvironmentalBlocks.CARTWHEEL.get().getDefaultState(), biome, 5);
                EnvironmentalBiomeFeatures.addDelphiniums(biome, 12);
                EnvironmentalBiomeFeatures.addWisteriaTreesBeehive(biome, 2, 0.01F, false);
            }
            if (biome == Biomes.DARK_FOREST || biome == Biomes.DARK_FOREST_HILLS) {
                EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.BLUEBELL.get().getDefaultState(), biome, 3);
            }
            EnvironmentalBiomeFeatures.addChickenNests(biome);
        }

        if (biome.getCategory() == Biome.Category.ICY) {
            if (wisterias) EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
        }
    }
}