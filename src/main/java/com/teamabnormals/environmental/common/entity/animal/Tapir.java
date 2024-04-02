package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.entity.ai.goal.tapir.TapirHuntFloraGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.tapir.TapirPanicGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.tapir.TapirSniffForFloraGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.tapir.TapirTemptGoal;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public class Tapir extends Animal {
	private static final EntityDataAccessor<Integer> TRACKING_TIME = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> HAS_BABY_PATTERN = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> BEING_TEMPTED = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SNIFFING = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_GRAZING = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<BlockPos>> FLORA_POS = SynchedEntityData.defineId(Tapir.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	private Item floraItem;
	private boolean running;

	private int sniffTimer;
    private float sniffAmount;
    private float sniffAmount0;

	private float snoutRaiseAmount;
	private float snoutRaiseAmount0;

	private float noAnimAmount;
	private float noAnimAmount0;

	private float neckAngle;
	private float neckAngle0;

	private int headShakeAnim;
	private int headShakeAnim0;

	public Tapir(EntityType<? extends Tapir> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TapirPanicGoal(this));
		this.goalSelector.addGoal(2, new TapirSniffForFloraGoal(this));
		this.goalSelector.addGoal(3, new TapirHuntFloraGoal(this));
		this.goalSelector.addGoal(4, new TapirTemptGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TRACKING_TIME, 0);
		this.entityData.define(HAS_BABY_PATTERN, false);
		this.entityData.define(BEING_TEMPTED, false);
		this.entityData.define(IS_SNIFFING, false);
        this.entityData.define(IS_GRAZING, false);
		this.entityData.define(FLORA_POS, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("BabyPattern", this.hasBabyPattern());
		if (this.floraItem != null)
			compound.putString("FloraItem", ForgeRegistries.ITEMS.getKey(this.floraItem).toString());
		BlockPos florapos = this.getFloraPos();
		if (florapos != null) {
			compound.putInt("FloraX", florapos.getX());
			compound.putInt("FloraY", florapos.getY());
			compound.putInt("FloraZ", florapos.getZ());
		}
		compound.putInt("TrackingTime", this.getTrackingTime());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHasBabyPattern(compound.getBoolean("BabyPattern"));
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("FloraItem")));
		if (item != Items.AIR)
			this.floraItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("FloraItem")));
		if (compound.contains("FloraX", 99) && compound.contains("FloraY", 99) && compound.contains("FloraZ", 99)) {
			BlockPos blockpos = new BlockPos(compound.getInt("FloraX"), compound.getInt("FloraY"), compound.getInt("FloraZ"));
			this.setFloraPos(blockpos);
		}
		this.setTrackingTime(compound.getInt("TrackingTime"));
	}

	public void stopTracking() {
		this.setTrackingTime(0);
		this.setFloraPos(null);
		this.floraItem = null;
		this.loveCause = null;
	}

	public int getTrackingTime() {
		return this.entityData.get(TRACKING_TIME);
	}

	public void setTrackingTime(int time) {
		this.entityData.set(TRACKING_TIME, time);
	}

	public boolean hasBabyPattern() {
		return this.entityData.get(HAS_BABY_PATTERN);
	}

	public void setHasBabyPattern(boolean hasBabyPattern) {
		this.entityData.set(HAS_BABY_PATTERN, hasBabyPattern);
	}

	public boolean isBeingTempted() {
		return this.entityData.get(BEING_TEMPTED);
	}

	public void setBeingTempted(boolean tempted) {
		this.entityData.set(BEING_TEMPTED, tempted);
	}

	public boolean isSniffing() {
		return this.entityData.get(IS_SNIFFING);
	}

	public void setSniffing(boolean sniffing) {
		this.entityData.set(IS_SNIFFING, sniffing);
	}

    public boolean isGrazing() {
        return this.entityData.get(IS_GRAZING);
    }

    public void setGrazing(boolean grazing) {
        this.entityData.set(IS_GRAZING, grazing);
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

	public Item getFloraItem() {
		return this.floraItem;
	}

	public boolean hasFloraItem() {
		return this.floraItem != null;
	}

	public float getSnoutRaiseAmount(float partialTick) {
		return Mth.lerp(partialTick, this.snoutRaiseAmount0, this.snoutRaiseAmount);
	}

	public float getNoAnimAmount(float partialTick) {
		return Mth.lerp(partialTick, this.noAnimAmount0, this.noAnimAmount);
	}

	public float getNeckAngle(float partialTick) {
		return Mth.lerp(partialTick, this.neckAngle0, this.neckAngle);
	}

	public float getHeadShakeAnim(float partialTick) {
		return Mth.lerp(partialTick, this.headShakeAnim0, this.headShakeAnim);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof BlockItem blockitem && !this.isBaby() && this.isFood(stack)) {
			if (this.getTrackingTime() <= 0) {
				if (!this.level.isClientSide()) {
					this.floraItem = blockitem;
					this.loveCause = player.getUUID();
					this.setTrackingTime(300);
					this.level.broadcastEntityEvent(this, (byte) 4);
				}
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}
			return InteractionResult.PASS;
		}

		return super.mobInteract(player, hand);
	}

    @Override
    public void tick() {
        super.tick();

        this.sniffAmount0 = this.sniffAmount;
        if (this.isSniffing())
            this.sniffAmount = Math.min(1.0F, this.sniffAmount + 0.25F);
        else
            this.sniffAmount = Math.max(0.0F, this.sniffAmount - 0.25F);

		this.headShakeAnim0 = this.headShakeAnim;
		if (this.headShakeAnim > 0)
			this.headShakeAnim--;

		this.noAnimAmount0 = this.noAnimAmount;
		if (this.isSniffing() || this.isGrazing())
			this.noAnimAmount = Math.min(1.0F, this.noAnimAmount + 0.15F);
		else
			this.noAnimAmount = Math.max(0.0F, this.noAnimAmount - 0.15F);

		this.neckAngle0 = this.neckAngle;
		if (this.isSniffing()) {
			this.neckAngle = Math.min(1.0F, this.neckAngle + 0.15F);
		} else if (this.isGrazing()) {
			BlockPos florapos = this.getFloraPos();
			boolean lookup = florapos != null && this.level.getBlockState(florapos).getShape(this.level, florapos).min(Axis.Y) + florapos.getY() > this.getEyeY();
			if (lookup)
				this.neckAngle = Math.max(-1.0F, this.neckAngle - 0.15F);
			else
				this.neckAngle = Math.min(1.0F, this.neckAngle + 0.15F);
		} else if (this.neckAngle > 0.0F) {
			this.neckAngle = Math.max(0.0F, this.neckAngle - 0.15F);
		} else {
			this.neckAngle = Math.min(0.0F, this.neckAngle + 0.15F);
		}

		this.snoutRaiseAmount0 = this.snoutRaiseAmount;
		if (this.isBeingTempted())
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

		if (!this.level.isClientSide()) {
			if (this.getTrackingTime() > 0) {
				if (this.hasFloraPos()) {
					if (!this.isGrazing()) {
						if (this.isLeashed())
							this.setTrackingTime(this.getTrackingTime() - 3);
						else
							this.setTrackingTime(this.getTrackingTime() - 1);

						if (this.getTrackingTime() == 0)
							this.stopTracking();
					}
				} else if (!this.isSniffing()) {
					this.setTrackingTime(this.getTrackingTime() - 1);
					if (this.getTrackingTime() == 0)
						this.stopTracking();
				}
			}

			if (this.isSniffing()) {
				if (this.sniffTimer-- <= 0) {
					this.playSound(EnvironmentalSoundEvents.PIG_SNIFF.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
					this.sniffTimer = 10 + this.random.nextInt(20);
				}
			} else {
				this.sniffTimer = 0;
			}

            if (this.tickCount % 20 == 0 && this.isGrazing() && this.hasFloraPos()) {
                this.playSound(SoundEvents.GENERIC_EAT, 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.level.broadcastEntityEvent(this, (byte) 7);
            }
		} else if (this.tickCount % 10 == 0 && this.getTrackingTime() > 0 && this.hasFloraItem()) {
			double d0 = random.nextGaussian() * 0.02D;
			double d1 = random.nextGaussian() * 0.02D;
			double d2 = random.nextGaussian() * 0.02D;
			level.addParticle(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), this.getRandomX(0.5D), this.getRandomY() + 0.5D, this.getRandomZ(0.5D), d0, d1, d2);
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
			for (int i = 0; i < 7; ++i) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.SMOKE, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
			}
		} else if (id == 6) {
			this.headShakeAnim = 20;
			this.headShakeAnim0 = 20;
		} else if (id == 7) {
			if (this.getFloraPos() != null) {
				for (int i = 0; i < 8; ++i) {
					Vec3 vector3d = new Vec3((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, (this.random.nextFloat() - 0.5D) * 0.1D);
					vector3d = vector3d.xRot(-this.getXRot() * Mth.DEG_TO_RAD);
					vector3d = vector3d.yRot(-this.getYRot() * Mth.DEG_TO_RAD);
					double d0 = -this.random.nextFloat() * 0.2D;
					Vec3 vector3d1 = new Vec3((this.random.nextFloat() - 0.5D) * 0.2D, d0, 0.9D - 0.05D * this.neckAngle + (this.random.nextFloat() - 0.5D) * 0.1D);
					vector3d1 = vector3d1.yRot(-this.yBodyRot * Mth.DEG_TO_RAD);
					vector3d1 = vector3d1.add(this.getX(), this.getEyeY() - 0.15D - 0.35D * this.neckAngle, this.getZ());
					this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.level.getBlockState(this.getFloraPos())), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
				}
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
	protected SoundEvent getAmbientSound() {
		return this.isSniffing() ? null : EnvironmentalSoundEvents.TAPIR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return EnvironmentalSoundEvents.TAPIR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.TAPIR_DEATH.get();
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
		if (this.isBaby())
			this.setHasBabyPattern(true);
		return data;
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
