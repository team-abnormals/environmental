package com.teamabnormals.environmental.common.entity.ai.brain.yak;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import com.teamabnormals.environmental.common.entity.animal.yak.Yaktelligence;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class YakMoveToRestrictionBehavior extends Behavior<Yak> {
    private final float speedModifier;
    private Vec3 targetVec;

    public YakMoveToRestrictionBehavior(float speedModifier) {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED
        ), 150, 250);
        this.speedModifier = speedModifier;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Yak yak) {
        if (yak.isWithinRestriction() || yak.isBaby() || yak.isLeashed()) {
            return false;
        }
        this.targetVec = DefaultRandomPos.getPosTowards(yak, 16, 7, Vec3.atBottomCenterOf(yak.getRestrictCenter()), Math.PI / 2.0F);
        return this.targetVec != null;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Yak yak, long gameTime) {
        return !yak.getNavigation().isDone() &&
                !yak.isWithinRestriction() && !
                yak.isBaby() &&
                !yak.isLeashed();
    }

    @Override
    protected void start(ServerLevel level, Yak yak, long gameTime) {
        yak.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(this.targetVec.x, this.targetVec.y, this.targetVec.z), this.speedModifier,  Yaktelligence.HERD_RADIUS - 2));
    }

    @Override
    protected void stop(ServerLevel level, Yak yak, long gameTime) {
        yak.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }
}