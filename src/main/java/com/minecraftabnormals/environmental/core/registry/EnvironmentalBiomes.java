package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.BiomeSubRegistryHelper;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomes {
	private static final BiomeSubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getBiomeSubHelper();

	public static final BiomeSubRegistryHelper.KeyedBiome MARSH = HELPER.createBiome("marsh", EnvironmentalBiomes::createMarshBiome);
	public static final BiomeSubRegistryHelper.KeyedBiome MUSHROOM_MARSH = HELPER.createBiome("mushroom_marsh", EnvironmentalBiomes::createMarshBiome);

	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_WOODS = HELPER.createBiome("blossom_woods", () -> createBlossomBiome(0.1F, 0.2F));
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_HILLS = HELPER.createBiome("blossom_hills", () -> createBlossomBiome(0.45F, 0.3F));
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_HIGHLANDS = HELPER.createBiome("blossom_highlands", () -> createBlossomBiome(2.0F, 0.15F));
	public static final BiomeSubRegistryHelper.KeyedBiome BLOSSOM_VALLEYS = HELPER.createBiome("blossom_valleys", () -> createBlossomBiome(0.1F, 0.01F));

	public static void addBiomesToGeneration() {
		BiomeUtil.addHillBiome(MARSH.getKey(), Pair.of(MARSH.getKey(), 3), Pair.of(MUSHROOM_MARSH.getKey(), 1));
		BiomeUtil.addHillBiome(BLOSSOM_WOODS.getKey(), Pair.of(BLOSSOM_HILLS.getKey(), 1));
		BiomeUtil.addHillBiome(BLOSSOM_HIGHLANDS.getKey(), Pair.of(BLOSSOM_VALLEYS.getKey(), 1));

		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MARSH.getKey(), EnvironmentalConfig.COMMON.marshWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MUSHROOM_MARSH.getKey(), EnvironmentalConfig.COMMON.mushroomMarshWeight.get()));

		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_WOODS.getKey(), EnvironmentalConfig.COMMON.blossomWoodsWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_HILLS.getKey(), EnvironmentalConfig.COMMON.blossomHillsWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_HIGHLANDS.getKey(), EnvironmentalConfig.COMMON.blossomHighlandsWeight.get()));
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BLOSSOM_VALLEYS.getKey(), EnvironmentalConfig.COMMON.blossomValleysWeight.get()));
	}

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
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(-0.25F).scale(0.0F).temperature(0.8F).downfall(0.9F).setEffects((new BiomeAmbience.Builder()).setWaterColor(6134398).setWaterFogColor(2302743).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.75F)).withGrassColor(6263617).withFoliageColor(6975545).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(new MobSpawnInfo.Builder().copy()).withGenerationSettings((new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j).build()).build();
	}

	private static Biome createBlossomBiome(float depth, float scale) {
		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.75F).downfall(0.8F).setEffects((new BiomeAmbience.Builder()).setWaterColor(5216182).setWaterFogColor(335411).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.75F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(new MobSpawnInfo.Builder().copy()).withGenerationSettings((new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j).build()).build();
	}

	private static int getSkyColorWithTemperatureModifier(float temperature) {
		float lvt_1_1_ = temperature / 3.0F;
		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
		return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}
}
