package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

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

public class DuckEggEntity extends ProjectileItemEntity {

   public DuckEggEntity(EntityType<? extends DuckEggEntity> type, World worldIn) {
      super(type, worldIn);
   }

   public DuckEggEntity(World worldIn, LivingEntity throwerIn) {
	   super(EnvironmentalEntities.DUCK_EGG.get(), throwerIn, worldIn);
   }

   public DuckEggEntity(World worldIn, double x, double y, double z) {
	   super(EnvironmentalEntities.DUCK_EGG.get(), x, y, z, worldIn);
   }
   
   public DuckEggEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
	   this(EnvironmentalEntities.DUCK_EGG.get(), world);
   }

   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
	   if (id == 3) {
		   for(int i = 0; i < 8; ++i) {
			   this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
		   }
	   }
   }
   
   @Override
   protected void onEntityHit(EntityRayTraceResult result) {
	   super.onEntityHit(result);
	   result.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0F);
   }

   @Override
   protected void onImpact(RayTraceResult result) {
	   super.onImpact(result);
	   if (!this.world.isRemote) {
		   if (this.rand.nextInt(8) == 0) {
			   int i = 1;
			   if (this.rand.nextInt(32) == 0) {
				   i = 4;
			   }

			   for(int j = 0; j < i; ++j) {
				   DuckEntity chickenentity = EnvironmentalEntities.DUCK.get().create(this.world);
				   chickenentity.setGrowingAge(-24000);
				   chickenentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
				   this.world.addEntity(chickenentity);
			   }
		   }
		   this.world.setEntityState(this, (byte)3);
		   this.remove();
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
   public IPacket<?> createSpawnPacket() {
	   return NetworkHooks.getEntitySpawningPacket(this);
   }
}