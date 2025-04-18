package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.entity.ai.brain.yak.YakHerdingSensor;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalSensorTypes {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(Registries.SENSOR_TYPE, Environmental.MOD_ID);

    public static final RegistryObject<SensorType<TemptingSensor>> YAK_TEMPTATIONS = SENSOR_TYPES.register("yak_temptations", () -> new SensorType<>(() -> new TemptingSensor(Ingredient.of(EnvironmentalItemTags.YAK_FOOD))));
    public static final RegistryObject<SensorType<YakHerdingSensor>> NEAREST_VISIBLE_YAKS = SENSOR_TYPES.register("nearest_visible_yaks", () -> new SensorType<>(YakHerdingSensor::new));
}
