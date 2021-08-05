package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEffects;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class KoiEntity extends AbstractFishEntity {

	public KoiEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
		this.moveControl = new MoveHelperController(this);
		this.setPathfindingMalus(PathNodeType.WATER, 0.4F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new PanicGoal(this, 4.25D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 5.0F, 1.6, 1.4, EntityPredicates.NO_SPECTATORS::test));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 50));
	}

	@Override
	protected PathNavigator createNavigation(World worldIn) {
		return new SwimmerPathNavigator(this, worldIn);
	}

	@Override
	public void travel(Vector3d travelVector) {
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
		Vector3d vector3d = this.getDeltaMovement();
		if (MathHelper.abs((float) vector3d.y) >= 0.01) {
			this.xRot = (float) (MathHelper.atan2(vector3d.y, MathHelper.sqrt(getHorizontalDistanceSqr(vector3d))) * (double) (180F / (float) Math.PI));
		} else {
			this.xRot = 0;
		}
		this.xRot = normalizeRotation(this.xRotO, this.xRot);
	}

	protected static float normalizeRotation(float prevRot, float rot) {
		while (rot - prevRot < -180.0F) {
			prevRot -= 360.0F;
		}

		while (rot - prevRot >= 180.0F) {
			prevRot += 360.0F;
		}

		return MathHelper.lerp(0.2F, prevRot, rot);
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
			for (PlayerEntity player : level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(horizontalRange, verticalRange, horizontalRange))) {
				if (!level.isClientSide())
					player.addEffect(new EffectInstance(EnvironmentalEffects.SERENITY.get(), 100, 0, false, false));
			}
		}
		super.aiStep();
	}

	public SoundEvent getFlopSound() {
		return EnvironmentalSounds.KOI_FLOP.get();
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

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10);
	}

	public static boolean canKoiSpawn(EntityType<? extends AbstractFishEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
		return worldIn.getBlockState(pos).is(Blocks.WATER) && worldIn.getBlockState(pos.above()).is(Blocks.WATER) && pos.getY() > 55;
	}

	static class MoveHelperController extends MovementController {

		private final KoiEntity koi;

		public MoveHelperController(KoiEntity koi) {
			super(koi);
			this.koi = koi;
		}

		@Override
		public void tick() {
			if (this.koi.isEyeInFluid(FluidTags.WATER)) {
				this.koi.setDeltaMovement(this.koi.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
			}

			if (this.operation == MovementController.Action.MOVE_TO && !this.koi.getNavigation().isDone()) {
				double d0 = this.wantedX - this.koi.getX();
				double d1 = this.wantedY - this.koi.getY();
				double d2 = this.wantedZ - this.koi.getZ();
				double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d1 = d1 / d3;
				if (d0 != 0 || d1 != 0) {
					float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.koi.yRot = this.rotlerp(this.koi.yRot, f, 15.0F);
					this.koi.yBodyRot = this.koi.yRot;
					this.koi.yHeadRot = this.koi.yRot;
				}
				float f1 = (float) (this.speedModifier * this.koi.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
				this.koi.setSpeed(MathHelper.lerp(0.125F, this.koi.getSpeed(), f1));
				this.koi.setDeltaMovement(this.koi.getDeltaMovement().add(0.0D, (double) this.koi.getSpeed() * d1 * 0.03D, 0.0D));
				// this.koi.setMoveVertical((float) (this.koi.getAIMoveSpeed() * d1 * 0.03F));
			}
		}
	}
}
