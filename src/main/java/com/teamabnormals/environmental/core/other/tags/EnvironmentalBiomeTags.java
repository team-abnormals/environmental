package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> HAS_HUSK = biomeTag("has_monster/husk");
	public static final TagKey<Biome> HAS_STRAY = biomeTag("has_monster/stray");

	public static final TagKey<Biome> IS_RAINFOREST = TagUtil.biomeTag("atmospheric", "is_rainforest");
	public static final TagKey<Biome> IS_DUNES = TagUtil.biomeTag("atmospheric", "is_dunes");
	public static final TagKey<Biome> IS_MAPLE = TagUtil.biomeTag("autumnity", "is_maple");
	public static final TagKey<Biome> IS_POISE = TagUtil.biomeTag("endergetic", "is_poise");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}