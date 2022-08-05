package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class SlabfishModLoadedCondition implements SlabfishCondition {

	public static final Codec<SlabfishModLoadedCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.fieldOf("mod_id").forGetter(SlabfishModLoadedCondition::getModId)
	).apply(instance, SlabfishModLoadedCondition::new));

	private final String modId;

	private SlabfishModLoadedCondition(String modId) {
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
