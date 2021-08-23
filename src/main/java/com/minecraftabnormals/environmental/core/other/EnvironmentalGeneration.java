package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalGeneration {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEarlyBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnInfoBuilder spawns = event.getSpawns();

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MARSH.getKey(), EnvironmentalBiomes.MUSHROOM_MARSH.getKey())) {
			EnvironmentalGeneration.withMarshFeatures(generation);
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MUSHROOM_MARSH.getKey()))
				EnvironmentalGeneration.withMarshMushrooms(generation);

			DefaultBiomeFeatures.farmAnimals(spawns);
			DefaultBiomeFeatures.commonSpawns(spawns);
		}

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_WOODS.getKey(), EnvironmentalBiomes.BLOSSOM_HILLS.getKey(), EnvironmentalBiomes.BLOSSOM_HIGHLANDS.getKey(), EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey())) {
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey()))
				EnvironmentalGeneration.withBlossomValleysFeatures(generation);
			else
				EnvironmentalGeneration.withBlossomWoodsFeatures(generation);

			DefaultBiomeFeatures.farmAnimals(spawns);
			DefaultBiomeFeatures.commonSpawns(spawns);
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 80, 1, 2));
			spawns.addSpawn(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EnvironmentalEntities.KOI.get(), 12, 1, 3));
		}
	}

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnInfoBuilder spawns = event.getSpawns();

		if (event.getCategory() == Biome.Category.SWAMP)
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.SLABFISH.get(), 8, 2, 4));

		if (DataUtil.matchesKeys(biome, Biomes.SWAMP, Biomes.SWAMP_HILLS)) {
			EnvironmentalGeneration.removeSwampTrees(generation);
			EnvironmentalGeneration.withMushrooms(generation);
			EnvironmentalGeneration.withCattails(generation);
			EnvironmentalGeneration.withMudDisks(generation);
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SWAMP_OAK);
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WILLOW_TREE);
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_SWAMP);
		}

		if (event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.SWAMP)
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.DUCK.get(), 5, 2, 4));

		if (event.getCategory() == Biome.Category.FOREST) {
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.DEER.get(), 16, 1, 4));
			if (DataUtil.matchesKeys(biome, Biomes.FLOWER_FOREST)) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_CARTWHEEL);
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DELPHINIUMS);
				if (EnvironmentalConfig.COMMON.generateWisteriaTrees.get()) {
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE);
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE_BIG);
				}
			}
			if (DataUtil.matchesKeys(biome, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS)) {
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLUEBELL);
			}
		}

		if (event.getCategory() == Biome.Category.EXTREME_HILLS)
			spawns.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.YAK.get(), 20, 2, 4));

		if (event.getCategory() == Biome.Category.MUSHROOM)
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_MYCELIUM_SPROUTS);

		if (!DataUtil.matchesKeys(biome, Biomes.FROZEN_RIVER) && (event.getCategory() == Biome.Category.SWAMP || event.getCategory() == Biome.Category.RIVER)) {
			EnvironmentalGeneration.withCattails(generation);
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_DUCK);
		}

		if (event.getCategory() == Biome.Category.DESERT) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH);
			//spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.FENNEC_FOX.get(), 8, 2, 4));
		}

		if (event.getCategory() == Biome.Category.MESA)
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH_MESA);

		if (event.getCategory() == Biome.Category.SAVANNA) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_ALLIUM);
			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);
		}

		if (event.getCategory() == Biome.Category.TAIGA)
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_VIOLET);

		if (biome.toString().contains("rosewood") && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);

		if (event.getCategory() == Biome.Category.PLAINS && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_PLAINS);

		if (biome.toString().contains("maple") || biome.toString().contains("pumpkin_fields")) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_TURKEY);
		} else if (event.getCategory() == Biome.Category.FOREST) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_CHICKEN);
		}

		if (event.getCategory() == Biome.Category.JUNGLE) {
			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
				generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_JUNGLE);
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_BIRD_OF_PARADISE);

			if (EnvironmentalConfig.COMMON.generateHibiscus.get()) {
				if (DataUtil.matchesKeys(biome, Biomes.JUNGLE_EDGE, Biomes.MODIFIED_JUNGLE_EDGE))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_YELLOW_HIBISCUS);
				if (DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE_HILLS))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_ORANGE_HIBISCUS);
				if (DataUtil.matchesKeys(biome, Biomes.JUNGLE))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_PINK_HIBISCUS);
				if (DataUtil.matchesKeys(biome, Biomes.MODIFIED_JUNGLE))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_RED_HIBISCUS);
				if (DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_MAGENTA_HIBISCUS);
				if (DataUtil.matchesKeys(biome, Biomes.JUNGLE_HILLS))
					generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_PURPLE_HIBISCUS);
			}
		}

		if (EnvironmentalConfig.COMMON.limitFarmAnimalSpawns.get())
			removeSpawns(event);
	}

	public static void withMarshFeatures(BiomeGenerationSettingsBuilder builder) {
		builder.addStructureStart(StructureFeatures.SWAMP_HUT);
		builder.addStructureStart(StructureFeatures.MINESHAFT);
		builder.addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);

		DefaultBiomeFeatures.addDefaultCarvers(builder);
		DefaultBiomeFeatures.addDefaultMonsterRoom(builder);
		DefaultBiomeFeatures.addDefaultUndergroundVariety(builder);
		DefaultBiomeFeatures.addDefaultOres(builder);
		DefaultBiomeFeatures.addSwampClayDisk(builder);
		DefaultBiomeFeatures.addSurfaceFreezing(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSavannaGrass(builder);
		DefaultBiomeFeatures.addSwampExtraVegetation(builder);
		DefaultBiomeFeatures.addFossilDecoration(builder);

		if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_MARSH);

		EnvironmentalGeneration.withMudDisks(builder);
		EnvironmentalGeneration.withMarshVegetation(builder);
		EnvironmentalGeneration.withMarshPonds(builder);

	}

	public static void withBaseBlossomFeatures(BiomeGenerationSettingsBuilder builder) {
		DefaultBiomeFeatures.addDefaultOverworldLandStructures(builder);
		builder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);

		DefaultBiomeFeatures.addDefaultCarvers(builder);
		DefaultBiomeFeatures.addDefaultMonsterRoom(builder);
		DefaultBiomeFeatures.addDefaultUndergroundVariety(builder);
		DefaultBiomeFeatures.addDefaultOres(builder);
		DefaultBiomeFeatures.addDefaultSoftDisks(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSavannaGrass(builder);
		DefaultBiomeFeatures.addDefaultLakes(builder);
		DefaultBiomeFeatures.addDefaultSprings(builder);
		DefaultBiomeFeatures.addSurfaceFreezing(builder);

		EnvironmentalGeneration.withBlossomVegetation(builder);
	}

	public static void withBlossomWoodsFeatures(BiomeGenerationSettingsBuilder builder) {
		EnvironmentalGeneration.withBaseBlossomFeatures(builder);
		EnvironmentalGeneration.withBlossomWoodsVegetation(builder);
	}

	public static void withBlossomValleysFeatures(BiomeGenerationSettingsBuilder builder) {
		EnvironmentalGeneration.withBaseBlossomFeatures(builder);
		EnvironmentalGeneration.withBlossomValleysVegetation(builder);
	}

	public static void withMushrooms(BiomeGenerationSettingsBuilder builder) {
		if (EnvironmentalConfig.COMMON.generateGiantMushrooms.get()) {
			builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.MUSHROOM_FIELD_VEGETATION);
		}
	}

	public static void withMarshMushrooms(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.HUGE_BROWN_MUSHROOM_MARSH);
	}

	public static void withMudDisks(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, EnvironmentalFeatures.Configured.DISK_MUD);
	}

	public static void withBlossomVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_SUGAR_CANE_BLOSSOM);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_TALL_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_BLOSSOM_WOODS);
	}

	public static void withBlossomWoodsVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_WOODS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_RED_LOTUS);
	}

	public static void withBlossomValleysVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE_BLOSSOM_VALLEYS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES_BLOSSOM_VALLEYS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_VALLEYS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_VALLEYS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_VALLEYS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_WHITE_LOTUS);
	}

	public static void withMarshPonds(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SPRING_WATER_MARSH);
		builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, EnvironmentalFeatures.Configured.LAKE_WATER_MARSH);
	}

	public static void withCattails(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS);
	}

	public static void withMarshVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.MARSH_OAK);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLUE_ORCHID);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_CORNFLOWER);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_DIANTHUS);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_WATERLILLY_MARSH);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_MARSH);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_GRASS_MARSH);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS_DENSE);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_MARSH);
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SEAGRASS_MARSH);
	}

	public static void removeSwampTrees(BiomeGenerationSettingsBuilder builder) {
		for (GenerationStage.Decoration stage : GenerationStage.Decoration.values()) {
			List<Supplier<ConfiguredFeature<?, ?>>> toRemove = new ArrayList<>();
			List<Supplier<ConfiguredFeature<?, ?>>> configuredFeatures = builder.getFeatures(stage);
			for (Supplier<ConfiguredFeature<?, ?>> configuredFeatureSupplier : configuredFeatures) {
				IFeatureConfig config = configuredFeatureSupplier.get().config;
				if (config instanceof DecoratedFeatureConfig) {
					ConfiguredFeature<?, ?> decorated = ((DecoratedFeatureConfig) config).feature.get();
					if (decorated.config instanceof DecoratedFeatureConfig) {
						ConfiguredFeature<?, ?> decorated2 = ((DecoratedFeatureConfig) decorated.config).feature.get();
						if (decorated2.feature == Feature.TREE) {
							if (decorated2.config instanceof BaseTreeFeatureConfig) {
								BaseTreeFeatureConfig tree = (BaseTreeFeatureConfig) decorated2.config;
								if (tree.decorators.contains(LeaveVineTreeDecorator.INSTANCE) && tree.maxWaterDepth == 1) {
									toRemove.add(configuredFeatureSupplier);
								}
							}
						}
					}
				}
			}
			toRemove.forEach(configuredFeatures::remove);
		}
	}

	private static void removeSpawns(BiomeLoadingEvent event) {
		MobSpawnInfoBuilder spawns = event.getSpawns();
		List<MobSpawnInfo.Spawners> entrysToRemove = new ArrayList<>();
		for (MobSpawnInfo.Spawners entry : spawns.getSpawner(EntityClassification.CREATURE)) {
			if (event.getCategory() != Biome.Category.FOREST) {
				if (entry.type == EntityType.PIG || entry.type == EntityType.CHICKEN) {
					entrysToRemove.add(entry);
				}
			}
			if (event.getCategory() != Biome.Category.PLAINS) {
				if (entry.type == EntityType.COW || entry.type == EntityType.SHEEP) {
					entrysToRemove.add(entry);
				}
			}
		}
		spawns.getSpawner(EntityClassification.CREATURE).removeAll(entrysToRemove);
	}
}
