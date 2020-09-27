package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.common.block.LargeLilyPadBlock;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class LargeLilyPadItem extends BlockItem {
    public LargeLilyPadItem(Item.Properties builder) {
        super(EnvironmentalBlocks.LARGE_LILY_PAD.get(), builder);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
                BlockPos blockpos = blockraytraceresult.getPos();
                Direction direction = blockraytraceresult.getFace();
                if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(direction), direction, itemstack)) {
                    return ActionResult.resultFail(itemstack);
                }

                BlockPos blockpos1 = blockpos.up();
                FluidState ifluidstate = worldIn.getFluidState(blockpos);
                if ((ifluidstate.getFluid() == Fluids.WATER) && LargeLilyPadBlock.checkPositions(worldIn, blockpos1, this.getBlock().getDefaultState())) {

                    // special case for handling block placement with water lilies
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.create(worldIn, blockpos1);
                    if (!worldIn.isRemote())
                    	LargeLilyPadBlock.placeAt(worldIn, blockpos1, this.getBlock().getDefaultState(), 18);
                    if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerIn, blocksnapshot, net.minecraft.util.Direction.UP)) {
                        blocksnapshot.restore(true, false);
                        return ActionResult.resultFail(itemstack);
                    }

                    if (playerIn instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1, itemstack);
                    }

                    if (!playerIn.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.resultSuccess(itemstack);
                }
            }

            return ActionResult.resultFail(itemstack);
        }
    }
}
