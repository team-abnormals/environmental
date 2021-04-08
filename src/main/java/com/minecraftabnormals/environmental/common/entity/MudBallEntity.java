package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.common.entity.util.SlabfishOverlay;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class MudBallEntity extends ProjectileItemEntity {
	public MudBallEntity(EntityType<? extends MudBallEntity> entity, World world) {
		super(entity, world);
	}

	public MudBallEntity(World worldIn, LivingEntity throwerIn) {
		super(EnvironmentalEntities.MUD_BALL.get(), throwerIn, worldIn);
	}

	public MudBallEntity(World worldIn, double x, double y, double z) {
		super(EnvironmentalEntities.MUD_BALL.get(), x, y, z, worldIn);
	}

	public MudBallEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
		this(EnvironmentalEntities.MUD_BALL.get(), world);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) result).getEntity();
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float) 0);
			if (entity instanceof SlabfishEntity) {
				SlabfishEntity slabby = (SlabfishEntity) entity;
				if (slabby.getSlabfishOverlay() != SlabfishOverlay.MUDDY)
					slabby.setSlabfishOverlay(SlabfishOverlay.MUDDY);
			}
		}

		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.remove();
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
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}