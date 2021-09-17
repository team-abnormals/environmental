package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import com.minecraftabnormals.environmental.common.entity.goals.DuckSwimGoal;
import com.minecraftabnormals.environmental.common.entity.goals.LayEggInNestGoal;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTags;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class DuckEntity extends AnimalEntity implements IEggLayingEntity {
	private static final DataParameter<Integer> EATING = EntityDataManager.defineId(DuckEntity.class, DataSerializers.INT);
	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;
	private float headLean;
	private float prevHeadLean;
	public int timeUntilNextEgg = this.random.nextInt(6000) + 6000;
	public boolean duckJockey;

	public DuckEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new DuckSwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new LayEggInNestGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, Ingredient.of(EnvironmentalTags.Items.DUCK_FOOD)));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(EATING, 0);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
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
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
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
		Vector3d vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		if (!this.level.isClientSide) {
			// Egg laying
			if (this.isAlive() && !this.isBaby() && !this.wasTouchingWater && !this.isDuckJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
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
		float f = MathHelper.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@OnlyIn(Dist.CLIENT)
	public float getHeadLean(float partialTicks) {
		return MathHelper.lerp(partialTicks, this.prevHeadLean, this.headLean);
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

	public static boolean canDuckSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return (worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) || worldIn.getFluidState(pos.below()).is(FluidTags.WATER)) && worldIn.getRawBrightness(pos, 0) > 8;
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSounds.DUCK_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSounds.DUCK_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSounds.DUCK_DEATH.get();
	}

	@Override
	public SoundEvent getEggLayingSound() {
		return EnvironmentalSounds.DUCK_EGG.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSounds.DUCK_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(EnvironmentalTags.Items.DUCK_FOOD).test(stack);
	}

	@Override
	protected int getExperienceReward(PlayerEntity player) {
		return this.isDuckJockey() ? 10 : super.getExperienceReward(player);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.duckJockey = compound.getBoolean("IsDuckJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}

	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
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
		float f = MathHelper.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = MathHelper.cos(this.yBodyRot * ((float) Math.PI / 180F));
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
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		return EnvironmentalEntities.DUCK.get().create(world);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(EnvironmentalItems.DUCK_SPAWN_EGG.get());
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
		return this.isDuckJockey();
	}

	@Override
	public Item getEggItem() {
		return EnvironmentalItems.DUCK_EGG.get();
	}

	@Override
	public int getNextEggTime(Random rand) {
		return rand.nextInt(6000) + 6000;
	}
}
