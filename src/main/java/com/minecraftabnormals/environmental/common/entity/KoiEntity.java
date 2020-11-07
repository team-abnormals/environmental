package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEffects;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalSounds;
import com.teamabnormals.abnormals_core.common.entity.BucketableWaterMobEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class KoiEntity extends BucketableWaterMobEntity {

	public KoiEntity(EntityType<? extends BucketableWaterMobEntity> type, World world) {
		super(type, world);
		this.moveController = new MoveHelperController(this);
		this.setPathPriority(PathNodeType.WATER, 0.6F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new PanicGoal(this, 4.25D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.6, 1.4, EntityPredicates.NOT_SPECTATING::test));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 50));
	}

	@Override
	protected PathNavigator createNavigator(World worldIn) {
		return new SwimmerPathNavigator(this, worldIn);
	}

	@Override
	public void travel(Vector3d travelVector) {
		if (this.isServerWorld() && this.isInWater()) {
			this.moveRelative(0.01F, travelVector);
			this.move(MoverType.SELF, this.getMotion());
			this.setMotion(this.getMotion().scale(0.9));
			if (this.getAttackTarget() == null) {
				this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(travelVector);
		}
	}

	@Override
	public void livingTick() {
		if (!this.isInWater() && this.onGround && this.collidedVertically) {
			this.setMotion(this.getMotion().add(((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4F, ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			this.onGround = false;
			this.isAirBorne = true;
			this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
		}
		if (world.getGameTime() % 20 == 0 && EnvironmentalConfig.COMMON.serenityEffect.get()) {
			int horizontalRange = EnvironmentalConfig.COMMON.koiHorizontalSerenityRange.get();
			int verticalRange = EnvironmentalConfig.COMMON.koiVerticalSerenityRange.get();
			for (PlayerEntity player : world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(horizontalRange, verticalRange, horizontalRange))) {
				player.addPotionEffect(new EffectInstance(EnvironmentalEffects.SERENITY.get(), 100, 0, false, false));
			}
		}
		super.livingTick();
	}

	public SoundEvent getFlopSound() {
		return EnvironmentalSounds.ENTITY_KOI_FLOP.get();
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COD_AMBIENT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COD_DEATH;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COD_HURT;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(EnvironmentalItems.KOI_SPAWN_EGG.get());
	}

	@Override
	public ItemStack getBucket() {
		return new ItemStack(EnvironmentalItems.KOI_BUCKET.get(), 1);
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10);
	}

	static class MoveHelperController extends MovementController {

		private final KoiEntity koi;

		public MoveHelperController(KoiEntity koi) {
			super(koi);
			this.koi = koi;
		}

		@Override
		public void tick() {
			if (this.koi.areEyesInFluid(FluidTags.WATER)) {
				this.koi.setMotion(this.koi.getMotion().add(0.0D, 0.005D, 0.0D));
			}

			if (this.action == MovementController.Action.MOVE_TO && !this.koi.getNavigator().noPath()) {
				double d0 = this.posX - this.koi.getPosX();
				double d1 = this.posY - this.koi.getPosY();
				double d2 = this.posZ - this.koi.getPosZ();
				double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d1 = d1 / d3;
				if (d0 >= 0.0001 || d0 <= -0.0001 || d1 >= 0.0001 || d1 <= -0.0001) {
					float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.koi.rotationYaw = this.limitAngle(this.koi.rotationYaw, f, 15.0F);
					this.koi.renderYawOffset = this.koi.rotationYaw;
					this.koi.rotationYawHead = this.koi.rotationYaw;
				}
				float f1 = (float) (this.speed * this.koi.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
				this.koi.setAIMoveSpeed(MathHelper.lerp(0.125F, this.koi.getAIMoveSpeed(), f1));
				this.koi.setMotion(this.koi.getMotion().add(0.0D, (double) this.koi.getAIMoveSpeed() * d1 * 0.03D, 0.0D));
				// this.koi.setMoveVertical((float) (this.koi.getAIMoveSpeed() * d1 * 0.03F));
			}
		}
	}
}
