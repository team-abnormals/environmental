package com.teamabnormals.environmental.common.entity.animal.yak;

import com.mojang.serialization.Dynamic;
import com.teamabnormals.environmental.common.item.YakPantsItem;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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

	private UUID lastHurtBy;
	private int grazeTimer;

	public Yak(EntityType<? extends Yak> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected Brain.Provider<Yak> brainProvider() {
		return Yaktelligence.createProvider();
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return Yaktelligence.createBrain(this.brainProvider().makeBrain(dynamic));
	}

	@Override
	public Brain<Yak> getBrain() {
		return (Brain<Yak>) super.getBrain();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CompoundTag tag) {
		Yaktelligence.createMemories(this, level.getRandom());
		return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
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
		compound.putBoolean("Sheared", this.isSheared());
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSheared(compound.getBoolean("Sheared"));
		this.readPersistentAngerSaveData(this.level(), compound);
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("yakBrain");
		Yaktelligence.tick(this);
		this.level().getProfiler().pop();

		AttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.isAngry()) {
			if (!this.isBaby() && !modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
				modifiableattributeinstance.addTransientModifier(ATTACKING_SPEED_BOOST);
			}
		} else if (modifiableattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
			modifiableattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
		}

		this.updatePersistentAnger((ServerLevel) this.level(), true);

		if (this.isAngry()) {
			this.lastHurtByPlayerTime = this.tickCount;
		}
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		if (this.level().isClientSide && this.grazeTimer > 0) {
			this.grazeTimer--;
		}
		super.aiStep();
	}

	@Override
	public void handleEntityEvent(byte id) {
		switch (id) {
			case 10 -> this.grazeTimer = 40;
			case 11 -> this.grazeTimer = 0;
			default -> super.handleEntityEvent(id);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.BUCKET) && !this.isBaby()) {
			player.playSound(EnvironmentalSoundEvents.YAK_MILK.get(), 1.0F, 1.0F);
			ItemStack newStack = ItemUtils.createFilledResult(stack, player, Items.MILK_BUCKET.getDefaultInstance());
			player.setItemInHand(hand, newStack);
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(EnvironmentalItemTags.YAK_FOOD).test(stack);
	}

	public float getHeadEatingOffset(float partialTicks) {
		if (this.grazeTimer <= 0) {
			return 0.0F;
		} else if (this.grazeTimer >= 4 && this.grazeTimer <= 36) {
			return 1.0F;
		} else {
			return this.grazeTimer < 4 ? ((float) this.grazeTimer - partialTicks) / 4.0F : -((float) (this.grazeTimer - 40) - partialTicks) / 4.0F;
		}
	}

	public float getHeadPitch(float partialTicks) {
		if (this.grazeTimer > 4 && this.grazeTimer <= 36) {
			return ((float) Math.PI / 5F) + 0.22F * Mth.sin((((float) (this.grazeTimer - 4) - partialTicks) / 32.0F) * 28.7F);
		} else {
			return this.grazeTimer > 0 ? ((float) Math.PI / 5F) : Mth.lerp(partialTicks, this.xRotO, this.getXRot()) * ((float) Math.PI / 180F);
		}
	}

	@Override
	public void ate() {
		this.setSheared(false);
		if (this.isBaby()) {
			this.ageUp(60);
		}
	}

	public boolean isSheared() {
		return this.entityData.get(SHEARED);
	}

	public void setSheared(boolean sheared) {
		this.entityData.set(SHEARED, sheared);
	}

	public boolean readyForShearing() {
		return this.isAlive() && !this.isSheared() && !this.isBaby();
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
		this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
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