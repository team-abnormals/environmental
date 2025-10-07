package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.common.entity.animal.deer.DeerVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalDeerVariants {
	public static final ResourceKey<DeerVariant> CREAMY = create("creamy");
	public static final ResourceKey<DeerVariant> CHESTNUT = create("chestnut");
	public static final ResourceKey<DeerVariant> GRAY = create("gray");
	public static final ResourceKey<DeerVariant> DEFAULT = CREAMY;

	public static void bootstrap(BootstrapContext<DeerVariant> context) {
		register(context, CREAMY, BiomeTags.IS_FOREST, 0);
		register(context, CHESTNUT, EnvironmentalBiomeTags.SPAWNS_CHESTNUT_DEER, 1);
		register(context, GRAY, EnvironmentalBiomeTags.SPAWNS_GRAY_DEER, 1);
	}

	public static void register(BootstrapContext<DeerVariant> context, ResourceKey<DeerVariant> key, ResourceKey<Biome> spawnBiome, int priority) {
		register(context, key, HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(spawnBiome)), priority);
	}

	public static void register(BootstrapContext<DeerVariant> context, ResourceKey<DeerVariant> key, TagKey<Biome> spawnBiomes, int priority) {
		register(context, key, context.lookup(Registries.BIOME).getOrThrow(spawnBiomes), priority);
	}

	public static void register(BootstrapContext<DeerVariant> context, ResourceKey<DeerVariant> key, HolderSet<Biome> spawnBiomes, int priority) {
		context.register(key, new DeerVariant(key.location().withPrefix("entity/deer/deer_"), spawnBiomes, priority));
	}

	public static ResourceKey<DeerVariant> create(String name) {
		return ResourceKey.create(EnvironmentalRegistries.DEER_VARIANT, Environmental.location(name));
	}
}
