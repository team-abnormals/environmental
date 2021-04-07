package com.minecraftabnormals.environmental.common.entity;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class FennecFoxEntity extends FoxEntity {
    public FennecFoxEntity(EntityType<? extends FoxEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.45F : 0.375F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(EnvironmentalItems.FENNEC_FOX_SPAWN_EGG.get());
    }
}
