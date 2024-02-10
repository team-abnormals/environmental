package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.damagesource.MountDamageSource;
import com.teamabnormals.environmental.common.entity.ai.goal.HerdLandWanderGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebra.ZebraAttackGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebra.ZebraFleeGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebra.ZebraHurtByTargetGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.zebra.ZebraRunAroundLikeCrazyGoal;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Zebra extends AbstractHorse implements NeutralMob {
	private static final UUID SPEED_MODIFIER_KICKING_ID = UUID.fromString("AF33F716-0F4D-43CA-9C8E-1068AE2F38E6");
	private static final AttributeModifier SPEED_MODIFIER_KICKING = new AttributeModifier(SPEED_MODIFIER_KICKING_ID, "Kicking speed reduction", -0.8D, Operation.MULTIPLY_BASE);
	private final Predicate<LivingEntity> kickablePredicate;

	private static final EntityDataAccessor<Integer> KICK_TIME = SynchedEntityData.defineId(Zebra.class, EntityDataSerializers.INT);

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(16, 32);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	private ZebraFleeGoal fleeGoal;

	private int kickCounter = 20;

	private float backKickAnim;
	private float backKickAnimO;

	private float frontKickAnim;
	private float frontKickAnimO;

	public Zebra(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
		this.kickablePredicate = living -> living.isAlive() && living != this && !living.getType().is(EnvironmentalEntityTypeTags.ZEBRAS_DONT_KICK) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living) && !living.isPassenger();
	}

	@Override
	protected void registerGoals() {
		this.fleeGoal = new ZebraFleeGoal(this, 1.8D);
		this.goalSelector.addGoal(1, new ZebraAttackGoal(this, 1.6D));
		this.goalSelector.addGoal(2, this.fleeGoal);
		this.goalSelector.addGoal(3, new ZebraRunAroundLikeCrazyGoal(this, 1.6D));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D, AbstractHorse.class));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new HerdLandWanderGoal(this, 0.7D, 1.2D, 16));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new ZebraHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
		this.addBehaviourGoals();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(KICK_TIME, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.readPersistentAngerSaveData(this.level, compound);
	}

	@Override
	protected void randomizeAttributes(RandomSource random) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(random));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed(random));
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength(random));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isEffectiveAi() && this.isAlive()) {
			boolean resetkickcounter = true;
			if (!this.isKicking()) {
				LivingEntity rider = this.getControllingPassenger();
				if (!this.isStanding()) {
					List<LivingEntity> nearby = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.9F), this.kickablePredicate);
					boolean shouldkick = false;
					boolean backkick = true;

					for (LivingEntity living : nearby) {
						Vec3 attackAngleVector = living.position().subtract(this.position()).normalize();
						attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);
						double angle = attackAngleVector.dot(Vec3.directionFromRotation(0.0F, this.getVisualRotationYInDegrees()).normalize());
						boolean isfleeing = this.isFleeing() && !this.isImmobile() && !this.getNavigation().isDone();

						if (angle > 0.7D) {
							if (isfleeing || (rider != null && rider.zza > 0.0F)) {
								shouldkick = true;
								backkick = false;
								break;
							}
						} else if (angle < -0.7D) {
							if (isfleeing) {
								shouldkick = true;
							} else if (rider == null) {
								if (!living.isDiscrete() && --this.kickCounter <= 0)
									shouldkick = true;
								else
									resetkickcounter = false;
							} else if (rider.zza <= 0.0F) {
								shouldkick = true;
							}
						}
					}

					if (shouldkick) {
						this.kick(backkick);
						this.playKickingSound();
					}
				}
			} else {
				this.setKickTime(this.getKickTime() + 1);
				if (this.getKickTime() > 10) {
					this.setKickTime(0);
					this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_KICKING);
				}
			}

			if (resetkickcounter) {
				this.kickCounter = 20;
			}
		}

		this.backKickAnimO = this.backKickAnim;
		if (this.backKickAnim > 0)
			--this.backKickAnim;

		this.frontKickAnimO = this.frontKickAnim;
		if (this.frontKickAnim > 0)
			--this.frontKickAnim;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide)
			this.updatePersistentAnger((ServerLevel)this.level, true);
	}

	@Override
	public void setLastHurtByMob(@Nullable LivingEntity attacker) {
		if (attacker != null && this.level instanceof ServerLevel) {
			int fleetime = this.getRandom().nextInt(60) + 160;
			float fleedirection = this.getRandom().nextFloat() * 360.0F;
			this.fleeGoal.trigger(fleetime, fleedirection);
			this.alertOthers(fleetime, fleedirection);
		}

		super.setLastHurtByMob(attacker);
	}

	public void alertOthers(int fleeTime, float fleeDirection) {
		List<Zebra> zebras = this.level.getEntitiesOfClass(Zebra.class, this.getBoundingBox().inflate(12.0D, 4.0D, 12.0D), zebra -> zebra != this && !zebra.isFleeing() && !zebra.isTamed() && zebra.getTarget() == null);
		List<Zebra> closestzebras = zebras.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this))).limit(3).toList();
		for (Zebra zebra : closestzebras) {
			zebra.getFleeGoal().trigger(fleeTime + this.getRandom().nextInt(20) - 20, fleeDirection);
		}
	}

	public void announceDirectionChange(float fleeDirection) {
		List<Zebra> zebras = this.level.getEntitiesOfClass(Zebra.class, this.getBoundingBox().inflate(12.0D, 4.0D, 12.0D), zebra -> zebra != this && zebra.isFleeing());
		List<Zebra> closestzebras = zebras.stream().sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(this))).limit(3).toList();
		for (Zebra zebra : closestzebras) {
			zebra.getFleeGoal().changeDirection(fleeDirection);
		}
	}

	public ZebraFleeGoal getFleeGoal() {
		return this.fleeGoal;
	}

	public boolean isFleeing() {
		return this.fleeGoal.running();
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!this.isBaby()) {
			if (this.isTamed() && player.isSecondaryUseActive()) {
				this.openCustomInventoryScreen(player);
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}

			if (this.isVehicle()) {
				return super.mobInteract(player, hand);
			}
		}

		if (!stack.isEmpty()) {
			if (this.isFood(stack) && this.getTarget() == null) {
				return this.fedFood(player, stack);
			}

			InteractionResult result = stack.interactLivingEntity(player, this, hand);
			if (result.consumesAction()) {
				return result;
			}

			if (!this.isTamed() && this.canDoIdleAnimation()) {
				this.makeMad();
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSaddled() && stack.is(Items.SADDLE);
			if (this.isArmor(stack) || flag) {
				this.openCustomInventoryScreen(player);
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}
		}

		if (!this.isBaby()) {
			this.doPlayerRide(player);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}

	public boolean isKicking() {
		return this.getKickTime() > 0;
	}

	private int getKickTime() {
		return this.entityData.get(KICK_TIME);
	}

	private void setKickTime(int time) {
		this.entityData.set(KICK_TIME, time);
	}

	private float getBackKickAnim(float partialTick) {
		return Mth.lerp(partialTick, this.backKickAnimO, this.backKickAnim);
	}

	public float getBackKickBodyRot(float partialTick) {
		float anim = this.getBackKickAnim(partialTick);
		return anim < 5 ? interpolateMovement(0F, 5F, anim) : anim < 6 ? 1F : interpolateMovement(10F, 6F, anim);
	}

	public float getBackKickLegRot(float partialTick) {
		float anim = this.getBackKickAnim(partialTick);
		return anim < 5 ? interpolateMovement(0F, 5F, anim) : anim < 8 ? interpolateMovement(8F, 5F, anim) : 0F;
	}

	private float getFrontKickAnim(float partialTick) {
		return Mth.lerp(partialTick, this.frontKickAnimO, this.frontKickAnim);
	}

	public float getFrontKickBodyRot(float partialTick) {
		float anim = this.getFrontKickAnim(partialTick);
		return anim < 6 ? interpolateMovement(0F, 6F, anim) : anim < 8 ? 1F : interpolateMovement(12F, 8F, anim);
	}

	public float getFrontKickLegRot(float partialTick) {
		float anim = this.getFrontKickAnim(partialTick);
		return anim < 6 ? interpolateMovement(0F, 6F, anim) : anim < 7 ? 1F : anim < 10 ? interpolateMovement(10F, 7F, anim) : 0F;
	}

	private static float interpolateMovement(float min, float max, float progress) {
		return 1F - Mth.square((progress - max) / (max - min));
	}

	public void playBackKickAnim() {
		this.backKickAnim = 10;
		this.frontKickAnim = 0;
		this.backKickAnimO = this.backKickAnim;
		this.frontKickAnimO = this.frontKickAnim;
	}

	public void playFrontKickAnim() {
		this.backKickAnim = 0;
		this.frontKickAnim = 12;
		this.backKickAnimO = this.backKickAnim;
		this.frontKickAnimO = this.frontKickAnim;
	}

	public void kick(boolean backKick) {
		this.setKickTime(1);
		this.setEating(false);

		AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (!attributeinstance.hasModifier(SPEED_MODIFIER_KICKING))
			attributeinstance.addTransientModifier(SPEED_MODIFIER_KICKING);

		if (!backKick) {
			this.playFrontKickAnim();
			this.level.broadcastEntityEvent(this, (byte) 8);
		} else {
			this.playBackKickAnim();
			this.level.broadcastEntityEvent(this, (byte) 9);
		}

		List<LivingEntity> nearby = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0F), this.kickablePredicate);
		for (LivingEntity living : nearby) {
			Vec3 attackAngleVector = living.position().subtract(this.position()).normalize();
			attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);

			float rot = this.getVisualRotationYInDegrees();
			float x = Mth.sin(rot * Mth.DEG_TO_RAD);
			float z = -Mth.cos(rot * Mth.DEG_TO_RAD);
			double angle = attackAngleVector.dot(Vec3.directionFromRotation(0.0F, rot).normalize());

			if (!backKick && angle > 0.7D || backKick && angle < -0.7D) {
				DamageSource source;
				LivingEntity rider = this.getControllingPassenger();
				if (rider != null)
					source = new MountDamageSource("ridingZebra", this, rider);
				else
					source = DamageSource.mobAttack(this);

				float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
				if (backKick)
					damage += 1.0F;

				boolean flag = living.hurt(source, damage);

				if (flag) {
					float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
					this.doEnchantDamageEffects(this, living);
					if (!backKick)
						living.knockback(knockback * 0.8F, x, z);
					else
						living.knockback(knockback * 1.5F, -x, -z);
				}
			}
		}
	}

	private boolean canDoIdleAnimation() {
		return !this.isKicking() && this.getMoveControl().getSpeedModifier() <= 1.0D;
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
		this.persistentAngerTarget = target;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.canDoIdleAnimation())
			super.getAmbientSound();
		return SoundEvents.HORSE_AMBIENT;
	}

	@Override
	protected SoundEvent getAngrySound() {
		if (this.canDoIdleAnimation())
			super.getAngrySound();
		return SoundEvents.HORSE_ANGRY;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.HORSE_DEATH;
	}

	@Override
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		if (!this.isKicking())
			super.getHurtSound(source);
		return SoundEvents.HORSE_HURT;
	}

	public void playKickingSound() {
		this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());
	}

	public void playAngrySound() {
		this.playSound(this.getAngrySound(), this.getSoundVolume(), this.getVoicePitch());
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (!state.getMaterial().isLiquid()) {
			BlockState blockstate = this.level.getBlockState(pos.above());
			SoundType soundtype = state.getSoundType(level, pos, this);
			if (blockstate.is(Blocks.SNOW)) {
				soundtype = blockstate.getSoundType(level, pos, this);
			}

			if ((this.isVehicle() || this.getMoveControl().getSpeedModifier() > 1.6D) && this.canGallop) {
				++this.gallopSoundCounter;
				if (this.gallopSoundCounter > 5 && this.gallopSoundCounter % 3 == 0) {
					this.playGallopSound(soundtype);
				} else if (this.gallopSoundCounter <= 5) {
					this.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
				}
			} else if (soundtype == SoundType.WOOD) {
				this.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			} else {
				this.playSound(SoundEvents.HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			}
		}
	}

	@Override
	public void positionRider(Entity rider) {
		super.positionRider(rider);

		float f = Mth.sin(this.yBodyRot * Mth.DEG_TO_RAD);
		float f1 = Mth.cos(this.yBodyRot * Mth.DEG_TO_RAD);
		float f2 = 0.0F;
		float f3 = 0.0F;
		float nostandanim = 1.0F - this.standAnimO;

		if (this.standAnimO > 0.0F) {
			f2 += 0.7F * this.standAnimO;
			f3 += 0.15F * this.standAnimO;
			if (rider instanceof LivingEntity)
				((LivingEntity) rider).yBodyRot = this.yBodyRot;
		}

		if (this.backKickAnimO > 0.0F) {
			float rot = this.getBackKickBodyRot(0.0F);
			f2 += -0.2F * rot * nostandanim;
			f3 += 0.15F * rot * nostandanim;
		} else if (this.frontKickAnimO > 0.0F) {
			float rot = this.getFrontKickBodyRot(0.0F);
			f2 += 0.2F * rot * nostandanim;
			f3 += 0.15F * rot * nostandanim;
		}

		rider.setPos(this.getX() + (double)(f2 * f), this.getY() + this.getPassengersRidingOffset() + rider.getMyRidingOffset() + (double)f3, this.getZ() - (double)(f2 * f1));
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
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return EnvironmentalEntityTypes.ZEBRA.get().create(level);
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() - 0.175D;
	}
}