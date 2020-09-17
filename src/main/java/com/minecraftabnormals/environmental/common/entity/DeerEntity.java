package com.minecraftabnormals.environmental.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatTypes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DeerEntity extends AnimalEntity {
    private static final DataParameter<Integer> DEER_COAT_COLOR = EntityDataManager.createKey(DeerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DEER_COAT_TYPE = EntityDataManager.createKey(DeerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_ANTLERS = EntityDataManager.createKey(DeerEntity.class, DataSerializers.BOOLEAN);

    private int floweringTime;
    private List<BlockState> flowers = new ArrayList<>();
    
    public DeerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.floweringTime = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromTag(ItemTags.FLOWERS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DEER_COAT_COLOR, 0);
        this.dataManager.register(DEER_COAT_TYPE, 0);
        this.dataManager.register(HAS_ANTLERS, true);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("CoatColor", this.getCoatColor());
        compound.putInt("CoatType", this.getCoatType());
        compound.putBoolean("Antlers", this.hasAntlers());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setCoatColor(compound.getInt("CoatColor"));
        this.setCoatType(compound.getInt("CoatType"));
        this.setHasAntlers(compound.getBoolean("Antlers"));
    }
    
    @Override
    public void livingTick() {
    	super.livingTick();
    	if (this.floweringTime > 0) {
    		this.floweringTime -= 1;
    		if (!flowers.isEmpty() && this.world.getGameTime() % 60 == 0) {
    			BlockState state = flowers.get(rand.nextInt(flowers.size()));
    			if (state.isValidPosition(world, this.getPosition()) && world.isAirBlock(this.getPosition())) {
    				world.setBlockState(this.getPosition(), state);
    				if (world.isRemote()) {
        				BoneMealItem.spawnBonemealParticles(world, this.getPosition(), 3);
        			}
    			}
    		}
    	} else {
    		this.flowers.clear();
    	}
    }
    
    private void particleCloud(IParticleData particle) {
        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particle, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
        }
    }
    
    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
    	ItemStack stack = player.getHeldItem(hand);
    	Item item = stack.getItem();
    	
    	if (item == Items.GOLDEN_APPLE) {
    		this.floweringTime = 600;
    		this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
    		return ActionResultType.SUCCESS;
    	} else if (item.isIn(ItemTags.FLOWERS) && item instanceof BlockItem) {
    		BlockItem block = (BlockItem)item;
    		if (!this.flowers.contains(block.getBlock().getDefaultState())) {
    			flowers.add(block.getBlock().getDefaultState());
    			this.particleCloud(ParticleTypes.HAPPY_VILLAGER);
    		}
    		return ActionResultType.SUCCESS;
    	}
    	
    	return super.func_230254_b_(player, hand);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FOX_DEATH;
    }

    private void setCoatColor(int id) {
        this.dataManager.set(DEER_COAT_COLOR, id);
    }

    public int getCoatColor() {
        return this.dataManager.get(DEER_COAT_COLOR);
    }

    private void setCoatType(int id) {
        this.dataManager.set(DEER_COAT_TYPE, id);
    }

    public int getCoatType() {
        return this.dataManager.get(DEER_COAT_TYPE);
    }

    private void setHasAntlers(boolean antlers) {
        this.dataManager.set(HAS_ANTLERS, antlers);
    }

    public boolean hasAntlers() {
        return this.dataManager.get(HAS_ANTLERS);
    }

    public void setDeerData(DeerCoatColors coatColor, DeerCoatTypes coatType, boolean antlers) {
        this.setCoatColor(coatColor.getId());
        this.setCoatType(coatType.getId());
        this.setHasAntlers(antlers);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem().isIn(ItemTags.FLOWERS);
    }

    @Override
    public DeerEntity createChild(AgeableEntity ageable) {
        DeerEntity entity = EnvironmentalEntities.DEER.get().create(this.world);
        
        entity.setCoatColor(((DeerEntity)ageable).getCoatColor());
        entity.setCoatType(this.getCoatType());
        entity.setHasAntlers(rand.nextBoolean());
        
        return entity;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(EnvironmentalItems.DEER_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(world, difficulty, reason, spawnDataIn, dataTag);

        this.setCoatColor(rand.nextInt(DeerCoatColors.values().length));
        this.setCoatType(rand.nextInt(DeerCoatTypes.values().length));
        this.setHasAntlers(rand.nextBoolean());

        return super.onInitialSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
