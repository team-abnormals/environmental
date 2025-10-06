package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.neoforged.fml.ModList;

public class SlabfishModLoadedCondition implements SlabfishCondition {
	public static final MapCodec<SlabfishModLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.fieldOf("modid").forGetter(SlabfishModLoadedCondition::getModId)
	).apply(instance, SlabfishModLoadedCondition::new));

	private final String modId;

	public SlabfishModLoadedCondition(String modId) {
		this.modId = modId;
	}

	public String getModId() {
		return modId;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return ModList.get().isLoaded(this.modId);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.MOD_LOADED.get();
	}
}