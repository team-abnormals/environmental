package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Locale;

public class KoiBucketItem extends MobBucketItem {

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
		if (tag.contains("BucketVariantTag", Tag.TAG_INT)) {
			tooltip.add(Component.translatable("entity." + Environmental.MOD_ID + ".koi.type." + KoiBreed.byId(tag.getInt("BucketVariantTag")).name().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
		}
	}
}
