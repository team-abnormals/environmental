package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EnvironmentalItemTags {
	public static final TagKey<Item> WILLOW_LOGS = itemTag("willow_logs");
	public static final TagKey<Item> PINE_LOGS = itemTag("pine_logs");
	public static final TagKey<Item> WISTERIA_LOGS = itemTag("wisteria_logs");
	public static final TagKey<Item> CHERRY_LOGS = itemTag("cherry_logs");

	public static final TagKey<Item> FRUITS = TagUtil.itemTag("forge", "fruits");
	public static final TagKey<Item> FRUITS_CHERRY = TagUtil.itemTag("forge", "fruits/cherry");

	public static final TagKey<Item> RAW_DUCK = TagUtil.itemTag("forge", "raw_duck");
	public static final TagKey<Item> RAW_VENISON = TagUtil.itemTag("forge", "raw_venison");
	public static final TagKey<Item> RAW_FISHES = TagUtil.itemTag("forge", "raw_fishes");
	public static final TagKey<Item> RAW_FISHES_KOI = TagUtil.itemTag("forge", "raw_fishes/koi");
	public static final TagKey<Item> COOKED_DUCK = TagUtil.itemTag("forge", "cooked_duck");
	public static final TagKey<Item> COOKED_VENISON = TagUtil.itemTag("forge", "cooked_venison");

	public static final TagKey<Item> DUCK_FOOD = itemTag("duck_food");
	public static final TagKey<Item> DEER_FOOD = itemTag("deer_food");
	public static final TagKey<Item> DEER_TEMPT_ITEMS = itemTag("deer_tempt_items");
	public static final TagKey<Item> DEER_PLANTABLES = itemTag("deer_plantables");
	public static final TagKey<Item> DEER_FLOWER_ITEMS = itemTag("deer_flower_items");
	public static final TagKey<Item> DEER_STRONG_FLOWER_ITEMS = itemTag("deer_strong_flower_items");
	public static final TagKey<Item> DEER_SUPER_FLOWER_ITEMS = itemTag("deer_super_flower_items");
	public static final TagKey<Item> REINDEER_FOOD = itemTag("reindeer_food");
	public static final TagKey<Item> REINDEER_TEMPT_ITEMS = itemTag("reindeer_tempt_items");
	public static final TagKey<Item> SLABFISH_SNACKS = itemTag("slabfish_snacks");
	public static final TagKey<Item> SLABFISH_FOOD = itemTag("slabfish_food");
	public static final TagKey<Item> SLABFISH_TAME_ITEMS = itemTag("slabfish_tame_items");
	public static final TagKey<Item> YAK_FOOD = itemTag("yak_food");
	public static final TagKey<Item> PIG_TRUFFLE_ITEMS = itemTag("pig_truffle_items");
	public static final TagKey<Item> MUDDY_PIG_DECORATIONS = itemTag("muddy_pig_decorations");
	public static final TagKey<Item> MUDDY_PIG_DRYING_ITEMS = itemTag("muddy_pig_drying_items");
	public static final TagKey<Item> SPAWNS_ON_MUDDY_PIG = itemTag("spawns_on_muddy_pig");

	private static TagKey<Item> itemTag(String tagName) {
		return TagUtil.itemTag(Environmental.MOD_ID, tagName);
	}
}