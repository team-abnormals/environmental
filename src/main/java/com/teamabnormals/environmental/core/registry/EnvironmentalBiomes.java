package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomes {
	public static final BiomeSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBiomeSubHelper();

	public static final BiomeSubRegistryHelper.KeyedBiome MARSH = HELPER.createBiome("marsh", EnvironmentalBiomes::marsh);

	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_WOODS = HELPER.createBiome("blossom_woods", EnvironmentalBiomes::blossomWoods);
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_VALLEYS = HELPER.createBiome("blossom_valleys", EnvironmentalBiomes::blossomValleys);

	private static Biome marsh() {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		EnvironmentalGeneration.withMarshFeatures(generation);
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
		Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP);
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).temperature(0.8F).downfall(0.9F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(6134398).waterFogColor(2302743).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.75F)).grassColorOverride(6263617).foliageColorOverride(6975545).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static Biome blossomWoods() {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.withBlossomWoodsFeatures(generation);
		return blossomBiome(generation);
	}

	private static Biome blossomValleys() {
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
		EnvironmentalGeneration.withBlossomValleysFeatures(generation);
		return blossomBiome(generation);
	}

	private static Biome blossomBiome(BiomeGenerationSettings.Builder generation) {
		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(spawns);
		BiomeDefaultFeatures.commonSpawns(spawns);
		spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 80, 1, 2));
		spawns.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.KOI.get(), 12, 1, 3));
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).temperature(0.75F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5216182).waterFogColor(335411).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.75F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}
