package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class EnvironmentalStructureTags {
	public static final TagKey<Structure> HAS_HEALER_POUCH = structureTag("has_healer_pouch");

	private static TagKey<Structure> structureTag(String tagName) {
		return TagUtil.structureTag(Environmental.MOD_ID, tagName);
	}
}