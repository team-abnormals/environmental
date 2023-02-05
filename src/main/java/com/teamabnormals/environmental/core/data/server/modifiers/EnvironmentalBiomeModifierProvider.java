package com.teamabnormals.environmental.core.data.server.modifiers;

import com.mojang.serialization.JsonOps;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnvironmentalBiomeModifierProvider {

	public static JsonCodecProvider<BiomeModifier> create(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		RegistryAccess access = RegistryAccess.builtinCopy();
		Registry<Biome> biomeRegistry = access.registryOrThrow(Registry.BIOME_REGISTRY);
		Registry<PlacedFeature> placedFeatures = access.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

		addModifier(modifiers, "add_spawn/slabfish", new AddSpawnsBiomeModifier(tag(biomeRegistry, EnvironmentalBiomeTags.HAS_SLABFISH), List.of(new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.SLABFISH.get(), 10, 2, 4))));
		addModifier(modifiers, "add_spawn/duck", new AddSpawnsBiomeModifier(tag(biomeRegistry, EnvironmentalBiomeTags.HAS_DUCK), List.of(new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DUCK.get(), 5, 2, 4))));
		addModifier(modifiers, "add_spawn/deer", new AddSpawnsBiomeModifier(tag(biomeRegistry, EnvironmentalBiomeTags.HAS_DEER), List.of(new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.DEER.get(), 16, 4, 4))));
		addModifier(modifiers, "add_spawn/yak", new AddSpawnsBiomeModifier(tag(biomeRegistry, EnvironmentalBiomeTags.HAS_YAK), List.of(new MobSpawnSettings.SpawnerData(EnvironmentalEntityTypes.YAK.get(), 20, 4, 4))));

//		addModifier(modifiers, "remove_spawn/farm_animals", new RemoveSpawnsBiomeModifier(tag(biomeRegistry, BiomeTags.IS_OVERWORLD), HolderSet.direct(List.of(ForgeRegistries.ENTITY_TYPES.getHolder(EntityType.CHICKEN).get(), ForgeRegistries.ENTITY_TYPES.getHolder(EntityType.COW).get(), ForgeRegistries.ENTITY_TYPES.getHolder(EntityType.PIG).get(), ForgeRegistries.ENTITY_TYPES.getHolder(EntityType.SHEEP).get()))));
//		addModifier(modifiers, "add_spawn/plains_farm_animals", new AddSpawnsBiomeModifier(biome(biomeRegistry, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS), List.of(new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.COW, 10, 4, 4))));
//		addModifier(modifiers, "add_spawn/forest_farm_animals", new AddSpawnsBiomeModifier(tag(biomeRegistry, BiomeTags.IS_FOREST), List.of(new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4))));

		addModifier(modifiers, "add_feature/bluebell", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.DARK_FOREST), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_BLUEBELL), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/violet", new AddFeaturesBiomeModifier(tag(biomeRegistry, BiomeTags.IS_TAIGA), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_VIOLET), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/warm_hibiscus", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.SPARSE_JUNGLE), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_WARM), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/cool_hibiscus", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.JUNGLE), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_HIBISCUS_COOL), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/cattails", new AddFeaturesBiomeModifier(tag(biomeRegistry, EnvironmentalBiomeTags.HAS_CATTAILS), of(placedFeatures, EnvironmentalPlacedFeatures.CATTAILS), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/tall_dead_bush", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.DESERT), of(placedFeatures, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/tall_dead_bush_badlands", new AddFeaturesBiomeModifier(tag(biomeRegistry, BiomeTags.IS_BADLANDS), of(placedFeatures, EnvironmentalPlacedFeatures.PATCH_TALL_DEAD_BUSH_BADLANDS), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/mycelium_sprouts", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.MUSHROOM_FIELDS), of(placedFeatures, EnvironmentalPlacedFeatures.PATCH_MYCELIUM_SPROUTS), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/mud_disks", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.SWAMP), of(placedFeatures, EnvironmentalPlacedFeatures.DISK_MUD), GenerationStep.Decoration.UNDERGROUND_ORES));

		addModifier(modifiers, "add_feature/swamp_vegetation", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.SWAMP), of(placedFeatures, EnvironmentalPlacedFeatures.HUGE_SWAMP_MUSHROOMS, EnvironmentalPlacedFeatures.TREES_SWAMP, EnvironmentalPlacedFeatures.PATCH_DUCKWEED), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/flower_forest_vegetation", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.FLOWER_FOREST), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_CARTWHEEL, EnvironmentalPlacedFeatures.PATCH_DELPHINIUMS, EnvironmentalPlacedFeatures.TREES_WISTERIA), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/savanna_vegetation", new AddFeaturesBiomeModifier(tag(biomeRegistry, BiomeTags.IS_SAVANNA), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_ALLIUM, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_SAVANNA), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/plains_vegetation", new AddFeaturesBiomeModifier(biome(biomeRegistry, Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS), of(placedFeatures, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_PLAINS), GenerationStep.Decoration.VEGETAL_DECORATION));
		addModifier(modifiers, "add_feature/jungle_vegetation", new AddFeaturesBiomeModifier(tag(biomeRegistry, BiomeTags.IS_JUNGLE), of(placedFeatures, EnvironmentalPlacedFeatures.FLOWER_BIRD_OF_PARADISE, EnvironmentalPlacedFeatures.PATCH_GIANT_TALL_GRASS_JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION));

		return JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, Environmental.MOD_ID, RegistryOps.create(JsonOps.INSTANCE, access), ForgeRegistries.Keys.BIOME_MODIFIERS, modifiers);
	}

	private static HolderSet<Biome> tag(Registry<Biome> biomeRegistry, TagKey<Biome> tagKey) {
		return new HolderSet.Named<>(biomeRegistry, tagKey);
	}

	private static void addModifier(HashMap<ResourceLocation, BiomeModifier> modifiers, String name, BiomeModifier modifier) {
		modifiers.put(new ResourceLocation(Environmental.MOD_ID, name), modifier);
	}

	@SafeVarargs
	@SuppressWarnings("ConstantConditions")
	private static HolderSet<PlacedFeature> of(Registry<PlacedFeature> placedFeatures, RegistryObject<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(registryObject -> placedFeatures.getOrCreateHolderOrThrow(registryObject.getKey())).collect(Collectors.toList()));
	}

	@SafeVarargs
	@SuppressWarnings("ConstantConditions")
	private static HolderSet<Biome> biome(Registry<Biome> biomeRegistry, ResourceKey<Biome>... biomes) {
		return HolderSet.direct(Stream.of(biomes).map(biomeRegistry::getOrCreateHolderOrThrow).collect(Collectors.toList()));
	}
}