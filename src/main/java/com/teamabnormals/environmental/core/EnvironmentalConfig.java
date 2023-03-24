package com.teamabnormals.environmental.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalConfig {

	public static class Common {
		public final ConfigValue<Boolean> blockOnlyNaturalSpawns;
		public final ConfigValue<Integer> koiHorizontalSerenityRange;
		public final ConfigValue<Integer> koiVerticalSerenityRange;
		public final ConfigValue<Boolean> serenityEffect;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("mobs");
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