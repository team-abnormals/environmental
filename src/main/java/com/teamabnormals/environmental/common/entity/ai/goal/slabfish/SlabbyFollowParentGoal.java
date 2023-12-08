package com.teamabnormals.environmental.common.entity.ai.goal.slabfish;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;

public class SlabbyFollowParentGoal extends Goal {
	private final Slabfish childAnimal;
	private Slabfish parentAnimal;
	private final double moveSpeed;
	private int delayCounter;

	public SlabbyFollowParentGoal(Slabfish animal, double speed) {
		this.childAnimal = animal;
		this.moveSpeed = speed;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (this.childAnimal.getAge() >= 0) {
			return false;
		} else if (this.childAnimal.isOrderedToSit()) {
			return false;
		} else {
			List<? extends Slabfish> list = this.childAnimal.level.getEntitiesOfClass(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
			Slabfish slabfish = null;
			double d0 = Double.MAX_VALUE;

			for (Slabfish slabfish1 : list) {
				if (slabfish1.getAge() >= 0) {
					double d1 = this.childAnimal.distanceToSqr(slabfish1);
					if (!(d1 > d0)) {
						d0 = d1;
						slabfish = slabfish1;
					}
				}
			}

			if (slabfish == null) {
				return false;
			} else if (d0 < 9.0D) {
				return false;
			} else {
				this.parentAnimal = slabfish;
				return true;
			}
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (this.childAnimal.getAge() >= 0) {
			return false;
		} else if (!this.parentAnimal.isAlive()) {
			return false;
		} else {
			double d0 = this.childAnimal.distanceToSqr(this.parentAnimal);
			return !(d0 < 9.0D) && !(d0 > 256.0D);
		}
	}

	@Override
	public void start() {
		this.delayCounter = 0;
	}

	@Override
	public void stop() {
		this.parentAnimal = null;
	}

	@Override
	public void tick() {
		if (--this.delayCounter <= 0) {
			this.delayCounter = this.adjustedTickDelay(10);
			this.childAnimal.getNavigation().moveTo(this.parentAnimal, this.moveSpeed);
		}
	}
}
