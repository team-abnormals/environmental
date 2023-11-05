package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class Tapir extends Animal {
	private static final EntityDataAccessor<Boolean> HAS_BABY_PATTERN = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);

	public Tapir(EntityType<? extends Tapir> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ItemTags.ANVIL), false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_BABY_PATTERN, false);
	}

	public void setHasBabyPattern(boolean hasBabyPattern) {
		this.entityData.set(HAS_BABY_PATTERN, hasBabyPattern);
	}

	public boolean hasBabyPattern() {
		return this.entityData.get(HAS_BABY_PATTERN);
	}

	// this.pig.getNavigation().moveTo((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY() + 1, (double) ((float) blockpos.getZ()) + 0.5D, 1.1D);

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof BlockItem blockItem) {
			boolean foundBlock = false;
			origin:
			for (int height = 0; height <= 3; height++) {
				for (int width = 0; width <= 96; width++) {
					for (int i = -width; i <= width; i++) {
						for (int j = -height; j <= height; j++) {
							for (int k = -width; k <= width; k++) {
								if ((Math.abs(i) == width && Math.abs(j) == height) || (Math.abs(k) == width && Math.abs(j) == height)) {
									BlockPos position = this.blockPosition().offset(i, j, k);
									if (level.getBlockState(position).is(blockItem.getBlock())) {
										this.getNavigation().moveTo((double) ((float) position.getX()) + 0.5D, position.getY() + 1, (double) ((float) position.getZ()) + 0.5D, 1.1D);
										foundBlock = true;
										break origin;
									}
								}
							}
						}
					}
				}
			}

			for (int p = 0; p < 4; ++p) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(foundBlock ? ParticleTypes.HAPPY_VILLAGER : ParticleTypes.LARGE_SMOKE, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PIG_AMBIENT;
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
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
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
		return stack.is(ItemTags.ANVIL);
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
