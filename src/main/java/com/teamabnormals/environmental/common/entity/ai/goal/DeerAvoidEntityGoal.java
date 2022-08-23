package com.teamabnormals.environmental.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.core.other.EnvironmentalTags;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DeerAvoidEntityGoal extends Goal {
	private final Deer deer;
	private final Level level;
	private final PathNavigation pathNav;
	@Nullable
	private LivingEntity toAvoid;
	private boolean running;
	private Vec3 avoidEntityOldPos;
	private final TargetingConditions avoidEntityTargeting;

	public DeerAvoidEntityGoal(Deer deerIn) {
		this.deer = deerIn;
		this.level = deerIn.getLevel();
		this.pathNav = deerIn.getNavigation();
		Predicate<LivingEntity> avoidPredicate = (entity) -> {
			if (!entity.getType().is(EnvironmentalTags.EntityTypes.SCARES_DEER)) {
				return false;
			} else if (entity.isDiscrete() || !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) {
				return false;
			} else if (this.deer.isTrusting() && entity instanceof TamableAnimal && ((TamableAnimal) entity).isTame()) {
				return false;
			} else if (entity.getType().is(EnvironmentalTags.EntityTypes.ALWAYS_SCARES_DEER)) {
				return true;
			} else {
				return !this.deer.isTrusting();
			}
		};
		this.avoidEntityTargeting = TargetingConditions.forCombat().range(10.0D).selector(avoidPredicate);
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		this.toAvoid = this.getNearestScaryEntity();
		return this.toAvoid != null;
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity entity = this.getNearestScaryEntity();
		if (entity != null && entity != this.toAvoid) {
			this.toAvoid = this.getNearestScaryEntity();
			return true;
		} else if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.toAvoid)) {
			return false;
		} else if (!this.toAvoid.isAlive()) {
			return false;
		} else {
			return this.deer.distanceToSqr(this.toAvoid) <= 100.0F;
		}
	}

	@Override
	public void start() {
		this.running = false;
		this.avoidEntityOldPos = this.toAvoid.position();
	}

	@Override
	public void stop() {
		this.toAvoid = null;
	}

	@Override
	public void tick() {
		if (this.pathNav.isDone())
			this.flee();

		if (!this.running) {
			boolean flag = this.toAvoid.distanceToSqr(this.avoidEntityOldPos) >= 0.0625D;
			if ((flag && this.deer.distanceToSqr(this.toAvoid) <= 49.0F) || (!flag && this.deer.distanceToSqr(this.toAvoid) <= 9.0F)) {
				this.running = true;
				this.deer.getNavigation().setSpeedModifier(1.75F);
			}

			this.avoidEntityOldPos = this.toAvoid.position();
		}
	}

	private void flee() {
		Vec3 vec3 = DefaultRandomPos.getPosAway(this.deer, 16, 7, this.toAvoid.position());
		if (vec3 != null && this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) >= this.toAvoid.distanceToSqr(this.deer)) {
			Path path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
			this.pathNav.moveTo(path, this.running ? 1.75F : 1.2D);
		}
	}

	private LivingEntity getNearestScaryEntity() {
		LivingEntity entity = this.level.getNearestEntity(this.level.getEntitiesOfClass(LivingEntity.class, this.deer.getBoundingBox().inflate(10.0D, 4.0D, 10.0D), (p_148124_) -> {
			return true;
		}), this.avoidEntityTargeting, this.deer, this.deer.getX(), this.deer.getY(), this.deer.getZ());
		return entity;
	}
}