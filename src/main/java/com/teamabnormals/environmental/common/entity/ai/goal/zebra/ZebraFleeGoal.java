package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ZebraFleeGoal extends Goal {
	private final Zebra zebra;
	private final PathNavigation pathNav;
	private final double speedModifier;

	private boolean trigger;
	private boolean running;
	private int fleeTime;
	private int stuckTime;
	private int alertOthersWait;
	private int announceDirChangeWait;
	private float fleeDirection;

	public ZebraFleeGoal(Zebra zebra, double speed) {
		this.zebra = zebra;
		this.pathNav = zebra.getNavigation();
		this.speedModifier = speed;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return this.trigger;
	}

	@Override
	public boolean canContinueToUse() {
		return --this.fleeTime >= 0;
	}

	@Override
	public void start() {
		this.running = true;
		this.trigger = false;
		this.alertOthersWait = this.adjustedTickDelay(10);
		this.announceDirChangeWait = 0;
		this.stuckTime = 0;
		this.pathNav.stop();
		this.zebra.setEating(false);
		this.zebra.setStanding(false);
	}

	@Override
	public void stop() {
		this.running = false;
	}

	@Override
	public void tick() {
		if (this.alertOthersWait > 0) {
			this.alertOthersWait--;
			if (this.alertOthersWait == 0) {
				this.zebra.alertOthers(this.fleeTime, this.fleeDirection);
				this.zebra.playAmbientSound();
			}
		}

		if (this.announceDirChangeWait > 0) {
			this.announceDirChangeWait--;
			if (this.announceDirChangeWait == 0) {
				this.zebra.announceDirectionChange(this.fleeDirection);
				this.zebra.playAmbientSound();
			}
		}

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
					return;
				}
			}
			if (++this.stuckTime > this.adjustedTickDelay(30)) {
				int i = this.zebra.getRandom().nextInt(4) - 2;
				if (i >= 0)
					i++;

				this.fleeDirection = Mth.wrapDegrees(this.fleeDirection + i * 35.0F);
				this.zebra.announceDirectionChange(this.fleeDirection);
				this.zebra.playAmbientSound();
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

	public void changeDirection(float fleeDirection) {
		if (fleeDirection != this.fleeDirection) {
			this.fleeDirection = fleeDirection;
			this.announceDirChangeWait = this.adjustedTickDelay(10);
			this.stuckTime = 0;
		}
	}
}