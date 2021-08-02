package com.minecraftabnormals.environmental.core;

import com.minecraftabnormals.abnormals_core.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalConfig {

	public static class Common {

		@ConfigKey("generate_giant_mushrooms")
		public final ConfigValue<Boolean> generateGiantMushrooms;

		@ConfigKey("generate_giant_tall_grass")
		public final ConfigValue<Boolean> generateGiantTallGrass;

		@ConfigKey("generate_wisteria_trees")
		public final ConfigValue<Boolean> generateWisteriaTrees;

		@ConfigKey("generate_delphiniums")
		public final ConfigValue<Boolean> generateDelphiniums;

		@ConfigKey("generate_hibiscus")
		public final ConfigValue<Boolean> generateHibiscus;


		@ConfigKey("marsh_weight")
		public final ConfigValue<Integer> marshWeight;

		@ConfigKey("mushroom_marsh_weight")
		public final ConfigValue<Integer> mushroomMarshWeight;


		@ConfigKey("blossom_woods_weight")
		public final ConfigValue<Integer> blossomWoodsWeight;

		@ConfigKey("blossom_hills_weight")
		public final ConfigValue<Integer> blossomHillsWeight;

		@ConfigKey("blossom_highlands_weight")
		public final ConfigValue<Integer> blossomHighlandsWeight;

		@ConfigKey("blossom_valleys_weight")
		public final ConfigValue<Integer> blossomValleysWeight;


		@ConfigKey("limit_farm_animal_spawns")
		public final ConfigValue<Boolean> limitFarmAnimalSpawns;

		@ConfigKey("biome_variants_always_spawn")
		public final ConfigValue<Boolean> biomeVariantsAlwaysSpawn;


		@ConfigKey("koi_only_block_natural_spawns")
		public final ConfigValue<Boolean> blockOnlyNaturalSpawns;

		@ConfigKey("koi_horizontal_serenity_range")
		public final ConfigValue<Integer> koiHorizontalSerenityRange;

		@ConfigKey("koi_vertical_serenity_range")
		public final ConfigValue<Integer> koiVerticalSerenityRange;

		@ConfigKey("koi_give_serenity")
		public final ConfigValue<Boolean> serenityEffect;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("worldgen");
			builder.push("biomes");
			builder.push("marsh");
			marshWeight = builder.define("Marsh weight", 6);
			mushroomMarshWeight = builder.define("Mushroom Marsh weight", 0);
			builder.pop();
			builder.push("blossom");
			blossomWoodsWeight = builder.define("Blossom Woods weight", 3);
			blossomHillsWeight = builder.define("Blossom Hills weight", 0);
			blossomHighlandsWeight = builder.define("Blossom Highlands weight", 1);
			blossomValleysWeight = builder.define("Blossom Valleys weight", 0);
			builder.pop();
			builder.pop();
			builder.push("features");
			generateGiantMushrooms = builder.define("Giant Mushroom generation in Swamps", true);
			generateGiantTallGrass = builder.define("Giant Tall Grass generation", true);
			generateWisteriaTrees = builder.define("Wisteria Tree generation in Flower Forests", true);
			generateDelphiniums = builder.define("Delphinium generation in Flower Forests", true);
			generateHibiscus = builder.define("Hibiscus generation in Jungles", true);
			builder.pop();
			builder.pop();

			builder.push("entities");
			limitFarmAnimalSpawns = builder.comment("Make farm animals spawn in less biomes to allow new mobs to take their place and diversify biome spawns").define("Limit farm animal spawns", true);
			biomeVariantsAlwaysSpawn = builder.comment("Make biome variants of mobs like Husks always spawn in place of their original in their biomes").define("Biome variants always spawn", true);
			builder.push("koi");
			blockOnlyNaturalSpawns = builder.comment("Make Koi only block natural spawns").define("Block only natural spawns", true);
			koiHorizontalSerenityRange = builder.comment("Horizontal radius of Serenity effect in blocks").define("Horizontal serenity range (radius)", 32);
			koiVerticalSerenityRange = builder.comment("Vertical radius of Serenity effect in blocks").define("Vertical serenity range (radius)", 8);
			serenityEffect = builder.comment("If Koi exude Serenity as a potion effect").define("Serenity potion effect", true);
			builder.pop();
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}
}