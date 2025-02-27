package com.teamabnormals.environmental.common.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class DuckAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
	private final TargetingConditions avoidEntityTargeting;

	public DuckAvoidEntityGoal(PathfinderMob mob, Class<T> classToAvoid, float p_25035_, double p_25036_, double p_25037_, Predicate<LivingEntity> p_25038_) {
		super(mob, classToAvoid, p_25035_, p_25036_, p_25037_, p_25038_);
		this.avoidEntityTargeting = TargetingConditions.forCombat().range(p_25035_).selector(p_25038_);
	}

	@Override
	public boolean canUse() {
		this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), p_148078_ -> true), this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
		if (this.toAvoid == null) {
			return false;
		} else {
			Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 4, 2, this.toAvoid.position());
			if (vec3 == null) {
				return false;
			} else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
				return false;
			} else {
				this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
				return this.path != null;
			}
		}
	}
}