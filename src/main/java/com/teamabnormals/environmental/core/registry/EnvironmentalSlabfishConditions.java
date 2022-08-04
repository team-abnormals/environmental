package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition.Factory;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * <p>Registers all inbuilt slabfish condition types.</p>
 *
 * @author Ocelot
 */
public class EnvironmentalSlabfishConditions {
	//TODO: Use proper creation
	public static final DeferredRegister<Factory> SLABFISH_CONDITIONS = DeferredRegister.create(Factory.class, Environmental.MOD_ID);

	public static final RegistryObject<SimpleSlabfishConditionFactory> IMPOSSIBLE = SLABFISH_CONDITIONS.register("impossible", () -> new SimpleSlabfishConditionFactory(SlabfishImpossibleCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> AND = SLABFISH_CONDITIONS.register("and", () -> new SimpleSlabfishConditionFactory(SlabfishAndCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> OR = SLABFISH_CONDITIONS.register("or", () -> new SimpleSlabfishConditionFactory(SlabfishOrCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> RENAME = SLABFISH_CONDITIONS.register("rename", () -> new SimpleSlabfishConditionFactory(SlabfishRenameCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> BIOME = SLABFISH_CONDITIONS.register("biome", () -> new SimpleSlabfishConditionFactory(SlabfishBiomeCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> HEIGHT = SLABFISH_CONDITIONS.register("height", () -> new SimpleSlabfishConditionFactory(SlabfishHeightCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> LIGHT_LEVEL = SLABFISH_CONDITIONS.register("light_level", () -> new SimpleSlabfishConditionFactory(SlabfishLightCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> SLABFISH_TYPE = SLABFISH_CONDITIONS.register("slabfish_type", () -> new SimpleSlabfishConditionFactory(SlabfishTypeCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> DIMENSION = SLABFISH_CONDITIONS.register("dimension", () -> new SimpleSlabfishConditionFactory(SlabfishDimensionCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> PARENTS = SLABFISH_CONDITIONS.register("parents", () -> new SimpleSlabfishConditionFactory(SlabfishParentCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> RAID = SLABFISH_CONDITIONS.register("raid", () -> new SimpleSlabfishConditionFactory(SlabfishRaidCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> INSOMNIA = SLABFISH_CONDITIONS.register("insomnia", () -> new SimpleSlabfishConditionFactory(SlabfishInsomniaCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> RANDOM = SLABFISH_CONDITIONS.register("random", () -> new SimpleSlabfishConditionFactory(SlabfishRandomCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> TIME = SLABFISH_CONDITIONS.register("time", () -> new SimpleSlabfishConditionFactory(SlabfishTimeCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> IN_BLOCK = SLABFISH_CONDITIONS.register("in_block", () -> new SimpleSlabfishConditionFactory(SlabfishInBlockCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> IN_FLUID = SLABFISH_CONDITIONS.register("in_fluid", () -> new SimpleSlabfishConditionFactory(SlabfishInFluidCondition::deserialize));
	public static final RegistryObject<SimpleSlabfishConditionFactory> EVENT = SLABFISH_CONDITIONS.register("event", () -> new SimpleSlabfishConditionFactory(SlabfishEventCondition::deserialize));
}
