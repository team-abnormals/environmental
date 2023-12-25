package com.teamabnormals.environmental.common.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class HerdLandWanderGoal extends HerdWanderGoal {
	protected final float probability;

	public HerdLandWanderGoal(PathfinderMob mob, double speed) {
		this(mob, speed, 0.001F);
	}

	public HerdLandWanderGoal(PathfinderMob mob, double speed, float probability) {
		super(mob, speed);
		this.probability = probability;
	}

	@Override
	protected Vec3 getNearPosition() {
		if (this.mob.isInWaterOrBubble()) {
			Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
			return vec3 == null ? DefaultRandomPos.getPos(this.mob, 10, 7) : vec3;
		} else {
			return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 6, 4) : DefaultRandomPos.getPos(this.mob, 6, 4);
		}
	}

	@Override
	protected Vec3 getFarPosition() {
		if (this.mob.isInWaterOrBubble()) {
			Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
			return vec3 == null ? DefaultRandomPos.getPos(this.mob, 10, 7) : vec3;
		} else {
			return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : DefaultRandomPos.getPos(this.mob, 10, 7);
		}
	}
}