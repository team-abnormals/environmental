package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiVariant;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KoiBucketItem extends MobBucketItem {
	private static final Map<String, Optional<Holder<KoiVariant>>> TYPE_CACHE = new HashMap<>();

	public KoiBucketItem(Properties properties) {
		super(EnvironmentalEntityTypes.KOI.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		CustomData data = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
		if (data.isEmpty()) {
			return;
		}

		CompoundTag tag = data.copyTag();
		if (tag.contains(Koi.BUCKET_VARIANT_TAG, CompoundTag.TAG_STRING) && context.registries() != null) {
			RegistryLookup<KoiVariant> registry = context.registries().lookupOrThrow(EnvironmentalRegistries.KOI_VARIANT);

			Optional<Holder<KoiVariant>> koi = TYPE_CACHE.computeIfAbsent(tag.getString(Koi.BUCKET_VARIANT_TAG), s ->
					Optional.ofNullable(ResourceLocation.tryParse(tag.getString(Koi.BUCKET_VARIANT_TAG)))
							.map(loc -> ResourceKey.create(EnvironmentalRegistries.KOI_VARIANT, loc))
							.flatMap(registry::get));

			koi.ifPresent(koiVariantHolder -> tooltip.add(koiVariantHolder.value().description().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)));
		}
	}
}
