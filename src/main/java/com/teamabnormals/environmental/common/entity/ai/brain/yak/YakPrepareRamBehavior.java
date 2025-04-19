package com.teamabnormals.environmental.common.entity.ai.brain.yak;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class YakPrepareRamBehavior extends Behavior<Yak> {
    public static final int TIME_OUT_DURATION = 160;
    private final ToIntFunction<Yak> getCooldownOnFail;
    private final int ramPrepareTime;
    private final Function<Yak, SoundEvent> getPrepareRamSound;
    private Optional<Long> ramTime = Optional.empty();
    private Optional<LivingEntity> ramCandidate = Optional.empty();

    public YakPrepareRamBehavior(ToIntFunction<Yak> getCooldownOnFail, int ramPrepareTime, Function<Yak, SoundEvent> getPrepareRamSound) {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
                MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT
        ), TIME_OUT_DURATION);
        this.getCooldownOnFail = getCooldownOnFail;
        this.ramPrepareTime = ramPrepareTime;
        this.getPrepareRamSound = getPrepareRamSound;
    }

    @Override
    protected void start(ServerLevel level, Yak yak, long gameTime) {
        yak.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET)
                .ifPresent(this::nominateTargetForRamming);
    }

    @Override
    protected void stop(ServerLevel level, Yak yak, long gameTime) {
        Brain<?> brain = yak.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.RAM_TARGET)) {
            level.broadcastEntityEvent(yak, (byte) 59);
            brain.setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getCooldownOnFail.applyAsInt(yak));
        }
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Yak yak, long gameTime) {
        return this.ramCandidate.isPresent() && this.ramCandidate.get().isAlive() && yak.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void tick(ServerLevel level, Yak yak, long gameTime) {
        if (this.ramCandidate.isEmpty())
            return;

        yak.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        yak.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(this.ramCandidate.get(), true));

        level.broadcastEntityEvent(yak, (byte) 58);
        if (this.ramTime.isEmpty()) {
            this.ramTime = Optional.of(gameTime);
        }

        if (gameTime - this.ramTime.get() >= (long) this.ramPrepareTime) {
            yak.getBrain().setMemory(MemoryModuleType.RAM_TARGET, this.ramCandidate.get().position());
            level.playSound(null, yak, this.getPrepareRamSound.apply(yak), SoundSource.NEUTRAL, 1.0F, yak.getVoicePitch());
            this.ramCandidate = Optional.empty();
        }

    }

    private void nominateTargetForRamming(LivingEntity target) {
        this.ramTime = Optional.empty();
        this.ramCandidate = Optional.of(target);
    }
}