package com.minecraftabnormals.environmental.common.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.item.YakPantsItem;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

public class YakEntity extends AnimalEntity implements IForgeShearable, IShearable, IAngerable {
	private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(YakEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(YakEntity.class, DataSerializers.VARINT);
	private static final RangedInteger ANGER_RANGE = TickRangeConverter.convertRange(20, 39);

	private static final UUID SPEED_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACKING_SPEED_BOOST = new AttributeModifier(SPEED_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);

	private EatGrassGoal eatGrassGoal;
	private UUID lastHurtBy;
	private int grassEatTimer;

	public YakEntity(EntityType<? extends YakEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.eatGrassGoal = new EatGrassGoal(this);
		this.goalSelector.addGoal(0, new SwimGoal(this));
//		this.goalSelector.addGoal(1, new YakChargeGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, this.eatGrassGoal);
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
		this.targetSelector.addGoal(2, new ResetAngerGoal<>(this, true));
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SHEARED, false);
		this.dataManager.register(ANGER_TIME, 0);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("Sheared", this.getSheared());
		this.writeAngerNBT(compound);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSheared(compound.getBoolean("Sheared"));
		this.readAngerNBT((ServerWorld) this.world, compound);
	}

	@Override
	protected void updateAITasks() {
		this.grassEatTimer = this.eatGrassGoal.getEatingGrassTimer();
		ModifiableAttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.func_233678_J__()) {
			if (!this.isChild() && !modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
				modifiableattributeinstance.applyNonPersistentModifier(ATTACKING_SPEED_BOOST);
			}
		} else if (modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
			modifiableattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
		}

		this.func_241359_a_((ServerWorld) this.world, true);

		if (this.func_233678_J__()) {
			this.recentlyHit = this.ticksExisted;
		}
		super.updateAITasks();
	}

	@Override
	public void livingTick() {
		if (this.world.isRemote && this.grassEatTimer > 0) {
			this.grassEatTimer--;
		}
		super.livingTick();
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			this.grassEatTimer = 40;
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

	public float getHeadEatingOffset(float partialTicks) {
		if (this.grassEatTimer <= 0) {
			return 0.0F;
		} else if (this.grassEatTimer >= 4 && this.grassEatTimer <= 36) {
			return 1.0F;
		} else {
			return this.grassEatTimer < 4 ? ((float) this.grassEatTimer - partialTicks) / 4.0F : -((float) (this.grassEatTimer - 40) - partialTicks) / 4.0F;
		}
	}

	public float getHeadPitch(float partialTicks) {
		if (this.grassEatTimer > 4 && this.grassEatTimer <= 36) {
			return ((float) Math.PI / 5F) + 0.22F * MathHelper.sin((((float) (this.grassEatTimer - 4) - partialTicks) / 32.0F) * 28.7F);
		} else {
			return this.grassEatTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * ((float) Math.PI / 180F);
		}
	}
	
	@Override
	public void eatGrassBonus() {
		this.setSheared(false);
		if (this.isChild()) {
			this.addGrowth(60);
		}	
	}

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
		return AnimalEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 25.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0F).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.2F);
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
			if (!(player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof YakPantsItem))
				this.setAttackTarget(player);
			int i = 4 + this.rand.nextInt(12);

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

	@Override
	public void shear(SoundCategory category) {
		this.world.playMovingSound((PlayerEntity) null, this, SoundEvents.ENTITY_SHEEP_SHEAR, category, 1.0F, 1.0F);
		this.setSheared(true);
		int i = 4 + this.rand.nextInt(12);

		for (int j = 0; j < i; ++j) {
			ItemEntity itementity = this.entityDropItem(EnvironmentalItems.YAK_HAIR.get(), 1);
			if (itementity != null) {
				itementity.setMotion(itementity.getMotion().add((double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double) (this.rand.nextFloat() * 0.05F), (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
			}
		}

	}

	@Override
	public int getAngerTime() {
		return this.dataManager.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int time) {
		this.dataManager.set(ANGER_TIME, time);
	}

	@Override
	public UUID getAngerTarget() {
		return this.lastHurtBy;
	}

	@Override
	public void setAngerTarget(UUID target) {
		this.lastHurtBy = target;
	}

	@Override
	public void func_230258_H__() {
		this.setAngerTime(ANGER_RANGE.func_233018_a_(this.rand));
	}

	public boolean func_230292_f_(PlayerEntity p_230292_1_) {
		return this.func_233680_b_(p_230292_1_);
	}
}