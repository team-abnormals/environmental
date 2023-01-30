package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EnvironmentalItemTags {
	public static final TagKey<Item> WILLOW_LOGS = itemTag("willow_logs");
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
	public static final TagKey<Item> SLABFISH_SNACKS = itemTag("slabfish_snacks");
	public static final TagKey<Item> SLABFISH_FOOD = itemTag("slabfish_food");
	public static final TagKey<Item> SLABFISH_TAME_ITEMS = itemTag("slabfish_tame_items");

	private static TagKey<Item> itemTag(String tagName) {
		return TagUtil.itemTag(Environmental.MOD_ID, tagName);
	}
}