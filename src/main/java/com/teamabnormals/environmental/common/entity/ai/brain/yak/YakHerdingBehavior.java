package com.teamabnormals.environmental.common.entity.ai.brain.yak;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import com.teamabnormals.environmental.common.entity.animal.yak.Yaktelligence;
import com.teamabnormals.environmental.core.registry.EnvironmentalMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.joml.Vector4d;

import java.util.List;

public class YakHerdingBehavior extends Behavior<Yak> {


    public YakHerdingBehavior() {
        super(ImmutableMap.of(
                EnvironmentalMemoryModuleTypes.HERDING_POSITION.get(), MemoryStatus.VALUE_PRESENT
        ));
    }

    public static BehaviorControl<Yak> createHerdingController() {
        return BehaviorBuilder.create(
                (instance) -> instance.group(
                        instance.present(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get()),
                        instance.registered(EnvironmentalMemoryModuleTypes.SINCE_LAST_HERD.get()),
                        instance.registered(EnvironmentalMemoryModuleTypes.HERDING_POSITION.get())
                ).apply(instance, (nearest, herdTicks, herdPos) -> (level, yak, gameTime) -> {
                    int ticksSinceLastHerd = instance.tryGet(herdTicks).orElse(0);

                    // Randomly check for a new herd
                    // After a certain amount of time, guarantee a herd check
                    if (yak.getRandom().nextInt(Yaktelligence.HERD_CHECK_GUARANTEE) < ticksSinceLastHerd++) {
                        ticksSinceLastHerd = 0;

                        List<Yak> herd = instance.get(nearest);
                        if (herd.isEmpty()) {
                            // If the yak doesn't know of a herd and can't find a herd, then return back to idle
                            // If the yak is leashed we want full control of the yak
                            if (instance.tryGet(herdPos).isEmpty() || yak.isLeashed()) {
                                yak.getBrain().useDefaultActivity();
                                yak.clearRestriction();
                            }
                        } else if (!yak.isWithinRestriction() || instance.tryGet(herdPos).isEmpty()) {
                            // Search for the nearest yaks and find the average position
                            // Set the memory to expire to trigger a herd check
                            Vector4d herdVec = herd.stream()
                                    .map(Yak::position)
                                    .reduce(new Vector4d(0, 0, 0, 0), (a, b) -> a.add(b.x, b.y, b.z, 1), Vector4d::add);
                            herdVec = herdVec.div(herdVec.w);
                            herdPos.setWithExpiry(BlockPos.containing(herdVec.x, herdVec.y, herdVec.z), Yaktelligence.HERD_MEMORY_EXPIRATION);
                        }
                    }
                    herdTicks.set(ticksSinceLastHerd);

                    return true;
                }));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Yak yak) {
        return !yak.isLeashed() &&
                !yak.isBaby() &&
                yak.getBrain().hasMemoryValue(EnvironmentalMemoryModuleTypes.HERDING_POSITION.get());
    }

    @Override
    protected void start(ServerLevel level, Yak yak, long gameTime) {
        yak.getBrain().getMemory(EnvironmentalMemoryModuleTypes.HERDING_POSITION.get())
                .ifPresent(pos -> yak.restrictTo(pos, Yaktelligence.HERD_RADIUS));
    }
}
