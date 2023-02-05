package com.teamabnormals.environmental.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SlabfishBucketItem extends MobBucketItem {
	private static final Map<String, ResourceLocation> LOCATION_CACHE = new HashMap<>();
	private static final MutablePair<CompoundTag, SweaterType> SWEATER_TYPE_CACHE = new MutablePair<>(null, SlabfishManager.EMPTY_SWEATER);
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.TROPICAL_FISH_BUCKET);

	public SlabfishBucketItem(Supplier<EntityType<? extends Slabfish>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties builder) {
		super(entityType, fluid, soundEvent, builder);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}

	@Deprecated
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		CompoundTag tag = stack.getTag();
		if (tag != null) {
			SlabfishManager slabfishManager = SlabfishManager.get(worldIn);

			if (tag.contains("SlabfishType", Tag.TAG_STRING)) {
				SlabfishType slabfishType = slabfishManager.getSlabfishType(LOCATION_CACHE.computeIfAbsent(tag.getString("SlabfishType"), ResourceLocation::new)).orElse(SlabfishManager.DEFAULT_SLABFISH);
				if (slabfishType != SlabfishManager.DEFAULT_SLABFISH)
					tooltip.add(slabfishType.getDisplayName().copy().withStyle(ChatFormatting.ITALIC, slabfishType.getRarity().getFormatting()));
			}
			if (tag.contains("Age", Tag.TAG_ANY_NUMERIC) && tag.getInt("Age") < 0) {
				tooltip.add((Component.translatable("entity.environmental.slabfish.baby").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)));
			}
			if (tag.contains("BackpackType", Tag.TAG_STRING)) {
				BackpackType backpackType = slabfishManager.getBackpackType(LOCATION_CACHE.computeIfAbsent(tag.getString("BackpackType"), ResourceLocation::new)).orElse(SlabfishManager.BROWN_BACKPACK);
				tooltip.add(backpackType.getDisplayName().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
			}

			if (!tag.equals(SWEATER_TYPE_CACHE.getLeft())) {
				SWEATER_TYPE_CACHE.setLeft(tag);
				SWEATER_TYPE_CACHE.setRight(SlabfishManager.EMPTY_SWEATER);

				ListTag list = tag.getList("Items", Tag.TAG_COMPOUND);
				for (int i = 0; i < list.size(); i++) {
					CompoundTag slotNbt = list.getCompound(i);
					int index = slotNbt.getByte("Slot") & 255;
					if (index == 0) {
						ItemStack slotStack = ItemStack.of(slotNbt);
						SWEATER_TYPE_CACHE.setRight(slabfishManager.getSweaterType(slotStack).orElse(SlabfishManager.EMPTY_SWEATER));
						break;
					}
				}
			}

			if (!SlabfishManager.EMPTY_SWEATER.equals(SWEATER_TYPE_CACHE.getRight()))
				tooltip.add(SWEATER_TYPE_CACHE.getRight().getDisplayName().copy().withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
		}
	}
}