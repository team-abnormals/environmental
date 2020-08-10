package com.minecraftabnormals.environmental.common.entity;

import javax.annotation.Nullable;

import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatTypes;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DeerEntity extends AnimalEntity {
    private static final DataParameter<Integer> DEER_COAT_COLOR = EntityDataManager.createKey(DeerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DEER_COAT_TYPE  = EntityDataManager.createKey(DeerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_ANTLERS     = EntityDataManager.createKey(DeerEntity.class, DataSerializers.BOOLEAN);

    public DeerEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

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

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DEER_COAT_COLOR, 0);
        this.dataManager.register(DEER_COAT_TYPE, 0);
        this.dataManager.register(HAS_ANTLERS, true);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("CoatColor", this.getCoatColor());
        compound.putInt("CoatType", this.getCoatType());
        compound.putBoolean("Antlers", this.hasAntlers());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setCoatColor(compound.getInt("CoatColor"));
        this.setCoatType(compound.getInt("CoatType"));
        this.setHasAntlers(compound.getBoolean("Antlers"));
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
    public DeerEntity createChild(AgeableEntity ageable) {
        return EnvironmentalEntities.DEER.get().create(this.world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D);
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
