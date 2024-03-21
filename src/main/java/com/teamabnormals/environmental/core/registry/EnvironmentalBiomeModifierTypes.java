package com.teamabnormals.environmental.core.registry;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Function;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalBiomeModifierTypes {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Environmental.MOD_ID);

	public static final RegistryObject<Codec<InvertedRemoveSpawnsBiomeModifier>> REMOVE_SPAWNS_INVERTED = BIOME_MODIFIER_SERIALIZERS.register("remove_spawns_inverted", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("inverted_biomes").forGetter(InvertedRemoveSpawnsBiomeModifier::biomes),
					RegistryCodecs.homogeneousList(ForgeRegistries.Keys.ENTITY_TYPES).fieldOf("entity_types").forGetter(InvertedRemoveSpawnsBiomeModifier::entityTypes)
			).apply(builder, InvertedRemoveSpawnsBiomeModifier::new))
	);

	public static final RegistryObject<Codec<InvertedRemoveSpawnsIgnoreBiomeModifier>> REMOVE_SPAWNS_INVERTED_WITH_IGNORE = BIOME_MODIFIER_SERIALIZERS.register("remove_spawns_inverted_ignore", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("inverted_biomes").forGetter(InvertedRemoveSpawnsIgnoreBiomeModifier::biomes),
					Biome.LIST_CODEC.fieldOf("ignored_biomes").forGetter(InvertedRemoveSpawnsIgnoreBiomeModifier::ignoredBiomes),
					RegistryCodecs.homogeneousList(ForgeRegistries.Keys.ENTITY_TYPES).fieldOf("entity_types").forGetter(InvertedRemoveSpawnsIgnoreBiomeModifier::entityTypes)
			).apply(builder, InvertedRemoveSpawnsIgnoreBiomeModifier::new))
	);

	public static final RegistryObject<Codec<AddSpawnsIgnoreBiomeModifier>> ADD_SPAWNS_WITH_IGNORE = BIOME_MODIFIER_SERIALIZERS.register("add_spawns_ignore", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddSpawnsIgnoreBiomeModifier::biomes),
					Biome.LIST_CODEC.fieldOf("ignored_biomes").forGetter(AddSpawnsIgnoreBiomeModifier::ignoredBiomes),
					new ExtraCodecs.EitherCodec<>(SpawnerData.CODEC.listOf(), SpawnerData.CODEC).xmap(
							either -> either.map(Function.identity(), List::of),
							list -> list.size() == 1 ? Either.right(list.get(0)) : Either.left(list)
					).fieldOf("spawners").forGetter(AddSpawnsIgnoreBiomeModifier::spawners)
			).apply(builder, AddSpawnsIgnoreBiomeModifier::new))
	);

	public static final RegistryObject<Codec<AddFeaturesIgnoreBiomeModifier>> ADD_FEATURES_WITH_IGNORE = BIOME_MODIFIER_SERIALIZERS.register("add_features_ignore", () ->
			RecordCodecBuilder.create(builder -> builder.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddFeaturesIgnoreBiomeModifier::biomes),
					Biome.LIST_CODEC.fieldOf("ignored_biomes").forGetter(AddFeaturesIgnoreBiomeModifier::ignoredBiomes),
					PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesIgnoreBiomeModifier::features),
					Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesIgnoreBiomeModifier::step)
			).apply(builder, AddFeaturesIgnoreBiomeModifier::new))
	);

	public record InvertedRemoveSpawnsBiomeModifier(HolderSet<Biome> biomes, HolderSet<EntityType<?>> entityTypes) implements BiomeModifier {
		@Override
		public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
			if (phase == Phase.REMOVE && !this.biomes.contains(biome)) {
				MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
				for (MobCategory category : MobCategory.values()) {
					List<SpawnerData> spawns = spawnBuilder.getSpawner(category);
					spawns.removeIf(spawnerData -> this.entityTypes.contains(ForgeRegistries.ENTITY_TYPES.getHolder(spawnerData.type).get()));
				}
			}
		}

		@Override
		public Codec<? extends BiomeModifier> codec() {
			return REMOVE_SPAWNS_INVERTED.get();
		}
	}

	public record InvertedRemoveSpawnsIgnoreBiomeModifier(HolderSet<Biome> biomes, HolderSet<Biome> ignoredBiomes, HolderSet<EntityType<?>> entityTypes) implements BiomeModifier {
		@Override
		public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
			if (phase == Phase.REMOVE && (!this.biomes.contains(biome) || this.ignoredBiomes.contains(biome))) {
				MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
				for (MobCategory category : MobCategory.values()) {
					List<SpawnerData> spawns = spawnBuilder.getSpawner(category);
					spawns.removeIf(spawnerData -> this.entityTypes.contains(ForgeRegistries.ENTITY_TYPES.getHolder(spawnerData.type).get()));
				}
			}
		}

		@Override
		public Codec<? extends BiomeModifier> codec() {
			return REMOVE_SPAWNS_INVERTED_WITH_IGNORE.get();
		}
	}

	public record AddSpawnsIgnoreBiomeModifier(HolderSet<Biome> biomes, HolderSet<Biome> ignoredBiomes, List<SpawnerData> spawners) implements BiomeModifier {

		@Override
		public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
			if (phase == Phase.ADD && this.biomes.contains(biome) && !this.ignoredBiomes.contains(biome)) {
				MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
				for (SpawnerData spawner : this.spawners) {
					EntityType<?> type = spawner.type;
					spawns.addSpawn(type.getCategory(), spawner);
				}
			}
		}

		@Override
		public Codec<? extends BiomeModifier> codec() {
			return ADD_SPAWNS_WITH_IGNORE.get();
		}
	}

	public record AddFeaturesIgnoreBiomeModifier(HolderSet<Biome> biomes, HolderSet<Biome> ignoredBiomes, HolderSet<PlacedFeature> features, Decoration step) implements BiomeModifier {
		@Override
		public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
			if (phase == Phase.ADD && this.biomes.contains(biome) && !this.ignoredBiomes.contains(biome)) {
				BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
				this.features.forEach(holder -> generationSettings.addFeature(this.step, holder));
			}
		}

		@Override
		public Codec<? extends BiomeModifier> codec() {
			return ADD_FEATURES_WITH_IGNORE.get();
		}
	}
}