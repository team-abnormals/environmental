package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class Zebra extends AbstractHorse {

	public Zebra(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void randomizeAttributes(RandomSource random) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.generateRandomMaxHealth(random));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed(random));
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength(random));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE, 0.5D);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.hasControllingPassenger()) {
			List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2F), living -> living.isAlive() && !living.is(this) && !living.isSpectator() && !living.isPassenger());
			if (!nearby.isEmpty()) {
				for (LivingEntity living : nearby) {
					Vec3 attackAngleVector = living.position().subtract(this.position()).normalize();
					attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);
					float x = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
					float z = -Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
					double angle = attackAngleVector.dot(this.getViewVector(1.0F));
					if (angle > 0.75D) {
						this.makeMad();
						this.getControllingPassenger().doHurtTarget(living);
						living.hurt(DamageSource.GENERIC, 1.0F);
						living.knockback(0.25F, x, z);
					} else if (angle < 0.25D) {
						this.makeMad();
						this.getControllingPassenger().doHurtTarget(living);
						living.hurt(DamageSource.GENERIC, 1.0F);
						living.knockback(1.5F, -x, -z);
					}
				}
			}
		}
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
	protected SoundEvent getAmbientSound() {
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
		super.getHurtSound(source);
		return SoundEvents.HORSE_HURT;
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
