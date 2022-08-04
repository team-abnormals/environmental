package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalMobEffects;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Koi extends AbstractFish {

	public Koi(EntityType<? extends AbstractFish> type, Level world) {
		super(type, world);
		this.moveControl = new MoveHelperController(this);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.4F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new PanicGoal(this, 4.25D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 5.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 50));
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		return new WaterBoundPathNavigation(this, worldIn);
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(0.01F, travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(travelVector);
		}
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 vector3d = this.getDeltaMovement();
		if (Mth.abs((float) vector3d.y) >= 0.01) {
			this.setXRot((float) (Mth.atan2(vector3d.y, Mth.sqrt((float) vector3d.horizontalDistanceSqr())) * (double) (180F / (float) Math.PI)));
		} else {
			this.setXRot(0);
		}
		this.setXRot(normalizeRotation(this.xRotO, this.getXRot()));
	}

	protected static float normalizeRotation(float prevRot, float rot) {
		while (rot - prevRot < -180.0F) {
			prevRot -= 360.0F;
		}

		while (rot - prevRot >= 180.0F) {
			prevRot += 360.0F;
		}

		return Mth.lerp(0.2F, prevRot, rot);
	}

	@Override
	public void aiStep() {
		if (!this.isInWater() && this.onGround && this.verticalCollision) {
			this.setDeltaMovement(this.getDeltaMovement().add(((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.45F, ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			this.onGround = false;
			this.hasImpulse = true;
			this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
		}
		if (level.getGameTime() % 20 == 0 && EnvironmentalConfig.COMMON.serenityEffect.get()) {
			int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
			int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
			for (Player player : level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(horizontalRange, verticalRange, horizontalRange))) {
				if (!level.isClientSide())
					player.addEffect(new MobEffectInstance(EnvironmentalMobEffects.SERENITY.get(), 100, 0, false, false));
			}
		}
		super.aiStep();
	}

	public SoundEvent getFlopSound() {
		return EnvironmentalSoundEvents.KOI_FLOP.get();
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.COD_HURT;
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(EnvironmentalItems.KOI_BUCKET.get());
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10);
	}

	public static boolean canKoiSpawn(EntityType<? extends AbstractFish> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
		return worldIn.getBlockState(pos).is(Blocks.WATER) && worldIn.getBlockState(pos.above()).is(Blocks.WATER) && pos.getY() > 55;
	}

	static class MoveHelperController extends MoveControl {

		private final Koi koi;

		public MoveHelperController(Koi koi) {
			super(koi);
			this.koi = koi;
		}

		@Override
		public void tick() {
			if (this.koi.isEyeInFluid(FluidTags.WATER)) {
				this.koi.setDeltaMovement(this.koi.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
			}

			if (this.operation == MoveControl.Operation.MOVE_TO && !this.koi.getNavigation().isDone()) {
				double d0 = this.wantedX - this.koi.getX();
				double d1 = this.wantedY - this.koi.getY();
				double d2 = this.wantedZ - this.koi.getZ();
				double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
				d1 = d1 / d3;
				if (d0 != 0 || d1 != 0) {
					float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.koi.setYRot(this.rotlerp(this.koi.getYRot(), f, 15.0F));
					this.koi.yBodyRot = this.koi.getYRot();
					this.koi.yHeadRot = this.koi.getYRot();
				}
				float f1 = (float) (this.speedModifier * this.koi.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
				this.koi.setSpeed(Mth.lerp(0.125F, this.koi.getSpeed(), f1));
				this.koi.setDeltaMovement(this.koi.getDeltaMovement().add(0.0D, (double) this.koi.getSpeed() * d1 * 0.03D, 0.0D));
				// this.koi.setMoveVertical((float) (this.koi.getAIMoveSpeed() * d1 * 0.03F));
			}
		}
	}
}
