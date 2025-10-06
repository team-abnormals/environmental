package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures.EnvironmentalConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class EnvironmentalTreeGrowers {
	public static final TreeGrower PINE = new TreeGrower("environmental:pine", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.PINE), Optional.empty());
	public static final TreeGrower TALL_PINE = new TreeGrower("environmental:tall_pine", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.TALL_PINE), Optional.empty());

	public static final TreeGrower WILLOW = new TreeGrower("environmental:willow", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.WILLOW), Optional.empty());
	public static final TreeGrower WEEPING_WILLOW = new TreeGrower("environmental:weeping_willow", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.WEEPING_WILLOW), Optional.empty());


	public static final TreeGrower PLUM = new TreeGrower("environmental:plum", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.PLUM), Optional.of(EnvironmentalConfiguredFeatures.PLUM_BEES_005));
	public static final TreeGrower CHEERFUL_PLUM = new TreeGrower("environmental:cheerful_plum", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.CHEERFUL_PLUM), Optional.of(EnvironmentalConfiguredFeatures.CHEERFUL_PLUM_BEES_005));
	public static final TreeGrower MOODY_PLUM = new TreeGrower("environmental:moody_plum", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.MOODY_PLUM), Optional.of(EnvironmentalConfiguredFeatures.MOODY_PLUM_BEES_005));

	public static final TreeGrower PURPLE_WISTERIA = new TreeGrower("environmental:purple_wisteria", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.PURPLE_WISTERIA), Optional.of(EnvironmentalConfiguredFeatures.PURPLE_WISTERIA_BEES_005));
	public static final TreeGrower WHITE_WISTERIA = new TreeGrower("environmental:white_wisteria", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.WHITE_WISTERIA), Optional.of(EnvironmentalConfiguredFeatures.WHITE_WISTERIA_BEES_005));
	public static final TreeGrower PINK_WISTERIA = new TreeGrower("environmental:pink_wisteria", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.PINK_WISTERIA), Optional.of(EnvironmentalConfiguredFeatures.PINK_WISTERIA_BEES_005));
	public static final TreeGrower BLUE_WISTERIA = new TreeGrower("environmental:blue_wisteria", Optional.empty(), Optional.of(EnvironmentalConfiguredFeatures.BLUE_WISTERIA), Optional.of(EnvironmentalConfiguredFeatures.BLUE_WISTERIA_BEES_005));
}