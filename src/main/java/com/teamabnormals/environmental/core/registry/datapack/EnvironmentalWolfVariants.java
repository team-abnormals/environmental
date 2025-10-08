package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalWolfVariants {
	public static final ResourceKey<WolfVariant> OMBRE = create("ombre");
	public static final ResourceKey<WolfVariant> TAWNY = create("tawny");
	public static final ResourceKey<WolfVariant> HORIZON = create("horizon");
	public static final ResourceKey<WolfVariant> GRAY = create("gray");
	public static final ResourceKey<WolfVariant> TIMBER = create("timber");

	public static void bootstrap(BootstrapContext<WolfVariant> context) {
		register(context, OMBRE, "wolf_ombre", EnvironmentalBiomes.PINE_SLOPES);
		register(context, TAWNY, "wolf_tawny", EnvironmentalBiomes.PINE_BARRENS);
		register(context, HORIZON, "wolf_horizon", EnvironmentalBiomes.SNOWY_PINE_BARRENS);
		register(context, GRAY, "wolf_gray", EnvironmentalBiomes.OLD_GROWTH_PINE_BARRENS);
		register(context, TIMBER, "wolf_timber", EnvironmentalBiomes.SNOWY_OLD_GROWTH_PINE_BARRENS);
	}

	private static ResourceKey<WolfVariant> create(String name) {
		return ResourceKey.create(Registries.WOLF_VARIANT, Environmental.location(name));
	}

	static void register(BootstrapContext<WolfVariant> context, ResourceKey<WolfVariant> key, String name, ResourceKey<Biome> spawnBiome) {
		register(context, key, name, HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(spawnBiome)));
	}

	private static void register(BootstrapContext<WolfVariant> context, ResourceKey<WolfVariant> key, String name, TagKey<Biome> spawnBiomes) {
		register(context, key, name, context.lookup(Registries.BIOME).getOrThrow(spawnBiomes));
	}

	private static void register(BootstrapContext<WolfVariant> context, ResourceKey<WolfVariant> key, String name, HolderSet<Biome> spawnBiomes) {
		ResourceLocation texture = Environmental.location("entity/wolf/" + name);
		ResourceLocation tame = Environmental.location("entity/wolf/" + name + "_tame");
		ResourceLocation angry = Environmental.location("entity/wolf/" + name + "_angry");
		context.register(key, new WolfVariant(texture, tame, angry, spawnBiomes));
	}
}