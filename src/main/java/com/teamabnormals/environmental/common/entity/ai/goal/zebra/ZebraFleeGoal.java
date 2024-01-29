package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.util.Mth;
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
	private int scareOthersWait;
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
		this.scareOthersWait = this.adjustedTickDelay(10);
		this.pathNav.stop();
	}

	@Override
	public void stop() {
		this.running = false;
	}

	@Override
	public void tick() {
		if (this.scareOthersWait > 0) {
			this.scareOthersWait--;
			if (this.scareOthersWait == 0) {
				this.zebra.scareOthers(this.fleeTime, this.fleeDirection);
				this.zebra.playAmbientSound();
			}
		}

		if (this.pathNav.isDone() || this.pathNav.getTargetPos().closerToCenterThan(this.zebra.position(), 5.0D)) {
			float f = this.fleeDirection * Mth.DEG_TO_RAD;
			Vec3 vec3 = this.zebra.position().add(new Vec3(Math.sin(f) * 32.0D, 0.0D, Math.cos(f) * 32.0D));
			for (int i = 0; i < 3; i++) {
				Vec3 vec31 = DefaultRandomPos.getPosTowards(this.zebra, 16, 7, vec3, Math.PI / 6.0D);
				if (vec31 != null) {
					this.pathNav.moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
					return;
				}
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