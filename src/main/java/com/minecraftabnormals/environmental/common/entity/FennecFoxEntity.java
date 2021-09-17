package com.minecraftabnormals.environmental.common.entity;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class FennecFoxEntity extends FoxEntity {
	public FennecFoxEntity(EntityType<? extends FoxEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isBaby() ? size.height * 0.45F : 0.375F;
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		if (this.random.nextFloat() < 0.2F) {
			float f = this.random.nextFloat();
			ItemStack stack;
			if (f < 0.05F) {
				stack = new ItemStack(Items.EMERALD);
			} else if (f < 0.2F) {
				stack = new ItemStack(Items.GOLD_INGOT);
			} else if (f < 0.4F) {
				stack = this.random.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE);
			} else if (f < 0.6F) {
				stack = new ItemStack(Items.GREEN_DYE);
			} else if (f < 0.8F) {
				stack = new ItemStack(Items.LEATHER);
			} else {
				stack = new ItemStack(Items.BONE);
			}

			this.setItemSlot(EquipmentSlotType.MAINHAND, stack);
		}
	}
}
