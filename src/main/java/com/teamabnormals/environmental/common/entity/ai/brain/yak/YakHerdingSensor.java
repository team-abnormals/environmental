package com.teamabnormals.environmental.common.entity.ai.brain.yak;

import com.google.common.collect.ImmutableSet;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import com.teamabnormals.environmental.common.entity.animal.yak.Yaktelligence;
import com.teamabnormals.environmental.core.registry.EnvironmentalMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class YakHerdingSensor extends Sensor<Yak> {

    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    protected void doTick(ServerLevel level, Yak yak) {
        yak.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).ifPresent((entities) -> {
            List<Yak> nearest = entities.find(
                    (entity) -> entity.getType() == yak.getType() &&
                            !entity.isBaby() &&
                            yak.distanceToSqr(entity) < Yaktelligence.HERD_SEARCH_RADIUS * Yaktelligence.HERD_SEARCH_RADIUS
            ).map(Yak.class::cast).sorted(Comparator.comparing(yak::distanceToSqr)).toList();
            yak.getBrain().setMemory(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get(), nearest);
        });
    }
}