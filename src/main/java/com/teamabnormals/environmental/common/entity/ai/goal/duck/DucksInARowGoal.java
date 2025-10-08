package com.teamabnormals.environmental.common.entity.ai.goal.duck;

import com.teamabnormals.environmental.common.entity.animal.Duck;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class DucksInARowGoal extends Goal {
	public final Duck duck;
	private double speedModifier;
	private int distCheckCounter;

	public DucksInARowGoal(Duck p_25501_, double p_25502_) {
		this.duck = p_25501_;
		this.speedModifier = p_25502_;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		if (!this.duck.isLeashed() && !this.duck.inRow() && this.duck.isBaby()) {
			List<Entity> list = this.duck.level().getEntities(this.duck, this.duck.getBoundingBox().inflate(9.0F, 4.0F, 9.0F), (entity) -> entity instanceof Duck);
			Duck duck = null;
			double d0 = Double.MAX_VALUE;

			for (Entity entity : list) {
				Duck duck1 = (Duck) entity;
				if (duck1.inRow() && !duck1.hasRow()) {
					double d1 = this.duck.distanceToSqr(duck1);
					if (!(d1 > d0)) {
						d0 = d1;
						duck = duck1;
					}
				}
			}

			if (duck == null) {
				for (Entity entity1 : list) {
					Duck duck2 = (Duck) entity1;
					if (!duck2.isBaby() && !duck2.hasRow()) {
						double d2 = this.duck.distanceToSqr(duck2);
						if (!(d2 > d0)) {
							d0 = d2;
							duck = duck2;
						}
					}
				}
			}

			if (duck == null) {
				return false;
			} else if (d0 < (double) 0.25F) {
				return false;
			} else if (duck.isBaby() && !this.firstIsAdult(duck, 1)) {
				return false;
			} else {
				this.duck.joinRow(duck);
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean canContinueToUse() {
		if (this.duck.inRow() && this.duck.getRowHead().isAlive() && this.firstIsAdult(this.duck, 0)) {
			double d0 = this.duck.distanceToSqr(this.duck.getRowHead());
			if (d0 > (double) 676.0F) {
				if (this.speedModifier <= (double) 3.0F) {
					this.speedModifier *= 1.2;
					this.distCheckCounter = reducedTickDelay(40);
					return true;
				}

				if (this.distCheckCounter == 0) {
					return false;
				}
			}

			if (this.distCheckCounter > 0) {
				--this.distCheckCounter;
			}

			return true;
		} else {
			return false;
		}
	}

	public void stop() {
		this.duck.leaveRow();
		this.speedModifier = 1.1;
	}

	public void tick() {
		if (this.duck.inRow()) {
			Duck duck = this.duck.getRowHead();
			double d0 = this.duck.distanceTo(duck);
			Vec3 vec3 = (new Vec3(duck.getX() - this.duck.getX(), duck.getY() - this.duck.getY(), duck.getZ() - this.duck.getZ())).normalize().scale(Math.max(d0 - 0.25F, 0.0F));
			this.duck.getNavigation().moveTo(this.duck.getX() + vec3.x, this.duck.getY() + vec3.y, this.duck.getZ() + vec3.z, this.speedModifier);
		}
	}

	private boolean firstIsAdult(Duck duck1, int size) {
		if (size > 8) {
			return false;
		} else if (duck1.inRow()) {
			if (!duck1.getRowHead().isBaby()) {
				return true;
			} else {
				Duck duck = duck1.getRowHead();
				++size;
				return this.firstIsAdult(duck, size);
			}
		} else {
			return false;
		}
	}
}
