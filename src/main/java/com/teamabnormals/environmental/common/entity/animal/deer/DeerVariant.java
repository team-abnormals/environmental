package com.teamabnormals.environmental.common.entity.animal.deer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalDeerVariants;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Comparator;

public record DeerVariant(ResourceLocation assetId, HolderSet<Biome> biomes, int priority) {
	public static final Codec<DeerVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("asset_id").forGetter(DeerVariant::assetId),
					RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(DeerVariant::biomes),
					Codec.INT.optionalFieldOf("priority", 0).forGetter(DeerVariant::priority))
			.apply(instance, DeerVariant::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, DeerVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, DeerVariant::assetId,
			ByteBufCodecs.holderSet(Registries.BIOME), DeerVariant::biomes,
			ByteBufCodecs.INT, DeerVariant::priority,
			DeerVariant::new
	);

	public static final Codec<Holder<DeerVariant>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.DEER_VARIANT, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DeerVariant>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.DEER_VARIANT, DIRECT_STREAM_CODEC);

	public static Holder<DeerVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> biome) {
		Registry<DeerVariant> registry = registryAccess.registryOrThrow(EnvironmentalRegistries.DEER_VARIANT);
		return registry.holders()
				.filter(holder -> holder.value().biomes().contains(biome))
				.max(Comparator.comparingInt(holder -> holder.value().priority()))
				.or(() -> registry.getHolder(EnvironmentalDeerVariants.DEFAULT))
				.or(registry::getAny)
				.orElseThrow();
	}
}