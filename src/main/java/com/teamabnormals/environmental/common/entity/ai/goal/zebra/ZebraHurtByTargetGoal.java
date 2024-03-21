package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class ZebraHurtByTargetGoal extends HurtByTargetGoal {

	public ZebraHurtByTargetGoal(Zebra zebra) {
		super(zebra);
	}

	@Override
	public void start() {
		super.start();
		if (this.mob.isBaby()) {
			this.stop();
		}
	}
}