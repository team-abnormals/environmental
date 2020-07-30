package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalEntities;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DuckEntity extends ChickenEntity {

	public DuckEntity(EntityType<? extends ChickenEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	@Override
    public DuckEntity createChild(AgeableEntity ageable) {
        return EnvironmentalEntities.DUCK.get().create(this.world);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(EnvironmentalItems.DUCK_SPAWN_EGG.get());
    }
}
