package com.teamabnormals.environmental.common.entity.animal.tapir;

import com.teamabnormals.environmental.common.entity.ai.goal.tapir.*;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

public class Tapir extends Animal {
	private static final EntityDataAccessor<Integer> TRACKING_TIME = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> ANIMATION = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> HAS_BABY_PATTERN = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<BlockPos>> FLORA_POS = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	private Player feeder;
	private Block floraBlock;
	private boolean running;

	private int sniffTimer;
    private int sniffAnim;
    private int sniffAnim0;
    private float sniffAmount;
    private float sniffAmount0;

	private int snoutRaiseTimer;
	private float snoutRaiseAmount;
	private float snoutRaiseAmount0;

	private int headShakeAnim;
	private int headShakeAnim0;

	public Tapir(EntityType<? extends Tapir> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TapirPanicGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TapirHuntFloraGoal(this));
		this.goalSelector.addGoal(4, new TapirSniffForFloraGoal(this));
		this.goalSelector.addGoal(5, new TapirTemptGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TRACKING_TIME, 0);
		this.entityData.define(ANIMATION, (byte) 0);
		this.entityData.define(HAS_BABY_PATTERN, false);
		this.entityData.define(FLORA_POS, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("BabyPattern", this.hasBabyPattern());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHasBabyPattern(compound.getBoolean("BabyPattern"));
	}

	public void stopTracking() {
		this.setTrackingTime(0);
		this.setFloraPos(null);
		this.floraBlock = null;
		this.feeder = null;
	}

	public boolean isTrackingFlora() {
		return this.hasFloraPos() && this.hasFloraBlock() && this.getTrackingTime() > 0;
	}

	public void setHasBabyPattern(boolean hasBabyPattern) {
		this.entityData.set(HAS_BABY_PATTERN, hasBabyPattern);
	}

	public boolean hasBabyPattern() {
		return this.entityData.get(HAS_BABY_PATTERN);
	}

	public void setAnimation(TapirAnimation anim) {
		this.entityData.set(ANIMATION, (byte) anim.getId());
	}

	public TapirAnimation getAnimation() {
		return TapirAnimation.byId(this.entityData.get(ANIMATION));
	}

	public boolean hasFloraPos() {
		return this.getFloraPos() != null;
	}

	public BlockPos getFloraPos() {
		return this.entityData.get(FLORA_POS).orElse(null);
	}

	public void setFloraPos(BlockPos pos) {
		this.entityData.set(FLORA_POS, Optional.ofNullable(pos));
	}

	public boolean hasFloraBlock() {
		return this.getFloraBlock() != null;
	}

	public Block getFloraBlock() {
		return this.floraBlock;
	}

	public void setFloraBlock(Block block) {
		this.floraBlock = block;
	}

	public int getTrackingTime() {
		return this.entityData.get(TRACKING_TIME);
	}

	public void setTrackingTime(int time) {
		this.entityData.set(TRACKING_TIME, time);
	}

    public float getSniffAnim(float partialTick) {
        return Mth.lerp(partialTick, this.sniffAnim0, this.sniffAnim);
    }

    public float getSniffAmount(float partialTick) {
        return Mth.lerp(partialTick, this.sniffAmount0, this.sniffAmount);
    }

	public float getSnoutRaiseAmount(float partialTick) {
		return Mth.lerp(partialTick, this.snoutRaiseAmount0, this.snoutRaiseAmount);
	}

	public float getHeadShakeAnim(float partialTick) {
		return Mth.lerp(partialTick, this.headShakeAnim0, this.headShakeAnim);
	}

	public Player getFeeder() {
		return this.feeder;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof BlockItem blockitem && !this.isBaby() && this.isFood(stack) && !this.hasFloraBlock()) {
			if (!this.level.isClientSide()) {
				this.floraBlock = blockitem.getBlock();
				this.feeder = player;
				this.level.broadcastEntityEvent(this, (byte) 4);
			}
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		}

