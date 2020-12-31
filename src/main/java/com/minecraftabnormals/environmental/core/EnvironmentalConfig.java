package com.minecraftabnormals.environmental.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalConfig {

	public static class Common {
		public final ConfigValue<Boolean> generateGiantMushroomsInSwamps;
		public final ConfigValue<Boolean> generateGiantTallGrass;

		public final ConfigValue<Integer> marshWeight;
		public final ConfigValue<Integer> mushroomMarshWeight;

		public final ConfigValue<Integer> blossomWoodsWeight;
		public final ConfigValue<Integer> blossomHillsWeight;
		public final ConfigValue<Integer> blossomHighlandsWeight;
		public final ConfigValue<Integer> blossomValleysWeight;

		public final ConfigValue<Boolean> limitFarmAnimalSpawns;
		public final ConfigValue<Boolean> biomeVariantsAlwaysSpawn;

		public final ConfigValue<Boolean> blockOnlyNaturalSpawns;
		public final ConfigValue<Integer> koiHorizontalSerenityRange;
		public final ConfigValue<Integer> koiVerticalSerenityRange;
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
			generateGiantMushroomsInSwamps = builder.define("Giant Mushroom generation in Swamps", true);
			generateGiantTallGrass = builder.define("Giant Tall Grass generation", true);
			builder.pop();
			builder.pop();

			builder.push("entities");
			limitFarmAnimalSpawns = builder.comment("Make farm animals spawn in less biomes to allow new mobs to take their place and diversify biome spawns").define("Limit farm animal spawns", true);
			biomeVariantsAlwaysSpawn = builder.comment("Make biome variants of mobs like Husk always spawn in place of their original in their biomes").define("Biome variants always spawn", true);
			builder.push("koi");
			blockOnlyNaturalSpawns = builder.comment("Make Koi only block natural spawns").define("Block only natural spawns", true);
			koiHorizontalSerenityRange = builder.comment("Horizontal radius of Serenity effect in blocks").define("Horizontal serenity range (radius)", 16);
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