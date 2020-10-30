package com.minecraftabnormals.environmental.core.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecraftabnormals.environmental.common.item.ThiefHoodItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(method = "getVisibilityMultiplier", at = @At("TAIL"), cancellable = true)
	private void getVisibilityMultiplier(@Nullable Entity lookingEntity, CallbackInfoReturnable<Double> cir) {
		double value = cir.getReturnValue();
		if (lookingEntity != null) {
			ItemStack stack = ((LivingEntity)(Object)this).getItemStackFromSlot(EquipmentSlotType.HEAD);
			Item item = stack.getItem();
			if (item instanceof ThiefHoodItem) {
				int uses = stack.getTag().getInt(ThiefHoodItem.NBT_TAG);
				value *= 1.0D - ThiefHoodItem.getIncreaseForUses(uses) * 0.15D;
			}
		}

		cir.setReturnValue(value);
	}
}
