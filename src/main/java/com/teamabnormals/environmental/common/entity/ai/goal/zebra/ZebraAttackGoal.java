package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.pathfinder.Path;

public class ZebraAttackGoal extends MeleeAttackGoal {

	public ZebraAttackGoal(Zebra zebra, double speed) {
		super(zebra, speed, true);
	}

	@Override
	public boolean canUse() {
		return super.canUse() && !this.mob.hasPassenger(this.mob.getTarget());
	}

	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse() && !this.mob.hasPassenger(this.mob.getTarget());
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
			if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
				this.pathedTargetX = livingentity.getX();
				this.pathedTargetY = livingentity.getY();
				this.pathedTargetZ = livingentity.getZ();
				this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
				if (d0 > 1024.0D) {
					this.ticksUntilNextPathRecalculation += 10;
				} else if (d0 > 256.0D) {
					this.ticksUntilNextPathRecalculation += 5;
				}

				Path path = this.mob.getNavigation().createPath(livingentity, 0);
				if (!this.mob.getNavigation().moveTo(path, this.speedModifier)) {
					this.ticksUntilNextPathRecalculation += 15;
				}

				this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
			}

			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
			this.checkAndPerformAttack(livingentity, d0);
		}
	}

	@Override
	protected void checkAndPerformAttack(LivingEntity target, double distance) {
		if (this.isTimeToAttack() && this.mob.getBoundingBox().inflate(1.0F).intersects(target.getBoundingBox())) {
			this.resetAttackCooldown();
			double x = target.getX() - this.mob.getX();
			double y = target.getZ() - this.mob.getZ();
			float f = (float)(Mth.atan2(y, x) * Mth.RAD_TO_DEG) - 90.0F;
			this.mob.setYRot(f);
			this.mob.yRotO = this.mob.getYRot();
			this.mob.yBodyRot = this.mob.getYRot();
			this.mob.yHeadRot = this.mob.yBodyRot;
			((Zebra) this.mob).kick(false);
			((Zebra) this.mob).playKickingSound();
		}
	}
}