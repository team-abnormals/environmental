package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class TapirSniffForFloraGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();

    private final Tapir tapir;
    private final PathNavigation pathNav;
    private boolean foundTarget;
    private int walkWait;
    private int sniffTime;
    private int animTime;

    public TapirSniffForFloraGoal(Tapir tapir) {
        this.tapir = tapir;
        this.pathNav = tapir.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.tapir.hasFloraState() && !this.tapir.hasFloraPos();
    }

    @Override
    public boolean canContinueToUse() {
        return (this.sniffTime > 0 || this.animTime > 0) && super.canContinueToUse();
    }

    @Override
    public void start() {
        this.setWalkWait();
        this.sniffTime = this.adjustedTickDelay(140 + this.tapir.getRandom().nextInt(80));
        this.foundTarget = false;
        this.pathNav.stop();
        this.tapir.setSniffing(true);
    }

    @Override
    public void stop() {
        if (!this.foundTarget)
            this.tapir.setFloraState(null);
        this.tapir.setSniffing(false);
    }

    @Override
    public void tick() {
        if (this.animTime > 0) {
            this.animTime--;
            return;
        }

        if (this.sniffTime > 1) {
            this.sniffTime--;
        }

        if (this.pathNav.isDone()) {
            if (this.sniffTime <= 1) {
                this.sniffTime = 0;

                BlockPos targetPos = this.findNearestTarget();
                if (targetPos != null) {
                    this.tapir.setFloraPos(targetPos);
                    this.tapir.setTrackingTime(1200);
                    this.foundTarget = true;
                } else {
                    this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 5);
                    this.tapir.playSound(SoundEvents.PIG_DEATH);
                    this.tapir.setSniffing(false);
                    this.animTime = 20;
                }
            } else if (this.walkWait-- <= 0) {
                Vec3 vec3 = LandRandomPos.getPos(this.tapir, 4, 2);
                if (vec3 != null) {
                    this.pathNav.moveTo(vec3.x(), vec3.y(), vec3.z(), 0.6D);
                    this.setWalkWait();
                }
            }
        }
    }

    private void setWalkWait() {
        this.walkWait = this.adjustedTickDelay(10 + this.tapir.getRandom().nextInt(20));
    }

    private BlockPos findNearestTarget() {
        List<Tapir> list = this.tapir.level.getNearbyEntities(Tapir.class, PARTNER_TARGETING, this.tapir, this.tapir.getBoundingBox().inflate(8.0D));
        Tapir partner = null;
        double d0 = Double.MAX_VALUE;

        for (Tapir entity : list) {
            double d1 = this.tapir.distanceToSqr(entity);
            if (entity.getFloraPos() != null && this.tapir.getFloraState().is(entity.getFloraState().getBlock()) && d1 < d0) {
                d0 = d1;
                partner = entity;
            }
        }

        if (partner != null) {
            return partner.getFloraPos();
        }

        BlockPos blockpos = this.tapir.blockPosition();
        for (int height = 0; height <= 5; height++) {
            for (int width = 0; width <= 96; width++) {
                for (int i = -width; i <= width; i++) {
                    for (int j = -height; j <= height; j++) {
                        for (int k = -width; k <= width; k++) {
                            if ((Math.abs(i) == width && Math.abs(j) == height) || (Math.abs(k) == width && Math.abs(j) == height)) {
                                BlockPos position = blockpos.offset(i, j, k);
                                if (this.tapir.level.getBlockState(position).is(this.tapir.getFloraState().getBlock())) {
                                    return position;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}