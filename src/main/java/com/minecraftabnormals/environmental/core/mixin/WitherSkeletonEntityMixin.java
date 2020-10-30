package com.minecraftabnormals.environmental.core.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {

	protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "setEquipmentBasedOnDifficulty", at = @At("TAIL"))
	private void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty, CallbackInfo info) {
		Random random = this.world.getRandom();
		int difficultyChance = difficulty.getDifficulty().getId() + 1;

		if (random.nextInt(difficultyChance) != 0) {
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_AXE));
		}

		if (Math.random() < 0.075F) {
			this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(EnvironmentalItems.THIEF_HOOD.get()));
			this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 1.0F;
		}
	}
}
