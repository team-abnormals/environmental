package com.team_abnormals.environmental.common.entity.goals;

import java.util.EnumSet;
import java.util.List;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.pathfinding.Path;

public class SlabbyGrabItemGoal extends Goal {
	private final SlabfishEntity slabfish;
	private ItemEntity itemEntity;
	private final double moveSpeed;
	private int delayCounter;
		
	public SlabbyGrabItemGoal(SlabfishEntity animal, double speed) {
		this.slabfish = animal;
		this.moveSpeed = speed;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	
	public boolean shouldExecute() {
		if (!this.slabfish.hasBackpack()) {
			return false;
		} else if (this.slabfish.func_233685_eM_()) {
			return false;
		} else {
			List<ItemEntity> list = this.slabfish.world.getEntitiesWithinAABB(ItemEntity.class, this.slabfish.getBoundingBox().grow(12.0D, 4.0D, 12.0D));
			ItemEntity item = null;
			double d0 = Double.MAX_VALUE;
	
			for(ItemEntity item1 : list) {
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
		if (!this.slabfish.hasBackpack()) {
			return false;
		} else if (!this.itemEntity.isAlive()) {
   			return false;
   		} else {
			return true;
		}
	}
	
	public void startExecuting() {
		this.delayCounter = 0;
	}
	
	public void resetTask() {
		this.itemEntity = null;
	}
	
	public void tick() {
		if (--this.delayCounter <= 0) {
			this.delayCounter = 10;
			Path path = this.slabfish.getNavigator().getPathToEntity(itemEntity, 0);
			if(path != null) {
			    this.slabfish.getNavigator().setPath(path, this.moveSpeed);
			}
		}
	}
}
