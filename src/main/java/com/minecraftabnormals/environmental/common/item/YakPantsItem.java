package com.minecraftabnormals.environmental.common.item;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MODID)
public class YakPantsItem extends ArmorItem {

	public YakPantsItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ItemStack legsStack = entity.getItemStackFromSlot(EquipmentSlotType.LEGS);
		boolean wearingPants = legsStack.getItem() instanceof YakPantsItem;
		if (entity instanceof PlayerEntity) {
			float defaultHeight = 0.6F;
			float ugpradedHeight = 1.1F;
			if (wearingPants && entity.stepHeight < ugpradedHeight) {
				entity.stepHeight = ugpradedHeight;
			} else if (entity.stepHeight > defaultHeight) {
				entity.stepHeight = defaultHeight;
			}
		}
		
		if (wearingPants && entity.getRidingEntity() instanceof LivingEntity) {
			LivingEntity mount = (LivingEntity)entity.getRidingEntity();
			mount.addPotionEffect(new EffectInstance(Effects.REGENERATION, 60));
			mount.addPotionEffect(new EffectInstance(Effects.SPEED, 60, 1));
		}
	}
}
