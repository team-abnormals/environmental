package com.teamabnormals.environmental.core.registry;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public class EnvironmentalMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, Environmental.MOD_ID);

    public static final RegistryObject<MemoryModuleType<Integer>> GRAZING_TICKS = MEMORY_MODULE_TYPES.register("grazing_ticks", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Integer>> SINCE_LAST_HERD = MEMORY_MODULE_TYPES.register("since_last_herd", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<BlockPos>> HERDING_POSITION = MEMORY_MODULE_TYPES.register("herding_position", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));
    public static final RegistryObject<MemoryModuleType<List<Yak>>> NEAREST_VISIBLE_YAKS = MEMORY_MODULE_TYPES.register("nearest_visible_yaks", () -> new MemoryModuleType<>(Optional.empty()));
}
