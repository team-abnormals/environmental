package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.blueprint.core.api.EggLayer;
import com.teamabnormals.environmental.common.entity.ai.goal.DuckAvoidEntityGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.DucksInARowGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.DuckSwimGoal;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Duck extends Animal implements EggLayer {
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

	@Nullable
	private Duck rowHead;
	@Nullable
	private Duck rowTail;

	public Duck(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	private static final Predicate<Entity> AVOID_PLAYERS = (entity) -> !entity.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new DucksInARowGoal(this, 1.1D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(EnvironmentalItemTags.DUCK_FOOD), false));
		this.goalSelector.addGoal(5, new DuckAvoidEntityGoal<>(this, Player.class, 2.0F, 1.0D, 1.0D, AVOID_PLAYERS::test));
		this.goalSelector.addGoal(6, new DuckSwimGoal(this));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
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

	@Override
	public double getFluidJumpThreshold() {
		return this.isBaby() ? 0.2D : 0.4D;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public void leaveRow() {
		if (this.rowHead != null) {
			this.rowHead.rowTail = null;
		}

		this.rowHead = null;
	}

	public void joinRow(Duck p_30767_) {
		this.rowHead = p_30767_;
		this.rowHead.rowTail = this;
	}

	public boolean hasRow() {
		return this.rowTail != null;
	}

	public boolean inRow() {
		return this.rowHead != null;
	}

	@Nullable
	public Duck getRowHead() {
		return this.rowHead;
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		if (!this.level().isClientSide() && this.isDeadOrDying()) {
			this.leaveRow();
		}
		super.remove(reason);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		double d0 = this.getFluidHeight(FluidTags.WATER);
		boolean flag = !this.isBaby() && d0 > 0.3D || this.isBaby() && d0 > 0.15D;

		// Wing rotation
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround() || this.wasTouchingWater ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround() && !flag && this.wingRotDelta < 1.0F) {
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
		if (!this.onGround() && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		if (!this.level().isClientSide) {
			// Egg laying
			if (this.isAlive() && !this.isBaby() && !this.wasTouchingWater && !this.isBirdJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(this.getEggLayingSound(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(this.getEggItem());
				this.timeUntilNextEgg = this.getNextEggTime(this.random);
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

	public static boolean checkDuckSpawnRules(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return worldIn.getBlockState(pos.below()).is(EnvironmentalBlockTags.WATER_ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(worldIn, pos);
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
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
		return Ingredient.of(EnvironmentalItemTags.DUCK_FOOD).test(stack);
	}

	@Override
	public int getExperienceReward() {
		return this.isBirdJockey() ? 10 : super.getExperienceReward();
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
		return this.isBirdJockey();
	}

	@Override
	public void positionRider(Entity passenger, Entity.MoveFunction function) {
		super.positionRider(passenger, function);
		float f = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
		function.accept(passenger, this.getX() + (double) (0.1F * f), this.getY(0.15D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double) (0.1F * f1));
		if (passenger instanceof LivingEntity living) {
			living.yBodyRot = this.yBodyRot;
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		return EnvironmentalEntityTypes.DUCK.get().create(world);
	}

	@Override
	public int getEggTimer() {
		return this.timeUntilNextEgg;
	}

	@Override
	public void setEggTimer(int time) {
		this.timeUntilNextEgg = time;
	}

	@Override
	public boolean isBirdJockey() {
		return this.duckJockey;
	}

	@Override
	public Item getEggItem() {
		return EnvironmentalItems.DUCK_EGG.get();
	}

	@Override
	public int getNextEggTime(RandomSource rand) {
		return rand.nextInt(6000) + 6000;
	}

	@Override
	public SoundEvent getEggLayingSound() {
		return EnvironmentalSoundEvents.DUCK_EGG.get();
	}
}
