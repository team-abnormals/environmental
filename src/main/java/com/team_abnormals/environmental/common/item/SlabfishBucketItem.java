package com.team_abnormals.environmental.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.entity.util.SlabfishType;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraftforge.registries.ForgeRegistries;

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
            if (compoundnbt.contains("SlabfishType", 3)) {
                int i = compoundnbt.getInt("SlabfishType");
                TextFormatting[] btextformatting = new TextFormatting[]{TextFormatting.ITALIC, SlabfishType.byId(i).getRarity().getFormatting()};
                tooltip.add((new TranslationTextComponent(SlabfishType.byId(i).getTranslationKey()).func_240701_a_(btextformatting)));
            }
            if (compoundnbt.contains("Age") && compoundnbt.getInt("Age") < 0) {
                TextFormatting[] atextformatting = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
                tooltip.add((new TranslationTextComponent("entity.environmental.slabfish.baby").func_240701_a_(atextformatting)));
            }
            if (compoundnbt.contains("HasBackpack") && compoundnbt.getBoolean("HasBackpack") && compoundnbt.contains("BackpackColor", 99)) {
                int i = compoundnbt.getInt("BackpackColor");
                TextFormatting[] atextformatting = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
                tooltip.add((new TranslationTextComponent("entity.environmental.slabfish." + DyeColor.byId(i).getTranslationKey() + "_backpack").func_240701_a_(atextformatting)));
            }


            ListNBT list = compoundnbt.getList("Items", 10);
            for (int i = 0; i < list.size(); ++i)
            {
                CompoundNBT slotNbt = list.getCompound(i);
                int index = slotNbt.getByte("Slot") & 255;
                if (index == 0)
                {
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(slotNbt.getString("id")));
                    if(item != null && SlabfishEntity.getSweaterMap().containsKey(item))
                        tooltip.add((new TranslationTextComponent("entity.environmental.slabfish." + SlabfishEntity.getSweaterMap().get(item).getTranslationKey() + "_sweater").func_240701_a_(TextFormatting.ITALIC, TextFormatting.GRAY)));
                    break;
                }
            }
        }
    }
}