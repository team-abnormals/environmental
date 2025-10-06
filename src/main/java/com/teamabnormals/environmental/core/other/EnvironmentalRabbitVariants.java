package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlueprintRabbitVariants;
import com.teamabnormals.blueprint.core.events.LoadThisClassEvent;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalRabbitVariants extends BlueprintRabbitVariants {
	private static final int UNIQUE_OFFSET = 9415;

	public static final BlueprintRabbitVariant MUDDY = BlueprintRabbitVariants.register(UNIQUE_OFFSET, Environmental.location("muddy"), context -> getBiome(context).is(EnvironmentalBiomeTags.SPAWNS_MUDDY_RABBITS));
	public static final BlueprintRabbitVariant GRAY = BlueprintRabbitVariants.register(UNIQUE_OFFSET + 1, Environmental.location("gray"), context -> getBiome(context).is(EnvironmentalBiomeTags.SPAWNS_GRAY_RABBITS));

	@SubscribeEvent
	public static void $(LoadThisClassEvent event) {
	}
}