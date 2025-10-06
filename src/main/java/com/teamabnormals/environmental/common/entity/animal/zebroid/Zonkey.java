package com.teamabnormals.environmental.common.entity.animal.zebroid;

import com.teamabnormals.environmental.common.entity.ai.goal.zebroid.*;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntUnaryOperator;

public class Zonkey extends AbstractChestedHorse implements NeutralMob, Zebroid {
	public static final float MIN_DAMAGE = generateAttackDamage(value -> 0);
	public static final float MAX_DAMAGE = generateAttackDamage(value -> value - 1);

	private static final EntityDataAccessor<Integer> KICK_TIME = SynchedEntityData.defineId(Zonkey.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STRIPE_OPACITY = SynchedEntityData.defineId(Zonkey.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> ANGRY_AT = SynchedEntityData.defineId(Zonkey.class, EntityDataSerializers.OPTIONAL_UUID);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(16, 32);
	private int remainingPersistentAngerTime;

	private float jumpStrength = -1.0F;

	private int kickCounter = 20;

	private float backKickAnim;
	private float backKickAnimO;

	private float frontKickAnim;
	private float frontKickAnimO;

	public Zonkey(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ZebroidAttackGoal<>(this, 1.6D));
		this.goalSelector.addGoal(2, new ZebroidPanicGoal<>(this, 1.8D));
		this.goalSelector.addGoal(3, new ZebroidRunAroundLikeCrazyGoal<>(this, 1.6D));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D, AbstractHorse.class));
		this.goalSelector.addGoal(5, new ZebroidTemptGoal<>(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE)));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new ZebroidAvoidEntityGoal<>(this, Player.class, 8.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.7D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(11, new ZebroidRandomStandGoal<>(this));
		this.targetSelector.addGoal(1, new ZebroidHurtByTargetGoal<>(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(KICK_TIME, 0);
		builder.define(STRIPE_OPACITY, 100);
		builder.define(ANGRY_AT, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
		compound.putInt("StripeOpacity", this.getStripeOpacity());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.readPersistentAngerSaveData(this.level(), compound);
		this.setStripeOpacity(compound.getInt("StripeOpacity"));
	}

	@Override
	protected void randomizeAttributes(RandomSource random) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(generateMaxHealth(random::nextInt));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(generateSpeed(random::nextDouble));
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength(random::nextDouble));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(generateAttackDamage(random::nextInt));
	}

	private static float generateAttackDamage(IntUnaryOperator random) {
		return 1.0F + random.applyAsInt(2);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	// Zebroid getters and setters
	@Override
	public void setKickTime(int time) {
		this.entityData.set(KICK_TIME, time);
	}

	@Override
	public int getKickTime() {
		return this.entityData.get(KICK_TIME);
	}

	@Override
	public boolean canJumpKick() {
		return this.jumpStrength >= 0.0F;
	}

	@Override
	public void setJumpStrength(float strength) {
		this.jumpStrength = strength;
	}

	@Override
	public float getJumpStrength() {
		return this.jumpStrength;
	}

	@Override
	public boolean isFleeing() {
		return false;
	}

	@Override
	public void setGallopSoundCounter(int count) {
		this.gallopSoundCounter = count;
	}

	@Override
	public int getGallopSoundCounter() {
		return this.gallopSoundCounter;
	}

	@Override
	public boolean canGallop() {
		return this.canGallop;
	}

	@Override
	public void setKickCounter(int count) {
		this.kickCounter = count;
	}

	@Override
	public int getKickCounter() {
		return this.kickCounter;
	}

	@Override
	public void setBackKickAnim(float amount) {
		this.backKickAnim = amount;
	}

	@Override
	public float getBackKickAnim() {
		return this.backKickAnim;
	}

	@Override
	public void setBackKickAnimO(float amount) {
		this.backKickAnimO = amount;
	}

	@Override
	public float getBackKickAnimO() {
		return this.backKickAnimO;
	}

	@Override
	public void setFrontKickAnim(float amount) {
		this.frontKickAnim = amount;
	}

	@Override
	public float getFrontKickAnim() {
		return this.frontKickAnim;
	}

	@Override
	public void setFrontKickAnimO(float amount) {
		this.frontKickAnimO = amount;
	}

	@Override
	public float getFrontKickAnimO() {
		return this.frontKickAnimO;
	}

	public int getStripeOpacity() {
		return this.entityData.get(STRIPE_OPACITY);
	}

	public void setStripeOpacity(int time) {
		this.entityData.set(STRIPE_OPACITY, time);
	}

	public void randomizeStripeOpacity(RandomSource random) {
		this.setStripeOpacity(33 + random.nextInt(68));
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		boolean flag = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
		if (!this.isVehicle() && !flag && !player.getItemInHand(hand).isEmpty() && !this.isTamed()) {
			if (this.isAngryAt(player)) {
				return InteractionResult.CONSUME;
			} else {
				this.makeMad();
				this.setTarget(player);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		return super.mobInteract(player, hand);
	}

	@Override
	public void tick() {
		super.tick();
		this.handleTick();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide)
			this.updatePersistentAnger((ServerLevel) this.level(), true);
	}

	@Override
	public void travel(Vec3 motion) {
		boolean flag = this.isJumping();
		super.travel(motion);
		this.handleTravel(flag);
	}

	@Override
	public void setLeashedTo(Entity entity, boolean broadcast) {
		super.setLeashedTo(entity, broadcast);
		this.handleLeashed(entity);
	}

	@Override
	public boolean canEatGrass() {
		return this.canDoIdleAnimation();
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return (!this.isTamed() || !(target instanceof Player)) && super.canAttack(target);
	}

	@Override
	public void stopBeingAngry() {
		NeutralMob.super.stopBeingAngry();
		this.setLastHurtByPlayer(null);
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.remainingPersistentAngerTime = time;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID target) {
		this.entityData.set(ANGRY_AT, Optional.ofNullable(target));
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.entityData.get(ANGRY_AT).orElse(null);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSoundEvents.ZONKEY_AMBIENT.get();
	}

	@Override
	protected SoundEvent getAngrySound() {
		return EnvironmentalSoundEvents.ZONKEY_ANGRY.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.ZONKEY_DEATH.get();
	}

	@Override
	protected SoundEvent getEatingSound() {
		return EnvironmentalSoundEvents.ZONKEY_EAT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		if (!this.isKicking())
			super.getHurtSound(source);
		return EnvironmentalSoundEvents.ZONKEY_HURT.get();
	}

	@Override
	public void playKickingSound() {
		this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	public void playAngrySound() {
		this.playSound(this.getAngrySound(), this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.handleStepSound(pos, state);
	}

	@Override
	public void playZebroidGallopSound(SoundType soundType) {
		this.playGallopSound(soundType);
	}

	@Override
	public void positionRider(Entity rider, Entity.MoveFunction function) {
		super.positionRider(rider, function);
		this.handlePositionRider(rider, function);
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 8) {
			this.playFrontKickAnim();
		} else if (id == 9) {
			this.playBackKickAnim();
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {
		this.randomizeStripeOpacity(level.getRandom());
		return super.finalizeSpawn(level, difficulty, spawnType, groupData);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Zonkey zonkey = EnvironmentalEntityTypes.ZONKEY.get().create(level);
		if (zonkey != null) {
			zonkey.randomizeStripeOpacity(level.getRandom());
			this.setOffspringAttributes(otherParent, zonkey);
		}
		return zonkey;
	}

	@Override
	public void setOffspringAttributes(AgeableMob otherParent, AbstractHorse child) {
		super.setOffspringAttributes(otherParent, child);
		this.setOffspringAttribute(otherParent, child, Attributes.ATTACK_DAMAGE, MIN_DAMAGE, MAX_DAMAGE);
	}

	@Override
	public int getInventoryColumns() {
		return this.hasChest() ? 3 : 0;
	}
}
