package com.teamabnormals.environmental.common.entity.animal.zebroid;

import com.teamabnormals.environmental.common.entity.ai.goal.zebroid.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractUnchestedZebroid extends AbstractHorse implements NeutralMob, Zebroid {
	private static final EntityDataAccessor<Integer> KICK_TIME = SynchedEntityData.defineId(AbstractUnchestedZebroid.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> ANGRY_AT = SynchedEntityData.defineId(AbstractUnchestedZebroid.class, EntityDataSerializers.OPTIONAL_UUID);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(16, 32);
	private int remainingPersistentAngerTime;

	private ZebraFleeGoal fleeGoal;
	private float jumpStrength = -1.0F;

	private int kickCounter = 20;

	private float backKickAnim;
	private float backKickAnimO;

	private float frontKickAnim;
	private float frontKickAnimO;

	public AbstractUnchestedZebroid(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ZebroidAttackGoal<>(this, 1.6D));
		this.goalSelector.addGoal(3, new ZebroidRunAroundLikeCrazyGoal<>(this, 1.6D));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D, AbstractHorse.class));
		this.goalSelector.addGoal(5, new ZebroidTemptGoal<>(this, 1.25D, Ingredient.of(Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE)));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.7D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(11, new ZebroidRandomStandGoal<>(this));
		this.targetSelector.addGoal(1, new ZebroidHurtByTargetGoal<>(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
		this.addBehaviourGoals();
	}

	@Override
	protected void addBehaviourGoals() {
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(KICK_TIME, 0);
		builder.define(ANGRY_AT, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.readPersistentAngerSaveData(this.level(), compound);
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

		if (!this.isVehicle() && !flag) {
			ItemStack itemstack = player.getItemInHand(hand);
			if (!itemstack.isEmpty() && this.isFood(itemstack)) {
				return this.fedFood(player, itemstack);
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
	public void playKickingSound() {
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
}