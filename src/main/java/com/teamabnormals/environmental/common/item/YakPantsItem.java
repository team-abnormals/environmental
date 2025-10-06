package com.teamabnormals.environmental.common.item;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class YakPantsItem extends ArmorItem {

	public YakPantsItem(Holder<ArmorMaterial> materialIn, ArmorItem.Type slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
		ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
		EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
		ResourceLocation name = ResourceLocation.withDefaultNamespace("armor." + type.getName());
		modifiers.withModifierAdded(Attributes.STEP_HEIGHT, new AttributeModifier(name, 0.4F, Operation.ADD_VALUE), slot);
		return modifiers;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingUpdate(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity entity) {
			ItemStack legsStack = entity.getItemBySlot(EquipmentSlot.LEGS);
			if (legsStack.getItem() instanceof YakPantsItem && entity.getVehicle() instanceof LivingEntity mount) {
				if (!entity.level().isClientSide()) {
					mount.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60));
					mount.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 1));
				}
			}
		}
	}
}
