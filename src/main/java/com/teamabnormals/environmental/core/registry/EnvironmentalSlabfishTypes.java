package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishImpossibleCondition;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class EnvironmentalSlabfishTypes {

    public static final ResourceKey<Registry<SlabfishType>> SLABFISH_TYPES_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(Environmental.MOD_ID, "slabfish_type"));

    /**
     * Internal use only!
     * Use {@link EnvironmentalSlabfishTypes#SLABFISH_TYPES} instead.
     */
    public static final DeferredRegister<SlabfishType> BUILTIN_SLABFISH_TYPES = DeferredRegister.create(SLABFISH_TYPES_REGISTRY, Environmental.MOD_ID);
    public static final Supplier<IForgeRegistry<SlabfishType>> SLABFISH_TYPES = BUILTIN_SLABFISH_TYPES.makeRegistry(SlabfishType.class, () -> new RegistryBuilder<SlabfishType>().setDefaultKey(new ResourceLocation(Environmental.MOD_ID, "swamp")).disableSaving().dataPackRegistry(SlabfishType.CODEC, SlabfishType.NETWORK_CODEC));

    public static final RegistryObject<SlabfishType> SWAMP = BUILTIN_SLABFISH_TYPES.register("swamp", () -> SlabfishType.builder().setWeight(-1).build());
    public static final RegistryObject<SlabfishType> GHOST = BUILTIN_SLABFISH_TYPES.register("ghost", () -> SlabfishType.builder().addCondition(new SlabfishImpossibleCondition()).build());

    public static Registry<SlabfishType> registryAccess() {
        if (EffectiveSide.get().isServer()) {
            return ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(SLABFISH_TYPES_REGISTRY);
        } else {
            Level level = Minecraft.getInstance().level;
            if (level != null) return level.registryAccess().registryOrThrow(SLABFISH_TYPES_REGISTRY);
        }
        return BuiltinRegistries.ACCESS.registryOrThrow(SLABFISH_TYPES_REGISTRY);
    }
}
