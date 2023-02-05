package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.client.model.SlabfishBucketModel;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalClientRegistry {

	@SubscribeEvent
	public static void onEvent(ModelEvent.RegisterAdditional event) {
		for (ResourceLocation location : Minecraft.getInstance().getResourceManager().listResources("models/item/slabfish_bucket", s -> s.getPath().endsWith(".json")).keySet())
			event.register(new ResourceLocation(location.getNamespace(), location.getPath().substring("models/".length(), location.getPath().length() - ".json".length())));
	}

	@SubscribeEvent
	public static void onEvent(ModelEvent.BakingCompleted event) {
		event.getModels().put(new ModelResourceLocation(EnvironmentalItems.SLABFISH_BUCKET.getId(), "inventory"), new SlabfishBucketModel(event.getModelManager()));
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.getItemColors().register((stack, color) -> color > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), EnvironmentalItems.THIEF_HOOD.get(), EnvironmentalItems.HEALER_POUCH.get(), EnvironmentalItems.ARCHITECT_BELT.get(), EnvironmentalItems.WANDERER_BOOTS.get());
	}
}
