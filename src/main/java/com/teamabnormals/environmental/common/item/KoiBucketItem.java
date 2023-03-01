package com.teamabnormals.environmental.common.item;

import com.teamabnormals.blueprint.common.item.BlueprintMobBucketItem;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class KoiBucketItem extends BlueprintMobBucketItem {

	public KoiBucketItem(Properties properties) {
		super(EnvironmentalEntityTypes.KOI, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("BucketVariantTag", Tag.TAG_INT)) {
			tooltip.add(Component.translatable("entity." + Environmental.MOD_ID + ".koi.type." + KoiBreed.byId(tag.getInt("BucketVariantTag")).name().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
		}
	}
}
