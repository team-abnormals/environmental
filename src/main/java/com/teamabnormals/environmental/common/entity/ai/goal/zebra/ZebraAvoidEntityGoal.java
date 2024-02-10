package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class ZebraAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

	public ZebraAvoidEntityGoal(Zebra zebra, Class<T> toAvoid, float maxDist, double speed, double sprintSpeed) {
		super(zebra, toAvoid, maxDist, speed, sprintSpeed);
	}

	public boolean canUse() {
		return !((Zebra) this.mob).isTamed() && super.canUse();
	}
}