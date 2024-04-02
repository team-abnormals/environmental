package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class TapirHuntFloraGoal extends Goal {
	private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

	private final Tapir tapir;
	private Tapir partner;
	private int grazeTime;
	private int romanticDinnerTime;
	private int delayCounter;

	public TapirHuntFloraGoal(Tapir tapir) {
		this.tapir = tapir;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.tapir.getTrackingTime() > 0 && this.tapir.hasFloraPos();
	}

	@Override
	public void start() {
		this.moveTo(this.tapir.getFloraPos());
		this.grazeTime = this.adjustedTickDelay(200 + this.tapir.getRandom().nextInt(100));
		this.partner = null;
		this.delayCounter = 0;
	}

	@Override
	public void stop() {
		this.tapir.setRunning(false);
		this.tapir.setGrazing(false);
		this.tapir.stopTracking();
	}

	@Override
	public void tick() {
		BlockPos florapos = this.tapir.getFloraPos();
		BlockState florastate = this.tapir.level.getBlockState(florapos);

		if (!florastate.getBlock().getCloneItemStack(this.tapir.level, florapos, florastate).is(this.tapir.getFloraItem())) {
			if (this.tapir.isGrazing()) {
				this.tapir.stopTracking();
				return;
			} else if (florapos.closerThan(this.tapir.blockPosition(), 5.0D)) {
				this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 5);
				this.tapir.playSound(SoundEvents.PIG_DEATH);
				this.tapir.stopTracking();
				return;
			}
		}

		if (florapos.closerThan(this.tapir.blockPosition(), 1.5D)) {
			this.tapir.getLookControl().setLookAt(florapos.getX() + 0.5D, this.tapir.getEyeY(), florapos.getZ() + 0.5D, 10.0F, this.tapir.getMaxHeadXRot());

			if (!this.tapir.isGrazing()) {
				this.tapir.setRunning(false);
				this.tapir.setGrazing(true);
				this.tapir.getNavigation().stop();
			} else {
				if (--this.grazeTime <= 0) {
					this.tapir.level.destroyBlock(florapos, false);
					this.tapir.stopTracking();

					Vec3 vec3 = DefaultRandomPos.getPos(this.tapir, 10, 7);
					if (vec3 != null)
						this.moveTo(new BlockPos(vec3));
				}

				if (this.tapir.getAge() == 0) {
					if (this.partner == null) {
						Tapir partner = this.findClosestPartner();
						if (partner != null) {
							this.partner = partner;
							this.romanticDinnerTime = this.adjustedTickDelay(80);

							if (this.grazeTime < this.adjustedTickDelay(200))
								this.grazeTime = this.adjustedTickDelay(200);

							if (!this.tapir.getFloraPos().equals(partner.getFloraPos()))
								this.tapir.setFloraPos(partner.getFloraPos());
						}
					} else if (this.canBreedWith(this.partner) && this.tapir.getFloraPos().equals(this.partner.getFloraPos())) {
						if (--this.romanticDinnerTime <= 0)
							this.tapir.spawnChildFromBreeding((ServerLevel) this.tapir.level, this.partner);
					} else {
						this.partner = null;
					}
				}
			}
		} else {
			if (--this.delayCounter <= 0) {
				this.delayCounter = this.adjustedTickDelay(5);
				this.moveTo(florapos);
				this.tapir.setRunning(true);
			}

			this.tapir.getLookControl().setLookAt(florapos.getX() + 0.5D, florapos.getY() + 0.5D, florapos.getZ() + 0.5D, 10.0F, this.tapir.getMaxHeadXRot());
			this.tapir.setGrazing(false);
		}
	}

	@Nullable
	private Tapir findClosestPartner() {
		List<? extends Tapir> list = this.tapir.level.getNearbyEntities(Tapir.class, PARTNER_TARGETING, this.tapir, this.tapir.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Tapir partner = null;

		for(Tapir entity : list) {
			double d1 = this.tapir.distanceToSqr(entity);
			if (this.canBreedWith(entity) && d1 < d0) {
				d0 = d1;
				partner = entity;
			}
		}

		return partner;
	}

	private boolean canBreedWith(Tapir partner) {
		return partner.isGrazing() && partner.getAge() == 0 && this.tapir.getFloraItem() == partner.getFloraItem();
	}

	private void moveTo(BlockPos pos) {
		this.tapir.getNavigation().moveTo(this.tapir.getNavigation().createPath(pos.getX(), pos.getY(), pos.getZ(), 0), 1.1D);
	}
}