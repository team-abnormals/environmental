package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.common.entity.KoiEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.function.Supplier;

public class KoiBucketItem extends BucketItem {
    
    private final Supplier<EntityType<? extends KoiEntity>> entityType;
    
    public KoiBucketItem(Supplier<EntityType<? extends KoiEntity>> entityType, Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
        this.entityType = entityType;
    }
    
    @Override
    public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
        if (worldIn instanceof ServerWorld) {
            this.placeEntity((ServerWorld)worldIn, p_203792_2_, pos);
        }
    }
    
    private void placeEntity(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(worldIn, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            ((KoiEntity) entity).setFromBucket(true);
        }
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
