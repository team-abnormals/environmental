package com.teamabnormals.environmental.common.entity.animal.koi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.List;

public record KoiVariant(ResourceLocation texture, Component description) {
	public static final Codec<KoiVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("texture").forGetter(KoiVariant::texture),
					ComponentSerialization.CODEC.fieldOf("description").forGetter(KoiVariant::description))
			.apply(instance, KoiVariant::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, KoiVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, KoiVariant::texture,
			ComponentSerialization.STREAM_CODEC, KoiVariant::description,
			KoiVariant::new
	);

	public static final Codec<Holder<KoiVariant>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.KOI_VARIANT, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<KoiVariant>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.KOI_VARIANT, DIRECT_STREAM_CODEC);

	public static Holder<KoiVariant> getRandomVariant(RegistryAccess registryAccess, RandomSource random) {
		Registry<KoiVariant> registry = registryAccess.registryOrThrow(EnvironmentalRegistries.KOI_VARIANT);
		List<Reference<KoiVariant>> variants = registry.holders().toList();
		return variants.get(random.nextInt(variants.size()));
	}

	public static Holder<KoiVariant> getNoiseVariant(RegistryAccess registryAccess, double noise) {
		Registry<KoiVariant> registry = registryAccess.registryOrThrow(EnvironmentalRegistries.KOI_VARIANT);
		List<Reference<KoiVariant>> variants = registry.holders().toList();
		return variants.get((int) (noise * variants.size()));
	}
}