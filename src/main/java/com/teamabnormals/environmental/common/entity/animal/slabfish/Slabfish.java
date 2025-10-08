package com.teamabnormals.environmental.common.entity.animal.slabfish;

import com.teamabnormals.environmental.common.entity.ai.goal.slabfish.SlabbyFollowParentGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.slabfish.SlabbyGrabItemGoal;
import com.teamabnormals.environmental.common.inventory.SlabfishInventory;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.network.message.OpenSlabfishInventoryPayload;
import com.teamabnormals.environmental.common.slabfish.SlabfishBackpack;
import com.teamabnormals.environmental.common.slabfish.SlabfishHelper;
import com.teamabnormals.environmental.common.slabfish.SlabfishSweater;
import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.teamabnormals.environmental.core.other.EnvironmentalDataSerializers;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalItemTags;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishBackpacks;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishOverlays;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class Slabfish extends TamableAnimal implements ContainerListener, Bucketable, VariantHolder<Holder<SlabfishVariant>> {
	private static final EntityDataAccessor<Holder<SlabfishVariant>> VARIANT = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.SLABFISH_VARIANT.get());
	private static final EntityDataAccessor<Optional<Holder<SlabfishBackpack>>> BACKPACK = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.SLABFISH_BACKPACK_VARIANT.get());
	private static final EntityDataAccessor<Optional<Holder<SlabfishSweater>>> SWEATER = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.SLABFISH_SWEATER_VARIANT.get());
	private static final EntityDataAccessor<Optional<Holder<SlabfishOverlay>>> OVERLAY = SynchedEntityData.defineId(Slabfish.class, EnvironmentalDataSerializers.SLABFISH_OVERLAY.get());

	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Slabfish.class, EntityDataSerializers.BOOLEAN);

	public static final EntityDimensions SIZE_SWIMMING = EntityDimensions.scalable(0.7F, 0.6F).withEyeHeight(0.513F);
	public static final EntityDimensions SIZE_SITTING = EntityDimensions.scalable(0.45F, 0.6F).withEyeHeight(0.36F);
	public static final EntityDimensions SIZE_SWIMMING_CHILD = EntityDimensions.scalable(0.35F, 0.3F).withEyeHeight(0.42F);
	public static final EntityDimensions SIZE_SITTING_CHILD = EntityDimensions.scalable(0.225F, 0.3F).withEyeHeight(0.18F);

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
		this.setPathfindingMalus(PathType.WATER, 0.0F);
		this.slabfishBackpack = new SlabfishInventory(this);
		this.slabfishBackpack.addListener(this);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 15.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, entity -> entity.getType().is(EntityTypeTags.RAIDERS), 15.0F, 1.0D, 1.25D, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new SlabbyGrabItemGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(EnvironmentalItemTags.SLABFISH_FOOD), false));
		this.goalSelector.addGoal(8, new SlabbyFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		Registry<SlabfishVariant> slabfishRegistry = SlabfishHelper.slabfishTypes(this.registryAccess());
		builder.define(VARIANT, slabfishRegistry.getHolder(EnvironmentalSlabfishVariants.SWAMP).or(slabfishRegistry::getAny).orElseThrow());
		builder.define(OVERLAY, Optional.empty());
		builder.define(BACKPACK, Optional.empty());
		builder.define(SWEATER, Optional.empty());
		builder.define(FROM_BUCKET, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		this.getVariant().unwrapKey().ifPresent(variant -> tag.putString("SlabfishType", variant.location().toString()));
		this.getSweater().flatMap(Holder::unwrapKey).ifPresent(sweater -> tag.putString("SweaterType", sweater.location().toString()));
		this.getBackpack().flatMap(Holder::unwrapKey).ifPresent(backpack -> tag.putString("BackpackType", backpack.location().toString()));
		this.getOverlay().flatMap(Holder::unwrapKey).ifPresent(overlay -> tag.putString("SlabfishOverlay", overlay.location().toString()));

		tag.putBoolean("FromBucket", this.fromBucket());

		this.slabfishBackpack.write(tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		Registry<SlabfishVariant> slabfishRegistry = SlabfishHelper.slabfishTypes(this.registryAccess());
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("SlabfishType"))).flatMap(slabfishRegistry::getHolder).ifPresent(this::setVariant);

		Registry<SlabfishBackpack> backpackRegistry = SlabfishHelper.slabfishBackpacks(this.registryAccess());
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("BackpackType")))
				.flatMap(backpackRegistry::getHolder)
				.ifPresent(this::setBackpack);

		Registry<SlabfishSweater> sweaterRegistry = SlabfishHelper.slabfishSweaters(this.registryAccess());
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("SweaterType")))
				.flatMap(sweaterRegistry::getHolder)
				.ifPresent(this::setSweater);

		Registry<SlabfishOverlay> overlayRegistry = SlabfishHelper.slabfishOverlays(this.registryAccess());
		Optional.ofNullable(ResourceLocation.tryParse(tag.getString("SlabfishOverlay")))
				.flatMap(overlayRegistry::getHolder)
				.ifPresent(this::setOverlay);

		this.setFromBucket(tag.getBoolean("FromBucket"));

		this.slabfishBackpack.read(tag);
		this.updateSweater();
		this.updateBackpack();
	}

	// GENERAL //

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		Holder<SlabfishVariant> slabfishVariant = this.getVariant();

		if (item == Items.WATER_BUCKET && this.isAlive() && !(this.isTame() && !player.isSecondaryUseActive())) {
			this.dropLeash(true, true);
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
			Registry<SlabfishBackpack> backpacks = SlabfishHelper.slabfishBackpacks(this.registryAccess());
			if (this.hasBackpack() && slabfishVariant.value().backpackOverride().isEmpty() && SlabfishHelper.getBackpackType(backpacks, stack).isPresent() && !SlabfishHelper.getBackpackType(backpacks, stack).get().is(this.getBackpack().get())) {
				if (!this.level().isClientSide()) {
					ItemStack previousBackpack = this.slabfishBackpack.getItem(2);

					if (!previousBackpack.isEmpty()) {
						Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), previousBackpack.copy());
						this.slabfishBackpack.removeItemNoUpdate(2);
					}

					this.slabfishBackpack.setItem(2, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
					this.playBackpackDyeSound();
				}
				return InteractionResult.SUCCESS;
			}

			Registry<SlabfishSweater> sweaters = SlabfishHelper.slabfishSweaters(this.registryAccess());
			if (SlabfishHelper.getSweaterType(sweaters, stack).isPresent() && !player.isSecondaryUseActive() && (!this.hasSweater() || !SlabfishHelper.getSweaterType(sweaters, stack).get().is(this.getSweater().get()))) {
				if (!this.level().isClientSide()) {
					ItemStack previousSweater = this.slabfishBackpack.getItem(0);
					if (!previousSweater.isEmpty()) {
						Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), previousSweater.copy());
						this.slabfishBackpack.removeItemNoUpdate(0);
					}
					this.slabfishBackpack.setItem(0, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
				}
				return InteractionResult.SUCCESS;
			}

			if (stack.is(Tags.Items.CHESTS_WOODEN) && !this.hasBackpack()) {
				if (!this.level().isClientSide()) {
					this.slabfishBackpack.setItem(1, new ItemStack(item));
					this.usePlayerItem(player, hand, stack);
					if (player instanceof ServerPlayer serverPlayer)
						EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.get().trigger(serverPlayer);
				}
				return InteractionResult.SUCCESS;
			}

			if (item == Items.SHEARS && this.hasSweater() && !player.isSecondaryUseActive()) {
				ItemStack previousSweater = this.slabfishBackpack.getItem(0);
				if (!previousSweater.isEmpty()) {
					Containers.dropItemStack(this.level(), this.getX(), this.getY(), this.getZ(), previousSweater.copy());
					this.slabfishBackpack.removeItemNoUpdate(0);
				}
				return InteractionResult.SUCCESS;
			}

			if (player.isSecondaryUseActive() && stack.is(Tags.Items.TOOLS_SHEAR) && this.hasBackpack()) {
				this.dropBackpack();
				return InteractionResult.SUCCESS;
			}

			if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
				this.usePlayerItem(player, hand, stack);
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSoundEvents.SLABFISH_EAT.get(), SoundSource.NEUTRAL, 1F, 1F, true);
				this.heal(item.getFoodProperties(stack, player).nutrition());
				this.particleCloud(ParticleTypes.COMPOSTER);
				return InteractionResult.SUCCESS;
			}

			if (Ingredient.of(EnvironmentalItemTags.SLABFISH_SNACKS).test(stack)) {
				stack.finishUsingItem(this.level(), this);
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), EnvironmentalSoundEvents.SLABFISH_EAT.get(), SoundSource.NEUTRAL, 1F, 1F, true);
				return InteractionResult.SUCCESS;
			}

			if (this.isFood(stack)) {
				int i = this.getAge();
				if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(player, hand, stack);
					this.setInLove(player);
					return InteractionResult.SUCCESS;
				}

				if (this.isBaby()) {
					this.usePlayerItem(player, hand, stack);
					this.ageUp((int) ((float) (-i / 20) * 0.1F), true);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}

				if (this.level().isClientSide) {
					return InteractionResult.CONSUME;
				}
			}

			if (!this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive() && !this.isInWater()) {
				this.setOwnerUUID(player.getUUID());
				if (!this.level().isClientSide())
					this.setOrderedToSit(true);
				return InteractionResult.SUCCESS;
			}

			if (this.isOrderedToSit() && this.isOwnedBy(player) && player.isSecondaryUseActive()) {
				if (!this.level().isClientSide())
					this.setOrderedToSit(false);
				return InteractionResult.SUCCESS;
			}

			if (!player.isSecondaryUseActive()) {
				if (!this.level().isClientSide()) {
					openGui((ServerPlayer) player);
				}
				return InteractionResult.SUCCESS;
			}

		} else if (stack.is(EnvironmentalItemTags.SLABFISH_TAME_ITEMS)) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, player)) {
				this.tame(player);
				this.setOrderedToSit(true);
				this.level().broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		}

		return super.mobInteract(player, hand);
	}

	@Override
	protected void onInsideBlock(BlockState state) {
		if (state.getBlock() == Blocks.WATER && this.hasOverlay()) {
			this.setOverlay(null);
		}
	}

	public boolean isPartying() {
		return this.isPartying;
	}

	@Override
	public void setRecordPlayingNearby(BlockPos pos, boolean isPartying) {
		this.jukeboxPosition = pos;
		this.isPartying = isPartying;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.jukeboxPosition == null || !this.jukeboxPosition.closerThan(this.blockPosition(), 3.46D) || this.level().getBlockState(jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
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
				this.level().addParticle(ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getY() + 0.2D, this.getRandomZ(0.5D), d0, d1, d2);
			}
		}

		if (this.random.nextFloat() < 0.1F && this.hasOverlay() && this.getOverlay().get().is(EnvironmentalSlabfishOverlays.MUD)) {
			for (int i = 0; i < this.random.nextInt(2) + 1; ++i) {
				this.doParticle(this.level(), this.getX() - (double) 0.3F, this.getX() + (double) 0.3F, this.getZ() - (double) 0.3F, this.getZ() + (double) 0.3F, this.getY(0.5D), new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(EnvironmentalItems.MUD_BALL.get())));
			}
		}

		this.refreshDimensions();
		this.setCanPickUpLoot(this.hasBackpack());

		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround() ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround()) {
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

	protected void playBackpackDyeSound() {
		this.playSound(SoundEvents.DYE_USE, 1.0F, 1.0F);
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
			this.level().addParticle(particle, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
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
	public Slabfish getBreedOffspring(ServerLevel level, AgeableMob ageable) {
		Slabfish baby = EnvironmentalEntityTypes.SLABFISH.get().create(level);
		if (baby != null && ageable instanceof Slabfish parent) {
			Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(this.registryAccess());
			Holder<SlabfishVariant> slabfishVariant = SlabfishHelper.getSlabfishType(registry, SlabfishConditionContext.breeding(baby, this.getLoveCause(), this, parent)).orElse(registry.getHolderOrThrow(EnvironmentalSlabfishVariants.SWAMP));
			baby.setVariant(slabfishVariant);
			if (this.isTame()) {
				baby.setOwnerUUID(this.getOwnerUUID());
				baby.setTame(true, true);
			}
		}
		return baby;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return !Ingredient.of(EnvironmentalItemTags.SLABFISH_TAME_ITEMS).test(stack) && Ingredient.of(EnvironmentalItemTags.SLABFISH_FOOD).test(stack);
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return this.isInWater() ? this.isBaby() ? SIZE_SWIMMING_CHILD : SIZE_SWIMMING : (this.isInSittingPose() || this.getVehicle() != null) ? this.isBaby() ? SIZE_SITTING_CHILD : SIZE_SITTING : super.getDefaultDimensions(pose);
	}

	@Override
	public boolean isPushedByFluid() {
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
		if (!this.level().isClientSide() && name != null && !this.getVariant().is(EnvironmentalSlabfishVariants.GHOST)) {
			super.setCustomName(name);
			Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(this.registryAccess());
			Holder<SlabfishVariant> currentType = this.getVariant();
			SlabfishHelper.getSlabfishType(registry, SlabfishConditionContext.rename(this)).ifPresent(newType -> {
				if (!SlabfishVariant.canBeSold(currentType) && SlabfishVariant.canBeSold(newType))
					return;
				if (!newType.is(currentType))
					this.setVariant(newType);
			});
		}
	}

	@Override
	public void thunderHit(ServerLevel world, LightningBolt lightningBolt) {
		UUID uuid = lightningBolt.getUUID();
		if (!world.isClientSide() && !uuid.equals(this.lightningUUID) && !this.getVariant().is(EnvironmentalSlabfishVariants.GHOST)) {
			Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(this.registryAccess());
			Holder<SlabfishVariant> currentType = this.getVariant();
			SlabfishHelper.getSlabfishType(registry, SlabfishConditionContext.lightning(this)).ifPresent(newType -> {
				if (!SlabfishVariant.canBeSold(currentType) && SlabfishVariant.canBeSold(newType))
					return;
				this.setVariant(newType);
				this.lightningUUID = uuid;
				this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 1.0F);
			});
		}
	}

	public void setVariant(Holder<SlabfishVariant> variant) {
		this.entityData.set(VARIANT, variant);
	}

	public Holder<SlabfishVariant> getVariant() {
		return this.entityData.get(VARIANT);
	}

	public void setBackpack(Holder<SlabfishBackpack> variant) {
		this.entityData.set(BACKPACK, Optional.ofNullable(variant));
	}

	public Optional<Holder<SlabfishBackpack>> getBackpack() {
		return this.entityData.get(BACKPACK);
	}

	public boolean hasBackpack() {
		return this.getBackpack().isPresent();
	}

	public void setSweater(Holder<SlabfishSweater> variant) {
		this.entityData.set(SWEATER, Optional.ofNullable(variant));
	}

	public Optional<Holder<SlabfishSweater>> getSweater() {
		return this.entityData.get(SWEATER);
	}

	public boolean hasSweater() {
		return this.getSweater().isPresent();
	}

	public void setOverlay(Holder<SlabfishOverlay> variant) {
		this.entityData.set(OVERLAY, Optional.ofNullable(variant));
	}

	public Optional<Holder<SlabfishOverlay>> getOverlay() {
		return this.entityData.get(OVERLAY);
	}

	public boolean hasOverlay() {
		return this.getOverlay().isPresent();
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnDataIn) {
		if (reason == MobSpawnType.BUCKET) {
			return spawnDataIn;
		} else {
			if (spawnDataIn instanceof Slabfish.SlabfishData slabfishData) {
				this.setVariant(slabfishData.type);
				return spawnDataIn;
			} else {
				Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(this.registryAccess());
				Holder<SlabfishVariant> type = SlabfishHelper.getSlabfishType(registry, SlabfishConditionContext.spawned(this)).orElse(registry.getHolderOrThrow(EnvironmentalSlabfishVariants.SWAMP));

				spawnDataIn = new SlabfishData(type);
				this.setVariant(type);
				return super.finalizeSpawn(world, difficulty, reason, spawnDataIn);
			}
		}
	}

	public static class SlabfishData extends AgeableMobGroupData implements SpawnGroupData {
		public final Holder<SlabfishVariant> type;

		public SlabfishData(Holder<SlabfishVariant> type) {
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

		if (tag.contains("BucketVariantTag", Tag.TAG_STRING)) {
			if (tag.contains("Age")) {
				this.setAge(tag.getInt("Age"));
			}

			if (tag.contains("Owner")) {
				this.setTame(true, true);
				this.setOwnerUUID(tag.getUUID("Owner"));
			}

			Optional.ofNullable(ResourceLocation.tryParse(tag.getString("BucketVariantTag")))
					.flatMap(key -> SlabfishHelper.slabfishTypes(this.registryAccess()).getHolder(key))
					.ifPresent(this::setVariant);

			Optional.ofNullable(ResourceLocation.tryParse(tag.getString("BackpackType")))
					.flatMap(key -> SlabfishHelper.slabfishBackpacks(this.registryAccess()).getHolder(key))
					.ifPresent(this::setBackpack);

			Optional.ofNullable(ResourceLocation.tryParse(tag.getString("SweaterType")))
					.flatMap(key -> SlabfishHelper.slabfishSweaters(this.registryAccess()).getHolder(key))
					.ifPresent(this::setSweater);

			this.slabfishBackpack.read(tag);
			this.updateSweater();
			this.updateBackpack();
		} else {
			TagKey<SlabfishVariant> rarity = SlabfishVariant.getRandomRarity(this.random.nextFloat());
			Registry<SlabfishVariant> registry = SlabfishHelper.slabfishTypes(this.registryAccess());
			Optional<Holder<SlabfishVariant>> type = SlabfishHelper.getRandomSlabfishType(registry, s -> !s.is(EnvironmentalSlabfishTypeTags.NOT_SOLD_BY_WANDERING_TRADER) && s.is(rarity), this.level().getRandom());
			type.ifPresent(this::setVariant);
		}
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_FISH;
	}

	@Override
	public void saveToBucketTag(ItemStack bucket) {
		Bucketable.saveDefaultDataToBucketTag(this, bucket);
		CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, tag -> {
			tag.putInt("Age", this.getAge());

			if (this.getOwnerUUID() != null && this.isTame())
				tag.putUUID("Owner", this.getOwnerUUID());

			this.getVariant().unwrapKey().ifPresent(variant -> tag.putString("BucketVariantTag", variant.location().toString()));
			this.getBackpack().flatMap(Holder::unwrapKey).ifPresent(backpack -> tag.putString("BackpackType", backpack.location().toString()));
			this.getSweater().flatMap(Holder::unwrapKey).ifPresent(sweater -> tag.putString("SweaterType", sweater.location().toString()));

			this.slabfishBackpack.write(tag);
		});
	}

	// DATA //

	private void updateSweater() {
		if (!this.level().isClientSide()) {
			ItemStack sweaterStack = this.slabfishBackpack.getItem(0);
			Registry<SlabfishSweater> sweaters = SlabfishHelper.slabfishSweaters(this.registryAccess());
			SlabfishHelper.getSweaterType(sweaters, sweaterStack).ifPresentOrElse(this::setSweater, () -> {
				this.setSweater(null);
			});
		}
	}

	private void updateBackpack() {
		if (!this.level().isClientSide()) {
			if (!this.slabfishBackpack.getItem(1).isEmpty() && this.slabfishBackpack.getItem(1).is(Tags.Items.CHESTS_WOODEN)) {
				SlabfishVariant slabfishVariant = this.getVariant().value();
				Optional<Holder<SlabfishBackpack>> backpackType = slabfishVariant.backpackOverride();
				Registry<SlabfishBackpack> registry = SlabfishHelper.slabfishBackpacks(this.registryAccess());

				if (backpackType.isPresent()) {
					this.setBackpack(backpackType.get());
					if (!this.slabfishBackpack.getItem(2).isEmpty())
						this.spawnAtLocation(this.slabfishBackpack.removeItemNoUpdate(2));
				} else {
					ItemStack backpackColorStack = this.slabfishBackpack.getItem(2);
					this.setBackpack(SlabfishHelper.getBackpackType(registry, backpackColorStack).orElse(registry.getHolderOrThrow(EnvironmentalSlabfishBackpacks.BROWN)));
				}
			} else {
				this.setBackpack(null);
			}
		}
	}

	// INVENTORY //

	public void openGui(ServerPlayer player) {
		if (player.containerMenu != player.inventoryMenu)
			player.closeContainer();

		player.nextContainerCounter();
		PacketDistributor.sendToPlayer(player, new OpenSlabfishInventoryPayload(this, player.containerCounter));
		player.containerMenu = new SlabfishInventoryMenu(player.containerCounter, player.getInventory(), this.slabfishBackpack, this);
		player.initMenu(player.containerMenu);
		NeoForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, player.containerMenu));
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
		if (!itemstack.isEmpty() && !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
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
					if (!itemstack.isEmpty() && !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
						this.spawnAtLocation(itemstack);
					}
				}
			}
		}
	}

	@Override
	protected void pickUpItem(ItemEntity itemEntity) {
		if (itemEntity.getOwner() == this)
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

	// MISC //

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(EnvironmentalItems.SLABFISH_BUCKET.get());
	}
}
