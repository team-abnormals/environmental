package com.teamabnormals.environmental.common.slabfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;
import java.util.function.Predicate;

public record SlabfishBackpack(Component description, ResourceLocation texture, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) implements Predicate<ItemStack> {
	public static final Codec<SlabfishBackpack> DIRECT_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ComponentSerialization.CODEC.fieldOf("description").forGetter(entry -> entry.description),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SlabfishBackpack::new);
	});

	public static final StreamCodec<RegistryFriendlyByteBuf, SlabfishBackpack> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ComponentSerialization.STREAM_CODEC, SlabfishBackpack::description,
			ResourceLocation.STREAM_CODEC, SlabfishBackpack::texture,
			ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(Registries.ITEM)), SlabfishBackpack::item,
			ByteBufCodecs.optional(ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM))), SlabfishBackpack::tagKey,
			SlabfishBackpack::new
	);
	public static final Codec<Holder<SlabfishBackpack>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.SLABFISH_BACKPACK, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SlabfishBackpack>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.SLABFISH_BACKPACK, DIRECT_STREAM_CODEC);

	public static SlabfishBackpack create(Component displayName, ResourceLocation texture, ItemLike item) {
		return new SlabfishBackpack(displayName, texture, Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem())), java.util.Optional.empty());
	}

	public static SlabfishBackpack create(Component displayName, ResourceLocation texture, TagKey<Item> tag) {
		return new SlabfishBackpack(displayName, texture, Optional.empty(), Optional.of(tag));
	}

	@Override
	public boolean test(ItemStack stack) {
		return (this.item.isPresent() && Ingredient.of(this.item.get().value()).test(stack)) || (this.tagKey.isPresent() && Ingredient.of(this.tagKey.get()).test(stack));
	}
}
