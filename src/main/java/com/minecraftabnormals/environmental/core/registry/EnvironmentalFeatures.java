package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.minecraftabnormals.environmental.common.world.gen.feature.*;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {
    public static final Feature<BlockClusterFeatureConfig> DIRECTIONAL_FLOWER = new DirectionalFlowersFeature<BlockClusterFeatureConfig>(BlockClusterFeatureConfig.field_236587_a_);
    public static final Feature<NoFeatureConfig> CATTAILS = new CattailsFeature(NoFeatureConfig.field_236558_a_);
    public static final Feature<NoFeatureConfig> DENSE_CATTAILS = new DenseCattailsFeature(NoFeatureConfig.field_236558_a_);
    public static final Feature<NoFeatureConfig> RICE = new RiceFeature(NoFeatureConfig.field_236558_a_);
    public static final Feature<BaseTreeFeatureConfig> WISTERIA_TREE = new WisteriaTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG);
    public static final Feature<BaseTreeFeatureConfig> BIG_WISTERIA_TREE = new BigWisteriaTreeFeature(BaseTreeFeatureConfig.CODEC_BASE_TREE_FEATURE_CONFIG);

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                DIRECTIONAL_FLOWER.setRegistryName(Environmental.MODID, "directional_flower"),
                CATTAILS.setRegistryName(Environmental.MODID, "cattails"),
                DENSE_CATTAILS.setRegistryName(Environmental.MODID, "dense_cattails"),
                RICE.setRegistryName(Environmental.MODID, "rice"),
                WISTERIA_TREE.setRegistryName(Environmental.MODID, "wisteria_tree"),
                BIG_WISTERIA_TREE.setRegistryName(Environmental.MODID, "big_wisteria_tree"));
    }

    public static void generateFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(EnvironmentalFeatures::generate);
    }

    public static void generate(Biome biome) {
        boolean wisterias = EnvironmentalConfig.ValuesHolder.generateExternalWisterias();

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
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.DIANTHUS.get().getDefaultState(), biome, 5);
            if (wisterias) EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
        }

        if (biome.getTempCategory() != Biome.TempCategory.COLD && (biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER)) {
            EnvironmentalBiomeFeatures.addCattails(biome);
        }

        if (biome.getCategory() == Biome.Category.SAVANNA) {
            EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 3);
            EnvironmentalBiomeFeatures.addShortFlower(Blocks.ALLIUM.getDefaultState(), biome, 4);
        }

        if (biome.getCategory() == Biome.Category.TAIGA) {
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.VIOLET.get().getDefaultState(), biome, 4);
        }
        if (biome.getCategory() == Biome.Category.EXTREME_HILLS) {
            EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalBlocks.COLUMBINE.get().getDefaultState(), biome, 4);
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
        }

        if (biome.getCategory() == Biome.Category.ICY) {
            if (wisterias) EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
        }
    }
}