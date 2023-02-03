package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> HAS_HUSK = biomeTag("has_monster/husk");
	public static final TagKey<Biome> HAS_STRAY = biomeTag("has_monster/stray");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}