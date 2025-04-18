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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.schedule.Activity;

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

    public static final int HERD_RADIUS = 10;
    public static final int HERD_SEARCH_RADIUS = 12;
    public static final int HERD_CHECK_GUARANTEE = 20 * 20; // 20 seconds
    public static final int HERD_MEMORY_EXPIRATION = 20 * 60; // 1 minute

    private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_IDLING = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_HERDING = 1.0F;
    private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_TEMPTED = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_PREPARING_TO_RAM = 1.1F;
    private static final float SPEED_MULTIPLIER_WHEN_RAMMING = 3.0F;

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
            MemoryModuleType.NEAREST_VISIBLE_ADULT,

            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,

            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,

            MemoryModuleType.RAM_COOLDOWN_TICKS,
            MemoryModuleType.RAM_TARGET,

            MemoryModuleType.IS_PANICKING, // needed by minecraft for the temptation behvaior, unused

            EnvironmentalMemoryModuleTypes.GRAZING_TICKS.get(),
            EnvironmentalMemoryModuleTypes.SINCE_LAST_HERD.get(),
            EnvironmentalMemoryModuleTypes.HERDING_POSITION.get(),
            EnvironmentalMemoryModuleTypes.NEAREST_VISIBLE_YAKS.get()
    );

    static Brain.Provider<Yak> createProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    static Brain<?> createBrain(Brain<Yak> brain) {
        Yaktelligence.learnCoreActivities(brain);
        Yaktelligence.learnIdleActivities(brain);
        Yaktelligence.learnRamActivities(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    static void createMemories(Yak yak, RandomSource random) {
        yak.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, TIME_BETWEEN_RAMS.sample(random));
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
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                        new CountDownCooldownTicks(MemoryModuleType.RAM_COOLDOWN_TICKS)
                )
        );
    }

    private static void learnIdleActivities(Brain<Yak> brain) {
        brain.addActivityWithConditions(Activity.IDLE,
                // Behaviors
                ImmutableList.of(
                        Pair.of(0, new AnimalMakeLove(EnvironmentalEntityTypes.YAK.get(), SPEED_MULTIPLIER_WHEN_MAKING_LOVE)),
                        Pair.of(1, new FollowTemptation((yak) -> SPEED_MULTIPLIER_WHEN_TEMPTED)),
                        Pair.of(2, BabyFollowAdult.create(ADULT_FOLLOW_RANGE, SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT)),
                        Pair.of(3, new RunOne<>(ImmutableList.of(
                                Pair.of(RandomStroll.stroll(SPEED_MULTIPLIER_WHEN_IDLING), 2),
                                Pair.of(SetWalkTargetFromLookTarget.create(SPEED_MULTIPLIER_WHEN_IDLING, 3), 2),
                                Pair.of(new DoNothing(30, 60), 1)
                        ))),
                        Pair.of(4, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
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

    static void tick(Yak yak) {
        yak.getBrain().tick((ServerLevel) yak.level(), yak);
        yak.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.RAM, Activity.IDLE));
    }
}
