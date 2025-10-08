package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.common.entity.animal.deer.DeerVariant;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiVariant;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.common.slabfish.SlabfishBackpack;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.common.slabfish.SlabfishSweater;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class EnvironmentalDataSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Environmental.MOD_ID);

	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<SlabfishVariant>>> SLABFISH_VARIANT = DATA_SERIALIZERS.register("slabfish_variant", () -> EntityDataSerializer.forValueType(SlabfishVariant.STREAM_CODEC));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Holder<SlabfishBackpack>>>> SLABFISH_BACKPACK_VARIANT = DATA_SERIALIZERS.register("slabfish_backpack_variant", () -> EntityDataSerializer.forValueType(SlabfishBackpack.STREAM_CODEC.apply(ByteBufCodecs::optional)));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Holder<SlabfishSweater>>>> SLABFISH_SWEATER_VARIANT = DATA_SERIALIZERS.register("slabfish_sweater_variant", () -> EntityDataSerializer.forValueType(SlabfishSweater.STREAM_CODEC.apply(ByteBufCodecs::optional)));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Holder<SlabfishOverlay>>>> SLABFISH_OVERLAY = DATA_SERIALIZERS.register("slabfish_overlay", () -> EntityDataSerializer.forValueType(SlabfishOverlay.STREAM_CODEC.apply(ByteBufCodecs::optional)));

	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<KoiVariant>>> KOI_VARIANT = DATA_SERIALIZERS.register("koi_variant", () -> EntityDataSerializer.forValueType(KoiVariant.STREAM_CODEC));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<DeerVariant>>> DEER_VARIANT = DATA_SERIALIZERS.register("deer_variant", () -> EntityDataSerializer.forValueType(DeerVariant.STREAM_CODEC));
}
