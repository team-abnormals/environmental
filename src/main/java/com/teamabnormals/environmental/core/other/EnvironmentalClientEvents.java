package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, value = Dist.CLIENT)
public class EnvironmentalClientEvents {

	@SubscribeEvent
	public static void renderNameplate(RenderNameplateEvent event) {
		if (event.getEntity() instanceof LivingEntity entity) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
				event.setResult(Result.DENY);
			}
		}
	}
}
