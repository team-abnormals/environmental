package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalGeneration;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;
import java.util.List;

public class EnvironmentalBiomes {
	public static final ResourceKey<Biome> MARSH = create("marsh");
	public static final ResourceKey<Biome> BLOSSOM_WOODS = create("blossom_woods");
	public static final ResourceKey<Biome> BLOSSOM_VALLEYS = create("blossom_valleys");
	public static final ResourceKey<Biome> PINE_BARRENS = create("pine_barrens");
	public static final ResourceKey<Biome> SNOWY_PINE_BARRENS = create("snowy_pine_barrens");
	public static final ResourceKey<Biome> OLD_GROWTH_PINE_BARRENS = create("old_growth_pine_barrens");
	public static final ResourceKey<Biome> SNOWY_OLD_GROWTH_PINE_BARRENS = create("snowy_old_growth_pine_barrens");
	public static final ResourceKey<Biome> PINE_SLOPES = create("pine_slopes");
	public static final ResourceKey<Biome> CEDAR_SWAMP = create("cedar_swamp");
	public static final ResourceKey<Biome> CEDAR_RIVER = create("cedar_river");
	public static final ResourceKey<Biome> CEDAR_BANK = create("cedar_bank");;
	public static final ResourceKey<Biome> CEDAR_RIVERINE = create("cedar_riverine");

	public static final List<ResourceKey<Biome>> NATURAL_BIOMES = List.of(
			MARSH, BLOSSOM_WOODS, BLOSSOM_VALLEYS,
			PINE_BARRENS, SNOWY_PINE_BARRENS, OLD_GROWTH_PINE_BARRENS, SNOWY_OLD_GROWTH_PINE_BARRENS, PINE_SLOPES,
			CEDAR_SWAMP, CEDAR_RIVER
	);

	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

		context.register(MARSH, marsh(features, carvers));

		context.register(BLOSSOM_WOODS, blossomBiome(features, carvers, false));
		context.register(BLOSSOM_VALLEYS, blossomBiome(features, carvers, true));

		context.register(PINE_BARRENS, pineBarrens(features, carvers, false, false));
		context.register(SNOWY_PINE_BARRENS, pineBarrens(features, carvers, true, false));
		context.register(OLD_GROWTH_PINE_BARRENS, pineBarrens(features, carvers, false, true));
		context.register(SNOWY_OLD_GROWTH_PINE_BARRENS, pineBarrens(features, carvers, true, true));
		context.register(PINE_SLOPES, pineSlopes(features, carvers));

		context.register(CEDAR_SWAMP, cedarSwamp(features, carvers));
		context.register(CEDAR_RIVER, cedarCreek(features, carvers, false));
		context.register(CEDAR_BANK, cedarCreek(features, carvers, true));
		context.register(CEDAR_RIVERINE, cedarRiverine(features, carvers));
	}

	public static ResourceKey<Biome> create(String name) {
		return ResourceKey.create(Registries.BIOME, Environmental.location(name));
	}

	private static Biome marsh(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.marsh(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(true, 0.8F, 0.9F, 6263617, 6975545, 6134398, 2302743, 12638463, spawns, generation, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP));
	}

	private static Biome blossomBiome(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers, boolean valley) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.blossomWoods(generation, valley);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 1, 2));
		spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.KOI.get(), 8, 1, 1));

		return biome(true, 0.45F, 0.8F, 5938278, 5216182, 335411, spawns, generation, null);
	}

	private static Biome pineBarrens(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers, boolean snowy, boolean oldGrowth) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.pineBarrens(generation, snowy, oldGrowth);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(true, snowy ? -0.2F : 0.4F, snowy ? 0.4F : 0.6F, snowy ? 8828286 : oldGrowth ? 9221482 : 9484136, snowy ? 6789454 : 7578444, 4286597, 2962245, 12638463, spawns, generation, null);
	}

	private static Biome pineSlopes(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.pineSlopes(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(true, 0.4F, 0.3F, 4286597, 2962245, spawns, generation, null);
	}

	private static Biome cedarSwamp(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.cedarSwamp(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
		spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 30, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));

		return biome(true, 0.4F, 0.9F, 9484136, 7578444, 6899245, 2233870, 12638463, spawns, generation, null);
	}

	private static Biome cedarCreek(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers, boolean edge) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.cedarCreek(generation, edge);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4))
				.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		return swamp(true, 0.4F, 0.9F, 9221482, 7578444, edge ? 11164171 : 8272935, edge ? 4859653 : 8272935, 12638463, spawns, generation, null);
	}

	private static Biome cedarRiverine(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);
		EnvironmentalGeneration.cedarRiverine(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4))
				.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 100, 1, 1));
		return swamp(true, 0.4F, 0.9F, 9221482, 7578444, 11164171, 4859653, 12638463, spawns, generation, null);
	}

	private static Biome biome(boolean precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome biome(boolean precipitation, float temperature, float downfall, int foliageColor, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome biome(boolean precipitation, float temperature, float downfall, int grassColor, int foliageColor, int waterColor, int waterFogColor, int fogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).grassColorOverride(grassColor).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome swamp(boolean precipitation, float temperature, float downfall, int grassColor, int foliageColor, int waterColor, int waterFogColor, int fogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).hasPrecipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).grassColorOverride(grassColor).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static int calculateSkyColor(float temperature) {
		float clampedTemp = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - clampedTemp * 0.05F, 0.5F + clampedTemp * 0.1F, 1.0F);
	}
}
