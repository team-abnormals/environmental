package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DeerFrolicGoal extends Goal {
	private final AbstractDeer deer;
	private int jumpCooldown;
	private int frolicCooldown;
	private boolean jumping = false;

	public DeerFrolicGoal(AbstractDeer deerIn) {
		this.deer = deerIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return this.deer.isSpreadingFlowers();
	}

	@Override
	public boolean canContinueToUse() {
		return this.deer.isSpreadingFlowers() || !this.deer.getNavigation().isDone();
	}

	@Override
	public void start() {
		this.resetJumpCooldown();
		this.frolicCooldown = 0;
	}

	@Override
	public void tick() {
		if (this.jumpCooldown > 0)
			--this.jumpCooldown;

		if (this.jumping && this.deer.isOnGround()) {
			this.deer.spawnFlower();
			this.jumpCooldown = Math.max(this.adjustedTickDelay(2), this.jumpCooldown);
			this.jumping = false;
		} else if (this.deer.getNavigation().isDone()) {
			if (this.frolicCooldown > 0) {
				--this.frolicCooldown;
			} else if (this.deer.isSpreadingFlowers()) {
				Vec3 vec3 = DefaultRandomPos.getPos(this.deer, 15, 7);
				if (vec3 != null) {
					this.deer.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.6D);
					if (this.deer.getRandom().nextInt(8) == 0)
						this.frolicCooldown = this.adjustedTickDelay(20 + this.deer.getRandom().nextInt(20));
				}
			}
		} else if (this.jumpCooldown <= 0 && this.deer.isOnGround()) {
			Path path = this.deer.getNavigation().getPath();
			Vec3 vec3 = path.getNextEntityPos(this.deer);
			Vec3 vec31 = vec3.subtract(this.deer.position());
			Vec3 vec32 = vec31.normalize();

			this.deer.level.broadcastEntityEvent(this.deer, (byte) 4);
			this.deer.setJumping(true);
			this.deer.setDeltaMovement(this.deer.getDeltaMovement().add(vec32.x * 0.32D, 0.5D, vec32.z * 0.32D));
			this.resetJumpCooldown();
			this.jumping = true;
		}
	}

	private void resetJumpCooldown() {
		this.jumpCooldown = this.adjustedTickDelay(16);
	}
}