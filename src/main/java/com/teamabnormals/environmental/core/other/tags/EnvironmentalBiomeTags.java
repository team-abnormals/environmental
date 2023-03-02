package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> HAS_SLABFISH = biomeTag("has_animal/slabfish");
	public static final TagKey<Biome> HAS_DUCK = biomeTag("has_animal/duck");
	public static final TagKey<Biome> HAS_DEER = biomeTag("has_animal/deer");
	public static final TagKey<Biome> HAS_REINDEER = biomeTag("has_animal/reindeer");
	public static final TagKey<Biome> HAS_YAK = biomeTag("has_animal/yak");

	public static final TagKey<Biome> HAS_HUSK = biomeTag("has_monster/husk");
	public static final TagKey<Biome> HAS_STRAY = biomeTag("has_monster/stray");

	public static final TagKey<Biome> HAS_PIG = biomeTag("has_animal/pig");
	public static final TagKey<Biome> HAS_COW = biomeTag("has_animal/cow");
	public static final TagKey<Biome> HAS_CHICKEN = biomeTag("has_animal/chicken");
	public static final TagKey<Biome> HAS_SHEEP = biomeTag("has_animal/sheep");

	public static final TagKey<Biome> HAS_CATTAILS = biomeTag("has_feature/cattails");
	public static final TagKey<Biome> HAS_MUD_DISK = biomeTag("has_feature/has_mud_disk");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}