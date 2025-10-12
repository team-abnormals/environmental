package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EnvironmentalItemTags {
	public static final TagKey<Item> WILLOW_LOGS = itemTag("willow_logs");
	public static final TagKey<Item> PINE_LOGS = itemTag("pine_logs");
	public static final TagKey<Item> WISTERIA_LOGS = itemTag("wisteria_logs");
	public static final TagKey<Item> PLUM_LOGS = itemTag("plum_logs");

	public static final TagKey<Item> FOODS_CHERRY = TagUtil.itemTag("c", "foods/cherry");
	public static final TagKey<Item> FOODS_PLUM = TagUtil.itemTag("c", "foods/plum");

	public static final TagKey<Item> RAW_DUCK = TagUtil.itemTag("c", "foods/raw_duck");
	public static final TagKey<Item> RAW_VENISON = TagUtil.itemTag("c", "foods/raw_venison");
	public static final TagKey<Item> COOKED_DUCK = TagUtil.itemTag("c", "foods/cooked_duck");
	public static final TagKey<Item> COOKED_VENISON = TagUtil.itemTag("c", "foods/cooked_venison");

	public static final TagKey<Item> STORAGE_BLOCKS_PLUM = TagUtil.itemTag("c", "storage_blocks/plum");
	public static final TagKey<Item> STORAGE_BLOCKS_CHERRY = TagUtil.itemTag("c", "storage_blocks/cherry");
	public static final TagKey<Item> STORAGE_BLOCKS_DUCK_EGG = TagUtil.itemTag("c", "storage_blocks/duck_egg");
	public static final TagKey<Item> STORAGE_BLOCKS_CATTAIL_FLUFF = TagUtil.itemTag("c", "storage_blocks/cattail_fluff");

	public static final TagKey<Item> DUCK_FOOD = itemTag("duck_food");
	public static final TagKey<Item> DEER_FOOD = itemTag("deer_food");
	public static final TagKey<Item> DEER_TEMPT_ITEMS = itemTag("deer_tempt_items");
	public static final TagKey<Item> DEER_PLANTABLES = itemTag("deer_plantables");
	public static final TagKey<Item> DEER_CANNOT_PLANT = itemTag("deer_cannot_plant");
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
	public static final TagKey<Item> CONVERTABLE_TO_MUD = itemTag("convertable_to_mud");

	private static TagKey<Item> itemTag(String tagName) {
		return TagUtil.itemTag(Environmental.MOD_ID, tagName);
	}
}