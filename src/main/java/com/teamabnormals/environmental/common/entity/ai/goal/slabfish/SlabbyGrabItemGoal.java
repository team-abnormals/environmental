package com.teamabnormals.environmental.common.entity.ai.goal.slabfish;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
import java.util.List;

public class SlabbyGrabItemGoal extends Goal implements ContainerListener {
	private final Slabfish slabfish;
	private ItemEntity itemEntity;
	private final double moveSpeed;
	private int delayCounter;
	private boolean listening;

	public SlabbyGrabItemGoal(Slabfish animal, double speed) {
		this.slabfish = animal;
		this.moveSpeed = speed;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	private boolean canPickupItem(Container inventory, ItemStack stack) {
		for (int i = 3; i < inventory.getContainerSize(); i++) {
			ItemStack stackInSlot = inventory.getItem(i);
			if (stackInSlot.isEmpty() || (ItemStack.isSame(stack, stackInSlot) && ItemStack.tagMatches(stackInSlot, stack) && stackInSlot.getCount() < Math.min(stackInSlot.getMaxStackSize(), inventory.getMaxStackSize())))
				return true;
		}
		return false;
	}

	@Override
	public boolean canUse() {
		if (!this.slabfish.hasBackpack() || this.slabfish.backpackFull || this.slabfish.isOrderedToSit()) {
			return false;
		} else {
			List<ItemEntity> list = this.slabfish.level.getEntitiesOfClass(ItemEntity.class, this.slabfish.getBoundingBox().inflate(12.0D, 4.0D, 12.0D));
			ItemEntity item = null;
			double d0 = Double.MAX_VALUE;

			for (ItemEntity item1 : list) {
				if (item1.getThrower() == this.slabfish.getUUID() || !canPickupItem(this.slabfish.slabfishBackpack, item1.getItem()))
					continue;
				double d1 = this.slabfish.distanceToSqr(item1);
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

	@Override
	public boolean canContinueToUse() {
		return this.slabfish.hasBackpack() && !this.slabfish.backpackFull && this.itemEntity != null && this.itemEntity.isAlive();
	}

	@Override
	public void start() {
		this.delayCounter = 0;
		if (!this.listening) {
			this.listening = true;
			this.slabfish.slabfishBackpack.addListener(this);
		}
	}

	@Override
	public void stop() {
		this.itemEntity = null;
	}

	@Override
	public void tick() {
		if (--this.delayCounter <= 0) {
			this.delayCounter = this.adjustedTickDelay(10);
			Path path = this.slabfish.getNavigation().createPath(itemEntity, 0);
			if (path != null) {
				this.slabfish.getNavigation().stop();
				this.slabfish.getNavigation().moveTo(path, this.moveSpeed);
			}
		}
	}

	@Override
	public void containerChanged(Container inventory) {
		if (itemEntity != null) {
			ItemStack stack = itemEntity.getItem();
			if (!canPickupItem(inventory, stack))
				itemEntity = null;
		}
	}
}
