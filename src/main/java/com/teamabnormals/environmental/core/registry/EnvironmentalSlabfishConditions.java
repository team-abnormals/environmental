package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EnvironmentalSlabfishConditions {
	public static final DeferredRegister<SlabfishConditionType> SLABFISH_CONDITIONS = DeferredRegister.create(Environmental.location("slabfish_condition"), Environmental.MOD_ID);
	public static final Supplier<IForgeRegistry<SlabfishConditionType>> SLABFISH_CONDITIONS_REGISTRY = SLABFISH_CONDITIONS.makeRegistry(() -> new RegistryBuilder<SlabfishConditionType>().setDefaultKey(Environmental.location("impossible")));

	public static final RegistryObject<SlabfishConditionType> IMPOSSIBLE = SLABFISH_CONDITIONS.register("impossible", () -> new SlabfishConditionType(SlabfishImpossibleCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> AND = SLABFISH_CONDITIONS.register("and", () -> new SlabfishConditionType(SlabfishAndCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> OR = SLABFISH_CONDITIONS.register("or", () -> new SlabfishConditionType(SlabfishOrCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> RENAME = SLABFISH_CONDITIONS.register("rename", () -> new SlabfishConditionType(SlabfishRenameCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> HEIGHT = SLABFISH_CONDITIONS.register("height", () -> new SlabfishConditionType(SlabfishHeightCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> LIGHT_LEVEL = SLABFISH_CONDITIONS.register("light_level", () -> new SlabfishConditionType(SlabfishLightCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> SLABFISH_TYPE = SLABFISH_CONDITIONS.register("slabfish_type", () -> new SlabfishConditionType(SlabfishTypeCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> DIMENSION = SLABFISH_CONDITIONS.register("dimension", () -> new SlabfishConditionType(SlabfishDimensionCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> BREED = SLABFISH_CONDITIONS.register("breed", () -> new SlabfishConditionType(SlabfishBreedCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> RAID = SLABFISH_CONDITIONS.register("raid", () -> new SlabfishConditionType(SlabfishRaidCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> INSOMNIA = SLABFISH_CONDITIONS.register("insomnia", () -> new SlabfishConditionType(SlabfishInsomniaCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> RANDOM = SLABFISH_CONDITIONS.register("random", () -> new SlabfishConditionType(SlabfishRandomCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> TIME = SLABFISH_CONDITIONS.register("time", () -> new SlabfishConditionType(SlabfishTimeCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> IN_BIOME = SLABFISH_CONDITIONS.register("in_biome", () -> new SlabfishConditionType(SlabfishInBiomeCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> IN_BIOME_KEY = SLABFISH_CONDITIONS.register("in_biome_key", () -> new SlabfishConditionType(SlabfishInBiomeKeyCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> IN_BLOCK = SLABFISH_CONDITIONS.register("in_block", () -> new SlabfishConditionType(SlabfishInBlockCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> IN_FLUID = SLABFISH_CONDITIONS.register("in_fluid", () -> new SlabfishConditionType(SlabfishInFluidCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> EVENT = SLABFISH_CONDITIONS.register("event", () -> new SlabfishConditionType(SlabfishEventCondition.CODEC));
	public static final RegistryObject<SlabfishConditionType> MOD_LOADED = SLABFISH_CONDITIONS.register("mod_loaded", () -> new SlabfishConditionType(SlabfishModLoadedCondition.CODEC));
}
