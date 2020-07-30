package com.minecraftabnormals.environmental.common.entity;
//package com.farcr.environmental.common.entity;
//import java.util.UUID;
//
//import com.farcr.environmental.core.registry.EnvironmentalEntities;
//import com.farcr.environmental.core.registry.EnvironmentalItems;
//
//import net.minecraft.advancements.CriteriaTriggers;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.AgeableEntity;
//import net.minecraft.entity.CreatureAttribute;
//import net.minecraft.entity.EntitySize;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.Pose;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.ai.goal.BreedGoal;
//import net.minecraft.entity.ai.goal.FollowOwnerGoal;
//import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
//import net.minecraft.entity.ai.goal.LookAtGoal;
//import net.minecraft.entity.ai.goal.LookRandomlyGoal;
//import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
//import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
//import net.minecraft.entity.ai.goal.RandomWalkingGoal;
//import net.minecraft.entity.ai.goal.SitGoal;
//import net.minecraft.entity.ai.goal.SwimGoal;
//import net.minecraft.entity.passive.AnimalEntity;
//import net.minecraft.entity.passive.TameableEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.item.DyeColor;
//import net.minecraft.item.DyeItem;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.item.SpawnEggItem;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.network.datasync.DataParameter;
//import net.minecraft.network.datasync.DataSerializers;
//import net.minecraft.network.datasync.EntityDataManager;
//import net.minecraft.pathfinding.PathNodeType;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.Hand;
//import net.minecraft.util.SoundEvent;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IWorldReader;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//public class AxolotlEntity extends TameableEntity {
//	
//   private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(AxolotlEntity.class, DataSerializers.VARINT);
//   private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(AxolotlEntity.class, DataSerializers.BOOLEAN);
//
//   public AxolotlEntity(EntityType<? extends AxolotlEntity> type, World worldIn) {
//      super(type, worldIn);
//      this.setTamed(false);
//      this.setPathPriority(PathNodeType.WATER, 0.0F);
//   }
//
//   protected void registerGoals() {
//      this.sitGoal = new SitGoal(this);
//      this.goalSelector.addGoal(1, new SwimGoal(this));
//      this.goalSelector.addGoal(2, this.sitGoal);
//      this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
//      this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
//      this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
//      this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 1.0D));
//      this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
//      this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
//      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
//      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
//   }
//
//   protected void registerAttributes() {
//      super.registerAttributes();
//      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
//      if (this.isTamed()) {
//         this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
//      } else {
//         this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
//      }
//   }
//
//   protected void registerData() {
//      super.registerData();
//      this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
//      this.dataManager.register(FROM_BUCKET, false);
//   }
//
//   protected void playStepSound(BlockPos pos, BlockState blockIn) {
//      this.playSound(SoundEvents.ENTITY_COD_FLOP, 0.15F, 1.0F);
//   }
//
//   public void writeAdditional(CompoundNBT compound) {
//      super.writeAdditional(compound);
//      compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
//      compound.putBoolean("FromBucket", this.isFromBucket());
//   }
//
//   public void readAdditional(CompoundNBT compound) {
//      super.readAdditional(compound);
//      if (compound.contains("CollarColor", 99)) {
//         this.setCollarColor(DyeColor.byId(compound.getInt("CollarColor")));
//      }
//      this.setFromBucket(compound.getBoolean("FromBucket"));
//   }
//
//   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//      return SoundEvents.ENTITY_COD_HURT;
//   }
//
//   protected SoundEvent getDeathSound() {
//      return SoundEvents.ENTITY_COD_DEATH;
//   }
//
//   protected float getSoundVolume() {
//      return 0.4F;
//   }
//
//   public void livingTick() {
//      super.livingTick();
//   }
//
//   public void tick() {
//      super.tick();
//   }
//   
//   public void onDeath(DamageSource cause) {
//      super.onDeath(cause);
//   }
//
//   protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
//      return sizeIn.height * 0.8F;
//   }
//
//   public int getVerticalFaceSpeed() {
//      return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
//   }
//
//   public boolean canBreatheUnderwater() {
//	      return true;
//	   }
//
//	   public CreatureAttribute getCreatureAttribute() {
//	      return CreatureAttribute.WATER;
//	   }
//
//	   public boolean isNotColliding(IWorldReader worldIn) {
//	      return worldIn.checkNoEntityCollision(this);
//	   }
//
//	   public int getTalkInterval() {
//	      return 120;
//	   }
//
//	   protected int getExperiencePoints(PlayerEntity player) {
//	      return 1 + this.world.rand.nextInt(3);
//	   }
//
//	   protected void updateAir(int p_209207_1_) {
//	      if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
//	         this.setAir(p_209207_1_ - 1);
//	         if (this.getAir() == -20) {
//	            this.setAir(0);
//	            this.attackEntityFrom(DamageSource.DROWN, 2.0F);
//	         }
//	      } else {
//	         this.setAir(300);
//	      }
//
//	   }
//
//	   public void baseTick() {
//	      int i = this.getAir();
//	      super.baseTick();
//	      this.updateAir(i);
//	   }
//
//	   public boolean isPushedByWater() {
//	      return false;
//	   }
//
//	   public boolean canBeLeashedTo(PlayerEntity player) {
//	      return false;
//	   }
//
//   public void setTamed(boolean tamed) {
//      super.setTamed(tamed);
//      if (tamed) {
//         this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
//         this.setHealth(20.0F);
//      } else {
//         this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
//      }
//   }
//
//   public boolean processInteract(PlayerEntity player, Hand hand) {
//	   ItemStack itemstack = player.getHeldItem(hand);
//	   Item item = itemstack.getItem();
//	   if (itemstack.getItem() instanceof SpawnEggItem) {
//		   return super.processInteract(player, hand);
//	   } else {
//		   if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
//			   this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
//			   itemstack.shrink(1);
//			   ItemStack itemstack1 = this.getBucket();
//			   this.setBucketData(itemstack1);
//			   if (!this.world.isRemote) {
//				   CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);
//			   }
//			   
//			   if (itemstack.isEmpty()) {
//				   player.setHeldItem(hand, itemstack1);
//			   } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
//				   player.dropItem(itemstack1, false);
//			   }
//
//			   this.remove();
//			   return true;
//		   } else if (this.isTamed()) {
//			   if (item.isFood() && item.getFood().isMeat() && this.getHealth() < this.getMaxHealth()) {
//				   if (!player.abilities.isCreativeMode) {
//					   itemstack.shrink(1);
//				   }
//
//				   this.heal((float)item.getFood().getHealing());
//				   return true;
//			   }
//
//            if (!(item instanceof DyeItem)) {
//               boolean flag = super.processInteract(player, hand);
//               if (!flag || this.isChild()) {
//            	   if (!world.isRemote()) this.sitGoal.setSitting(!this.isSitting());
//               }
//
//               return flag;
//            }
//
//            DyeColor dyecolor = ((DyeItem)item).getDyeColor();
//            if (dyecolor != this.getCollarColor()) {
//               this.setCollarColor(dyecolor);
//               if (!player.abilities.isCreativeMode) {
//                  itemstack.shrink(1);
//               }
//
//               return true;
//            }
//
//            if (this.isOwner(player) && !this.isBreedingItem(itemstack)) {
//            	if (!world.isRemote()) this.sitGoal.setSitting(!this.isSitting());
//            	this.isJumping = false;
//               this.navigator.clearPath();
//            }
//         } else if (item == Items.CHICKEN) {
//            if (!player.abilities.isCreativeMode) {
//               itemstack.shrink(1);
//            }
//
//            if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
//               this.setTamedBy(player);
//               this.navigator.clearPath();
//               if (!world.isRemote()) this.sitGoal.setSitting(true);
//               this.world.setEntityState(this, (byte)7);
//            } else {
//               this.world.setEntityState(this, (byte)6);
//            }
//
//            return true;
//         }
//
//         return super.processInteract(player, hand);
//      }
//   }
//
//   @Override
//   public boolean canDespawn(double distanceToClosestPlayer) {
//       return !this.isFromBucket() && !this.hasCustomName();
//   }
//
//   @Override
//   public boolean preventDespawn() {
//   	return this.isFromBucket();
//   }
//   
//   public boolean isFromBucket() {
//       return this.dataManager.get(FROM_BUCKET);
//   }
//
//   public void setFromBucket(boolean value) {
//       this.dataManager.set(FROM_BUCKET, value);
//   }
//   
//   protected void setBucketData(ItemStack bucket) {
//       if(this.hasCustomName()) {
//           bucket.setDisplayName(this.getCustomName());
//       }
//   }
//   
//   @OnlyIn(Dist.CLIENT)
//   public float getTailRotation() {
//	   return this.isTamed() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float)Math.PI : ((float)Math.PI / 5F);
//   }
//
//   public boolean isBreedingItem(ItemStack stack) {
//      Item item = stack.getItem();
//      return item.isFood() && item.getFood().isMeat();
//   }
//
//   public int getMaxSpawnedInChunk() {
//      return 8;
//   }
//
//   public DyeColor getCollarColor() {
//      return DyeColor.byId(this.dataManager.get(COLLAR_COLOR));
//   }
//
//   public void setCollarColor(DyeColor collarcolor) {
//      this.dataManager.set(COLLAR_COLOR, collarcolor.getId());
//   }
//
//   public AxolotlEntity createChild(AgeableEntity ageable) {
//      AxolotlEntity axolotlentity = EnvironmentalEntities.AXOLOTL.get().create(this.world);
//      UUID uuid = this.getOwnerId();
//      if (uuid != null) {
//         axolotlentity.setOwnerId(uuid);
//         axolotlentity.setTamed(true);
//      }
//
//      return axolotlentity;
//   }
//
//   public boolean canMateWith(AnimalEntity otherAnimal) {
//      if (otherAnimal == this) {
//         return false;
//      } else if (!this.isTamed()) {
//         return false;
//      } else if (!(otherAnimal instanceof AxolotlEntity)) {
//         return false;
//      } else {
//         AxolotlEntity axolotlentity = (AxolotlEntity)otherAnimal;
//         if (!axolotlentity.isTamed()) {
//            return false;
//         } else if (axolotlentity.isSitting()) {
//            return false;
//         } else {
//            return this.isInLove() && axolotlentity.isInLove();
//         }
//      }
//   }
//
//   public ItemStack getBucket() {
//	   return new ItemStack(EnvironmentalItems.AXOLOTL_BUCKET.get());
//   }
//}
