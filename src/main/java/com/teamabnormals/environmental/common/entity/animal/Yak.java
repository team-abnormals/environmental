package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.item.YakPantsItem;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Yak extends Animal implements IForgeShearable, Shearable, NeutralMob {
	private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Yak.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(Yak.class, EntityDataSerializers.INT);
	private static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);

	private static final UUID SPEED_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACKING_SPEED_BOOST = new AttributeModifier(SPEED_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);

	private EatBlockGoal eatGrassGoal;
	private UUID lastHurtBy;
	private int grassEatTimer;

	public Yak(EntityType<? extends Yak> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.eatGrassGoal = new EatBlockGoal(this);
		this.goalSelector.addGoal(0, new FloatGoal(this));
//		this.goalSelector.addGoal(1, new YakChargeGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(EnvironmentalItemTags.YAK_FOOD), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, this.eatGrassGoal);
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHEARED, false);
		this.entityData.define(ANGER_TIME, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Sheared", this.getSheared());
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSheared(compound.getBoolean("Sheared"));
		this.readPersistentAngerSaveData(this.level, compound);
	}

	@Override
	protected void customServerAiStep() {
		this.grassEatTimer = this.eatGrassGoal.getEatAnimationTick();
		AttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.isAngry()) {
			if (!this.isBaby() && !modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
				modifiableattributeinstance.addTransientModifier(ATTACKING_SPEED_BOOST);
			}
		} else if (modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
			modifiableattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
		}

		this.updatePersistentAnger((ServerLevel) this.level, true);

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
	public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
			p_230254_1_.playSound(EnvironmentalSoundEvents.YAK_MILK.get(), 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, p_230254_1_, Items.MILK_BUCKET.getDefaultInstance());
			p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			return super.mobInteract(p_230254_1_, p_230254_2_);
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(EnvironmentalItemTags.YAK_FOOD).test(stack);
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
			return ((float) Math.PI / 5F) + 0.22F * Mth.sin((((float) (this.grassEatTimer - 4) - partialTicks) / 32.0F) * 28.7F);
		} else {
			return this.grassEatTimer > 0 ? ((float) Math.PI / 5F) : Mth.lerp(partialTicks, this.xRotO, this.getXRot()) * ((float) Math.PI / 180F);
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
	public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
		return readyForShearing();
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 3.0F).add(Attributes.ATTACK_KNOCKBACK, 1.2F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSoundEvents.YAK_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSoundEvents.YAK_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.YAK_DEATH.get();
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
	public float getVoicePitch() {
		return 0.8F;
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
		if (!world.isClientSide) {
			this.setSheared(true);
			if (!player.getAbilities().instabuild && !(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof YakPantsItem))
				this.setTarget(player);
			int i = 4 + this.random.nextInt(12);

			List<ItemStack> items = new ArrayList<>();
			for (int j = 0; j < i; ++j) {
				items.add(new ItemStack(EnvironmentalItems.YAK_HAIR.get()));
			}
			return items;
		}
		return Collections.emptyList();
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		return EnvironmentalEntityTypes.YAK.get().create(world);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
		return this.isBaby() ? size.height * 0.95F : 1.3F;
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(EnvironmentalItems.YAK_SPAWN_EGG.get());
	}

	@Override
	public void shear(SoundSource category) {
		this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
		this.setSheared(true);
		int i = 4 + this.random.nextInt(12);

		for (int j = 0; j < i; ++j) {
			ItemEntity itementity = this.spawnAtLocation(EnvironmentalItems.YAK_HAIR.get(), 1);
			if (itementity != null) {
				itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
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
		this.setRemainingPersistentAngerTime(ANGER_RANGE.sample(this.random));
	}
}