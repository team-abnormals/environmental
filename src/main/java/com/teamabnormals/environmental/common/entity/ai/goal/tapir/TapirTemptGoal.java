package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TapirTemptGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final Tapir tapir;
    private final double speedModifier;
    @Nullable
    protected Player player;
    private int calmDown;

    public TapirTemptGoal(Tapir tapir, double speed) {
        this.tapir = tapir;
        this.speedModifier = speed;
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            this.player = this.tapir.level.getNearestPlayer(this.targetingConditions, this.tapir);
            return this.player != null;
        }
    }

    private boolean shouldFollow(LivingEntity entity) {
        return (this.tapir.isFood(entity.getMainHandItem()) || this.tapir.isFood(entity.getOffhandItem())) && !this.tapir.hasFloraItem();
    }

    @Override
    public void start() {
        this.tapir.setBeingTempted(true);
    }

    @Override
    public void stop() {
        this.player = null;
        this.tapir.getNavigation().stop();
        this.tapir.setBeingTempted(false);
        this.calmDown = reducedTickDelay(100);
    }

    @Override
    public void tick() {
        this.tapir.getLookControl().setLookAt(this.player, this.tapir.getMaxHeadYRot() + 20, this.tapir.getMaxHeadXRot());
        if (this.tapir.distanceToSqr(this.player) < 6.25D) {
            this.tapir.getNavigation().stop();
        } else {
            this.tapir.getNavigation().moveTo(this.player, this.speedModifier);
        }
    }
}