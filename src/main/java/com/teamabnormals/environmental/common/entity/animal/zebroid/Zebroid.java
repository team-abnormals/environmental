package com.teamabnormals.environmental.common.entity.animal.zebroid;

import com.teamabnormals.environmental.common.network.message.C2SZebraJumpMessage;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalDamageTypes;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface Zebroid {
	UUID SPEED_MODIFIER_KICKING_ID = UUID.fromString("AF33F716-0F4D-43CA-9C8E-1068AE2F38E6");
	AttributeModifier SPEED_MODIFIER_KICKING = new AttributeModifier(SPEED_MODIFIER_KICKING_ID, "Kicking speed reduction", -0.8D, Operation.MULTIPLY_BASE);
	Predicate<LivingEntity> KICKABLE_PREDICATE = living -> living.isAlive() && !living.getType().is(EnvironmentalEntityTypeTags.ZEBROIDS_DONT_KICK) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(living) && !living.isPassenger();
	;

	// Kicking
	void setKickTime(int time);

	int getKickTime();

	default boolean isKicking() {
		return this.getKickTime() > 0;
	}

	// Jumping
	boolean canJumpKick();

	void setJumpStrength(float strength);

	float getJumpStrength();

	// Fleeing
	boolean isFleeing();

	// Sound
	void playKickingSound();

	void playAngrySound();

	void setGallopSoundCounter(int count);

	int getGallopSoundCounter();

	boolean canGallop();

	void playZebroidGallopSound(SoundType soundType);

	// Animation
	void setKickCounter(int count);

	int getKickCounter();

	void setBackKickAnim(float amount);

	float getBackKickAnim();

	void setBackKickAnimO(float amount);

	float getBackKickAnimO();

	void setFrontKickAnim(float amount);

	float getFrontKickAnim();

	void setFrontKickAnimO(float amount);

	float getFrontKickAnimO();

	default float getBackKickAnim(float partialTick) {
		return Mth.lerp(partialTick, this.getBackKickAnimO(), this.getBackKickAnim());
	}

	default float getFrontKickAnim(float partialTick) {
		return Mth.lerp(partialTick, this.getFrontKickAnimO(), this.getFrontKickAnim());
	}

	default float getBackKickBodyRot(float partialTick) {
		float anim = this.getBackKickAnim(partialTick);
		return anim < 5 ? smoothAnim(0F, 5F, anim) : anim < 6 ? 1F : smoothAnim(10F, 6F, anim);
	}

	default float getBackKickLegRot(float partialTick) {
		float anim = this.getBackKickAnim(partialTick);
		return anim < 5 ? smoothAnim(0F, 5F, anim) : anim < 8 ? smoothAnim(8F, 5F, anim) : 0F;
	}

	default float getFrontKickBodyRot(float partialTick) {
		float anim = this.getFrontKickAnim(partialTick);
		return anim < 6 ? smoothAnim(0F, 6F, anim) : anim < 8 ? 1F : smoothAnim(12F, 8F, anim);
	}

	default float getFrontKickLegRot(float partialTick) {
		float anim = this.getFrontKickAnim(partialTick);
		return anim < 6 ? smoothAnim(0F, 6F, anim) : anim < 7 ? 1F : anim < 10 ? smoothAnim(10F, 7F, anim) : 0F;
	}

	default void playBackKickAnim() {
		this.setBackKickAnim(10);
		this.setFrontKickAnim(0);
		this.setBackKickAnimO(10);
		this.setFrontKickAnimO(0);
	}

	default void playFrontKickAnim() {
		this.setBackKickAnim(0);
		this.setFrontKickAnim(12);
		this.setBackKickAnimO(0);
		this.setFrontKickAnimO(12);
	}

	default void handleTick() {
		AbstractHorse horse = (AbstractHorse) this;

		if (horse.onGround() || horse.isInFluidType())
			this.setJumpStrength(-1.0F);

		if (horse.isEffectiveAi() && horse.isAlive()) {
			boolean resetkickcounter = true;
			if (!horse.isBaby() && !this.isKicking()) {
				LivingEntity rider = horse.getControllingPassenger();
				boolean jumpkick = this.canJumpKick();
				boolean isfleeing = this.isFleeing() && !horse.isImmobile() && !horse.getNavigation().isDone();

				if (!horse.isStanding() || jumpkick) {
					List<LivingEntity> nearby = horse.level().getEntitiesOfClass(LivingEntity.class, horse.getBoundingBox().inflate(0.9F), KICKABLE_PREDICATE);
					boolean shouldkick = false;
					boolean backkick = true;

					for (LivingEntity living : nearby) {
						Vec3 attackAngleVector = living.position().subtract(horse.position()).normalize();
						attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);
						double angle = attackAngleVector.dot(Vec3.directionFromRotation(0.0F, horse.getVisualRotationYInDegrees()).normalize());

						if (angle > 0.7D) {
							if (isfleeing || jumpkick || (rider != null && rider.zza > 0.0F)) {
								shouldkick = true;
								backkick = false;
								break;
							}
						} else if (angle < -0.7D && !jumpkick) {
							if (isfleeing) {
								shouldkick = true;
							} else if (rider == null) {
								if (!living.isDiscrete()) {
									this.setKickCounter(this.getKickCounter() - 1);
									if (this.getKickCounter() <= 0)
										shouldkick = true;
								}
								if (!shouldkick)
									resetkickcounter = false;
							} else if (rider.zza <= 0.0F) {
								shouldkick = true;
							}
						}
					}

					if (shouldkick) {
						this.kick(backkick, rider == null && !isfleeing);
						this.playKickingSound();
					}
				}
			} else {
				this.setKickTime(this.getKickTime() + 1);
				if (this.getKickTime() > 10) {
					this.setKickTime(0);
					horse.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_KICKING);
				}
			}

			if (resetkickcounter) {
				this.setKickCounter(20);
			}
		}

		float f = this.getBackKickAnim();
		this.setBackKickAnimO(f);
		if (f > 0)
			this.setBackKickAnim(f - 1);

		float f1 = this.getFrontKickAnim();
		this.setFrontKickAnimO(f1);
		if (f1 > 0)
			this.setFrontKickAnim(f1 - 1);
	}

	default void kick(boolean backKick) {
		this.kick(backKick, false);
	}

	default void kick(boolean backKick, boolean softBackKick) {
		AbstractHorse horse = (AbstractHorse) this;

		this.setKickTime(1);
		horse.setEating(false);

		AttributeInstance attributeinstance = horse.getAttribute(Attributes.MOVEMENT_SPEED);
		if (!attributeinstance.hasModifier(SPEED_MODIFIER_KICKING))
			attributeinstance.addTransientModifier(SPEED_MODIFIER_KICKING);

		if (!backKick) {
			this.playFrontKickAnim();
			horse.level().broadcastEntityEvent(horse, (byte) 8);
		} else {
			this.playBackKickAnim();
			horse.level().broadcastEntityEvent(horse, (byte) 9);
		}

		List<LivingEntity> nearby = horse.level().getEntitiesOfClass(LivingEntity.class, horse.getBoundingBox().inflate(1.0F), KICKABLE_PREDICATE);
		for (LivingEntity living : nearby) {
			Vec3 attackAngleVector = living.position().subtract(horse.position()).normalize();
			attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);

			float rot = horse.getVisualRotationYInDegrees();
			float x = Mth.sin(rot * Mth.DEG_TO_RAD);
			float z = -Mth.cos(rot * Mth.DEG_TO_RAD);
			double angle = attackAngleVector.dot(Vec3.directionFromRotation(0.0F, rot).normalize());
			boolean jumpkick = this.canJumpKick();

			if (!backKick && angle > 0.7D || backKick && angle < -0.7D) {
				DamageSource source;
				LivingEntity rider = horse.getControllingPassenger();
				if (rider != null) {
					source = EnvironmentalDamageTypes.ridingZebra(horse.level(), horse, rider);
				} else {
					source = horse.damageSources().mobAttack(horse);
				}
				float damage = (float) horse.getAttributeValue(Attributes.ATTACK_DAMAGE);
				float knockback = (float) horse.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

				if (jumpkick) {
					float f = this.getJumpStrength();
					if (rider != null && rider.zza <= 0.0F)
						f *= 0.5F;
					damage += f * 6.0F;
					knockback = knockback * 0.8F + f * 1.1F;
				} else if (backKick) {
					if (!softBackKick)
						damage += 2.0F;
					else if (damage > 1.0F)
						damage = 1.0F;
					knockback *= 1.2F;
				} else {
					knockback *= 0.8F;
				}

				boolean flag = living.hurt(source, (int) damage);

				if (flag) {
					horse.doEnchantDamageEffects(horse, living);
					if (!backKick)
						living.knockback(knockback, x, z);
					else
						living.knockback(knockback, -x, -z);
				}
			}
		}
	}

	// TODO: This only works sometimes
	default void flingPassengers(boolean backFling) {
		AbstractHorse horse = (AbstractHorse) this;

		float rot = horse.getVisualRotationYInDegrees();
		float x = Mth.sin(rot * Mth.DEG_TO_RAD);
		float z = -Mth.cos(rot * Mth.DEG_TO_RAD);

		for (int i = horse.getPassengers().size() - 1; i >= 0; --i) {
			Entity passenger = horse.getPassengers().get(i);
			passenger.stopRiding();
			Vec3 vec3 = (new Vec3(x, 0.0D, z)).scale(0.8F);
			if (backFling)
				vec3 = vec3.scale(-1.0D);
			passenger.push(vec3.x, 0.8D, vec3.z);
			passenger.hurtMarked = true;
		}
	}

	default void handleTravel(boolean wasJumping) {
		AbstractHorse horse = (AbstractHorse) this;

		if (!wasJumping && horse.isJumping() && horse.getControllingPassenger() instanceof Player)
			Environmental.PLAY.sendToServer(new C2SZebraJumpMessage((float) horse.getDeltaMovement().y));
	}

	default void handleLeashed(Entity entity) {
		AbstractHorse horse = (AbstractHorse) this;

		if (!horse.isTamed() && entity instanceof LivingEntity)
			horse.setTarget((LivingEntity) entity);
	}

	default boolean canDoIdleAnimation() {
		AbstractHorse horse = (AbstractHorse) this;

		return !this.isKicking() && horse.getMoveControl().getSpeedModifier() <= 1.0D;
	}

	default void handleStepSound(BlockPos pos, BlockState state) {
		AbstractHorse horse = (AbstractHorse) this;

		if (!state.liquid()) {
			BlockState blockstate = horse.level().getBlockState(pos.above());
			SoundType soundtype = state.getSoundType(horse.level(), pos, horse);
			if (blockstate.is(Blocks.SNOW)) {
				soundtype = blockstate.getSoundType(horse.level(), pos, horse);
			}

			if ((horse.isVehicle() || horse.getMoveControl().getSpeedModifier() > 1.6D) && this.canGallop()) {
				this.setGallopSoundCounter(this.getGallopSoundCounter() + 1);
				if (this.getGallopSoundCounter() > 5 && this.getGallopSoundCounter() % 3 == 0) {
					this.playZebroidGallopSound(soundtype);
				} else if (this.getGallopSoundCounter() <= 5) {
					horse.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
				}
			} else if (soundtype == SoundType.WOOD) {
				horse.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			} else {
				horse.playSound(SoundEvents.HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			}
		}
	}

	default void handlePositionRider(Entity rider, Entity.MoveFunction function) {
		AbstractHorse horse = (AbstractHorse) this;

		float f = Mth.sin(horse.yBodyRot * Mth.DEG_TO_RAD);
		float f1 = Mth.cos(horse.yBodyRot * Mth.DEG_TO_RAD);
		float f2 = 0.0F;
		float f3 = 0.0F;
		float nostandanim = 1.0F - horse.standAnimO;

		if (horse.standAnimO > 0.0F) {
			f2 += 0.7F * horse.standAnimO;
			f3 += 0.15F * horse.standAnimO;
			if (rider instanceof LivingEntity living)
				living.yBodyRot = horse.yBodyRot;
		}

		if (this.getBackKickAnimO() > 0.0F) {
			float rot = this.getBackKickBodyRot(0.0F);
			f2 += -0.2F * rot * nostandanim;
			f3 += 0.15F * rot * nostandanim;
		} else if (this.getFrontKickAnimO() > 0.0F) {
			float rot = this.getFrontKickBodyRot(0.0F);
			f2 += 0.2F * rot * nostandanim;
			f3 += 0.15F * rot * nostandanim;
		}

		function.accept(rider, horse.getX() + (double) (f2 * f), horse.getY() + horse.getPassengersRidingOffset() + rider.getMyRidingOffset() + (double) f3, horse.getZ() - (double) (f2 * f1));
	}

	static float smoothAnim(float min, float max, float progress) {
		return 1F - Mth.square((progress - max) / (max - min));
	}
}