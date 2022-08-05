package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * <p>Registers all inbuilt slabfish condition types.</p>
 *
 * @author Ocelot
 */
public class EnvironmentalSlabfishConditions {
    public static final DeferredRegister<SlabfishConditionType> BUILTIN_SLABFISH_CONDITIONS = DeferredRegister.create(new ResourceLocation(Environmental.MOD_ID, "slabfish_condition"), Environmental.MOD_ID);
    public static final Supplier<IForgeRegistry<SlabfishConditionType>> SLABFISH_CONDITIONS = BUILTIN_SLABFISH_CONDITIONS.makeRegistry(SlabfishConditionType.class, () -> new RegistryBuilder<SlabfishConditionType>().setDefaultKey(new ResourceLocation(Environmental.MOD_ID, "impossible")).disableSaving());

    public static final RegistryObject<SlabfishConditionType> IMPOSSIBLE = BUILTIN_SLABFISH_CONDITIONS.register("impossible", () -> new SlabfishConditionType(SlabfishImpossibleCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> AND = BUILTIN_SLABFISH_CONDITIONS.register("and", () -> new SlabfishConditionType(SlabfishAndCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> OR = BUILTIN_SLABFISH_CONDITIONS.register("or", () -> new SlabfishConditionType(SlabfishOrCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> RENAME = BUILTIN_SLABFISH_CONDITIONS.register("rename", () -> new SlabfishConditionType(SlabfishRenameCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> HEIGHT = BUILTIN_SLABFISH_CONDITIONS.register("height", () -> new SlabfishConditionType(SlabfishHeightCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> LIGHT_LEVEL = BUILTIN_SLABFISH_CONDITIONS.register("light_level", () -> new SlabfishConditionType(SlabfishLightCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> SLABFISH_TYPE = BUILTIN_SLABFISH_CONDITIONS.register("slabfish_type", () -> new SlabfishConditionType(SlabfishTypeCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> DIMENSION = BUILTIN_SLABFISH_CONDITIONS.register("dimension", () -> new SlabfishConditionType(SlabfishDimensionCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> BREED = BUILTIN_SLABFISH_CONDITIONS.register("breed", () -> new SlabfishConditionType(SlabfishBreedCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> RAID = BUILTIN_SLABFISH_CONDITIONS.register("raid", () -> new SlabfishConditionType(SlabfishRaidCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> INSOMNIA = BUILTIN_SLABFISH_CONDITIONS.register("insomnia", () -> new SlabfishConditionType(SlabfishInsomniaCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> RANDOM = BUILTIN_SLABFISH_CONDITIONS.register("random", () -> new SlabfishConditionType(SlabfishRandomCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> TIME = BUILTIN_SLABFISH_CONDITIONS.register("time", () -> new SlabfishConditionType(SlabfishTimeCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> IN_BIOME = BUILTIN_SLABFISH_CONDITIONS.register("in_biome", () -> new SlabfishConditionType(SlabfishInBiomeCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> IN_BLOCK = BUILTIN_SLABFISH_CONDITIONS.register("in_block", () -> new SlabfishConditionType(SlabfishInBlockCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> IN_FLUID = BUILTIN_SLABFISH_CONDITIONS.register("in_fluid", () -> new SlabfishConditionType(SlabfishInFluidCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> EVENT = BUILTIN_SLABFISH_CONDITIONS.register("event", () -> new SlabfishConditionType(SlabfishEventCondition.CODEC));
    public static final RegistryObject<SlabfishConditionType> MOD_LOADED = BUILTIN_SLABFISH_CONDITIONS.register("mod_loaded", () -> new SlabfishConditionType(SlabfishModLoadedCondition.CODEC));
}
