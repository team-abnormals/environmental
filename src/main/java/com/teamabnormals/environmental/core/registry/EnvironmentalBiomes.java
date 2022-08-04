package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BiomeSubRegistryHelper;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomes {
	private static final BiomeSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBiomeSubHelper();

	public static final BiomeSubRegistryHelper.KeyedBiome MARSH = HELPER.createBiome("marsh", EnvironmentalBiomes::createMarshBiome);
	public static final BiomeSubRegistryHelper.KeyedBiome MUSHROOM_MARSH = HELPER.createBiome("mushroom_marsh", EnvironmentalBiomes::createMarshBiome);

	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_WOODS = HELPER.createBiome("blossom_woods", EnvironmentalBiomes::createBlossomBiome);
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_HILLS = HELPER.createBiome("blossom_hills", EnvironmentalBiomes::createBlossomBiome);
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_HIGHLANDS = HELPER.createBiome("blossom_highlands", EnvironmentalBiomes::createBlossomBiome);
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_VALLEYS = HELPER.createBiome("blossom_valleys", EnvironmentalBiomes::createBlossomBiome);

	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(MARSH.getKey(), Type.OVERWORLD, Type.PLAINS, Type.WET, Type.SWAMP);
		BiomeDictionary.addTypes(MUSHROOM_MARSH.getKey(), Type.OVERWORLD, Type.RARE, Type.WET, Type.PLAINS, Type.SWAMP);
		BiomeDictionary.addTypes(BLOSSOM_WOODS.getKey(), Type.OVERWORLD, Type.FOREST, Type.RARE);
		BiomeDictionary.addTypes(BLOSSOM_HILLS.getKey(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.HILLS);
		BiomeDictionary.addTypes(BLOSSOM_HIGHLANDS.getKey(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.MOUNTAIN);
		BiomeDictionary.addTypes(BLOSSOM_VALLEYS.getKey(), Type.OVERWORLD, Type.FOREST, Type.RARE, Type.PLAINS);

		BiomeDictionary.addTypes(Biomes.DEEP_FROZEN_OCEAN, Type.SNOWY);
	}

	private static Biome createMarshBiome() {
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.SWAMP).temperature(0.8F).downfall(0.9F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(6134398).waterFogColor(2302743).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.75F)).grassColorOverride(6263617).foliageColorOverride(6975545).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).build()).build();
	}

	private static Biome createBlossomBiome() {
		return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.FOREST).temperature(0.75F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(5216182).waterFogColor(335411).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.75F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings((new BiomeGenerationSettings.Builder()).build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}
