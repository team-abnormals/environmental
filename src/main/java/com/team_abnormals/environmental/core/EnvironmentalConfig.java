package com.team_abnormals.environmental.core;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;

/**
 * @author SmellyModder(Luke Tonon)
 */
public class EnvironmentalConfig {

	public static class Common {
		public final ConfigValue<Boolean> generateExternalWisterias;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Common Config")
			.push("common");
			
			generateExternalWisterias = builder
				.comment("If Wisteria Trees generate outside of the Wisteria Forest; Default: False")
				.translation(makeTranslation("generate_external_wisterias"))
				.define("generateExternalWisterias", false);
			
			builder.pop();
		}
	}
	
	private static String makeTranslation(String name) {
		return "environmental.config." + name;
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	public static class ValuesHolder {
		private static boolean generateExternalWisterias;
		
		public static void updateCommonValuesFromConfig(ModConfig config) {
			generateExternalWisterias = EnvironmentalConfig.COMMON.generateExternalWisterias.get();
		}
		
		public static boolean generateWisterias() {
			return generateExternalWisterias;
		}
	}
 
}