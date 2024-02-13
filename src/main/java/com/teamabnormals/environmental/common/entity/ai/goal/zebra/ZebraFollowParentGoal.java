package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ZebraFollowParentGoal extends FollowParentGoal {

	public ZebraFollowParentGoal(Zebra zebra, double speed) {
		super(zebra, speed);
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
}