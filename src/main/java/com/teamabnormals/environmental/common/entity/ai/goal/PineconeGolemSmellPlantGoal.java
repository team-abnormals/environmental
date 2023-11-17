package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class PineconeGolemSmellPlantGoal extends MoveToBlockGoal {
    private final PineconeGolem golem;

    public PineconeGolemSmellPlantGoal(PathfinderMob golem, double speed) {
        super(golem, speed, 4);
        this.golem = (PineconeGolem) golem;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget()) {
            this.golem.getNavigation().stop();
            this.golem.setSniffs(3 + this.golem.getRandom().nextInt(3));
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.is(BlockTags.SAPLINGS) || blockstate.is(BlockTags.FLOWERS);
    }
}