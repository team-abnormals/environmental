package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class EnvironmentalBiomeTags {
	public static final TagKey<Biome> IS_PINE_BARRENS = biomeTag("is_pine_barrens");

	public static final TagKey<Biome> SPAWNS_MUDDY_RABBITS = biomeTag("spawns_muddy_rabbits");
	public static final TagKey<Biome> SPAWNS_GRAY_RABBITS = biomeTag("spawns_gray_rabbits");
	public static final TagKey<Biome> SPAWNS_CHESTNUT_DEER = biomeTag("spawns_chestnut_deer");
	public static final TagKey<Biome> SPAWNS_GRAY_DEER = biomeTag("spawns_gray_deer");

	public static final TagKey<Biome> HAS_LOG_CABIN = biomeTag("has_structure/log_cabin");

	public static final TagKey<Biome> HAS_SLABFISH = biomeTag("has_spawn/slabfish");
	public static final TagKey<Biome> HAS_DUCK = biomeTag("has_spawn/duck");
	public static final TagKey<Biome> HAS_DEER = biomeTag("has_spawn/deer");
	public static final TagKey<Biome> HAS_MUDDY_PIG = biomeTag("has_spawn/muddy_pig");
	public static final TagKey<Biome> HAS_REINDEER = biomeTag("has_spawn/reindeer");
	public static final TagKey<Biome> HAS_TAPIR = biomeTag("has_spawn/tapir");
	public static final TagKey<Biome> HAS_YAK = biomeTag("has_spawn/yak");
	public static final TagKey<Biome> HAS_ZEBRA = biomeTag("has_spawn/zebra");

	public static final TagKey<Biome> WITHOUT_DEER = biomeTag("without_animal/deer");

	public static final TagKey<Biome> HAS_PIG = biomeTag("has_spawn/pig");
	public static final TagKey<Biome> HAS_COW = biomeTag("has_spawn/cow");
	public static final TagKey<Biome> HAS_CHICKEN = biomeTag("has_spawn/chicken");
	public static final TagKey<Biome> HAS_SHEEP = biomeTag("has_spawn/sheep");

	public static final TagKey<Biome> HAS_CATTAILS = biomeTag("has_feature/cattails");
	public static final TagKey<Biome> HAS_CUP_LICHEN = biomeTag("has_feature/cup_lichen");
	public static final TagKey<Biome> HAS_DWARF_SPRUCE_SPARSE = biomeTag("has_feature/dwarf_spruce_sparse");
	public static final TagKey<Biome> HAS_BLUEBELL = biomeTag("has_feature/bluebell");
	public static final TagKey<Biome> HAS_VIOLET = biomeTag("has_feature/violet");
	public static final TagKey<Biome> HAS_TASSELFLOWER = biomeTag("has_feature/tasselflower");
	public static final TagKey<Biome> HAS_BIRD_OF_PARADISE = biomeTag("has_feature/bird_of_paradise");
	public static final TagKey<Biome> HAS_HIBISCUS = biomeTag("has_feature/hibiscus");
	public static final TagKey<Biome> HAS_MYCELIUM_SPROUTS = biomeTag("has_feature/mycelium_sprouts");
	public static final TagKey<Biome> HAS_CARTWHEEL = biomeTag("has_feature/cartwheel");

	public static final TagKey<Biome> HAS_SWAMP_VEGETATION = biomeTag("has_feature/swamp_vegetation");
	public static final TagKey<Biome> HAS_FOREST_VEGETATION = biomeTag("has_feature/flower_forest_vegetation");
	public static final TagKey<Biome> HAS_SAVANNA_VEGETATION = biomeTag("has_feature/savanna_vegetation");
	public static final TagKey<Biome> HAS_PLAINS_VEGETATION = biomeTag("has_feature/plains_vegetation");
	public static final TagKey<Biome> HAS_JUNGLE_VEGETATION = biomeTag("has_feature/jungle_vegetation");

	public static final TagKey<Biome> HAS_MUD_DISK = biomeTag("has_feature/mud_disk");

	public static final TagKey<Biome> IS_RAINFOREST = TagUtil.biomeTag(EnvironmentalConstants.ATMOSPHERIC, "is_rainforest");
	public static final TagKey<Biome> IS_DUNES = TagUtil.biomeTag(EnvironmentalConstants.ATMOSPHERIC, "is_dunes");

	private static TagKey<Biome> biomeTag(String tagName) {
		return TagUtil.biomeTag(Environmental.MOD_ID, tagName);
	}
}