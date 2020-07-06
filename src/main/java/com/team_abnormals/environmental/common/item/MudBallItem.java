package com.team_abnormals.environmental.common.item;

import com.team_abnormals.environmental.common.entity.MudBallEntity;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MudBallItem extends Item {
   public MudBallItem(Item.Properties builder) {
      super(builder);
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (!playerIn.abilities.isCreativeMode) {
         itemstack.shrink(1);
      }

      worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
      if (!worldIn.isRemote) {
         MudBallEntity mudballentity = new MudBallEntity(worldIn, playerIn);
         mudballentity.setItem(new ItemStack(EnvironmentalItems.MUD_BALL.get()));
         mudballentity.shoot(playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.6F, 1.0F);
         worldIn.addEntity(mudballentity);
      }

      playerIn.addStat(Stats.ITEM_USED.get(this));
      return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
   }
}
