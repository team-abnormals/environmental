package com.teamabnormals.environmental.common.entity.ai.goal;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class PineconeGolemLookForSpotGoal extends WaterAvoidingRandomStrollGoal {

    public PineconeGolemLookForSpotGoal(PathfinderMob golem, double speed) {
        super(golem, speed);
        this.interval = 40;
    }

    @Override
    public boolean canUse() {
        return this.mob.getMainHandItem().is(ItemTags.SAPLINGS) && super.canUse();
    }
}