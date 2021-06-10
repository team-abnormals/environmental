package com.minecraftabnormals.environmental.core.other;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class EnvironmentalFoods {

	public static final Food CHERRIES = new Food.Builder().hunger(1).saturation(0.2F).fastToEat().build();
	public static final Food CHERRY_PIE = new Food.Builder().hunger(6).saturation(0.3F).build();
	public static final Food APPLE_PIE = new Food.Builder().hunger(10).saturation(0.2F).build();

	public static final Food VENISON = (new Food.Builder()).hunger(2).saturation(0.3F).meat().build();
	public static final Food COOKED_VENISON = (new Food.Builder()).hunger(6).saturation(0.8F).meat().build();

	public static final Food DUCK = (new Food.Builder()).hunger(4).saturation(0.1F).effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build();
	public static final Food COOKED_DUCK = (new Food.Builder()).hunger(8).saturation(0.3F).meat().build();

	public static final Food FRIED_EGG = (new Food.Builder()).hunger(3).saturation(0.6F).build();
	public static final Food SCRAMBLED_EGGS = (new Food.Builder()).hunger(6).saturation(0.6F).build();

	public static final Food TRUFFLE = new Food.Builder().hunger(4).saturation(0.1F).setAlwaysEdible().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 2400), 1.0F).build();
	public static final Food TRUFFLE_MASH = new Food.Builder().hunger(20).saturation(0.5F).setAlwaysEdible().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 6000), 1.0F).effect(() -> new EffectInstance(Effects.RESISTANCE, 1200), 1.0F).effect(() -> new EffectInstance(Effects.SPEED, 1200), 1.0F).build();
	public static final Food TRUFFLE_PIE = new Food.Builder().hunger(15).saturation(0.6F).setAlwaysEdible().effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 3600), 1.0F).effect(() -> new EffectInstance(Effects.HASTE, 1200), 1.0F).effect(() -> new EffectInstance(Effects.STRENGTH, 1200), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 1200), 1.0F).build();

	public static final Food KOI = new Food.Builder().hunger(1).saturation(0.1F).build();
}
