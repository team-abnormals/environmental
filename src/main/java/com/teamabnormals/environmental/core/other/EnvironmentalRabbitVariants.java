package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlueprintRabbitVariants;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalRabbitVariants extends BlueprintRabbitVariants {
	private static final int UNIQUE_OFFSET = 9415;

	public static final BlueprintRabbitVariant MUDDY = BlueprintRabbitVariants.register(UNIQUE_OFFSET, Environmental.location("muddy"), context -> getBiome(context).is(EnvironmentalBiomeTags.SPAWNS_MUDDY_RABBITS));
	public static final BlueprintRabbitVariant GRAY = BlueprintRabbitVariants.register(UNIQUE_OFFSET + 1, Environmental.location("gray"), context -> getBiome(context).is(EnvironmentalBiomeTags.SPAWNS_GRAY_RABBITS));
}