package com.minecraftabnormals.environmental.core;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalConfig {

	public static class Common {
		public final ConfigValue<Boolean> generateGiantMushrooms;
		public final ConfigValue<Boolean> generateGiantTallGrass;
		public final ConfigValue<Boolean> generateWisteriaTrees;
		public final ConfigValue<Boolean> generateDelphiniums;
		public final ConfigValue<Boolean> generateHibiscus;

		public final ConfigValue<Integer> marshWeight;
		public final ConfigValue<Integer> mushroomMarshWeight;

		public final ConfigValue<Integer> blossomWoodsWeight;
		public final ConfigValue<Integer> blossomHillsWeight;
		public final ConfigValue<Integer> blossomHighlandsWeight;
		public final ConfigValue<Integer> blossomValleysWeight;

		public final ConfigValue<Boolean> limitFarmAnimalSpawns;
		public final ConfigValue<Boolean> biomeVariantsAlwaysSpawn;

		public final ConfigValue<List<String>> healerPouchStructures;

		public final ConfigValue<Boolean> blockOnlyNaturalSpawns;
		public final ConfigValue<Integer> koiHorizontalSerenityRange;
		public final ConfigValue<Integer> koiVerticalSerenityRange;
		public final ConfigValue<Boolean> serenityEffect;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("world");
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
			builder.push("generation");
			generateGiantMushrooms = builder.define("Giant Mushroom generation in Swamps", true);
			generateGiantTallGrass = builder.define("Giant Tall Grass generation", true);
			generateWisteriaTrees = builder.define("Wisteria Tree generation in Flower Forests", true);
			generateDelphiniums = builder.define("Delphinium generation in Flower Forests", true);
			generateHibiscus = builder.define("Hibiscus generation in Jungles", true);
			builder.pop();
			builder.pop();

			builder.push("items");
			builder.push("healers_pouch");
			healerPouchStructures = builder.comment("Structures that can spawn mobs wearing a Healer's Pouch").define("Healer's Pouch structures", Lists.newArrayList("minecraft:mineshaft", "minecraft:stronghold"));
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