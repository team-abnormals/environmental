package com.teamabnormals.environmental.common.slabfish;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record SlabfishVariant(Component description, ResourceLocation texture, Optional<Holder<SlabfishBackpack>> backpackOverride, int priority, SlabfishCondition[] conditions) implements Predicate<SlabfishConditionContext> {

	public static final Map<TagKey<SlabfishVariant>, Pair<Float, ChatFormatting>> RARITIES = Util.make(new HashMap<>(), map -> {
		map.put(EnvironmentalSlabfishTypeTags.COMMON, Pair.of(1.0F, ChatFormatting.GRAY));
		map.put(EnvironmentalSlabfishTypeTags.UNCOMMON, Pair.of(0.60F, ChatFormatting.GREEN));
		map.put(EnvironmentalSlabfishTypeTags.RARE, Pair.of(0.25F, ChatFormatting.AQUA));
		map.put(EnvironmentalSlabfishTypeTags.EPIC, Pair.of(0.05F, ChatFormatting.LIGHT_PURPLE));
		map.put(EnvironmentalSlabfishTypeTags.LEGENDARY, Pair.of(0.01F, ChatFormatting.GOLD));
	});

	public static final Codec<SlabfishVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
				ComponentSerialization.CODEC.fieldOf("description").forGetter(entry -> entry.description),
				ResourceLocation.CODEC.fieldOf("texture").forGetter(entry -> entry.texture),
				RegistryFixedCodec.create(EnvironmentalRegistries.SLABFISH_BACKPACK).optionalFieldOf("backpack_override").forGetter(entry -> entry.backpackOverride),
				Codec.INT.optionalFieldOf("priority", 0).forGetter(entry -> entry.priority),
				SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList).fieldOf("conditions").forGetter(entry -> entry.conditions)
		).apply(instance, SlabfishVariant::new);
	});

	public static final StreamCodec<RegistryFriendlyByteBuf, SlabfishVariant> DIRECT_STREAM_CODEC = StreamCodec.composite(
			ComponentSerialization.STREAM_CODEC, SlabfishVariant::description,
			ResourceLocation.STREAM_CODEC, SlabfishVariant::texture,
			ByteBufCodecs.optional(SlabfishBackpack.STREAM_CODEC), SlabfishVariant::backpackOverride,
			ByteBufCodecs.INT, SlabfishVariant::priority,
			ByteBufCodecs.fromCodec(SlabfishCondition.CODEC.listOf().xmap(list -> list.toArray(SlabfishCondition[]::new), Arrays::asList)), SlabfishVariant::conditions,
			SlabfishVariant::new
	);

	public static final Codec<Holder<SlabfishVariant>> CODEC = RegistryFileCodec.create(EnvironmentalRegistries.SLABFISH_VARIANT, DIRECT_CODEC);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SlabfishVariant>> STREAM_CODEC = ByteBufCodecs.holder(EnvironmentalRegistries.SLABFISH_VARIANT, DIRECT_STREAM_CODEC);

	@Override
	public boolean test(SlabfishConditionContext slabfishEntity) {
		for (SlabfishCondition condition : this.conditions)
			if (!condition.test(slabfishEntity))
				return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlabfishType{" +
				//"registryName=" + registryName +
				", description=" + description.getString() +
				//", modLoaded=" + modLoaded +
				", priority=" + priority +
				'}';
	}

	public Holder<SlabfishVariant> holder(Level level) {
		Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(level.registryAccess());
		return registry.getHolderOrThrow(registry.getResourceKey(this).get());
	}

	public boolean is(Level level, TagKey<SlabfishVariant> tag) {
		Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(level.registryAccess());
		Optional<HolderSet.Named<SlabfishVariant>> set = registry.getTag(tag);
		if (set.isEmpty())
			return false;

		return set.get().contains(this.holder(level));
	}

	public static boolean canBeSold(Holder<SlabfishVariant> variant) {
		return !variant.is(EnvironmentalSlabfishTypeTags.NOT_SOLD_BY_WANDERING_TRADER);
	}

	public static boolean isTranslucent(Holder<SlabfishVariant> variant) {
		return variant.is(EnvironmentalSlabfishTypeTags.TRANSLUCENT);
	}

	public static TagKey<SlabfishVariant> getRandomRarity(float chance) {
		return SlabfishVariant.RARITIES.entrySet().stream()
				.filter(value -> chance < value.getValue().getFirst())
				.sorted(Comparator.comparingDouble(value -> value.getValue().getFirst()))
				.toList().getFirst().getKey();
	}

	public TagKey<SlabfishVariant> getRarity(Level level) {
		for (Map.Entry<TagKey<SlabfishVariant>, Pair<Float, ChatFormatting>> entry : SlabfishVariant.RARITIES.entrySet()) {
			if (this.is(level, entry.getKey()))
				return entry.getKey();
		}

		return EnvironmentalSlabfishTypeTags.COMMON;
	}
}
