package com.team_abnormals.environmental.common.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.team_abnormals.environmental.common.entity.goals.SlabbyBreedGoal;
import com.team_abnormals.environmental.common.entity.goals.SlabbyFollowParentGoal;
import com.team_abnormals.environmental.common.entity.goals.SlabbyGrabItemGoal;
import com.team_abnormals.environmental.common.entity.util.SlabfishOverlay;
import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.entity.util.SlabfishType;
import com.team_abnormals.environmental.common.inventory.SlabfishInventory;
import com.team_abnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.team_abnormals.environmental.common.item.MudBallItem;
import com.team_abnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.other.EnvironmentalCriteriaTriggers;
import com.team_abnormals.environmental.core.other.EnvironmentalTags;
import com.team_abnormals.environmental.core.registry.*;
import com.teamabnormals.abnormals_core.core.library.api.IBucketableEntity;
import com.teamabnormals.abnormals_core.core.library.endimator.Endimation;
import com.teamabnormals.abnormals_core.core.library.endimator.entity.IEndimatedEntity;
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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public class SlabfishEntity extends TameableEntity implements IInventoryChangedListener, IBucketableEntity, IEndimatedEntity {
    private static final DataParameter<Integer> SLABFISH_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SLABFISH_OVERLAY = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PRE_NAME_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> HAS_BACKPACK = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BACKPACK_COLOR = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> BACKPACK_USED = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.ITEMSTACK);

    private static final DataParameter<Boolean> HAS_SWEATER = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SWEATER_COLOR = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);

    public static final EntitySize SIZE_SWIMMING = EntitySize.fixed(0.7F, 0.6F);
    public static final EntitySize SIZE_SITTING = EntitySize.fixed(0.45F, 0.6F);
    public static final EntitySize SIZE_SWIMMING_CHILD = EntitySize.fixed(0.35F, 0.3F);
    public static final EntitySize SIZE_SITTING_CHILD = EntitySize.fixed(0.225F, 0.3F);

    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.TROPICAL_FISH, EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get());
    private static final Ingredient FOOD_ITEMS = Ingredient.fromItems(
            ForgeRegistries.ITEMS.getValue(new ResourceLocation("atmospheric", "passionfruit")),
            ForgeRegistries.ITEMS.getValue(new ResourceLocation("atmospheric", "shimmering_passionfruit")),
            ForgeRegistries.ITEMS.getValue(new ResourceLocation("endergetic", "bolloom_fruit")),
            Items.CHORUS_FRUIT
    );

    private Endimation playingEndimation = BLANK_ANIMATION;
    public static final Endimation DANCE = new Endimation(Environmental.REGISTRY_HELPER.prefix("slabfish_dancing"), 40);

    public SlabfishInventory slabfishBackpack;
    public boolean backpackFull;
    public int playersUsing;
    private UUID lightningUUID;
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;
    private int animationTick;
    public boolean isPartying = false;
    BlockPos jukeboxPosition;

    private static final Map<List<String>, SlabfishType> NAMES = Util.make(Maps.newHashMap(), (skins) -> {
        skins.put(Arrays.asList("cameron", "cam", "cringe"), SlabfishType.CAMERON);
        skins.put(Arrays.asList("bagel", "shyguy", "shy guy", "bagielo"), SlabfishType.BAGEL);
        skins.put(Arrays.asList("gore", "gore.", "musicano"), SlabfishType.GORE);
        skins.put(Arrays.asList("snake", "snake block", "snakeblock"), SlabfishType.SNAKE_BLOCK);
        skins.put(Arrays.asList("jackson", "jason", "json"), SlabfishType.JACKSON);
        skins.put(Arrays.asList("jub", "slabrave", "mista jub"), SlabfishType.MISTA_JUB);
        skins.put(Arrays.asList("smelly", "stinky", "smellysox", "thefaceofgaming"), SlabfishType.SMELLY);
        skins.put(Arrays.asList("squart", "squar", "squarticus"), SlabfishType.SQUART);
        skins.put(Arrays.asList("bmo", "beemo", "be more"), SlabfishType.BMO);
    });

    public SlabfishEntity(EntityType<? extends SlabfishEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.slabfishBackpack = new SlabfishInventory(this);
        this.slabfishBackpack.addListener(this);
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.slabfishBackpack));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .func_233815_a_(Attributes.MAX_HEALTH, 15.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));

        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, LivingEntity.class, entity -> EntityTypeTags.RAIDERS.contains(entity.getType()), 15.0F, 1.0D, 1.5D, EntityPredicates.CAN_AI_TARGET::test));

        this.goalSelector.addGoal(4, new SlabbyBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new SlabbyGrabItemGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, false, Ingredient.merge(Arrays.asList(BREEDING_ITEMS, Ingredient.fromTag(ItemTags.FISHES), Ingredient.fromTag(EnvironmentalTags.SUSHI)))));
        this.goalSelector.addGoal(8, new SlabbyFollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(SLABFISH_TYPE, 0);
        this.getDataManager().register(SLABFISH_OVERLAY, 0);
        this.getDataManager().register(PRE_NAME_TYPE, 0);
        this.getDataManager().register(FROM_BUCKET, false);

        this.getDataManager().register(HAS_BACKPACK, false);
        this.getDataManager().register(BACKPACK_COLOR, DyeColor.BROWN.getId());
        this.getDataManager().register(BACKPACK_USED, new ItemStack(Items.CHEST));

        this.getDataManager().register(HAS_SWEATER, false);
        this.getDataManager().register(SWEATER_COLOR, DyeColor.WHITE.getId());
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("SlabfishType", this.getSlabfishType().getId());
        compound.putInt("SlabfishOverlay", this.getSlabfishOverlay().getId());
        compound.putInt("PreNameType", this.getPreNameType().getId());
        compound.putBoolean("FromBucket", this.isFromBucket());

        compound.putBoolean("HasBackpack", this.hasBackpack());
        compound.putByte("BackpackColor", (byte) this.getBackpackColor().getId());
        CompoundNBT itemstackTag = new CompoundNBT();
        this.getBackpackItem().write(itemstackTag);

        compound.put("BackpackItem", itemstackTag);

        compound.putBoolean("HasSweater", this.hasSweater());
        compound.putByte("SweaterColor", (byte) this.getSweaterColor().getId());

        this.slabfishBackpack.write(compound);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.getLeashed();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSlabfishType(SlabfishType.byId(compound.getInt("SlabfishType")));
        this.setSlabfishOverlay(SlabfishOverlay.byId(compound.getInt("SlabfishOverlay")));
        this.setPreNameType(SlabfishType.byId(compound.getInt("PreNameType")));
        this.setFromBucket(compound.getBoolean("FromBucket"));

        this.setBackpacked(compound.getBoolean("HasBackpack"));
        if (compound.contains("BackpackColor", 99))
            this.setBackpackColor(DyeColor.byId(compound.getInt("BackpackColor")));
        ItemStack backpackItem = ItemStack.read(compound.getCompound("BackpackItem"));
        this.setBackpackItem(backpackItem);

        this.setSweatered(compound.getBoolean("HasSweater"));
        if (compound.contains("SweaterColor", 99)) this.setSweaterColor(DyeColor.byId(compound.getInt("SweaterColor")));

        this.slabfishBackpack.read(compound);
        updateSweater();
    }

    // GENERAL //

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        Item item = itemstack.getItem();
        if (item instanceof SpawnEggItem || item instanceof NameTagItem || item == Items.TROPICAL_FISH || item == EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get() || item instanceof EggItem || item instanceof MudBallItem) {
            return super.func_230254_b_(player, hand);
        }
        if (item instanceof DyeItem && this.hasBackpack()) {
            DyeColor dyecolor = ((DyeItem) item).getDyeColor();
            if (dyecolor != this.getBackpackColor()) {
                this.setBackpackColor(dyecolor);
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;

        } else if (item == Items.RABBIT_FOOT && !player.isBeingRidden()) {
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            this.startRiding(player);
            this.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.particleCloud(ParticleTypes.CAMPFIRE_COSY_SMOKE);
            return ActionResultType.SUCCESS;

        } else if (SWEATER_MAP.containsKey(item) && !(this.hasSweater() && this.getSweaterColor() == SWEATER_MAP.get(item)) && !player.isSecondaryUseActive()) {
            ItemStack previousSweater = this.slabfishBackpack.getStackInSlot(0);

            if (!previousSweater.isEmpty()) {
                InventoryHelper.spawnItemStack(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), previousSweater.copy());
                this.slabfishBackpack.removeStackFromSlot(0);
            }

            this.slabfishBackpack.setInventorySlotContents(0, new ItemStack(itemstack.getItem()));
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            return ActionResultType.SUCCESS;

        } else if (item.isIn(Tags.Items.CHESTS_WOODEN) && !this.hasBackpack()) {
            this.setBackpacked(true);
            this.setBackpackItem(itemstack);
            this.playBackpackSound();
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
                if (!world.isRemote()) EnvironmentalCriteriaTriggers.BACKPACK_SLABFISH.trigger(serverplayerentity);
            }
            return ActionResultType.SUCCESS;

        } else if (item == Items.SHEARS && this.hasSweater() && !player.isSecondaryUseActive()) {
            ItemStack previousSweater = this.slabfishBackpack.getStackInSlot(0);

            if (!previousSweater.isEmpty()) {
                InventoryHelper.spawnItemStack(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), previousSweater.copy());
                this.slabfishBackpack.removeStackFromSlot(0);
            }

            return ActionResultType.SUCCESS;

        } else if (item == Items.SHEARS && this.hasBackpack() && player.isSecondaryUseActive()) {
            this.setBackpackColor(DyeColor.BROWN);
            this.dropBackpack();
            this.setBackpacked(false);
            this.playBackpackSound();
            if (!this.world.isRemote) itemstack.attemptDamageItem(1, player.getRNG(), (ServerPlayerEntity) player);
            return ActionResultType.SUCCESS;

        } else if (item == Items.WATER_BUCKET && this.isAlive()) {
            if (this.getGrowingAge() < 0) {
                return ActionResultType.FAIL;
            }
            if (this.hasBackpack()) {
                this.setBackpackColor(DyeColor.BROWN);
                this.dropBackpack();
                this.setBackpacked(false);
            }
            this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getBucket();
            this.setBucketData(itemstack1);
            if (!this.world.isRemote) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
            }

            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, itemstack1);
            } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }

            this.remove();
            return ActionResultType.SUCCESS;
        } else if (item.isIn(ItemTags.MUSIC_DISCS)) {
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            this.playBurpSound();
            this.particleCloud(ParticleTypes.NOTE);
            this.dropItem(EnvironmentalItems.MUSIC_DISC_SLABRAVE.get());
            return ActionResultType.SUCCESS;

        } else if (Ingredient.fromTag(ItemTags.FISHES).test(itemstack) && itemstack.isFood() && this.getHealth() < this.getMaxHealth()) {
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), EnvironmentalSounds.ENTITY_SLABFISH_EAT.get(), SoundCategory.NEUTRAL, 1F, 1F, true);
            this.heal((float) item.getFood().getHealing());
            this.particleCloud(ParticleTypes.COMPOSTER);
            return ActionResultType.SUCCESS;

        } else if (Ingredient.fromTag(EnvironmentalTags.SUSHI).test(itemstack)) {
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            this.playBurpSound();
            this.addPotionEffect(new EffectInstance(Effects.SPEED, 3600, 2, true, true));
            this.particleCloud(ParticleTypes.CLOUD);
            return ActionResultType.SUCCESS;

        } else if (FOOD_ITEMS.test(itemstack)) {
            if (!player.abilities.isCreativeMode) itemstack.shrink(1);
            itemstack.onItemUseFinish(this.world, this);
            world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), EnvironmentalSounds.ENTITY_SLABFISH_EAT.get(), SoundCategory.NEUTRAL, 1F, 1F, true);
            return ActionResultType.SUCCESS;
        } else if (!this.func_233685_eM_() && this.hasBackpack() && player.isSecondaryUseActive() && !this.isInWater()) {
            this.setTamed(true);
            this.setOwnerId(player.getUniqueID());
            if (!world.isRemote()) this.func_233687_w_(true);
            return ActionResultType.SUCCESS;

        } else if (this.func_233685_eM_() && player.isSecondaryUseActive()) {
            if (!world.isRemote()) this.func_233687_w_(false);
            this.setTamed(false);
            return ActionResultType.SUCCESS;
        } else if (openGui(player)) {
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        if (state.getBlock() == EnvironmentalBlocks.MUD.get()) {
            if (this.getSlabfishOverlay() != SlabfishOverlay.MUDDY) this.setSlabfishOverlay(SlabfishOverlay.MUDDY);
        }
        if (state.getBlock() == Blocks.WATER) {
            if (this.getSlabfishOverlay() != SlabfishOverlay.NONE) this.setSlabfishOverlay(SlabfishOverlay.NONE);
        }
    }

    public boolean isPartying() {
        return this.isPartying;
    }

    @SuppressWarnings("deprecation")
    public void onDeath(DamageSource cause) {
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, cause)) return;
        if (!this.removed && !this.dead) {
            Entity entity = cause.getTrueSource();
            LivingEntity livingentity = this.getAttackingEntity();
            if (this.scoreValue >= 0 && livingentity != null) {
                livingentity.awardKillScore(this, this.scoreValue, cause);
            }

            if (entity != null) {
                entity.onKillEntity(this);
            }

            if (this.isSleeping()) {
                this.wakeUp();
            }

            this.dead = true;
            this.getCombatTracker().reset();
            if (!this.world.isRemote) {
                this.spawnDrops(cause);
                this.createWitherRose(livingentity);
            }

            this.world.setEntityState(this, (byte) 3);
            this.setPose(Pose.DYING);
        }
    }

    @Override
    public void setPartying(BlockPos pos, boolean isPartying) {
        this.jukeboxPosition = pos;
        this.isPartying = isPartying;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.jukeboxPosition == null || !this.jukeboxPosition.withinDistance(this.getPositionVec(), 3.46D) || this.world.getBlockState(jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.isPartying = false;
            this.jukeboxPosition = null;
        }

        if (!this.func_233684_eK_()) this.setTamed(false);

        if (this.isInWater() && this.getRidingEntity() != null) this.stopRiding();

        if (this.isMoving()) {
            if (this.isPotionActive(Effects.SPEED) && rand.nextInt(3) == 0 && !this.isInWater()) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.CLOUD, this.getPosXRandom(0.5D), this.getPosY() + 0.2D, this.getPosZRandom(0.5D), d0, d1, d2);
            }
        }

        if (this.rand.nextFloat() < 0.1F && this.getSlabfishOverlay() == SlabfishOverlay.MUDDY) {
            for (int i = 0; i < this.rand.nextInt(2) + 1; ++i) {
                this.doParticle(this.world, this.getPosX() - (double) 0.3F, this.getPosX() + (double) 0.3F, this.getPosZ() - (double) 0.3F, this.getPosZ() + (double) 0.3F, this.getPosYHeight(0.5D), new ItemParticleData(ParticleTypes.ITEM, new ItemStack(EnvironmentalItems.MUD_BALL.get())));
            }
        }

        List<PlayerEntity> playerList = world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(5.0D, 5.0D, 5.0D));

        for (PlayerEntity player : playerList) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
                if (!world.isRemote()) {
                    if (this.getSlabfishType() == SlabfishType.SWAMP)
                        EnvironmentalCriteriaTriggers.SWAMP_SLABFISH.trigger(serverplayerentity);
                    if (this.getSlabfishType() == SlabfishType.MARSH)
                        EnvironmentalCriteriaTriggers.MARSH_SLABFISH.trigger(serverplayerentity);
                }
            }
        }

        this.recalculateSize();
        this.setCanPickUpLoot(this.hasBackpack());

        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.onGround ) {
            if(!this.isInWater() && this.getMotion().y < 0)
                this.setMotion(this.getMotion().mul(1, 0.6, 1));
            if(this.wingRotDelta < 1.0F)
                this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
        this.wingRotation += this.wingRotDelta * 2.0F;

    }

    private boolean isMoving() {
        return this.getMotion().getX() > 0 || this.getMotion().getY() > 0 || this.getMotion().getZ() > 0;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() != null) {
            if (this.getRidingEntity() instanceof BoatEntity) return 0.3D;
            else return 0.52F;
        }
        return super.getYOffset();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (this.getRidingEntity() != null) this.stopRiding();
            Entity entity = source.getTrueSource();
            this.func_233687_w_(false);

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    // DETAILS //

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return EnvironmentalSounds.ENTITY_SLABFISH_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return EnvironmentalSounds.ENTITY_SLABFISH_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(EnvironmentalSounds.ENTITY_SLABFISH_STEP.get(), 0.15F, 1.0F);
    }

    protected void playBackpackSound() {
        this.playSound(EnvironmentalSounds.ENTITY_SLABFISH_BACKPACK.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    protected void playSweaterSound() {
        this.playSound(EnvironmentalSounds.ENTITY_SLABFISH_SWEATER.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    protected void playBurpSound() {
        this.playSound(EnvironmentalSounds.ENTITY_SLABFISH_BURP.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    private void doParticle(World world, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double p_226397_10_, IParticleData p_226397_12_) {
        world.addParticle(p_226397_12_, MathHelper.lerp(world.rand.nextDouble(), p_226397_2_, p_226397_4_), p_226397_10_, MathHelper.lerp(world.rand.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
    }

    private void particleCloud(IParticleData particle) {
        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particle, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
        }
    }

    private void dropItem(IItemProvider item) {
        ItemEntity itementity = this.entityDropItem(item, 0);
        if (itementity != null) {
            itementity.setMotion(itementity.getMotion().add((double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double) (this.rand.nextFloat() * 0.05F), (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
        }
    }

    // STATS //

    @Override
    public SlabfishEntity createChild(AgeableEntity ageable) {
        SlabfishEntity baby = EnvironmentalEntities.SLABFISH.get().create(this.world);
        baby.setSlabfishType(this.getSlabfishType());
        baby.setPreNameType(this.getSlabfishType());
        return baby;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(EnvironmentalItems.SLABFISH_SPAWN_EGG.get());
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return this.isInWater() ? this.isChild() ? SIZE_SWIMMING_CHILD : SIZE_SWIMMING : (this.func_233684_eK_() || this.getRidingEntity() != null) ? this.isChild() ? SIZE_SITTING_CHILD : SIZE_SITTING : super.getSize(pose);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.func_233684_eK_() ? sizeIn.height * 0.6F : this.isInWater() ? (this.isChild() ? sizeIn.height * 1.4F : sizeIn.height * 0.855F) : sizeIn.height * 0.8F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }


    @Override
    public int getMaxSpawnedInChunk() {
        return 5;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.96F;
    }

    // SLABFISH TYPE //

    public SlabfishType getTypeForConditions(IWorld world) {
        BlockPos pos = new BlockPos(this.getPositionVec());
        Biome biome = world.getBiome(pos);

        List<Biome> MARSH = new ArrayList<Biome>();
        MARSH.add(EnvironmentalBiomes.MARSH.get());
        MARSH.add(EnvironmentalBiomes.MUSHROOM_MARSH.get());

        List<Biome> ROSEWOOD = new ArrayList<Biome>();
        ROSEWOOD.add(findBiome("atmospheric", "rosewood_forest"));
        ROSEWOOD.add(findBiome("atmospheric", "rosewood_mountains"));
        ROSEWOOD.add(findBiome("atmospheric", "rosewood_plateau"));
        ROSEWOOD.add(findBiome("atmospheric", "rosewood_forest_plateau"));

        List<Biome> DUNES = new ArrayList<Biome>();
        DUNES.add(findBiome("atmospheric", "dunes"));
        DUNES.add(findBiome("atmospheric", "dunes_hills"));
        DUNES.add(findBiome("atmospheric", "petrified_dunes"));
        DUNES.add(findBiome("atmospheric", "rocky_dunes"));
        DUNES.add(findBiome("atmospheric", "rocky_dunes_hills"));
        DUNES.add(findBiome("atmospheric", "flourishing_dunes"));

        List<Biome> MAPLE = new ArrayList<Biome>();
        MAPLE.add(findBiome("autumnity", "maple_forest"));
        MAPLE.add(findBiome("autumnity", "maple_forest_hills"));
        MAPLE.add(findBiome("autumnity", "pumpkin_fields"));

        List<Biome> POISE = new ArrayList<Biome>();
        POISE.add(findBiome("endergetic", "poise_forest"));

        if (world.getWorld().func_234922_V_() == DimensionType.field_235999_c_) {
            if (((ServerWorld) this.world).findRaid(pos) != null) return SlabfishType.TOTEM;
            if (pos.getY() <= 20 && world.getLight(pos) == 0) return SlabfishType.CAVE;
            if (pos.getY() >= 200) return SlabfishType.SKY;

            if (MARSH.contains(biome)) return SlabfishType.MARSH;
            if (MAPLE.contains(biome)) return SlabfishType.MAPLE;
            if (ROSEWOOD.contains(biome)) return SlabfishType.ROSEWOOD;
            if (DUNES.contains(biome)) return SlabfishType.DUNES;

            if (biome == Biomes.ICE_SPIKES) return SlabfishType.ICE_SPIKES;
            if (biome == Biomes.DARK_FOREST || biome == Biomes.DARK_FOREST_HILLS) return SlabfishType.DARK_FOREST;
            if (biome == Biomes.FLOWER_FOREST) return SlabfishType.FLOWER_FOREST;

            if (biome.getCategory() == Biome.Category.OCEAN) {
                if (pos.getY() <= 50 && world.getFluidState(pos).getLevel() == 8) return SlabfishType.DROWNED;
                else if (biome == Biomes.FROZEN_OCEAN || biome == Biomes.DEEP_FROZEN_OCEAN)
                    return SlabfishType.FROZEN_OCEAN;
                else if (biome == Biomes.WARM_OCEAN || biome == Biomes.DEEP_WARM_OCEAN) return SlabfishType.WARM_OCEAN;
                else return SlabfishType.OCEAN;
            }

            if (biome.getCategory() == Biome.Category.JUNGLE) {
                if (biome == Biomes.BAMBOO_JUNGLE || biome == Biomes.BAMBOO_JUNGLE_HILLS) return SlabfishType.BAMBOO;
                else return SlabfishType.JUNGLE;
            }

            if (biome.getCategory() == Biome.Category.MUSHROOM) return SlabfishType.MUSHROOM;
            if (biome.getCategory() == Biome.Category.RIVER) return SlabfishType.RIVER;
            if (biome.getCategory() == Biome.Category.BEACH) return SlabfishType.BEACH;
            if (biome.getCategory() == Biome.Category.SAVANNA) return SlabfishType.SAVANNA;
            if (biome.getCategory() == Biome.Category.MESA) return SlabfishType.BADLANDS;
            if (biome.getCategory() == Biome.Category.ICY) return SlabfishType.SNOWY;
            if (biome.getCategory() == Biome.Category.DESERT) return SlabfishType.DESERT;
            if (biome.getCategory() == Biome.Category.TAIGA) return SlabfishType.TAIGA;
            if (biome.getCategory() == Biome.Category.FOREST) return SlabfishType.FOREST;
            if (biome.getCategory() == Biome.Category.PLAINS) return SlabfishType.PLAINS;
            if (biome.getCategory() == Biome.Category.EXTREME_HILLS || biome == Biomes.STONE_SHORE)
                return SlabfishType.MOUNTAIN;
        }

        if (world.getWorld().func_234922_V_() == DimensionType.field_236000_d_) {
            if (biome == Biomes.field_235253_az_) return SlabfishType.CRIMSON;
            if (biome == Biomes.field_235250_aA_) return SlabfishType.WARPED;
            if (biome == Biomes.field_235252_ay_) return SlabfishType.SOUL_SAND_VALLEY;
            if (biome == Biomes.field_235251_aB_) return SlabfishType.BASALT_DELTAS;
            else return SlabfishType.NETHER;
        }

        if (world.getWorld().func_234922_V_() == DimensionType.field_236001_e_) {
            if (POISE.contains(biome)) return SlabfishType.POISE;
            else if (biome == Biomes.END_HIGHLANDS) return SlabfishType.CHORUS;
            else return SlabfishType.END;
        }

        return SlabfishType.SWAMP;
    }

    public SlabfishType getRandomType() {
        boolean flag = false;
        SlabfishType type = SlabfishType.SWAMP;
        float chance = rand.nextFloat();
        SlabfishRarity rarity = SlabfishRarity.COMMON;

        if (chance > 0.45) {
            if (chance > 0.75) {
                if (chance > 0.90) {
                    if (chance > 0.98) {
                        rarity = SlabfishRarity.LEGENDARY;
                    } else {
                        rarity = SlabfishRarity.EPIC;
                    }
                } else {
                    rarity = SlabfishRarity.RARE;
                }
            } else {
                rarity = SlabfishRarity.UNCOMMON;
            }
        } else {
            rarity = SlabfishRarity.COMMON;
        }

        while (flag == false) {
            type = SlabfishType.getRandomFromRarity(rarity, rand);
            if ((type == SlabfishType.DUNES || type == SlabfishType.ROSEWOOD) && !ModList.get().isLoaded("atmospheric")) {
                flag = false;
            } else if (type == SlabfishType.POISE && !ModList.get().isLoaded("endergetic")) {
                flag = false;
            } else if (type == SlabfishType.MAPLE && !ModList.get().isLoaded("autumnity")) {
                flag = false;
            } else if (type == SlabfishType.BAGEL || type == SlabfishType.CAMERON || type == SlabfishType.GORE || type == SlabfishType.SNAKE_BLOCK
                    || type == SlabfishType.SMELLY || type == SlabfishType.SQUART || type == SlabfishType.MISTA_JUB || type == SlabfishType.JACKSON) {
                flag = false;
            } else {
                flag = true;
            }
        }

        return type;
    }

    public Biome findBiome(String modid, String name) {
        return ForgeRegistries.BIOMES.getValue(new ResourceLocation(modid, name));
    }

    public SlabfishType getTypeForBreeding(IWorld world, SlabfishEntity parent1, SlabfishEntity parent2) {
        BlockPos pos = new BlockPos(this.getPositionVec());
        Biome biome = world.getBiome(pos);

        if (parent1.getSlabfishType() == SlabfishType.SKELETON && parent2.getSlabfishType() == SlabfishType.SKELETON) {
            if (world.getWorld().func_234922_V_() == DimensionType.field_236000_d_) return SlabfishType.WITHER;
            if (world.getBiome(pos).getCategory() == Biome.Category.ICY) return SlabfishType.STRAY;
        }

        if (this.getTypeForConditions(world) == SlabfishType.SWAMP && biome.getCategory() != Biome.Category.SWAMP) {
            if (rand.nextBoolean()) {
                return parent1.getSlabfishType();
            } else {
                return parent2.getSlabfishType();
            }
        } else {
            if (rand.nextBoolean()) {
                return this.getTypeForConditions(world);
            } else {
                if (rand.nextBoolean()) {
                    return parent1.getSlabfishType();
                } else {
                    return parent2.getSlabfishType();
                }
            }
        }
    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        if (name != null && this.getSlabfishType() != SlabfishType.GHOST) {
            super.setCustomName(name);
            for (Map.Entry<List<String>, SlabfishType> entries : NAMES.entrySet()) {
                if (entries.getKey().contains(name.getString().toLowerCase().trim())) {
                    if (this.getSlabfishType() == entries.getValue()) {
                        return;
                    }
                    if (!NAMES.containsValue(this.getSlabfishType())) {
                        this.setPreNameType(this.getSlabfishType());
                    }
                    this.setSlabfishType(entries.getValue());
                    return;
                }
            }
            if (this.getSlabfishType() != this.getPreNameType()) {
                this.setSlabfishType(this.getPreNameType());
            }
        }
    }

    public void playTransformSound() {
        this.playSound(EnvironmentalSounds.ENTITY_SLABFISH_TRANSFORM.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        this.particleCloud(ParticleTypes.CAMPFIRE_COSY_SMOKE);
    }

    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        UUID uuid = lightningBolt.getUniqueID();
        if (!uuid.equals(this.lightningUUID) && this.getSlabfishType() != SlabfishType.GHOST) {
            if (this.getSlabfishType() == SlabfishType.MUSHROOM) {
                this.setSlabfishType(SlabfishType.BROWN_MUSHROOM);
                this.setPreNameType(SlabfishType.BROWN_MUSHROOM);
            } else if (this.getSlabfishType() == SlabfishType.BROWN_MUSHROOM) {
                this.setSlabfishType(SlabfishType.MUSHROOM);
                this.setPreNameType(SlabfishType.MUSHROOM);
            } else {
                this.setSlabfishType(SlabfishType.SKELETON);
                this.setPreNameType(SlabfishType.SKELETON);
            }
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 2.0F, 1.0F);
        }
    }

    public SlabfishType getSlabfishType() {
        return SlabfishType.byId(this.dataManager.get(SLABFISH_TYPE));
    }

    public void setSlabfishType(SlabfishType typeId) {
        this.dataManager.set(SLABFISH_TYPE, typeId.getId());
    }

    public SlabfishType getPreNameType() {
        return SlabfishType.byId(this.dataManager.get(PRE_NAME_TYPE));
    }

    public void setPreNameType(SlabfishType typeId) {
        this.dataManager.set(PRE_NAME_TYPE, typeId.getId());
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        SlabfishType type = reason == SpawnReason.BUCKET ? this.getRandomType() : this.getTypeForConditions(worldIn);

        if (dataTag != null && dataTag.contains("SlabfishType", 3)) {
            if (dataTag.contains("Health")) this.setHealth(dataTag.getFloat("Health"));
            if (dataTag.contains("Age")) this.setGrowingAge(dataTag.getInt("Age"));
            this.setSlabfishType(SlabfishType.byId(dataTag.getInt("SlabfishType")));
            if (dataTag.contains("PreNameType")) {
                this.setPreNameType(SlabfishType.byId(dataTag.getInt("PreNameType")));
            } else {
                this.setPreNameType(SlabfishType.byId(dataTag.getInt("SlabfishType")));
            }

            if (dataTag.contains("HasBackpack")) this.setBackpacked(dataTag.getBoolean("HasBackpack"));
            if (dataTag.contains("BackpackColor"))
                this.setBackpackColor(DyeColor.byId(dataTag.getInt("BackpackColor")));

            if (dataTag.contains("HasSweater")) this.setSweatered(dataTag.getBoolean("HasSweater"));
            if (dataTag.contains("SweaterColor")) this.setSweaterColor(DyeColor.byId(dataTag.getInt("SweaterColor")));

            if (dataTag.contains("BackpackItem")) {
                ItemStack backpackItem = ItemStack.read(dataTag.getCompound("BackpackItem"));
                this.setBackpackItem(backpackItem);
            }

            this.slabfishBackpack.read(dataTag);

            return spawnDataIn;
        }

        if (spawnDataIn instanceof SlabfishEntity.SlabfishData) {
            type = ((SlabfishEntity.SlabfishData) spawnDataIn).typeData;
        } else if (!this.isFromBucket()) {
            spawnDataIn = new SlabfishEntity.SlabfishData(type);
        }

        this.setSlabfishType(type);
        this.setPreNameType(type);
        return spawnDataIn;
    }

    public static class SlabfishData extends AgeableData implements ILivingEntityData {
        public final SlabfishType typeData;

        public SlabfishData(SlabfishType type) {
            this.typeData = type;
        }
    }

    // BUCKETING //

    @Override
    public boolean preventDespawn() {
        return this.isFromBucket();
    }

    public boolean isFromBucket() {
        return this.dataManager.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean value) {
        this.dataManager.set(FROM_BUCKET, value);
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setDisplayName(this.getCustomName());
        }

        CompoundNBT compound = bucket.getOrCreateTag();
        CompoundNBT itemstackTag = new CompoundNBT();

        compound.putFloat("Health", this.getHealth());
        compound.putInt("Age", this.getGrowingAge());

        compound.putInt("SlabfishType", this.getSlabfishType().getId());
        compound.putInt("PreNameType", this.getPreNameType().getId());

        compound.putBoolean("HasBackpack", this.hasBackpack());
        if (this.hasBackpack()) compound.putByte("BackpackColor", (byte) this.getBackpackColor().getId());

        this.getBackpackItem().write(itemstackTag);
        if (this.hasBackpack()) compound.put("BackpackItem", itemstackTag);

        compound.putBoolean("HasSweater", this.hasSweater());

        if (this.hasSweater()) compound.putByte("SweaterColor", (byte) this.getSweaterColor().getId());
    }

    // DATA //

    private void updateSweater() {
        if (!this.world.isRemote()) {
            ItemStack stack = this.slabfishBackpack.getStackInSlot(0);
            if (SWEATER_MAP.containsKey(stack.getItem())) {
                this.setSweatered(true);
                this.setSweaterColor(SWEATER_MAP.get(stack.getItem()));
            } else {
                this.setSweatered(false);
                this.setSweaterColor(DyeColor.WHITE);
            }
        }
    }


    public boolean hasBackpack() {
        return this.dataManager.get(HAS_BACKPACK);
    }

    public void setBackpacked(boolean hasBackpack) {
        this.dataManager.set(HAS_BACKPACK, hasBackpack);
    }

    public DyeColor getBackpackColor() {
        return DyeColor.byId(this.dataManager.get(BACKPACK_COLOR));
    }

    public void setBackpackColor(DyeColor color) {
        this.dataManager.set(BACKPACK_COLOR, color.getId());
    }

    public ItemStack getBackpackItem() {
        return this.dataManager.get(BACKPACK_USED);
    }

    public void setBackpackItem(ItemStack itemStack) {
        this.dataManager.set(BACKPACK_USED, itemStack);
    }

    public boolean hasSweater() {
        return this.dataManager.get(HAS_SWEATER);
    }

    public void setSweatered(boolean hasSweater) {
        this.dataManager.set(HAS_SWEATER, hasSweater);
    }

    public DyeColor getSweaterColor() {
        return DyeColor.byId(this.dataManager.get(SWEATER_COLOR));
    }

    public void setSweaterColor(DyeColor color) {
        this.dataManager.set(SWEATER_COLOR, color.getId());
    }

    public SlabfishOverlay getSlabfishOverlay() {
        return SlabfishOverlay.byId(this.dataManager.get(SLABFISH_OVERLAY));
    }

    public void setSlabfishOverlay(SlabfishOverlay typeId) {
        this.dataManager.set(SLABFISH_OVERLAY, typeId.getId());
    }

    // INVENTORY //

    public boolean openGui(PlayerEntity player) {
        if (!player.world.isRemote()) {
            OptionalInt optional = player.openContainer(new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return SlabfishEntity.this.getDisplayName();
                }

                @Override
                public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                    return new SlabfishInventoryContainer(id, inventory, SlabfishEntity.this.slabfishBackpack, SlabfishEntity.this);
                }
            });
            optional.ifPresent(windowId -> Environmental.CHANNEL.sendToServer(new SOpenSlabfishInventoryMessage(this, windowId)));
            if(optional.isPresent())
                playersUsing++;

            return optional.isPresent();
        }
        return true;
    }

    @Override
    public boolean canEquipItem(ItemStack stack) {
        return this.hasBackpack();
    }

    @Override
    public boolean canPickUpItem(ItemStack stack) {
        return hasBackpack();
    }

    protected int getInventorySize() {
        return this.hasBackpack() ? 1 + 3 * this.getInventoryColumns() : 1;
    }

    public List<ItemEntity> getNearbyItems(float multiplier) {
        return this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(8.0F * multiplier, 4.0F, 8.0F * multiplier));
    }

    public boolean isPlayerNear(float multiplier) {
        return !this.getNearbyItems(multiplier).isEmpty();
    }

    @Override
    protected void dropInventory() {
        this.dropBackpack();
        if (this.hasSweater()) {
            this.dropItem(SWEATER_MAP.inverse().get(this.getSweaterColor()));
        }
    }

    protected void dropBackpack() {
        super.dropInventory();
        if (this.hasBackpack()) {
            this.dropItem(this.getBackpackItem().getItem());
            if (this.slabfishBackpack != null) {
                for (int i = 1; i < this.slabfishBackpack.getSizeInventory(); ++i) {
                    ItemStack itemstack = this.slabfishBackpack.removeStackFromSlot(i);
                    if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                        this.entityDropItem(itemstack);
                    }
                }

            }
        }
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();

        if (this.hasBackpack()) {
            ItemStack stack = this.slabfishBackpack.addItem(itemstack);
            if(!ItemStack.areItemStacksEqual(itemstack, stack))
                this.onItemPickup(itemEntity, itemstack.getCount() - stack.getCount());
            if(stack.isEmpty())
                itemEntity.remove();
            else
                itemEntity.setItem(stack);
        }
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.hasSweater();
        this.updateSweater();

        if (!flag && this.hasSweater())
            playSweaterSound();

        this.backpackFull = true;
        for (int i = 1; i < this.slabfishBackpack.getSizeInventory(); i++)
        {
            ItemStack stack = this.slabfishBackpack.getStackInSlot(i);
            if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize())
            {
                this.backpackFull = false;
                break;
            }
        }
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
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

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private static final BiMap<Item, DyeColor> SWEATER_MAP = Util.make(HashBiMap.create(), (sweaterMap) -> {
        sweaterMap.put(Items.WHITE_WOOL, DyeColor.WHITE);
        sweaterMap.put(Items.ORANGE_WOOL, DyeColor.ORANGE);
        sweaterMap.put(Items.MAGENTA_WOOL, DyeColor.MAGENTA);
        sweaterMap.put(Items.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE);
        sweaterMap.put(Items.YELLOW_WOOL, DyeColor.YELLOW);
        sweaterMap.put(Items.LIME_WOOL, DyeColor.LIME);
        sweaterMap.put(Items.PINK_WOOL, DyeColor.PINK);
        sweaterMap.put(Items.GRAY_WOOL, DyeColor.GRAY);
        sweaterMap.put(Items.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY);
        sweaterMap.put(Items.CYAN_WOOL, DyeColor.CYAN);
        sweaterMap.put(Items.PURPLE_WOOL, DyeColor.PURPLE);
        sweaterMap.put(Items.BLUE_WOOL, DyeColor.BLUE);
        sweaterMap.put(Items.BROWN_WOOL, DyeColor.BROWN);
        sweaterMap.put(Items.GREEN_WOOL, DyeColor.GREEN);
        sweaterMap.put(Items.RED_WOOL, DyeColor.RED);
        sweaterMap.put(Items.BLACK_WOOL, DyeColor.BLACK);
    });

    public static Map<Item, DyeColor> getSweaterMap() {
        return SWEATER_MAP;
    }

    @Override
    public ItemStack getBucket() {
        return new ItemStack(EnvironmentalItems.SLABFISH_BUCKET.get());
    }

    @Override
    public int getAnimationTick() {
        return animationTick;
    }

    @Override
    public Endimation[] getEndimations() {
        return new Endimation[]{DANCE};
    }

    @Override
    public Endimation getPlayingEndimation() {
        return this.playingEndimation;
    }

    @Override
    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    @Override
    public void setPlayingEndimation(Endimation playingEndimation) {
        this.playingEndimation = playingEndimation;
    }

    public int getInventoryColumns() {
        return 5;
    }
}
