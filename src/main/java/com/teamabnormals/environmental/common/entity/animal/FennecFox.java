package com.teamabnormals.environmental.common.entity.animal;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FennecFox extends Fox {
	public FennecFox(EntityType<? extends Fox> type, Level world) {
		super(type, world);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
		return this.isBaby() ? size.height * 0.45F : 0.375F;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		if (random.nextFloat() < 0.2F) {
			float f = random.nextFloat();
			ItemStack stack;
			if (f < 0.05F) {
				stack = new ItemStack(Items.EMERALD);
			} else if (f < 0.2F) {
				stack = new ItemStack(Items.GOLD_INGOT);
			} else if (f < 0.4F) {
				stack = random.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE);
			} else if (f < 0.6F) {
				stack = new ItemStack(Items.GREEN_DYE);
			} else if (f < 0.8F) {
				stack = new ItemStack(Items.LEATHER);
			} else {
				stack = new ItemStack(Items.BONE);
			}

			this.setItemSlot(EquipmentSlot.MAINHAND, stack);
		}
	}
}