		return super.mobInteract(player, hand);
	}

    @Override
    public void tick() {
        super.tick();

        this.sniffAnim0 = this.sniffAnim;
        if (this.sniffAnim > 0)
            this.sniffAnim--;

        this.sniffAmount0 = this.sniffAmount;
        if (this.getAnimation() == TapirAnimation.SNIFFING)
            this.sniffAmount = Math.min(1.0F, this.sniffAmount + 0.25F);
        else
            this.sniffAmount = Math.max(0.0F, this.sniffAmount - 0.25F);

		this.headShakeAnim0 = this.headShakeAnim;
		if (this.headShakeAnim > 0)
			this.headShakeAnim--;

		/*
		if (this.snoutRaiseTimer > 0)
			this.snoutRaiseTimer--;
		else if (this.isBeingTempted())
			this.snoutRaiseTimer = 30 + this.random.nextInt(30);
		*/

		this.snoutRaiseAmount0 = this.snoutRaiseAmount;
		if (this.getAnimation() == TapirAnimation.RAISING_TRUNK)
			this.snoutRaiseAmount = Math.min(1.0F, this.snoutRaiseAmount + 0.25F);
		else
			this.snoutRaiseAmount = Math.max(0.0F, this.snoutRaiseAmount - 0.25F);
    }

	@Override
	public void customServerAiStep() {
		this.setSprinting(!this.isInWater() && this.getMoveControl().hasWanted() && this.running);
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getTrackingTime() > 0) {
			this.setTrackingTime(this.getTrackingTime() - 1);
			if (this.getTrackingTime() == 0)
				this.stopTracking();
		}

		if (!this.level.isClientSide()) {
			if (this.getAnimation() == TapirAnimation.SNIFFING) {
				if (this.sniffTimer-- <= 0) {
					this.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
					this.sniffTimer = 10 + this.random.nextInt(20);
				}
			} else {
				this.sniffTimer = 0;
			}
		}
	}

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
			for (int i = 0; i < 7; ++i) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
			}
        } else if (id == 5) {
			this.headShakeAnim = 20;
			this.headShakeAnim0 = 20;

			for (int i = 0; i < 7; ++i) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.SMOKE, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
			}
		} else {
            super.handleEntityEvent(id);
        }
    }

	@Override
	public boolean canFallInLove() {
		return false;
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.98F;
	}

	@Override
	public int getMaxHeadXRot() {
		return (this.getAnimation() == TapirAnimation.SNIFFING || this.getAnimation() == TapirAnimation.GRAZING) ? 0 : super.getMaxHeadXRot();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.getAnimation() == TapirAnimation.SNIFFING ? null : SoundEvents.PIG_AMBIENT;
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
		AABB aabb = this.getBoundingBox();
		for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX + 0.001D), Mth.floor(aabb.minY + 0.001D), Mth.floor(aabb.minZ + 0.001D), Mth.floor(aabb.maxX - 0.001D), Mth.floor(aabb.maxY - 0.001D), Mth.floor(aabb.maxZ - 0.001D))) {
			if (this.level.getBlockState(blockpos).getBlock() instanceof LeavesBlock) {
				this.playSound(EnvironmentalSoundEvents.TAPIR_LEAF_STEP.get(), 0.6F, 1.0F);
				break;
			}
		}
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.3D);
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

		@Override
		protected PathFinder createPathFinder(int p_33382_) {
			this.nodeEvaluator = new TapirNodeEvaluator();
            this.nodeEvaluator.setCanPassDoors(true);
			return new PathFinder(this.nodeEvaluator, p_33382_);
		}
	}

	public static class TapirNodeEvaluator extends WalkNodeEvaluator {
		protected BlockPathTypes evaluateBlockPathType(BlockGetter level, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
			return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.WALKABLE : super.evaluateBlockPathType(level, p_33388_, p_33389_, p_33390_, p_33391_);
		}
	}
}
