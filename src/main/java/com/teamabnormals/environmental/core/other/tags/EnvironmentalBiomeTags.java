package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> HAS_SLABFISH = biomeTag("has_animal/slabfish");
	public static final TagKey<Biome> HAS_DUCK = biomeTag("has_animal/duck");
	public static final TagKey<Biome> HAS_DEER = biomeTag("has_animal/deer");
	public static final TagKey<Biome> HAS_YAK = biomeTag("has_animal/yak");

	public static final TagKey<Biome> HAS_HUSK = biomeTag("has_monster/husk");
	public static final TagKey<Biome> HAS_STRAY = biomeTag("has_monster/stray");

	public static final TagKey<Biome> HAS_SHEEP = biomeTag("has_animal/sheep");
	public static final TagKey<Biome> HAS_COW = biomeTag("has_animal/cow");
	public static final TagKey<Biome> HAS_PIG = biomeTag("has_animal/pig");
	public static final TagKey<Biome> HAS_CHICKEN = biomeTag("has_animal/chicken");

	public static final TagKey<Biome> HAS_BLUEBELL = biomeTag("has_feature/bluebell");
	public static final TagKey<Biome> HAS_VIOLET = biomeTag("has_feature/violet");
	public static final TagKey<Biome> HAS_WARM_HIBISCUS = biomeTag("has_feature/warm_hibiscus");
	public static final TagKey<Biome> HAS_COOL_HIBISCUS = biomeTag("has_feature/cool_hibiscus");
	public static final TagKey<Biome> HAS_CATTAILS = biomeTag("has_feature/cattails");
	public static final TagKey<Biome> HAS_TALL_DEAD_BUSH = biomeTag("has_feature/tall_dead_bush");
	public static final TagKey<Biome> HAS_TALL_DEAD_BUSH_BADLANDS = biomeTag("has_feature/tall_dead_bush_badlands");
	public static final TagKey<Biome> HAS_MYCELIUM_SPROUTS = biomeTag("has_feature/mycelium_sprouts");
	public static final TagKey<Biome> HAS_MUD_DISK = biomeTag("has_feature/mud_disk");
	
	public static final TagKey<Biome> HAS_FLOWER_FOREST_VEGETATION = biomeTag("has_feature/flower_forest_vegetation");
	public static final TagKey<Biome> HAS_PLAINS_VEGETATION = biomeTag("has_feature/plains_vegetation");
	public static final TagKey<Biome> HAS_SAVANNA_VEGETATION = biomeTag("has_feature/savanna_vegetation");
	public static final TagKey<Biome> HAS_JUNGLE_VEGETATION = biomeTag("has_feature/jungle_vegetation");
	public static final TagKey<Biome> HAS_SWAMP_VEGETATION = biomeTag("has_feature/swamp_vegetation");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}
