package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class TapirHuntFloraGoal extends Goal {
	private final Tapir tapir;

	private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

	public TapirHuntFloraGoal(Tapir tapir) {
		this.tapir = tapir;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.tapir.hasFloraPos() && this.tapir.getTrackingTime() > 0;
	}

	@Override
	public void start() {
		this.moveToTarget();
		this.tapir.setRunning(true);
	}

	@Override
	public void stop() {
		this.tapir.resetLove();
		this.tapir.setRunning(false);
		this.tapir.stopTracking();
	}

	@Override
	public void tick() {
		BlockPos florapos = this.tapir.getFloraPos();

		if (florapos.closerThan(this.tapir.blockPosition(), 1.0D)) {
			// this.tapir.stopTracking();
		} else {
			BlockPos pos = this.tapir.getFloraPos();
			this.moveToTarget();
			this.tapir.getLookControl().setLookAt(pos.getX(), pos.getY(), pos.getZ(), 10.0F, this.tapir.getMaxHeadXRot());
		}

		if (florapos.closerThan(this.tapir.blockPosition(), 3.0D)) {
			if (this.tapir.getAge() == 0 && this.tapir.getInLoveTime() <= 0) {
				Tapir partner = this.findFreePartner();

				if (partner != null) {
					this.tapir.setInLove(this.tapir.getFeeder());
				}
			}
		}
	}

	@Nullable
	private Tapir findFreePartner() {
		List<? extends Tapir> list = this.tapir.level.getNearbyEntities(Tapir.class, PARTNER_TARGETING, this.tapir, this.tapir.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Tapir partner = null;

		for(Tapir entity : list) {
			double d1 = this.tapir.distanceToSqr(entity);
			if (entity.isHuntingForFlora() && entity.getAge() == 0 && this.tapir.getFloraState().is(entity.getFloraState().getBlock()) && d1 < d0) {
				d0 = d1;
				partner = entity;
			}
		}

		return partner;
	}

	private void moveToTarget() {
		BlockPos pos = this.tapir.getFloraPos();
		this.tapir.getNavigation().moveTo((double) ((float) pos.getX()) + 0.5D, pos.getY() + 1, (double) ((float) pos.getZ()) + 0.5D, 1.0D);
	}
}