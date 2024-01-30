package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class TapirSniffForFloraGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(12.0D).ignoreLineOfSight();

    private final Tapir tapir;
    private final PathNavigation pathNav;
    private int walkWait;
    private int sniffTime;
    private int stopTime;
    private BlockPos origin;
    private BlockPos foundPos;

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
        return (this.sniffTime > 0 || this.stopTime > 0) && this.tapir.hasFloraState();
    }

    @Override
    public void start() {
        this.setWalkWait();
        this.sniffTime = this.adjustedTickDelay(300);
        this.stopTime = 0;
        this.foundPos = null;
        this.origin = this.tapir.blockPosition();
        this.tapir.setSniffing(true);
        this.pathNav.stop();
    }

    @Override
    public void stop() {
        if (this.foundPos != null && this.stopTime == 0) {
            int time = (int) Math.sqrt(this.tapir.distanceToSqr(Vec3.atCenterOf(this.foundPos))) * 12;
            this.tapir.setFloraPos(this.foundPos);
            this.tapir.setTrackingTime(Math.max(time, 200) + 100);
        } else {
            this.tapir.stopTracking();
        }
        this.tapir.setSniffing(false);
        this.pathNav.stop();
    }

    @Override
    public void tick() {
        if (this.stopTime > 0) {
            --this.stopTime;
            return;
        }

        if (--this.sniffTime <= 0) {
            for (int i = 0; i < 16; ++i) {
                BlockPos targetpos = this.findNearestFlora(i);
                if (targetpos != null) {
                    this.findFlora(targetpos);
                    return;
                }
            }

            this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 5);
            this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 6);
            this.tapir.playSound(SoundEvents.PIG_DEATH);
            this.tapir.setSniffing(false);
            this.stopTime = this.adjustedTickDelay(20);
        } else {
            if (this.sniffTime <= 129) {
                BlockPos targetpos = this.findNearestFlora(128 - this.sniffTime + 1);
                if (targetpos != null) {
                    this.findFlora(targetpos);
                }
            }

            if (this.pathNav.isDone() && --this.walkWait <= 0) {
                Vec3 vec3 = LandRandomPos.getPos(this.tapir, 4, 2);
                if (vec3 != null) {
                    this.pathNav.moveTo(vec3.x(), vec3.y(), vec3.z(), 0.6D);
                    this.setWalkWait();
                }
            }
        }
    }

    private void findFlora(BlockPos targetPos) {
        BlockPos partnerpos = this.findNearestPartnersFlora();
        if (partnerpos != null)
            targetPos = partnerpos;

        this.foundPos = targetPos;
        this.tapir.level.broadcastEntityEvent(this.tapir, (byte) 4);
        this.sniffTime = 0;
        this.stopTime = this.adjustedTickDelay(20);
    }

    private void setWalkWait() {
        this.walkWait = this.adjustedTickDelay(10 + this.tapir.getRandom().nextInt(20));
    }

    private BlockPos findNearestFlora(int range) {
        BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
        Block block = this.tapir.getFloraBlock();

        for (int height = 0; height <= 12; height++) {
            for (int i = 0; i < 2; i++) {
                int y = i == 0 ? -height : height;
                for(int x = 0; x <= range; x = x > 0 ? -x : 1 - x) {
                    for(int z = x < range && x > -range ? range : 0; z <= range; z = z > 0 ? -z : 1 - z) {
                        mutablepos.setWithOffset(this.origin, x, y, z);
                        if (this.tapir.level.getBlockState(mutablepos).is(block))
                            return mutablepos;
                    }
                }
            }
        }

        return null;
    }

    private BlockPos findNearestPartnersFlora() {
        List<Tapir> list = this.tapir.level.getNearbyEntities(Tapir.class, PARTNER_TARGETING, this.tapir, this.tapir.getBoundingBox().inflate(12.0D));
        Tapir partner = null;
        double d0 = Double.MAX_VALUE;

        for (Tapir entity : list) {
            double d1 = this.tapir.distanceToSqr(entity);
            if (entity.isTrackingFlora() && entity.getAge() == 0 && this.tapir.getFloraBlock() == entity.getFloraBlock() && d1 < d0) {
                d0 = d1;
                partner = entity;
            }
        }

        if (partner != null) {
            return partner.getFloraPos();
        }

        return null;
    }
}