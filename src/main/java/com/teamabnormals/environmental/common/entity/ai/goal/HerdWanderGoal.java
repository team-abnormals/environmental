package com.teamabnormals.environmental.common.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class HerdWanderGoal extends RandomStrollGoal {
	protected Predicate<PathfinderMob> herdPredicate = this.defaultHerdPredicate();
	protected double speedModifierFar;
	protected int preferredHerdSize;
	protected boolean isHerdFar;

	public HerdWanderGoal(PathfinderMob mob, double speed, double speedFar, int herdSize) {
		super(mob, speed);
		this.speedModifierFar = speedFar;
		this.preferredHerdSize = herdSize;
	}

	public HerdWanderGoal(PathfinderMob mob, double speed, double speedFar, int interval, int herdSize) {
		super(mob, speed, interval);
		this.speedModifierFar = speedFar;
		this.preferredHerdSize = herdSize;
	}

	public HerdWanderGoal(PathfinderMob mob, double speed, double speedFar, int interval, boolean checkNoActionTime, int herdSize) {
		super(mob, speed, interval, checkNoActionTime);
		this.speedModifierFar = speedFar;
		this.preferredHerdSize = herdSize;
	}

	@Nullable
	@Override
	protected Vec3 getPosition() {
		List<PathfinderMob> herd = this.mob.level.getEntitiesOfClass(PathfinderMob.class, this.mob.getBoundingBox().inflate(32.0D, 8.0D, 32.0D), this.herdPredicate.and(entity -> entity != this.mob));
		List<PathfinderMob> closeherd = herd.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this.mob))).limit(this.preferredHerdSize - 1).toList();

		if (!closeherd.isEmpty()) {
			double frienddist = this.mob.distanceToSqr(closeherd.get(0));
			double herdx = 0.0D;
			double herdy = 0.0D;
			double herdz = 0.0D;
			int herdsize = 0;

			for (PathfinderMob entity : closeherd) {
				if (this.mob.distanceToSqr(entity) > 144.0D && herdsize >= this.preferredHerdSize - 2)
					break;

				herdx += entity.getX();
				herdy += entity.getY();
				herdz += entity.getZ();
				herdsize++;
			}

			Vec3 herdcenter = new Vec3(herdx / herdsize, herdy / herdsize, herdz / herdsize);

			if (herdsize > 0) {
				double herddist = this.mob.distanceToSqr(herdcenter);
				if (frienddist > 9.0D || herddist > 64.0D) {
					int maxdist = Math.min(Math.max(Mth.floor(Math.sqrt(herddist)), 3), 16);
					int mindist = Math.max(maxdist - 4, 1);
					for (int i = 0; i < 8; i++) {
						Vec3 pos = DefaultRandomPos.getPosTowards(this.mob, maxdist, mindist, herdcenter, Math.PI / 2.0D);
						if (pos != null) {
							this.isHerdFar = herddist > 196.0D;
							return pos;
						}
					}
				}
			}
		}

		return this.randomPosition();
	}

	@Override
	public void start() {
		this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.isHerdFar ? this.speedModifierFar : this.speedModifier);
	}

	public HerdWanderGoal setHerdPredicate(Predicate<PathfinderMob> predicate) {
		this.herdPredicate = predicate;
		return this;
	}

	protected Predicate<PathfinderMob> defaultHerdPredicate() {
		return entity -> entity.getClass().isAssignableFrom(this.mob.getClass()) || this.mob.getClass().isAssignableFrom(entity.getClass());
	}

	protected Vec3 randomPosition() {
		return DefaultRandomPos.getPos(this.mob, 10, 7);
	}
}