package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;

import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class DeerFollowParentGoal extends FollowParentGoal {

	public DeerFollowParentGoal(Deer deer)
	{
		super(deer, 1.2D);
	}

	@Override
	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			boolean flag = this.parent.isSprinting() || ((AbstractDeer) this.animal).getNearestScaryEntity() != null;
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			this.animal.getNavigation().moveTo(this.parent, flag ? 1.75F : 1.2F);
		}
	}
}