package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class ZebraFleeGoal extends Goal {
	private final Zebra zebra;
	private final PathNavigation pathNav;
	private final double speedModifier;

	private boolean trigger;
	private boolean running;
	private boolean isStuck;
	private int nextStartTicks;
	private int fleeTime;
	private int stuckTime;
	private float fleeDirection;

	public ZebraFleeGoal(Zebra zebra, double speed) {
		this.zebra = zebra;
		this.pathNav = zebra.getNavigation();
		this.speedModifier = speed;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (this.trigger)
			return true;
		if (--this.nextStartTicks > 0)
			return false;

		if (!this.zebra.isTamed() && this.zebra.getTarget() == null) {
			List<Zebra> zebras = this.zebra.getLevel().getEntitiesOfClass(Zebra.class, this.zebra.getBoundingBox().inflate(10.0D, 4.0D, 10.0D), zebra1 -> zebra1 != this.zebra && zebra1.isFleeing())
					.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this.zebra))).toList();
			if (!zebras.isEmpty()) {
				Zebra zebra1 = zebras.get(0);
				this.fleeTime = zebra1.getFleeGoal().fleeTime + this.adjustedTickDelay(this.zebra.getRandom().nextInt(30) - 30);
				this.fleeDirection = zebra1.getFleeGoal().fleeDirection;
				this.nextStartTicks = this.adjustedTickDelay(60);
				return true;
			}
		}

		this.nextStartTicks = this.adjustedTickDelay(this.zebra.getRandom().nextInt(10) + 10);
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return --this.fleeTime >= 0;
	}

	@Override
	public void start() {
		this.running = true;
		this.trigger = false;
		this.isStuck = false;
		this.stuckTime = 0;

		this.pathNav.stop();
		this.zebra.setEating(false);
		this.zebra.setStanding(false);
		this.zebra.playAmbientSound();
	}

	@Override
	public void stop() {
		this.running = false;
	}

	@Override
	public void tick() {
		if (this.pathNav.isDone() || this.pathNav.getTargetPos().closerToCenterThan(this.zebra.position(), 5.0D)) {
			float f = this.fleeDirection * Mth.DEG_TO_RAD;
			Vec3 vec3 = this.zebra.position().add(new Vec3(Math.sin(f) * 32.0D, 0.0D, Math.cos(f) * 32.0D));
			for (int i = 0; i < 3; i++) {
				Vec3 vec31 = DefaultRandomPos.getPosTowards(this.zebra, 16, 7, vec3, Math.PI / 6.0D);
				if (vec31 != null) {
					double d0 = this.speedModifier;
					double d1 = this.zebra.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
					if (d1 < 0.225D && d1 != 0.0D)
						d0 = 0.4D / d1;
					this.pathNav.moveTo(vec31.x, vec31.y, vec31.z, d0);
					this.stuckTime = 0;
					if (this.isStuck) {
						this.isStuck = false;
						this.zebra.playAmbientSound();
					}
					return;
				}
			}

			if (++this.stuckTime > this.adjustedTickDelay(20)) {
				List<Zebra> zebras = this.zebra.getLevel().getEntitiesOfClass(Zebra.class, this.zebra.getBoundingBox().inflate(10.0D, 4.0D, 10.0D), zebra1 -> zebra1 != this.zebra && zebra1.isFleeing() && zebra1.getFleeGoal().fleeDirection != this.fleeDirection && !zebra1.getFleeGoal().isStuck)
						.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this.zebra))).toList();

				if (!zebras.isEmpty()) {
					this.fleeDirection = zebras.get(0).getFleeGoal().fleeDirection;
				} else {
					int i = this.zebra.getRandom().nextInt(2) - 1;
					if (i >= 0)
						i++;

					this.fleeDirection = Mth.wrapDegrees(this.fleeDirection + i * 60.0F);
				}

				this.stuckTime = 0;
				this.isStuck = true;
			}
		}
	}

	public boolean running() {
		return this.running;
	}

	public void trigger(int fleeTime, float fleeDirection) {
		this.trigger = true;
		this.fleeTime = fleeTime;
		this.fleeDirection = fleeDirection;
	}
}