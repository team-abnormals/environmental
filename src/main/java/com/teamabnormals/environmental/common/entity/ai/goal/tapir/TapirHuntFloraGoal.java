package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class TapirHuntFloraGoal extends Goal {
	private final Tapir tapir;
	private int bites;
	private int munchTime;
	private int grazeAnimWait;

	private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

	public TapirHuntFloraGoal(Tapir tapir) {
		this.tapir = tapir;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.tapir.isTrackingFlora();
	}

	@Override
	public void start() {
		this.moveTo(this.tapir.getFloraPos());
		this.tapir.setRunning(true);
		this.munchTime = 0;
		this.grazeAnimWait = 0;
		this.bites = 3 + this.tapir.getRandom().nextInt(2);
	}

	@Override
	public void stop() {
		this.tapir.setRunning(false);
		this.tapir.setGrazingState((byte) 0);
		this.tapir.stopTracking();
	}

	@Override
	public void tick() {
		BlockPos florapos = this.tapir.getFloraPos();
		boolean floraexists = this.tapir.level.getBlockState(florapos).is(this.tapir.getFloraBlock());

		if (florapos.closerThan(this.tapir.blockPosition(), 5.0D) && !this.tapir.isGrazing() && !floraexists) {
			this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 5);
			this.tapir.playSound(SoundEvents.PIG_DEATH);
			this.tapir.stopTracking();
			return;
		}

		if (florapos.closerThan(this.tapir.blockPosition(), 1.25D)) {
			this.tapir.getLookControl().setLookAt(florapos.getX(), this.tapir.getEyeY(), florapos.getZ(), 10.0F, this.tapir.getMaxHeadXRot());

			if (!this.tapir.isGrazing()) {
				this.tapir.setRunning(false);
				this.tapir.setGrazingState((byte) 1);

				this.munchTime = 0;
				this.grazeAnimWait = 0;

				this.tapir.getNavigation().stop();
			} else {
				if (this.munchTime > 0) {
					if (--this.munchTime <= 0) {
						this.tapir.setGrazingState((byte) 2);

						if (this.bites <= 0) {
							this.tapir.level.destroyBlock(florapos, false);
						}
					}
				} else if (--this.grazeAnimWait <= 0) {
					if (this.bites > 0 && floraexists) {
						--this.bites;
						this.tapir.setGrazingState((byte) 1);
						this.munchTime = this.adjustedTickDelay(20);
						this.grazeAnimWait = this.adjustedTickDelay(80 + this.tapir.getRandom().nextInt(20));
					} else {
						this.tapir.stopTracking();

						Vec3 vec3 = DefaultRandomPos.getPos(this.tapir, 10, 7);
						if (vec3 != null)
							this.moveTo(new BlockPos(vec3));
					}
				}

				if (this.tapir.getAge() == 0 && !this.tapir.isInLove()) {
					Tapir partner = this.findFreePartner();

					if (partner != null) {
						this.tapir.setInLove(this.tapir.getFeeder());
						if (!partner.isInLove())
							partner.setInLove(this.tapir.getFeeder());
					}
				}
			}
		} else {
			this.moveTo(florapos);
			this.tapir.getLookControl().setLookAt(florapos.getX(), florapos.getY(), florapos.getZ(), 10.0F, this.tapir.getMaxHeadXRot());

			this.tapir.setRunning(true);
			this.tapir.setGrazingState((byte) 0);
		}
	}

	@Nullable
	private Tapir findFreePartner() {
		List<? extends Tapir> list = this.tapir.level.getNearbyEntities(Tapir.class, PARTNER_TARGETING, this.tapir, this.tapir.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Tapir partner = null;

		for(Tapir entity : list) {
			double d1 = this.tapir.distanceToSqr(entity);
			if (entity.isGrazing() && entity.getAge() == 0 && this.tapir.getFloraBlock() == entity.getFloraBlock() && d1 < d0) {
				d0 = d1;
				partner = entity;
			}
		}

		return partner;
	}

	private void moveTo(BlockPos pos) {
		this.tapir.getNavigation().moveTo(this.tapir.getNavigation().createPath(pos.getX(), pos.getY(), pos.getZ(), 0), 1.1D);
	}
}