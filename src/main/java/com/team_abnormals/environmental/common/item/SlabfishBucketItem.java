package com.team_abnormals.environmental.common.item;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.slabfish.SlabfishManager;
import com.team_abnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SlabfishBucketItem extends BucketItem {
    private final Supplier<EntityType<? extends SlabfishEntity>> entityType;

    public SlabfishBucketItem(Supplier<EntityType<? extends SlabfishEntity>> entityType, Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder);
        this.entityType = entityType;
    }

    public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
        if (!worldIn.isRemote) {
            this.placeEntity(worldIn, p_203792_2_, pos);
        }
    }

    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeEntity(World worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(worldIn, stack, (PlayerEntity) null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            ((SlabfishEntity) entity).setFromBucket(true);
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            int targetIndex = ItemStackUtils.findIndexOfItem(Items.TROPICAL_FISH_BUCKET, items);
            if (targetIndex != -1) {
                items.add(targetIndex + 1, new ItemStack(this));
            } else {
                super.fillItemGroup(group, items);
            }
        }
    }

    @Deprecated
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null) {
            if (compoundnbt.contains("SlabfishType", Constants.NBT.TAG_STRING)) {
                SlabfishType slabfishType = SlabfishManager.get(worldIn).get(new ResourceLocation(compoundnbt.getString("SlabfishType")));
                if (slabfishType != SlabfishManager.DEFAULT_SLABFISH)
                    tooltip.add(slabfishType.getDisplayName().deepCopy().func_240701_a_(TextFormatting.ITALIC, slabfishType.getRarity().getFormatting()));
            }
            if (compoundnbt.contains("Age", Constants.NBT.TAG_ANY_NUMERIC) && compoundnbt.getInt("Age") < 0) {
                tooltip.add((new TranslationTextComponent("entity.environmental.slabfish.baby").func_240701_a_(TextFormatting.ITALIC, TextFormatting.GRAY)));
            }
            if (compoundnbt.contains("HasBackpack") && compoundnbt.getBoolean("HasBackpack") && compoundnbt.contains("BackpackColor", Constants.NBT.TAG_ANY_NUMERIC)) {
                int i = compoundnbt.getInt("BackpackColor");
                tooltip.add((new TranslationTextComponent("entity.environmental.slabfish." + DyeColor.byId(i).getTranslationKey() + "_backpack").func_240701_a_(TextFormatting.ITALIC, TextFormatting.GRAY)));
            }

            ListNBT list = compoundnbt.getList("Items", Constants.NBT.TAG_LIST);
            for (int i = 0; i < list.size(); ++i) {
                CompoundNBT slotNbt = list.getCompound(i);
                int index = slotNbt.getByte("Slot") & 255;
                if (index == 0) {
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(slotNbt.getString("id")));
                    if (item != null && SlabfishEntity.getSweaterMap().containsKey(item))
                        tooltip.add((new TranslationTextComponent("entity.environmental.slabfish." + SlabfishEntity.getSweaterMap().get(item).getTranslationKey() + "_sweater").func_240701_a_(TextFormatting.ITALIC, TextFormatting.GRAY)));
                    break;
                }
            }
        }
    }
}