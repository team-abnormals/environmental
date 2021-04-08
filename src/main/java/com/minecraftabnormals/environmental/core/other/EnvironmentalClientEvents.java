package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, value = Dist.CLIENT)
public class EnvironmentalClientEvents {

	@SubscribeEvent
	public static void renderNameplate(RenderNameplateEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if (entity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
				event.setResult(Result.DENY);
			}
		}
	}
}
