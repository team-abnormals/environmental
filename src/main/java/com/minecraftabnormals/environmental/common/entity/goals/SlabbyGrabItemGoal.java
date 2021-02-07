package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;

import java.util.EnumSet;
import java.util.List;

public class SlabbyGrabItemGoal extends Goal implements IInventoryChangedListener {
	private final SlabfishEntity slabfish;
	private ItemEntity itemEntity;
	private final double moveSpeed;
	private int delayCounter;
	private boolean listening;

	public SlabbyGrabItemGoal(SlabfishEntity animal, double speed) {
		this.slabfish = animal;
		this.moveSpeed = speed;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	private boolean canPickupItem(IInventory inventory, ItemStack stack) {
		for (int i = 3; i < inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = inventory.getStackInSlot(i);
			if (stackInSlot.isEmpty() || (ItemStack.areItemsEqual(stack, stackInSlot) && ItemStack.areItemStackTagsEqual(stackInSlot, stack) && stackInSlot.getCount() < Math.min(stackInSlot.getMaxStackSize(), inventory.getInventoryStackLimit())))
				return true;
		}
		return false;
	}

	public boolean shouldExecute() {
		if (!this.slabfish.hasBackpack() || this.slabfish.backpackFull || this.slabfish.isSitting()) {
			return false;
		} else {
			List<ItemEntity> list = this.slabfish.world.getEntitiesWithinAABB(ItemEntity.class, this.slabfish.getBoundingBox().grow(12.0D, 4.0D, 12.0D));
			ItemEntity item = null;
			double d0 = Double.MAX_VALUE;

			this.slabfish.getNavigator().clearPath();

			for (ItemEntity item1 : list) {
				if (!canPickupItem(this.slabfish.slabfishBackpack, item1.getItem()))
					continue;
				double d1 = this.slabfish.getDistanceSq(item1);
				if (d1 < d0) {
					d0 = d1;
					item = item1;
				}
			}

			if (item == null) {
				return false;
			} else {
				this.itemEntity = item;
				return true;
			}
		}
	}

	public boolean shouldContinueExecuting() {
		return this.slabfish.hasBackpack() && !this.slabfish.backpackFull && this.itemEntity != null && this.itemEntity.isAlive();
	}

	public void startExecuting() {
		this.delayCounter = 0;
		if (!this.listening) {
			this.listening = true;
			this.slabfish.slabfishBackpack.addListener(this);
		}
	}

	public void resetTask() {
		this.itemEntity = null;
	}

	public void tick() {
		if (--this.delayCounter <= 0) {
			this.delayCounter = 10;
			Path path = this.slabfish.getNavigator().getPathToEntity(itemEntity, 0);
			if (path != null) {
				this.slabfish.getNavigator().setPath(path, this.moveSpeed);
			}
		}
	}

	@Override
	public void onInventoryChanged(IInventory inventory) {
		if (itemEntity != null) {
			ItemStack stack = itemEntity.getItem();
			if (!canPickupItem(inventory, stack))
				itemEntity = null;
		}
	}
}
