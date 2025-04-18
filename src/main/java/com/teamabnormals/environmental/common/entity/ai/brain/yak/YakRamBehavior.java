package com.teamabnormals.environmental.common.entity.ai.brain.yak;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class YakRamBehavior extends Behavior<Yak> {
    public static final int TIME_OUT_DURATION = 200;
    public static final float RAM_SPEED_FORCE_FACTOR = 1.65F;
    private final Function<Yak, UniformInt> getTimeBetweenRams;
    private final TargetingConditions ramTargeting;
    private final float speed;
    private final ToDoubleFunction<Yak> getKnockbackForce;
    private final Function<Yak, SoundEvent> getImpactSound;
    private Vec3 ramDirection;

    public YakRamBehavior(Function<Yak, UniformInt> ramCooldown, TargetingConditions ramTargeting, float speed, ToDoubleFunction<Yak> knockbackForce, Function<Yak, SoundEvent> impactSound) {
        super(ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), TIME_OUT_DURATION);
        this.getTimeBetweenRams = ramCooldown;
        this.ramTargeting = ramTargeting;
        this.speed = speed;
        this.getKnockbackForce = knockbackForce;
        this.getImpactSound = impactSound;
        this.ramDirection = Vec3.ZERO;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Yak yak) {
        return yak.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Yak yak, long gameTime) {
        return yak.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
    }

    @Override
    protected void start(ServerLevel level, Yak yak, long gameTime) {
        BlockPos pos = yak.blockPosition();
        Brain<?> brain = yak.getBrain();
        Vec3 ramTargetPos = brain.getMemory(MemoryModuleType.RAM_TARGET).get();
        this.ramDirection = (new Vec3(pos.getX() - ramTargetPos.x(), 0.0D, pos.getZ() - ramTargetPos.z())).normalize();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(ramTargetPos, this.speed, 0));
    }

    @Override
    protected void tick(ServerLevel level, Yak yak, long gameTime) {
        List<LivingEntity> potentialTargets = level.getNearbyEntities(LivingEntity.class, this.ramTargeting, yak, yak.getBoundingBox());
        Brain<?> brain = yak.getBrain();
        if (potentialTargets.isEmpty()) {
            Optional<WalkTarget> destination = brain.getMemory(MemoryModuleType.WALK_TARGET);
            Optional<Vec3> ramTarget = brain.getMemory(MemoryModuleType.RAM_TARGET);
            if (destination.isEmpty() || ramTarget.isEmpty() || destination.get().getTarget().currentPosition().closerThan(ramTarget.get(), 0.25D)) {
                this.finishRam(level, yak);
            }
            return;
        }

        LivingEntity target = potentialTargets.get(0);
        target.hurt(level.damageSources().noAggroMobAttack(yak), (float) yak.getAttributeValue(Attributes.ATTACK_DAMAGE));

        int speedFactor = yak.hasEffect(MobEffects.MOVEMENT_SPEED) ? yak.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = yak.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? yak.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float force = 0.25F * (speedFactor - slownessFactor);

        float speedForce = Mth.clamp(yak.getSpeed() * RAM_SPEED_FORCE_FACTOR, 0.2F, 3.0F) + force;
        float knockbackModifier = target.isDamageSourceBlocked(level.damageSources().mobAttack(yak)) ? 0.5F : 1.0F;

        target.knockback((knockbackModifier * speedForce) * this.getKnockbackForce.applyAsDouble(yak), this.ramDirection.x(), this.ramDirection.z());
        this.finishRam(level, yak);
        level.playSound(null, yak, this.getImpactSound.apply(yak), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    protected void finishRam(ServerLevel level, Yak yak) {
        level.broadcastEntityEvent(yak, (byte) 59);
        yak.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, this.getTimeBetweenRams.apply(yak).sample(level.getRandom()));
        yak.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
    }
}
