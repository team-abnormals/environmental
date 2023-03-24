package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> ONLY_ALLOWS_MUDDY_RABBITS = biomeTag("only_allows_muddy_rabbits");

	public static final TagKey<Biome> HAS_SLABFISH = biomeTag("has_animal/slabfish");
	public static final TagKey<Biome> HAS_DUCK = biomeTag("has_animal/duck");
	public static final TagKey<Biome> HAS_DEER = biomeTag("has_animal/deer");
	public static final TagKey<Biome> HAS_REINDEER = biomeTag("has_animal/reindeer");
	public static final TagKey<Biome> HAS_YAK = biomeTag("has_animal/yak");

	public static final TagKey<Biome> HAS_PIG = biomeTag("has_animal/pig");
	public static final TagKey<Biome> HAS_COW = biomeTag("has_animal/cow");
	public static final TagKey<Biome> HAS_CHICKEN = biomeTag("has_animal/chicken");
	public static final TagKey<Biome> HAS_SHEEP = biomeTag("has_animal/sheep");

	public static final TagKey<Biome> HAS_CATTAILS = biomeTag("has_feature/cattails");
	public static final TagKey<Biome> HAS_BLUEBELL = biomeTag("has_feature/has_bluebell");
	public static final TagKey<Biome> HAS_VIOLET = biomeTag("has_feature/has_violet");
	public static final TagKey<Biome> HAS_RARE_VIOLET = biomeTag("has_feature/has_rare_violet");
	public static final TagKey<Biome> HAS_TASSELFLOWER = biomeTag("has_feature/has_tasselflower");
	public static final TagKey<Biome> HAS_BIRD_OF_PARADISE = biomeTag("has_feature/has_bird_of_paradise");
	public static final TagKey<Biome> HAS_HIBISCUS = biomeTag("has_feature/has_hibiscus");

	public static final TagKey<Biome> HAS_MUD_DISK = biomeTag("has_feature/has_mud_disk");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}