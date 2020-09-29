package com.minecraftabnormals.environmental.common.entity;

import java.util.Random;

import com.minecraftabnormals.environmental.common.entity.goals.DuckEatItemsGoal;
import com.minecraftabnormals.environmental.common.entity.goals.DuckLayEggInNestGoal;
import com.minecraftabnormals.environmental.common.entity.goals.DuckSwimGoal;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTags;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class DuckEntity extends AnimalEntity {
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;
	public int timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
	public boolean chickenJockey;

	public DuckEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new DuckSwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new DuckLayEggInNestGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, Ingredient.fromTag(EnvironmentalTags.Items.DUCK_BREEDING_ITEMS)));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public void livingTick() {
		super.livingTick();
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float)((double)this.destPos + (double)(this.onGround || this.inWater ? -1 : 4) * 0.3D);
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && !this.inWater && this.wingRotDelta < 1.0F) {
			this.wingRotDelta = 1.0F;
		}

		this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
		Vector3d vector3d = this.getMotion();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
		}

		this.wingRotation += this.wingRotDelta * 2.0F;
		if (!this.world.isRemote && this.isAlive() && !this.isChild() && !this.inWater && !this.isChickenJockey() && --this.timeUntilNextEgg <= 0) {
			this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.entityDropItem(EnvironmentalItems.DUCK_EGG.get());
			this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getWingRotation(float partialTicks) {
		float f = MathHelper.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	public static boolean canDuckSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return (worldIn.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK) || worldIn.getFluidState(pos.down()).isTagged(FluidTags.WATER)) && worldIn.getLightSubtracted(pos, 0) > 8;
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_CHICKEN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CHICKEN_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return Ingredient.fromTag(EnvironmentalTags.Items.DUCK_BREEDING_ITEMS).test(stack);
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return this.isChickenJockey() ? 10 : super.getExperiencePoints(player);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.chickenJockey = compound.getBoolean("IsChickenJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}

	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsChickenJockey", this.chickenJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return this.isChickenJockey();
	}

	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		float f = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
		float f1 = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
		passenger.setPosition(this.getPosX() + (double)(0.1F * f), this.getPosYHeight(0.5D) + passenger.getYOffset() + 0.0D, this.getPosZ() - (double)(0.1F * f1));
		if (passenger instanceof LivingEntity) {
			((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
		}

	}

	public boolean isChickenJockey() {
		return this.chickenJockey;
	}

	public void setChickenJockey(boolean jockey) {
		this.chickenJockey = jockey;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public DuckEntity createChild(AgeableEntity ageable) {
		return EnvironmentalEntities.DUCK.get().create(this.world);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(EnvironmentalItems.DUCK_SPAWN_EGG.get());
	}
}
