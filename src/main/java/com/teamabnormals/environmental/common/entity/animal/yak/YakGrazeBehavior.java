package com.teamabnormals.environmental.common.entity.animal.yak;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.environmental.core.registry.EnvironmentalMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class YakGrazeBehavior extends Behavior<Yak> {
    private static final int GRAZING_TIME = 40;
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);

    YakGrazeBehavior() {
        super(ImmutableMap.of(
                EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(), MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT
        ), GRAZING_TIME);
    }

    static BehaviorControl<Yak> createGrazeController() {
        return BehaviorBuilder.create(
                (instance) -> instance.group(
                        instance.present(EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get())
                ).apply(instance, (grazingTicksMemory) -> (level, yak, gameTime) -> {
                    int grazingTicks = instance.get(grazingTicksMemory);

                    // Grazing is over
                    if (grazingTicks <= 0) {
                        grazingTicksMemory.erase();
                        yak.getBrain().useDefaultActivity();
                        level.broadcastEntityEvent(yak, (byte) 11);
                    } else {
                        // Tick grazing timer
                        grazingTicksMemory.set(grazingTicks - 1);
                    }

                    return true;
                }));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Yak yak) {
        // TODO: use brain cooldown for this?
        if (yak.getRandom().nextInt(yak.isBaby() ? 5 : 10) != 0)
            return false;

        Brain<Yak> brain = yak.getBrain();
        brain.setMemory(EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(), GRAZING_TIME);
        return level.getBlockState(yak.blockPosition().below()).is(Blocks.GRASS_BLOCK) ||
                IS_TALL_GRASS.test(level.getBlockState(yak.blockPosition()));
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Yak yak, long gameTime) {
        return (level.getBlockState(yak.blockPosition().below()).is(Blocks.GRASS_BLOCK) ||
                IS_TALL_GRASS.test(level.getBlockState(yak.blockPosition()))) &&
        yak.getBrain().hasMemoryValue(EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get());
    }

    @Override
    protected void start(ServerLevel level, Yak yak, long gameTime) {
        this.dementia(yak);
        level.broadcastEntityEvent(yak, (byte) 10);
    }

    @Override
    protected void stop(ServerLevel level, Yak yak, long gameTime) {
        yak.getBrain().setMemory(EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(), 0);
    }

    protected void tick(ServerLevel level, Yak yak, long gameTime) {
        this.dementia(yak);
        if (!yak.getBrain().isMemoryValue(EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(), Mth.positiveCeilDiv(4, 2)))
            return;

        BlockPos pos = yak.blockPosition();
        if (IS_TALL_GRASS.test(level.getBlockState(pos))) {
            if (ForgeEventFactory.getMobGriefingEvent(level, yak)) {
                level.destroyBlock(pos, false);
            }

            yak.ate();
            this.doStop(level, yak, gameTime);
            return;
        }

        pos = pos.below();
        if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
            if (ForgeEventFactory.getMobGriefingEvent(level, yak)) {
                level.levelEvent(2001, pos, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 2);
            }

            yak.ate();
            this.doStop(level, yak, gameTime);
        }
    }

    private void dementia(Yak yak) {
        yak.getBrain().eraseMemory(MemoryModuleType.PATH);
        yak.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        yak.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        yak.getNavigation().stop();
    }
}
