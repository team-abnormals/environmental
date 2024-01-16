package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.ai.goal.Goal;

public class ZebraFleeGoal extends Goal {
	private final Zebra zebra;
	private final double speedModifier;

	public ZebraFleeGoal(Zebra zebra, double speed) {
		this.zebra = zebra;
		this.speedModifier = speed;
	}

	@Override
	public boolean canUse() {
		return false;
	}
}