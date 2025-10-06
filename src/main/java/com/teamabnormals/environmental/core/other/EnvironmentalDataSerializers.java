package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class EnvironmentalDataSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Environmental.MOD_ID);

	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<ResourceLocation>> RESOURCE_LOCATION = DATA_SERIALIZERS.register("resource_location", () -> EntityDataSerializer.forValueType(ResourceLocation.STREAM_CODEC));
}
