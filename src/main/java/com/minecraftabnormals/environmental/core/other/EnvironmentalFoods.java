package com.minecraftabnormals.environmental.core.other;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class EnvironmentalFoods {

	public static final Food CHERRIES = new Food.Builder().nutrition(1).saturationMod(0.2F).fast().build();
	public static final Food CHERRY_PIE = new Food.Builder().nutrition(6).saturationMod(0.3F).build();
	public static final Food APPLE_PIE = new Food.Builder().nutrition(10).saturationMod(0.2F).build();

	public static final Food VENISON = (new Food.Builder()).nutrition(2).saturationMod(0.3F).meat().build();
	public static final Food COOKED_VENISON = (new Food.Builder()).nutrition(6).saturationMod(0.8F).meat().build();

	public static final Food DUCK = (new Food.Builder()).nutrition(4).saturationMod(0.1F).effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build();
	public static final Food COOKED_DUCK = (new Food.Builder()).nutrition(8).saturationMod(0.3F).meat().build();

	public static final Food TRUFFLE = new Food.Builder().nutrition(4).saturationMod(0.1F).alwaysEat().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 2400), 1.0F).build();
	public static final Food TRUFFLE_MASH = new Food.Builder().nutrition(20).saturationMod(0.5F).alwaysEat().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 6000), 1.0F).build();
	public static final Food TRUFFLE_PIE = new Food.Builder().nutrition(15).saturationMod(0.6F).alwaysEat().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 3600), 1.0F).build();

	public static final Food KOI = new Food.Builder().nutrition(1).saturationMod(0.1F).build();
}
