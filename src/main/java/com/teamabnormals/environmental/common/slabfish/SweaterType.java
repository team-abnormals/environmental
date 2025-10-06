package com.teamabnormals.environmental.common.slabfish;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;
import java.util.function.Predicate;

public record SweaterType(Optional<Component> description, Optional<ResourceLocation> texture, Optional<Holder<Item>> item, Optional<TagKey<Item>> tagKey) implements Predicate<ItemStack> {
	public static final Codec<SweaterType> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ComponentSerialization.CODEC.optionalFieldOf("description").forGetter(entry -> entry.description),
				ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(entry -> entry.texture),
				RegistryFixedCodec.create(Registries.ITEM).optionalFieldOf("item").forGetter(entry -> entry.item),
				TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(entry -> entry.tagKey)
		).apply(instance, SweaterType::new);
	});

	public static final Codec<SweaterType> NETWORK_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ComponentSerialization.CODEC.optionalFieldOf("description").forGetter(entry -> entry.description),
				ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(entry -> entry.texture)
		).apply(instance, (component, texture) -> new SweaterType(component, texture, Optional.empty(), Optional.empty()));
	});

	public static SweaterType create(Component displayName, ResourceLocation texture, ItemLike item) {
		return new SweaterType(Optional.of(displayName), Optional.of(texture), Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem())), java.util.Optional.empty());
	}

	public static SweaterType create(Component displayName, ResourceLocation texture, TagKey<Item> tag) {
		return new SweaterType(Optional.of(displayName), Optional.of(texture), Optional.empty(), Optional.of(tag));
	}

	public boolean isEmpty() {
		return this.texture.isEmpty();
	}

	@Override
	public boolean test(ItemStack stack) {
		return !this.isEmpty() && ((this.item.isPresent() && Ingredient.of(this.item.get().value()).test(stack)) || (this.tagKey.isPresent() && Ingredient.of(this.tagKey.get()).test(stack)));
	}
}
