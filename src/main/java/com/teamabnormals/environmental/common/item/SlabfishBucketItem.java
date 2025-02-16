package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishSweaters;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlabfishBucketItem extends MobBucketItem {
	private static final Map<String, ResourceLocation> TYPE_CACHE = new HashMap<>();
	private static final Map<String, ResourceLocation> BACKPACK_CACHE = new HashMap<>();
	private static final Map<String, ResourceLocation> SWEATER_CACHE = new HashMap<>();

	public SlabfishBucketItem(Item.Properties builder) {
		super(EnvironmentalEntityTypes.SLABFISH::get, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, builder);
	}

	@Deprecated
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag tag = stack.getTag();
		if (tag != null) {
			if (tag.contains("SlabfishType", Tag.TAG_STRING)) {
				Registry<SlabfishType> registry = SlabfishHelper.slabfishTypes(worldIn);
				SlabfishType slabfishType = registry.get(TYPE_CACHE.computeIfAbsent(tag.getString("SlabfishType"), ResourceLocation::new));
				if (slabfishType != null)
					tooltip.add(slabfishType.displayName().copy().withStyle(ChatFormatting.ITALIC, SlabfishType.RARITIES.get(slabfishType.getRarity(worldIn)).getSecond()));
			}

			if (tag.contains("Age", Tag.TAG_ANY_NUMERIC) && tag.getInt("Age") < 0) {
				tooltip.add(Component.translatable("entity.environmental.slabfish.baby").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
			}

			if (tag.contains("BackpackType", Tag.TAG_STRING)) {
				Registry<BackpackType> registry = SlabfishHelper.slabfishBackpacks(worldIn);
				BackpackType backpackType = registry.get(BACKPACK_CACHE.computeIfAbsent(tag.getString("BackpackType"), ResourceLocation::new));
				if (backpackType != null) {
					tooltip.add(backpackType.displayName().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
				}
			}

			if (tag.contains("SweaterType", Tag.TAG_STRING)) {
				Registry<SweaterType> registry = SlabfishHelper.slabfishSweaters(worldIn);
				SweaterType sweaterType = registry.get(SWEATER_CACHE.computeIfAbsent(tag.getString("SweaterType"), ResourceLocation::new));
				if (sweaterType != null && sweaterType.displayName().isPresent() && !registry.getKey(sweaterType).equals(EnvironmentalSlabfishSweaters.EMPTY.location())) {
					tooltip.add(sweaterType.displayName().get().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
				}
			}
		}
	}
}