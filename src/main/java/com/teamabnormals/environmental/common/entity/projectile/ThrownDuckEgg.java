package com.teamabnormals.environmental.common.entity.projectile;

import com.teamabnormals.environmental.common.entity.animal.Duck;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class ThrownDuckEgg extends ThrowableItemProjectile {

	public ThrownDuckEgg(EntityType<? extends ThrownDuckEgg> type, Level worldIn) {
		super(type, worldIn);
	}

	public ThrownDuckEgg(Level worldIn, LivingEntity throwerIn) {
		super(EnvironmentalEntityTypes.DUCK_EGG.get(), throwerIn, worldIn);
	}

	public ThrownDuckEgg(Level worldIn, double x, double y, double z) {
		super(EnvironmentalEntityTypes.DUCK_EGG.get(), x, y, z, worldIn);
	}

	public ThrownDuckEgg(PlayMessages.SpawnEntity spawnEntity, Level world) {
		this(EnvironmentalEntityTypes.DUCK_EGG.get(), world);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				for (int j = 0; j < i; ++j) {
					Duck chickenentity = EnvironmentalEntityTypes.DUCK.get().create(this.level);
					chickenentity.setAge(-24000);
					chickenentity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
					this.level.addFreshEntity(chickenentity);
				}
			}
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	@Override
	protected Item getDefaultItem() {
		return EnvironmentalItems.DUCK_EGG.get();
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(EnvironmentalItems.DUCK_EGG.get());
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}