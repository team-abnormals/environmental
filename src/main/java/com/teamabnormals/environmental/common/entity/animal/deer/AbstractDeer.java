package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.common.entity.ai.goal.*;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractDeer extends Animal {
	private static final EntityDimensions GRAZING_DIMENSIONS = EntityDimensions.scalable(0.8F, 1.2F);

	private static final Predicate<LivingEntity> AVOID_ENTITY_PREDICATE = (entity) ->
			entity.getType().is(EnvironmentalEntityTypeTags.SCARES_DEER) && !entity.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);
	private static final Predicate<LivingEntity> TRUSTING_AVOID_ENTITY_PREDICATE = (entity) -> {
		if (!entity.getType().is(EnvironmentalEntityTypeTags.SCARES_TRUSTING_DEER) || (entity instanceof TamableAnimal && ((TamableAnimal) entity).isTame())) {
			return false;
		} else {
			return !entity.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);
		}
	};
	private static final TargetingConditions AVOID_ENTITY_TARGETING = TargetingConditions.forCombat().range(10.0D).selector(AVOID_ENTITY_PREDICATE);
	private static final TargetingConditions TRUSTING_AVOID_ENTITY_TARGETING = TargetingConditions.forCombat().range(10.0D).selector(TRUSTING_AVOID_ENTITY_PREDICATE);

	private static final EntityDataAccessor<Integer> TARGET_NECK_ANGLE = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FLOWER_AMOUNT = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> HAS_ANTLERS = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> TRUSTING = SynchedEntityData.defineId(AbstractDeer.class, EntityDataSerializers.BOOLEAN);

	private float neckAngle;
	private float neckAngleO;
	private float sprintAmount;
	private float sprintAmountO;
	private int hopProgress;
	private float hopAmount;
	private float hopAmountO;
	private float hopAngle;
	private float hopAngleO;

	private int floweringTime;
	private final List<BlockState> flowers = new ArrayList<>();
	@Nullable
	private TemptGoal temptGoal;

	public AbstractDeer(EntityType<? extends Animal> type, Level level) {
		super(type, level);
		this.neckAngle = 15F;
		this.neckAngleO = 15F;
		this.floweringTime = 0;
	}

	@Override
	protected void registerGoals() {
		this.temptGoal = new DeerTemptGoal(this, 0.6D, 1.1D, this.getTemptItems());
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new DeerFollowParentGoal(this));
		this.goalSelector.addGoal(2, new DeerRunFromAttackerGoal(this));
		this.goalSelector.addGoal(3, new DeerAvoidEntityGoal(this));
		this.goalSelector.addGoal(4, new BreedGoal(this, 0.8D));
		this.goalSelector.addGoal(5, new DeerFrolicGoal(this));
		this.goalSelector.addGoal(6, this.temptGoal);
		this.goalSelector.addGoal(7, new DeerGrazeGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TARGET_NECK_ANGLE, 15);
		this.entityData.define(FLOWER_AMOUNT, 0);
		this.entityData.define(HAS_ANTLERS, true);
		this.entityData.define(TRUSTING, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Antlers", this.hasAntlers());
		compound.putBoolean("Trusting", this.isTrusting());

		compound.putInt("FloweringTime", this.floweringTime);
		compound.putInt("FlowerAmount", this.getFlowerAmount());
		ListTag listtag = new ListTag();
		for (BlockState blockstate : this.flowers) {
			if (blockstate != null) {
				listtag.add(NbtUtils.writeBlockState(blockstate));
			}
		}
		compound.put("Flowers", listtag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHasAntlers(compound.getBoolean("Antlers"));
		this.setTrusting(compound.getBoolean("Trusting"));

		this.floweringTime = compound.getInt("FloweringTime");
		this.setFlowerAmount(compound.getInt("FlowerAmount"));
		ListTag listtag = compound.getList("Flowers", 10);
		for (int i = 0; i < listtag.size(); ++i) {
			BlockState blockstate = NbtUtils.readBlockState(listtag.getCompound(i));
			if (blockstate != null && !blockstate.isAir()) {
				this.flowers.add(blockstate);
			}
		}
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
	}

	@Override
	public void customServerAiStep() {
		this.setSprinting(!this.isInWater() && this.getMoveControl().hasWanted() && this.getMoveControl().getSpeedModifier() >= 1.75D);
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.level.isClientSide) {
			if (this.getFlowerAmount() > 0 && this.tickCount % 16 == 0)
				this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
		} else {
			if (this.floweringTime > 0)
				--this.floweringTime;

			if ((this.floweringTime <= 0 && this.getFlowerAmount() > 0) || (this.getFlowerAmount() <= 0 && !this.flowers.isEmpty())) {
				this.setFlowerAmount(0);
				this.flowers.clear();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		this.updateNeckAngle();
		this.updateSprintAnimation();
		this.updateHopAnimation();
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		boolean flag = player.isCreative() || this.temptGoal == null || this.temptGoal.isRunning();

		if (!this.isBaby() && (this.isTrusting() || flag)) {
			if (stack.is(Items.MELON_SLICE)) {
				this.setFlowerAmount(this.getFlowerAmount() + 6);
				this.floweringTime += 2400;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (stack.is(Items.GLISTERING_MELON_SLICE)) {
				this.setFlowerAmount(this.getFlowerAmount() + 20);
				this.floweringTime += 2400;
				this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
				this.usePlayerItem(player, hand, stack);
				return InteractionResult.SUCCESS;
			} else if (this.getFlowerAmount() > 0 && stack.is(EnvironmentalItemTags.DEER_PLANTABLES) && item instanceof BlockItem block) {
				if (!this.flowers.contains(block.getBlock().defaultBlockState())) {
					this.flowers.add(block.getBlock().defaultBlockState());
					this.floweringTime = Math.max(600, this.floweringTime);
					this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
					this.usePlayerItem(player, hand, stack);
					return InteractionResult.SUCCESS;
				} else {
					return InteractionResult.PASS;
				}
			}
		}

		if (!this.isTrusting()) {
			if (flag && this.isFood(stack)) {
				if (!this.level.isClientSide) {
					this.usePlayerItem(player, hand, stack);
					if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
						this.setTrusting(true);
						this.spawnTrustingParticles(true);
						this.level.broadcastEntityEvent(this, (byte) 5);
					} else {
						this.spawnTrustingParticles(false);
						this.level.broadcastEntityEvent(this, (byte) 6);
					}
				}

				return InteractionResult.sidedSuccess(this.level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		} else {
			return super.mobInteract(player, hand);
		}
	}

	public Ingredient getTemptItems() {
		return Ingredient.of(EnvironmentalItemTags.DEER_TEMPT_ITEMS);
	}

	public void spawnFlower() {
		if (this.isSpreadingFlowers()) {
			BlockPos pos = this.blockPosition();
			BlockState state = this.flowers.get(this.random.nextInt(this.flowers.size()));
			boolean tall = state.getBlock() instanceof DoublePlantBlock;
			if (state.canSurvive(this.level, pos) && this.level.isEmptyBlock(pos) && (!tall || this.level.isEmptyBlock(pos.above()))) {
				if (!tall) {
					this.level.setBlock(pos, state, 3);
				} else {
					DoublePlantBlock.placeAt(this.level, state, pos, 2);
				}
				SoundType sound = state.getSoundType();
				this.playSound(sound.getPlaceSound(), (sound.getVolume() + 1.0F) / 4.0F, sound.getPitch() * 0.8F);
				this.setFlowerAmount(this.getFlowerAmount() - 1);
				this.spawnBoneMealParticles(state, pos);
			}
		}
	}

	private void particleCloud(ParticleOptions particle) {
		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private void spawnTrustingParticles(boolean trusts) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!trusts) {
			particleoptions = ParticleTypes.SMOKE;
		}

		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private void spawnBoneMealParticles(BlockState blockstate, BlockPos blockpos) {
		int amount = 15;
		double d0 = 0.5D;
		double d1;
		if (blockstate.isSolidRender(this.level, blockpos)) {
			blockpos = blockpos.above();
			amount *= 3;
			d0 = 3.0D;
			d1 = 1.0D;
		} else {
			d1 = blockstate.getShape(this.level, blockpos).max(Direction.Axis.Y);
		}

		((ServerLevel) this.level).sendParticles(ParticleTypes.HAPPY_VILLAGER, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D, 0, 0.0D, 0.0D, 0.0D, 0.0D);
		RandomSource random = this.level.getRandom();

		for (int i = 0; i < amount; ++i) {
			double d2 = random.nextGaussian() * 0.02D;
			double d3 = random.nextGaussian() * 0.02D;
			double d4 = random.nextGaussian() * 0.02D;
			double d5 = 0.5D - d0;
			double d6 = (double) blockpos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
			double d7 = (double) blockpos.getY() + random.nextDouble() * d1;
			double d8 = (double) blockpos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
			((ServerLevel) this.level).sendParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 0, d2, d3, d4, 0.0D);
		}
	}

	private void updateNeckAngle() {
		this.neckAngleO = this.neckAngle;
		int i = this.getTargetNeckAngle();
		float f = this.neckAngle + (i - this.neckAngle) * 0.3F;
		if (this.neckAngle < i == f > i) {
			this.neckAngle = i;
		} else {
			this.neckAngle = f;
		}
	}

	private void updateSprintAnimation() {
		this.sprintAmountO = this.sprintAmount;
		if (this.isSprinting()) {
			this.sprintAmount = Math.min(1.0F, this.sprintAmount + 0.1F);
		} else {
			this.sprintAmount = Math.max(0.0F, this.sprintAmount - 0.1F);
		}
	}

	private void updateHopAnimation() {
		this.hopAmountO = this.hopAmount;
		this.hopAngleO = this.hopAngle;

		if (this.hopProgress != 3) {
			--this.hopProgress;
		} else if (this.isOnGround() || this.isInWaterOrBubble()) {
			this.hopProgress = 2;
		}

		if (this.hopProgress >= 3) {
			if (this.hopProgress > 3) {
				this.hopAngle = Math.max(-0.2F, this.hopAngle - 0.18F);
			} else {
				this.hopAngle = Math.min(0.2F, this.hopAngle + 0.08F);
			}

			this.hopAmount = Math.min(1.0F, this.hopAmount + 0.25F);
		} else {
			if (this.hopAngle > 0.0F) {
				this.hopAngle = Math.max(0.0F, this.hopAngle - 0.05F);
			} else if (this.hopAngle < 0.0F) {
				this.hopAngle = Math.min(0.0F, this.hopAngle + 0.05F);
			}
		}

		if (this.hopProgress > 0) {
			this.hopAmount = Math.min(1.0F, this.hopAmount + 0.25F);
		} else {
			this.hopAmount = Math.max(0.0F, this.hopAmount - 0.25F);
		}
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
		return this.isGrazing() ? dimensions.height * 0.3F : dimensions.height * 0.95F;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		if (this.isGrazing()) {
			return GRAZING_DIMENSIONS.scale(this.getScale());
		} else {
			return super.getDimensions(pose);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return EnvironmentalSoundEvents.DEER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSoundEvents.DEER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.DEER_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSoundEvents.DEER_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (TARGET_NECK_ANGLE.equals(key)) {
			this.refreshDimensions();
		}

		super.onSyncedDataUpdated(key);
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 4) {
			this.hopProgress = 10;
		} else if (id == 5) {
			this.spawnTrustingParticles(true);
		} else if (id == 6) {
			this.spawnTrustingParticles(false);
		} else {
			super.handleEntityEvent(id);
		}
	}

	private int getTargetNeckAngle() {
		return this.entityData.get(TARGET_NECK_ANGLE);
	}

	public void setTargetNeckAngle(int angle) {
		this.entityData.set(TARGET_NECK_ANGLE, angle);
	}

	public void resetTargetNeckAngle() {
		this.setTargetNeckAngle(15);
	}

	public float getNeckAngle(float partialTick) {
		return Mth.lerp(partialTick, this.neckAngleO, this.neckAngle);
	}

	private boolean isGrazing() {
		return this.getTargetNeckAngle() >= 90;
	}

	public float getSprintAmount(float partialTick) {
		return Mth.lerp(partialTick, this.sprintAmountO, this.sprintAmount);
	}

	public float getHopAmount(float partialTick) {
		return Mth.lerp(partialTick, this.hopAmountO, this.hopAmount);
	}

	public float getHopAngle(float partialTick) {
		return Mth.lerp(partialTick, this.hopAngleO, this.hopAngle);
	}

	public boolean isSpreadingFlowers() {
		return this.getFlowerAmount() > 0 && !this.flowers.isEmpty();
	}

	private int getFlowerAmount() {
		return this.entityData.get(FLOWER_AMOUNT);
	}

	private void setFlowerAmount(int amount) {
		this.entityData.set(FLOWER_AMOUNT, amount);
	}

	public void setHasAntlers(boolean antlers) {
		this.entityData.set(HAS_ANTLERS, antlers);
	}

	public boolean hasAntlers() {
		return this.entityData.get(HAS_ANTLERS);
	}

	public void setTrusting(boolean trusting) {
		this.entityData.set(TRUSTING, trusting);
	}

	public boolean isTrusting() {
		return this.entityData.get(TRUSTING);
	}

	public LivingEntity getNearestScaryEntity() {
		return this.level.getNearestEntity(this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10.0D, 4.0D, 10.0D), (p_148124_) -> {
			return true;
		}), this.isTrusting() ? TRUSTING_AVOID_ENTITY_TARGETING : AVOID_ENTITY_TARGETING, this, this.getX(), this.getY(), this.getZ());
	}
}
