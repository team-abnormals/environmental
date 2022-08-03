package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.abnormals_core.core.api.IBucketableEntity;
import com.minecraftabnormals.environmental.common.entity.goals.*;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.minecraftabnormals.environmental.common.entity.util.SlabfishRarity;
import com.minecraftabnormals.environmental.common.inventory.SlabfishInventory;
import com.minecraftabnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.minecraftabnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.common.slabfish.SweaterType;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.minecraftabnormals.environmental.core.other.EnvironmentalTags;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalParticles;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.AgeableEntity.AgeableData;

public class SlabfishEntity extends TameableEntity implements IInventoryChangedListener, IBucketableEntity {

	private static final DataParameter<ResourceLocation> SLABFISH_TYPE = EntityDataManager.defineId(SlabfishEntity.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);
	private static final DataParameter<Integer> SLABFISH_OVERLAY = EntityDataManager.defineId(SlabfishEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.defineId(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<BlockPos>> EFFIGY = EntityDataManager.defineId(SlabfishEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);

	private static final DataParameter<ResourceLocation> BACKPACK = EntityDataManager.defineId(SlabfishEntity.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);
	private static final DataParameter<Boolean> HAS_BACKPACK = EntityDataManager.defineId(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<ResourceLocation> SWEATER = EntityDataManager.defineId(SlabfishEntity.class, EnvironmentalDataSerializers.RESOURCE_LOCATION);

	public static final EntitySize SIZE_SWIMMING = EntitySize.fixed(0.7F, 0.6F);
	public static final EntitySize SIZE_SITTING = EntitySize.fixed(0.45F, 0.6F);
	public static final EntitySize SIZE_SWIMMING_CHILD = EntitySize.fixed(0.35F, 0.3F);
	public static final EntitySize SIZE_SITTING_CHILD = EntitySize.fixed(0.225F, 0.3F);

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

	public SlabfishEntity(EntityType<? extends SlabfishEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
		this.slabfishBackpack = new SlabfishInventory(this);
		this.slabfishBackpack.addListener(this);
		this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.slabfishBackpack));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 15.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new SitGoal(this));

		this.goalSelector.addGoal(2, new SlabbyFindEffigyGoal(this));
		this.goalSelector.addGoal(2, new SlabbyPraiseEffigyGoal(this));

		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, entity -> EntityTypeTags.RAIDERS.contains(entity.getType()), 15.0F, 1.0D, 1.5D, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test));

		this.goalSelector.addGoal(4, new SlabbyBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new SlabbyGrabItemGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, false, Ingredient.of(EnvironmentalTags.Items.SLABFISH_FOOD)));
		this.goalSelector.addGoal(8, new SlabbyFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
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
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("SlabfishType", this.getSlabfishType().toString());
		compound.putInt("SlabfishOverlay", this.getSlabfishOverlay().getId());
		if (this.hasBackpack())
			compound.putString("BackpackType", this.getBackpack().toString());
		compound.putBoolean("FromBucket", this.isFromBucket());

		if (this.getEffigy() != null) {
			compound.put("Effigy", NBTUtil.writeBlockPos(this.getEffigy()));
		}

		this.slabfishBackpack.write(compound);
	}

	@Override
	public boolean canBeLeashed(PlayerEntity player) {
		return !this.isLeashed();
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setSlabfishType(new ResourceLocation(compound.getString("SlabfishType")));
		this.setSlabfishOverlay(SlabfishOverlay.byId(compound.getInt("SlabfishOverlay")));
		this.setBackpack(compound.contains("BackpackType", Constants.NBT.TAG_STRING) ? new ResourceLocation(compound.getString("BackpackType")) : SlabfishManager.BROWN_BACKPACK.getRegistryName());
		this.setFromBucket(compound.getBoolean("FromBucket"));

		if (compound.contains("Effigy", Constants.NBT.TAG_COMPOUND)) {
			this.setEffigy(NBTUtil.readBlockPos(compound.getCompound("Effigy")));
		}

		this.slabfishBackpack.read(compound);
		this.updateSweater();
		this.updateBackpack();
		this.setHasBackpack(!this.slabfishBackpack.getItem(1).isEmpty() && this.slabfishBackpack.getItem(1).getItem().is(Tags.Items.CHESTS_WOODEN));
	}

	// GENERAL //

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		SlabfishManager slabfishManager = SlabfishManager.get(this.level);
		SlabfishType slabfishType = slabfishManager.getSlabfishType(this.getSlabfishType()).orElse(SlabfishManager.DEFAULT_SLABFISH);

		if (item == Items.WATER_BUCKET && this.isAlive()) {
			if (this.getAge() < 0) {
				return ActionResultType.FAIL;
			}
			if (this.hasBackpack())
				this.dropBackpack();
			this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
			stack.shrink(1);
			ItemStack stack1 = this.getBucket();
			this.setBucketData(stack1);

			if (!this.level.isClientSide) {
				CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, stack1);
			}

			if (stack.isEmpty()) {
				player.setItemInHand(hand, stack1);
			} else if (!player.inventory.add(stack1)) {
				player.drop(stack1, false);
			}
			this.remove();
			return ActionResultType.SUCCESS;
		}

		if (item.is(ItemTags.CREEPER_DROP_MUSIC_DISCS)) {
			this.usePlayerItem(player, stack);
			this.playBurpSound();
			this.particleCloud(ParticleTypes.NOTE);
			this.dropItem(EnvironmentalItems.MUSIC_DISC_SLABRAVE.get());
			return ActionResultType.SUCCESS;
		}

		if (this.isTame()) {
			if (this.hasBackpack() && (slabfishType.getCustomBackpack() == null || !slabfishManager.getBackpackType(slabfishType.getCustomBackpack()).isPresent()) && slabfishManager.getBackpackType(stack).isPresent() && !slabfishManager.getBackpackType(stack).orElse(SlabfishManager.BROWN_BACKPACK).getRegistryName().equals(this.getBackpack())) {
				if (!this.level.isClientSide()) {
					ItemStack previousBackpack = this.slabfishBackpack.getItem(2);

					if (!previousBackpack.isEmpty()) {
						InventoryHelper.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousBackpack.copy());
						this.slabfishBackpack.removeItemNoUpdate(2);
					}

					this.slabfishBackpack.setItem(2, new ItemStack(item));
					this.usePlayerItem(player, stack);
				}
				return ActionResultType.SUCCESS;
			}

			if (slabfishManager.getSweaterType(stack).isPresent() && !player.isSecondaryUseActive() && (!this.hasSweater() || !slabfishManager.getSweaterType(stack).orElse(SlabfishManager.EMPTY_SWEATER).getRegistryName().equals(this.getSweater()))) {
				if (!this.level.isClientSide()) {
					ItemStack previousSweater = this.slabfishBackpack.getItem(0);
					if (!previousSweater.isEmpty()) {
						InventoryHelper.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousSweater.copy());
						this.slabfishBackpack.removeItemNoUpdate(0);
					}
					this.slabfishBackpack.setItem(0, new ItemStack(item));
					this.usePlayerItem(player, stack);
				}
				return ActionResultType.SUCCESS;
			}

			if (item.is(Tags.Items.CHESTS_WOODEN) && !this.hasBackpack()) {
				if (!this.level.isClientSide()) {
					this.slabfishBackpack.setItem(1, new ItemStack(item));
					this.usePlayerItem(player, stack);
					if (player instanceof ServerPlayerEntity)
						EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.trigger((ServerPlayerEntity) player);
				}
				return ActionResultType.SUCCESS;
			}

			if (item == Items.SHEARS && this.hasSweater() && !player.isSecondaryUseActive()) {
				ItemStack previousSweater = this.slabfishBackpack.getItem(0);
				if (!previousSweater.isEmpty()) {
					InventoryHelper.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), previousSweater.copy());
					this.slabfishBackpack.removeItemNoUpdate(0);
				}
				return ActionResultType.SUCCESS;
			}

			if (player.isSecondaryUseActive() && item == Items.SHEARS && this.hasBackpack()) {
				this.dropBackpack();
				return ActionResultType.SUCCESS;
			}

			if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
				this.usePlayerItem(player, stack);
				level.playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSounds.SLABFISH_EAT.get(), SoundCategory.NEUTRAL, 1F, 1F, true);
				this.heal(item.getFoodProperties().getNutrition());
				this.particleCloud(ParticleTypes.COMPOSTER);
				return ActionResultType.SUCCESS;
			}

			if (Ingredient.of(EnvironmentalTags.Items.SLABFISH_SNACKS).test(stack)) {
				stack.finishUsingItem(this.level, this);
				this.usePlayerItem(player, stack);
				level.playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSounds.SLABFISH_EAT.get(), SoundCategory.NEUTRAL, 1F, 1F, true);
				return ActionResultType.SUCCESS;
			}

			if (this.isFood(stack)) {
				int i = this.getAge();
				if (!this.level.isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(player, stack);
					this.setInLove(player);
					return ActionResultType.SUCCESS;
				}

				if (this.isBaby()) {
					this.usePlayerItem(player, stack);
					this.ageUp((int) ((float) (-i / 20) * 0.1F), true);
					return ActionResultType.sidedSuccess(this.level.isClientSide);
				}

				if (this.level.isClientSide) {
					return ActionResultType.CONSUME;
				}
			}

			if (!this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive() && !this.isInWater()) {
				this.setOwnerUUID(player.getUUID());
				if (!level.isClientSide())
					this.setOrderedToSit(true);
				return ActionResultType.SUCCESS;
			}

			if (this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive()) {
				if (!level.isClientSide())
					this.setOrderedToSit(false);
				return ActionResultType.SUCCESS;
			}

			if (!player.isSecondaryUseActive()) {
				if (!level.isClientSide()) {
					openGui((ServerPlayerEntity) player);
				}
				return ActionResultType.SUCCESS;
			}

		} else if (item.is(EnvironmentalTags.Items.SLABFISH_TAME_ITEMS)) {
			if (!player.abilities.instabuild) {
				stack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.setOrderedToSit(true);
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return ActionResultType.SUCCESS;
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
		if (!this.removed && !this.dead) {
			Entity entity = cause.getEntity();
			LivingEntity livingentity = this.getKillCredit();
			if (this.deathScore >= 0 && livingentity != null) {
				livingentity.awardKillScore(this, this.deathScore, cause);
			}

			if (entity != null) {
				entity.killed((ServerWorld) this.level, this);
			}

			if (this.isSleeping()) {
				this.stopSleeping();
			}

			this.dead = true;
			this.getCombatTracker().recheckStatus();
			if (!this.level.isClientSide) {
				this.dropAllDeathLoot(cause);
				this.createWitherRose(livingentity);
			}

			this.level.broadcastEntityEvent(this, (byte) 3);
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

		if (this.jukeboxPosition == null || !this.jukeboxPosition.closerThan(this.position(), 3.46D) || this.level.getBlockState(jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
			this.isPartying = false;
			this.jukeboxPosition = null;
		}

		if (this.getVehicle() != null && this.getVehicle().isShiftKeyDown())
			this.stopRiding();
		if (this.isInWater() && this.getVehicle() != null)
			this.stopRiding();

		if (this.isMoving()) {
			if (this.hasEffect(Effects.MOVEMENT_SPEED) && random.nextInt(3) == 0 && !this.isInWater()) {
				double d0 = this.random.nextGaussian() * 0.02D;
				double d1 = this.random.nextGaussian() * 0.02D;
				double d2 = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getY() + 0.2D, this.getRandomZ(0.5D), d0, d1, d2);
			}
		}

		if (this.random.nextFloat() < 0.1F && this.getSlabfishOverlay() == SlabfishOverlay.MUDDY) {
			for (int i = 0; i < this.random.nextInt(2) + 1; ++i) {
				this.doParticle(this.level, this.getX() - (double) 0.3F, this.getX() + (double) 0.3F, this.getZ() - (double) 0.3F, this.getZ() + (double) 0.3F, this.getY(0.5D), new ItemParticleData(ParticleTypes.ITEM, new ItemStack(EnvironmentalItems.MUD_BALL.get())));
			}
		}

		List<PlayerEntity> playerList = this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));

		for (PlayerEntity player : playerList) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
				if (!this.level.isClientSide()) {
					EnvironmentalCriteriaTriggers.SLABFISH.trigger(serverplayerentity, this);
				}
			}
		}

		this.refreshDimensions();
		this.setCanPickUpLoot(this.hasBackpack());

		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
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
			if (this.getVehicle() instanceof BoatEntity)
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

			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.hurt(source, amount);
		}
	}

	// DETAILS //

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return EnvironmentalSounds.SLABFISH_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSounds.SLABFISH_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(EnvironmentalSounds.SLABFISH_STEP.get(), 0.15F, 1.0F);
	}

	protected void playBackpackSound() {
		this.playSound(EnvironmentalSounds.SLABFISH_BACKPACK.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	protected void playSweaterSound() {
		this.playSound(EnvironmentalSounds.SLABFISH_SWEATER.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	protected void playBurpSound() {
		this.playSound(EnvironmentalSounds.SLABFISH_BURP.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	private void doParticle(World world, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double p_226397_10_, IParticleData p_226397_12_) {
		world.addParticle(p_226397_12_, MathHelper.lerp(world.random.nextDouble(), p_226397_2_, p_226397_4_), p_226397_10_, MathHelper.lerp(world.random.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
	}

	private void particleCloud(IParticleData particle) {
		for (int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private void dropItem(IItemProvider item) {
		ItemEntity itementity = this.spawnAtLocation(item, 0);
		if (itementity != null) {
			itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
		}
	}

	// STATS //

	@Override
	public SlabfishEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		SlabfishEntity baby = EnvironmentalEntities.SLABFISH.get().create(world);
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
		return !Ingredient.of(EnvironmentalTags.Items.SLABFISH_TAME_ITEMS).test(stack) && Ingredient.of(EnvironmentalTags.Items.SLABFISH_FOOD).test(stack);
	}

	@Override
	public EntitySize getDimensions(Pose pose) {
		return this.isInWater() ? this.isBaby() ? SIZE_SWIMMING_CHILD : SIZE_SWIMMING : (this.isInSittingPose() || this.getVehicle() != null) ? this.isBaby() ? SIZE_SITTING_CHILD : SIZE_SITTING : super.getDimensions(pose);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
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
	public boolean causeFallDamage(float distance, float damageMultiplier) {
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
	public void setCustomName(@Nullable ITextComponent name) {
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
		this.playSound(EnvironmentalSounds.SLABFISH_TRANSFORM.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
		this.particleCloud(ParticleTypes.CAMPFIRE_COSY_SMOKE);
	}

	@Override
	public void thunderHit(ServerWorld world, LightningBoltEntity lightningBolt) {
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
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(world, difficulty, reason, spawnDataIn, dataTag);

		if (dataTag != null && dataTag.contains("SlabfishType", Constants.NBT.TAG_STRING)) {
			if (dataTag.contains("Health"))
				this.setHealth(dataTag.getFloat("Health"));
			if (dataTag.contains("Age"))
				this.setAge(dataTag.getInt("Age"));
			if (dataTag.contains("Owner")) {
				this.setTame(true);
				this.setOwnerUUID(dataTag.getUUID("Owner"));
			}
			this.setSlabfishType(new ResourceLocation(dataTag.getString("SlabfishType")));

			if (dataTag.contains("BackpackType", Constants.NBT.TAG_STRING))
				this.setBackpack(new ResourceLocation(dataTag.getString("BackpackType")));

			this.slabfishBackpack.read(dataTag);
			this.updateSweater();
			this.updateBackpack();

			return spawnDataIn;
		}

		if (spawnDataIn instanceof SlabfishEntity.SlabfishData) {
			this.setSlabfishType(((SlabfishEntity.SlabfishData) spawnDataIn).type);
			return spawnDataIn;
		}

		SlabfishManager slabfishManager = SlabfishManager.get(world);
		SlabfishRarity rarity = SlabfishRarity.byChance(world.getRandom().nextFloat());
		Optional<SlabfishType> type = reason == SpawnReason.BUCKET ? slabfishManager.getRandomSlabfishType(s -> s.isModLoaded() && s.isTradable() && s.getRarity() == rarity, world.getRandom()) : slabfishManager.getSlabfishType(SlabfishConditionContext.spawned(this));

		if (!this.isFromBucket()) {
			spawnDataIn = new SlabfishData(type.orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName());
		}

		this.setSlabfishType(type.orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName());
		return spawnDataIn;
	}

	public static class SlabfishData extends AgeableData implements ILivingEntityData {
		public final ResourceLocation type;

		public SlabfishData(ResourceLocation type) {
			super(0.2F);
			this.type = type;
		}
	}

	// BUCKETING //

	@Override
	public boolean requiresCustomPersistence() {
		return this.isFromBucket();
	}

	public boolean isFromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	public void setFromBucket(boolean value) {
		this.entityData.set(FROM_BUCKET, value);
	}

	protected void setBucketData(ItemStack bucket) {
		if (this.hasCustomName()) {
			bucket.setHoverName(this.getCustomName());
		}

		CompoundNBT compound = bucket.getOrCreateTag();

		compound.putFloat("Health", this.getHealth());
		compound.putInt("Age", this.getAge());

		if (this.getOwnerUUID() != null && this.isTame())
			compound.putUUID("Owner", this.getOwnerUUID());

		compound.putString("SlabfishType", this.getSlabfishType().toString());
		if (this.hasBackpack())
			compound.putString("BackpackType", this.getBackpack().toString());

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

	public void openGui(ServerPlayerEntity player) {

		if (player.containerMenu != player.inventoryMenu)
			player.closeContainer();

		player.nextContainerCounter();
		Environmental.PLAY.send(PacketDistributor.PLAYER.with(() -> player), new SOpenSlabfishInventoryMessage(this, player.containerCounter));
		player.containerMenu = new SlabfishInventoryContainer(player.containerCounter, player.inventory, this.slabfishBackpack, this);
		player.containerMenu.addSlotListener(player);
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
				itemEntity.remove();
			else
				itemEntity.setItem(stack);
		}
	}

	private boolean clearing;

	@Override
	public void containerChanged(IInventory invBasic) {
		if (this.clearing)
			return;

		boolean hadSweater = this.hasSweater();
		boolean hadBackpack = this.hasBackpack();
		this.updateSweater();

		boolean hasBackpack = !this.slabfishBackpack.getItem(1).isEmpty() && this.slabfishBackpack.getItem(1).getItem().is(Tags.Items.CHESTS_WOODEN);

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
	public void remove(boolean keepData) {
		super.remove(keepData);
		if (!keepData && itemHandler != null) {
			itemHandler.invalidate();
			itemHandler = null;
		}
	}

	// MISC //


	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == 8) {
			this.particleCloud(EnvironmentalParticles.SLABFISH_FINDS_EFFIGY.get());
		} else super.handleEntityEvent(id);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public ItemStack getBucket() {
		return new ItemStack(EnvironmentalItems.SLABFISH_BUCKET.get());
	}
}
