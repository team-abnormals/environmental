package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class PineconeGolemPlantSaplingGoal extends Goal {
    private final PineconeGolem golem;
    private final double moveSpeed;

    public PineconeGolemPlantSaplingGoal(PineconeGolem golem, double speed) {
        this.golem = golem;
        this.moveSpeed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return false;
    }
}