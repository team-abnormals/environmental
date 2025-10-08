package com.teamabnormals.environmental.common.entity.animal.slabfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record SlabfishOverlay(ResourceLocation texture, Optional<ResourceLocation> backpackTexture, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) {
	public static final Codec<SlabfishOverlay> DIRECT_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				ResourceLocation.CODEC.optionalFieldOf("backpack_texture").forGetter(entry -> entry.backpackTexture),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("projectile_item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("projectile_item_tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SlabfishOverlay::new);
	});

	public static final StreamCodec<RegistryFriendlyByteBuf, SlabfishOverlay> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, SlabfishOverlay::texture,
			ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), SlabfishOverlay::backpackTexture,
			ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(Registries.ITEM)), SlabfishOverlay::item,
			ByteBufCodecs.optional(ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM))), SlabfishOverlay::tagKey,
			SlabfishOverlay::new
	);

	public static final Codec<Holder<SlabfishOverlay>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.SLABFISH_OVERLAY, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SlabfishOverlay>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.SLABFISH_OVERLAY, DIRECT_STREAM_CODEC);

	public static Optional<Reference<SlabfishOverlay>> getOverlayForItem(RegistryAccess access, ItemStack item) {
		Optional<Reference<SlabfishOverlay>> overlay;
		overlay = SlabfishHelper.slabfishOverlays(access).holders().filter(holder -> holder.value().item.isPresent() && item.is(holder.value().item.get())).findAny();
		if (overlay.isEmpty()) {
			overlay = SlabfishHelper.slabfishOverlays(access).holders().filter(holder -> holder.value().tagKey.isPresent() && item.is(holder.value().tagKey().get())).findAny();
		}
		return overlay;
	}

}