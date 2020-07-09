package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.team_abnormals.environmental.common.world.gen.feature.BigWisteriaTreeFeature;
import com.team_abnormals.environmental.common.world.gen.feature.CattailsFeature;
import com.team_abnormals.environmental.common.world.gen.feature.DenseCattailsFeature;
import com.team_abnormals.environmental.common.world.gen.feature.RiceFeature;
import com.team_abnormals.environmental.common.world.gen.feature.WisteriaTreeFeature;
import com.team_abnormals.environmental.common.world.gen.util.WisteriaColor;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.EnvironmentalConfig;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalFeatures {

	public static final Feature<NoFeatureConfig> CATTAILS 		= new CattailsFeature(NoFeatureConfig.field_236558_a_);
	public static final Feature<NoFeatureConfig> DENSE_CATTAILS = new DenseCattailsFeature(NoFeatureConfig.field_236558_a_);
	public static final Feature<NoFeatureConfig> RICE 			= new RiceFeature(NoFeatureConfig.field_236558_a_);
    public static final Feature<BaseTreeFeatureConfig> WISTERIA_TREE 		= new WisteriaTreeFeature(BaseTreeFeatureConfig.field_236676_a_);
    public static final Feature<BaseTreeFeatureConfig> BIG_WISTERIA_TREE 	= new BigWisteriaTreeFeature(BaseTreeFeatureConfig.field_236676_a_);

	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
		event.getRegistry().registerAll(
				CATTAILS.setRegistryName("cattails"), 
				DENSE_CATTAILS.setRegistryName("dense_cattails"), 
				RICE.setRegistryName("rice"), 
				WISTERIA_TREE.setRegistryName("wisteria_tree"),
                BIG_WISTERIA_TREE.setRegistryName("big_wisteria_tree"));
	}

	public static void generateFeatures() {
		ForgeRegistries.BIOMES.getValues().forEach(EnvironmentalFeatures::generate);
	}

	public static void generate(Biome biome) {
		if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
			EnvironmentalBiomeFeatures.overrideFeatures(biome);
			EnvironmentalBiomeFeatures.addMushrooms(biome);
			EnvironmentalBiomeFeatures.addWillowTrees(biome);
			EnvironmentalBiomeFeatures.addSwampOaks(biome);
			EnvironmentalBiomeFeatures.addCattails(biome);
			EnvironmentalBiomeFeatures.addDuckweed(biome, 0.15F);
		}
		
		if (biome.getTempCategory() != Biome.TempCategory.COLD && (biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER)) {
			EnvironmentalBiomeFeatures.addCattails(biome);
		}
		
		if (biome.getCategory() == Biome.Category.SAVANNA || biome.getRegistryName().toString().contains("rosewood")) {
			EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 3);
		}
		
		if (biome.getCategory() == Biome.Category.JUNGLE) {
			EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 5);
		}
		
		if (biome.getCategory() == Biome.Category.PLAINS) {
			EnvironmentalBiomeFeatures.addGiantTallGrass(biome, 1);
		}
		
		if (biome.getCategory() == Biome.Category.FOREST) {
            if (biome == Biomes.FLOWER_FOREST)  {
            	EnvironmentalBiomeFeatures.addDelphiniums(biome, 12);
            	EnvironmentalBiomeFeatures.addWisteriaTreesBeehive(biome, 2, 0.01F, false);
            }
        }
		
    	if (EnvironmentalConfig.ValuesHolder.generateExternalWisterias()) {
	        if (biome.getCategory() == Biome.Category.JUNGLE) {
	        	EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.PINK, 0, 0.1F, true);
	        }
	        else if (biome.getCategory() == Biome.Category.SWAMP) {
	        	EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
	        }
	        else if (biome.getCategory() == Biome.Category.PLAINS) {
	        	EnvironmentalBiomeFeatures.addWisteriaTreeBeehive(biome, WisteriaColor.PURPLE, 0, 0.001F, true);
	        }
	        else if (biome.getCategory() == Biome.Category.ICY) {
	        	EnvironmentalBiomeFeatures.addWisteriaTree(biome, WisteriaColor.BLUE, 0, 0.001F, true);
	        }
    	}
	}
}