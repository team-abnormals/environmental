package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.levelgen.feature.*;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnvironmentalFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Environmental.MOD_ID);

	public static final DeferredHolder<Feature<?>, Feature<FallenLeavesConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(FallenLeavesConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> CATTAILS = FEATURES.register("cattails", () -> new CattailsFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> DENSE_CATTAILS = FEATURES.register("dense_cattails", () -> new DenseCattailsFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> HIBISCUS_BUSH = FEATURES.register("hibiscus_bush", () -> new HibiscusBushFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> TASSELFLOWER_PATCH = FEATURES.register("tasselflower_patch", () -> new TasselflowerPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> LARGE_BLUEBELL_PATCH = FEATURES.register("large_bluebell_patch", () -> new LargeBluebellPatchFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<ProbabilityFeatureConfiguration>> GRAINY_COARSE_DIRT = FEATURES.register("grainy_coarse_dirt", () -> new GrainyCoarseDirtFeature(ProbabilityFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> COARSE_DIRT_ON_STONE = FEATURES.register("coarse_dirt_on_stone", () -> new CoarseDirtOnStoneFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SMALL_COARSE_DIRT_ON_STONE = FEATURES.register("small_coarse_dirt_on_stone", () -> new SmallCoarseDirtOnStoneFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<BlockStateConfiguration>> PINE_SLOPES_BOULDER = FEATURES.register("pine_slopes_boulder", () -> new PineSlopesBoulderFeature(BlockStateConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FALLEN_PINE_TREE = FEATURES.register("fallen_pine_tree", () -> new FallenPineTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FALLEN_TALL_PINE_TREE = FEATURES.register("fallen_tall_pine_tree", () -> new FallenTallPineTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<SimpleBlockConfiguration>> CARTWHEEL = FEATURES.register("cartwheel", () -> new CartwheelFeature(SimpleBlockConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<ProbabilityFeatureConfiguration>> SHORT_BAMBOO = FEATURES.register("short_bamboo", () -> new ShortBambooFeature(ProbabilityFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<DwarfSpruceConfiguration>> DWARF_SPRUCE = FEATURES.register("dwarf_spruce", () -> new DwarfSpruceFeature(DwarfSpruceConfiguration.CODEC));

	public static final DeferredHolder<Feature<?>, Feature<CupLichenPatchConfiguration>> CUP_LICHEN_PATCH = FEATURES.register("cup_lichen_patch", () -> new CupLichenPatchFeature(CupLichenPatchConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MUDDY_SAND = FEATURES.register("muddy_sand", () -> new MuddySandFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<ShrubPatchConfiguration>> SHRUB_PATCH = FEATURES.register("shrub_patch", () -> new ShrubPatchFeature(ShrubPatchConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<ProbabilityFeatureConfiguration>> TREE_LICHEN = FEATURES.register("tree_lichen", () -> new TreeLichenFeature(ProbabilityFeatureConfiguration.CODEC));

	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> WEEPING_WILLOW_TREE = FEATURES.register("weeping_willow_tree", () -> new WeepingWillowTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> PLUM_TREE = FEATURES.register("plum_tree", () -> new PlumTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> WISTERIA_TREE = FEATURES.register("wisteria_tree", () -> new WisteriaTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> PINE_TREE = FEATURES.register("pine_tree", () -> new PineTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> PINE_TREE_ON_STONE = FEATURES.register("pine_tree_on_stone", () -> new PineTreeOnStoneFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> CEDAR_TREE = FEATURES.register("cedar_tree", () -> new CedarTreeFeature(TreeConfiguration.CODEC));

	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> WILLOW_TREE_PLACER = FEATURES.register("willow_tree_placer", () -> new WillowTreePlacerFeature(NoneFeatureConfiguration.CODEC));

	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ZEBRA_DAZZLE = FEATURES.register("zebra_dazzle", () -> new ZebraDazzleFeature(NoneFeatureConfiguration.CODEC));

	public static final DeferredHolder<Feature<?>, Feature<NoiseSelectorFeatureConfiguration>> NOISE_SELECTOR = FEATURES.register("noise_selector", () -> new NoiseSelectorFeature(NoiseSelectorFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<BestNoisesSelectorFeatureConfiguration>> MULTI_NOISE_SELECTOR = FEATURES.register("best_noises_selector", () -> new BestNoisesSelectorFeature(BestNoisesSelectorFeatureConfiguration.CODEC));
}