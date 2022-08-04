package com.teamabnormals.environmental.common.entity.projectile;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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

	public ThrownMudBall(PlayMessages.SpawnEntity spawnEntity, Level world) {
		this(EnvironmentalEntityTypes.MUD_BALL.get(), world);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		if (result.getType() == HitResult.Type.ENTITY) {
			Entity entity = ((EntityHitResult) result).getEntity();
			entity.hurt(DamageSource.thrown(this, this.getOwner()), (float) 0);
			if (entity instanceof Slabfish slabby) {
				if (slabby.getSlabfishOverlay() != SlabfishOverlay.MUDDY)
					slabby.setSlabfishOverlay(SlabfishOverlay.MUDDY);
			}
		}

		if (!this.level.isClientSide) {
			this.level.broadcastEntityEvent(this, (byte) 3);
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

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}