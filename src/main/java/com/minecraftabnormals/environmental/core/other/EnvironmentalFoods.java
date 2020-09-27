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
	
    public static final Food RICE_BALL = new Food.Builder().hunger(4).saturation(0.5F).build();

    public static final Food COD_KELP_ROLL = new Food.Builder().hunger(3).saturation(0.7F).fastToEat().build();
    public static final Food TROPICAL_FISH_KELP_ROLL = new Food.Builder().hunger(4).saturation(1.3F).fastToEat().build();
    public static final Food CRAB_KELP_ROLL = new Food.Builder().hunger(6).saturation(0.7F).fastToEat().build();
    public static final Food PIKE_KELP_ROLL = new Food.Builder().hunger(5).saturation(0.9F).fastToEat().build();

    public static final Food SALMON_RICE_CAKE = new Food.Builder().hunger(4).saturation(0.7F).fastToEat().build();
    public static final Food PUFFERFISH_RICE_CAKE = new Food.Builder().hunger(4).saturation(0.3F).fastToEat().effect(() -> new EffectInstance(Effects.WEAKNESS, 600), 1.0F).build();
    public static final Food LIONFISH_RICE_CAKE = new Food.Builder().hunger(3).saturation(0.9F).fastToEat().effect(() -> new EffectInstance(Effects.SLOWNESS, 600), 1.0F).build();

    public static final Food SQUID_INK_RISOTTO = new Food.Builder().hunger(8).saturation(0.9F).build();
}
