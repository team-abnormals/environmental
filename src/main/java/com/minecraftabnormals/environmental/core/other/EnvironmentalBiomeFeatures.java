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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalBiomeFeatures {

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		MobSpawnInfoBuilder spawns = event.getSpawns();

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MARSH.getKey(), EnvironmentalBiomes.MUSHROOM_MARSH.getKey())) {
			EnvironmentalBiomeFeatures.withMarshFeatures(generation);
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.MUSHROOM_MARSH.getKey()))
				EnvironmentalBiomeFeatures.withMarshMushrooms(generation);

			DefaultBiomeFeatures.withPassiveMobs(spawns);
			DefaultBiomeFeatures.withBatsAndHostiles(spawns);
		}

		if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_WOODS.getKey(), EnvironmentalBiomes.BLOSSOM_HILLS.getKey(), EnvironmentalBiomes.BLOSSOM_HIGHLANDS.getKey(), EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey())) {
			if (DataUtil.matchesKeys(biome, EnvironmentalBiomes.BLOSSOM_VALLEYS.getKey()))
				EnvironmentalBiomeFeatures.withBlossomValleysFeatures(generation);
			else
				EnvironmentalBiomeFeatures.withBlossomWoodsFeatures(generation);

			DefaultBiomeFeatures.withPassiveMobs(spawns);
			DefaultBiomeFeatures.withBatsAndHostiles(spawns);
			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PANDA, 80, 1, 2));
			spawns.withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EnvironmentalEntities.KOI.get(), 12, 1, 3));
		}

		if (event.getCategory() == Biome.Category.SWAMP)
			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.SLABFISH.get(), 8, 2, 4));

		if (DataUtil.matchesKeys(biome, Biomes.SWAMP, Biomes.SWAMP_HILLS)) {
			EnvironmentalBiomeFeatures.removeSwampTrees(generation);
			EnvironmentalBiomeFeatures.withMushrooms(generation);
			EnvironmentalBiomeFeatures.withCattails(generation);
			EnvironmentalBiomeFeatures.withMudDisks(generation);
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SWAMP_OAK);
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WILLOW_TREE);
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_SWAMP);
		}

		if (event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.SWAMP)
			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.DUCK.get(), 5, 2, 4));

		if (event.getCategory() == Biome.Category.FOREST) {
			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.DEER.get(), 16, 1, 4));
			if (DataUtil.matchesKeys(biome, Biomes.FLOWER_FOREST)) {
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_CARTWHEEL);
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DELPHINIUMS);
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE);
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.WISTERIA_TREE_BIG);
			}
			if (DataUtil.matchesKeys(biome, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS)) {
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLUEBELL);
			}
		}

		if (event.getCategory() == Biome.Category.EXTREME_HILLS)
			spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EnvironmentalEntities.YAK.get(), 20, 2, 4));

		if (event.getCategory() == Biome.Category.MUSHROOM)
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_MYCELIUM_SPROUTS);

		if (DataUtil.matchesKeys(biome, Biomes.FROZEN_RIVER) && (event.getCategory() == Biome.Category.SWAMP || event.getCategory() == Biome.Category.RIVER)) {
			EnvironmentalBiomeFeatures.withCattails(generation);
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_DUCK);
		}

		if (event.getCategory() == Biome.Category.DESERT)
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH);

		if (event.getCategory() == Biome.Category.MESA)
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_DEAD_BUSH_MESA);

		if (event.getCategory() == Biome.Category.SAVANNA) {
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_ALLIUM);
			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);
		}

		if (event.getCategory() == Biome.Category.TAIGA)
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_VIOLET);

		if (biome.toString().contains("rosewood") && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_SAVANNA);

		if (event.getCategory() == Biome.Category.PLAINS && EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_PLAINS);

		if (biome.toString().contains("maple") || biome.toString().contains("pumpkin_fields")) {
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_TURKEY);
		} else if (event.getCategory() == Biome.Category.FOREST) {
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.NEST_CHICKEN);
		}

		if (event.getCategory() == Biome.Category.JUNGLE) {
			if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_JUNGLE);
			generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BIRD_OF_PARADISE);

			if (DataUtil.matchesKeys(biome, Biomes.JUNGLE_EDGE, Biomes.MODIFIED_JUNGLE_EDGE))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_YELLOW_HIBISCUS);
			if (DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE_HILLS))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_ORANGE_HIBISCUS);
			if (DataUtil.matchesKeys(biome, Biomes.JUNGLE))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_PINK_HIBISCUS);
			if (DataUtil.matchesKeys(biome, Biomes.MODIFIED_JUNGLE))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_RED_HIBISCUS);
			if (DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_MAGENTA_HIBISCUS);
			if (DataUtil.matchesKeys(biome, Biomes.JUNGLE_HILLS))
				generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_PURPLE_HIBISCUS);
		}

		if (EnvironmentalConfig.COMMON.limitFarmAnimalSpawns.get())
			removeSpawns(event);
	}

	public static void withMarshFeatures(BiomeGenerationSettingsBuilder builder) {
		builder.withStructure(StructureFeatures.SWAMP_HUT);
		builder.withStructure(StructureFeatures.MINESHAFT);
		builder.withStructure(StructureFeatures.RUINED_PORTAL_SWAMP);

		DefaultBiomeFeatures.withCavesAndCanyons(builder);
		DefaultBiomeFeatures.withMonsterRoom(builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
		DefaultBiomeFeatures.withOverworldOres(builder);
		DefaultBiomeFeatures.withClayDisks(builder);
		DefaultBiomeFeatures.withFrozenTopLayer(builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
		DefaultBiomeFeatures.withTallGrass(builder);
		DefaultBiomeFeatures.withJungleGrass(builder);
		DefaultBiomeFeatures.withSwampSugarcaneAndPumpkin(builder);
		DefaultBiomeFeatures.withFossils(builder);

		EnvironmentalBiomeFeatures.withMudDisks(builder);
		EnvironmentalBiomeFeatures.withMarshVegetation(builder);
		EnvironmentalBiomeFeatures.withMarshPonds(builder);

		if (EnvironmentalConfig.COMMON.generateGiantTallGrass.get())
			builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GIANT_TALL_GRASS_MARSH);
	}

	public static void withBaseBlossomFeatures(BiomeGenerationSettingsBuilder builder) {
		DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
		builder.withStructure(StructureFeatures.RUINED_PORTAL);

		DefaultBiomeFeatures.withCavesAndCanyons(builder);
		DefaultBiomeFeatures.withMonsterRoom(builder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
		DefaultBiomeFeatures.withOverworldOres(builder);
		DefaultBiomeFeatures.withDisks(builder);
		DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
		DefaultBiomeFeatures.withTallGrass(builder);
		DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
		DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
		DefaultBiomeFeatures.withFrozenTopLayer(builder);

		EnvironmentalBiomeFeatures.withBlossomVegetation(builder);
	}

	public static void withBlossomWoodsFeatures(BiomeGenerationSettingsBuilder builder) {
		EnvironmentalBiomeFeatures.withBaseBlossomFeatures(builder);
		EnvironmentalBiomeFeatures.withBlossomWoodsVegetation(builder);
	}

	public static void withBlossomValleysFeatures(BiomeGenerationSettingsBuilder builder) {
		EnvironmentalBiomeFeatures.withBaseBlossomFeatures(builder);
		EnvironmentalBiomeFeatures.withBlossomValleysVegetation(builder);
	}

	public static void withMushrooms(BiomeGenerationSettingsBuilder builder) {
		if (EnvironmentalConfig.COMMON.generateGiantMushroomsInSwamps.get()) {
			builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.MUSHROOM_FIELD_VEGETATION);
		}
	}

	public static void withMarshMushrooms(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.HUGE_BROWN_MUSHROOM_MARSH);
	}

	public static void withMudDisks(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, EnvironmentalFeatures.Configured.DISK_MUD);
	}

	public static void withBlossomVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_SUGAR_CANE_BLOSSOM);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLOSSOM_WOODS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_TALL_BLOSSOM_WOODS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_BLOSSOM_WOODS);
	}

	public static void withBlossomWoodsVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_WOODS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_WOODS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_WOODS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_RED_LOTUS);
	}

	public static void withBlossomValleysVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.CHERRY_TREE_VALLEY);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FALLEN_CHERRY_LEAVES_VALLEY);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BIRCH_TREE_BLOSSOM_VALLEY);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_BLOSSOM_VALLEYS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.BAMBOO_LIGHT_BLOSSOM_VALLEYS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_WHITE_LOTUS);
	}

	public static void withMarshPonds(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SPRING_WATER_MARSH);
		builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, EnvironmentalFeatures.Configured.LAKE_WATER_MARSH);
	}

	public static void withCattails(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS);
	}

	public static void withMarshVegetation(BiomeGenerationSettingsBuilder builder) {
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.MARSH_OAK);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_BLUE_ORCHID);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_CORNFLOWER);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.FLOWER_DIANTHUS);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_GRASS_MARSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_WATERLILLY_MARSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_TALL_GRASS_MARSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.SEAGRASS_MARSH);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_RICE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_CATTAILS_DENSE);
		builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, EnvironmentalFeatures.Configured.PATCH_DUCKWEED_MARSH);
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
								if (tree.decorators.contains(LeaveVineTreeDecorator.field_236871_b_) && tree.maxWaterDepth == 1) {
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
		;
		spawns.getSpawner(EntityClassification.CREATURE).removeAll(entrysToRemove);
	}
}
