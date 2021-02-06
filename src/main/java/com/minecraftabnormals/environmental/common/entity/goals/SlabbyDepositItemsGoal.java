package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;
import java.util.List;

public class SlabbyDepositItemsGoal extends MoveToBlockGoal implements IInventoryChangedListener {

	public SlabbyDepositItemsGoal(SlabfishEntity creature, double speedIn, int length) {
		super(creature, speedIn, length);
	}

	@Override
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		SlabfishEntity slabfish = (SlabfishEntity)creature;
		if (worldIn.getBlockState(pos).isIn(EnvironmentalBlocks.SLABFISH_EFFIGY.get()) && !slabfish.world.isDaytime() && slabfish.hasBackpack()) {
			boolean backpackEmpty = true;
			for (int i = 3; i < slabfish.slabfishBackpack.getSizeInventory(); i++) {
				ItemStack stack = slabfish.slabfishBackpack.getStackInSlot(i);
				if (!stack.isEmpty()) {
					backpackEmpty = false;
					break;
				}
			}

			return !backpackEmpty;
		}
		return false;
	}

	@Override
	public void onInventoryChanged(IInventory invBasic) {

	}
}
