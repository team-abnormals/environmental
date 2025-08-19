package com.teamabnormals.environmental.common.entity.animal.zebroid;

import com.teamabnormals.environmental.common.entity.ai.goal.zebroid.ZebroidPanicGoal;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Markings;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.IntUnaryOperator;

public class Zorse extends AbstractUnchestedZebroid implements VariantHolder<Variant> {
	public static final float MIN_DAMAGE = generateAttackDamage(value -> 0);
	public static final float MAX_DAMAGE = generateAttackDamage(value -> value - 1);

	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("6CEF6F73-493A-491E-B49C-BA4B2F8ABEB6");
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(Zorse.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STRIPE_OPACITY = SynchedEntityData.defineId(Zorse.class, EntityDataSerializers.INT);

	public Zorse(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new ZebroidPanicGoal<>(this, 1.8D));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.7D));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractHorse.createBaseHorseAttributes().add(Attributes.ATTACK_DAMAGE).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	protected void randomizeAttributes(RandomSource random) {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(generateMaxHealth(random::nextInt));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(generateSpeed(random::nextDouble));
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength(random::nextDouble));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(generateAttackDamage(random::nextInt));
	}

	private static float generateAttackDamage(IntUnaryOperator random) {
		return 1.0F + random.applyAsInt(2);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
		this.entityData.define(STRIPE_OPACITY, 100);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", this.getTypeVariant());
		compound.putInt("StripeOpacity", this.getStripeOpacity());
		if (!this.inventory.getItem(1).isEmpty()) {
			compound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setTypeVariant(compound.getInt("Variant"));
		this.setStripeOpacity(compound.getInt("StripeOpacity"));
		if (compound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}

		this.updateContainerEquipment();
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}

	private void setArmor(ItemStack stack) {
		this.setItemSlot(EquipmentSlot.CHEST, stack);
		this.setDropChance(EquipmentSlot.CHEST, 0.0F);
	}

	private void setTypeVariant(int p_30737_) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, p_30737_);
	}

	private int getTypeVariant() {
		return this.entityData.get(DATA_ID_TYPE_VARIANT);
	}

	public void setVariantAndMarkings(Variant variant, Markings markings) {
		this.setTypeVariant(variant.getId() & 255 | markings.getId() << 8 & '\uff00');
	}

	public Variant getVariant() {
		return Variant.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(Variant variant) {
		this.setTypeVariant(variant.getId() & 255 | this.getTypeVariant() & -256);
	}

	public Markings getMarkings() {
		return Markings.byId((this.getTypeVariant() & '\uff00') >> 8);
	}

	public int getStripeOpacity() {
		return this.entityData.get(STRIPE_OPACITY);
	}

	public void setStripeOpacity(int time) {
		this.entityData.set(STRIPE_OPACITY, time);
	}

	public void randomizeStripeOpacity(RandomSource random) {
		this.setStripeOpacity(33 + random.nextInt(68));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return !this.canDoIdleAnimation() ? EnvironmentalSoundEvents.ZORSE_AMBIENT.get() : null;
	}

	@Override
	protected SoundEvent getAngrySound() {
		return !this.canDoIdleAnimation() ? EnvironmentalSoundEvents.ZORSE_ANGRY.get() : null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.ZORSE_DEATH.get();
	}

	@Override
	protected SoundEvent getEatingSound() {
		return EnvironmentalSoundEvents.ZORSE_EAT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		if (!this.isKicking())
			super.getHurtSound(source);
		return EnvironmentalSoundEvents.ZORSE_HURT.get();
	}

	@Override
	protected void playGallopSound(SoundType soundType) {
		super.playGallopSound(soundType);

		ItemStack stack = this.inventory.getItem(1);
		if (isArmor(stack))
			stack.onHorseArmorTick(level(), this);
	}

	@Override
	protected void updateContainerEquipment() {
		if (!this.level().isClientSide) {
			super.updateContainerEquipment();
			this.setArmorEquipment(this.inventory.getItem(1));
			this.setDropChance(EquipmentSlot.CHEST, 0.0F);
		}
	}

	private void setArmorEquipment(ItemStack stack) {
		this.setArmor(stack);
		if (!this.level().isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isArmor(stack)) {
				int i = ((HorseArmorItem) stack.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", i, AttributeModifier.Operation.ADDITION));
				}
			}
		}
	}

	@Override
	public void containerChanged(Container container) {
		ItemStack itemstack = this.getArmor();
		super.containerChanged(container);
		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean canWearArmor() {
		return true;
	}

	@Override
	public boolean isArmor(ItemStack stack) {
		return stack.is(Items.LEATHER_HORSE_ARMOR);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		RandomSource random = level.getRandom();
		this.setVariantAndMarkings(Util.getRandom(Variant.values(), random), Util.getRandom(Markings.values(), random));
		this.randomizeStripeOpacity(level.getRandom());
		return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Zorse zorse = (Zorse) otherParent;
		Zorse zorse1 = EnvironmentalEntityTypes.ZORSE.get().create(level);
		if (zorse1 != null) {
			int i = this.random.nextInt(9);
			Variant variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = zorse.getVariant();
			} else {
				variant = Util.getRandom(Variant.values(), this.random);
			}

			int j = this.random.nextInt(5);
			Markings markings;
			if (j < 2) {
				markings = this.getMarkings();
			} else if (j < 4) {
				markings = zorse.getMarkings();
			} else {
				markings = Util.getRandom(Markings.values(), this.random);
			}

			zorse1.randomizeStripeOpacity(level.getRandom());
			zorse1.setVariantAndMarkings(variant, markings);
			this.setOffspringAttributes(otherParent, zorse1);
		}
		return zorse1;
	}

	@Override
	public void setOffspringAttributes(AgeableMob otherParent, AbstractHorse child) {
		super.setOffspringAttributes(otherParent, child);
		this.setOffspringAttribute(otherParent, child, Attributes.ATTACK_DAMAGE, MIN_DAMAGE, MAX_DAMAGE);
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() - 0.1D;
	}
}