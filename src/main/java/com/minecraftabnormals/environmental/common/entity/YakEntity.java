package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.common.item.YakPantsItem;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class YakEntity extends AnimalEntity implements IForgeShearable, IShearable, IAngerable {
	private static final DataParameter<Boolean> SHEARED = EntityDataManager.defineId(YakEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(YakEntity.class, DataSerializers.INT);
	private static final RangedInteger ANGER_RANGE = TickRangeConverter.rangeOfSeconds(20, 39);

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
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, this.eatGrassGoal);
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new ResetAngerGoal<>(this, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHEARED, false);
		this.entityData.define(ANGER_TIME, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Sheared", this.getSheared());
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setSheared(compound.getBoolean("Sheared"));
		this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
	}

	@Override
	protected void customServerAiStep() {
		this.grassEatTimer = this.eatGrassGoal.getEatAnimationTick();
		ModifiableAttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.isAngry()) {
			if (!this.isBaby() && !modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
				modifiableattributeinstance.addTransientModifier(ATTACKING_SPEED_BOOST);
			}
		} else if (modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
			modifiableattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
		}

		this.updatePersistentAnger((ServerWorld) this.level, true);

		if (this.isAngry()) {
			this.lastHurtByPlayerTime = this.tickCount;
		}
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		if (this.level.isClientSide && this.grassEatTimer > 0) {
			this.grassEatTimer--;
		}
		super.aiStep();
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 10) {
			this.grassEatTimer = 40;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
			p_230254_1_.playSound(EnvironmentalSounds.ENTITY_YAK_MILK.get(), 1.0F, 1.0F);
			ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, p_230254_1_, Items.MILK_BUCKET.getDefaultInstance());
			p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else {
			return super.mobInteract(p_230254_1_, p_230254_2_);
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
			return this.grassEatTimer > 0 ? ((float) Math.PI / 5F) : MathHelper.lerp(partialTicks, this.xRotO, this.xRot) * ((float) Math.PI / 180F);
		}
	}

	@Override
	public void ate() {
		this.setSheared(false);
		if (this.isBaby()) {
			this.ageUp(60);
		}
	}

	public boolean getSheared() {
		return this.entityData.get(SHEARED);
	}

	public void setSheared(boolean sheared) {
		this.entityData.set(SHEARED, sheared);
	}

	public boolean readyForShearing() {
		return this.isAlive() && !this.getSheared() && !this.isBaby();
	}

	@Override
	public boolean isShearable(ItemStack item, World world, BlockPos pos) {
		return readyForShearing();
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return AnimalEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 3.0F).add(Attributes.ATTACK_KNOCKBACK, 1.2F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSounds.ENTITY_YAK_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSounds.ENTITY_YAK_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSounds.ENTITY_YAK_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected float getVoicePitch() {
		return 0.8F;
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
		if (!world.isClientSide) {
			this.setSheared(true);
			if (!(player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof YakPantsItem))
				this.setTarget(player);
			int i = 4 + this.random.nextInt(12);

			java.util.List<ItemStack> items = new java.util.ArrayList<>();
			for (int j = 0; j < i; ++j) {
				items.add(new ItemStack(EnvironmentalItems.YAK_HAIR.get()));
			}
			return items;
		}
		return java.util.Collections.emptyList();
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		return EnvironmentalEntities.YAK.get().create(world);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isBaby() ? size.height * 0.95F : 1.3F;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(EnvironmentalItems.YAK_SPAWN_EGG.get());
	}

	@Override
	public void shear(SoundCategory category) {
		this.level.playSound((PlayerEntity) null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
		this.setSheared(true);
		int i = 4 + this.random.nextInt(12);

		for (int j = 0; j < i; ++j) {
			ItemEntity itementity = this.spawnAtLocation(EnvironmentalItems.YAK_HAIR.get(), 1);
			if (itementity != null) {
				itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double) (this.random.nextFloat() * 0.05F), (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
			}
		}

	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(ANGER_TIME, time);
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.lastHurtBy;
	}

	@Override
	public void setPersistentAngerTarget(UUID target) {
		this.lastHurtBy = target;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_RANGE.randomValue(this.random));
	}

	public boolean isPreventingPlayerRest(PlayerEntity p_230292_1_) {
		return this.isAngryAt(p_230292_1_);
	}
}