package com.minecraftabnormals.environmental.core.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Maps;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;

@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends AbstractIllagerEntity {

	protected VindicatorEntityMixin(EntityType<? extends AbstractIllagerEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "applyWaveBonus", at = @At("TAIL"))
	private void waveBonusApplication(int p_213660_1_, boolean p_213660_2_, CallbackInfo info) {
		int bladeChance = 5 - this.world.getDifficulty().getId();
		if (this.world.rand.nextInt(bladeChance) == 0) {
			ItemStack itemstack = new ItemStack(EnvironmentalItems.EXECUTIONER_CLEAVER.get());
			Raid raid = this.getRaid();
			int i = 1;
			if (p_213660_1_ > raid.getWaves(Difficulty.NORMAL)) {
				i = 2;
			}

			boolean flag = this.rand.nextFloat() <= raid.getEnchantOdds();
			if (flag) {
				Map<Enchantment, Integer> map = Maps.newHashMap();
				map.put(Enchantments.SHARPNESS, i);
				EnchantmentHelper.setEnchantments(map, itemstack);
			}

			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
		}

	}

	@Inject(method = "setEquipmentBasedOnDifficulty", at = @At("TAIL"))
	private void replaceAxeWithIronAxe(DifficultyInstance difficulty, CallbackInfo info) {
		int bladeChance = 5 - this.world.getDifficulty().getId();
		if (this.getRaid() == null && this.world.rand.nextInt(bladeChance) == 0) {
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(EnvironmentalItems.EXECUTIONER_CLEAVER.get()));
		}
	}
}
