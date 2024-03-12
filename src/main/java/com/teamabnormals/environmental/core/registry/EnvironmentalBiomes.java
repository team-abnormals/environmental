package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper.KeyedBiome;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalGeneration;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nullable;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomes {
	public static final BiomeSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBiomeSubHelper();

	public static final KeyedBiome MARSH = HELPER.createBiome("marsh", EnvironmentalBiomes::marsh);

	public static final KeyedBiome BLOSSOM_WOODS = HELPER.createBiome("blossom_woods", () -> blossomBiome(false));
	public static final KeyedBiome BLOSSOM_VALLEYS = HELPER.createBiome("blossom_valleys", () -> blossomBiome(true));

	public static final KeyedBiome PINE_BARRENS = HELPER.createBiome("pine_barrens", () -> pineBarrens(false, false));
	public static final KeyedBiome SNOWY_PINE_BARRENS = HELPER.createBiome("snowy_pine_barrens", () -> pineBarrens(true, false));
	public static final KeyedBiome OLD_GROWTH_PINE_BARRENS = HELPER.createBiome("old_growth_pine_barrens", () -> pineBarrens(false, true));
	public static final KeyedBiome SNOWY_OLD_GROWTH_PINE_BARRENS = HELPER.createBiome("snowy_old_growth_pine_barrens", () -> pineBarrens(true, true));
	public static final KeyedBiome PINE_SLOPES = HELPER.createBiome("pine_slopes", EnvironmentalBiomes::pineSlopes);

	private static Biome marsh() {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.marsh(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(Precipitation.RAIN, 0.8F, 0.9F, 6263617, 6975545, 6134398, 2302743, 12638463, spawns, generation, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP));
	}

	private static Biome blossomBiome(boolean valley) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.blossomWoods(generation, valley);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 1, 2));
		spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.KOI.get(), 8, 1, 1));

		return biome(Precipitation.RAIN, 0.45F, 0.8F, 5938278, 5216182, 335411, spawns, generation, null);
	}

	private static Biome pineBarrens(boolean snowy, boolean oldGrowth) {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.pineBarrens(generation, snowy, oldGrowth);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(snowy ? Precipitation.SNOW : Precipitation.RAIN, snowy ? -0.2F : 0.4F, snowy ? 0.4F : 0.6F, snowy ? 8697226 : 9221482, snowy ? 6725203 : 6397770, 4159204, 329011, 12638463, spawns, generation, null);
	}

	private static Biome pineSlopes() {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.pineSlopes(generation);

		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));

		return biome(Precipitation.RAIN, 0.4F, 0.6F, 4159204, 329011, spawns, generation, null);
	}

	private static Biome biome(Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome biome(Precipitation precipitation, float temperature, float downfall, int foliageColor, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome biome(Precipitation precipitation, float temperature, float downfall, int grassColor, int foliageColor, int waterColor, int waterFogColor, int fogColor, MobSpawnSettings.Builder spawns, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
		return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).grassColorOverride(grassColor).foliageColorOverride(foliageColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static int calculateSkyColor(float temperature) {
		float clampedTemp = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - clampedTemp * 0.05F, 0.5F + clampedTemp * 0.1F, 1.0F);
	}
}
