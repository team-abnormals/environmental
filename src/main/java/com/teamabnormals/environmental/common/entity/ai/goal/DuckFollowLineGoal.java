package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.Duck;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class DuckFollowLineGoal extends Goal {
	protected final Duck duck;
	protected int timeToRecalcPath;
	private int nextStartTick;

	public DuckFollowLineGoal(Duck duck) {
		this.duck = duck;
		this.nextStartTick = this.nextStartTick(duck);
	}

	protected int nextStartTick(Duck duck) {
		return reducedTickDelay(200 + duck.getRandom().nextInt(200) % 20);
	}

	@Override
	public boolean canUse() {
		if (this.duck.getAge() >= 0) {
			return false;
		} else if (this.duck.isFollower()) {
			return true;
		} else if (this.nextStartTick > 0) {
			--this.nextStartTick;
			return false;
		} else {
			List<? extends Duck> nearbyDucks = this.duck.level().getEntitiesOfClass(this.duck.getClass(), this.duck.getBoundingBox().inflate(8.0F, 4.0F, 8.0F));

			for (Duck leader : nearbyDucks) {
				if (leader.getAge() >= 0) {
					this.duck.joinDuckLine(leader);
					break;
				}
			}

			return this.duck.isFollower();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return this.duck.isFollower() && this.duck.inRangeOfLeader();
	}

	@Override
	public void start() {
		this.timeToRecalcPath = 0;
	}

	@Override
	public void stop() {
		this.duck.removeFromDuckLine();
	}

	@Override
	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			this.duck.pathToLeader();
		}
	}
}