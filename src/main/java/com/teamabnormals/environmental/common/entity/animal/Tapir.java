package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.entity.ai.goal.HuntFloraGoal;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class Tapir extends Animal {
	private static final EntityDataAccessor<Boolean> HAS_BABY_PATTERN = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);

	private static final EntityDataAccessor<Integer> TRACKING_TIME = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_TRACKING = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<BlockPos>> TRACKING_POS = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
	private static final EntityDataAccessor<Optional<BlockState>> TRACKING_STATE = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BLOCK_STATE);

	private int sniffSoundTime;

	public Tapir(EntityType<? extends Tapir> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new HuntFloraGoal(this));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_BABY_PATTERN, false);
		this.entityData.define(IS_TRACKING, false);
		this.entityData.define(HAS_TARGET, false);
		this.entityData.define(TRACKING_TIME, 0);
		this.entityData.define(TRACKING_POS, Optional.empty());
		this.entityData.define(TRACKING_STATE, Optional.empty());
	}

	public boolean hasTarget() {
		return this.entityData.get(HAS_TARGET);
	}

	public void setHasTarget(boolean hasTarget) {
		this.entityData.set(HAS_TARGET, hasTarget);
	}

	public void setTrackingPos(Optional<BlockPos> pos) {
		this.entityData.set(TRACKING_POS, pos);
	}

	public Optional<BlockPos> getTrackingPos() {
		return this.entityData.get(TRACKING_POS);
	}

	public Optional<BlockState> getTrackingState() {
		return this.entityData.get(TRACKING_STATE);
	}

	public void setTrackingState(Optional<BlockState> state) {
		this.entityData.set(TRACKING_STATE, state);
	}

	public int getTrackingTime() {
		return this.entityData.get(TRACKING_TIME);
	}

	public void setTrackingTime(int time) {
		this.entityData.set(TRACKING_TIME, time);
	}

	public boolean isTracking() {
		return this.entityData.get(IS_TRACKING);
	}

	public void setTracking(boolean tracking) {
		this.entityData.set(IS_TRACKING, tracking);
	}

	public void setHasBabyPattern(boolean hasBabyPattern) {
		this.entityData.set(HAS_BABY_PATTERN, hasBabyPattern);
	}

	public boolean hasBabyPattern() {
		return this.entityData.get(HAS_BABY_PATTERN);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof BlockItem blockItem && this.getTrackingTime() == 0) {
			BlockState state = blockItem.getBlock().defaultBlockState();
			if (isFood(stack) && this.findNearestTarget(state)) {
				this.setTrackingState(Optional.of(state));
				this.setTrackingTime(4800);
				if (this.canFallInLove()) {
					this.setInLoveTime(600);
					this.loveCause = player.getUUID();
				}

				if (level.isClientSide()) {
					for (int i = 0; i < 7; ++i) {
						double d0 = random.nextGaussian() * 0.02D;
						double d1 = random.nextGaussian() * 0.02D;
						double d2 = random.nextGaussian() * 0.02D;
						level.addParticle(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), this.getRandomX(0.5D), this.getRandomY() + 0.25D, this.getRandomZ(0.5D), d0, d1, d2);
					}
				}
				return InteractionResult.sidedSuccess(level.isClientSide());
			} else {
				this.getLookControl().setLookAt(player.getX(), player.getEyeY(), player.getZ());
				this.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

				this.getNavigation().moveTo(player, 1.0D);
				Vec3 vec3 = LandRandomPos.getPos(this, 5, 4);
				if (vec3 != null) {
					this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0D);
				}

				if (level.isClientSide()) {
					for (int i = 0; i < 7; ++i) {
						double d0 = random.nextGaussian() * 0.02D;
						double d1 = random.nextGaussian() * 0.02D;
						double d2 = random.nextGaussian() * 0.02D;
						level.addParticle(ParticleTypes.SMOKE, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
					}
				}
			}
		}

		return super.mobInteract(player, hand);
	}

	private boolean findNearestTarget(BlockState state) {
		BlockPos blockpos = this.blockPosition();
		for (int height = 0; height <= 5; height++) {
			for (int width = 0; width <= 96; width++) {
				for (int i = -width; i <= width; i++) {
					for (int j = -height; j <= height; j++) {
						for (int k = -width; k <= width; k++) {
							if ((Math.abs(i) == width && Math.abs(j) == height) || (Math.abs(k) == width && Math.abs(j) == height)) {
								BlockPos position = blockpos.offset(i, j, k);
								if (this.level.getBlockState(position).is(state.getBlock())) {
									this.setHasTarget(true);
									this.setTrackingPos(Optional.of(position));
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		int huntingtime = this.getTrackingTime();

		if (huntingtime == 0 || (this.hasTarget() && this.getTrackingPos().isPresent() && this.getTrackingState().isPresent() && !this.level.getBlockState(this.getTrackingPos().get()).is(this.getTrackingState().get().getBlock()))) {
			this.setHasTarget(false);
			if (huntingtime > 0) {
				this.setTrackingTime(Math.max(-400, -huntingtime));
			}
		} else {
			if (huntingtime > 0) {
				this.setTrackingTime(huntingtime - 1);
			} else {
				this.setTrackingTime(huntingtime + 1);
				if (level.isClientSide() && this.hasTarget() && huntingtime % 10 == 0) {
					double d0 = random.nextGaussian() * 0.02D;
					double d1 = random.nextGaussian() * 0.02D;
					double d2 = random.nextGaussian() * 0.02D;
					level.addParticle(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), this.getRandomX(0.4D), this.getRandomY() + 0.25D, this.getRandomZ(0.5D), d0, d1, d2);
				}
			}
		}

		this.sniffSoundTime += 1;
		if (!level.isClientSide() && this.isTracking() && random.nextInt(60) < this.sniffSoundTime) {
			this.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			this.sniffSoundTime = -20;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isTracking() ? null : SoundEvents.PIG_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PIG_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.35D);
	}

	@Override
	public Tapir getBreedOffspring(ServerLevel level, AgeableMob mob) {
		Tapir child = EnvironmentalEntityTypes.TAPIR.get().create(level);
		child.setHasBabyPattern(true);
		return child;
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.random.nextFloat() < 0.9F) {
			this.setHasBabyPattern(false);
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			BlockState state = blockItem.getBlock().defaultBlockState();
			Material material = state.getMaterial();
			return (material == Material.PLANT
					|| material == Material.WATER_PLANT
					|| material == Material.REPLACEABLE_PLANT
					|| material == Material.REPLACEABLE_FIREPROOF_PLANT
					|| material == Material.REPLACEABLE_WATER_PLANT
					|| material == Material.BAMBOO_SAPLING
					|| material == Material.BAMBOO
					|| material == Material.LEAVES
					|| material == Material.CACTUS
					|| material == Material.MOSS
			);
		}

		return false;
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new TapirNavigator(this, level);
	}

	public static class TapirNavigator extends GroundPathNavigation {
		public TapirNavigator(Mob mob, Level level) {
			super(mob, level);
		}

		protected PathFinder createPathFinder(int p_33382_) {
			this.nodeEvaluator = new TapirNodeEvaluator();
			return new PathFinder(this.nodeEvaluator, p_33382_);
		}
	}

	public static class TapirNodeEvaluator extends WalkNodeEvaluator {
		protected BlockPathTypes evaluateBlockPathType(BlockGetter level, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
			return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.WALKABLE : super.evaluateBlockPathType(level, p_33388_, p_33389_, p_33390_, p_33391_);
		}
	}
}
