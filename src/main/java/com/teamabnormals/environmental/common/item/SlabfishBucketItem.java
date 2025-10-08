package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.SlabfishBackpack;
import com.teamabnormals.environmental.common.slabfish.SlabfishSweater;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SlabfishBucketItem extends MobBucketItem {
	private static final Map<String, Optional<Holder<SlabfishVariant>>> VARIANT_CACHE = new HashMap<>();
	private static final Map<String, Optional<Holder<SlabfishBackpack>>> BACKPACK_CACHE = new HashMap<>();
	private static final Map<String, Optional<Holder<SlabfishSweater>>> SWEATER_CACHE = new HashMap<>();

	public SlabfishBucketItem(Item.Properties builder) {
		super(EnvironmentalEntityTypes.SLABFISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, builder);
	}

	@Deprecated
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		CustomData data = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
		if (data.isEmpty()) {
			return;
		}

		CompoundTag tag = data.copyTag();
		HolderLookup.Provider provider = context.registries();
		if (provider != null) {
			if (tag.contains(Slabfish.BUCKET_VARIANT_TAG, CompoundTag.TAG_STRING)) {
				Optional<Holder<SlabfishVariant>> holder = VARIANT_CACHE.computeIfAbsent(tag.getString(Slabfish.BUCKET_VARIANT_TAG), s -> Optional.ofNullable(ResourceLocation.tryParse(s))
						.map(loc -> ResourceKey.create(EnvironmentalRegistries.SLABFISH_VARIANT, loc))
						.flatMap(key -> provider.lookupOrThrow(EnvironmentalRegistries.SLABFISH_VARIANT).get(key)));

				if (holder.isPresent()) {
					SlabfishVariant slabfishVariant = holder.get().value();
					tooltip.add(slabfishVariant.description().copy().withStyle(ChatFormatting.ITALIC, SlabfishVariant.RARITIES.get(slabfishVariant.getRarity(context.level())).getSecond()));
				}
			}

			if (tag.contains("Age", Tag.TAG_ANY_NUMERIC) && tag.getInt("Age") < 0) {
				tooltip.add(Component.translatable("entity.environmental.slabfish.baby").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
			}

			if (tag.contains(Slabfish.BACKPACK_TAG, CompoundTag.TAG_STRING)) {
				Optional<Holder<SlabfishBackpack>> holder = BACKPACK_CACHE.computeIfAbsent(tag.getString(Slabfish.BACKPACK_TAG), s -> Optional.ofNullable(ResourceLocation.tryParse(s))
						.map(loc -> ResourceKey.create(EnvironmentalRegistries.SLABFISH_BACKPACK, loc))
						.flatMap(key -> provider.lookupOrThrow(EnvironmentalRegistries.SLABFISH_BACKPACK).get(key)));

				if (holder.isPresent()) {
					SlabfishBackpack backpackType = holder.get().value();
					tooltip.add(backpackType.description().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
				}
			}

			if (tag.contains(Slabfish.SWEATER_TAG, CompoundTag.TAG_STRING)) {
				Optional<Holder<SlabfishSweater>> holder = SWEATER_CACHE.computeIfAbsent(tag.getString(Slabfish.SWEATER_TAG), s -> Optional.ofNullable(ResourceLocation.tryParse(s))
						.map(loc -> ResourceKey.create(EnvironmentalRegistries.SLABFISH_SWEATER, loc))
						.flatMap(key -> provider.lookupOrThrow(EnvironmentalRegistries.SLABFISH_SWEATER).get(key)));

				if (holder.isPresent()) {
					SlabfishSweater sweaterType = holder.get().value();
					tooltip.add(sweaterType.description().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
				}
			}
		}
	}
}