package com.teamabnormals.environmental.common.entity.animal.slabfish;

import com.teamabnormals.environmental.common.entity.ai.goal.*;
import com.teamabnormals.environmental.common.inventory.SlabfishInventory;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Slabfish extends TamableAnimal implements ContainerListener, Bucketable {

	private static final EntityDataAccessor<ResourceLocation> SLABFISH_TYPE = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);
	private static final EntityDataAccessor<Integer> SLABFISH_OVERLAY = SynchedEntityData.defineId(Slabfish.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Slabfish.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<BlockPos>> EFFIGY = SynchedEntityData.defineId(Slabfish.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	private static final EntityDataAccessor<ResourceLocation> BACKPACK = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);
	private static final EntityDataAccessor<Boolean> HAS_BACKPACK = SynchedEntityData.defineId(Slabfish.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<ResourceLocation> SWEATER = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);

	public static final EntityDimensions SIZE_SWIMMING = EntityDimensions.fixed(0.7F, 0.6F);
	public static final EntityDimensions SIZE_SITTING = EntityDimensions.fixed(0.45F, 0.6F);
	public static final EntityDimensions SIZE_SWIMMING_CHILD = EntityDimensions.fixed(0.35F, 0.3F);
	public static final EntityDimensions SIZE_SITTING_CHILD = EntityDimensions.fixed(0.225F, 0.3F);

	public SlabfishInventory slabfishBackpack;
	public boolean backpackFull;
	public int playersUsing;
	private UUID lightningUUID;
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;
	public boolean isPartying = false;
	BlockPos jukeboxPosition;

	public Slabfish(EntityType<? extends Slabfish> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.slabfishBackpack = new SlabfishInventory(this);
		this.slabfishBackpack.addListener(this);
		this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.slabfishBackpack));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 15.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));

		this.goalSelector.addGoal(2, new SlabbyFindEffigyGoal(this));
		this.goalSelector.addGoal(2, new SlabbyPraiseEffigyGoal(this));

		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, entity -> entity.getType().is(EntityTypeTags.RAIDERS), 15.0F, 1.0D, 1.5D, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));

		this.goalSelector.addGoal(4, new SlabbyBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new SlabbyGrabItemGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(EnvironmentalItemTags.SLABFISH_FOOD), false));
		this.goalSelector.addGoal(8, new SlabbyFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(SLABFISH_TYPE, SlabfishManager.DEFAULT_SLABFISH.getRegistryName());
		this.getEntityData().define(SLABFISH_OVERLAY, 0);
		this.getEntityData().define(FROM_BUCKET, false);
		this.getEntityData().define(EFFIGY, Optional.empty());

		this.getEntityData().define(BACKPACK, SlabfishManager.BROWN_BACKPACK.getRegistryName());
		this.getEntityData().define(HAS_BACKPACK, false);
		this.getEntityData().define(SWEATER, SlabfishManager.EMPTY_SWEATER.getRegistryName());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("SlabfishType", this.getSlabfishType().toString());
		compound.putInt("SlabfishOverlay", this.getSlabfishOverlay().getId());
		if (this.hasBackpack())
			compound.putString("BackpackType", this.getBackpack().toString());
		compound.putBoolean("FromBucket", this.fromBucket());

		if (this.getEffigy() != null) {
			compound.put("Effigy", NbtUtils.writeBlockPos(this.getEffigy()));
		}

		this.slabfishBackpack.write(compound);
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return !this.isLeashed();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSlabfishType(new ResourceLocation(compound.getString("SlabfishType")));
		this.setSlabfishOverlay(SlabfishOverlay.byId(compound.getInt("SlabfishOverlay")));
		this.setBackpack(compound.contains("BackpackType", Tag.TAG_STRING) ? new ResourceLocation(compound.getString("BackpackType")) : SlabfishManager.BROWN_BACKPACK.getRegistryName());
		this.setFromBucket(compound.getBoolean("FromBucket"));

		if (compound.contains("Effigy", Tag.TAG_COMPOUND)) {
			this.setEffigy(NbtUtils.readBlockPos(compound.getCompound("Effigy")));
		}

		this.slabfishBackpack.read(compound);
		this.updateSweater();
		this.updateBackpack();
		this.setHasBackpack(!this.slabfishBackpack.getItem(1).isEmpty() && this.slabfishBackpack.getItem(1).is(Tags.Items.CHESTS_WOODEN));
	}

	// GENERAL //

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		SlabfishManager slabfishManager = SlabfishManager.get(this.level);
		SlabfishType slabfishType = slabfishManager.getSlabfishType(this.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);

		if (item == Items.WATER_BUCKET && this.isAlive() && !(this.isTame() && !player.isSecondaryUseActive())) {
			if (this.hasBackpack())
				this.dropBackpack();
			return Bucketable.bucketMobPickup(player, hand, this).get();
		}

		if (stack.is(ItemTags.CREEPER_DROP_MUSIC_DISCS)) {
			this.usePlayerItem(player, hand, stack);
			this.playBurpSound();
			this.particleCloud(ParticleTypes.NOTE);
			this.dropItem(EnvironmentalItems.MUSIC_DISC_SLABRAVE.get());
			return InteractionResult.SUCCESS;
		}

		if (this.isTame()) {
			if (this.hasBackpack() && (slabfishType.getCustomBackpack() == null || !slabfishManager.getBackpackType(slabfishType.getCustomBackpack()).isPresent()) && slabfishManager.getBackpackType(stack).isPresent() && !slabfishManager.getBackpackType(stack).orElse(SlabfishManager.BROWN_BACKPACK).getRegistryName().equals(this.getBackpack())) {
				if (!this.level.isClientSide()) {
					ItemStack previousBackpack = this.slabfishBackpack.getItem(2);

					if (!previousBackpack.isEmpty()) {
						Containers.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousBackpack.copy());
						this.slabfishBackpack.removeItemNoUpdate(2);
					}

					this.slabfishBackpack.setItem(2, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
				}
				return InteractionResult.SUCCESS;
			}

			if (slabfishManager.getSweaterType(stack).isPresent() && !player.isSecondaryUseActive() && (!this.hasSweater() || !slabfishManager.getSweaterType(stack).orElse(SlabfishManager.EMPTY_SWEATER).getRegistryName().equals(this.getSweater()))) {
				if (!this.level.isClientSide()) {
					ItemStack previousSweater = this.slabfishBackpack.getItem(0);
					if (!previousSweater.isEmpty()) {
						Containers.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousSweater.copy());
						this.slabfishBackpack.removeItemNoUpdate(0);
					}
					this.slabfishBackpack.setItem(0, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
				}
				return InteractionResult.SUCCESS;
			}

			if (stack.is(Tags.Items.CHESTS_WOODEN) && !this.hasBackpack()) {
				if (!this.level.isClientSide()) {
					this.slabfishBackpack.setItem(1, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
					if (player instanceof ServerPlayer)
						EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.trigger((ServerPlayer) player);
				}
				return InteractionResult.SUCCESS;
			}

			if (item == Items.SHEARS && this.hasSweater() && !player.isSecondaryUseActive()) {
				ItemStack previousSweater = this.slabfishBackpack.getItem(0);
				if (!previousSweater.isEmpty()) {
					Containers.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousSweater.copy());
					this.slabfishBackpack.removeItemNoUpdate(0);
				}
				return InteractionResult.SUCCESS;
			}

			if (player.isSecondaryUseActive() && stack.is(Tags.Items.SHEARS) && this.hasBackpack()) {
				this.dropBackpack();
				return InteractionResult.SUCCESS;
			}

			if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
				this.usePlayerItem(player, hand, stack);
				level.playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSoundEvents.SLABFISH_EAT.get(), SoundSource.NEUTRAL, 1F, 1F, true);
				this.heal(item.getFoodProperties(stack, player).getNutrition());
				this.particleCloud(ParticleTypes.COMPOSTER);
				return InteractionResult.SUCCESS;
			}

			if (Ingredient.of(EnvironmentalItemTags.SLABFISH_SNACKS).test(stack)) {
				stack.finishUsingItem(this.level, this);
				this.usePlayerItem(player, hand, stack);
				level.playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSoundEvents.SLABFISH_EAT.get(), SoundSource.NEUTRAL, 1F, 1F, true);
				return InteractionResult.SUCCESS;
			}

			if (this.isFood(stack)) {
				int i = this.getAge();
				if (!this.level.isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(player, hand, stack);
					this.setInLove(player);
					return InteractionResult.SUCCESS;
				}

				if (this.isBaby()) {
					this.usePlayerItem(player, hand, stack);
					this.ageUp((int) ((float) (-i / 20) * 0.1F), true);
					return InteractionResult.sidedSuccess(this.level.isClientSide);
				}

				if (this.level.isClientSide) {
					return InteractionResult.CONSUME;
				}
			}

			if (!this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive() && !this.isInWater()) {
				this.setOwnerUUID(player.getUUID());
				if (!level.isClientSide())
					this.setOrderedToSit(true);
				return InteractionResult.SUCCESS;
			}

			if (this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive()) {
				if (!level.isClientSide())
					this.setOrderedToSit(false);
				return InteractionResult.SUCCESS;
			}

			if (!player.isSecondaryUseActive()) {
				if (!level.isClientSide()) {
					openGui((ServerPlayer) player);
				}
				return InteractionResult.SUCCESS;
			}

		} else if (stack.is(EnvironmentalItemTags.SLABFISH_TAME_ITEMS)) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.setOrderedToSit(true);
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		}

		return super.mobInteract(player, hand);
	}

	@Override
	protected void onInsideBlock(BlockState state) {
		if (state.getBlock() == Blocks.WATER) {
			if (this.getSlabfishOverlay() != SlabfishOverlay.NONE)
				this.setSlabfishOverlay(SlabfishOverlay.NONE);
		}
	}

	public boolean isPartying() {
		return this.isPartying;
	}

	public void die(DamageSource cause) {
		if (ForgeHooks.onLivingDeath(this, cause))
			return;
		if (!this.isRemoved() && !this.dead) {
			Entity entity = cause.getEntity();
			LivingEntity livingentity = this.getKillCredit();
			if (this.deathScore >= 0 && livingentity != null) {
				livingentity.awardKillScore(this, this.deathScore, cause);
			}

			if (this.isSleeping()) {
				this.stopSleeping();
			}

			this.dead = true;
			this.getCombatTracker().recheckStatus();
			if (this.level instanceof ServerLevel serverLevel) {
				if (entity == null || entity.wasKilled(serverLevel, this)) {
					this.gameEvent(GameEvent.ENTITY_DIE);
					this.dropAllDeathLoot(cause);
					this.createWitherRose(livingentity);
				}

				this.level.broadcastEntityEvent(this, (byte) 3);
			}
			this.setPose(Pose.DYING);
		}
	}

	@Override
	public void setRecordPlayingNearby(BlockPos pos, boolean isPartying) {
		this.jukeboxPosition = pos;
		this.isPartying = isPartying;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.jukeboxPosition == null || !this.jukeboxPosition.closerThan(this.blockPosition(), 3.46D) || this.level.getBlockState(jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
			this.isPartying = false;
			this.jukeboxPosition = null;
		}

		if (this.getVehicle() != null && this.getVehicle().isShiftKeyDown())
			this.stopRiding();
		if (this.isInWater() && this.getVehicle() != null)
			this.stopRiding();

		if (this.isMoving()) {
			if (this.hasEffect(MobEffects.MOVEMENT_SPEED) && random.nextInt(3) == 0 && !this.isInWater()) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getY() + 0.2D, this.getRandomZ(0.5D), d0, d1, d2);
			}
		}

		if (this.random.nextFloat() < 0.1F && this.getSlabfishOverlay() == SlabfishOverlay.MUDDY) {
			for (int i = 0; i < this.random.nextInt(2) + 1; ++i) {
				this.doParticle(this.level, this.getX() - (double) 0.3F, this.getX() + (double) 0.3F, this.getZ() - (double) 0.3F, this.getZ() + (double) 0.3F, this.getY(0.5D), new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(EnvironmentalItems.MUD_BALL.get())));
			}
		}

		List<Player> playerList = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));

		for (Player player : playerList) {
			if (player instanceof ServerPlayer serverPlayer) {
				if (!this.level.isClientSide()) {
					EnvironmentalCriteriaTriggers.SLABFISH.trigger(serverPlayer, this);
				}
			}
		}

		this.refreshDimensions();
		this.setCanPickUpLoot(this.hasBackpack());

		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround) {
			if (!this.isInWater() && this.getDeltaMovement().y < 0)
				this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0.6, 1));
			if (this.wingRotDelta < 1.0F)
				this.wingRotDelta = 1.0F;
		}

		this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
		this.wingRotation += this.wingRotDelta * 2.0F;

	}

	private boolean isMoving() {
		return this.getDeltaMovement().x() > 0 || this.getDeltaMovement().y() > 0 || this.getDeltaMovement().z() > 0;
	}

	@Override
	public double getMyRidingOffset() {
		if (this.getVehicle() != null) {
			if (this.getVehicle() instanceof Boat)
				return this.isBaby() ? 0.43D : 0.3D;
			else
				return this.isBaby() ? 0.70F : 0.52F;
		}
		return super.getMyRidingOffset();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			if (this.getVehicle() != null)
				this.stopRiding();
			Entity entity = source.getEntity();
			this.setOrderedToSit(false);

			if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.hurt(source, amount);
		}
	}

	// DETAILS //

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSoundEvents.SLABFISH_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.SLABFISH_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSoundEvents.SLABFISH_STEP.get(), 0.15F, 1.0F);
	}

	protected void playBackpackSound() {
		this.playSound(EnvironmentalSoundEvents.SLABFISH_BACKPACK.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	protected void playSweaterSound() {
		this.playSound(EnvironmentalSoundEvents.SLABFISH_SWEATER.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	protected void playBurpSound() {
		this.playSound(EnvironmentalSoundEvents.SLABFISH_BURP.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	private void doParticle(Level world, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double p_226397_10_, ParticleOptions p_226397_12_) {
		world.addParticle(p_226397_12_, Mth.lerp(world.random.nextDouble(), p_226397_2_, p_226397_4_), p_226397_10_, Mth.lerp(world.random.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
	}

	private void particleCloud(ParticleOptions particle) {
		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private void dropItem(ItemLike item) {
		ItemEntity itementity = this.spawnAtLocation(item, 0);
		if (itementity != null) {
			itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
		}
	}

	// STATS //

	@Override
	public Slabfish getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		Slabfish baby = EnvironmentalEntityTypes.SLABFISH.get().create(world);
		UUID uuid = this.getOwnerUUID();
		if (baby != null) {
			baby.setSlabfishType(this.getSlabfishType());
			if (uuid != null) {
				baby.setOwnerUUID(uuid);
				baby.setTame(true);
			}
		}
		return baby;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return !Ingredient.of(EnvironmentalItemTags.SLABFISH_TAME_ITEMS).test(stack) && Ingredient.of(EnvironmentalItemTags.SLABFISH_FOOD).test(stack);
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return this.isInWater() ? this.isBaby() ? SIZE_SWIMMING_CHILD : SIZE_SWIMMING : (this.isInSittingPose() || this.getVehicle() != null) ? this.isBaby() ? SIZE_SITTING_CHILD : SIZE_SITTING : super.getDimensions(pose);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isInSittingPose() ? sizeIn.height * 0.6F : this.isInWater() ? (this.isBaby() ? sizeIn.height * 1.4F : sizeIn.height * 0.855F) : sizeIn.height * 0.8F;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 5;
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.96F;
	}

	// SLABFISH TYPE //

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		if (!this.level.isClientSide() && name != null && !this.getSlabfishType().equals(SlabfishManager.GHOST)) {
			super.setCustomName(name);
			SlabfishManager slabfishManager = SlabfishManager.get(this.level);
			SlabfishType currentType = slabfishManager.getSlabfishType(this.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
			slabfishManager.getSlabfishType(SlabfishConditionContext.rename(this)).ifPresent(newType -> {
				if (!currentType.isTradable() && newType.isTradable())
					return;
				if (newType.getRegistryName() != this.getSlabfishType())
					this.setSlabfishType(newType.getRegistryName());
			});
		}
	}

	public void playTransformSound() {
		this.playSound(EnvironmentalSoundEvents.SLABFISH_TRANSFORM.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
		this.particleCloud(ParticleTypes.CAMPFIRE_COSY_SMOKE);
	}

	@Override
	public void thunderHit(ServerLevel world, LightningBolt lightningBolt) {
		UUID uuid = lightningBolt.getUUID();
		if (!world.isClientSide() && !uuid.equals(this.lightningUUID) && !this.getSlabfishType().equals(SlabfishManager.GHOST)) {
			SlabfishManager slabfishManager = SlabfishManager.get(world);
			SlabfishType currentType = slabfishManager.getSlabfishType(this.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
			slabfishManager.getSlabfishType(SlabfishConditionContext.lightning(this)).ifPresent(newType -> {
				if (!currentType.isTradable() && newType.isTradable())
					return;
				this.setSlabfishType(newType.getRegistryName());
				this.lightningUUID = uuid;
				this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 1.0F);
			});
		}
	}

	public ResourceLocation getSlabfishType() {
		return this.entityData.get(SLABFISH_TYPE);
	}

	public void setSlabfishType(ResourceLocation type) {
		this.entityData.set(SLABFISH_TYPE, type);
		this.updateBackpack();
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		if (reason == MobSpawnType.BUCKET) {
			return spawnDataIn;
		} else {
			if (spawnDataIn instanceof Slabfish.SlabfishData) {
				this.setSlabfishType(((Slabfish.SlabfishData) spawnDataIn).type);
				return spawnDataIn;
			} else {
				SlabfishManager slabfishManager = SlabfishManager.get(world);
				Optional<SlabfishType> type = slabfishManager.getSlabfishType(SlabfishConditionContext.spawned(this));

				spawnDataIn = new SlabfishData(type.orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName());
				this.setSlabfishType(type.orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName());
				return super.finalizeSpawn(world, difficulty, reason, spawnDataIn, dataTag);
			}
		}
	}

	public static class SlabfishData extends AgeableMobGroupData implements SpawnGroupData {
		public final ResourceLocation type;

		public SlabfishData(ResourceLocation type) {
			super(true);
			this.type = type;
		}
	}

	// BUCKETING //

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBucket();
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean value) {
		this.entityData.set(FROM_BUCKET, value);
	}

	@Override
	public void loadFromBucketTag(CompoundTag tag) {
		Bucketable.loadDefaultDataFromBucketTag(this, tag);

		if (tag.contains("SlabfishType", Tag.TAG_STRING)) {
			if (tag.contains("Age"))
				this.setAge(tag.getInt("Age"));
			if (tag.contains("Owner")) {
				this.setTame(true);
				this.setOwnerUUID(tag.getUUID("Owner"));
			}
			this.setSlabfishType(new ResourceLocation(tag.getString("SlabfishType")));

			if (tag.contains("BackpackType", Tag.TAG_STRING))
				this.setBackpack(new ResourceLocation(tag.getString("BackpackType")));

			if (tag.contains("SweaterType", Tag.TAG_STRING))
				this.setBackpack(new ResourceLocation(tag.getString("SweaterType")));

			this.slabfishBackpack.read(tag);
			this.updateSweater();
			this.updateBackpack();
		} else {
			SlabfishManager slabfishManager = SlabfishManager.get(this.level);
			SlabfishRarity rarity = SlabfishRarity.byChance(this.level.getRandom().nextFloat());
			Optional<SlabfishType> type = slabfishManager.getRandomSlabfishType(s -> s.isModLoaded() && s.isTradable() && s.getRarity() == rarity, this.level.getRandom());

			this.setSlabfishType(type.orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName());
		}
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_FISH;
	}

	@Override
	public void saveToBucketTag(ItemStack bucket) {
		Bucketable.saveDefaultDataToBucketTag(this, bucket);

		if (this.hasCustomName()) {
			bucket.setHoverName(this.getCustomName());
		}

		CompoundTag compound = bucket.getOrCreateTag();

		compound.putFloat("Health", this.getHealth());
		compound.putInt("Age", this.getAge());

		if (this.getOwnerUUID() != null && this.isTame())
			compound.putUUID("Owner", this.getOwnerUUID());

		compound.putString("SlabfishType", this.getSlabfishType().toString());

		if (this.hasBackpack())
			compound.putString("BackpackType", this.getBackpack().toString());

		if (this.hasSweater())
			compound.putString("SweaterType", this.getSweater().toString());

		this.slabfishBackpack.write(compound);
	}

	// DATA //

	private void updateSweater() {
		if (!this.level.isClientSide()) {
			ItemStack sweaterStack = this.slabfishBackpack.getItem(0);
			SweaterType sweaterType = SlabfishManager.get(this.level).getSweaterType(sweaterStack).orElse(SlabfishManager.EMPTY_SWEATER);
			if (!sweaterType.isEmpty()) {
				this.setSweater(sweaterType.getRegistryName());
			} else {
				this.setSweater(SlabfishManager.EMPTY_SWEATER.getRegistryName());
			}
		}
	}

	private void updateBackpack() {
		if (!this.level.isClientSide()) {
			SlabfishManager slabfishManager = SlabfishManager.get(this.level);
			SlabfishType slabfishType = slabfishManager.getSlabfishType(this.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);
			ResourceLocation backpackType = slabfishType.getCustomBackpack();

			if (backpackType != null && slabfishManager.getBackpackType(backpackType).isPresent()) {
				this.setBackpack(backpackType);
				if (!this.slabfishBackpack.getItem(2).isEmpty())
					this.spawnAtLocation(this.slabfishBackpack.removeItemNoUpdate(2));
			} else {
				ItemStack backpackColorStack = this.slabfishBackpack.getItem(2);
				this.setBackpack(SlabfishManager.get(this.level).getBackpackType(backpackColorStack).orElse(SlabfishManager.BROWN_BACKPACK).getRegistryName());
			}
		}
	}

	public boolean hasBackpack() {
		return this.getEntityData().get(HAS_BACKPACK);
	}

	public void setHasBackpack(boolean hasBackpack) {
		this.getEntityData().set(HAS_BACKPACK, hasBackpack);
	}

	public void setBackpack(ResourceLocation backpackType) {
		this.entityData.set(BACKPACK, backpackType);
	}

	public ResourceLocation getBackpack() {
		return this.entityData.get(BACKPACK);
	}

	public boolean hasSweater() {
		return !this.entityData.get(SWEATER).equals(SlabfishManager.EMPTY_SWEATER.getRegistryName());
	}

	public void setSweater(ResourceLocation sweaterType) {
		this.entityData.set(SWEATER, sweaterType);
	}

	public ResourceLocation getSweater() {
		return this.entityData.get(SWEATER);
	}

	public SlabfishOverlay getSlabfishOverlay() {
		return SlabfishOverlay.byId(this.entityData.get(SLABFISH_OVERLAY));
	}

	public void setSlabfishOverlay(SlabfishOverlay typeId) {
		this.entityData.set(SLABFISH_OVERLAY, typeId.getId());
	}

	public void setEffigy(@Nullable BlockPos beamTarget) {
		this.getEntityData().set(EFFIGY, Optional.ofNullable(beamTarget));
	}

	@Nullable
	public BlockPos getEffigy() {
		return this.getEntityData().get(EFFIGY).orElse(null);
	}

	// INVENTORY //

	public void openGui(ServerPlayer player) {
		if (player.containerMenu != player.inventoryMenu)
			player.closeContainer();

		player.nextContainerCounter();
		Environmental.PLAY.send(PacketDistributor.PLAYER.with(() -> player), new SOpenSlabfishInventoryMessage(this, player.containerCounter));
		player.containerMenu = new SlabfishInventoryMenu(player.containerCounter, player.getInventory(), this.slabfishBackpack, this);
		player.initMenu(player.containerMenu);
		MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, player.containerMenu));
	}

	@Override
	public boolean canHoldItem(ItemStack stack) {
		return this.hasBackpack();
	}

	@Override
	public boolean canTakeItem(ItemStack stack) {
		return this.hasBackpack();
	}

	@Override
	protected void dropEquipment() {
		ItemStack itemstack = this.slabfishBackpack.removeItemNoUpdate(0);
		if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
			this.spawnAtLocation(itemstack);
		}
		this.dropBackpack();
	}

	protected void dropBackpack() {
		super.dropEquipment();
		if (this.hasBackpack()) {
			if (this.slabfishBackpack != null) {
				for (int i = this.slabfishBackpack.getContainerSize(); i > 0; --i) {
					ItemStack itemstack = this.slabfishBackpack.removeItemNoUpdate(i);
					if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
						this.spawnAtLocation(itemstack);
					}
				}
			}
		}
	}

	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		if (itemEntity.getPersistentData().getBoolean("EffigyItem") || itemEntity.getThrower() == this.getUUID())
			return;

		ItemStack itemstack = itemEntity.getItem();
		if (this.hasBackpack()) {
			ItemStack stack = this.slabfishBackpack.addItem(itemstack, 3, this.slabfishBackpack.getContainerSize());
			if (!ItemStack.matches(itemstack, stack))
				this.take(itemEntity, itemstack.getCount() - stack.getCount());
			if (stack.isEmpty())
				itemEntity.discard();
			else
				itemEntity.setItem(stack);
		}
	}

	private boolean clearing;

	@Override
	public void containerChanged(Container invBasic) {
		if (this.clearing)
			return;

		boolean hadSweater = this.hasSweater();
		boolean hadBackpack = this.hasBackpack();
		this.updateSweater();

		boolean hasBackpack = !this.slabfishBackpack.getItem(1).isEmpty() && this.slabfishBackpack.getItem(1).is(Tags.Items.CHESTS_WOODEN);

		if (!hadSweater && this.hasSweater())
			this.playSweaterSound();
		if (hadBackpack != hasBackpack)
			this.playBackpackSound();
		if (hadBackpack && !hasBackpack) {
			this.clearing = true;
			this.dropBackpack();
			this.clearing = false;
		}

		this.setHasBackpack(hasBackpack);
		this.updateBackpack();

		if (this.hasBackpack()) {
			this.backpackFull = true;
			for (int i = 3; i < this.slabfishBackpack.getContainerSize(); i++) {
				ItemStack stack = this.slabfishBackpack.getItem(i);
				if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize()) {
					this.backpackFull = false;
					break;
				}
			}
		} else {
			this.backpackFull = false;
		}
	}

	private LazyOptional<?> itemHandler = null;

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
			return itemHandler.cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null) {
			LazyOptional<?> oldHandler = itemHandler;
			itemHandler = null;
			oldHandler.invalidate();
		}
	}

	// MISC //


	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 8) {
			this.particleCloud(EnvironmentalParticleTypes.SLABFISH_FINDS_EFFIGY.get());
		} else super.handleEntityEvent(id);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(EnvironmentalItems.SLABFISH_BUCKET.get());
	}
}
