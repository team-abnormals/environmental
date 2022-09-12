package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.entity.ai.goal.DuckSwimGoal;
import com.teamabnormals.environmental.core.other.EnvironmentalTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Duck extends Animal {
	private static final EntityDataAccessor<Integer> EATING = SynchedEntityData.defineId(Duck.class, EntityDataSerializers.INT);
	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;
	private float headLean;
	private float prevHeadLean;
	public int timeUntilNextEgg = this.random.nextInt(6000) + 6000;
	public boolean duckJockey;

	public Duck(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new DuckSwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(EnvironmentalTags.Items.DUCK_FOOD), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(EATING, 0);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		double d0 = this.getFluidHeight(FluidTags.WATER);
		boolean flag = !this.isBaby() && d0 > 0.3D || this.isBaby() && d0 > 0.15D;

		// Wing rotation
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround || this.wasTouchingWater ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && !flag && this.wingRotDelta < 1.0F) {
			this.wingRotDelta = 1.0F;
		}
		this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
		this.wingRotation += this.wingRotDelta * 2.0F;

		// Eating animation
		this.prevHeadLean = this.headLean;
		if (this.isEating()) {
			this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
			if (this.headLean > 1.0F) {
				this.headLean = 1.0F;
			}
		} else {
			this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
			if (this.headLean < 0.0F) {
				this.headLean = 0.0F;
			}
		}

		// Motion
		Vec3 vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		if (!this.level.isClientSide) {
			// Egg laying
			if (this.isAlive() && !this.isBaby() && !this.wasTouchingWater && !this.isDuckJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(EnvironmentalItems.DUCK_EGG.get());
				this.timeUntilNextEgg = this.getRandomNextEggTime(this.random);
			}

			// Eating
			if (this.isEating()) {
				if (this.wasTouchingWater && !this.isInLove())
					this.setEatingTime(this.getEatingTime() - 1);
				else
					this.setEatingTime(0);
			} else if (!this.isInLove() && this.random.nextInt(300) == 0 && this.wasTouchingWater) {
				this.setEatingTime(40 + this.random.nextInt(20));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getWingRotation(float partialTicks) {
		float f = Mth.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = Mth.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (Mth.sin(f) + 1.0F) * f1;
	}

	@OnlyIn(Dist.CLIENT)
	public float getHeadLean(float partialTicks) {
		return Mth.lerp(partialTicks, this.prevHeadLean, this.headLean);
	}

	public boolean isEating() {
		return this.getEatingTime() > 0;
	}

	public int getEatingTime() {
		return this.entityData.get(EATING);
	}

	public void setEatingTime(int eatingTimeIn) {
		this.entityData.set(EATING, eatingTimeIn);
	}

	//TODO: water_animal_spawnable_on
	public static boolean canDuckSpawn(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
		return (worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) || worldIn.getFluidState(pos.below()).is(FluidTags.WATER)) && worldIn.getRawBrightness(pos, 0) > 8;
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSoundEvents.DUCK_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSoundEvents.DUCK_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.DUCK_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSoundEvents.DUCK_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(EnvironmentalTags.Items.DUCK_FOOD).test(stack);
	}

	@Override
	protected int getExperienceReward(Player player) {
		return this.isDuckJockey() ? 10 : super.getExperienceReward(player);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.duckJockey = compound.getBoolean("IsDuckJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}

	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsDuckJockey", this.duckJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return this.isDuckJockey();
	}

	@Override
	public void positionRider(Entity passenger) {
		super.positionRider(passenger);
		float f = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
		passenger.setPos(this.getX() + (double) (0.1F * f), this.getY(0.5D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double) (0.1F * f1));
		if (passenger instanceof LivingEntity) {
			((LivingEntity) passenger).yBodyRot = this.yBodyRot;
		}

	}

	public boolean isDuckJockey() {
		return this.duckJockey;
	}

	public void setDuckJockey(boolean jockey) {
		this.duckJockey = jockey;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		return EnvironmentalEntityTypes.DUCK.get().create(world);
	}

	public int getRandomNextEggTime(Random rand) {
		return rand.nextInt(6000) + 6000;
	}
}
