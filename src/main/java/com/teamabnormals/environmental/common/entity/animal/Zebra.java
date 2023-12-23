package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Zebra extends AbstractHorse {
	private static final UUID SPEED_MODIFIER_KICKING_ID = UUID.fromString("AF33F716-0F4D-43CA-9C8E-1068AE2F38E6");
	private static final AttributeModifier SPEED_MODIFIER_KICKING = new AttributeModifier(SPEED_MODIFIER_KICKING_ID, "Kicking speed reduction", -0.8D, Operation.MULTIPLY_BASE);
	private final Predicate<LivingEntity> kickablePredicate;

	private static final EntityDataAccessor<Integer> KICK_TIME = SynchedEntityData.defineId(Zebra.class, EntityDataSerializers.INT);

	private float backKickAnim;
	private float backKickAnimO;

	private float frontKickAnim;
	private float frontKickAnimO;

	public Zebra(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
		this.kickablePredicate = living -> living.isAlive() && living != this && !living.getType().is(EnvironmentalEntityTypeTags.ZEBRAS_DONT_KICK) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living) && !living.isPassenger();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(KICK_TIME, 0);
	}

	@Override
	protected void randomizeAttributes(RandomSource random) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(random));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed(random));
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength(random));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isEffectiveAi()) {
			if (!this.isKicking()) {
				LivingEntity rider = this.getControllingPassenger();
				if (rider != null && !this.isStanding()) {
					List<LivingEntity> nearby = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.45F), this.kickablePredicate);
					boolean kicking = false;
					boolean backkick = true;

					for (LivingEntity living : nearby) {
						Vec3 attackAngleVector = living.position().subtract(this.position()).normalize();
						attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);

						double angle = attackAngleVector.dot(this.getForward().normalize());

						if (rider.zza > 0.0F && angle > 0.7D) {
							kicking = true;
							backkick = false;
							break;
						} else if (rider.zza <= 0.0F && angle < -0.7D) {
							kicking = true;
						}
					}

					if (kicking)
						this.kick(backkick);
				}
			} else {
				this.setKickTime(this.getKickTime() + 1);
				if (this.getKickTime() > 10) {
					this.setKickTime(0);
					this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_KICKING);
				}
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
			if (this.isFood(stack)) {
				return this.fedFood(player, stack);
			}

			InteractionResult result = stack.interactLivingEntity(player, this, hand);
			if (result.consumesAction()) {
				return result;
			}

			if (!this.isTamed()) {
				this.makeMad();
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSaddled() && stack.is(Items.SADDLE);
			if (this.isArmor(stack) || flag) {
				this.openCustomInventoryScreen(player);
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}
		}

		if (this.isBaby()) {
			return super.mobInteract(player, hand);
		} else {
			this.doPlayerRide(player);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		}
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		DamageSource source;
		LivingEntity rider = this.getControllingPassenger();
		if (rider != null)
			source = new IndirectEntityDamageSource("zebraKick", this, rider);
		else
			source = DamageSource.mobAttack(this);

		boolean flag = target.hurt(source, (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		if (flag) {
			this.doEnchantDamageEffects(this, target);
		}

		return flag;
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

	private void kick(boolean backKick) {
		this.setKickTime(1);
		this.setEating(false);

		this.playSound(this.getAmbientSound(), this.getSoundVolume(), this.getVoicePitch());

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

		List<LivingEntity> nearby = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.6F), this.kickablePredicate);
		for (LivingEntity living : nearby) {
			Vec3 attackAngleVector = living.position().subtract(this.position()).normalize();
			attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);

			float x = Mth.sin(this.getYRot() * Mth.DEG_TO_RAD);
			float z = -Mth.cos(this.getYRot() * Mth.DEG_TO_RAD);
			double angle = attackAngleVector.dot(this.getForward().normalize());

			if (!backKick && angle > 0.7D || backKick && angle < -0.7D) {
				DamageSource source;
				LivingEntity rider = this.getControllingPassenger();
				if (rider != null)
					source = new IndirectEntityDamageSource("zebraKick", this, rider);
				else
					source = DamageSource.mobAttack(this);

				int damage = (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
				if (backKick)
					damage *= 2;

				boolean flag = living.hurt(source, (float)damage);

				if (flag) {
					this.doEnchantDamageEffects(this, living);
					if (!backKick)
						living.knockback(0.8F, x, z);
					else
						living.knockback(1.5F, -x, -z);
				}
			}
		}
	}

	@Override
	public boolean canEatGrass() {
		return !this.isKicking();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.isKicking())
			super.getAmbientSound();
		return SoundEvents.HORSE_AMBIENT;
	}

	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.HORSE_ANGRY;
	}

	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.HORSE_DEATH;
	}

	@Override
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		if (!this.isKicking())
			super.getHurtSound(source);
		return SoundEvents.HORSE_HURT;
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
