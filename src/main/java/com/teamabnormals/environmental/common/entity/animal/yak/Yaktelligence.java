package com.teamabnormals.environmental.common.entity.animal.yak;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.environmental.common.entity.ai.brain.yak.YakGrazeBehavior;
import com.teamabnormals.environmental.common.entity.ai.brain.yak.YakHerdingBehavior;
import com.teamabnormals.environmental.common.entity.ai.brain.yak.YakMoveToRestrictionBehavior;
import com.teamabnormals.environmental.common.entity.ai.brain.yak.YakRamBehavior;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalMemoryModuleTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSensorTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.GameRules;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// yak 🧠
public class Yaktelligence {
    public static final int RAM_PREPARE_TIME = 20;
    public static final int RAM_MAX_DISTANCE = 7;
    public static final int RAM_MIN_DISTANCE = 4;
    public static final float ADULT_RAM_KNOCKBACK_FORCE = 2.5F;
    public static final float BABY_RAM_KNOCKBACK_FORCE = 1.0F;
    private static final UniformInt TIME_BETWEEN_RAMS = UniformInt.of(600, 6000);
    private static final TargetingConditions RAM_TARGET_CONDITIONS = TargetingConditions.forCombat().selector(
            (entity) -> !entity.getType().equals(EnvironmentalEntityTypes.YAK.get()) &&
                    entity.level().getWorldBorder().isWithinBounds(entity.getBoundingBox()));

    private static final int ATTACK_COOLDOWN = 20;

