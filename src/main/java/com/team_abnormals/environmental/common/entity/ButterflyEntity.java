package com.team_abnormals.environmental.common.entity;

import java.awt.Color;
import java.util.Random;

import com.team_abnormals.environmental.common.entity.goals.LandOnPlantGoal;
import com.team_abnormals.environmental.common.entity.util.ButterflyType;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("static-access")
public class ButterflyEntity extends CreatureEntity {
    private BlockPos spawnPosition;
    private boolean field_204228_bA = true;

    public ButterflyEntity(EntityType<? extends ButterflyEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        goalSelector.addGoal(8, new LandOnPlantGoal(this, 0.6D));
        goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 0.6D));
    }

    public static boolean spawnCondition(EntityType<ButterflyEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        int light = world.getLight(pos);
        if (world.getWorld().func_234922_V_() == DimensionType.field_235999_c_ && world.canBlockSeeSky(pos) && light >= 7 && world.getWorld().isDaytime()) {
            return (world.getBlockState(pos).getBlock() instanceof BushBlock || world.getBlockState(pos.down()).getBlock() instanceof BushBlock || world.getBlockState(pos.north()).getBlock() instanceof BushBlock || world.getBlockState(pos.south()).getBlock() instanceof BushBlock || world.getBlockState(pos.east()).getBlock() instanceof BushBlock || world.getBlockState(pos.west()).getBlock() instanceof BushBlock) && world.getLightSubtracted(pos, 0) > 8;
        }
        else return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static String getBottleName() {
        return "entity.environmental.butterfly." + Variant.toRegistryName(Variant.name);
    }

    public boolean func_204209_c(int p_204209_1_) {
        return !field_204228_bA;
    }

    public Variant getButterflyVariant() {
        ButterflyType type = ButterflyType.types[rand.nextInt(ButterflyType.values().length)];
        Variant variant = type.getVariants().get(rand.nextInt(type.getVariants().size()));
        System.out.println(variant + type.name());
        return variant;
    }

    @OnlyIn(Dist.CLIENT)
    public Color getPatternColorA() {
        return getButterflyVariant().colorA;
    }

    @OnlyIn(Dist.CLIENT)
    public Color getPatternColorB() {
        return getButterflyVariant().colorB;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getPatternTextureA() {
        return getButterflyVariant().patternA;
    }

	@OnlyIn(Dist.CLIENT)
    public ResourceLocation getPatternTextureB() {
        return getButterflyVariant().patternB;
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entity) {
    }

    protected void collideWithNearbyEntities() {
    }
    
    public static AttributeModifierMap.MutableAttribute registerAttributes() {
    	return MobEntity.func_233666_p_().func_233815_a_(Attributes.MAX_HEALTH, 3.0D);
    }

    public void tick() {
        super.tick();
        setMotion(getMotion().mul(1.0D, 0.6D, 1.0D));
    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (spawnPosition != null && (!world.isAirBlock(spawnPosition) || spawnPosition.getY() < 1)) {
            spawnPosition = null;
        }
        if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.withinDistance(getPositionVec(), 2.0D)) {
            spawnPosition = new BlockPos(getPosX() + (double)rand.nextInt(7) - (double)rand.nextInt(7), getPosY() + (double)rand.nextInt(6) - 2.0D, getPosZ() + (double)rand.nextInt(7) - (double)rand.nextInt(7));
        }
        double x = (double)spawnPosition.getX() + 0.5D - getPosX();
        double y = (double)spawnPosition.getY() + 0.1D - getPosY();
        double z = (double)spawnPosition.getZ() + 0.5D - getPosZ();
        Vector3d lvt_9_1_ = getMotion();
        Vector3d lvt_10_1_ = lvt_9_1_.add((Math.signum(x) * 0.5D - lvt_9_1_.x) * 0.10000000149011612D, (Math.signum(y) * 0.699999988079071D - lvt_9_1_.y) * 0.10000000149011612D, (Math.signum(z) * 0.5D - lvt_9_1_.z) * 0.10000000149011612D);
        setMotion(lvt_10_1_);
        float lvt_11_1_ = (float)(MathHelper.atan2(lvt_10_1_.z, lvt_10_1_.x) * 57.2957763671875D) - 90.0F;
        float lvt_12_1_ = MathHelper.wrapDegrees(lvt_11_1_ - rotationYaw);
        moveForward = 0.5F;
        rotationYaw += lvt_12_1_;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void fall(float p_180430_1_, float p_180430_2_) {
    }

    protected void updateFallState(double p_184231_1_, boolean p_184231_3_, BlockState state, BlockPos pos) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (isInvulnerableTo(source)) {
            return false;
        } else {
            return super.attackEntityFrom(source, damage);
        }
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height / 2.0F;
    }

    public static class Variant {
        //display name
        private static String name;
        //primary color
        private static Color colorA;
        //secondary color
        private static Color colorB;
        //primary pattern texture
        //environmental:textures/entity/butterfly/brushfoot_a
        private static ResourceLocation patternA = new ResourceLocation("environmental", "textures/entity/butterfly/butterfly_a_pattern_1.png"); //new ResourceLocation("environmental", "textures/entity/butterfly/" + toRegistryName(name) + "_a");
        //secondary pattern texture
        //environmental:textures/entity/butterfly/brushfoot_b
        private static ResourceLocation patternB = new ResourceLocation("environmental", "textures/entity/butterfly/butterfly_b_pattern_1.png"); //new ResourceLocation("environmental", "textures/entity/butterfly/" + toRegistryName(name) + "_b");

        public Variant(String nameIn, Color a, Color b) {
            name = nameIn;
            colorA = a;
            colorB = b;
        }

        private static String toRegistryName(String string) {
            string.toLowerCase().replace(" ", "_").replace("-", "_");
            return string;
        }
    }
}