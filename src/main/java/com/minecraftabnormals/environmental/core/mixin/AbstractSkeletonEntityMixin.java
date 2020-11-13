package com.minecraftabnormals.environmental.core.mixin;

import java.util.Random;

import net.minecraft.world.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends MonsterEntity {

	protected AbstractSkeletonEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "setEquipmentBasedOnDifficulty", at = @At("TAIL"))
	private void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty, CallbackInfo info) {
		Random random = this.world.getRandom();
		int difficultyChance = difficulty.getDifficulty().getId() + 1;

		if (this.world.func_234922_V_() != DimensionType.THE_NETHER && random.nextInt(difficultyChance) == 0) {
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(random.nextInt(difficultyChance) == 0 ? Items.WOODEN_SWORD : Items.WOODEN_AXE));
		}

		if (Math.random() < 0.025F) {
			this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(EnvironmentalItems.THIEF_HOOD.get()));
			this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 1.0F;
		}
	}
}
