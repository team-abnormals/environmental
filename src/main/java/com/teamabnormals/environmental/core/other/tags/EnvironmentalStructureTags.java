package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class EnvironmentalStructureTags {
	public static final TagKey<ConfiguredStructureFeature<?, ?>> HAS_HEALER_POUCH = structureTag("has_healer_pouch");

	private static TagKey<ConfiguredStructureFeature<?, ?>> structureTag(String tagName) {
		return TagUtil.configuredStructureTag(Environmental.MOD_ID, tagName);
	}
}