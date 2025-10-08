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

public record SlabfishSweater(Component description, ResourceLocation texture, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) implements Predicate<ItemStack> {
	public static final Codec<SlabfishSweater> DIRECT_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ComponentSerialization.CODEC.fieldOf("description").forGetter(entry -> entry.description),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SlabfishSweater::new);
	});

	public static final StreamCodec<RegistryFriendlyByteBuf, SlabfishSweater> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ComponentSerialization.STREAM_CODEC, SlabfishSweater::description,
			ResourceLocation.STREAM_CODEC, SlabfishSweater::texture,
			ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(Registries.ITEM)), SlabfishSweater::item,
			ByteBufCodecs.optional(ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM))), SlabfishSweater::tagKey,
			SlabfishSweater::new
	);
	public static final Codec<Holder<SlabfishSweater>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.SLABFISH_SWEATER, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SlabfishSweater>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.SLABFISH_SWEATER, DIRECT_STREAM_CODEC);

	public static SlabfishSweater create(Component displayName, ResourceLocation texture, ItemLike item) {
		return new SlabfishSweater(displayName, texture, Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem())), Optional.empty());
	}

	public static SlabfishSweater create(Component displayName, ResourceLocation texture, TagKey<Item> tag) {
		return new SlabfishSweater(displayName, texture, Optional.empty(), Optional.of(tag));
	}

	@Override
	public boolean test(ItemStack stack) {
		return (this.item.isPresent() && Ingredient.of(this.item.get().value()).test(stack)) || (this.tagKey.isPresent() && Ingredient.of(this.tagKey.get()).test(stack));
	}
}
