package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.slabfish.BackpackType;
import com.team_abnormals.environmental.common.slabfish.SlabfishManager;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnvironmentalCompat {

    public static void setupVanilla() {
        // TODO make these work properly with tags or smth
        for(Item item : SlabfishEntity.getSweaterMap().keySet()) {
            DispenserBlock.registerDispenseBehavior(item, new OptionalDispenseBehavior() {
                @Override
                protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

                    for(SlabfishEntity slabfishEntity : source.getWorld().getEntitiesWithinAABB(SlabfishEntity.class, new AxisAlignedBB(blockpos), (entity) -> entity.isAlive() && !entity.hasSweater())) {
                        if (SlabfishEntity.getSweaterMap().containsKey(stack.getItem())) {
                            slabfishEntity.slabfishBackpack.setInventorySlotContents(0, stack.split(1));
                            this.func_239796_a_(true);
                            return stack;
                        }
                    }

                    return super.dispenseStack(source, stack);
                }
            });
        }

        DispenserBlock.registerDispenseBehavior(EnvironmentalItems.SLABFISH_BUCKET.get(), new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BucketItem bucket = (BucketItem)stack.getItem();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                World world = source.getWorld();

                if (bucket.tryPlaceContainedLiquid(null, world, pos, null)) {
                    bucket.onLiquidPlaced(world, stack, pos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return super.dispense(source, stack);
                }
            }
        });

        DispenserBlock.registerDispenseBehavior(Items.CHEST, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));

                for(SlabfishEntity slabfishEntity : source.getWorld().getEntitiesWithinAABB(SlabfishEntity.class, new AxisAlignedBB(blockpos), (entity) -> entity.isAlive() && !entity.hasBackpack())) {
                    slabfishEntity.slabfishBackpack.setInventorySlotContents(1, stack.split(1));
                    this.func_239796_a_(true);
                    return stack;
                }
                return super.dispenseStack(source, stack);
            }
        });
    }
}
