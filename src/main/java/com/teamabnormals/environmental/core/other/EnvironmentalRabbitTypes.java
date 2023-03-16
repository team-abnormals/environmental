package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.api.BlueprintRabbitTypes;
import com.teamabnormals.blueprint.core.api.BlueprintRabbitTypes.BlueprintRabbitType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalRabbitTypes {
	private static final int UNIQUE_OFFSET = 9415;

	public static final BlueprintRabbitType MUDDY = BlueprintRabbitTypes.register(UNIQUE_OFFSET, new ResourceLocation(Environmental.MOD_ID, "muddy"));
}