    public static final int HERD_RADIUS = 10;
    public static final int HERD_SEARCH_RADIUS = 12;
    public static final int HERD_CHECK_GUARANTEE = 20 * 20; // 20 seconds
    public static final int HERD_MEMORY_EXPIRATION = 20 * 60; // 1 minute

    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = SPEED_MULTIPLIER_WHEN_IDLING;
    private static final float SPEED_MULTIPLIER_WHEN_HERDING = SPEED_MULTIPLIER_WHEN_IDLING;
    private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_PREPARING_TO_RAM = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_RAMMING = 3.0F;
    private static final float SPEED_MULTIPLIER_WHEN_FIGHTING = 1.05F;
    private static final float SPEED_MULTIPLIER_WHEN_AVOIDING = 1.3F;

    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    // Yak awareness
    private static final ImmutableList<SensorType<? extends Sensor<? super Yak>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            EnvironmentalSensorTypes.YAK_TEMPTATIONS.get(),
            EnvironmentalSensorTypes.NEAREST_VISIBLE_YAKS.get()
    );

    // Yak memory
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.PATH,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,

            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get(),
            EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_BABY_YAKS.get(),
            EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_ADULT_YAKS.get(),

            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.IS_PANICKING, // needed by minecraft for the temptation behvaior, unused

            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,

            MemoryModuleType.RAM_COOLDOWN_TICKS,
            MemoryModuleType.RAM_TARGET,

            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.UNIVERSAL_ANGER,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,

            EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(),

            EnvironmentalMemoryModuleTypes.SINCE_LAST_HERD.get(),
            EnvironmentalMemoryModuleTypes.HERDING_POSITION.get()
    );

    static Brain.Provider<Yak> createProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    static Brain<?> createBrain(Yak yak, Brain<Yak> brain) {
        Yaktelligence.learnCoreActivities(brain);
        Yaktelligence.learnIdleActivities(brain);
        Yaktelligence.learnAvoidActivities(brain);
        Yaktelligence.learnFightActivities(yak, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    static void createMemories(Yak yak, RandomSource random) {
        yak.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
    }

    private static BehaviorControl<Yak> createIdleMoveBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(RandomStroll.stroll(SPEED_MULTIPLIER_WHEN_IDLING), 2),
                Pair.of(SetWalkTargetFromLookTarget.create(SPEED_MULTIPLIER_WHEN_IDLING, 3), 2),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }

    private static BehaviorControl<Yak> createIdleLookBehaviors() {
        return new RunOne<>(ImmutableList.of(
                Pair.of(SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)), 1),
                Pair.of(new DoNothing(30, 60), 1)
        ));
    }


    // Core entity functions
    private static void learnCoreActivities(Brain<Yak> brain) {
        brain.addActivity(Activity.CORE, 0,
                ImmutableList.of(
                        new Swim(0.8F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        YakHerdingBehavior.createHerdingController(),
                        YakGrazeBehavior.createGrazeController(),
                        StopBeingAngryIfTargetDead.create(),
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS)
                )
        );
    }

    private static void learnIdleActivities(Brain<Yak> brain) {
        brain.addActivityWithConditions(Activity.IDLE,
                // Behaviors
                ImmutableList.of(
                        Pair.of(0, StartAttacking.create(yak -> !yak.isBaby(), Yaktelligence::findNearestValidAttackTarget)),
                        Pair.of(1, new AnimalMakeLove(EnvironmentalEntityTypes.YAK.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE)),
                        Pair.of(2, new FollowTemptation((yak) -> SPEED_MULTIPLIER_WHEN_TEMPTED)),
                        Pair.of(3, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT)),
                        Pair.of(4, Yaktelligence.createIdleMoveBehaviors()),
                        Pair.of(4, Yaktelligence.createIdleLookBehaviors()),
                        Pair.of(5, new YakGrazeBehavior()),
                        Pair.of(6, new YakHerdingBehavior()),
                        Pair.of(7, new YakMoveToRestrictionBehavior(SPEED_MULTIPLIER_WHEN_HERDING))
                ),

                // Condition
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_ABSENT)
                )
        );
    }


    private static void learnAvoidActivities(Brain<Yak> yak) {
        yak.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                        SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, SPEED_MULTIPLIER_WHEN_AVOIDING, 12, true),
                        Yaktelligence.createIdleLookBehaviors(),
                        Yaktelligence.createIdleMoveBehaviors()
                ),
                MemoryModuleType.AVOID_TARGET);
    }

    private static void learnFightActivities(Yak yak, Brain<Yak> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                StopAttackingIfTargetInvalid.create((target) -> !isNearestValidAttackTarget(yak, target)),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(SPEED_MULTIPLIER_WHEN_FIGHTING),
                MeleeAttack.create(Yaktelligence.ATTACK_COOLDOWN)
                // TODO: Ram attack
        ), MemoryModuleType.ATTACK_TARGET);
    }

    private static void learnRamActivities(Brain<Yak> brain) {
        brain.addActivityWithConditions(Activity.RAM,

                // Behaviors
                ImmutableList.of(
                        Pair.of(0, new YakRamBehavior(
                                (yak) -> TIME_BETWEEN_RAMS, RAM_TARGET_CONDITIONS, SPEED_MULTIPLIER_WHEN_RAMMING,
                                (yak) -> yak.isBaby() ? BABY_RAM_KNOCKBACK_FORCE : ADULT_RAM_KNOCKBACK_FORCE,
                                (yak) -> SoundEvents.GOAT_RAM_IMPACT
                        )),
                        Pair.of(1, new PrepareRamNearestTarget<>((yak) ->
                                TIME_BETWEEN_RAMS.getMinValue(), RAM_MIN_DISTANCE, RAM_MAX_DISTANCE, SPEED_MULTIPLIER_WHEN_PREPARING_TO_RAM, RAM_TARGET_CONDITIONS, RAM_PREPARE_TIME,
                                (yak) -> SoundEvents.GOAT_PREPARE_RAM
                        ))
                ),

                // Conditions
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
                        Pair.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
                        Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)
                )
        );
    }

    public static boolean isFighting(Brain<Yak> brain) {
        return brain.isActive(Activity.FIGHT);
    }

    public static List<Yak> getNearestVisibleYaks(Yak yak) {
        return yak.getBrain().getMemory(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get()).orElse(Collections.emptyList());
    }

    public static List<Yak> getNearestVisibleAdultYaks(Yak yak) {
        return yak.getBrain().getMemory(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_ADULT_YAKS.get()).orElse(Collections.emptyList());
    }

    public static List<Yak> getNearestVisibleBabyYaks(Yak yak) {
        return yak.getBrain().getMemory(EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_BABY_YAKS.get()).orElse(Collections.emptyList());
    }

    protected static void wasHurtBy(Yak yak, LivingEntity target) {
        if (target instanceof Yak)
            return;

        Brain<Yak> brain = yak.getBrain();
        brain.eraseMemory(EnvironmentalMemoryModuleTypes.HERDING_POSITION.get());

        if (yak.isBaby()) {
            retreatAndAlert(yak, target);
            broadcastRetreat(yak, target);
        } else {
            Yaktelligence.retaliate(yak, target);
        }
    }

    protected static void retaliate(Yak yak, LivingEntity target) {
        if (yak.getBrain().isActive(Activity.AVOID))
            return;

        if (Sensor.isEntityAttackableIgnoringLineOfSight(yak, target)) {
            if (!BehaviorUtils.isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(yak, target, 4.0D)) {
                if (target.getType() == EntityType.PLAYER && yak.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                    setAngerTargetToNearestTargetablePlayerIfFound(yak, target);
                    broadcastUniversalAnger(yak);
                } else {
                    setAngerTarget(yak, target);
                    broadcastAngerTarget(yak, target);
                }
            }
        }
    }

    protected static void retreatAndAlert(Yak yak, LivingEntity target) {
        yak.getBrain().setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, target, 100L);
        if (Sensor.isEntityAttackableIgnoringLineOfSight(yak, target)) {
            broadcastAngerTarget(yak, target);
        }
    }

    protected static void broadcastAngerTarget(Yak yak, LivingEntity target) {
        Yaktelligence.getNearestVisibleAdultYaks(yak).forEach((y) -> setAngerTargetIfCloserThanCurrent(y, target));
    }

    private static void broadcastRetreat(Yak yak, LivingEntity target) {
        Yaktelligence.getNearestVisibleBabyYaks(yak).forEach((y) -> {
            retreatAndAlert(y, target);
        });
    }

    private static void broadcastUniversalAnger(Yak yak) {
        Yaktelligence.getNearestVisibleAdultYaks(yak).forEach((y) -> {
            getNearestVisibleTargetablePlayer(y).ifPresent((target) -> {
                setAngerTarget(y, target);
            });
        });
    }

    protected static void setAngerTarget(Yak yak, LivingEntity target) {
        if (Sensor.isEntityAttackableIgnoringLineOfSight(yak, target)) {
            yak.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            yak.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && yak.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                yak.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

    private static void setAngerTargetToNearestTargetablePlayerIfFound(Yak yak, LivingEntity target) {
        Optional<Player> optional = getNearestVisibleTargetablePlayer(yak);
        if (optional.isPresent()) {
            setAngerTarget(yak, optional.get());
        } else {
            setAngerTarget(yak, target);
        }

    }

    private static void setAngerTargetIfCloserThanCurrent(Yak yak, LivingEntity target) {
        Optional<LivingEntity> angerTarget = getAngerTarget(yak);
        LivingEntity livingentity = BehaviorUtils.getNearestTarget(yak, angerTarget, target);
        if (angerTarget.isEmpty() || angerTarget.get() != livingentity) {
            setAngerTarget(yak, livingentity);
        }
    }

    private static Optional<LivingEntity> getAngerTarget(Yak yak) {
        return BehaviorUtils.getLivingEntityFromUUIDMemory(yak, MemoryModuleType.ANGRY_AT);
    }

    private static boolean isNearestValidAttackTarget(Yak yak, LivingEntity target) {
        return findNearestValidAttackTarget(yak).filter((entity) -> entity == target).isPresent();
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Yak yak) {
        Brain<Yak> brain = yak.getBrain();

        Optional<LivingEntity> target = BehaviorUtils.getLivingEntityFromUUIDMemory(yak, MemoryModuleType.ANGRY_AT);
        if (target.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(yak, target.get())) {
            return target;
        }

        if (brain.hasMemoryValue(MemoryModuleType.UNIVERSAL_ANGER)) {
            Optional<Player> player = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
            if (player.isPresent()) {
                return player;
            }
        }

        return Optional.empty();
    }

    public static Optional<Player> getNearestVisibleTargetablePlayer(Yak yak) {
        return yak.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) ? yak.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
    }

    static void tick(Yak yak) {
        Brain<Yak> brain = yak.getBrain();
        brain.tick((ServerLevel) yak.level(), yak);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));

        yak.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }
}
