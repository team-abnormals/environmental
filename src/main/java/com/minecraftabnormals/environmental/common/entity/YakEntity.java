package com.minecraftabnormals.environmental.common.entity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;

public class YakEntity extends AnimalEntity implements IForgeShearable {
	private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(YakEntity.class, DataSerializers.BOOLEAN);
	private int timer;
	private EatGrassGoal eatGrassGoal;

	public YakEntity(EntityType<? extends YakEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.eatGrassGoal = new EatGrassGoal(this);
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp(YakEntity.class));
//		this.goalSelector.addGoal(1, new YakChargeGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, this.eatGrassGoal);
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SHEARED, false);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("Sheared", this.getSheared());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSheared(compound.getBoolean("Sheared"));
	}

	@Override
	protected void updateAITasks() {
		this.timer = this.eatGrassGoal.getEatingGrassTimer();
		super.updateAITasks();
	}

	@Override
	public void livingTick() {
		if (this.world.isRemote) {
			this.timer = Math.max(0, this.timer - 1);
		}

		super.livingTick();
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			this.timer = 40;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (itemstack.getItem() == Items.BUCKET && !this.isChild()) {
			p_230254_1_.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = DrinkHelper.func_241445_a_(itemstack, p_230254_1_, Items.MILK_BUCKET.getDefaultInstance());
			p_230254_1_.setHeldItem(p_230254_2_, itemstack1);
			return ActionResultType.func_233537_a_(this.world.isRemote);
		} else {
			return super.func_230254_b_(p_230254_1_, p_230254_2_);
		}
	}

//	public float getHeadRotationPointY(float partialTicks) {
//		if (this.timer <= 0) {
//			return 0.0F;
//		} else if (this.timer >= 4 && this.timer <= 36) {
//			return 1.0F;
//		} else {
//			return this.timer < 4 ? ((float) this.timer - partialTicks) / 4.0F : -((float) (this.timer - 40) - partialTicks) / 4.0F;
//		}
//	}
//
//	public float getHeadRotationAngleX(float partialTicks) {
//		if (this.timer > 4 && this.timer <= 36) {
//			float f = ((float) (this.timer - 4) - partialTicks) / 32.0F;
//			return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
//		} else {
//			return this.timer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * ((float) Math.PI / 180F);
//		}
//	}

	public boolean getSheared() {
		return this.dataManager.get(SHEARED);
	}

	public void setSheared(boolean sheared) {
		this.dataManager.set(SHEARED, sheared);
	}

	public boolean isShearable() {
		return this.isAlive() && !this.getSheared() && !this.isChild();
	}

	@Override
	public boolean isShearable(ItemStack item, World world, BlockPos pos) {
		return isShearable();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected float getSoundPitch() {
		return 0.8F;
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
		world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
		if (!world.isRemote) {
			this.setSheared(true);
			int i = 6 + this.rand.nextInt(6);

			java.util.List<ItemStack> items = new java.util.ArrayList<>();
			for (int j = 0; j < i; ++j) {
				items.add(new ItemStack(EnvironmentalItems.YAK_HAIR.get()));
			}
			return items;
		}
		return java.util.Collections.emptyList();
	}

	@Override
	public YakEntity createChild(AgeableEntity ageableEntity) {
		return EnvironmentalEntities.YAK.get().create(this.world);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isChild() ? size.height * 0.95F : 1.3F;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(EnvironmentalItems.YAK_SPAWN_EGG.get());
	}
}