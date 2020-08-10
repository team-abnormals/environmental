package com.minecraftabnormals.environmental.core.other;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

@SuppressWarnings("deprecation")
public class EnvironmentalFoods {

    // saturation value = (hunger * saturation * 2)
    public static final Food CHERRIES = new Food.Builder().hunger(1).saturation(0.2F).fastToEat().build(); //0.5
    public static final Food CHERRY_PIE = new Food.Builder().hunger(6).saturation(0.3F).build(); //3.6
    public static final Food APPLE_PIE = new Food.Builder().hunger(10).saturation(0.2F).build(); //4.0
    
    public static final Food VENISON = (new Food.Builder()).hunger(2).saturation(0.3F).meat().build();
    public static final Food COOKED_VENISON = (new Food.Builder()).hunger(6).saturation(0.8F).meat().build();
	
    public static final Food RICE_BALL = new Food.Builder().hunger(4).saturation(0.5F).build(); //0.5

    public static final Food COD_KELP_ROLL = new Food.Builder().hunger(3).saturation(0.7F).fastToEat().build(); //0.70
    public static final Food TROPICAL_FISH_KELP_ROLL = new Food.Builder().hunger(4).saturation(1.3F).fastToEat().build(); //1.25
    public static final Food CRAB_KELP_ROLL = new Food.Builder().hunger(6).saturation(0.7F).fastToEat().build(); //0.69
    public static final Food PIKE_KELP_ROLL = new Food.Builder().hunger(5).saturation(0.9F).fastToEat().build(); //0.86
    public static final Food CAVEFISH_KELP_ROLL = new Food.Builder().hunger(2).saturation(0.8F).fastToEat().build(); //0.75

    public static final Food SALMON_RICE_CAKE = new Food.Builder().hunger(4).saturation(0.7F).fastToEat().build(); //0.67
    public static final Food PUFFERFISH_RICE_CAKE = new Food.Builder().hunger(4).saturation(0.3F).fastToEat().effect(new EffectInstance(Effects.WEAKNESS, 600), 1.0F).build(); //0.25
    public static final Food LIONFISH_RICE_CAKE = new Food.Builder().hunger(3).saturation(0.9F).fastToEat().effect(new EffectInstance(Effects.SLOWNESS, 600), 1.0F).build(); //0.90

    public static final Food SQUID_INK_RISOTTO = new Food.Builder().hunger(8).saturation(0.9F).build(); //0.94
}
