package com.team_abnormals.environmental.common.entity.goals;

import java.util.EnumSet;
import java.util.List;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;

import net.minecraft.entity.ai.goal.Goal;

public class SlabbyFollowParentGoal extends Goal {
	private final SlabfishEntity childAnimal;
	private SlabfishEntity parentAnimal;
	private final double moveSpeed;
	private int delayCounter;

	public SlabbyFollowParentGoal(SlabfishEntity animal, double speed) {
		this.childAnimal = animal;
		this.moveSpeed = speed;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	   public boolean shouldExecute() {
	      if (this.childAnimal.getGrowingAge() >= 0) {
	         return false;
	      } else if (this.childAnimal.isSitting()) {
	          return false;
	      } else {
	         List<SlabfishEntity> list = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
	         SlabfishEntity SlabfishEntity = null;
	         double d0 = Double.MAX_VALUE;

	         for(SlabfishEntity SlabfishEntity1 : list) {
	            if (SlabfishEntity1.getGrowingAge() >= 0) {
	               double d1 = this.childAnimal.getDistanceSq(SlabfishEntity1);
	               if (!(d1 > d0)) {
	                  d0 = d1;
	                  SlabfishEntity = SlabfishEntity1;
	               }
	            }
	         }

	         if (SlabfishEntity == null) {
	            return false;
	         } else if (d0 < 9.0D) {
	            return false;
	         } else {
	            this.parentAnimal = SlabfishEntity;
	            return true;
	         }
	      }
	   }

	   public boolean shouldContinueExecuting() {
	      if (this.childAnimal.getGrowingAge() >= 0) {
	         return false;
	      } else if (!this.parentAnimal.isAlive()) {
	         return false;
	      } else {
	         double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
	         return !(d0 < 9.0D) && !(d0 > 256.0D);
	      }
	   }

	   public void startExecuting() {
	      this.delayCounter = 0;
	   }

	   public void resetTask() {
	      this.parentAnimal = null;
	   }

	   public void tick() {
	      if (--this.delayCounter <= 0) {
	         this.delayCounter = 10;
	         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
	      }
	   }
	}
