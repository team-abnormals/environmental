package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.item.explorer.ThiefHoodItem;
import com.teamabnormals.environmental.core.registry.EnvironmentalAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	@Inject(method = "getVisibilityPercent", at = @At("TAIL"), cancellable = true)
	private void getVisibilityMultiplier(@Nullable Entity lookingEntity, CallbackInfoReturnable<Double> cir) {
		double value = cir.getReturnValue();
		if (lookingEntity != null) {
			ItemStack stack = ((LivingEntity) (Object) this).getItemBySlot(EquipmentSlot.HEAD);
			Item item = stack.getItem();
			if (item instanceof ThiefHoodItem) {
				Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(EquipmentSlot.HEAD).get(EnvironmentalAttributes.STEALTH.get());
				if (modifiers.isEmpty())
					return;
				double attribute = modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
				value *= 1.0D - Math.max(attribute, 0.0D);
			}
		}
		cir.setReturnValue(value);
	}
}
