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
	protected Predicate<PathfinderMob> herdPredicate;

	public HerdWanderGoal(PathfinderMob mob, double speed) {
		super(mob, speed);
		this.herdPredicate = this.defaultHerdPredicate();
	}

	public HerdWanderGoal(PathfinderMob mob, double speed, int interval) {
		super(mob, speed, interval);
		this.herdPredicate = this.defaultHerdPredicate();
	}

	public HerdWanderGoal(PathfinderMob mob, double speed, int interval, boolean checkNoActionTime) {
		super(mob, speed, interval, checkNoActionTime);
		this.herdPredicate = this.defaultHerdPredicate();
	}

	@Nullable
	@Override
	protected Vec3 getPosition() {
		List<PathfinderMob> herd = this.mob.level.getEntitiesOfClass(PathfinderMob.class, this.mob.getBoundingBox().inflate(16.0D, 8.0D, 16.0D), this.herdPredicate.and(entity -> entity != this.mob));
		List<PathfinderMob> closeherd = herd.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this.mob))).limit(8).toList();

		double frienddist = Double.MAX_VALUE;
		double herdx = 0.0D;
		double herdy = 0.0D;
		double herdz = 0.0D;
		int herdsize = 0;

		for (PathfinderMob entity : closeherd) {
			double dist = this.mob.distanceToSqr(entity);
			if (dist < frienddist)
				frienddist = dist;

			herdx += entity.getX();
			herdy += entity.getY();
			herdz += entity.getZ();
			herdsize++;
		}

		Vec3 herdcenter = new Vec3(herdx / herdsize, herdy / herdsize, herdz / herdsize);

		if (herdsize > 0) {
			double herddist = this.mob.distanceToSqr(herdcenter);
			if (frienddist > 9.0D || herddist > 64.0D) {
				int maxdist = Math.min(Math.max(Mth.floor(Math.sqrt(herddist)), 3), 10);
				int mindist = Mth.ceil(maxdist / 2.0D);
				for (int i = 0; i < 8; i++) {
					Vec3 pos = DefaultRandomPos.getPosTowards(this.mob, maxdist, mindist, herdcenter, Math.PI / 2.0D);
					if (pos != null)
						return pos;
				}
			}
		}

		Vec3 farpos = this.getFarPosition();
		if (farpos != null) {
			List<PathfinderMob> farherd = this.mob.level.getEntitiesOfClass(PathfinderMob.class, this.mob.getBoundingBox().move(farpos.subtract(this.mob.position())).inflate(4.0D, 4.0D, 4.0D), this.herdPredicate.and(entity -> entity != this.mob && entity.distanceToSqr(this.mob) <= 16.0D));
			if (!farherd.isEmpty())
				return farpos;
		}

		if (herdsize > 0) {
			return this.getNearPosition();
		} else {
			return farpos;
		}
	}

	public HerdWanderGoal setHerdPredicate(Predicate<PathfinderMob> predicate) {
		this.herdPredicate = predicate;
		return this;
	}

	protected Predicate<PathfinderMob> defaultHerdPredicate() {
		return entity -> entity.getClass() == this.mob.getClass();
	}

	protected Vec3 getNearPosition() {
		return DefaultRandomPos.getPos(this.mob, 6, 4);
	}

	protected Vec3 getFarPosition() {
		return DefaultRandomPos.getPos(this.mob, 10, 7);
	}
}