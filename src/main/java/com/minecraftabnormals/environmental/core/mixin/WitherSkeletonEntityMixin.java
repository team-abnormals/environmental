package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {

	protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "setEquipmentBasedOnDifficulty", at = @At("TAIL"))
	private void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty, CallbackInfo info) {
		if (Math.random() < 0.05F) {
			this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(EnvironmentalItems.THIEF_HOOD.get()));
			this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 1.0F;
		}
	}
}
