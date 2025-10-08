package com.teamabnormals.environmental.common.entity.projectile;

import com.teamabnormals.environmental.common.entity.animal.MuddyPig;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ThrownMudBall extends ThrowableItemProjectile {
	public ThrownMudBall(EntityType<? extends ThrownMudBall> entity, Level world) {
		super(entity, world);
	}

	public ThrownMudBall(Level worldIn, LivingEntity throwerIn) {
		super(EnvironmentalEntityTypes.MUD_BALL.get(), throwerIn, worldIn);
	}

	public ThrownMudBall(Level worldIn, double x, double y, double z) {
		super(EnvironmentalEntityTypes.MUD_BALL.get(), x, y, z, worldIn);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		if (result.getType() == HitResult.Type.ENTITY) {
			Entity entity = ((EntityHitResult) result).getEntity();
			entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) 0);
			if (entity instanceof Pig pig && MuddyPig.enabled()) {
				MuddyPig.setMuddy(pig, true);
				MuddyPig.updateDryingTime(pig, 1200);
			}
		}

		if (!this.level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}

	}

	@Override
	protected Item getDefaultItem() {
		return EnvironmentalItems.MUD_BALL.get();
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(EnvironmentalItems.MUD_BALL.get());
	}
}