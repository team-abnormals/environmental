package com.teamabnormals.environmental.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalConfig {

	public static class Common {
		public final BooleanValue deerFlowerReproducing;

		public final BooleanValue blockOnlyNaturalSpawns;
		public final IntValue koiHorizontalSerenityRange;
		public final IntValue koiVerticalSerenityRange;
		public final BooleanValue serenityEffect;

		public final BooleanValue largerPigLitters;
		public final IntValue minimumAdditionalPiglets;
		public final IntValue maximumAdditionalPiglets;
		
		public final BooleanValue largerHoglinLitters;
		public final IntValue minimumAdditionalHoglets;
		public final IntValue maximumAdditionalHoglets;

		public final BooleanValue muddyPigs;
		public final BooleanValue naturalMuddyPigs;
		public final DoubleValue muddyPigDecorationChance;
		public final BooleanValue decoratableMuddyPigs;
		public final BooleanValue muddyPigsDryOverTime;
		public final BooleanValue muddyPigsOnlyDryInTheNether;

		public final BooleanValue pigsHuntTruffles;

		public final BooleanValue cactusBobble;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("mobs");
			builder.push("deer");
			deerFlowerReproducing = builder.comment("If Deer can reproduce and spread flowers by feeding them an Apple followed by a flower").define("Deer flower reproducing", true);
			builder.pop();
			builder.push("koi");
			blockOnlyNaturalSpawns = builder.comment("Make Koi only block natural spawns").define("Block only natural spawns", true);
			koiHorizontalSerenityRange = builder.comment("Horizontal radius of Serenity effect in blocks").defineInRange("Horizontal serenity range (radius)", 32, 0, Integer.MAX_VALUE);
			koiVerticalSerenityRange = builder.comment("Vertical radius of Serenity effect in blocks").defineInRange("Vertical serenity range (radius)", 8, 0, Integer.MAX_VALUE);
			serenityEffect = builder.comment("If Koi exude Serenity as a potion effect").define("Serenity potion effect", true);
			builder.pop();
			builder.push("pig");
			pigsHuntTruffles = builder.comment("If Pigs hunt for Truffles when given a Golden Carrot").define("Pigs hunt Truffles", true);
			largerPigLitters = builder.comment("If Pigs should spawn more Piglets upon breeding").define("Larger Pig litters", true);
			builder.push("larger_litters");
			minimumAdditionalPiglets = builder.comment("The minimum amount of piglets that can spawn in addition to the original child").defineInRange("Minimum additional piglets", 1, 0, Integer.MAX_VALUE);
			maximumAdditionalPiglets = builder.comment("The maximum amount of piglets that can spawn in addition to the original child").defineInRange("Maximum additional piglets", 3, 0, Integer.MAX_VALUE);
			builder.pop();
			muddyPigs = builder.comment("If Pigs can be made muddy by walking in Mud or being hit with Mud Balls").define("Muddy Pigs", true);
			builder.push("muddy_pigs");
			naturalMuddyPigs = builder.comment("If Pigs naturally spawn muddy in swampy biomes").define("Natural Muddy Pigs", true);
			muddyPigDecorationChance = builder.comment("The chance that a Muddy Pig has to spawn with a decoration, such as a flower").defineInRange("Muddy Pig decoration chance", 0.2D, 0.0D, 1.0D);
			decoratableMuddyPigs = builder.comment("If Muddy Pigs can be decorated with various flowers, saplings, and plants").define("Decoratable Muddy Pigs", true);
			muddyPigsDryOverTime = builder.comment("If Muddy Pigs become dry naturally over time").define("Muddy Pigs dry over time", true);
			muddyPigsOnlyDryInTheNether = builder.comment("If Muddy Pigs only naturally dry when in the Nether").define("Muddy Pigs only dry in the Nether", true);
			builder.pop();
			builder.pop();
			builder.push("hoglin");
			largerHoglinLitters = builder.comment("If Hoglins should spawn more babies upon breeding").define("Larger Hoglin litters", true);
			builder.push("larger_litters");
			minimumAdditionalHoglets = builder.comment("The minimum amount of baby hoglins that can spawn in addition to the original child").defineInRange("Minimum additional babies", 1, 0, Integer.MAX_VALUE);
			maximumAdditionalHoglets = builder.comment("The maximum amount of baby hoglins that can spawn in addition to the original child").defineInRange("Maximum additional babies", 2, 0, Integer.MAX_VALUE);
			builder.pop();
			builder.pop();
			builder.pop();
			builder.push("blocks");
			cactusBobble = builder.comment("Cacti generate with a Cactus Bobble on top to prevent natural growth").define("Cactus bobble", true);
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