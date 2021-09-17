package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.BackpackType;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.common.slabfish.SweaterType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SlabfishBucketItem extends BucketItem {
	private static final Map<String, ResourceLocation> LOCATION_CACHE = new HashMap<>();
	private static final MutablePair<CompoundNBT, SweaterType> SWEATER_TYPE_CACHE = new MutablePair<>(null, SlabfishManager.EMPTY_SWEATER);
	private final Supplier<EntityType<? extends SlabfishEntity>> entityType;
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.TROPICAL_FISH_BUCKET);

	public SlabfishBucketItem(Supplier<EntityType<? extends SlabfishEntity>> entityType, Supplier<? extends Fluid> supplier, Item.Properties builder) {
		super(supplier, builder);
		this.entityType = entityType;
	}

	public void checkExtraContent(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
		if (worldIn instanceof ServerWorld) {
			this.placeEntity((ServerWorld) worldIn, p_203792_2_, pos);
		}
	}

	protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}

	private void placeEntity(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.entityType.get().spawn(worldIn, stack, null, pos, SpawnReason.BUCKET, true, false);
		if (entity != null) {
			((SlabfishEntity) entity).setFromBucket(true);
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this, group, items);
	}

	@Deprecated
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT compoundnbt = stack.getTag();
		if (compoundnbt != null) {
			SlabfishManager slabfishManager = SlabfishManager.get(worldIn);

			if (compoundnbt.contains("SlabfishType", Constants.NBT.TAG_STRING)) {
				SlabfishType slabfishType = slabfishManager.getSlabfishType(LOCATION_CACHE.computeIfAbsent(compoundnbt.getString("SlabfishType"), ResourceLocation::new)).orElse(SlabfishManager.DEFAULT_SLABFISH);
				if (slabfishType != SlabfishManager.DEFAULT_SLABFISH)
					tooltip.add(slabfishType.getDisplayName().copy().withStyle(TextFormatting.ITALIC, slabfishType.getRarity().getFormatting()));
			}
			if (compoundnbt.contains("Age", Constants.NBT.TAG_ANY_NUMERIC) && compoundnbt.getInt("Age") < 0) {
				tooltip.add((new TranslationTextComponent("entity.environmental.slabfish.baby").withStyle(TextFormatting.ITALIC, TextFormatting.GRAY)));
			}
			if (compoundnbt.contains("BackpackType", Constants.NBT.TAG_STRING)) {
				BackpackType backpackType = slabfishManager.getBackpackType(LOCATION_CACHE.computeIfAbsent(compoundnbt.getString("BackpackType"), ResourceLocation::new)).orElse(SlabfishManager.BROWN_BACKPACK);
				tooltip.add(backpackType.getDisplayName().copy().withStyle(TextFormatting.ITALIC, TextFormatting.GRAY));
			}

			if (!compoundnbt.equals(SWEATER_TYPE_CACHE.getLeft())) {
				SWEATER_TYPE_CACHE.setLeft(compoundnbt);
				SWEATER_TYPE_CACHE.setRight(SlabfishManager.EMPTY_SWEATER);

				ListNBT list = compoundnbt.getList("Items", Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < list.size(); i++) {
					CompoundNBT slotNbt = list.getCompound(i);
					int index = slotNbt.getByte("Slot") & 255;
					if (index == 0) {
						ItemStack slotStack = ItemStack.of(slotNbt);
						SWEATER_TYPE_CACHE.setRight(slabfishManager.getSweaterType(slotStack).orElse(SlabfishManager.EMPTY_SWEATER));
						break;
					}
				}
			}

			if (!SlabfishManager.EMPTY_SWEATER.equals(SWEATER_TYPE_CACHE.getRight()))
				tooltip.add(SWEATER_TYPE_CACHE.getRight().getDisplayName().copy().withStyle(TextFormatting.ITALIC, TextFormatting.GRAY));
		}
	}
}