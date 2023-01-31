package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DeerAvoidEntityGoal extends Goal {
	private final AbstractDeer deer;
	private final PathNavigation pathNav;
	@Nullable
	private LivingEntity toAvoid;
	private boolean running;

	public DeerAvoidEntityGoal(AbstractDeer deerIn) {
		this.deer = deerIn;
		this.pathNav = deerIn.getNavigation();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		this.toAvoid = this.deer.getNearestScaryEntity();
		return this.toAvoid != null;
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity entity = this.deer.getNearestScaryEntity();
		if (entity != null && entity != this.toAvoid) {
			this.toAvoid = entity;
			return true;
		} else if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.toAvoid)) {
			return false;
		} else if (!this.toAvoid.isAlive()) {
			return false;
		} else {
			return this.deer.distanceToSqr(this.toAvoid) <= 256.0F;
		}
	}

	@Override
	public void start() {
		this.running = false;
		this.flee();
	}

	@Override
	public void stop() {
		this.toAvoid = null;
	}

	@Override
	public void tick() {
		if (this.pathNav.isDone())
			this.flee();

		if (!this.running && this.deer.distanceToSqr(this.toAvoid) <= 36.0F) {
			this.running = true;
			this.deer.getNavigation().setSpeedModifier(1.8F);
		}
	}

	private void flee() {
		Vec3 vec3 = DefaultRandomPos.getPosAway(this.deer, 12, 6, this.toAvoid.position());
		if (vec3 != null && this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) >= this.toAvoid.distanceToSqr(this.deer)) {
			Path path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
			this.pathNav.moveTo(path, this.running ? 1.8F : 1.2D);
		}
	}
}